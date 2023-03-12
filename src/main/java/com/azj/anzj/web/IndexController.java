package com.azj.anzj.web;

import com.azj.anzj.pojo.Blog;
import com.azj.anzj.service.BlogService;
import com.azj.anzj.service.TagService;
import com.azj.anzj.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String main(){
        return "main";
    }
    @GetMapping("/blogIndex")
    public String index(HttpServletRequest request,
                        Model model){

        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));

        PageInfo<Blog> blogPageInfo = blogService.allBlogs(pageNum,10);

        model.addAttribute("allBlogs",blogPageInfo.getList());
        model.addAttribute("total",blogPageInfo.getTotal());
        model.addAttribute("pages",blogPageInfo.getPages());
        model.addAttribute("pageNum",blogPageInfo.getPageNum());
        model.addAttribute("pageSize",blogPageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",blogPageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",blogPageInfo.isHasNextPage());

//        博客数量前6个分类
        model.addAttribute("types",typeService.listTopType(6));
//        博客数量前十个标签
        model.addAttribute("tags",tagService.listTopTag(10));
//        前八篇推荐博客
        model.addAttribute("tBlogs",blogService.tuiJianList(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(HttpServletRequest request,
                         @RequestParam String query,
                         Model model){
        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));
        PageInfo<Blog> blogPageInfo = blogService.queryBlogs(pageNum,10,query);

        model.addAttribute("queryBlogs",blogPageInfo.getList());
        model.addAttribute("queryTotal",blogPageInfo.getTotal());
        model.addAttribute("query",query);
        model.addAttribute("pages",blogPageInfo.getPages());
        model.addAttribute("pageNum",blogPageInfo.getPageNum());
        model.addAttribute("pageSize",blogPageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",blogPageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",blogPageInfo.isHasNextPage());

        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Integer id, Model model){

        model.addAttribute("blog",blogService.getReadOnlyBlogById(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.tuiJianList(3));
        return "_fragments :: newblogList";
    }
}
