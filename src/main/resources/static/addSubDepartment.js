jQuery(document).ready(function () {

    var addSubDepartmentVue = new Vue({
        el: '#addSubDepartmentVue',
        data: {
            showErrMsg: false,
            errMsg: '',
            deptName: '',
            showPromptMessage: false,
            promptMessage: '',
            canSubmit: false
        },
        methods: {
            'cancelAddSubDepartment': function() {
                window.location = '/customer/customerListPage';
            },
            'confirmAddSubDepartment': function() {
                if (this.canSubmit === false) {
                    return;
                }
                var vue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/action/addSubDepartment',
                    data: {
                        parentDeptId: jQuery('#parentDeptId').val(),
                        deptName: this.deptName
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.successFlg) {
                            window.location = "/customer/customerListPage";
                        } else {
                            vue.$set(vue, 'errMsg', result.errMsg);
                            vue.$set(vue, 'showErrMsg', true);
                        }
                    },
                    error: function () {
                        vue.$set(vue, "errMsg", "发生了未知错误");
                        vue.$set(vue, 'showErrMsg', true);
                    }
                });
            },
            'deptNameInputChanged': function() {
                var vue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/customer/action/subDepartmentCheck',
                    data: {
                        parentDeptId: jQuery('#parentDeptId').val(),
                        deptName: this.deptName
                    },
                    dataType: "json",
                    cache: false,
                    success: function (result) {
                        if (result.successFlg) {
                            vue.canSubmit = true;
                            vue.showPromptMessage = false;
                            jQuery('#submitBtn').css("color", '#0076FF');
                        } else {
                            vue.canSubmit = false;
                            vue.showPromptMessage = true;
                            vue.promptMessage = result.errMsg;
                            jQuery('#submitBtn').css('color', 'gray');
                        }
                    }
                })
            }
        }
    });

});


