package com.dck.community.service;

import com.dck.community.dto.PageDTO;
import com.dck.community.dto.QuestionDTO;
import com.dck.community.mapper.QuestionMapper;
import com.dck.community.mapper.UserMapper;
import com.dck.community.model.Question;
import com.dck.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageDTO questionlist(Integer page, Integer size) {
        page=size*(page-1);
        List<Question> questionList=questionMapper.questionlist(page,size);
        List<QuestionDTO> questionListDTOlist=new ArrayList<>();
        PageDTO pageDTO=new PageDTO();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
      //      System.out.println("user测试"+user);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionListDTOlist.add(questionDTO);

        }
        pageDTO.setQuestions(questionListDTOlist);
        Integer totalCount = questionMapper.count();
        pageDTO.setPagination(totalCount,page,size);
        return pageDTO;
    }
}
