/**
 * Created by Administrator on 2018/7/12.
 */
/**
 * Created by Administrator on 2018/7/12.
 */
jQuery(document).ready(function () {
    var insertVue = new Vue({
        el: '#insertVue',
        data: {
            realName:'',
            tel:'',
            pwd:''

        },
        methods: {
            'login': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/login/login',
                    data: {
                        tel: thisVue.tel,
                        pwd: thisVue.pwd
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location.href="/sample";
                    } else {
                        alert("用户名或密码不正确");
                    }
                });
            },
            'findPwd': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/login/findPwd',
                    data: {

                        tel: thisVue.tel,
                        pwd: thisVue.pwd

                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location.href="/login";
                    } else {
                        alert("无效的用户名或密码,密码至少6位");
                    }
                });
            },
            'register': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/login/telRegister',
                    data: {
                        realName: thisVue.realName,
                        tel: thisVue.tel,
                        pwd: thisVue.pwd

                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location.href="/login";
                    } else {
                        alert("无效的手机号、姓名或密码，密码至少6位");
                    }
                });
            }
        }
    });

});