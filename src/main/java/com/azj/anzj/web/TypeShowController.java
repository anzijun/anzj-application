package com.azj.anzj.web;

import com.azj.anzj.pojo.Blog;
import com.azj.anzj.pojo.Type;
import com.azj.anzj.service.BlogService;
import com.azj.anzj.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{typeId}")
    public String types(HttpServletRequest request,
                        Model model,
                        @PathVariable("typeId") Integer typeId){

        List<Type> types = typeService.listTopType(10000);
        if(typeId == -1){
            typeId = types.get(0).getId();
        }

        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));

        PageInfo<Blog> blogPageInfo = blogService.queryTypesBlogs(pageNum,10,typeId);

        model.addAttribute("queryTypesBlogs",blogPageInfo.getList());
        model.addAttribute("queryTotal",blogPageInfo.getTotal());
        model.addAttribute("pages",blogPageInfo.getPages());
        model.addAttribute("pageNum",blogPageInfo.getPageNum());
        model.addAttribute("pageSize",blogPageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",blogPageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",blogPageInfo.isHasNextPage());
        model.addAttribute("activeTypeId",typeId);
        model.addAttribute("types",types);
        return "types";
    }
}
