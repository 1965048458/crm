/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var opportunityVue = new Vue({
        el: '#opportunityVue',
        data: {
            imgSort:'/images/opportunity/排序未选中.svg',
            imgFilter:'/images/opportunity/筛选未选中.svg',
            showSortPage:false,
            showFilterPage:false,
            showPage:'',
            filterCondition:'',

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
                if(this.imgFilter == '/images/opportunity/筛选已选中.svg'){
                    this.showFilterPage = false;
                    this.filterCondition = '';
                }
                if(this.imgSort == '/images/opportunity/排序未选中.svg'){
                    this.showSortPage = true;
                    this.imgSort = "/images/opportunity/排序已选中.svg";
                    this.imgFilter = "/images/opportunity/筛选未选中.svg";
                }else{
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/排序未选中.svg";
                }
            },
            'filter': function () {
                if(this.imgSort == '/images/opportunity/排序已选中.svg'){
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/排序未选中.svg";

                }
                if(this.imgFilter == '/images/opportunity/筛选未选中.svg'){
                    this.showFilterPage = true;
                    this.filterCondition = 'scene';
                    this.imgFilter = "/images/opportunity/筛选已选中.svg";
                    this.imgSort = "/images/opportunity/排序未选中.svg";
                }else{
                    this.showFilterPage = false;
                    this.filterCondition = '';
                    this.imgFilter = "/images/opportunity/筛选未选中.svg";
                }
            },
            'cancelMask': function () {
                this.showSortPage = false;
                this.showFilterPage=false;
                this.filterCondition = '';
                this.imgSort = "/images/opportunity/排序未选中.svg";
                this.imgFilter = "/images/opportunity/筛选未选中.svg";
            },
            'filterSelect': function () {

            },
            'cancelSearch': function () {
                this.hideSearchResult();
                $('#searchBar').removeClass('weui-search-bar_focusing');
                $('#searchText').show();
            },
            'clear': function () {
                this.searchWord = "";
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.cancelSearch();
                this.titleBar = true;
                this.customers = true;
                $('#searchInput').blur();
            },
            'loadDetail': function (customerId) {
                //
            }
        }
    });
    opportunityVue.showResult();
});
