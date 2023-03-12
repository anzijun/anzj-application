package com.azj.anzj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {

    private Integer id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    private Integer count;

//    @OneToMany(mappedBy = "blogs")
    private List<Blog> blogs= new ArrayList<>();

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
