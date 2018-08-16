$(document).ready(function () {
    var projectVue = new Vue({
        el: '#projectVue',
        data: {
            showPage: 'projectList',
            stages: ['未开始', '未交付', '交付及回款', '已结束'],
            filterPage: false,
            filterCondition: '',
            imgFilter: '/images/opportunity/筛选未选中.svg',
            projectList: [],
            dateValueStart: '',
            newDate: 'all',
            dateValueEnd: '',
            subMemberList: [],
            tempSub: [],
            subUserId: [],
            subsName: [],
            subUser: '',
            creatorValue: 'all',
            customerValue: 'all',
            customerValueIn: '',
            creatorV: '',
            stageValue: 'all'
        },
        methods: {
            'showResult': function (data) {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/queryOpportunity',
                    data:data,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'opportunityList', result.opportunityList);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        alert(thisVue.errMsg);
                    }
                })
            },
            'back': function () {
                //
            },
            'add': function () {
                window.location = '/project/new';
            },
            'search': function () {
                //
            },
            'filter': function () {
                this.imgFilter = "/images/opportunity/筛选已选中.svg";
                this.filterPage = true;
                this.filterCondition = 'creator';
            },
            'all': function () {
                this.imgFilter = '/images/opportunity/筛选未选中.svg';
                this.filterPage = false;
                this.filterCondition = '';
            },
            'before': function () {
                this.imgFilter = '/images/opportunity/筛选未选中.svg';
                this.filterPage = false;
                this.filterCondition = '';
            },
            'after': function () {
                this.imgFilter = '/images/opportunity/筛选未选中.svg';
                this.filterPage = false;
                this.filterCondition = '';
            },
            'cancelMask': function () {
                this.filterPage = false;
                this.filterCondition = '';
            },
            'selCreator': function () {
                this.filterCondition = 'creator';
            },
            'selDate': function () {
                this.filterCondition = 'date';
            },
            'selCustomer': function () {
                this.filterCondition = 'customer';
            },
            'selStatus': function () {
                this.filterCondition = 'status';
            },
            'dateChecked': function () {
                this.dateValueStart = '';
                this.dateValueEnd = '';
            },
            'creatorChecked': function () {
                this.creatorV = '';
                this.subUser = '';
                this.tempSub = [];
            },
            'creatorChecked1': function () {
                this.creatorV = '';
                this.subUser = '';
                this.tempSub = [];
            },
            'creatorChecked2': function () {
                this.creatorV = '';
                this.subUser = '';
                this.tempSub = [];
            },
            'customerChecked': function () {
                this.customerValueIn = '';
            },
            'backToFilter': function () {
                this.tempSub = [];
                this.showPage = 'projectList';
            },
            'chooseCreator': function () {
                this.creatorValue = '';
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/subMemberList',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'subMemberList', result.subMemberList);
                        thisVue.showPage = 'selectCreator';
                    }
                });

            },
            'finish': function () {
                this.filterPage = false;
                this.imgFilter ='/images/opportunity/筛选未选中.svg';
                this.filterCondition = '';
                var data = {
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName:this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                console.log(data);
                this.showResult(data);
            },
            'reset': function () {
                this.creatorValue = 'all';
                this.stageValue = 'all';
                this.newDate = 'all';
                this.customerValue = 'all';
                this.dateValueStart = '';
                this.dateValueEnd = '';
                this.creatorV = '';
                this.customerValueIn='';
                this.subUser ='';
                this.tempSub =[];
            },
            'submit': function () {
                this.subUserId = [];
                this.subsName = [];

                for (var i = 0; i < this.tempSub.length; i++) {
                    var str = this.tempSub[i].split(',');
                    this.subUserId[i] = str[0];
                    this.subsName[i] = str[1];
                }
                var tempId = '';
                for (var i = 0; i < this.subUserId.length; i++) {
                    tempId += this.subUserId[i] + ',';
                }
                this.subUser = tempId;
                this.creatorV = this.subsName;
                this.showPage = 'projectList';
            }
        }
    });
});
