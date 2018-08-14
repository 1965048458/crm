/**
 * Created by Administrator on 2018/7/12.
 */

jQuery(document).ready(function () {
    var staffAudit = new Vue({
        el: '#staffAudit',
        data: {
            applyStaffList:'',

        },

        methods: {
            'init': function () {
                var thisVue = this;
                jQuery.ajax({
                    type:'get',
                    url:'/staffAudit/check',
                    dataType:'json',
                    cache:false
                }).done(function(result){
                    if(result.successFlg){
                        thisVue.$set(thisVue, 'applyStaffList', result.companyUsers);
                    }
                })
            },
            'back': function () {
                window.location.href = "/administrator";
            },
            'agree': function(userId){
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/agreeApply',
                    data: {
                        userId:userId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                     thisVue.init();

                    }
                });

            },
            'refuse': function(userId){
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/refuseApply',
                    data: {
                        userId:userId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        thisVue.init();
                    }

                });

            },
        },
        watch: {
            'name': function () {
                var thisVue = this;
                this.searchCompany();
                if(thisVue.name ==''){
                    thisVue.$set(thisVue, 'companyList', '');
                }
            }
        }
    });
    staffAudit.init();
})
