package com.azj.anzj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author anzj
 * @date 2021/12/12 22:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    private Integer id;   //歌曲id
    private String name;  //歌曲名称
    private String musicPath; //歌曲存放路径
    private String bgPicturePath;  //背景图片存放路径
    private String author;   //歌曲作者
    private String duration;  //歌曲时长
    private String lyric;   //歌词
    private Date createDate;    //创建时间

}
