/**
 * Created by Administrator on 2018/7/12.
 */

jQuery(document).ready(function () {
    var accountSecurity = new Vue({
        el: '#accountSecurity',
        data: {
            realName:'',
            tel:'',

        },

        methods: {
            'init': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/accountSecurity/ini',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'realName', result.user.realName);
                        thisVue.$set(thisVue, 'tel', result.user.tel);
                    }
                })
            },
            'back': function () {
                window.location.href = "/myAccount";
            },
            'dropOut': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/accountSecurity/dropOut',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        window.location.href = "/";
                    }

                })

            },
        }

    });
    accountSecurity.init();
})
