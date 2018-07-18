/**
 * Created by Administrator on 2018/7/17.
 */

function filterList(customer) {
    return customer.customerName.indexOf($('#searchInput').val()) != -1;
}

$(function(){

    var searchCustInfoVue = new Vue({
        el:'#searchResult',
        data:{
            searchWord: '',
            errMsg: undefined,
            customerList: []
        },
        methods:{
            'showResult':function () {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    url:'/customer/queryCustomer',
                    data:{
                        searchWord: ''
                    },
                    dataType:'json',
                    cache:false
                }).done(function (result) {
                    if (result.successFlg){
                        thisVue.$set(thisVue, 'customerList', result.customerList);
                    }else {
                        thisVue.errMsg = result.errMsg;
                    }
                })
            }
        }
    });


    var $searchBar = $('#searchBar'),
        $searchResult = $('#searchResult'),
        $searchText = $('#searchText'),
        $searchInput = $('#searchInput'),
        $searchClear = $('#searchClear'),
        $searchCancel = $('#searchCancel');

    function hideSearchResult(){
        $searchResult.hide();
        $searchInput.val('');
    }
    function cancelSearch(){
        hideSearchResult();
        $searchBar.removeClass('weui-search-bar_focusing');
        $searchText.show();
    }

    $searchText.on('click', function(){
        $searchBar.addClass('weui-search-bar_focusing');
        $searchInput.focus();
    });
    $searchInput
        .on('keydown', function () {
            $searchResult.show();
            searchCustInfoVue.showResult();
        })
        .on('blur', function () {
            if(!this.value.length) cancelSearch();
        })
        .on('click', function(){
            $searchResult.show();
            searchCustInfoVue.showResult();
        })
        .bind('keypress', function (event) {
            if (event.keyCode == 13){

                $.ajax({
                    type:'get',
                    url:'/customer/queryCustomer',
                    data:{
                        searchWord: $searchInput.val()
                    },
                    dataType:'json',
                    cache:false
                }).done(function (result) {
                    if (result.successFlg){
                        //$searchResult.customerList = result.customerList;   跳转显示客户信息页面
                    }else {
                        this.errMsg = result.errMsg;
                    }
                })
            }
        });
    $searchClear.on('click', function(){
        hideSearchResult();
        $searchInput.focus();
    });
    $searchCancel.on('click', function(){
        cancelSearch();
        $searchInput.blur();
    });
});
