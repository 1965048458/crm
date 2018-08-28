jQuery(document).ready(function () {

    function handleTime(time) {
        if (time < 10)
            return "0" + time;
        else
            return time;
    }

    var newProject = new Vue({
        el: '#newProject',
        data: {
            stages: ['未开始', '未交付', '交付及回款', '已结束'],
            showPage: 'addProject',
            name: '',
            content: '',
            agent: '',
            person: '',
            selStage: '',
            status: '未启动(创建后提交启动申请)',
            lastStatus: 0,
            deadLine: '请选择',
            background: '',
            temp: '',
            contactId: '',
            contact: '请选择',
            customerId: '',
            myCustomers: [{
                showSub: false,
                imgPath: "/images/customer/fold.svg"
            }],
            amount: ''
        },
        methods: {
            'back': function () {
                window.location = '/project/projectList';
            },
            'backToInfo': function () {
                this.showPage = 'addProject';
            },
            'submit': function () {
                if (this.name === '' || this.contact === '请选择' ) {
                    alert("带星号内容必须填写！");
                    return;
                } else {
                    var thisVue = this;
                    if(thisVue.deadLine === '请选择' ){
                        thisVue.deadLine = '';
                    }

                    var postData = {
                        projectName: thisVue.name,
                        content: thisVue.content,
                        agent: thisVue.agent,
                        contactId: thisVue.contactId,
                        deadLine: thisVue.deadLine,
                        status: thisVue.lastStatus,
                        amount: thisVue.amount,
                        customerId: thisVue.customerId
                    };
                    jQuery.ajax({
                        type: 'post',
                        url: '/project/add',
                        data: JSON.stringify(postData),
                        dataType: 'json',
                        contentType: 'application/json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            $('#toast').fadeIn(100);
                            setTimeout(function () {
                                $('#toast').fadeOut(100);
                                window.location = '/project/projectList';
                            }, 1000);
                        } else {
                            alert("请填写正确的信息");
                        }
                    });
                }
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
                        thisVue.deadLine = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
                        console.log(result);
                    }
                });
            },
            'selStatus': function () {
                this.showPage = 'status';
            },
            'done1': function () {
                if (this.selStage === "") {
                    alert("项目状态不能为空！");
                    return;
                }
                this.status = this.stages[this.selStage];
                this.lastStatus = this.selStage;
                this.showPage = 'addProject';
            },
            'selContact': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/getCustomers',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'myCustomers', result.customerList);
                        for (var index in thisVue.myCustomers) {
                            thisVue.$set(thisVue.myCustomers[index], 'showSub', false);
                            thisVue.$set(thisVue.myCustomers[index], 'imgPath', "/images/customer/fold.svg");
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                    }
                });
                this.showPage = 'customerContact';
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
                this.showPage = 'addProject';
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
