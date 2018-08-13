

jQuery(document).ready(function () {

    var addTopDepartmentVue = new Vue({
        el: '#addTopDepartmentVue',
        data: {
            showErrMsg: false,
            errMsg: '',
            deptName: '',
            profile: '',
            website: ''
        },
        methods: {
            'cancelAddTopDepartment': function() {
                window.location = '/customer/editCustomer?customerId=' + jQuery('#customerId').val();
            },
            'confirmAddTopDepartment': function() {
                var vue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/action/addTopDepartment',
                    data: {
                        customerId: jQuery('#customerId').val(),
                        deptName: this.deptName,
                        profile: this.profile,
                        website: this.website
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.successFlg) {
                            window.location = '/customer/editCustomer?customerId=' + jQuery('#customerId').val();
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
            }
        }
    });

});