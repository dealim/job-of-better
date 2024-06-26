import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Button, Card, CardBody, CardHeader, CardSubtitle, CardTitle, Col, Row} from "reactstrap";
import {Viewer} from "@toast-ui/react-editor";
import {getContentSummary, getProgram} from "../../../apis/program";
import {format} from "date-fns";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faUserCheck, faUserClock, faUsers, faUserSlash} from '@fortawesome/free-solid-svg-icons'
import TotalRegNumber from "../../../components/Infos/numbers/TotalRegNumber";
import ApprovedNumber from "../../../components/Infos/numbers/ApprovedNumber";
import RegisteredNumber from "../../../components/Infos/numbers/RegisteredNumber";
import RejectedNumber from "../../../components/Infos/numbers/RejectedNumber";
import ProgDateInfos from "../../../components/Infos/ProgDateInfos";
import RefreshBtn from "../../../components/Buttons/RefreshBtn";
import ProgCurrentStatus from "../../../components/Infos/ProgCurrentStatus";

const ProgramDetails = () => {
    const {pgIdx} = useParams();
    const navigate = useNavigate();
    const [program, setProgram] = useState();
    const [isSummaryLoading, setIsSummaryLoading] = useState(false);

    useEffect(() => {
        updateProgram(pgIdx);
    }, []);

    const updateProgram = (pgIdx) => {
        getProgram(pgIdx).then((response) => {
            const fetchedProgram = response.data
            setProgram({...fetchedProgram})
        });
    }

    const handleSummaryRefreshBtn = async () => {
        setIsSummaryLoading(true)
        try {
            await getContentSummary(pgIdx);
            await updateProgram(pgIdx);
        } catch (e) {
            console.log(e)
        } finally {
            setIsSummaryLoading(false)
        }
    }

    const handleModifyBtn = (pgIdx) => {
        navigate('/company/program-modify/' + pgIdx)
    }

    return (
        <div className="content program">
            <Card className='program-details'>
                <CardHeader>
                    <div className="program-details-tt">
                        <CardTitle tag="h1">{program ? program.pgTitle : null}</CardTitle>
                        <CardSubtitle>{program ? "등록일 : " + format(program.pgModifiedDate, 'yyyy-MM-dd') + " | 수정일: " + format(program.pgModifiedDate, 'yyyy-MM-dd') : null}</CardSubtitle>
                    </div>
                    <Button onClick={() => {
                        handleModifyBtn(pgIdx)
                    }}>수정하기</Button>
                </CardHeader>
                <CardBody>
                    <Row>
                        <Col>
                            <div className='program-title'>
                                <span>프로그램 정보</span>
                            </div>
                            <div className='program-content'>
                                <Row>
                                    <Col>
                                        <label>현재 상태</label>
                                        <ProgCurrentStatus
                                            program={program}
                                        />
                                    </Col>
                                </Row>
                                <Row className="ppl-info-row">
                                    <Col>
                                        <Row className="ppl-info">
                                            <Col>
                                                <span>
                                                <FontAwesomeIcon icon={faUsers}/>
                                                    </span>
                                            </Col>
                                            <Col>
                                                <label>총 신청자</label>
                                                <TotalRegNumber pgIdx={pgIdx}/>
                                            </Col>
                                        </Row>
                                    </Col>
                                    <Col>
                                        <Row className="ppl-info">
                                            <Col>
                                                <span>
                                                    <FontAwesomeIcon icon={faUserCheck}/>
                                                </span>
                                            </Col>
                                            <Col>
                                                <label>참여자</label>
                                                <ApprovedNumber pgIdx={pgIdx}/>
                                            </Col>
                                        </Row>
                                    </Col>
                                </Row>
                                <Row className="ppl-info-row">
                                    <Col>
                                        <Row className="ppl-info">
                                            <Col>
                                                <span>
                                                    <FontAwesomeIcon icon={faUserClock}/>
                                                </span>
                                            </Col>
                                            <Col>
                                                <label>가입신청</label>
                                                <RegisteredNumber pgIdx={pgIdx}/>
                                            </Col>
                                        </Row>
                                    </Col>
                                    <Col>
                                        <Row className="ppl-info">
                                            <Col>
                                                <span>
                                                    <FontAwesomeIcon icon={faUserSlash}/>
                                                </span>
                                            </Col>
                                            <Col>
                                                <label>불합격자</label>
                                                <RejectedNumber pgIdx={pgIdx}/>
                                            </Col>
                                        </Row>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        <div className="ai-summary-label">
                                            <label>AI 교육요약</label>
                                            <RefreshBtn
                                                onClick={handleSummaryRefreshBtn}
                                                loading={isSummaryLoading}
                                            />
                                        </div>
                                        <div className="form-control">
                                            <Viewer
                                                key={program ? program.pgContentSummary : null}
                                                initialValue={program ? program.pgContentSummary : ""}
                                            />
                                        </div>
                                    </Col>
                                </Row>
                            </div>
                        </Col>
                        <Col>
                            <ProgDateInfos program={program}/>
                        </Col>
                    </Row>
                    <div className='program-title'>
                        <span>프로그램 내용</span>
                    </div>
                    <div className='program-content'>
                        <Viewer
                            key={program ? program.pgContent : null}
                            initialValue={program ? program.pgContent : null}
                        />
                    </div>
                </CardBody>
            </Card>
        </div>
    );
};

export default ProgramDetails;