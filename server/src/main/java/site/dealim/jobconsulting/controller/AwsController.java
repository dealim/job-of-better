package site.dealim.jobconsulting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dealim.jobconsulting.security.custom.CustomMember;
import site.dealim.jobconsulting.service.AwsService;
import site.dealim.jobconsulting.service.MyInfoService;

import java.util.List;

@RestController
@Log4j2
@Tag(name = "AWS 파일 업로드", description = "AWS Controller")
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class AwsController {

    private final AwsService awsService;
    private final MyInfoService myInfoService;

    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "S3 파일 업로드" , description = "multifile list를 s3에 업로드하여 filename list 반환")
    public ResponseEntity<?> uploadFile(
            @AuthenticationPrincipal CustomMember member,
            @RequestParam("path") String path,
            @RequestParam("file") List<MultipartFile> multipartFiles) throws Exception{

        log.info("profile file aws Upload :, path - {}", path);
        List<String> fileUrls = null;

        if(path.equals("profile")) {
            fileUrls = myInfoService.uploadProfileImg(member.getMember(), awsService.uploadFile(member.getMember().getIdx(), path, multipartFiles));
        }

        if(path.equals("resume")) {
            fileUrls = myInfoService.uploadResumeFile(member.getMember(), awsService.uploadFile(member.getMember().getIdx(), path, multipartFiles));
        }

        if(fileUrls == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
        }

        log.info("파일 업로드 성공!");
        return ResponseEntity.ok(fileUrls);
    }

    @GetMapping("/get-files")
    public ResponseEntity<?> getFiles(@AuthenticationPrincipal CustomMember member, @RequestParam("path") String path) {
        log.info("유저 파일 불러오기...");
        return new ResponseEntity<>(myInfoService.getFileList(member.getMember().getIdx(), path), HttpStatus.OK);
    }

    @Secured("ROLE_COMPANY")
    @GetMapping("/get-files-idx")
    public ResponseEntity<?> getFilesIdx(@RequestParam("path") String path, @RequestParam("memIdx") Long memIdx) {
        log.info("유저 파일 불러오기... : memIdx - {}", memIdx);
        return new ResponseEntity<>(myInfoService.getFileList(memIdx, path), HttpStatus.OK);
    }

    @DeleteMapping("/delete-file/{fileIdx}")
    public ResponseEntity<?> deleteFile(@AuthenticationPrincipal CustomMember member,@PathVariable("fileIdx") Long fileIdx) {
        log.info("파일 삭제... : fileIdx - {}", fileIdx);
        myInfoService.deleteFileByIdx(fileIdx);
        return new ResponseEntity<>("test",HttpStatus.OK);
    }

}
