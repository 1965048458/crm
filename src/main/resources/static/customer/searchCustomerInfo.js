/**
 * Created by Administrator on 2018/7/17.
 */
var custList = ['浙江大学',
    '浙江理工大学','浙江工商大学'];


$(function(){
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
        .on('blur', function () {
            if(!this.value.length) cancelSearch();
        })
        .on('input', function(){
            if(this.value.length) {

                $searchResult.show();
                var searchCustInfoVue = new Vue({
                    el:'#searchResult',
                    data:{
                        custList: custList
                    },
                    methods:{
                        'searchResult':function (data) {
                            var thisVue = this;
                            // $.ajax({
                            //     type:'get',
                            //     url:'',
                            //     data:data,
                            //     cache:false
                            // }).done(function (result) {
                            thisVue.$set(thisVue, 'custList', custList);
                            //})
                        }
                    }
                });
                searchCustInfoVue.searchResult();
            } else {
                $searchResult.hide();
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
