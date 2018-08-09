/**
 * Created by Administrator on 2018/7/12.
 */
/**
 * Created by Administrator on 2018/7/12.
 */
var TYPE_NAMES = {
    'PERMITTED': '已加入',
    'PENDING': '待审核',
    'REFUSE': '审核未通过',
};
jQuery(document).ready(function () {
    var myAccount = new Vue({
        el: '#myAccount',
        data: {
            realName: '',
            tel: '',
            show: '',
            title: '',
            type: '',
            myCompany: '',
            name: '',
            companyList: [],
            applyStaffList: [],
            userId: '',

        },

        methods: {
            'init': function () {
                var thisVue = this;
                thisVue.type = $("#type").val();
                if (thisVue.type == 'ADMIN') {
                    thisVue.show = 'manager';
                    thisVue.title = $("#realName").val() + '管理员';
                    // thisVue.applyStaffList = $("#applyStaffList").val() ;
                    // // thisVue.$set(thisVue, "applyStaffList", $("#applyStaffList").val());
                    // console.log(thisVue.applyStaffList);
                } else {
                    thisVue.show = 'staff';
                    thisVue.title = $("#realName").val();
                }

            },

            'myCompany1': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/myCompany',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'myCompany', result.myCompany);
                        thisVue.show = 'myCompany';
                    }

                })
            },
            'staffAudit': function () {
                window.location.href = "/staffAudit";
            },
            'accountSecurity': function () {
                window.location.href = "/accountSecurity";
            },
            'back': function () {
                var thisVue = this;
                if (thisVue.type == 'ADMIN') {
                    thisVue.show = 'manager';
                    thisVue.title = $("#realName").val() + '管理员';
                } else {
                    thisVue.show = 'staff';
                    thisVue.title = $("#realName").val();
                }
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

                    } else {
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
    myAccount.init();
})
