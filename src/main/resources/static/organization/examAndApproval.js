/**
 * Created by Administrator on 2018/8/21.
 */

$(document).ready(function () {
    var examAndApprovalVue = new Vue({
        el: "#examAndApprovalVue",
        data: {
            applyList: [],
            projectApplyList: []
        },
        methods:{
            'init':function () {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    url:'/message/applyList',
                    data:{},
                    dataType:'json',
                    cache:false
                }).done(function (result) {
                    console.log(result);
                    thisVue.applyList = result.applyList;
                });

                $.ajax({
                    type: 'get',
                    url: '/message/projectApply',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'projectApplyList', result.projectApplyList);
                });
            },
            'back': function () {
               window.location.href = "/myAccountInfor";
            },
            'applyAgree': function (applyType,applyId) {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    url:'/message/applyCheck',
                    data:{
                        applyType:applyType,
                        applyId:applyId,
                        isApprove:true
                    },
                    dataType:'json',
                    cache:false
                }).done(function (result) {
                    console.log(result);
                    thisVue.init()
                })
            },
            'applyDecline':function (applyType,applyId) {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    data:{
                        applyType:applyType,
                        applyId:applyId,
                        isApprove:false
                    },
                    url:'/message/applyCheck',
                    cache:false,
                    dataType:'json'
                }).done(function (result) {
                    console.log(result);
                    thisVue.init()
                })
            }
        }
    });

    Vue.component('enclosure-apply-component',{
        template:'#enclosureApplyComponent',
        props:['apply'],
        data:function () {
            return{

            }
        },
        methods:{
            'applyAgree':function (applyType,applyId) {
                examAndApprovalVue.applyAgree(applyType,applyId);
            },
            'applyDecline':function (applyType,applyId) {
                examAndApprovalVue.applyDecline(applyType,applyId);
            }
        }
    });

    Vue.component('enclosure-delay-apply-component',{
        template:'#enclosureDelayApplyComponent',
        props:['apply'],
        data:function () {
            return{

            }
        },
        methods:{
            'applyAgree':function (applyType,applyId) {
                examAndApprovalVue.applyAgree(applyType,applyId);
            },
            'applyDecline':function (applyType,applyId) {
                examAndApprovalVue.applyDecline(applyType,applyId);
            }
        }
    });

    examAndApprovalVue.init();
});