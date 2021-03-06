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
            ind: ['A', 'B', 'C', 'D', 'F'],
            stages: ['拿到老师手机及微信号', '提交方案', '以我方提供参数挂标', '中标', '输单'],
            showPage: 'basicInfo',
            preDate: '请选择',
            deliverDate: '请选择',
            saleStage: '请选择',
            selStage: '',
            lastStage: '',
            contact: '请选择',
            temp: '',
            contactId: '',
            content: '',
            opportunityName: '',
            amount: '',
            customerId: '',
            myCustomers: [{
                showSub: false,
                imgPath: "/images/customer/fold.svg"
            }],
            errMsg: '',
            keyWord: ''
        },
        methods: {
            'back': function () {
                window.location = '/opportunity';
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
            'backToInfo': function () {
                this.showPage = 'basicInfo';
            },
            'selSaleStage': function () {
                this.showPage = 'saleStage';
            },
            'done1': function () {
                if (this.selStage === "") {
                    alert("销售阶段不能为空！");
                    return;
                } else if (this.selStage === 'F') {
                    this.saleStage = '输单';
                } else {
                    this.saleStage = this.selStage + '阶段';
                }
                this.lastStage = this.selStage;
                this.showPage = 'basicInfo';
            },
            'done2': function () {
                if (this.temp === "") {
                    alert("客户联系人不能为空！");
                    return;
                }
                var str = this.temp.split(':');
                this.contact = str[0];
                this.contactId = str[1];
                this.customerId = str[2];
                console.log(this.contactId);
                this.showPage = 'basicInfo';
            },
            'selCustomer': function () {
                this.showPage = 'customerContact';
                this.showMyCustomers();
            },
            'checkNull': function () {
                if (this.contactId === "" ||
                    this.lastStage === "" || this.opportunityName === "") {
                    alert("以下带星号内容均为必填项！");
                    return false;
                } else {
                    if (this.preDate === "请选择") {
                        this.preDate = '';
                    }
                    if (this.deliverDate === "请选择") {
                        this.deliverDate = '';
                    }
                    return true;
                }
            },
            'add': function () {
                if (!this.checkNull()) {
                    return;
                }
                var thisVue = this;
                var postData = {
                    customerId: this.customerId,
                    opportunityName: this.opportunityName,
                    salesStatus: this.lastStage,
                    amount: this.amount,
                    checkDate: this.preDate,
                    clinchDate: this.deliverDate,
                    content: this.content,
                    contactId: this.contactId
                };
                $.ajax({
                    type: 'post',
                    url: '/opportunity/addSale',
                    data: JSON.stringify(postData),
                    dataType: 'json',
                    contentType: 'application/json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            window.location = '/opportunity';
                        }, 1000);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'search': function () {
                //this.customers = true;   逻辑待修改
                //window.location.href = "/customer/customer?customerName=" + this.keyWord;
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchInput').focus();
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
                        for (var index in thisVue.myCustomers ) {// 0; index < thisVue.myCustomers.length; index++
                            thisVue.$set(thisVue.myCustomers[index], 'showSub', false);
                            thisVue.$set(thisVue.myCustomers[index], 'imgPath', "/images/customer/fold.svg");
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                });
            },
            'setImgPath': function (index) {
                if (this.myCustomers[index].showSub == false) {
                    this.$set(this.myCustomers[index], 'imgPath', "/images/customer/fold.svg");
                } else {
                    this.$set(this.myCustomers[index], 'imgPath', "/images/customer/unfold.svg");
                }
            },
            'changeSubFold': function (index) {
                var sub = this.myCustomers[index].showSub;
                this.$set(this.myCustomers[index], 'showSub', !sub);
                this.setImgPath(index);
            },
            'onTransferValue': function (customerInfo) {
                this.temp = customerInfo;
            }
        }
    });

    Vue.component('customer', {
        template: '#customer',
        props: ['customer'],
        data: function () {
            return {
                customerInfo: '',
                showSub: false,
                imgPath: "/images/customer/fold.svg"
            };
        },
        methods: {
            'changeSubFold': function () {
                this.showSub = !this.showSub;
                this.setImgPath();
            },
            'setImgPath': function () {
                if (this.showSub == false) {
                    this.imgPath = "/images/customer/fold.svg";
                } else {
                    this.imgPath = "/images/customer/unfold.svg";
                }
            },
            'checkGender': function (gender) {
                if (gender == 'FEMALE') {
                    return "/images/customer/FEMALE.svg";
                } else {
                    return "/images/customer/MALE.svg";
                }
            },
            'addNumBrackets': function (number) {
                if (number == '0') {
                    return '';
                } else {
                    return "( " + number + " )";
                }
            },
            'onTransferValue': function (customerInfo) {
                this.$emit('transfer_value', customerInfo);
            }
        },
        watch: {
            'customerInfo': function () {
                this.$emit('transfer_value', this.customerInfo);
            }
        }
    });
});