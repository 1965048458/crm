/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var opportunityVue = new Vue({
        el: '#opportunityVue',
        data: {
            imgSort: '/images/opportunity/排序未选中.svg',
            imgFilter: '/images/opportunity/筛选未选中.svg',
            showSortPage: false,
            showFilterPage: false,
            showPage: '',
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

            titleBar: true,
            searchCustomer: true,
            customers: true,
            searchWord: '',
            errMsg: undefined,

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
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                })
            },
            'imgSrc' : function(data){
                if(data =='A阶段'){
                    return '/images/opportunity/Astage.svg';
                }else if(data =='B阶段'){
                    return '/images/opportunity/Bstage.svg';
                }else if(data =='C阶段'){
                    return '/images/opportunity/Cstage.svg';
                }else if(data =='D阶段'){
                    return '/images/opportunity/Dstage.svg';
                }else if(data =='输单'){
                    return '/images/opportunity/loseOrder.svg';
                }
            },
            'add':function () {
                window.location = "/opportunity/newSale";
            },
            'sort': function () {
                if (this.imgFilter == '/images/opportunity/筛选已选中.svg') {
                    this.showFilterPage = false;
                    this.filterCondition = '';
                }
                if (this.imgSort == '/images/opportunity/排序未选中.svg') {
                    this.showSortPage = true;
                    this.imgSort = "/images/opportunity/排序已选中.svg";
                    this.imgFilter = "/images/opportunity/筛选未选中.svg";
                } else {
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/排序未选中.svg";
                }
            },
            'sortAsc': function(){
                var creator ='';
                if(this.creatorV ==''){
                    creator = this.creatorValue;
                }else{
                    creator = this.creatorV;
                }
                var data = {
                    sortMode:'ASC',
                    userId: creator,
                    customerName:this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                this.showResult(data);
            },
            'sortDesc': function(){
                var creator ='';
                if(this.creatorV ==''){
                    creator = this.creatorValue;
                }else{
                    creator = this.creatorV;
                }
                var data = {
                    sortMode:'DESC',
                    userId: creator,
                    customerName:this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                console.log(data);
                this.showResult(data);
            },
            'filter': function () {
                if (this.imgSort == '/images/opportunity/排序已选中.svg') {
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/排序未选中.svg";

                }
                if (this.imgFilter == '/images/opportunity/筛选未选中.svg') {
                    this.showFilterPage = true;
                    this.filterCondition = 'creator';
                    this.imgFilter = "/images/opportunity/筛选已选中.svg";
                    this.imgSort = "/images/opportunity/排序未选中.svg";
                } else {
                    this.showFilterPage = false;
                    this.filterCondition = '';
                    this.imgFilter = "/images/opportunity/筛选未选中.svg";
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
                this.imgSort = "/images/opportunity/排序未选中.svg";
                this.imgFilter = "/images/opportunity/筛选未选中.svg";
            },
            'removeChecked': function () {
                this.dateValue = '';
            },
            'removeChecked1': function () {
                this.creatorValue = '';
                this.creatorV = '汪峰';
            },
            'creatorChecked': function () {
                this.creatorV = '';
            },
            'creatorChecked1': function () {
                this.creatorV = '';
            },
            'creatorChecked2': function () {
                this.creatorV = '';
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
                this.imgFilter ='/images/opportunity/筛选未选中.svg';
                this.filterCondition = '';
                var creator ='';
                if(this.creatorV ==''){
                    creator = this.creatorValue;
                }else{
                    creator = this.creatorV;
                }
                var data = {
                    sortMode:this.sortMode,
                    userId: creator,
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
            }
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
                //  else {
                //     this.creatorValue = 'all';
                // }
            },
        }
    });
    opportunityVue.showResult();
});
