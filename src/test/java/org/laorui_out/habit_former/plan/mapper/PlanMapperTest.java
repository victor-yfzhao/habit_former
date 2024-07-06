package org.laorui_out.habit_former.plan.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PlanMapperTest {
    @Autowired
    private PlanMapper planMapper;

    /*
    @BeforeEach
    public void setUp() {// 清理测试数据
        List<Integer> plan1 = planMapper.getAllDailyPlanIDByPlanID(1);
        List<Integer> plan2 = planMapper.getAllDailyPlanIDByPlanID(2);
        planMapper.dropUPbyPlanID(1);
        planMapper.dropUPbyPlanID(2);
        planMapper.dropPDByPlanID(1);
        planMapper.dropPDByPlanID(2);
        for (int i : plan1
        ) {
            planMapper.dropDailyPlan(i);
        }
        for (int i : plan2
        ) {
            planMapper.dropDailyPlan(i);
        }
        planMapper.dropPlan(1);
        planMapper.dropPlan(2);
    }

    @Test
    public void testAddPlanBean() {
        PlanBean plan = new PlanBean();
        plan.setPlanName("Test Plan");
        plan.setPlanInfo("Test Plan Info");
        int result = planMapper.addPlanBean(plan);
        assertTrue(result > 0);
        assertNotNull(plan.getPlanID());
    }

    //    @Test
//    public void testAddPlan2User() {
//        PlanBean plan = new PlanBean();
//        plan.setPlanName("Test Plan");
//        plan.setPlanInfo("Test Plan Info");
//        planMapper.addPlanBean(plan);
//        int planID=1,userID=1;
//        int result = planMapper.addPlan2User(planID,userID);
//        assertTrue(result > 0);
//    }
//      TODO:需要USERMAPPER加外键的暂时不测试

    @Test
    public void testAddDailyPlanBean() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanDetail("Test DailyPlan Detail");
        dailyPlanBean.setDate(new Date());
        int result = planMapper.addDailyPlanBean(dailyPlanBean);
        assertTrue(result > 0);
        assertNotNull(dailyPlanBean.getDailyPlanID());
    }

    @Test
    public void testAddDailyPlan2Plan(){
        PlanBean plan = new PlanBean();
        plan.setPlanName("Test Plan");
        plan.setPlanInfo("Test Plan Info");
        planMapper.addPlanBean(plan);
        int planID=plan.getPlanID();
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanDetail("Test DailyPlan Detail");
        dailyPlanBean.setDate(new Date());
        planMapper.addDailyPlanBean(dailyPlanBean);
        int dailyPlanID=dailyPlanBean.getDailyPlanID();
        int result = planMapper.addDailyPlan2Plan(planID,dailyPlanID);
        assertTrue(result > 0);
    }

    @Test
    public void testUpdatePlanName(){
        PlanBean plan = new PlanBean();
        plan.setPlanName("Test Plan");
        plan.setPlanInfo("Test Plan Info");
        planMapper.addPlanBean(plan);
        int planID=plan.getPlanID();
        String test ="updated_name";
        plan.setPlanName(test);
        int result=planMapper.updatePlanName(plan);
        PlanBean planBean_test=planMapper.getPlanBeanByID(planID);
        assertTrue(result > 0);
        assertEquals(planBean_test.getPlanName(), test);
    }

    @Test
    public void testUpdatePlanInfo(){
        PlanBean plan = new PlanBean();
        plan.setPlanName("Test Plan");
        plan.setPlanInfo("Test Plan Info");
        planMapper.addPlanBean(plan);
        int planID=plan.getPlanID();
        String test ="updated_info";
        plan.setPlanInfo(test);
        int result=planMapper.updatePlanInfo(plan);
        PlanBean planBean_test=planMapper.getPlanBeanByID(planID);
        assertTrue(result > 0);
        assertEquals(planBean_test.getPlanInfo(), test);
    }
    @Test
    public void testUpdatePlanStatus(){
        PlanBean plan = new PlanBean();
        plan.setPlanName("Test Plan");
        plan.setPlanInfo("Test Plan Info");
        planMapper.addPlanBean(plan);//新插入时默认状态为未打卡
        int planID=plan.getPlanID();
        plan.setStatus(Constants.CHECKED);
        int result=planMapper.updatePlanStatus(plan);
        PlanBean planBean_test=planMapper.getPlanBeanByID(planID);
        assertTrue(result > 0);
        assertEquals(Constants.CHECKED, planBean_test.getStatus());
    }
    @Test
    public void testUpdateDailyPlanStatus(){
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanDetail("Test DailyPlan Detail");
        dailyPlanBean.setDate(new Date());
        planMapper.addDailyPlanBean(dailyPlanBean);//新插入时默认状态为未打卡
        int dailyPlanID=dailyPlanBean.getDailyPlanID();
        dailyPlanBean.setStatus(Constants.CHECKED);
        int result=planMapper.updateDailyPlanStatus(dailyPlanBean);
        DailyPlanBean dailyplanBean_test=planMapper.getDailyPlanByID(dailyPlanID);
        assertTrue(result > 0);
        assertEquals(Constants.CHECKED, dailyplanBean_test.getStatus());
    }
    @Test
    public void testUpdateDailyPlanDetail(){
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanDetail("Test DailyPlan Detail");
        dailyPlanBean.setDate(new Date());
        planMapper.addDailyPlanBean(dailyPlanBean);//新插入时默认状态为未打卡
        int dailyPlanID=dailyPlanBean.getDailyPlanID();
        String test="updated_detail";
        dailyPlanBean.setPlanDetail(test);
        int result=planMapper.updateDailyPlanDetail(dailyPlanBean);
        DailyPlanBean dailyplanBean_test=planMapper.getDailyPlanByID(dailyPlanID);
        assertTrue(result > 0);
        assertEquals(dailyplanBean_test.getPlanDetail(), test);
    }

    @Test
    public void testDropPlanBean() {
        PlanBean plan = new PlanBean();
        plan.setPlanName("Test Plan");
        plan.setPlanInfo("Test Plan Info");
        planMapper.addPlanBean(plan);
        int planID= plan.getPlanID();
        int result=planMapper.dropPlan(planID);
        assertTrue(result > 0);
        assertNull(planMapper.getPlanBeanByID(planID));
    }

    @Test
    public void testDropDailyPlanBean() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanDetail("Test DailyPlan Detail");
        dailyPlanBean.setDate(new Date());
        planMapper.addDailyPlanBean(dailyPlanBean);
        int dailyplan_id=dailyPlanBean.getDailyPlanID();
        int result = planMapper.dropDailyPlan(dailyplan_id);
        assertTrue(result > 0);
        assertNull(planMapper.getDailyPlanByID(dailyplan_id));
    }*/



}
