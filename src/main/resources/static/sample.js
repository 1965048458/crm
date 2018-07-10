jQuery(document).ready(function () {
   var searchVue = new Vue({
       el: '#searchVue',
       data: {
           keyword: '',
           userList: [],
           errMsg: undefined
       },
       methods: {
           'searchUser': function () {
               var thisVue = this;
               jQuery.ajax({
                   type: 'get',
                   url: '/sample/searchUser',
                   data: {
                       keyword: thisVue.keyword
                   },
                   dataType: 'json',
                   cache: false
               }).done(function (result){
                  if (result.successFlg) {
                      thisVue.$set(thisVue, 'userList', result.userList);
                  } else {
                      thisVue.errMsg = result.errMsg;
                  }
               });
           }
       }
   });
   searchVue.searchUser();
});