/**
 * Created by Administrator on 2018/7/17.
 */

function filterList(customer) {
    return customer.customerName.indexOf($('#searchInput').val()) != -1;
}

$(document).ready(function () {

    var searchCustInfoVue = new Vue({
        el: '#customerVue',
        data: {
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
                })
            },
            'submit': function (e) {
                var keyCode = window.event? e.keyCode:e.which;

                if(keyCode == 13 ) {  //&& this.input
                    this.showResult();
                    if (this.customerList.length != 0){
                        this.customers = true;
                        cancelSearch();
                    }else{
                        this.searchCustomer = false;
                    }
                    this.titleBar = true;
                    alert("enter事件触发");
                }
            },
            'loadDetail':function (customerId) {
                //
            }
        }
    });
    searchCustInfoVue.showResult();

    var $searchBar = $('#searchBar'),
        $searchResult = $('#searchResult'),
        $searchText = $('#searchText'),
        $searchInput = $('#searchInput'),
        $searchClear = $('#searchClear'),
        $searchCancel = $('#searchCancel');

    function hideSearchResult() {
        $searchResult.hide();
        $searchInput.val('');
    }

    function cancelSearch() {
        hideSearchResult();
        $searchBar.removeClass('weui-search-bar_focusing');
        $searchText.show();
    }

    $searchText.on('click', function () {
        $searchBar.addClass('weui-search-bar_focusing');
        $searchInput.focus();
        $searchResult.show();
        searchCustInfoVue.titleBar = false;
        searchCustInfoVue.customers = false;
    });
    $searchClear.on('click', function () {
        hideSearchResult();
        $searchInput.focus();
    });
    $searchCancel.on('click', function () {
        cancelSearch();
        searchCustInfoVue.titleBar = true;
        searchCustInfoVue.customers = true;
        $searchInput.blur();
    });
});
