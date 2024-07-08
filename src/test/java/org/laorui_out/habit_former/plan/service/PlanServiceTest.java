package org.laorui_out.habit_former.plan.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PlanServiceTest {

    @Mock
    private PlanMapper planMapper;

    @InjectMocks
    private PlanService planService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsersAllPlanBean() {
        int userID = 1;
        List<PlanBean> mockPlanBeans = new ArrayList<>();
        PlanBean planBean = new PlanBean();
        planBean.setPlanID(1);
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus("NOT_CHECKED");
        planBean.setUserID(userID);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        mockPlanBeans.add(planBean);

        when(planMapper.getAllPlanBeanByUserID(userID)).thenReturn(mockPlanBeans);

        List<PlanBean> planBeans = planService.getUsersAllPlanBean(userID);
        assertNotNull(planBeans);
        assertEquals(1, planBeans.size());
        assertEquals("Test Plan", planBeans.get(0).getPlanName());
        verify(planMapper, times(1)).getAllPlanBeanByUserID(userID);
    }

    @Test
    public void testGetPlanBean() {
        int planID = 1;
        PlanBean mockPlanBean = new PlanBean();
        mockPlanBean.setPlanID(planID);
        mockPlanBean.setPlanName("Test Plan");
        mockPlanBean.setPlanInfo("Test Info");
        mockPlanBean.setStatus("NOT_CHECKED");
        mockPlanBean.setUserID(1);
        mockPlanBean.setPlanDate(new Date(System.currentTimeMillis()));
        mockPlanBean.setPlanTime(new Time(System.currentTimeMillis()));

        when(planMapper.getPlanBeanByPlanID(planID)).thenReturn(mockPlanBean);

        PlanBean planBean = planService.getPlanBean(planID);
        assertNotNull(planBean);
        assertEquals("Test Plan", planBean.getPlanName());
        verify(planMapper, times(1)).getPlanBeanByPlanID(planID);
    }

    @Test
    public void testGetAllDailyPlan() {
        int planID = 1;
        List<DailyPlanBean> mockDailyPlanBeans = new ArrayList<>();
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDailyPlanID(1);
        dailyPlanBean.setDate(new Date(System.currentTimeMillis()));
        dailyPlanBean.setPlanDetail("Test Daily Plan");
        dailyPlanBean.setStatus("NOT_CHECKED");
        dailyPlanBean.setPlanID(planID);
        mockDailyPlanBeans.add(dailyPlanBean);

        when(planMapper.getAllDailyPlanIDByPlanID(planID)).thenReturn(mockDailyPlanBeans);

        List<DailyPlanBean> dailyPlanBeans = planService.getAllDailyPlan(planID);
        assertNotNull(dailyPlanBeans);
        assertEquals(1, dailyPlanBeans.size());
        assertEquals("Test Daily Plan", dailyPlanBeans.get(0).getPlanDetail());
        verify(planMapper, times(1)).getAllDailyPlanIDByPlanID(planID);
    }
}