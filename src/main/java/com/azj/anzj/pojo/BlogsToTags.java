package com.azj.anzj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogsToTags {

    private Integer relId;
    private Integer blogId;
    private Integer tagId;


}
