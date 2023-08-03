package com.dhbinh.yummybites.workingtime.service;

import com.dhbinh.yummybites.employee.service.EmployeeService;
import com.dhbinh.yummybites.employee.service.dto.EmployeeDTO;
import com.dhbinh.yummybites.employee.service.mapper.EmployeeMapper;
import com.dhbinh.yummybites.workingtime.entity.WorkingTime;
import com.dhbinh.yummybites.workingtime.repository.WorkingTimeRepository;
import com.dhbinh.yummybites.workingtime.service.dto.WorkingTimeDTO;
import com.dhbinh.yummybites.workingtime.service.mapper.WorkingTimeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class WorkingTimeService {

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Autowired
    private EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    private final WorkingTimeMapper workingTimeMapper;

    public WorkingTimeDTO checkin(Long ID){
        EmployeeDTO dto = employeeService.findByID(ID);
        WorkingTime workingTime = WorkingTime.builder()
                .checkinHour(LocalDateTime.now())
                .employee(employeeMapper.toEntity(dto))
                .build();

        return workingTimeMapper.toDTO(workingTimeRepository.save(workingTime));
    }

    public WorkingTimeDTO checkout(Long ID){
        EmployeeDTO dto = employeeService.findByID(ID);
        WorkingTime workingTime = workingTimeRepository.findByEmployee(employeeMapper.toEntity(dto));
        workingTime.setCheckoutHour(LocalDateTime.now());

        return workingTimeMapper.toDTO(workingTimeRepository.save(workingTime));
    }
}
