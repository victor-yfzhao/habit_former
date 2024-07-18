package org.laorui_out.habit_former.plan.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.mapper.DailyPlanMapper;
import org.laorui_out.habit_former.mapper.FitPlanMapper;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.mapper.StudyPlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.plan.utils.PlanDetailMessage;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanDetailServiceImplTest {

    @Mock
    private PlanMapper planMapper;

    @Mock
    private DailyPlanMapper dailyPlanMapper;

    @Mock
    private FitPlanMapper fitPlanMapper;

    @Mock
    private StudyPlanMapper studyPlanMapper;

    @InjectMocks
    private PlanDetailServiceImpl planDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFitPlanDetail() {
        int planID = 1;
        Date date = new Date(System.currentTimeMillis());
        PlanBean plan = new PlanBean();
        plan.setPlanID(planID);
        plan.setPlanType(Constants.FIT_PLAN_TYPE);

        when(planMapper.selectById(planID)).thenReturn(plan);
        List<FitPlanBean> fitPlans = Arrays.asList(new FitPlanBean(), new FitPlanBean());
        when(fitPlanMapper.getFitPlanByDate(planID, date)).thenReturn(fitPlans);

        PlanDetailMessage<FitPlanBean> result = planDetailService.getFitPlanDetail(planID, new java.sql.Date(date.getTime()));

        assertNotNull(result);
        assertEquals(planID, result.getPlanID());
        assertEquals(date, result.getDate());
        assertEquals(fitPlans, result.getPlanItems());

        verify(planMapper, times(1)).selectById(planID);
        verify(fitPlanMapper, times(1)).getFitPlanByDate(planID, date);
    }

    @Test
    void testGetStudyPlanDetail() {
        int planID = 2;
        Date date = new Date(System.currentTimeMillis());
        PlanBean plan = new PlanBean();
        plan.setPlanID(planID);
        plan.setPlanType(Constants.STUDY_PLAN_TYPE);

        when(planMapper.selectById(planID)).thenReturn(plan);
        List<StudyPlanBean> studyPlans = Arrays.asList(new StudyPlanBean(), new StudyPlanBean());
        when(studyPlanMapper.getStudyPlanByDate(planID, new java.sql.Date(date.getTime()))).thenReturn(studyPlans);

        PlanDetailMessage<StudyPlanBean> result = planDetailService.getStudyPlanDetail(planID, new java.sql.Date(date.getTime()));

        assertNotNull(result);
        assertEquals(planID, result.getPlanID());
        assertEquals(date, result.getDate());
        assertEquals(studyPlans, result.getPlanItems());

        verify(planMapper, times(1)).selectById(planID);
        verify(studyPlanMapper, times(1)).getStudyPlanByDate(planID, new java.sql.Date(date.getTime()));
    }

    @Test
    void testGetDailyPlanDetail() {
        int planID = 3;
        Date date = new Date(System.currentTimeMillis());
        java.sql.Date sql_date=new java.sql.Date(date.getTime());
        PlanBean plan = new PlanBean();
        plan.setPlanID(planID);
        plan.setPlanType(Constants.PLAN_TYPE);

        when(planMapper.selectById(planID)).thenReturn(plan);
        DailyPlanBean dailyPlanBean1=new DailyPlanBean();DailyPlanBean dailyPlanBean2=new DailyPlanBean();
        List<DailyPlanBean> dailyPlans = Arrays.asList(dailyPlanBean1, dailyPlanBean2);
        when(dailyPlanMapper.getDailyPlanByDate(sql_date, planID)).thenReturn(dailyPlans);

        PlanDetailMessage<DailyPlanBean> result = planDetailService.getDailyPlanDetail(planID, sql_date);

        assertNotNull(result);
        assertEquals(planID, result.getPlanID());
        assertEquals(date, result.getDate());
        assertEquals(dailyPlans, result.getPlanItems());

        verify(planMapper, times(1)).selectById(planID);
        verify(dailyPlanMapper, times(1)).getDailyPlanByDate(date, planID);
    }

    @Test
    void testGetFitPlanDetailWithWrongType() {
        int planID = 4;
        Date date = new Date(System.currentTimeMillis());
        PlanBean plan = new PlanBean();
        plan.setPlanID(planID);
        plan.setPlanType("WRONG_TYPE");

        when(planMapper.selectById(planID)).thenReturn(plan);

        PlanDetailMessage<FitPlanBean> result = planDetailService.getFitPlanDetail(planID, new java.sql.Date(date.getTime()));

        assertNull(result);
        verify(planMapper, times(1)).selectById(planID);
        verify(fitPlanMapper, times(0)).getFitPlanByDate(anyInt(), any(Date.class));
    }

    // Similarly, you can add tests for wrong types in getStudyPlanDetail and getDailyPlanDetail methods
}
