package org.laorui_out.habit_former.mapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.PosterBean;

import java.util.List;


@Mapper
public interface PosterMapper {

    //根据帖子ID删除poster
    @Delete("delete from poster where posterID = #{posterID}")
    public boolean deletePosterByPosterId(@Param("posterID") int posterID);

    //根据帖子ID返回poster
    @Select("select posterID, posterHeadline, posterDetail from poster where posterID = #{posterID}")
    PosterBean getPosterById(int posterID);

    //根据帖子ID返回帖子所有图片
    @Select("select posterPicture from posterpicture where posterID = #{posterID}")
    List<String> getPosterPicturesByPosterId(int posterID); // 返回图片URL列表


    @Select("select * from poster")
    List<PosterBean> getAll();



//    @Update()
//
//
//    @Select()


}
