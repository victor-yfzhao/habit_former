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

import java.sql.Time;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PlanMapperTest {
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
    @Autowired
    private PlanMapper planMapper;

    @BeforeEach
    public void setUp() {
        planMapper.deleteAllDailyPlanByPlanID(1);
        planMapper.deleteAllPlanByUserID(1);
    }

    @Test
    public void testGetAllPlanBeanByUserID() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        planMapper.addPlanBean(planBean);

        List<PlanBean> plans = planMapper.getAllPlanBeanByUserID(1);
        assertNotNull(plans);
        assertFalse(plans.isEmpty());
        assertEquals(1, plans.size());
    }

    @Test
    public void testGetPlanBeanByPlanID() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        planMapper.addPlanBean(planBean);

        PlanBean retrievedPlan = planMapper.getPlanBeanByPlanID(planBean.getPlanID());
        assertNotNull(retrievedPlan);
        assertEquals(planBean.getPlanName(), retrievedPlan.getPlanName());
    }

    @Test
    public void testGetAllDailyPlanIDByPlanID() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        planMapper.addPlanBean(planBean);

        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDate(new Date(System.currentTimeMillis()));
        dailyPlanBean.setPlanDetail("Test Daily Plan");
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanID(planBean.getPlanID());
        planMapper.addDailyPlanBean(dailyPlanBean);

        List<DailyPlanBean> dailyPlans = planMapper.getAllDailyPlanIDByPlanID(planBean.getPlanID());
        assertNotNull(dailyPlans);
        assertFalse(dailyPlans.isEmpty());
        assertEquals(1, dailyPlans.size());
    }

    @Test
    public void testGetDailyPlanByID() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDate(new Date(System.currentTimeMillis()));
        dailyPlanBean.setPlanDetail("Test Daily Plan");
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanID(1);
        planMapper.addDailyPlanBean(dailyPlanBean);

        DailyPlanBean retrievedDailyPlan = planMapper.getDailyPlanByID(dailyPlanBean.getDailyPlanID());
        assertNotNull(retrievedDailyPlan);
        assertEquals(dailyPlanBean.getPlanDetail(), retrievedDailyPlan.getPlanDetail());
    }

    @Test
    public void testAddPlanBean() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));

        int result = planMapper.addPlanBean(planBean);
        assertTrue(result > 0);
        assertNotNull(planBean.getPlanID());
    }

    @Test
    public void testAddDailyPlanBean() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDate(new Date(System.currentTimeMillis()));
        dailyPlanBean.setPlanDetail("Test Daily Plan");
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanID(1);

        int result = planMapper.addDailyPlanBean(dailyPlanBean);
        assertTrue(result > 0);
        assertNotNull(dailyPlanBean.getDailyPlanID());
    }

    @Test
    public void testUpdatePlan() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        planMapper.addPlanBean(planBean);

        planBean.setPlanName("Updated Plan Name");
        planBean.setPlanInfo("Updated Plan Info");
        planBean.setStatus(Constants.CHECKED);

        int result = planMapper.updatePlan(planBean);
        assertTrue(result > 0);
    }

    @Test
    public void testUpdateDailyPlan() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDate(new Date(System.currentTimeMillis()));
        dailyPlanBean.setPlanDetail("Test Daily Plan");
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanID(1);
        planMapper.addDailyPlanBean(dailyPlanBean);

        dailyPlanBean.setPlanDetail("Updated Daily Plan Detail");
        dailyPlanBean.setStatus(Constants.CHECKED);

        int result = planMapper.updateDailyPlan(dailyPlanBean);
        assertTrue(result > 0);
    }

    @Test
    public void testDeleteDailyPlanByID() {
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDate(new Date(System.currentTimeMillis()));
        dailyPlanBean.setPlanDetail("Test Daily Plan");
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanID(1);
        planMapper.addDailyPlanBean(dailyPlanBean);

        int result = planMapper.deleteDailyPlanByID(dailyPlanBean.getDailyPlanID());
        assertTrue(result > 0);
    }

    @Test
    public void testDeleteAllDailyPlanByPlanID() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        planMapper.addPlanBean(planBean);

        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDate(new Date(System.currentTimeMillis()));
        dailyPlanBean.setPlanDetail("Test Daily Plan");
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanID(planBean.getPlanID());
        planMapper.addDailyPlanBean(dailyPlanBean);

        int result = planMapper.deleteAllDailyPlanByPlanID(planBean.getPlanID());
        assertTrue(result > 0);
    }

    @Test
    public void testDeletePlanByID() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        planMapper.addPlanBean(planBean);

        int result = planMapper.deletePlanByID(planBean.getPlanID());
        assertTrue(result > 0);
    }

    @Test
    public void testDeleteAllPlanByUserID() {
        PlanBean planBean = new PlanBean();
        planBean.setPlanName("Test Plan");
        planBean.setPlanInfo("Test Info");
        planBean.setStatus(Constants.NOT_CHECKED);
        planBean.setUserID(1);
        planBean.setPlanDate(new Date(System.currentTimeMillis()));
        planBean.setPlanTime(new Time(System.currentTimeMillis()));
        planMapper.addPlanBean(planBean);

        int result = planMapper.deleteAllPlanByUserID(1);
        assertTrue(result > 0);
    }

}
