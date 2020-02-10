package com.dck.community.mapper;

import com.dck.community.model.Question;
import com.dck.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);
    @Select("select * from question LIMIT #{page},#{size}")
    List<Question> questionlist(Integer page, Integer size);
    @Select("select count(1) from question")
    Integer count();
}
