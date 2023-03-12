package com.azj.anzj.web.admin;

import com.alibaba.fastjson.JSONArray;
import com.azj.anzj.pojo.Music;
import com.azj.anzj.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author anzj
 * @date 2021/12/12 22:48
 */
@Controller
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/music")
    public String musicList(Model model){

        Music currMusic = musicService.getCurrMusic();
        model.addAttribute("musicTotalName",currMusic.getMusicPath()+currMusic.getName());
        model.addAttribute("currMusic",currMusic);
        model.addAttribute("musicList",musicService.findMusicList());
        return "musicLove";
    }
    //按顺序获取下一首歌曲
    @GetMapping("/nextMusic")
    @ResponseBody
    public String getNextByOrder(@RequestParam("currMusicId") String currMusicId){

        Integer CurrId = Integer.valueOf(currMusicId);
        Integer nextId = CurrId == musicService.getMaxId()?1:CurrId + 1;

        Music currMusic = musicService.getMusicById(nextId);
        Object obj = JSONArray.toJSON(currMusic);
        String musicStr = obj.toString();
        return musicStr;
    }
    //按顺序获取上一首歌曲
    @GetMapping("/preMusic")
    @ResponseBody
    public String getPreByOrder(@RequestParam("currMusicId") String currMusicId){

        Integer CurrId = Integer.valueOf(currMusicId);
        Integer preId = CurrId == 1?musicService.getMaxId():CurrId - 1;

        Music currMusic = musicService.getMusicById(preId);
        Object obj = JSONArray.toJSON(currMusic);
        String musicStr = obj.toString();
        return musicStr;
    }
    //随机获取下一首歌曲
    public String getNextByRandom(){
        return "";
    }

}
