package com.dxs.front.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class PaginationDTO {
    private List<ArticleDTO> articles;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    /**
     * @param totalCount 文章总数
     * @param page       当前传入的页码
     * @param size       一页显示的文章数
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {


    if(totalCount % size == 0){
        totalPage=totalCount / size;
    }else{
        totalPage=totalCount / size + 1;
    };

        //页码的容错处理
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;

        this.page = page;
        pages.add(page);

        //向前展示三个页码，向后展示三个页码
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) pages.add(0, page - i);
            if (page + i <= totalPage) pages.add(page + i);
        }

        //是否展示上一页
        showPrevious = page == 1 ? false : true;
        //是否展示下一页
        showNext = page == totalPage ? false : true;
        //是否展示第一页
        showFirstPage = pages.contains(1) ? false : true;

        //是否展示最后一页
        showEndPage = pages.contains(totalPage) ? false : true;

    }
}
