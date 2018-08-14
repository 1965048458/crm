jQuery(document).ready(function () {
    var administrator = new Vue({
        el: '#administrator',
        data: {
            companyId:'',
        },
        methods: {
            'init': function () {
                var thisVue = this;
                thisVue.companyId = $("#companyId").val();
            },
            'accountSecurity': function () {
                window.location.href = "/accountSecurity";
            },
            'back': function () {
                window.location.href = "/myCompany";
            },
            'staffAudit': function () {
                window.location.href = "/staffAudit";
            },
            'enter': function () {
                window.location = '/journal/toList';
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
        },
    });
    administrator.init();
})
