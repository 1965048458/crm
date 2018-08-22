/**
 * Created by Administrator on 2018/7/17.
 */
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

$(document).ready(function () {

    var opportunityVue = new Vue({
        el: '#opportunityVue',
        data: {
            imgSort: '/images/opportunity/sortUnchecked.svg',
            imgFilter: '/images/opportunity/filterUnchecked.svg',
            showSortPage: false,
            showFilterPage: false,
            showPage: 'opportunity',
            filterCondition: '',
            filterValue: '',
            sortMode: 'ASC',
            sceneValue: 'all',
            dateValue: 'all',
            dateValueStart: '',
            dateValueEnd: '',
            creatorValue: 'all',
            creatorV: '',
            stageValue: 'all',
            customerValue: 'all',
            customerValueIn: '',
            opportunityList: '',
            subMemberList: [],
            tempSub: [],
            subUserId: [],
            subsName: [],
            subUser: '',

            titleBar: true,
            searchCustomer: true,
            customers: true,
            searchWord: '',
            errMsg: undefined,

            showNull: '',


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
            lastStage: '',
            content: '',
            opportunityName: '',
            amount: '',

            creatorName: '',

            modifyRecord: '',
            visitRecords: '',

            opportunityId: '',

            applySupports: '',

            searchBar: false,
            keyWord: '',

            failReason:'',

            showOption: false,
            showDialog:false,

        },
        methods: {
            'showResult': function (data) {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/queryOpportunity',
                    data: data,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'opportunityList', result.opportunityList);
                        if (result.opportunityList == null || result.opportunityList == '') {
                            thisVue.showNull = 'null';
                        } else {
                            thisVue.showNull = '';
                        }
                    } else {
                        thisVue.errMsg = result.errMsg;
                        alert(thisVue.errMsg);
                    }
                })
            },
            'imgSrc': function (data) {
                if (data == 'A') {
                    return '/images/opportunity/AStage.svg';
                } else if (data == 'B') {
                    return '/images/opportunity/BStage.svg';
                } else if (data == 'C') {
                    return '/images/opportunity/CStage.svg';
                } else if (data == 'D') {
                    return '/images/opportunity/DStage.svg';
                } else if (data == 'F') {
                    return '/images/opportunity/loseOrder.svg';
                }
            },
            'search': function () {
                this.searchBar = !this.searchBar;
                if (this.searchBar == false) {
                    this.keyWord = '';
                } else {
                    this.imgFilter = '/images/opportunity/filterUnchecked.svg';
                    this.imgSort = '/images/opportunity/sortUnchecked.svg';
                    this.showFilterPage = false;
                    this.showSortPage = false;
                    this.filterCondition = '';
                }

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
            'add': function () {
                window.location = "/opportunity/newSale";
            },
            'sort': function () {
                if (this.imgFilter == '/images/opportunity/filterChecked.svg') {
                    this.showFilterPage = false;
                    this.filterCondition = '';
                }
                if (this.imgSort == '/images/opportunity/sortUnchecked.svg') {
                    this.showSortPage = true;
                    this.imgSort = "/images/opportunity/sortChecked.svg";
                    this.imgFilter = "/images/opportunity/filterUnchecked.svg";
                } else {
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/sortUnchecked.svg";
                }
            },
            'sortAsc': function () {
                var data = {
                    sortMode: 'ASC',
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName: this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                this.showResult(data);
            },
            'sortDesc': function () {
                var data = {
                    sortMode: 'DESC',
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName: this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                this.showResult(data);
            },
            'filter': function () {
                if (this.imgSort == '/images/opportunity/sortChecked.svg') {
                    this.showSortPage = false;
                    this.imgSort = "/images/opportunity/sortUnchecked.svg";

                }
                if (this.imgFilter == '/images/opportunity/filterUnchecked.svg') {
                    this.showFilterPage = true;
                    this.filterCondition = 'creator';
                    this.imgFilter = "/images/opportunity/filterChecked.svg";
                    this.imgSort = "/images/opportunity/sortUnchecked.svg";
                } else {
                    this.showFilterPage = false;
                    this.filterCondition = '';
                    this.imgFilter = "/images/opportunity/filterUnchecked.svg";
                }
            },
            'filterScene': function () {
                this.filterCondition = 'scene';
            },
            'filterDate': function () {
                this.filterCondition = 'date';
            },
            'filterCreator': function () {
                this.filterCondition = 'creator';
            },
            'filterStage': function () {
                this.filterCondition = 'stage';
            },
            'filterCustomer': function () {
                this.filterCondition = 'customer';
            },
            'cancelMask': function () {
                this.showSortPage = false;
                this.showFilterPage = false;
                this.filterCondition = '';
                this.imgSort = "/images/opportunity/sortUnchecked.svg";
                this.imgFilter = "/images/opportunity/filterUnchecked.svg";
            },
            'removeChecked': function () {
                this.dateValue = '';
            },
            'removeChecked1': function () {
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

                //this.creatorV = '汪峰';
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
            'dateChecked': function () {
                this.dateValueStart = '';
                this.dateValueEnd = '';
            },
            'customerChecked': function () {
                this.customerValueIn = '';
            },
            'finish': function () {
                this.showFilterPage = false;
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
                this.filterCondition = '';
                var data = {
                    sortMode: this.sortMode,
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName: this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                };
                this.showResult(data);
            },
            'reset': function () {
                this.sceneValue = 'all';
                this.dateValue = 'all';
                this.creatorValue = 'all';
                this.stageValue = 'all';
                this.customerValue = 'all';
                this.dateValueStart = '';
                this.dateValueEnd = '';
                this.creatorV = '';
                this.customerValueIn = '';
                this.subUser = '';
                this.tempSub = [];
            },
            'backToFilter': function () {
                this.showPage = 'opportunity';
                this.tempSub = [];
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
                this.showPage = 'opportunity';
            },
            'detail': function (data) {
                var thisVue = this;
                thisVue.opportunityId = data;
                thisVue.showDetailResult();
                thisVue.showPage = 'toDetail';

            },


            'showDetailResult': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/opportunityDetail',
                    data: {
                        opportunityId: thisVue.opportunityId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'opportunity', result.opportunity);
                        thisVue.$set(thisVue, 'currentOppoId', result.opportunityId);
                        thisVue.$set(thisVue, 'creatorName', result.creatorName);
                        thisVue.$set(thisVue, 'lastStage', result.opportunity.salesStatus);
                        if (result.contact != null) {
                            thisVue.$set(thisVue, 'contact', result.contact);
                        } else {
                            thisVue.$set(thisVue, 'contact', '');
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
                    url: '/opportunity/opportunityRecord',
                    data: {
                        opportunityId: thisVue.opportunity.opportunityId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'visitRecords', result.visitRecords);
                        // thisVue.$set(thisVue, 'applySupports', result.applySupports);
                        thisVue.showDetailPage = 'relevantPage';
                    }
                })
            },
            'visitType': function (type) {
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
                this.showPage = 'opportunity';
                this.showDetailPage = 'detailPage';
                $("#detailBox").css('border-bottom', 'solid 2px #38A4F2');
                $("#detail").css('color', '#38A4F2');
                $("#relevantBox").removeAttr("style");
                $("#relevant").css('color', 'black');
                $("#modifBox").removeAttr("style");
                $("#modif").css('color', 'black');

            },
            'modif': function () {
                this.show = 'modif';
                this.preDate = this.opportunity.checkDateString;
                this.deliverDate = this.opportunity.clinchDateString;
                if (this.opportunity.salesStatus == 'F') {
                    this.saleStage = '输单';
                } else {
                    this.saleStage = this.opportunity.salesStatus + '阶段';
                }
                this.selStage = this.opportunity.salesStatus;
                this.content = this.opportunity.content;
                this.opportunityName = this.opportunity.opportunityName;
                this.amount = this.opportunity.amount;
            },
            'modifBack': function () {
                this.show = 'home';
            },
            'failBack': function () {
                this.show = 'home';
            },
            'fail': function () {
                this.show = 'failReason';
            },
            'detailSubmit': function () {
                var thisVue = this;
                var postData = {
                    opportunityId: thisVue.opportunity.opportunityId,
                    opportunityName: this.opportunityName,
                    salesStatus: this.lastStage,
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
                            $("#detailBox").css('border-bottom', 'solid 2px #38A4F2');
                            $("#detail").css('color', '#38A4F2');
                            $("#relevantBox").removeAttr("style");
                            $("#relevant").css('color', 'black');
                            $("#modifBox").removeAttr("style");
                            $("#modif").css('color', 'black');
                            thisVue.show = 'home';
                            thisVue.showDetailPage = 'detailPage';

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

                    },
                    onConfirm: function (result) {
                        thisVue.preDate = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
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
                    },
                    onConfirm: function (result) {
                        thisVue.deliverDate = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
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
                } else if (this.selStage === 'F') {
                    this.saleStage = '输单';
                } else {
                    this.saleStage = this.selStage + '阶段';
                }
                this.lastStage = this.selStage;
                this.show = 'modif';
            },
            'failSubmit':function(data){
                var thisVue = this;
                $.ajax({
                    type: 'post',
                    url: '/opportunity/failReason',
                    data: {
                        opportunityId: data,
                        failReason:thisVue.failReason,
                    },
                    dataType: 'json',
                    cache: false,
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.showDetailResult();
                        thisVue.show = 'home';
                    }
                })
            },
            'cancelOtherEvent': function () {
               this.showOption = false;
            },
            'other':function () {
                this.showOption = !this.showOption;
            },
            'deleteOppo': function () {
                this.showOption = false;
                this.showDialog = true;
            },
            'cancelDialog': function () {
                this.showDialog = false;
            },
            'deleteConfirm': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/deleteOpportunity',
                    data: {
                        opportunityId: thisVue.opportunityId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.showDialog = false;
                        thisVue.showResult();
                        thisVue.showPage = 'opportunity';
                        thisVue.showDetailPage = 'detailPage';
                    }
                })
            },
            'convert': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/convertOpportunity',
                    data: {
                        opportunityId: thisVue.opportunityId,
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.showResult();
                        $('#toast1').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            thisVue.showPage = 'opportunity';
                            thisVue.showDetailPage = 'detailPage';
                        }, 500);
                    }
                })
            },


        },
        watch: {
            'filterCondition': function () {
                $("#" + this.filterValue).css('background-color', '#FFFFFF');
                $("#" + this.filterCondition).css('background-color', 'gainsboro');
                this.filterValue = this.filterCondition;
            },
            'customerValueIn': function () {
                if (this.customerValueIn != '') {
                    this.customerValue = '';
                } else {
                    this.customerValue = 'all';
                }
            },
            'creatorV': function () {
                if (this.creatorV != '') {
                    this.creatorValue = '';
                }
            },
            'keyWord': function () {
                var thisVue = this;
                var data = {
                    sortMode: this.sortMode,
                    userId: this.creatorValue,
                    subUser: this.subUser,
                    customerName: this.customerValueIn,
                    createStart: this.dateValueStart,
                    createEnd: this.dateValueEnd,
                    salesStatus: this.stageValue,
                    keyWord: this.keyWord,
                };
                this.showResult(data);
            }


        }
    });
    opportunityVue.showResult();
});
