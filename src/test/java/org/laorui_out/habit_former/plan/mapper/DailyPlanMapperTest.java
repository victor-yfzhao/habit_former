package org.laorui_out.habit_former.plan.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.mapper.DailyPlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class DailyPlanMapperTest {

    @Autowired
    private DailyPlanMapper dailyPlanMapper;

    @Test
    public void testGetAllDailyPlanByPlanID() {
        DailyPlanBean dailyPlanBean=new DailyPlanBean();
        int planID=1;
        Date date=new Date(System.currentTimeMillis());
        dailyPlanBean.setPlanID(planID);
        dailyPlanBean.setDate(date);
        dailyPlanMapper.addDailyPlan(dailyPlanBean);
        List<DailyPlanBean> plans = dailyPlanMapper.getAllDailyPlanByPlanID(planID);
        assertNotNull(plans);
        assertFalse(plans.isEmpty());
    }

    @Test
    public void testGetDailyPlanByID() {
        DailyPlanBean dailyPlanBean=new DailyPlanBean();
        int planID=1;
        Date date=new Date(System.currentTimeMillis());
        dailyPlanBean.setPlanID(planID);
        dailyPlanBean.setDate(date);
        dailyPlanMapper.addDailyPlan(dailyPlanBean);
        int dailyPlanID = dailyPlanBean.getDailyPlanID();
        DailyPlanBean plan = dailyPlanMapper.getDailyPlanByID(dailyPlanID);
        assertNotNull(plan);
    }

    @Test
    public void testGetDailyPlanByDate() {
        Date date = new Date(System.currentTimeMillis());
        int planID = 1;
        List<DailyPlanBean> plans = dailyPlanMapper.getDailyPlanByDate(date, planID);
        assertNotNull(plans);
    }

    @Test
    public void testAddDailyPlan() {
        DailyPlanBean plan = new DailyPlanBean();
        plan.setDate(new Date(System.currentTimeMillis()));
        plan.setPlanDetail("Test plan");
        plan.setPlanID(1);

        int result = dailyPlanMapper.addDailyPlan(plan);
        assertEquals(1, result);
        assertNotNull(plan.getDailyPlanID());
    }

    @Test
    public void testUpdateDailyPlan() {DailyPlanBean dailyPlanBean=new DailyPlanBean();
        int planID=1;
        Date date=new Date(System.currentTimeMillis());
        dailyPlanBean.setPlanID(planID);
        dailyPlanBean.setDate(date);
        dailyPlanMapper.addDailyPlan(dailyPlanBean);
        dailyPlanBean.setPlanDetail("Updated plan detail");
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);

        int result = dailyPlanMapper.updateDailyPlan(dailyPlanBean);
        assertEquals(1, result);
    }

    @Test
    public void testDeleteDailyPlanByID() {
        DailyPlanBean dailyPlanBean=new DailyPlanBean();
        int planID=1;
        Date date=new Date(System.currentTimeMillis());
        dailyPlanBean.setPlanID(planID);
        dailyPlanBean.setDate(date);
        dailyPlanMapper.addDailyPlan(dailyPlanBean);
        int dailyPlanID = dailyPlanBean.getDailyPlanID();
        int result = dailyPlanMapper.deleteDailyPlanByID(dailyPlanID);
        assertEquals(1, result);
    }

    @Test
    public void testDeleteAllDailyPlanByPlanID() {
        DailyPlanBean dailyPlanBean1=new DailyPlanBean();DailyPlanBean dailyPlanBean2=new DailyPlanBean();
        int planID=1;
        Date date=new Date(System.currentTimeMillis());
        dailyPlanBean1.setPlanID(planID);
        dailyPlanBean1.setDate(date);
        dailyPlanBean2.setPlanID(planID);
        dailyPlanBean2.setDate(date);
        dailyPlanMapper.addDailyPlan(dailyPlanBean1);
        dailyPlanMapper.addDailyPlan(dailyPlanBean2);
        int result = dailyPlanMapper.deleteAllDailyPlanByPlanID(planID);
        assertEquals(2, result);
    }
}
