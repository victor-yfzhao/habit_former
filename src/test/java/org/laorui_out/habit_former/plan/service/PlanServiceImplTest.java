package org.laorui_out.habit_former.plan.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class PlanServiceImplTest {

    @Mock
    private PlanMapper planMapper;

    @InjectMocks
    private PlanServiceImpl planServiceImpl;

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

        List<PlanBean> planBeans = planServiceImpl.getUsersAllPlanBean(userID);
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

        PlanBean planBean = planServiceImpl.getPlanBean(planID);
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

        List<DailyPlanBean> dailyPlanBeans = planServiceImpl.getAllDailyPlan(planID);
        assertNotNull(dailyPlanBeans);
        assertEquals(1, dailyPlanBeans.size());
        assertEquals("Test Daily Plan", dailyPlanBeans.get(0).getPlanDetail());
        verify(planMapper, times(1)).getAllDailyPlanIDByPlanID(planID);
    }
    @Test
    public void testGetDailyPlanBean() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        when(planMapper.getDailyPlanByID(1)).thenReturn(dailyPlanBean);

        DailyPlanBean result = planServiceImpl.getDailyPlanBean(1);
        assertEquals(dailyPlanBean, result);
    }

    @Test
    public void testUpdateDailyPlanDetail() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanDetail("old detail");
        when(planMapper.getDailyPlanByID(1)).thenReturn(dailyPlanBean);
        when(planMapper.updateDailyPlan(any(DailyPlanBean.class))).thenReturn(1);

        DailyPlanBean result = planServiceImpl.updateDailyPlanDetail(1, "new detail");
        assertEquals("new detail", result.getPlanDetail());
        verify(planMapper).updateDailyPlan(any(DailyPlanBean.class));
    }

    @Test
    public void testUpdateDailyPlanDetail_NotFound() {
        when(planMapper.getDailyPlanByID(1)).thenReturn(null);

        DailyPlanBean result = planServiceImpl.updateDailyPlanDetail(1, "new detail");
        assertNull(result);
    }

    @Test
    public void testDeletePlan() {
        when(planMapper.deletePlanByID(1)).thenReturn(1);

        boolean result = planServiceImpl.deletePlan(1);
        assertTrue(result);
    }

    @Test
    public void testDeletePlan_NotFound() {
        when(planMapper.deletePlanByID(1)).thenReturn(0);

        boolean result = planServiceImpl.deletePlan(1);
        assertFalse(result);
    }

    @Test
    public void testAddPlan() {
        PlanBean planBean = new PlanBean();
        when(planMapper.addPlanBean(any(PlanBean.class))).thenReturn(1);

        PlanBean result = planServiceImpl.addPlan("info", "name", 1);
        assertEquals("info", result.getPlanInfo());
        assertEquals("name", result.getPlanName());
        assertEquals(1, result.getUserID());
        verify(planMapper).addPlanBean(any(PlanBean.class));
    }

    @Test
    public void testAddDailyPlan() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        when(planMapper.addDailyPlanBean(any(DailyPlanBean.class))).thenReturn(1);

        DailyPlanBean result = planServiceImpl.addDailyPlan("detail", 1);
        assertEquals("detail", result.getPlanDetail());
        assertEquals(1, result.getPlanID());
        verify(planMapper).addDailyPlanBean(any(DailyPlanBean.class));
    }

    @Test
    public void testCheckDailyPlan() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        when(planMapper.getDailyPlanByID(1)).thenReturn(dailyPlanBean);
        when(planMapper.updateDailyPlan(any(DailyPlanBean.class))).thenReturn(1);

        boolean result = planServiceImpl.checkDailyPlan(1);
        assertTrue(result);
        verify(planMapper).updateDailyPlan(any(DailyPlanBean.class));
    }

    @Test
    public void testCheckDailyPlan_NotFound() {
        when(planMapper.getDailyPlanByID(1)).thenReturn(null);

        boolean result = planServiceImpl.checkDailyPlan(1);
        assertFalse(result);
    }

    @Test
    public void testRefreshAllPlanStatus() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanID(1);
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setPlanDate(new java.util.Date());
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setStatus(Constants.CHECKED);
        dailyPlanBean.setDate(new java.util.Date());
        when(planMapper.getAllPlanBeanByUserID(1)).thenReturn(Arrays.asList(planBean));
        when(planMapper.getAllDailyPlanIDByPlanID(1)).thenReturn(Arrays.asList(dailyPlanBean));

        planServiceImpl.refreshAllPlanStatus(1);

        //verify(planMapper).updateDailyPlan(any(DailyPlanBean.class));
        verify(planMapper).updatePlan(any(PlanBean.class));
    }

    @Test
    public void testRefreshPlanStatus() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanID(1);
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setPlanDate(new java.util.Date());
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setStatus(Constants.CHECKED);
        dailyPlanBean.setDate(new java.util.Date());
        when(planMapper.getPlanBeanByPlanID(1)).thenReturn(planBean);
        when(planMapper.getAllDailyPlanIDByPlanID(1)).thenReturn(Arrays.asList(dailyPlanBean));

        planServiceImpl.refreshPlanStatus(1);

        //verify(planMapper).updateDailyPlan(any(DailyPlanBean.class));
        verify(planMapper).updatePlan(any(PlanBean.class));
    }
}