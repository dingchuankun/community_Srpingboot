package com.dck.community.dto;

import lombok.Data;

import java.util.List;
@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        Integer totalPage=0;
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        //是否
        if (page==1){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }

        if (page==totalPage){
             showEndPage=false;
        }else {
            showEndPage=true;
        }
    }
}
