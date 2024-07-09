package org.laorui_out.habit_former.plan.mapper;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.mapper.FitPlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@MapperScan("com.example.mapper") // 请根据实际包路径修改
@Transactional
public class FitPlanMapperTest {

    @Autowired
    private FitPlanMapper fitPlanMapper;

    @Test
    public void testGetFitPlanByID() {
        FitPlanBean plan = new FitPlanBean();
        Date date = new Date(System.currentTimeMillis());
        plan.setDate(date);
        plan.setFitItemName("itemname");
        plan.setStatus(Constants.NOT_CHECKED);
        plan.setPlanID(1);
        plan.setFitType("Cardio");
        plan.setGroupNum(3);
        plan.setNumPerGroup(15);
        plan.setTimePerGroup(30);
        fitPlanMapper.insert(plan);
        int fitPlanID = plan.getFitPlanItemID();
        FitPlanBean test = fitPlanMapper.getFitPlanByID(fitPlanID);
        assertNotNull(test);
        assertEquals("itemname",test.getFitItemName());
    }

    @Test
    public void testGetFitPlanByDate() {
        int planID = 1;
        FitPlanBean plan = new FitPlanBean();
        Date date = new Date(System.currentTimeMillis());
        plan.setDate(date);
        plan.setFitItemName("itemname");
        plan.setStatus(Constants.NOT_CHECKED);
        plan.setPlanID(planID);
        plan.setFitType("Cardio");
        plan.setGroupNum(3);
        plan.setNumPerGroup(15);
        plan.setTimePerGroup(30);
        fitPlanMapper.insert(plan);
        //int fitPlanID = plan.getPlanID();
        List<FitPlanBean> plans = fitPlanMapper.getFitPlanByDate(planID, date);
        assertNotNull(plans);
        assertEquals(1,plans.size());
    }

    @Test
    public void testGetFitPlanByPlanID() {
        int planID = 1;
        FitPlanBean plan = new FitPlanBean();
        Date date = new Date(System.currentTimeMillis());
        plan.setDate(date);
        plan.setFitItemName("itemname");
        plan.setStatus(Constants.NOT_CHECKED);
        plan.setPlanID(planID);
        plan.setFitType("Cardio");
        plan.setGroupNum(3);
        plan.setNumPerGroup(15);
        plan.setTimePerGroup(30);
        fitPlanMapper.insert(plan);
        List<FitPlanBean> plans = fitPlanMapper.getFitPlanByPlanID(planID);
        assertNotNull(plans);
        assertFalse(plans.isEmpty());
    }

    @Test
    public void testDeleteFitPlan() {
        int planID = 1;
        FitPlanBean plan = new FitPlanBean();
        Date date = new Date(System.currentTimeMillis());
        plan.setDate(date);
        plan.setFitItemName("itemname");
        plan.setStatus(Constants.NOT_CHECKED);
        plan.setPlanID(planID);
        plan.setFitType("Cardio");
        plan.setGroupNum(3);
        plan.setNumPerGroup(15);
        plan.setTimePerGroup(30);
        fitPlanMapper.insert(plan);
        int fitPlanID=plan.getFitPlanItemID();
        int result = fitPlanMapper.deleteFitPlan(fitPlanID);
        assertEquals(1, result);
    }

    @Test
    public void testUpdateFitPlan() {
        int planID = 1;
        FitPlanBean plan = new FitPlanBean();
        Date date = new Date(System.currentTimeMillis());
        plan.setDate(date);
        plan.setFitItemName("itemname");
        plan.setStatus(Constants.NOT_CHECKED);
        plan.setPlanID(planID);
        plan.setFitType("Cardio");
        plan.setGroupNum(3);
        plan.setNumPerGroup(15);
        plan.setTimePerGroup(30);
        fitPlanMapper.insert(plan);
        plan.setStatus(Constants.CHECKED);
        int result = fitPlanMapper.updateFitPlan(plan);
        assertEquals(1, result);
    }

    @Test
    public void testUpdateFitPlanStatus() {
        int planID = 1;
        FitPlanBean plan = new FitPlanBean();
        Date date = new Date(System.currentTimeMillis());
        plan.setDate(date);
        plan.setFitItemName("itemname");
        plan.setStatus(Constants.NOT_CHECKED);
        plan.setPlanID(planID);
        plan.setFitType("Cardio");
        plan.setGroupNum(3);
        plan.setNumPerGroup(15);
        plan.setTimePerGroup(30);
        fitPlanMapper.insert(plan);
        plan.setStatus(Constants.CHECKED);
        int result = fitPlanMapper.updateFitPlanStatus(plan);
        assertEquals(1, result);
    }
}

