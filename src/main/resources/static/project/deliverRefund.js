$(document).ready(function () {

    function handleTime(time) {
        if (time < 10)
            return "0" + time;
        else
            return time;
    }

    var refundVue = new Vue({
        el: '#refundVue',
        data: {
            selected: [],
            refundDate: '',
            deliverDate: '请选择',
            pays: [{
                refundId: '',
                refundNum: '',
                time: ''
            }],
            contract: {
                contractId: '',
                amount: '',
                advanceTime: '',
                advancePay: ''
            },
            id: '',
            projectStart: {},
            projectId: '',
            projectName: '',
            advanceTime: '',
            advancePay: '',
            totalAmount: '',
            content: '',
            character: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十']
        },
        methods: {
            'showContent': function () {
                var thisVue = this;
                thisVue.projectId = $('#projectId').val();
                $.ajax({
                    type: 'get',
                    url: '/project/getContractInfo',
                    data: {
                        projectId: thisVue.projectId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'projectStart', result.projectStart);
                        if (thisVue.projectStart != null) {
                            thisVue.$set(thisVue, 'contract', result.projectStart.contract);
                            thisVue.$set(thisVue, 'pays', result.projectStart.refunds);
                            thisVue.content = thisVue.projectStart.applyContent;
                            thisVue.totalAmount = thisVue.contract.amount;
                            thisVue.advancePay = thisVue.contract.advancePay;
                            thisVue.advanceTime = thisVue.contract.advanceTime;
                            thisVue.id = thisVue.projectStart.id;
                        }
                    }
                });
            },
            'backToDetail': function () {
                window.location = '/project/projectDetail?projectId=' + this.projectId;
            },
            'getStyle': function (length) {
                var style;
                if (length > 1) {
                    style = {
                        'color': '#38A4F2'
                    }
                } else {
                    style = {
                        'color': '#030303'
                    }
                }
                return style;
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
                        console.log(thisVue.deliverDate);
                    }
                });
            },
            'refundDatePicker':function () {
                var temp = this;
                const nowDate = new Date();
                weui.datePicker({
                    start: 1990,
                    end: 2030,
                    defaultValue: [nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate()],
                    onChange: function (result) {
                        console.log(result);
                    },
                    onConfirm: function (result) {
                        var t = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
                        temp.deliverDate = t;
                        //temp.$set(temp.pays[index], 'time', t);
                        //console.log(temp.pays[index].time);
                    }
                });
            },
            'numToCha': function (index) {
                return this.character[index];
            },
            'confirm': function () {
                console.log(this.pays);
                var str = '';
                for(var i in this.selected){
                    str += this.selected[i] + ',';
                }
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/submitRefund',
                    data: {
                        refunds: str,
                        projectId: thisVue.projectId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            window.location = '/project/projectDetail?projectId=' + thisVue.projectId;
                        }, 1000);
                    } else {
                        alert("提交不成功!");
                    }
                });
            }
        }
    });
    refundVue.showContent();
});
