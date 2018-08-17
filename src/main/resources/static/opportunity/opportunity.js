/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var opportunityVue = new Vue({
        el: '#opportunityVue',
        data: {
            imgSort: '/images/opportunity/sortUnchecked.svg',
            imgFilter: '/images/opportunity/filterUnchecked.svg',
            showSortPage: false,
            showFilterPage: false,
            showPage: 'opportunity',
            filterCondition: '',
            filterValue: '',
            sortMode:'ASC',
            sceneValue: 'all',
            dateValue: 'all',
            dateValueStart: '',
            dateValueEnd: '',
            creatorValue: 'all',
            creatorV: '',
            stageValue: 'all',
            customerValue: 'all',
            customerValueIn: '',
            opportunityList:'',
            subMemberList: [],
            tempSub:[],
            subUserId:[],
            subsName:[],
            subUser:'',

            titleBar: true,
            searchCustomer: true,
            customers: true,
            searchWord: '',
            errMsg: undefined,

            showNull:'',

        },
        methods: {
            'showResult': function (data) {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/queryOpportunity',
                    data:data,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'opportunityList', result.opportunityList);
                        if(result.opportunityList == null || result.opportunityList ==''){
                            thisVue.showNull = 'null';
                        }else{
                            thisVue.showNull ='';
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                        alert(thisVue.errMsg);
                    }
                })
            },
            'imgSrc' : function(data){
                if(data =='A阶段'){
                    return '/images/opportunity/AStage.svg';
                }else if(data =='B阶段'){
                    return '/images/opportunity/BStage.svg';
                }else if(data =='C阶段'){
                    return '/images/opportunity/CStage.svg';
                }else if(data =='D阶段'){
                    return '/images/opportunity/DStage.svg';
                }else if(data =='输单'){
                    return '/images/opportunity/loseOrder.svg';
                }
            },
            'add':function () {
                window.location = "/opportunity/newSale";
            },
            'sort': function () {
                if (this.imgFilter == '/images/opportunity/filterChecked.svg') {
                    this.showFilterPage = false;
                    this.filterCondition = '';
                }
                if (this.imgSort == '/images/opportunity/sortUnchecked.svg') {
                    this.showSortPage = true;
                    this.imgSort = "/images/opportunity/sortChecked.svg";
                    this.imgFilter = "/images/opportunity/filterUnchecked.svg";
                } else {
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/sortUnchecked.svg";
                }
            },
            'sortAsc': function(){
                var data = {
                    sortMode:'ASC',
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName:this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                this.showResult(data);
            },
            'sortDesc': function(){
                var data = {
                    sortMode:'DESC',
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName:this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                console.log(data);
                this.showResult(data);
            },
            'filter': function () {
                if (this.imgSort == '/images/opportunity/sortChecked.svg') {
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/sortUnchecked.svg";

                }
                if (this.imgFilter == '/images/opportunity/filterUnchecked.svg') {
                    this.showFilterPage = true;
                    this.filterCondition = 'creator';
                    this.imgFilter = "/images/opportunity/filterChecked.svg";
                    this.imgSort = "/images/opportunity/sortUnchecked.svg";
                } else {
                    this.showFilterPage = false;
                    this.filterCondition = '';
                    this.imgFilter = "/images/opportunity/filterUnchecked.svg";
                }
            },
            'filterScene': function () {
                this.filterCondition = 'scene';
            },
            'filterDate': function () {
                this.filterCondition = 'date';
            },
            'filterCreator': function () {
                this.filterCondition = 'creator';
            },
            'filterStage': function () {
                this.filterCondition = 'stage';
            },
            'filterCustomer': function () {
                this.filterCondition = 'customer';
            },
            'cancelMask': function () {
                this.showSortPage = false;
                this.showFilterPage = false;
                this.filterCondition = '';
                this.imgSort = "/images/opportunity/sortUnchecked.svg";
                this.imgFilter = "/images/opportunity/filterUnchecked.svg";
            },
            'removeChecked': function () {
                this.dateValue = '';
            },
            'removeChecked1': function () {
                this.creatorValue = '';
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/subMemberList',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'subMemberList', result.subMemberList);
                        thisVue.showPage = 'selectCreator';
                    }
                });

                //this.creatorV = '汪峰';
            },
            'creatorChecked': function () {
                this.creatorV = '';
                this.subUser = '';
                this.tempSub =[];
            },
            'creatorChecked1': function () {
                this.creatorV = '';
                this.subUser ='';
                this.tempSub =[];
            },
            'creatorChecked2': function () {
                this.creatorV = '';
                this.subUser = '';
                this.tempSub =[];
            },
            'dateChecked': function () {
                this.dateValueStart = '';
                this.dateValueEnd = '';
            },
            'customerChecked': function () {
                this.customerValueIn = '';
            },
            'finish': function () {
                this.showFilterPage = false;
                this.imgFilter ='/images/opportunity/filterUnchecked.svg';
                this.filterCondition = '';
                var data = {
                    sortMode:this.sortMode,
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName:this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                console.log(data);
                this.showResult(data);
            },
            'reset': function () {
                this.sceneValue = 'all';
                this.dateValue = 'all';
                this.creatorValue = 'all';
                this.stageValue = 'all';
                this.customerValue = 'all';
                this.dateValueStart = '';
                this.dateValueEnd = '';
                this.creatorV = '';
                this.customerValueIn='';
                this.subUser ='';
                this.tempSub =[];
            },
            'backToFilter': function(){
                this.showPage ='opportunity';
                this.tempSub =[];
            },
            'submit': function(){
                this.subUserId = [];
                this.subsName = [];

                for (var i = 0; i < this.tempSub.length; i++) {
                    var str = this.tempSub[i].split(',');
                    this.subUserId[i] = str[0];
                    this.subsName[i] = str[1];
                }
                var tempId = '';
                for(var i = 0; i < this.subUserId.length; i++){
                    tempId += this.subUserId[i] + ',';
                }
                this.subUser = tempId;
                this.creatorV= this.subsName;
                this.showPage ='opportunity';
            },
            'detail': function(data){
                var thisVue = this;
                $.ajax({
                    type: 'post',
                    url: '/opportunity/opportunityToDetail',
                    data:{
                        opportunityId:data,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        window.location = "opportunity/detail";
                    }
                })

            },
        },
        watch: {
            'filterCondition': function () {
                $("#" + this.filterValue).css('background-color', '#FFFFFF');
                $("#" + this.filterCondition).css('background-color', 'gainsboro');
                this.filterValue = this.filterCondition;
            },
            'customerValueIn': function () {
                if (this.customerValueIn != '') {
                    this.customerValue = '';
                } else {
                    this.customerValue = 'all';
                }
            },
            'creatorV': function () {
                if (this.creatorV != '') {
                    this.creatorValue = '';
                }
            },
        }
    });
    opportunityVue.showResult();
});
