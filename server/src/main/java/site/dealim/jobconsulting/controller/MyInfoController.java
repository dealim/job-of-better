package site.dealim.jobconsulting.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dealim.jobconsulting.domain.Member;
import site.dealim.jobconsulting.dto.ProgramCompanyDto;
import site.dealim.jobconsulting.security.custom.CustomMember;
import site.dealim.jobconsulting.service.MyInfoService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "회원 - 마이페이지", description = "Member Info Controller")
@RequestMapping("/api/user/")
@Slf4j
public class MyInfoController {
    @Autowired
    MyInfoService myinfoService;

    @Secured("ROLE_USER")
    @GetMapping("/user-profile-info")
    public Member userProfileInfo(@AuthenticationPrincipal CustomMember customMember) {
        log.info("사용자 정보 불러오기");
        Member user = customMember.getMember();
        return myinfoService.userProfileInfo(user.getUsername());
    }

    @Secured("ROLE_USER")
    @GetMapping("/pg-info")
    public ProgramCompanyDto pgInfo(@AuthenticationPrincipal CustomMember customMember) {
        log.info("사용자 지정 프로그램 정보 불러오기");
        Member user = customMember.getMember();
        ProgramCompanyDto pgInfo = myinfoService.pgInfo(user.getPgIdx());
        //System.out.println("pgInfo = " + pgInfo);
        return pgInfo;
    }

    @Secured("ROLE_USER")
    @GetMapping("/cover-letter-info")
    public Map coverLetterInfo(@AuthenticationPrincipal CustomMember customMember) {
        log.info("자소서 항목 불러오기");
        Member user = customMember.getMember();
        Map coverLetterInfo = new HashMap();
        coverLetterInfo.put("clList",myinfoService.coverLetterList(user.getIdx(),user.getPgIdx()));
        coverLetterInfo.put("clPercent",myinfoService.coverLetterInfo(user.getIdx(),user.getPgIdx()));
        return coverLetterInfo;
    }

    @Secured("ROLE_USER")
    @PostMapping("/register-interview")
    public void registerInterview(
            @RequestParam("registeredInterviewDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime registeredInterviewDate,
            @AuthenticationPrincipal CustomMember customMember)
    {
        log.info("면접시간 저장... : registeredInterviewDate = " + registeredInterviewDate);
        Member user = customMember.getMember();
        myinfoService.registerInterview(registeredInterviewDate.withNano(0).withSecond(0), user.getIdx(), user.getPgIdx());
    }

}
