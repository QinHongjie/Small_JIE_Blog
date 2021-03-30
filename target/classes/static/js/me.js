/* ------------- ajax接口请求 --------------*/
var userNickName=null,userEmail=null;
$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "/api/getUserBySession",
        success: function (data) {
            if (data.code == 200) {
                console.log(data.data.username + "," + data.data.email + "," + data.data.avatar)
                userNickName = data.data.nickname;
                userEmail = data.data.email;
                $(".userNickName").text(data.data.nickname)
                $(".userEmail").text(data.data.email)
                $(".userAvatar").attr("src", data.data.avatar)
            }
        }
    });
});






/* 顶部导航栏隐藏显示按钮 */
$('.menu.toggle').click(function() {
    $('.m-item').toggleClass('m-mobile-hide');
});

/* ------ 平滑滚动置顶 -------- */
$('#toTop-button').click(function(){
    $(window).scrollTo(0,500);
});

$('.ui.dropdown').dropdown({
    on : 'click'
});


/* 滚动监听 */
$('#toolbar:not(.nohide)').hide();
setInterval(function () {
    var scrollTop = $(document).scrollTop();
    if (scrollTop > 200) {
        $('#toolbar:not(.nohide)').show(500);
    } else {
        $('#toolbar:not(.nohide)').hide(500);
    }
},500);


/* 页面元素不足浏览器窗口高度时 页脚自动定位到最下面 */
var windowHight = $(window).height();
var bodyHight = $('#footer').height() + $('#footer').offset().top;
if (bodyHight < windowHight) {
    $('#footer').css({'position': 'absolute', 'left': '0', 'bottom': '0', 'width': '100%'});
    var footerTime = setInterval(function(){
        var lastDivOffsetTop = $('body>div').last().offset().top + $('body>div').last().outerHeight();
        if (lastDivOffsetTop > $('#footer').offset().top) {
            $('#footer').css({'position': 'relative'});
        }
    },100);
    setTimeout(function () { clearInterval(footerTime) }, 3000);
}


/* --------- 页面加载完成后执行的内容 ------- */
$(document).ready(function(){

    /* 页面加载完成后清除加载层 */
    $('#loading').hide();

    /* -- 禁用鼠标右键 -- */
    $(document).bind("contextmenu",function(e){
        if (e.which==3){
            // 鼠标右键事件
        }
        return false;
    });
});


/* ---------- 鼠标点击特效 -----------*/
$(document).ready(function($) {
    $("body").click(function(e) {
        //随机选择文字
        var a_idx = Math.floor((Math.random() * 13));
        var a = new Array(
             "❤", "富强", "民主", "文明", "和谐", "自由", "平等", "公正",
            "法制", "爱国", "敬业", "诚信", "友善", "(*^▽^*)"
        );
        // 随机产生文字颜色
        var color1 = Math.floor((Math.random() * 255));
        var color2 = Math.floor((Math.random() * 255));
        var color3 = Math.floor((Math.random() * 255));

        var $i = $("<span>").text(a[a_idx]);
        a_idx = (a_idx + 1) % a.length;
        var x = e.pageX,
            y = e.pageY;
        $i.css({　　　　　　//文字样式--------------------------
            "z-index": 9999999999999 ,
            "top": y - 20,
            "left": x,
            "position": "absolute",
            "font-family":"宋体",
            "fontSize":Math.floor((Math.random() * 22)+15),
            "font-weight": "bold",
            "color": "rgb("+color1+","+color2+","+color3+")",
            "-webkit-user-select":"none",
            "-moz-user-select":"none",
            "-ms-user-select":"none",
            "user-select":"none",
        });
        $("body").append($i);
        $i.animate({
            "top": y - 200,  //点击后文字上升高度
            "opacity": 0    //透明度
        }, 1000, function() {
            $i.remove();//文字消失时间
        });
    });
});


/* ----------- Live-2D看板娘模型 ------------*/
L2Dwidget.init({
    model: {
        //jsonpath控制显示那个小萝莉模型，下面这个就是我觉得最可爱的小萝莉模型 //hijiki猫  //izumi小姐姐 //shizuku书桌 //koharu萝莉
        jsonPath: "https://unpkg.com/live2d-widget-model-koharu@1.0.5/assets/koharu.model.json", //这里改模型，前面后面都要改
        scale: 1,
    },
    display: {
        position: "left", //设置看板娘的上下左右位置
        width: 120,  //小萝莉的宽度
        height: 200, //小萝莉的高度
        hOffset: 0,  //水平偏移
        vOffset: 0  //垂直偏移
    },
    mobile: {
        show: true,  //在移动端显示
        scale: 0.4    //移动设备上的缩放
    },
    react: {
        opacityDefault: 1, //设置透明度
        opacityOnHover: 0.2
    },
});
setTimeout(function () {
    $("#l2d-clear").show();
},3000)
// 清除看板娘
function L2Dclear() {
    L2Dwidget.init({
        model: {
            //jsonpath控制显示那个小萝莉模型，下面这个就是我觉得最可爱的小萝莉模型 //hijiki猫  //izumi小姐姐 //shizuku书桌 //koharu萝莉
            jsonPath: "https://unpkg.com/live2d-widget-model-koharu@1.0.5/assets/koharu.model.json", //这里改模型，前面后面都要改
            scale: 1,
        },
        display: {
            display: "none",
        },
        react: {
            opacityDefault: 0, //设置透明度
        }
    });
    $("#l2d-clear").hide();
}



