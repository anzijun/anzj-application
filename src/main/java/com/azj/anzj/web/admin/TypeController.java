package com.azj.anzj.web.admin;

import com.azj.anzj.pojo.Type;
import com.azj.anzj.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(HttpServletRequest request,
                        Model model){
        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));

        PageInfo<Type> pageInfo = typeService.queryTypePage(pageNum,5);
        model.addAttribute("types",pageInfo.getList());
        model.addAttribute("pages",pageInfo.getPages());
        model.addAttribute("pageNum",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",pageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",pageInfo.isHasNextPage());

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types_input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        model.addAttribute("type",typeService.getTypeById(id));
        return "admin/types_input";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Integer i = typeService.deleteType(id);
        if(i >=1 ){
            redirectAttributes.addFlashAttribute("message","删除成功");
        }else{
            redirectAttributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types")
    public String addType(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types_input";
        }
        Integer i = typeService.addType(type);
        if(i == 0){
            redirectAttributes.addFlashAttribute("message","插入失败");
        }else{
            redirectAttributes.addFlashAttribute("message","插入成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String updateType(@Valid Type type, BindingResult result,RedirectAttributes redirectAttributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types_input";
        }
        Integer i = typeService.updateType(type);
        if(i >= 1){
            redirectAttributes.addFlashAttribute("message","更新成功");
        }else{
            redirectAttributes.addFlashAttribute("message","更新失败");
        }
        return "redirect:/admin/types";
    }

}
