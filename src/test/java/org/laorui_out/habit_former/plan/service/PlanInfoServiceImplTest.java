package org.laorui_out.habit_former.plan.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.DailyPlanMapper;
import org.laorui_out.habit_former.mapper.FitPlanMapper;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.mapper.StudyPlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.plan.utils.Plan4EachDay;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
//import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanInfoServiceImplTest {

    @Mock
    private PlanMapper planMapper;

    @Mock
    private DailyPlanMapper dailyPlanMapper;

    @Mock
    private FitPlanMapper fitPlanMapper;

    @Mock
    private StudyPlanMapper studyPlanMapper;

    @InjectMocks
    private PlanInfoServiceImpl planInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPlanInfo() {
        int userID = 1;
        List<PlanBean> expectedPlans = Arrays.asList(new PlanBean(), new PlanBean());
        when(planMapper.getAllPlanByUserID(userID)).thenReturn(expectedPlans);

        List<PlanBean> actualPlans = planInfoService.getAllPlanInfo(userID);

        assertEquals(expectedPlans, actualPlans);
        verify(planMapper, times(1)).getAllPlanByUserID(userID);
    }

    @Test
    void testGetPlanInMonth() {
        int userID = 1;
        int year = 2023;
        int month = 7;

        PlanBean fitPlan = new PlanBean();
        fitPlan.setPlanType(Constants.FIT_PLAN_TYPE);
        fitPlan.setPlanID(1);

        PlanBean dailyPlan = new PlanBean();
        dailyPlan.setPlanType(Constants.PLAN_TYPE);
        dailyPlan.setPlanID(2);

        PlanBean studyPlan = new PlanBean();
        studyPlan.setPlanType(Constants.STUDY_PLAN_TYPE);
        studyPlan.setPlanID(3);

        List<PlanBean> planList = Arrays.asList(fitPlan, dailyPlan, studyPlan);
        when(planMapper.getAllPlanByUserID(userID)).thenReturn(planList);

        Date startDate = new GregorianCalendar(year, month - 1, 1).getTime();
        Date endDate = new GregorianCalendar(year, month, 1).getTime();

        when(fitPlanMapper.getFitPlanByDate(anyInt(), any(Date.class))).thenReturn(Collections.emptyList());
        when(dailyPlanMapper.getDailyPlanByDate(any(Date.class), anyInt())).thenReturn(Collections.emptyList());
        //when(studyPlanMapper.getStudyPlanByDate(anyInt(), any(Date.class))).thenReturn(Collections.emptyList());

        List<Plan4EachDay> result = planInfoService.getPlanInMonth(userID, year, month);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(planMapper, times(1)).getAllPlanByUserID(userID);
        verify(fitPlanMapper, atLeastOnce()).getFitPlanByDate(anyInt(), any(Date.class));
        verify(dailyPlanMapper, atLeastOnce()).getDailyPlanByDate(any(Date.class), anyInt());
        //verify(studyPlanMapper, atLeastOnce()).getStudyPlanByDate(anyInt(), any(Date.class));
    }

    // You can add more test cases to cover different scenarios, such as non-empty results from the mappers, different plan types, etc.
}
