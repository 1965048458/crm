/**
 * Created by Administrator on 2018/7/17.
 */

var customerList = [
    {
        name: '校长',
        number: '1',
        Warning:{},
        isCircle:"未圈",
        contacts: [{'name': '李某人'}]
    },
    {
        name: '副校长',
        number: '1',
        Warning:{openSeaWarning:'1',
            leftTime:'7天10小时后',
            warnedOrganization:'机械学院',
            createdTime:'2018-01-30',
            times:'1',
            lastTime:'2018-03-01'
                    },
        isCircle:"已圈",
        contacts: [{'name': '李副校长'}]
    },
    {
        name:'书记',
        number:'2',
        Warning:{},
        isCircle:"未圈",
        customerList:[],
        contacts:[{name:'张书记'},
            {name:'赵书记'}]

    },

    {
        name: '机械学院',
        number: '2',
        Warning:{},
        isCircle:"未圈",
        customerList: [
            {
                name: '机械制造及自动化',
                number: '1',
                Warning:{},
                isCircle:"",
                customerList: [],
                contacts: [{'name': '李机械制造及自动化长', 'number': '0'}]
            }
        ],
        contacts: [{'name': '李机械学院长', 'number': '0'}]
    }
];
jQuery(document).ready(function () {

    var organizationVue = new Vue({
        el: '#organizationVue',
        data: {
            showPage:'showCustomerOrganization',
            showOrganization: false,
            showApplyDialog: false,
            customerList: '',
            applyOrganization:'',
            applyReasons:'',
            showSubmitDialog:false,
            warningDetails:{
                openSeaWarning:'',
                leftTime:'',
                warnedOrganization:'',
                createdTime:'',
                times:'',
                lastTime:''
            }
            //data: ''
        },
        methods: {
            'searchOrganizations': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/organization/show',
                    data: {
                        customerId:'customerzju',
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    console.log(result);
                    thisVue.showOrganization = true;
                    thisVue.$set(thisVue, 'customerList', result.customerList);
                });
            },
            'apply':function (name) {
                this.applyOrganization = name;
                this.showApplyDialog=true
            },
            'dialogCheck': function () {
                this.showApplyDialog = false;
                this.showPage = 'showApply';
            },
            'dialogQuit': function () {
                this.showApplyDialog = false;
            },
            'applySubmit':function () {
                var thisVue = this;
                jQuery.ajax({
                    type:'get',
                    url:'/journal/list',
                    data:'',
                    dataType:'json',
                    cache:false
                }).done(function(){
                    thisVue.showSubmitDialog = true;
                });

            },
            'applyQuit':function () {
                this.showPage= 'showCustomerOrganization';
            },
            'submitDialogCheck':function () {
                this.showSubmitDialog = false;
            },
            'openSea2Organization':function () {
                this.showPage = 'showCustomerOrganization';
            },
            'openSeaWarningDetail':function (warning) {

                this.warningDetails.leftTime = warning.leftTime;
                this.warningDetails.warnedOrganization = warning.warnedOrganization;
                this.warningDetails.createdTime = warning.createdTime;
                this.warningDetails.times = warning.times;
                this.warningDetails.lastTime = warning.lastTime;
                this.showPage = 'showOpenSeaWarning';

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
                else if(isCircle == "已圈"){
                    return "["+isCircle+"]";
                }
                else{
                    return "";
                }
            },
            'addOpenSeaWarning':function (Warning) {
                if(Warning.openSeaWarning == '1'){
                    return "!!即将进入公海";
                }
                else {
                    return '';
                }
            },
            'apply':function (name) {
                organizationVue.apply(name);
                console.log(name);
            },
            'openSeaWarning':function (warning) {
                console.log("component.warning")
                organizationVue.openSeaWarningDetail(warning);
            }


        }
    });
});
