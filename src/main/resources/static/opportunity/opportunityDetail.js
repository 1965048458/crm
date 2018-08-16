$(document).ready(function () {

    var opportunityDetailVue = new Vue({
        el: '#opportunityDetailVue',
        data: {

            showPage:'detailPage',
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
                        alert(thisVue.errMsg);
                    }
                })
            },
            'detail':function(){
                $("#detailBox").css('border-bottom', 'solid 2px #38A4F2');
                $("#detail").css('color', '#38A4F2');
                $("#relevantBox").removeAttr("style");
                $("#relevant").css('color','black');
                $("#modifBox").removeAttr("style");
                $("#modif").css('color','black');
                this.showPage ='detailPage';
            },
            'relevant':function(){
                $("#relevantBox").css('border-bottom', 'solid 2px #38A4F2');
                $("#relevant").css('color', '#38A4F2');
                $("#detailBox").removeAttr("style");
                $("#detail").css('color','black');
                $("#modifBox").removeAttr("style");
                $("#modif").css('color','black');
                this.showPage = 'relevantPage';
            },
            'modification':function(){
                $("#modifBox").css('border-bottom', 'solid 2px #38A4F2');
                $("#modif").css('color', '#38A4F2');
                $("#relevantBox").removeAttr("style");
                $("#relevant").css('color','black');
                $("#detailBox").removeAttr("style");
                $("#detail").css('color','black');
                this.showPage ='modifPage';
            },
        },
        watch: {
            'filterCondition': function () {
                $("#" + this.filterValue).css('background-color', '#FFFFFF');
                $("#" + this.filterCondition).css('background-color', 'gainsboro');
                this.filterValue = this.filterCondition;
            },
        }
    });

});
