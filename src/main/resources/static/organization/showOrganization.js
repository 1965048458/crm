/**
 * Created by Administrator on 2018/7/17.
 */

var customerList = [
    {
        'name': '校长',
        'number': '1',
        isCircle:"未圈",
        contacts: [{'name': '李某人'}]
    },
    {
        'name': '副校长',
        'number': '1',
        contacts: [{'name': '李副校长'}]
    },
    {
        name:'书记',
        number:'2',
        customerList:[],
        contacts:[{name:'张书记'},
            {name:'赵书记'}]

    },

    {
        'name': '机械学院',
        'number': '2',
        'customerList': [
            {
                'name': '机械制造及自动化',
                'number': '1',
                'customerList': [],
                contacts: [{'name': '李机械制造及自动化长', 'number': '0'}]
            }
        ],
        contacts: [{'name': '李机械学院长', 'number': '0'}]
    }
];
jQuery(document).ready(function () {
    new Vue({
        el: '#organizationVue',
        data: {
            showCustomerOrganization:true,
            showOrganization: false,
            showApplyDialog: false,
            showApply: false,
            customerList: ''
            //data: ''
        },
        methods: {
            'searchOrganizations': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/list',
                    data: '',
                    dataType: 'json',
                    cache: false
                }).done(function () {
                    thisVue.showOrganization = true;
                    thisVue.$set(thisVue, 'customerList', customerList);
                });
            },
            'apply':function () {
                this.showApplyDialog=true
            },
            'dialogCheck': function () {
                this.showApplyDialog = false;
                this.showCustomerOrganization = false;
                this.showApply = true
            },
            'dialogQuit': function () {
                this.showApplyDialog = false;
            },
            'applySubmit':function () {
                this.showApply=false;
                this.showCustomerOrganization=true;
            },
            'applyQuit':function () {
                this.showApply=false;
                this.showCustomerOrganization=true;
            }
        }
    });
    Vue.component('customer', {
        template: '#customer',
        props: ['customer'],
        data: function () {
            return {
                showSub: false,
                showCustomerOrganization:true,
                showOrganization: false,
                showApplyDialog: false,
                showApply: false,
            };
        },
        methods: {
            'changeSubFold' : function () {
                this.showSub = !this.showSub;
            },
            'addBrackets':function (number) {
                if (number == "0") {
                    return ""
                }
                else {
                    return " ( " + number + " )";
                }
            },
            'addSquareBrackets':function (isCircle) {
                if (isCircle == "未圈"){
                    return "["+isCircle+"]";
                }
                else{
                    return "";
                }
            },
            'apply':function (name) {
                this.showApplyDialog = true;
                console.log(name);
            },

        }
    });
});
