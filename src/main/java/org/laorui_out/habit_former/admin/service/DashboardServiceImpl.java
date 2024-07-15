package org.laorui_out.habit_former.admin.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.mapper.*;

import java.util.HashMap;
import java.util.Map;
import java.sql.Date;

import static org.laorui_out.habit_former.plan.constant.Constants.sdf;

public class DashboardServiceImpl implements DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PosterMapper posterMapper;

    @Resource
    private PlanMapper planMapper;

    @Resource
    private DailyPlanMapper dailyPlanMapper;

    @Resource
    private FitPlanMapper fitPlanMapper;

    @Resource
    private StudyPlanMapper studyPlanMapper;

    @Override
    public Map<String, Integer> countDailyFinishedPlanItem() {
        Date date = new Date(System.currentTimeMillis());
        Map<String, Integer> res = new HashMap<>();
        for(int i = 6; i >= 0; i--){
            int count = 0;
            Date tmp= new Date(date.getTime() - 24L * 60 * 60 * 1000 * i);

            count += fitPlanMapper.countDailyFinishedPlan(tmp);
            count += studyPlanMapper.countDailyFinishedPlan(tmp);
            count += dailyPlanMapper.countDailyFinishedPlan(tmp);

            res.put(sdf.format(tmp), count);
        }
        return res;
    }

    @Override
    public Map<String, Integer> countDailyFinishedUser() {
        return null;
    }

    @Override
    public Map<String, Integer> countDailyAddedPost() {
        return null;
    }

    @Override
    public Map<String, Integer> countDailyFinishedPlanItemByType() {
        return Map.of();
    }

    @Override
    public Map<Integer, Integer> countPostLikeRanking() {
        return Map.of();
    }

    @Override
    public Map<Integer, Integer> countPostCollectRanking() {
        return Map.of();
    }
}
