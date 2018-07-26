/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var searchCustInfoVue = new Vue({
        el: '#customerVue',
        data: {
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
                        searchWord: thisVue.searchWord
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'customerList', result.customerList);
                        thisVue.customerList = result.customerList;
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                })
            },
            'search': function () {
                this.showResult();//this.customers = true;   逻辑待修改
                window.location.href = "/customer/customerInfo?customerName=" + this.searchWord;
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchText').focus();
                $('#searchResult').show();
                this.customers = false;
            },
            'filterList': function (customer) {
                return customer.customerName.indexOf(this.searchWord) != -1;
            },
            'hideSearchResult': function () {
                $('#searchResult').hide();
                this.searchWord = "";
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
                this.customers = true;
                $('#searchInput').blur();
            },
            'loadDetail': function (customerName) {
                window.location.href = "/customer/customerInfo?customerName=" +customerName;
            }
        }
    });
    searchCustInfoVue.showResult();
});
