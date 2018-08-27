
$(document).ready(function () {
    var editCustomerVue = new Vue({
        el:"#editCustomerVue",
        data:{},
        methods:{
            'init':function () {
                
            },
            'confirm':function () {
                window.location = '/customer/organization?customerId=' + $('#customerId').val();
                // var thisVue = this;
                // $.ajax({
                //     type:'get',
                //     url:'/message/applyList',
                //     data:{},
                //     dataType:'json',
                //     cache:false
                // }).done(function (result) {
                //     console.log(result)
                // })
            }
        }
    });
    
});