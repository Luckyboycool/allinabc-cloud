package com.allinabc.cloud.org;

import com.allinabc.cloud.org.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author QQF
 * @date 2020/12/24 15:31
 */


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrgApplication.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void save(){

    }

    @Test
    public void update(){

    }

    @Test
    public void find(){

    }

    @Test
    public void del(){

    }


}