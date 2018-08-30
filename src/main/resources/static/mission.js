/**
 * Created by Administrator on 2018/8/1.
 */
var TYPE_OEDER = {
    'NORMAL': '普通',
    'URGENT': '紧急',
    'GREATEURGENT': '十分紧急'
};

var TYPE_TYPE = {
    'A': '方案', 'B': '资源示例', 'C': '试用',
    'D': '人员外出支持',
    'E': '项目评估', 'F': '为代理商陪标', 'G': '代理商授权',
    'H': '撰写招标参数', 'I': '其他'
};
var mission;
$(document).ready(function () {

    mission = new Vue({
        el: '#mission',
        data: {
            showPage: 'unfinish',
            show: 'home',
            percent: '',
            unfinishList: '',
            finishList: '',
            curSupport: '',
            information: '',
            admin:false,
            operator:false,
            progress:'',
            showSlider:false,
            button:'修改',
            userList:'',
            chosenExe:'',
            searchBar:false,
            keyWord:''

        },
        methods: {
            init: function () {
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/missionList',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'unfinishList', result.unfinish);
                        thisVue.$set(thisVue, 'finishList', result.finish);
                        $loadingToast.fadeOut(100);
                    }
                });
            },
            'unfinish': function () {
                this.showPage = 'unfinish';
            },
            'finish': function () {
                this.showPage = 'finish';

            },
            'toDetail': function (supportId) {
                this.show = 'detail';
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/missionDetail',
                    data: {
                        supportId: supportId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'curSupport', result.support);
                        thisVue.$set(thisVue, 'information', result.information);
                        thisVue.$set(thisVue, 'admin', result.admin);
                        thisVue.$set(thisVue, 'operator', result.operator);
                    }
                });
            },
            'addProgress': function () {
                this.show = 'addProgress';
            },
            'addBack':function () {
              this.show = 'detail';
            },
            'submit':function () {
                var thisVue = this;
                if(this.progress == ''){
                    alert("请填写内容后提交");
                }else {
                    $.ajax({
                        type: 'get',
                        url: '/project/addProgressInform',
                        data: {
                            supportId: thisVue.curSupport.support.supportId,
                            progress: thisVue.progress
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            thisVue.toDetail(thisVue.curSupport.support.supportId);
                            thisVue.show = 'detail';
                        }
                    });
                }
            },
            'back': function () {
                this.show = 'home';
            },
            'supportType': function (type) {
                return TYPE_TYPE[type];
            },
            'supportOrder': function (type) {
                return TYPE_OEDER[type];
            },
            'modify': function () {
                if(this.showSlider==false){
                    this.button = '提交';
                    this.showSlider = true;
                }else{
                    var thisVue = this;
                    $.ajax({
                        type: 'get',
                        url: '/project/updateSupportProgress',
                        data: {
                            supportId: thisVue.curSupport.support.supportId,
                            progress: jQuery("#sliderValue").text()
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            thisVue.button = '修改';
                            thisVue.showSlider = false;
                            thisVue.init();
                        }else{
                            thisVue.button = '修改';
                            thisVue.showSlider = false;
                            alert("系统出错");
                        }

                    });
                }
            },
            'chooseExe':function (keyword) {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/queryCompanyUser',
                    data:{
                      keyword:keyword
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'userList', result.companyUsers);
                        thisVue.show = 'chooseExe';
                    }else{
                        alert("系统出错");
                    }
                });

            },
            'chooseBack':function () {
                this.show = 'detail';
            },
            'confirm': function () {
                var thisVue = this;
                if(this.chosenExe==''){
                    alert("请选择执行人");
                }else {
                    $.ajax({
                        type: 'get',
                        url: '/project/chooseExecutor',
                        data: {
                            supportId: thisVue.curSupport.support.supportId,
                            leaderId: thisVue.chosenExe
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            thisVue.toDetail(thisVue.curSupport.support.supportId);
                            thisVue.chosenExe ='';
                            thisVue.init();
                            thisVue.show = 'detail';
                        } else {
                            alert("系统出错");
                        }
                    });
                }
            },
            'clear': function () {
                this.keyWord = '';
                $('#searchInput').focus();
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.keyWord = '';
                $('#searchInput').blur();
                this.searchBar = false;
            },
            'search': function () {
                this.searchBar = !this.searchBar;
                if(!this.searchBar){
                    this.keyWord ='';
                }
            },
        },
        watch: {
            'keyWord':function () {
                this.chooseExe(this.keyWord);
            }
        },
        updated: function () {
            if (this.curSupport !== undefined &&
                this.curSupport.support !== undefined) {
                var percent = this.curSupport.support.percent;
                jQuery("#sliderTrack").css('width', percent + '%');
                jQuery("#sliderHandler").css('left', percent + '%');
                initSilder();
            }
        }
    });
    mission.init();
});
var totalLen,
    startLeft = 0,
    startX = 0;
function initSilder() {
    var $sliderTrack = $('#sliderTrack'),
        $sliderHandler = $('#sliderHandler'),
        $sliderValue = $('#sliderValue');

    $sliderHandler.unbind('touchstart')
        .on('touchstart', function (e) {
            totalLen = $('#sliderInner').width();
            startLeft = parseInt($sliderHandler.css('left'));
            startX = e.originalEvent.changedTouches[0].clientX;
        }).unbind('mousedown')
        .on('mousedown', function (e) {
            totalLen = $('#sliderInner').width();
            startLeft = parseInt($sliderHandler.css('left'));
            startX = e.originalEvent.pageX;

            $sliderHandler.unbind('mousemove').on('mousemove', function (e) {
                totalLen = $('#sliderInner').width();
                var moveX = e.originalEvent.pageX;
                var dist = startLeft + moveX - startX,
                    percent;
                dist = dist < 0 ? 0 : dist > totalLen ? totalLen : dist;
                percent = parseInt(dist / totalLen * 100);
                $sliderTrack.css('width', percent + '%');
                $sliderHandler.css('left', percent + '%');
                $sliderValue.text(percent);

                e.preventDefault();
            })
        }).unbind('touchmove')
        .on('touchmove', function (e) {
            var moveX = e.originalEvent.changedTouches[0].clientX;
            var dist = startLeft + moveX - startX,
                percent;
            dist = dist < 0 ? 0 : dist > totalLen ? totalLen : dist;
            percent = parseInt(dist / totalLen * 100);
            $sliderTrack.css('width', percent + '%');
            $sliderHandler.css('left', percent + '%');
            $sliderValue.text(percent);
            mission.curSupport.support.percent = percent;
            e.preventDefault();
        })
    ;
}