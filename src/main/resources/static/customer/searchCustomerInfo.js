/**
 * Created by Administrator on 2018/7/17.
 */

$(document).ready(function () {

    var searchCustInfoVue = new Vue({
        el: '#customerVue',
        data: {
            searchCustomer: true,
            customers: true,
            titleBar: true,
            blank: false,
            searchWord: '',
            errMsg: undefined,
            customerList: [],
            myCustomers: []
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
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                });
            },
            'backToInfo': function () {
                //this.customers = true;
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchText').focus();
                $('#searchResult').show();
                this.customers = false;
                this.titleBar = false;
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
                this.titleBar = true;
                $('#searchInput').blur();
            },
            'loadDetail': function (customer) {
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                //var temp = this;
                $loadingToast.fadeIn(100);
                setTimeout(function () {
                    $loadingToast.fadeOut(100);
                    //temp.showPage = 'invite';
                    window.location.href = "/customer/customerInfo?customerId=" + customer.customerId + "&customerName=" + customer.customerName;
                }, 500);
            }
        },
        watch: {
            'customerList': function () {
                //
            },
            'myCustomers': function () {
                //
            }
        }
    });
    searchCustInfoVue.showResult();

});
