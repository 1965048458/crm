jQuery(document).ready(function () {
    var addCustomerVue = new Vue({
        el: '#addCustomerVue',
        data: {
            showPage: 'addCustomer',
            schoolType: '',
            name:'',
            profile:'',
            website:'',
            schList: [],
        },
        methods: {
            'selectSch':function () {
                this.showPage = 'selSchType';
            },
            'selectNam':function () {
                this.showPage = 'selName';
                this.searchSchool();
            },
            'cancel':function () {
                this.showPage = 'addCustomer';
                this.name ='';
            },
            'submit':function () {
                if(this.name ==''){
                    alert("请填写学校名称！");
                }else{
                    this.showPage = 'addCustomer';
                };
            },
            'subMit':function () {
                if(this.name ==''|| this.schoolType ==''){
                    alert("请填写学校类型或学校名称！");
                }else{
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/customer/new',
                        data: {
                            schoolType:thisVue.schoolType,
                            name:thisVue.name,
                            profile:thisVue.profile,
                            website:thisVue.website,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result){
                        if (result.exist) {
                            alert("用户已经创建，请勿重复创建");
                        }else if(result.successFlg){
                            window.location.href="/customer/customerList";
                        } else {
                            alert("请填写正确的信息");
                        }
                    });
                };
            },
            'selectType':function () {
                this.showPage = 'addCustomer';
            },
            'selectSchool':function () {
                this.showPage = 'addCustomer';
            },
            'selectName':function () {
                this.showPage = 'addCustomer';
            },
            'searchSchool': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/searchSchool',
                    data: {
                        keyword:thisVue.name,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'schList', result.schList);
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                });
            },
        },
        watch: {
            'name': function () {
                this.searchSchool();
            }
        }
    });
});