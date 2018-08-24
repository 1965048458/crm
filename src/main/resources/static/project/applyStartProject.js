$(document).ready(function () {

    var startProjectVue = new Vue({
        el: '#startProjectVue',
        data: {
            showContract: true,
            imgPath: '/images/customer/unfold.svg',
            pays: [{
                //refundId: '',
                refundNum: '',
                condition: ''
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
                window.location.href = '/project/projectDetail';
            },
            'addPay': function () {
                this.pays.push({
                    refund: '',
                    condition: ''
                });
            },
            'remove': function (index) {
                this.pays.splice(index, 1);
                // console.log(this.pays.splice(index, 1));
            },
            'changeFold': function () {
                this.showContract = !this.showContract;
                if (this.showContract) {
                    this.imgPath = '/images/customer/unfold.svg';
                } else {
                    this.imgPath = '/images/customer/fold.svg';
                }
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
            'numToCha': function (index) {
                return this.character[index];
            },
            'submit': function () {
                console.log(this.pays);
                var contract = {
                    contractId: this.contract.contractId,
                    amount: this.totalAmount,
                    advanceTime: this.advanceTime,
                    advancePay: this.advancePay
                };
                this.contract = contract;
                var postData = {
                    id: this.id,
                    projectId: this.projectId,
                    contract: this.contract,
                    refunds: this.pays,
                    applyContent: this.content
                };
                var thisVue = this;
                $.ajax({
                    type: 'post',
                    url: '/project/submitProject',
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
    startProjectVue.showContent();
});
