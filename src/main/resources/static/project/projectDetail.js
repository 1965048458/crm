
$(function(){
    $().timelinr({
        orientation: 'vertical', //垂直滚动
        issuesSpeed: 300, // 对应内容区的滚动速度，可为100～1000之间的数字，或者设置为'slow', 'normal' or 'fast'
        datesSpeed: 100, //主轴滚动速度，可为100～1000之间的数字，或者设置为'slow', 'normal' or 'fast'
        arrowKeys: 'true', //支持方向键
        startAt: 1 //初始化起点，即初始化轴点位置，数字
    });
});