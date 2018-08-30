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
            pays: [{
                refundId: '',
                refundNum: '',
                time: '',
                isRefunded: ''
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
                            for(var i in thisVue.pays){
                                if (thisVue.pays[i].isRefunded == 1){
                                    thisVue.selected.push(thisVue.pays[i].refundId);
                                }
                            }
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
            'refundDatePicker':function (index) {
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
                        temp.$set(temp.pays[index], 'time', t);
                        console.log(temp.pays[index].time);
                    }
                });
            },
            'numToCha': function (index) {
                return this.character[index];
            },
            'confirm': function () {
                for(var i in this.selected){
                    for(var j in this.pays){
                        if (this.selected[i] === this.pays[j].refundId){
                            this.pays[j].isRefunded = 1;
                        }
                    }
                }
                console.log(this.pays);
                var thisVue = this;
                var postData = {
                    projectId: thisVue.projectId,
                    refunds: thisVue.pays
                };
                $.ajax({
                    type: 'post',
                    url: '/project/submitRefund',
                    data: JSON.stringify(postData),
                    dataType: 'json',
                    contentType: 'application/json',
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
