package com.ceshiren.mini.service;

import com.ceshiren.mini.dto.OperateJenkinsJobDTO;
import com.ceshiren.mini.dto.TestTaskDTO;
import com.ceshiren.mini.entity.TestTask;

public interface JenkinsService {

    OperateJenkinsJobDTO runTask(TestTask testTask);
}
