$(document).ready(function () {

    function handleTime(time) {
        if (time < 10)
            return "0" + time;
        else
            return time;
    }
    var TYPE_NAMES = {
        'VISIT': '日常拜访',
        'OFFLINE': '线下拜访',
        'PHONE': '电话拜访'
    };

    var opportunityDetailVue = new Vue({
        el: '#opportunityDetailVue',
        data: {
            show: 'home',
            showDetailPage: 'detailPage',
            opportunity: '',
            contact: '',
            currentOppoId: '',

            ind: ['A', 'B', 'C', 'D'],
            stages: ['拿到老师手机及微信号', '提交方案', '以我方提供参数挂标', '中标'],
            preDate: '请选择',
            deliverDate: '请选择',
            saleStage: '请选择',
            selStage: '',
            content: '',
            opportunityName: '',
            amount: '',

            creatorName:'',
            modifyRecord:'',
            visitRecords:'',
        },
        methods: {
            'showDetailResult': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/opportunityDetail',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'opportunity', result.opportunity);
                        thisVue.$set(thisVue, 'currentOppoId', result.opportunityId);
                        thisVue.$set(thisVue, 'creatorName', result.creatorName);
                        if (result.contact != null) {
                            thisVue.$set(thisVue, 'contact', result.contact);
                        }
                    }
                })
            },
            'detailShow': function () {
                $("#detailBox").css('border-bottom', 'solid 2px #38A4F2');
                $("#detail").css('color', '#38A4F2');
                $("#relevantBox").removeAttr("style");
                $("#relevant").css('color', 'black');
                $("#modifBox").removeAttr("style");
                $("#modif").css('color', 'black');
                this.showDetailPage = 'detailPage';
            },
            'relevant': function () {
                $("#relevantBox").css('border-bottom', 'solid 2px #38A4F2');
                $("#relevant").css('color', '#38A4F2');
                $("#detailBox").removeAttr("style");
                $("#detail").css('color', 'black');
                $("#modifBox").removeAttr("style");
                $("#modif").css('color', 'black');
                var thisVue = this;
                $.ajax({
                    type: 'post',
                    url: '/opportunity/opportunityVisitRecord',
                    data: {
                        opportunityId: thisVue.opportunity.opportunityId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'visitRecords', result.visitRecords);
                        thisVue.showDetailPage = 'relevantPage';
                    }
                })
            },
            'visitType': function (type){
                return TYPE_NAMES[type];
            },
            'modification': function () {
                $("#modifBox").css('border-bottom', 'solid 2px #38A4F2');
                $("#modif").css('color', '#38A4F2');
                $("#relevantBox").removeAttr("style");
                $("#relevant").css('color', 'black');
                $("#detailBox").removeAttr("style");
                $("#detail").css('color', 'black');
                var thisVue = this;
                $.ajax({
                    type: 'post',
                    url: '/opportunity/modificationRecord',
                    data: {
                        opportunityId: thisVue.opportunity.opportunityId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'modifyRecord', result.modificationRecord);
                        thisVue.showDetailPage = 'modifPage';
                    }
                })
            },
            'back': function () {
                history.back();
            },
            'modif': function () {
                this.show = 'modif';
                this.preDate = this.opportunity.checkDateString;
                this.deliverDate = this.opportunity.clinchDateString;
                this.saleStage= this.opportunity.salesStatus;
                this.content = this.opportunity.content;
                this.opportunityName=this.opportunity.opportunityName;
                this.amount=this.opportunity.amount;
            },
            'modifBack': function () {
                this.show = 'home';
            },
            'detailSubmit': function () {
                var thisVue = this;
                var postData = {
                    opportunityId:thisVue.opportunity.opportunityId,
                    opportunityName: this.opportunityName,
                    salesStatus: this.saleStage,
                    amount: this.amount,
                    checkDate: this.preDate,
                    clinchDate: this.deliverDate,
                    content: this.content,
                };
                $.ajax({
                    type: 'post',
                    url: '/opportunity/modification',
                    data: JSON.stringify(postData),
                    dataType: 'json',
                    contentType: 'application/json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            thisVue.showDetailResult();
                            thisVue.show = 'home';
                        }, 1000);
                    }
                });

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
            'selSaleStage': function () {
                this.show = 'saleStage';
            },
            'chooseBack': function () {
                this.show = 'modif';
            },
            'done1': function () {
                if (this.selStage === "") {
                    alert("销售阶段不能为空！");
                    return;
                }
                this.saleStage = this.selStage;
                this.show = 'modif';
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
    opportunityDetailVue.showDetailResult();

});
