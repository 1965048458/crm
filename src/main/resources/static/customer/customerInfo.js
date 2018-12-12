/**
 * Created by Administrator on 2018/7/18.
 */
var color = ["", "#2eff4e", "#2b79ff", "#ff7e24"];
var percent = [0, 80, 75, 90];
//window.onload =
$(document).ready(function ()  {

    var customerVue = new Vue({
        el: '#customerVue',
        data: {
            customerName: '',
            customerId:'',
            companyId:'',
            visitCount:0,
            a:0,
            aRate:'0.00',
            companyData:'',
            personCount:0
        },
        methods: {
            'init':function (customerId, customerName,companyId) {
              this.customerId = customerId;
              this.customerName = customerName;
              this.companyId=companyId;
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/company/oppData',
                    data:
                        {
                            customerId:this.customerId,
                            companyId:this.companyId
                        },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    thisVue.$set(thisVue, 'companyData', result.companyData);
                    thisVue.$set(thisVue, 'visitCount', result.visitCount);
                    thisVue.$set(thisVue, 'a', result.a);
                    thisVue.$set(thisVue, 'personCount', result.personCount);
                    thisVue.$set(thisVue, 'aRate', result.aRate);
                });
            },
            'getCustomerInfo': function () {
                $.ajax({
                    type: 'get',
                    url: '',
                    data:{},
                    dataType: 'json',
                    cache: false
                });
            },
            'orgDisplay':function () {
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                var temp = this;
                $loadingToast.fadeIn(100);
                setTimeout(function () {
                    $loadingToast.fadeOut(100);
                    location.href = '/customer/organization?customerId=' + temp.customerId;
                }, 500);
            }
        }
    });

    var customerId = $("#customerId").val();
    var customerName = $("#customerName").val();
    var companyId=$("#companyId").val();

    customerVue.init(customerId,customerName,companyId);

    // for (var i = 1; i <= 3; i++) {
    //     var canvas = document.getElementById('canvas' + i),  //获取canvas元素
    //         context = canvas.getContext('2d'),  //获取画图环境，指明为2d
    //         centerX = canvas.width / 2,   //Canvas中心点x轴坐标
    //         centerY = canvas.height / 2,  //Canvas中心点y轴坐标
    //         rad = Math.PI * 2 / 100, //将360度分成100份，那么每一份就是rad度
    //         speed = 0.1; //加载的快慢就靠它了
    //
    //     //动画循环
    //     (function () {
    //         //window.requestAnimationFrame(drawFrame, canvas);
    //         context.clearRect(0, 0, canvas.width, canvas.height);
    //         whiteCircle(context);
    //         text(context, customerVue.percent[i], color[i]);
    //         blueCircle(context, customerVue.percent[i], color[i]);
    //         if (speed > 100) speed = 0;
    //         speed += 0.1;
    //     }());
    // }
    //
    // //绘制蓝色外圈
    // function blueCircle(context, n, color) {
    //     context.save();
    //     context.strokeStyle = color; //设置描边样式
    //     context.lineWidth = 3; //设置线宽
    //     context.beginPath(); //路径开始
    //     context.arc(centerX, centerY, 30, -Math.PI / 2, -Math.PI / 2 + n * rad, false); //用于绘制圆弧context.arc(x坐标，y坐标，半径，起始角度，终止角度，顺时针/逆时针)
    //     context.stroke(); //绘制
    //     context.closePath(); //路径结束
    //     context.restore();
    // }
    //
    // //绘制白色外圈
    // function whiteCircle(context) {
    //     context.save();
    //     context.beginPath();
    //     context.strokeStyle = "#808080";
    //     context.lineWidth = 3; //设置线宽
    //     context.arc(centerX, centerY, 30, 0, Math.PI * 2, false);
    //     context.stroke();
    //     context.closePath();
    //     context.restore();
    // }
    //
    // //百分比文字绘制
    // function text(context, n, color) {
    //     context.save(); //save和restore可以保证样式属性只运用于该段canvas元素
    //     context.strokeStyle = color; //设置描边样式
    //     context.font = "20px Arial"; //设置字体大小和字体
    //     //绘制字体，并且指定位置
    //     context.strokeText(n.toFixed(0) + "%", centerX - 18, centerY + 7);
    //     context.stroke(); //执行绘制
    //     context.restore();
    // }

});



