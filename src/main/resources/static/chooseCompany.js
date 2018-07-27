

jQuery(document).ready(function () {
    var chooseCompanyVue = new Vue({
        el: '#chooseCompanyVue',
        data: {
            companys: []
        },
        methods: {
            'confirmCompany': function (companyID) {
                jQuery.ajax({
                    type: 'get',
                    url: '/company/action/confirmCompany',
                    data: {
                        companyId: companyID
                    },
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        if (result.successFlg) {
                            window.location = "/demo/list";
                        }
                    }
                });
            },
            'create':function () {
                window.location.href = '/company/newCompany';
            }
        }
    });

    jQuery.ajax({
        type: 'get',
        url: '/company/action/getCompanyList',
        dataType: 'json',
        cache: false,
        success: function (result) {
            if (result.successFlg) {
                chooseCompanyVue.$set(chooseCompanyVue, 'companys', result.companys);
            }
            console.log(result);
        }
    });

});