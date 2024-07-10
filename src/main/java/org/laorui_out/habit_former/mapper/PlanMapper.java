package org.laorui_out.habit_former.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PlanMapper {







    @Select("select planID from plan")
    List<Integer> getAllPlanID();
}
