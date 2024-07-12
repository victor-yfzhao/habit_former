package org.laorui_out.habit_former.plan.service;

import org.junit.jupiter.api.BeforeEach;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.mapper.DailyPlanMapper;
import org.laorui_out.habit_former.mapper.FitPlanMapper;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.mapper.StudyPlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CheckPlanServiceImplTest {

    @Mock
    private PlanMapper planMapper;

    @Mock
    private DailyPlanMapper dailyPlanMapper;

    @Mock
    private FitPlanMapper fitPlanMapper;

    @Mock
    private StudyPlanMapper studyPlanMapper;

    @InjectMocks
    private CheckPlanServiceImpl checkPlanService;

    @BeforeEach
    public void setUp() {
        // 初始化 CheckPlanServiceImpl 对象，并注入模拟的依赖
    }

    @Test
    public void testCheckDailyPlan() {
        int dailyPlanID = 1;
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDailyPlanID(dailyPlanID);
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);

        when(dailyPlanMapper.getDailyPlanByID(dailyPlanID)).thenReturn(dailyPlanBean);

        boolean result = checkPlanService.checkDailyPlan(dailyPlanID);

        assertTrue(result);
        assertEquals(Constants.CHECKED, dailyPlanBean.getStatus());
        verify(dailyPlanMapper, times(1)).updateDailyPlan(dailyPlanBean);
    }

    @Test
    public void testUncheckDailyPlan() {
        int dailyPlanID = 1;
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDailyPlanID(dailyPlanID);
        dailyPlanBean.setStatus(Constants.CHECKED);

        when(dailyPlanMapper.getDailyPlanByID(dailyPlanID)).thenReturn(dailyPlanBean);

        boolean result = checkPlanService.uncheckDailyPlan(dailyPlanID);

        assertTrue(result);
        assertEquals(Constants.NOT_CHECKED, dailyPlanBean.getStatus());
        verify(dailyPlanMapper, times(1)).updateDailyPlan(dailyPlanBean);
    }

    @Test
    public void testCheckFitPlan() {
        int fitPlanID = 1;
        FitPlanBean fitPlanBean = new FitPlanBean();
        fitPlanBean.setFitPlanItemID(fitPlanID);
        fitPlanBean.setStatus(Constants.NOT_CHECKED);

        when(fitPlanMapper.getFitPlanByID(fitPlanID)).thenReturn(fitPlanBean);

        boolean result = checkPlanService.checkFitPlan(fitPlanID);

        assertTrue(result);
        assertEquals(Constants.CHECKED, fitPlanBean.getStatus());
        verify(fitPlanMapper, times(1)).updateFitPlan(fitPlanBean);
    }

    @Test
    public void testUncheckFitPlan() {
        int fitPlanID = 1;
        FitPlanBean fitPlanBean = new FitPlanBean();
        fitPlanBean.setFitPlanItemID(fitPlanID);
        fitPlanBean.setStatus(Constants.CHECKED);

        when(fitPlanMapper.getFitPlanByID(fitPlanID)).thenReturn(fitPlanBean);

        boolean result = checkPlanService.uncheckFitPlan(fitPlanID);

        assertTrue(result);
        assertEquals(Constants.NOT_CHECKED, fitPlanBean.getStatus());
        verify(fitPlanMapper, times(1)).updateFitPlan(fitPlanBean);
    }

    @Test
    public void testCheckStudyPlan() {
        int studyPlanID = 1;
        StudyPlanBean studyPlanBean = new StudyPlanBean();
        studyPlanBean.setStudyPlanItemID(studyPlanID);
        studyPlanBean.setStatus(Constants.NOT_CHECKED);

        when(studyPlanMapper.getStudyPlanByID(studyPlanID)).thenReturn(studyPlanBean);

        boolean result = checkPlanService.checkStudyPlan(studyPlanID);

        assertTrue(result);
        assertEquals(Constants.CHECKED, studyPlanBean.getStatus());
        verify(studyPlanMapper, times(1)).updateStudyPlan(studyPlanBean);
    }

    @Test
    public void testUncheckStudyPlan() {
        int studyPlanID = 1;
        StudyPlanBean studyPlanBean = new StudyPlanBean();
        studyPlanBean.setStudyPlanItemID(studyPlanID);
        studyPlanBean.setStatus(Constants.CHECKED);

        when(studyPlanMapper.getStudyPlanByID(studyPlanID)).thenReturn(studyPlanBean);

        boolean result = checkPlanService.uncheckStudyPlan(studyPlanID);

        assertTrue(result);
        assertEquals(Constants.NOT_CHECKED, studyPlanBean.getStatus());
        verify(studyPlanMapper, times(1)).updateStudyPlan(studyPlanBean);
    }

    @Test
    public void testRefreshUsersAllPlan() {
        int userID = 1;
        List<PlanBean> plans = new ArrayList<>();
        PlanBean planBean1 = new PlanBean();
        planBean1.setPlanID(1);
        planBean1.setPlanType(Constants.PLAN_TYPE);
        planBean1.setStatus(Constants.NOT_CHECKED);

        PlanBean planBean2 = new PlanBean();
        planBean2.setPlanID(2);
        planBean2.setPlanType(Constants.FIT_PLAN_TYPE);
        planBean2.setStatus(Constants.NOT_CHECKED);

        plans.add(planBean1);
        plans.add(planBean2);

        when(planMapper.getAllPlanByUserID(userID)).thenReturn(plans);
        when(planMapper.getPlanByPlanID(1)).thenReturn(planBean1);
        when(planMapper.getPlanByPlanID(2)).thenReturn(planBean2);

        List<PlanBean> result = checkPlanService.refreshUsersAllPlan(userID);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(planMapper, times(1)).getAllPlanByUserID(userID);
    }

    @Test
    public void testRefreshPlanStatus() {
        int planID = 1;
        PlanBean planBean = new PlanBean();
        planBean.setPlanID(planID);
        planBean.setPlanType(Constants.PLAN_TYPE);
        planBean.setStatus(Constants.NOT_CHECKED);

        when(planMapper.getPlanByPlanID(planID)).thenReturn(planBean);

        // Mock the behavior of refreshAllDailyPlan method instead of the service method
        // Since refreshAllDailyPlan is a private method, we don't need to mock it directly
        List<DailyPlanBean> dailyPlanBeans = new ArrayList<>();
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setStatus(Constants.CHECKED);
        dailyPlanBeans.add(dailyPlanBean);

        when(dailyPlanMapper.getAllDailyPlanByPlanID(planID)).thenReturn(dailyPlanBeans);

        PlanBean result = checkPlanService.refreshPlanStatus(planID);

        assertNotNull(result);
        assertEquals(Constants.CHECKED, result.getStatus());
        verify(planMapper, times(1)).updatePlan(planBean);
    }

    @Test
    public void testCountSuccess() {
        int planID = 1;
        PlanBean planBean = new PlanBean();
        planBean.setPlanID(planID);
        planBean.setPlanType(Constants.PLAN_TYPE);

        List<DailyPlanBean> dailyPlans = new ArrayList<>();
        DailyPlanBean dailyPlan1 = new DailyPlanBean();
        dailyPlan1.setStatus(Constants.CHECKED);
        DailyPlanBean dailyPlan2 = new DailyPlanBean();
        dailyPlan2.setStatus(Constants.NOT_CHECKED);

        dailyPlans.add(dailyPlan1);
        dailyPlans.add(dailyPlan2);

        when(planMapper.getPlanByPlanID(planID)).thenReturn(planBean);
        when(dailyPlanMapper.getAllDailyPlanByPlanID(planID)).thenReturn(dailyPlans);

        int count = checkPlanService.countSuccess(planID);

        assertEquals(1, count);
    }

    @Test
    public void testCountFailed() {
        int planID = 1;
        PlanBean planBean = new PlanBean();
        planBean.setPlanID(planID);
        planBean.setPlanType(Constants.PLAN_TYPE);

        List<DailyPlanBean> dailyPlans = new ArrayList<>();
        DailyPlanBean dailyPlan1 = new DailyPlanBean();
        dailyPlan1.setStatus(Constants.FAILED);
        DailyPlanBean dailyPlan2 = new DailyPlanBean();
        dailyPlan2.setStatus(Constants.NOT_CHECKED);

        dailyPlans.add(dailyPlan1);
        dailyPlans.add(dailyPlan2);

        when(planMapper.getPlanByPlanID(planID)).thenReturn(planBean);
        when(dailyPlanMapper.getAllDailyPlanByPlanID(planID)).thenReturn(dailyPlans);

        int count = checkPlanService.countFailed(planID);

        assertEquals(1, count);
    }

    @Test
    public void testTotalCount() {
        int planID = 1;
        PlanBean planBean = new PlanBean();
        planBean.setPlanID(planID);
        planBean.setPlanType(Constants.PLAN_TYPE);

        List<DailyPlanBean> dailyPlans = new ArrayList<>();
        dailyPlans.add(new DailyPlanBean());
        dailyPlans.add(new DailyPlanBean());

        when(planMapper.getPlanByPlanID(planID)).thenReturn(planBean);
        when(dailyPlanMapper.getAllDailyPlanByPlanID(planID)).thenReturn(dailyPlans);

        int count = checkPlanService.totalCount(planID);

        assertEquals(2, count);
    }
}
