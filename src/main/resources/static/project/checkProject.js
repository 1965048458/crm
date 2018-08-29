$(document).ready(function () {

    var startProjectVue = new Vue({
        el: '#startProjectVue',
        data: {
            showContract: true,
            showBasicInfo: true,
            show: 'main',
            keyWord: '',
            chosenExe: '',
            searchBar: false,
            imgPath: '/images/customer/unfold.svg',
            imgPath1: '/images/customer/unfold.svg',
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
            userList: [],
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
            /* 'addPay': function () {
             this.pays.push({
             refund: '',
             condition: ''
             });
             },
             'remove': function (index) {
             this.pays.splice(index, 1);
             // console.log(this.pays.splice(index, 1));
             },*/
            'changeFold': function () {
                this.showContract = !this.showContract;
                if (this.showContract) {
                    this.imgPath = '/images/customer/unfold.svg';
                } else {
                    this.imgPath = '/images/customer/fold.svg';
                }
            },
            'changeFold1': function () {
                this.showBasicInfo = !this.showBasicInfo;
                if (this.showBasicInfo) {
                    this.imgPath1 = '/images/customer/unfold.svg';
                } else {
                    this.imgPath1 = '/images/customer/fold.svg';
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
            'clear': function () {
                this.keyWord = '';
                $('#searchInput').focus();
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.keyWord = '';
                $('#searchInput').blur();
                this.searchBar = false;
            },
            'search': function () {
                this.searchBar = !this.searchBar;
                if (!this.searchBar) {
                    this.keyWord = '';
                }
            },
            'skip': function () {
                window.location.href = '/message/showApplyList';
            },
            'pass': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/passProject',
                    data: {
                        projectId: thisVue.projectId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $.ajax({
                            type: 'get',
                            url: '/project/queryCompanyUser',
                            data: {
                                keyword: thisVue.keyWord
                            },
                            dataType: 'json',
                            cache: false
                        }).done(function (result) {
                            if (result.successFlg) {
                                thisVue.$set(thisVue, 'userList', result.companyUsers);
                                thisVue.show = 'chooseExe';
                            } else {
                                alert("系统出错");
                            }
                        });
                    }
                });

            },
            'confirm': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/assignLeader',
                    data: {
                        projectId: thisVue.projectId,
                        leader: thisVue.chosenExe
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if(result.successFlg){
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            window.location = '/message/showApplyList';
                        }, 1000);
                    }
                });
            }
        }
    });
    startProjectVue.showContent();
});
