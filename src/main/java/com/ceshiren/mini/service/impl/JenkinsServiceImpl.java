package com.ceshiren.mini.service.impl;

import com.ceshiren.mini.dto.OperateJenkinsJobDTO;
import com.ceshiren.mini.entity.TestTask;
import com.ceshiren.mini.exception.ServiceException;
import com.ceshiren.mini.service.JenkinsService;
import com.ceshiren.mini.util.JenkinsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class JenkinsServiceImpl implements JenkinsService {
    //jenkins地址
    @Value("${jenkins.url}")
    private String jenkinsUrl;
    //Jenkins用户
    @Value("${jenkins.user}")
    private String jenkinsUserName;
    //Jenkins密码
    @Value("${jenkins.pwd}")
    private String jenkinsPassword;
    @Value("${jenkins.job}")
    private String job;

    @Override
    public OperateJenkinsJobDTO runTask(TestTask testTask) {
        OperateJenkinsJobDTO operateJenkinsJobDTO = new OperateJenkinsJobDTO();
        operateJenkinsJobDTO.setJenkinsUrl(jenkinsUrl);
        operateJenkinsJobDTO.setJenkinsUserName(jenkinsUserName);
        operateJenkinsJobDTO.setJenkinsPassword(jenkinsPassword);
        operateJenkinsJobDTO.setJob(job);
        HashMap<String, String> params = new HashMap<>();

        params.put("testCommand", testTask.getTestCommand());

        operateJenkinsJobDTO.setParams(params);
        System.out.println(operateJenkinsJobDTO);
        try {
            OperateJenkinsJobDTO jenkinsJobDTO = JenkinsUtil.buildWithXML(operateJenkinsJobDTO);
            jenkinsJobDTO.setJenkinsUrl(operateJenkinsJobDTO.getJenkinsUrl());

        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
        return operateJenkinsJobDTO;




    }
}
