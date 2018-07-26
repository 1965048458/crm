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
            realName: '',
            tel: '',
            pwd: '',
            captcha: '',
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
                }).done(function (result) {
                    if (result.successFlg) {
                        window.location = '/company/chooseCompany';
                    } else {
                        alert("用户名或密码不正确");
                    }
                });
            },
            'findPwd': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else if(this.captcha.length !=4) {
                    alert("请填写4位有效验证码");
                } else if(this.pwd.length <6) {
                    alert("密码至少6位！");
                } else{
                        var thisVue = this;
                        jQuery.ajax({
                            type: 'post',
                            url: '/findPwd',
                            data: {
                                tel: thisVue.tel,
                                pwd: thisVue.pwd,
                                captcha:thisVue.captcha,
                            },
                            dataType: 'json',
                            cache: false
                        }).done(function (result) {
                            if (!result.exist){
                                alert("用户不存在");
                            }else if(!result.time){
                                alert("验证码已经失效")
                            }else if (!result.captcha){
                                alert("验证码填写错误");
                            }else if (result.successFlg) {
                                window.location.href = "/";
                            } else {
                                alert("无效的用户名或密码,密码至少6位");
                            }
                        });
                    }
                },
            'register': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/telRegister',
                    data: {
                        realName: thisVue.realName,
                        tel: thisVue.tel,
                        pwd: thisVue.pwd,
                        captcha: thisVue.captcha,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (!result.time){
                        alert("验证码已经失效")
                    }else if (!result.captcha) {
                        alert("验证码填写错误");
                    }else if(result.successFlg) {
                        window.location.href = "/";
                    } else {
                        alert("请正确填写信息且密码至少6位，检查验证码是否填写正确或者用户已经存在");
                    }
                });
            },
            'getCaptcha': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else {
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/telRegister/captcha',
                        data: {
                            tel: thisVue.tel,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.exist) {
                            alert("用户已存在");
                        } else if (result.notExpire) {
                            alert("已发送验证码，如未收到，60s后重新点击发送");
                        } else if (result.successFlag) {
                            alert("短信发送成功");
                        } else {
                            alert("短信发送失败");
                        }
                    });
                }
            },
            'getCaptcha1': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else {
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/findPwd/captcha',
                        data: {
                            tel: thisVue.tel,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (!result.exist){
                            alert("用户不存在")
                        }else if(result.notExpire){
                            alert("已发送验证码，如未收到，60s后重新点击发送");
                        } else if (result.successFlag) {
                            alert("短信发送成功");
                        } else {
                            alert("短信发送失败");
                        }
                    });
                }
            }
        }

    });
})
