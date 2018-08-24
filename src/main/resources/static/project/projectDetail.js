
jQuery(document).ready(function () {

   var projDetailVue = new Vue({
       el: '#projDetailVue',
       data: {
            showPage: 'detailPage',
       },
       methods: {
           'back':function () {
               window.location.href = '/project/projectList';
           },
           'clearStyle': function () {
               $('#detailBox').attr("class", "weui-grid navi-bar my-weui-grid");
               $('#detail').attr("class", "weui-grid__label");
               $('#relevantBox').attr("class", "weui-grid navi-bar my-weui-grid");
               $('#relevant').attr("class", "weui-grid__label");
               $('#modifBox').attr("class", "weui-grid navi-bar my-weui-grid");
               $('#modif').attr("class", "weui-grid__label");
           },
           'toDetailPage': function () {
               this.showPage = 'detailPage';
               this.clearStyle();
               $('#detailBox').attr("class", "weui-grid navi-bar my-weui-grid weui-grid-select");
               $('#detail').attr("class", "weui-grid__label weui-grid-select-content");
           },
           'toRelevantPage': function () {
               this.showPage = 'relevantPage';
               this.clearStyle();
               $('#relevantBox').attr("class", "weui-grid navi-bar my-weui-grid weui-grid-select");
               $('#relevant').attr("class", "weui-grid__label weui-grid-select-content");
           },
           toModifyPage: function () {
               this.showPage = 'modifyPage';
               this.clearStyle();
               $('#modifBox').attr("class", "weui-grid navi-bar my-weui-grid weui-grid-select");
               $('#modif').attr("class", "weui-grid__label weui-grid-select-content");
           },
           'clickApplySupport': function () {
               window.location = "/opportunity/applySupport?salesOpportunityId=" + $("#salesOpportunityId").val();
           }

       }
   });

});