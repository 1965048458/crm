/**
 * Created by Administrator on 2018/7/12.
 */

var TYPE_NAMES = {
    'PERMITTED': '已加入',
    'PENDING': '待审核',
    'REFUSE': '审核未通过',
};
jQuery(document).ready(function () {
    var mineVue = new Vue({
        el: '#mineVue',
        data: {
            realName: '',
            tel: '',
            show: 'myCompany',
            title: '',
            type: '',
            myCompany: '',
            name: '杭州浙大旭日科技开发有限公司',
            companyList: [],
            applyStaffList: [],
            userId: '',

        },

        methods: {
            'myCompany1': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/myCompany/query',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'myCompany', result.myCompany);
                        thisVue.show = 'myCompany';
                    }

                })
            },
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
            'staffAudit': function (id) {
                window.location.href = "/staffAudit?companyId="+id;
            },
            'accountSecurity': function () {
                window.location.href = "/accountSecurity";
            },
            'back': function () {
                window.location.href = "/myAccount";
            },
            'back1': function () {
                var thisVue = this;
                thisVue.show = 'myCompany';
            },
            'addCompany': function () {
                var thisVue = this;
                thisVue.show = 'addCompany';
            },
            'companyStatus': function (status) {
                return TYPE_NAMES[status];

            },
            'searchCompany': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/searchCompany',
                    data: {
                        name: thisVue.name,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        if (thisVue.name == '') {
                            thisVue.$set(thisVue, 'companyList', '');
                        } else {
                            thisVue.$set(thisVue, 'companyList', result.companyList);
                        }
                    }
                });

            },
            'submit': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/applyCompany',
                    data: {
                        name: thisVue.name,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.myCompany1();

                    } else if(result.again){
                        alert(result.errMsg);
                        thisVue.myCompany1();

                    }else{
                        alert(result.errMsg);

                    }

                });

            },
            'agree': function () {
                var thisVue = this;
                thisVue.userId = $("#userId").val();
                jQuery.ajax({
                    type: 'post',
                    url: '/agreeApply',
                    data: {
                        userId: thisVue.userId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {

                        // thisVue.staffAudit();
                    }
                });

            },
            'refuse': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/applyCompany',
                    data: {
                        name: thisVue.name,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.myCompany1();

                    } else {
                        alert(result.errMsg);
                    }

                });

            },
            'intoCompany': function (companyId) {
                jQuery.ajax({
                    type: 'post',
                    url: '/chooseCompany',
                    data: {
                        companyId: companyId
                    },
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        if (result.successFlg) {
                            window.location = '/journal/toList';
                        } else {
                            alert(result.errMsg);
                        }
                    }
                });
            },
            'toMessage':function (id) {
                window.location="message/showApplyList?companyId="+id;
            }
        },
        watch: {
            'name': function () {
                var thisVue = this;
                this.searchCompany();
                if (thisVue.name == '') {
                    thisVue.$set(thisVue, 'companyList', '');
                }
            }
        }
    });
    mineVue.myCompany1();
    mineVue.init();
})
