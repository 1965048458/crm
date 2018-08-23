$(document).ready(function () {
    var startProjectVue = new Vue({
        el: '#startProjectVue',
        data: {
            showContract: true,
            imgPath: '/images/customer/unfold.svg',
            pays: [{
                refund: '',
                condition: ''
            }],
            contract: {},
            projectId: '',
            projectName: '',
            advanceTime: '',
            advancePay: '',
            totalAmount: '',
            content: '',
            character: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十']
        },
        methods: {
            'showContent':function () {
                //
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
                //console.log('remove');
                this.pays.splice(index, 1);
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
                this.contract.amount = this.totalAmount;
                this.contract.advancePay = this.advancePay;
                this.contract.advanceTime = this.advanceTime;
                var postData = {
                    projectId: this.projectId,
                    projectName: this.projectName,
                    contract: this.contract,
                    refunds: this.pays,
                    applyContent: this.content
                };
                var thisVue = this;
                $.ajax({
                    type: 'post',
                    url: '/project/submitProject',
                    data: JSON.stringfy(postData),
                    dataType: 'json',
                    contentType: 'application/json',
                    cacche: false
                }).done(function (result) {
                    if (result.successFlg){
                        //
                    }
                });
            }
        }
    });
});
