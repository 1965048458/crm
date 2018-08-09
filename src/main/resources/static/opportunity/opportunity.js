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
            sceneValue: 'all',
            dateValue: 'all',
            dateValueStart: '',
            dateValueEnd: '',
            creatorValue: 'all',
            creatorV: '',
            stageValue: 'all',
            customerValue: 'all',
            customerValueIn: '',

            titleBar: true,
            searchCustomer: true,
            customers: true,
            searchWord: '',
            errMsg: undefined,
            customerList: []
        },
        methods: {
            'showResult': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/customer/queryCustomer',
                    data: {
                        searchWord: thisVue.searchWord,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'customerList', result.customerList);
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                })
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
            'add':function () {
                window.location = "/opportunity/newSale";
            },
            'filter': function () {
                if (this.imgSort == '/images/opportunity/排序已选中.svg') {
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/排序未选中.svg";

                }
                if (this.imgFilter == '/images/opportunity/筛选未选中.svg') {
                    this.showFilterPage = true;
                    this.filterCondition = 'scene';
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
            'dateChecked': function () {
                this.dateValueStart = '';
                this.dateValueEnd = '';
            },
            'customerChecked': function () {
                this.customerValueIn = '';
            },
            'finish': function () {
                this.showFilterPage = false;
                this.filterCondition = '';
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
                } else {
                    this.creatorValue = 'all';
                }
            },
        }
    });

});
