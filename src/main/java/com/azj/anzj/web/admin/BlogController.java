package com.azj.anzj.web.admin;

import com.azj.anzj.pojo.Blog;
import com.azj.anzj.pojo.User;
import com.azj.anzj.service.BlogService;
import com.azj.anzj.service.BlogsToTagsService;
import com.azj.anzj.service.TagService;
import com.azj.anzj.service.TypeService;
import com.azj.anzj.vo.BlogQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs_input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogsToTagsService blogsToTagsService;

//展示所有笔记
    @GetMapping("/blogs")
    public String listBlog(HttpServletRequest request,
                           Model model){
        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));

        PageInfo<Blog> allBlogPageInfo = blogService.allBlogs(pageNum,5);

        model.addAttribute("blogs",allBlogPageInfo.getList());
        model.addAttribute("pages",allBlogPageInfo.getPages());
        model.addAttribute("pageNum",allBlogPageInfo.getPageNum());
        model.addAttribute("pageSize",allBlogPageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",allBlogPageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",allBlogPageInfo.isHasNextPage());
        model.addAttribute("types",typeService.listType());
        return LIST;
    }
//根据条件查询博客
    @PostMapping("/blogs/search")
    public String search(HttpServletRequest request,
                         BlogQuery blog,
                           Model model){
        Integer pageNum = request.getParameter("pageNum") == null ? 1 : Integer.valueOf(request.getParameter("pageNum"));

        PageInfo<Blog> blogPageInfo = blogService.queryBlogs(pageNum,5,blog);
        model.addAttribute("blogs",blogPageInfo.getList());
        model.addAttribute("pages",blogPageInfo.getPages());
        model.addAttribute("pageNum",blogPageInfo.getPageNum());
        model.addAttribute("pageSize",blogPageInfo.getPageSize());
        model.addAttribute("hasPreviousPage",blogPageInfo.isHasPreviousPage());
        model.addAttribute("hasNextPage",blogPageInfo.isHasNextPage());

        model.addAttribute("types",typeService.listType());
        return "admin/blogs :: blogList";
    }
//新增博客页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
//    从后台查询所有标签和所有分类类别
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")  //根据id查博客(修改博客时用)
    public String editInput(@PathVariable Integer id, Model model){
        setTypeAndTag(model);

        Blog blog = blogService.getBlogById(id);

        blog.init();//将当前对象的标签id集合转换为字符串

        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping("/blogs")   //新增一篇博客
    public String post(Blog blog, RedirectAttributes redirectAttributes,HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        int b = blogService.addBlog(blog);
        if(b >= 1){
            redirectAttributes.addFlashAttribute("message","操作成功");
            Blog tempBlog = blogService.getBlogByTitle(blog.getTitle());
            blogsToTagsService.addRel(tempBlog.getId(),stringToList(blog.getTagIds()));
        }else{
            redirectAttributes.addFlashAttribute("message","操作失败");
        }
        return REDIRECT_LIST;
    }

    @PostMapping("/blogs/{id}")  //修改一篇博客
    public String updateBlog(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        
        Integer d = blogsToTagsService.deleteRel(blog.getId());//获取当前页面传来的博客id删除相关标签
        Integer a = blogsToTagsService.addRel(blog.getId(),stringToList(blog.getTagIds()));//添加新的博客标签关系
        Integer i = blogService.updateBlog(blog);
        if(i >= 1){
            redirectAttributes.addFlashAttribute("message","更新成功");
        }else{
            redirectAttributes.addFlashAttribute("message","更新失败");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")  //根据id删除一篇博客
    public String delete(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return  REDIRECT_LIST;
    }

    public List<Integer> stringToList(String ids){
        List<Integer> list = new ArrayList<>();
        if(!"".equals(ids) && ids !=null){
            String[] idarr = ids.split(",");
            for(int i=0; i<idarr.length; i++){
                list.add(new Integer(idarr[i]));
            }
        }
        return list;
    }
}
