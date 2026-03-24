package com.smartcity.urban_management.modules.task.service.impl;

import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Override
    public void autoAssign(Report report) {
        // TODO: logic assign theo category → department

        System.out.println("Auto assigning report: " + report.getId());
    }
}
