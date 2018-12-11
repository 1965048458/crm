/**
 * Created by Administrator on 2018/7/17.
 */

jQuery(document).ready(function () {

    var organizationVue = new Vue({
        el: '#organizationVue',
        data:{
            customerId:'',
            customerName:'',
            showPage:'showCustomerOrganization',
            showOrganization: false,
            showApplyDialog: false,
            customerList: '',
            scustomerList: '',
            departmentList:'',
            applyDeptName:'',
            applyDeptId:'',
            applyReasons:'',
            delayApplyReasons:'',
            showSubmitDialog:false,
            showSubmitErrDialog:false,
            showSearchResult:false,
            showDelayApplyDialog:false,
            showDelayApplyErrDialog:false,
            submitReasons:'',
            warningDetails:{
                deptId:'',
                leftDays:'',
                leftHours:'',
                warnedOrganization:'',
                createdTime:'',
                times:'',
                lastTime:'',
                isApplied:false
            },
            deptList:'',
            errMsg:'',
            searchList:[],
            searchWord:'',
        },
        methods: {
            'init':function (customerId, customerName) {
                this.customerId=customerId;
                this.customerName=customerName;
            },
            'toCustomerList':function () {
                window.location='/customer/customerList';
            },
            'toCustomerInfo':function () {
                window.location='/customer/customerInfo?customerId='+this.customerId;
            },
            'searchOrganizations': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/organization/show',
                    data: {
                        customerId:this.customerId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.showPage='showCustomerOrganization';
                        thisVue.showOrganization = true;
                        thisVue.$set(thisVue, 'customerList',result.customerList);
                        thisVue.$set(thisVue, 'searchList', result.searchList)
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'toModifyPage': function () {
                window.location = '/customer/editCustomer?customerId='+jQuery('#customerId').val();
            },
            'switch2CustomerInfoPage':function () {
                $('#customerInfoBtn').attr("class","weui-navbar__item weui-bar__item_on");
                $('#organizationInfoBtn').attr("class","weui-navbar__item");
                this.showPage='showCustomerInfoPage';
            },
            'switch2OrganizationInfoPage':function () {
                $('#organizationInfoBtn').attr("class","weui-navbar__item weui-bar__item_on");
                $('#customerInfoBtn').attr("class","weui-navbar__item");
                // this.searchOrganizations(this.customer.customerId);
                this.showPage='showCustomerOrganization';
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
                    type:'get',
                    url:'/customer/organization/apply',
                    data:{
                        applyReasons:this.submitReasons,
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
                this.submitReasons='';
                this.showPage= 'showCustomerOrganization';
            },
            'submitDialogCheck':function () {
                this.showSubmitDialog = false;
                this.submitReasons = '';
                this.searchOrganizations();
            },
            'openSea2Organization':function () {
                this.delayApplyReasons='';
                this.showPage = 'showCustomerOrganization';
            },
            'openSeaWarningDetail':function (warning) {
                this.warningDetails.deptId = warning.deptId;
                this.warningDetails.leftDays = warning.leftDays;
                this.warningDetails.leftHours = warning.leftHours;
                this.warningDetails.warnedOrganization = warning.deptName;
                this.warningDetails.createdTime = warning.createdTime;
                this.warningDetails.lastTime = warning.lastTimeFollow;
                this.warningDetails.isApplied = warning.isDelayApplied;
                this.showPage = 'showOpenSeaWarning';

            },
            'enclosureDelayApply':function(deptId, deptName){
                var thisVue = this;
                jQuery.ajax({
                    type:'post',
                    url:'/customer/organization/delayApply',
                    data:{
                        deptId:deptId,
                        delayApplyReasons:thisVue.delayApplyReasons
                    },
                    dataType:'json',
                    cache:false
                }).done(function(result){
                    console.log(result);
                    if(result.successFlg){
                        thisVue.showDelayApplyDialog = true;
                        //console.log(thisVue.showDelayApplyErrDialog)
                    }else{
                        thisVue.errMsg = result.errMsg;
                        thisVue.showDelayApplyErrDialog = true;
                    }
                });
            },
            'delayApplyDialogCheck':function () {
                this.showDelayApplyDialog=false;
                this.searchOrganizations();
                this.delayApplyReasons='';
            },
            'delayApplyErrDialogCheck':function () {
                this.showDelayApplyErrDialog = false;
                this.showPage = 'showCustomerOrganization';
            },

            /**
             * 搜索
             * */
            search:function () {
                console.log(this.searchWord);
                var target  = "#" + this.searchWord;
                var offset = $(target).offset().top - jQuery(document).scrollTop();
                window.scrollBy(0, offset);
                console.log(offset);
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
                return searchItem.indexOf(this.searchWord) != -1;
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
            },
            
            searchCompany: function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/searchCompany',
                    data: {
                    	searchWord:this.searchWord,
                    	customerId:this.customerId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        if (thisVue.name == '') {
                            thisVue.$set(thisVue, 'scustomerList', '');
                        } else {
                            thisVue.$set(thisVue, 'scustomerList', result.scustomerList);
                        }
                    }
                });

            },
            /*
            searchDone:function (searchItem) {
                this.clear();
                this.cancel();
                this.cancelSearch();
                this.hideSearchResult();
                console.log(searchItem);
            }*/
        },
        watch: {
            'searchWord': function () {
                var thisVue = this;
                this.searchCompany();
                if (thisVue.name == '') {
                    thisVue.$set(thisVue, 'companyList', '');
                }
            }
        }
    });
    Vue.component('bbb', {
        template: '#customerB3',
        props: ['bbb'],
        data: function () {
            return {
                showSub: true,
                showCustomerOrganization:true,
                showOrganization: false,
                showApplyDialog: false,
                showApply: false,
                imgPath:"/images/customer/unfold.svg",

            };
        },
        methods: {

            'changeSubFold' : function (status) {
                if(status == 'MINE'|| (status == '' || status == undefined)){

                    this.showSub=!this.showSub;
                    this.setImgPath();
                } else {
                    this.showSub = false;
                    this.setImgPath();
                }

            },
            'setImgPath':function () {
                if(this.showSub == false){
                    this.imgPath = "/images/customer/fold.svg";
                }else {
                    this.imgPath = "/images/customer/unfold.svg";
                }
            },
            'checkGender':function(gender){
                if(gender == 'FEMALE'){
                    return "/images/customer/FEMALE.svg";
                }else{
                    return "/images/customer/MALE.svg";
                }
            },
            'addNumBrackets':function (number,status) {
                if(status == 'ENCLOSURE' || number == '0'){
                    return '';
                }else{
                    return "( "+number+" )";
                }
            },
            'addMineBrackets':function (status,applyName) {
                if (status == 'MINE' ){
                    if(applyName == '' || applyName == undefined){
                        return "[ 我的 ]"
                    }else {
                        return "[ "+ applyName + "]"
                    }

                }
            },
            'addEnclosureBrackets':function (status) {
                if(status == 'ENCLOSURE'){
                    return "[ 别人正在申请 ]"
                }
            },
            'addNormalBrackets':function (status) {
                if(status == 'NORMAL'){
                    return "[ 未圈 ]"
                }
            },
            'addApplyingBrackets':function (status,applyName) {
                if(status == 'APPLYING'){
                    if(applyName == '' || applyName == undefined){
                        return "[ 待审核 ]";
                    }else {
                        return "["+applyName+"] [ 待审核 ]";
                    }

                }
            },
            'addOpenSeaWarning':function (warning) {
                if(warning != null){
                    if(warning.isDelayApplied == true){
                        return "!!已申请延期";
                    }else {
                        return "!!即将进入公海";
                    }
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
                
            },
            'toContactDetail':function (contactsId) {
                window.location = '/customer/contactsInfo?contactsId='+contactsId;
            },
            'addTypeName':function (contact) {
                if(contact.typeName == undefined || contact.typeName==''){
                    $("#type_name_label_"+contact.contactsId).css("border","hidden");
                    return '';
                }else {
                    return contact.typeName;
                }
                
            }
        }
    });
    Vue.component('customer', {
        template: '#customer3',
        props: ['customer2'],
        data: function () {
            return {
                showSub: false,
                showCustomerOrganization:true,
                showOrganization: false,
                showApplyDialog: false,
                showApply: false,
                imgPath:"/images/customer/fold.svg",

            };
        },
        methods: {

            'changeSubFold' : function (status) {
                if(status == 'MINE'|| (status == '' || status == undefined)){

                   this.showSub= !this.showSub;
                    
                    this.setImgPath();
                } else {
                    this.showSub = false;
                    this.setImgPath();
                }

            },
            'setImgPath':function () {
                if(this.showSub == false){
                    this.imgPath = "/images/customer/fold.svg";
                }else {
                    this.imgPath = "/images/customer/unfold.svg";
                }
            },
            'checkGender':function(gender){
                if(gender == 'FEMALE'){
                    return "/images/customer/FEMALE.svg";
                }else{
                    return "/images/customer/MALE.svg";
                }
            },
            'addNumBrackets':function (number,status) {
                if(status == 'ENCLOSURE' || number == '0'){
                    return '';
                }else{
                    return "( "+number+" )";
                }
            },
            'addMineBrackets':function (status,applyName) {
                if (status == 'MINE' ){
                    if(applyName == '' || applyName == undefined){
                        return "[ 我的 ]"
                    }else {
                        return "[ "+ applyName + "]"
                    }

                }
            },
            'addEnclosureBrackets':function (status) {
                if(status == 'ENCLOSURE'){
                    return "[ 别人正在申请 ]"
                }
            },
            'addNormalBrackets':function (status) {
                if(status == 'NORMAL'){
                    return "[ 未圈 ]"
                }
            },
            'addApplyingBrackets':function (status,applyName) {
                if(status == 'APPLYING'){
                    if(applyName == '' || applyName == undefined){
                        return "[ 待审核 ]";
                    }else {
                        return "["+applyName+"] [ 待审核 ]";
                    }

                }
            },
            'addOpenSeaWarning':function (warning) {
                if(warning != null){
                    if(warning.isDelayApplied == true){
                        return "!!已申请延期";
                    }else {
                        return "!!即将进入公海";
                    }
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
                
            },
            'toContactDetail':function (contactsId) {
                window.location = '/customer/contactsInfo?contactsId='+contactsId;
            },
            'addTypeName':function (contact) {
                if(contact.typeName == undefined || contact.typeName==''){
                    $("#type_name_label_"+contact.contactsId).css("border","hidden");
                    return '';
                }else {
                    return contact.typeName;
                }
                
            }
        }
    });
    var customerId = $("#customerId").val();
    var customerName = $("#customerName").val();
    organizationVue.init(customerId,customerName);
    organizationVue.searchOrganizations();
});
