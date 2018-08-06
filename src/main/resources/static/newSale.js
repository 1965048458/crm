/**
 * Created by Administrator on 2018/8/1.
 */

$(document).ready(function () {

    function handleTime(time) {
        if (time < 10)
            return "0" + time;
        else
            return time;
    }

    var saleVue = new Vue({
        el: '#saleVue',
        data: {
            ind: ['A', 'B', 'C', 'D'],
            stages: ['拿到老师手机及微信号', '提交方案', '以我方提供参数挂标', '中标'],
            showPage: 'basicInfo',
            preDate: '请选择',
            deliverDate: '请选择',
            saleStage: '请选择',
            selStage: '',
            customer: '请选择',
            contact: '',
            content: '',
            saleName: '',
            keyWord: ''
        },
        methods: {
            'back': function () {
                //todo
            },
            'showDatePicker': function () {
                var thisVue = this;
                const nowDate = new Date();
                weui.datePicker({
                    start: 1990,
                    end: 2030,
                    defaultValue: [nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate()],
                    onChange: function (result) {
                        console.log(result);
                    },
                    onConfirm: function (result) {
                        thisVue.preDate = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
                        console.log(result);
                    }
                });
            },
            'deliverDatePicker': function () {
                var thisVue = this;
                const nowDate = new Date();
                weui.datePicker({
                    start: 1990,
                    end: 2030,
                    defaultValue: [nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate()],
                    onChange: function (result) {
                        console.log(result);
                    },
                    onConfirm: function (result) {
                        thisVue.deliverDate = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
                        console.log(result);
                    }
                });
            },
            'finish': function () {
                //todo
            },
            'backToInfo': function () {
                this.showPage = 'basicInfo';
            },
            'selSaleStage': function () {
                this.showPage = 'saleStage';
            },
            'done': function () {
                this.saleStage = this.selStage;
                this.showPage = 'basicInfo';
            },
            'selCustomer': function () {
                this.showPage = 'customerContact';
            },
            'add': function () {
                //
            },
            'search': function () {
                //this.customers = true;   逻辑待修改
                window.location.href = "/customer/customerInfo?customerName=" + this.keyWord;
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchText').focus();
            },
            'hideSearchResult': function () {
                $('#searchResult').hide();
                this.keyWord = "";
            },
            'cancelSearch': function () {
                this.hideSearchResult();
                $('#searchBar').removeClass('weui-search-bar_focusing');
            },
            'clear': function () {
                this.keyWord = "";
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.cancelSearch();
                $('#searchInput').blur();
            }
        }
    });
});