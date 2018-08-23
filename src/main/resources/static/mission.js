/**
 * Created by Administrator on 2018/8/1.
 */

$(document).ready(function () {

    var mission = new Vue({
        el: '#mission',
        data: {
            showPage:'unfinish',
            show:'home',
        },
        methods: {
            'unfinish':function () {
                this.showPage ='unfinish';
            },
            'finish': function () {
                this.showPage ='finish';

            },
            'toDetail': function () {
                this.show = 'detail';
            },
            'back': function () {
               this.show = 'home';
            },
            'showMyCustomers': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/getCustomers',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'myCustomers', result.customerList);
                        for (var index = 0; index < thisVue.myCustomers.length; index++) {
                            thisVue.$set(thisVue.myCustomers[index], 'showSub', false);
                            thisVue.$set(thisVue.myCustomers[index], 'imgPath', "/images/customer/fold.svg");
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                });
            },
        }
    });
});