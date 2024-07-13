package org.laorui_out.habit_former.admin.service;

import java.util.Map;

public class DashboardServiceImpl implements DashboardService {
    @Override
    public int countDailyFinishedPlanItem() {

    }

    @Override
    public int countDailyFinishedUser() {
        return 0;
    }

    @Override
    public int countDailyAddedPost() {
        return 0;
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
