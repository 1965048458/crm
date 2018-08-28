

jQuery(document).ready(function () {

    var addTopDepartmentVue = new Vue({
        el: '#addTopDepartmentVue',
        data: {
            showErrMsg: false,
            errMsg: '',
            deptName: '',
            profile: '',
            website: '',
            canSubmit:false,
            showPromptMessage:false,
            promptMessage:''
        },
        methods: {
            'cancelAddTopDepartment': function() {
                window.location = '/customer/editCustomer?customerId=' + jQuery('#customerId').val();
            },
            'confirmAddTopDepartment': function() {
                if(!this.canSubmit){
                    return;
                }
                var vue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/action/addTopDepartment',
                    data: {
                        customerId: $('#customerId').val(),
                        deptName: this.deptName,
                        website: this.website,
                        profile: this.profile

                    },
                    dataType: "json",
                    cache:false,
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
            },
            'deptNameInputChanged':function () {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    url:'/customer/action/departmentCheck',
                    data:{deptName:this.deptName,
                         customerId:$('#customerId').val()
                    },
                    dataType:'json',
                    cache:false,
                    success:function (result) {
                        if(result.successFlg){
                            thisVue.canSubmit=true;
                            thisVue.showPromptMessage=false;
                            $('#submitBtn').css("color",'rgb(61, 168, 244)');
                        }else {
                            thisVue.canSubmit=false;
                            thisVue.showPromptMessage=true;
                            thisVue.promptMessage=result.errMsg;
                            $('#submitBtn').css("color",'gray');
                        }
                    }

                })
            }
        }
    });

});