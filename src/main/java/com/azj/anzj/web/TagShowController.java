package com.azj.anzj.web;

import com.azj.anzj.pojo.Blog;
import com.azj.anzj.pojo.Tag;
import com.azj.anzj.service.BlogService;
import com.azj.anzj.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{tagId}")
    public String types(HttpServletRequest request,
                        Model model,
                        @PathVariable("tagId") Integer tagId){

        List<Tag> tags = tagService.listTopTag(10000);
        if(tagId == -1){
            tagId = tags.get(0).getId();
        }

        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));

        PageInfo<Blog> blogPageInfo = blogService.queryTagsBlogs(pageNum,10,tagId);

        model.addAttribute("queryTagsBlogs",blogPageInfo.getList());
        model.addAttribute("queryTagBlogsTotal",blogPageInfo.getTotal());
        model.addAttribute("pages",blogPageInfo.getPages());
        model.addAttribute("pageNum",blogPageInfo.getPageNum());
        model.addAttribute("pageSize",blogPageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",blogPageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",blogPageInfo.isHasNextPage());
        model.addAttribute("activeTagId",tagId);

        model.addAttribute("tags",tags);
        return "tags";
    }
}
