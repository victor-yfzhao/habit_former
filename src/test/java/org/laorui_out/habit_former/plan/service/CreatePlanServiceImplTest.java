package org.laorui_out.habit_former.plan.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CreatePlanServiceImplTest {

    @Mock
    private PlanMapper planMapper;

    @Mock
    private DailyPlanMapper dailyPlanMapper;

    @Mock
    private FitPlanMapper fitPlanMapper;

    @Mock
    private StudyPlanMapper studyPlanMapper;

    @InjectMocks
    private CreatePlanServiceImpl createPlanService;

    @BeforeEach
    public void setUp() {
        // 初始化 CreatePlanServiceImpl 对象，并注入模拟的依赖
    }

    @Test
    public void testAddPlan() {
        String planInfo = "Plan Info";
        String planName = "Plan Name";
        String planType = Constants.PLAN_TYPE;
        int userID = 1;

        PlanBean planBean = createPlanService.addPlan(planInfo, planName, planType, userID);

        assertNotNull(planBean);
        assertEquals(planInfo, planBean.getPlanInfo());
        assertEquals(planName, planBean.getPlanName());
        assertEquals(planType, planBean.getPlanType());
        assertEquals(userID, planBean.getUserID());

        verify(planMapper, times(1)).addPlan(planBean);
    }

    @Test
    public void testAddPlanWithInvalidType() {
        String planInfo = "Plan Info";
        String planName = "Plan Name";
        String planType = "Invalid Type";
        int userID = 1;

        PlanBean planBean = createPlanService.addPlan(planInfo, planName, planType, userID);

        assertNull(planBean);
        verify(planMapper, times(0)).addPlan(any(PlanBean.class));
    }

    @Test
    public void testAddDailyPlan() {
        String planDetail = "Daily Plan Detail";
        int planID = 1;

        DailyPlanBean dailyPlanBean = createPlanService.addDailyPlan(planDetail, planID);

        assertNotNull(dailyPlanBean);
        assertEquals(planDetail, dailyPlanBean.getPlanDetail());
        assertEquals(planID, dailyPlanBean.getPlanID());

        verify(dailyPlanMapper, times(1)).addDailyPlan(dailyPlanBean);
    }

    @Test
    public void testAddFitPlan() {
        String fitItemName = "Fit Item Name";
        String fitType = "Cardio";
        int groupNum = 3;
        int numPerGroup = 15;
        int timePerGroup = 30;
        int planID = 1;

        FitPlanBean fitPlanBean = createPlanService.addFitPlan(fitItemName, fitType, groupNum, numPerGroup, timePerGroup, planID);

        assertNotNull(fitPlanBean);
        assertEquals(fitItemName, fitPlanBean.getFitItemName());
        assertEquals(fitType, fitPlanBean.getFitType());
        assertEquals(groupNum, fitPlanBean.getGroupNum());
        assertEquals(numPerGroup, fitPlanBean.getNumPerGroup());
        assertEquals(timePerGroup, fitPlanBean.getTimePerGroup());
        assertEquals(planID, fitPlanBean.getPlanID());
        assertEquals(Constants.NOT_CHECKED, fitPlanBean.getStatus());

        verify(fitPlanMapper, times(1)).insert(fitPlanBean);
    }

    @Test
    public void testAddStudyPlan() {
        String studySubject = "Study Subject";
        String studyContent = "Study Content";
        int studyTime = 120;
        int planID = 1;

        StudyPlanBean studyPlanBean = createPlanService.addStudyPlan(studySubject, studyContent, studyTime, planID);

        assertNotNull(studyPlanBean);
        assertEquals(studySubject, studyPlanBean.getStudySubject());
        assertEquals(studyContent, studyPlanBean.getStudyContent());
        assertEquals(studyTime, studyPlanBean.getStudyTime());
        assertEquals(planID, studyPlanBean.getPlanID());
        assertEquals(Constants.NOT_CHECKED, studyPlanBean.getStatus());

        verify(studyPlanMapper, times(1)).insert(studyPlanBean);
    }
}
