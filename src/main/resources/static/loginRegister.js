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
                    url: '/login',
                    data: {
                        tel: thisVue.tel,
                        pwd: thisVue.pwd
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location.href="/customer";
                    } else {
                        alert("用户名或密码不正确");
                    }
                });
            },
            'findPwd': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/findPwd',
                    data: {

                        tel: thisVue.tel,
                        pwd: thisVue.pwd

                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location.href="/";
                    } else {
                        alert("无效的用户名或密码,密码至少6位");
                    }
                });
            },
            'register': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/telRegister',
                    data: {
                        realName: thisVue.realName,
                        tel: thisVue.tel,
                        pwd: thisVue.pwd

                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location.href="/";
                    } else {
                        alert("无效的手机号、姓名或密码，密码至少6位");
                    }
                });
            }
        }
    });

});