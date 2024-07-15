package org.laorui_out.habit_former.admin.service;

import org.laorui_out.habit_former.admin.utils.CollectsRank;
import org.laorui_out.habit_former.admin.utils.LikesRank;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    //--平台内每日计划完成总数目
    Map<String, Integer> countDailyFinishedPlanItem();

    //--平台内每日完成了计划的用户数
    Map<String, Integer> countDailyFinishedUser();

    //--平台内每日帖子增加量
    Map<String, Integer> countDailyAddedPost();

    //--每日计划完成的比例(按计划类型分类)
    Map<String, Integer> countDailyFinishedPlanItemByType();

    //--帖子点赞量排名
    List<LikesRank> countPostLikeRanking();

    //--帖子收藏量排名
    List<CollectsRank> countPostCollectRanking();
}
