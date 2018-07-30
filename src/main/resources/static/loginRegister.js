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
            showPage: 'telRegister',
            gender:'',
            age:'',
            mail:'',
            address:'',
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
                        window.location = '/journal/toList';
                    } else {
                        alert("用户名或密码不正确");
                    }
                });
            },
            'findPwd': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else if (this.captcha.length != 4) {
                    alert("请填写4位有效验证码");
                } else if (this.pwd.length < 6) {
                    alert("密码至少6位！");
                } else {
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/findPwd',
                        data: {
                            tel: thisVue.tel,
                            pwd: thisVue.pwd,
                            captcha: thisVue.captcha,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            window.location.href = "/";
                        } else {
                            alert(result.errMsg);
                        }
                    });
                }
            },
            'register': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else if (this.captcha.length != 4) {
                    alert("请填写4位有效验证码");
                } else if (this.realName == '') {
                    alert("请填写姓名信息");
                } else if (this.pwd.length < 6) {
                    alert("密码至少6位！");
                } else {
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
                        if (result.successFlg) {
                            thisVue.showPage = 'supplementaryInformation';
                        } else {
                            alert(result.errMsg);
                        }
                    });
                }
            },
            'getCaptchaRegister': function () {
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
                        if (result.successFlg) {
                            alert(result.errMsg);
                        } else {
                            alert(result.errMsg);
                        }
                    });
                }
            },
            'getCaptchaPwd': function () {
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
                        if (result.successFlg) {
                            alert(result.errMsg);
                        } else {
                            alert(result.errMsg);
                        }
                    });
                }
            },
            'confirm': function () {
                if (this.gender =='') {
                    alert("请选择性别");
                } else if(this.age =='') {
                    alert("请填写年龄");
                }else if(this.age >150 || this.age <0) {
                    alert("请输入正确的年龄");
                } else{
                        var thisVue = this;

                        jQuery.ajax({
                            type: 'post',
                            url: '/supplementaryInformation/add',
                            data: {
                                realName: thisVue.realName,
                                tel: thisVue.tel,
                                pwd: thisVue.pwd,
                                gender: thisVue.gender,
                                age: thisVue.age,
                                mail:thisVue.mail,
                                address:thisVue.address,
                            },
                            dataType: 'json',
                            cache: false
                        }).done(function (result) {
                            if (result.successFlg) {
                                window.location = '/company/chooseCompany';
                            } else {
                                alert("信息填写错误，注册失败");
                            }
                        });
                    }


            },
        }

    });
})
