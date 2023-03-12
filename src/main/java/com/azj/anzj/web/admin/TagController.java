package com.azj.anzj.web.admin;

import com.azj.anzj.pojo.Tag;
import com.azj.anzj.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String types(HttpServletRequest request,
                        Model model){
        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));

        PageInfo<Tag> pageInfo = tagService.queryTagPage(pageNum,5);
        model.addAttribute("tags",pageInfo.getList());
        model.addAttribute("tagPages",pageInfo.getPages());
        model.addAttribute("pageNum",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",pageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",pageInfo.isHasNextPage());

        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags_input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        model.addAttribute("tag",tagService.getTagById(id));
        return "admin/tags_input";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Integer i = tagService.deleteTag(id);
        if(i >=1 ){
            redirectAttributes.addFlashAttribute("message","删除成功");
        }else{
            redirectAttributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
    public String addTag(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags_input";
        }
        Integer i = tagService.addTag(tag);
        if(i == 0){
            redirectAttributes.addFlashAttribute("message","添加失败");
        }else{
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String updateTag(@Valid Tag tag, BindingResult result,RedirectAttributes redirectAttributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags_input";
        }
        Integer i = tagService.updateTag(tag);
        if(i >= 1){
            redirectAttributes.addFlashAttribute("message","更新成功");
        }else{
            redirectAttributes.addFlashAttribute("message","更新失败");
        }
        return "redirect:/admin/tags";
    }

}
