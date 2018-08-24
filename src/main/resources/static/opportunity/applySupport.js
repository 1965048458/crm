/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var applySupportVue = new Vue({
        el: '#applySupportVue',
        data: {
            'showPage': 'mainPage',
            'supportType': '',
            showErrMsg: false,
            errMsg: ''
        },
        methods: {
            'backPage': function () {
                //do nothing
                history.back();
            },
            'selectSupportType': function () {
                this.showPage = 'supportTypePage';
            },
            'confirmSupportType': function () {
                this.showPage = 'mainPage';
            },
            'submitSupport': function () {
                var thisVue = this;

                jQuery.ajax({
                    type: 'post',
                    url: '/opportunity/action/submitApplySupport',
                    data: {
                        salesOpportunityId: jQuery('#salesOpportunityId').val(),
                        supportType: jQuery('#supportType').val(),
                        expireDate: jQuery('#date').val(),
                        order: jQuery('#selectOrder').val(),
                        content: jQuery('#content').val()
                    },
                    dataType: 'json',
                    cache: false,
                    success: function(result) {
                        if (result.successFlg) {
                            history.back();
                        } else {
                            thisVue.errMsg = result.errMsg;
                            thisVue.showErrMsg = true;
                        }
                    }
                });
            }
        },
        computed: {
            'supportTypeString': function () {
                switch (this.supportType) {
                    case 'A': return "方案";
                    case 'B': return "资源示例";
                    case 'C': return "试用";
                    case 'D': return "人员外出支持";
                    case 'E': return "项目评估";
                    case 'F': return "为代理商陪标";
                    case 'G': return "代理商授权";
                    case 'H': return "撰写招标参数";
                    case 'I': return "其他";
                    default: return "未选择";
                }
            }
        }
    });
});
