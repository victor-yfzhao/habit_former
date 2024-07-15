package org.laorui_out.habit_former.admin.service;

import java.util.Map;

public interface DashboardService {
    //--平台内每日计划完成总数目
    int[] countDailyFinishedPlanItem();

    //--平台内每日完成了计划的用户数
    int[] countDailyFinishedUser();

    //--平台内每日帖子增加量
    int[] countDailyAddedPost();

    //--每日计划完成的比例(按计划类型分类)
    Map<String, Integer> countDailyFinishedPlanItemByType();

    //--帖子点赞量排名
    Map<Integer, Integer> countPostLikeRanking();

    //--帖子收藏量排名
    Map<Integer, Integer> countPostCollectRanking();
}
