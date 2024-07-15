package org.laorui_out.habit_former.admin.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.admin.utils.CollectsRank;
import org.laorui_out.habit_former.admin.utils.LikesRank;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.Date;

import static org.laorui_out.habit_former.plan.constant.Constants.sdf;

@Service
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
        for (int i = 6; i >= 0; i--) {
            int count = 0;
            Date tmp = new Date(date.getTime() - 24L * 60 * 60 * 1000 * i);

            count += fitPlanMapper.countDailyFinishedPlan(tmp);
            count += studyPlanMapper.countDailyFinishedPlan(tmp);
            count += dailyPlanMapper.countDailyFinishedPlan(tmp);
            System.out.println(tmp);
            System.out.println(count);
            res.put(sdf.format(tmp), count);
        }
        return res;
    }

    @Override
    public Map<String, Integer> countDailyFinishedUser() {
        Set<Integer> finishedUsers = new HashSet<>();
        Map<String, Integer> res = new HashMap<>();
        Date date = new Date(System.currentTimeMillis());
        List<DailyPlanBean> dpFinished;
        List<FitPlanBean> fpFinished;
        List<StudyPlanBean> spFinished;
        for (int i = 6; i >= 0; i--) {
            Date tmp = new Date(date.getTime() - 24L * 60 * 60 * 1000 * i);
            dpFinished = dailyPlanMapper.getDailyFinishedPlan(tmp);
            fpFinished = fitPlanMapper.getDailyFinishedPlan(tmp);
            spFinished = studyPlanMapper.getDailyFinishedPlan(tmp);
            for (DailyPlanBean item:dpFinished) {
                finishedUsers.add(planMapper.getPlanByPlanID(item.getPlanID()).getUserID());
            }
            for (FitPlanBean item:fpFinished) {
                finishedUsers.add(planMapper.getPlanByPlanID(item.getPlanID()).getUserID());
            }
            for (StudyPlanBean item:spFinished) {
                finishedUsers.add(planMapper.getPlanByPlanID(item.getPlanID()).getUserID());
            }
            res.put(sdf.format(tmp), finishedUsers.size());
            finishedUsers.clear();
        }
        return res;
    }

    @Override
    public Map<String, Integer> countDailyAddedPost() {
        Date date = new Date(System.currentTimeMillis());
        Map<String, Integer> res = new HashMap<>();
        for (int i = 6; i >= 0; i--) {
            Date tmp = new Date(date.getTime() - 24L * 60 * 60 * 1000 * i);

            int count = posterMapper.countDailyAddedPoster(tmp);
            System.out.println(tmp);
            System.out.println(count);
            res.put(sdf.format(tmp), count);
        }
        return res;
    }

    @Override
    public Map<String, Integer> countDailyFinishedPlanItemByType() {
        Date date = new Date(System.currentTimeMillis());
        Map<String, Integer> res = new HashMap<>();
        //Date tmp= new Date(date.getTime());

        int count = dailyPlanMapper.countDailyFinishedPlan(date);
        res.put("dailyPlan", count);
        count = fitPlanMapper.countDailyFinishedPlan(date);
        res.put("fitPlan", count);
        count = studyPlanMapper.countDailyFinishedPlan(date);
        res.put("studyPlan", count);
        return res;
    }

    //TODO:限定排名的日期？
    @Override
    public List<LikesRank> countPostLikeRanking(int rankSize) {
        //int rankSize=10;
        List<LikesRank> likesRank;//=new LinkedHashMap<>();
        likesRank=posterMapper.getLikesRank(rankSize);
        return likesRank;
    }

    @Override
    public List<CollectsRank> countPostCollectRanking(int rankSize) {
        //int rankSize=10;
        List<CollectsRank> collectRank;//=new LinkedHashMap<>();
        collectRank=posterMapper.getCollectRank(rankSize);
        return collectRank;
    }
}
