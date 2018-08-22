
$(document).ready(function () {
   var startProjectVue = new Vue({
        el: '#startProjectVue',
        data:{
            showContract: true,
            imgPath: '/images/customer/unfold.svg',
            pays:[{}]
        },
        methods:{
            'backToDetail':function () {
                window.location.href = '/project/projectDetail';
            },
            'addPay':function () {
                //
            },
            'remove':function () {
                console.log('remove');
            },
            'changeFold':function () {
                this.showContract = !this.showContract;
                if(this.showContract){
                    this.imgPath = '/images/customer/unfold.svg';
                }else{
                    this.imgPath = '/images/customer/fold.svg';
                }
            },
            'getStyle':function (index) {
                //
            },
            'numToCha':function (index) {
                //
            },
            'submit':function () {
                //
            }
        }
   });
});
