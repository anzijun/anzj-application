var audio = document.getElementById('audio');
var totalProgress = $('.totalProgress');
var currentProgress = $('.currentProgress');
var currentTime = $('.currentTime');
var totalTime = $('.totalTime');
var timer; //计时器

/*按钮点击播放事件*/
$('.btn').on('click',function(){
    if(audio.paused){
        changeToPlay();
    }else{
        audio.pause();
        $('#play').attr('class','btn');
        $('#pause').attr('class','btn-hide btn');
    }
});
function changeToPlay(){
    audio.play();
    $('#play').attr('class','btn-hide btn');
    $('#pause').attr('class','btn');
    timer = setInterval(function(){
        if(audio.ended){
            //如果音频播放结束
            $('#play').attr('class','btn');
            $('#pause').attr('class','btn-hide btn');
            getChangedMusic('/nextMusic');
        }else{
            //更改时间
            currentTime.text(formatTime(audio.currentTime));
            totalTime.text(formatTime(audio.duration));
            //更改进度条
            var ratio = audio.currentTime/audio.duration;
            currentProgress.css({'width':ratio*100+'%'});

        }
    },100);
}
/*单机进度条更改进度*/
totalProgress.on('click',function(ev){
    //获取当前进度百分比
    var ratio = getRatio(ev);
    currentProgress.css({'width': ratio*100+'%'});
    audio.currentTime = audio.duration * ratio;

});
var musicList = 0;
/*单机列表按钮展示歌曲列表*/
/*$('.btn-music-list').on('click',function(){
        if(musicList == 0){
            $('.music-list-container').attr('class','music-list-container display');
            musicList = 1;
        }else {
            $('.music-list-container').attr('class','music-list-container hidden');
            musicList = 0;
        }
});*/

$('.btn-music-list').popup({
    popup : $('.music-list-container.popup'),
    on : 'click',
});

var loveFlag=0;
/*单机心型按钮添加喜欢*/
$('.btn-music-love').on('click',function(){
    if(loveFlag == 0){
        $('#loveFlag').attr('class','large heart icon heart-color');
        loveFlag = 1;
    }else{
        $('#loveFlag').attr('class','large heart outline icon');
        loveFlag = 0;
    }
});
function syncLyric(){
    var html = "";
    var lyric = $('#lyric').val();//获取歌词
    var lyricArr = lyric.split("[");
    for(var i=0;i< lyricArr.length;i++){
        var lyricArrInner = lyricArr[i].split("]");
        var time = lyricArrInner[0].split(".");
        var timeArr = time[0].split(":");
        var currSecond = timeArr[0]*60+timeArr[1]*1;
        // console.log(lyricArrInner[1]);
        var message = lyricArrInner[1];
        if(message){
            html += "<p id='"+currSecond+"'>"+message+"</p>";
        }
        $(".lyricText").html(html);
    }
}
$(function(){
    syncLyric();
    var bgObj = $('.bg');
    var initPicPath = $('#picPath').text();
    bgObj.css('background','url("'+initPicPath+'")');  //页面初始化时同步背景图片
});

/*歌词联动*/
audio.addEventListener("timeupdate",function(){
    //获取播放时间
    var time = this.currentTime;
    //通过时间驱动歌词
    var s = parseInt(time);
    for(var i =0;i<s;i++){
        $("#"+i).addClass("current-lyric-color").siblings().removeClass("current-lyric-color");
        $(".lyricText").scrollTop(($(".current-lyric-color").index()-5)*32);
    }
});
function getRatio(ev){
    //总进度条的实际宽度
    var totalWidth = totalProgress[0].offsetWidth;
    //总进度条起始位置横坐标
    var totalX = totalProgress.offset().left;
    //鼠标点击的X坐标
    var mouseX = ev.clientX;
    //求出百分比
    var ratio = (mouseX - totalX)/totalWidth;
    return ratio;
}

function formatTime(time){
    //取整
    time = ~~time;
    var formatTime;
    if(time<10){
        formatTime = "00:0"+time;
    }else if (time<60){
        formatTime = "00:"+time;
    }else{
        var m = ~~(time/60);
        if(m<10){
            m = "0" + m;
        }
        var s = time % 60;
        if(s<10){
            s = "0" + s;
        }
        formatTime =  m + ":" + s;
    }
    return formatTime;
}
//点击下一首切换歌曲
$('#next').on('click',function(){
    getChangedMusic('/nextMusic');
});

//点击上一首切换歌曲
$('#pre').on('click',function(){
    getChangedMusic('/preMusic');
});

function getChangedMusic(reqUrl){
    var bgObj = $('.bg');
    var audioObj = $('#audio'); //获取audio元素对象
    var lyricObj = $('#lyric'); //获取歌词文本元素对象
    var musicPic = $('.img-position'); //获取当前歌曲背景图片元素对象
    var musicName = $('.musicName'); //获取当前播放歌曲名称元素对象
    var currMusicIdObj = $('#currMusicId');
    var currMusicId = currMusicIdObj.text();

    $.ajax({
        url : reqUrl+"?currMusicId="+currMusicId,
        data : "json",
        success : function (data) {
            var musicObj = JSON.parse(data);
            audioObj.attr('src',musicObj.musicPath+musicObj.name); //同步当前播放歌曲
            lyricObj.text(musicObj.lyric);   //同步歌词文本内容
            syncLyric();  //同步歌词
            musicPic.attr('src',musicObj.bgPicturePath);  //同步设置歌曲图片
            bgObj.css('background','url("'+musicObj.bgPicturePath+'")');  //同步设置页面背景图片
            bgObj.css('background-size','cover');
            musicName.text(musicObj.name);  //同步歌曲名称
            currMusicIdObj.text(musicObj.id);   //同步当前播放歌曲id
            //切换时默认播放
            if(audio.paused){
                changeToPlay();
            }
        }
    });
}