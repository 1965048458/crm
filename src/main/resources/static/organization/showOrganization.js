/**
 * Created by Administrator on 2018/7/17.
 */

jQuery(document).ready(function () {

    var organizationVue = new Vue({
        el: '#organizationVue',
        data: {
            showPage:'showCustomerOrganization',
            showOrganization: false,
            showApplyDialog: false,
            customerList: '',
            departmentList:'',
            applyDeptName:'',
            applyDeptId:'',
            applyReasons:'',
            showSubmitDialog:false,
            showSubmitErrDialog:false,
            showSearchResult:false,
            submitReasons:'',
            warningDetails:{
                openSeaWarning:'',
                leftTime:'',
                warnedOrganization:'',
                createdTime:'',
                times:'',
                lastTime:''
            },
            deptList:'',
            errMsg:'',
            searchList:[],
            searchWord:'',

            //data: ''
        },
        methods: {
            'searchOrganizations': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/organization/show',
                    data: {
                        customerId:'customerzju'
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    console.log(result);
                    if (result.successFlg) {
                        thisVue.showOrganization = true;
                        thisVue.$set(thisVue, 'customerList',result.customerList);
                        thisVue.$set(thisVue, 'searchList', result.searchList)
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }

                });
            },
            'apply':function (name,id) {
                this.applyDeptName = name;
                this.applyDeptId = id;
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
                    type:'post',
                    url:'/customer/organization/apply',
                    data:{
                        submitReasons:this.submitReasons,
                        //applyDeptName:this.applyDeptName,
                        applyDeptId:this.applyDeptId,
                    },
                    dataType:'json',
                    cache:false
                }).done(function(result){
                    console.log(result);
                    if (result.successFlg) {
                        thisVue.showSubmitDialog = true;
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showSubmitErrDialog = true;
                    }

                });

            },
            'applyQuit':function () {
                this.submitReasons='',
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

            },
            search:function () {
                console.log(this.searchWord);
                this.showOrganization = true;
                this.cancelSearch();
            },
            text:function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchText').focus();
                $('#searchResult').show();
                this.showOrganization = false;
            },
            filterList:function (searchItem) {
                return searchItem.realName.indexOf(this.searchWord) != -1;
            },
            hideSearchResult:function () {
                $('#searchResult').hide();
                this.searchWord = "";
            },
            cancelSearch:function () {
                this.hideSearchResult();
                $('#searchBar').removeClass('weui-search-bar_focusing');
                $('#searchText').show();

            },
            clear:function () {
                this.searchWord="";
                $('#searchInput').focus();
            },
            cancel:function () {
                this.cancelSearch();
                this.showOrganization = true;
                $('#searchInput').blur();
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
            'addNumBrackets':function (number) {
                return "("+number+")";
            },
            'addMineBrackets':function (status) {
                if (status == 'MINE'){
                    return "[我的]"
                }
            },
            'addEnclosureBrackets':function (status) {
                if(status == 'ENCLOSURE'){
                    return "[已圈]"
                }
            },
            'addNormalBrackets':function (status) {
                if(status == 'NORMAL'){
                    return "[未圈]"
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
            'apply':function (name,id) {
                organizationVue.apply(name,id);
                console.log(name,id);
            },
            'openSeaWarning':function (warning) {
                console.log("component.warning")
                organizationVue.openSeaWarningDetail(warning);
            }


        }
    });

    organizationVue.searchOrganizations();
});
