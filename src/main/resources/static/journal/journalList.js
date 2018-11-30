var TYPE_NAMES = {
    'DAILY': '日报',
    'WEEKLY': '周报',
    'MONTHLY': '月报'
};

jQuery(document).ready(function () {
    var journalListVue = new Vue({
        el: '#journalVue',
        data: {
            showPage: 'journalList',
            showAddJournalDialog: false,
            journalList: '',
            curJournal: {
                user: {}
            },
            isAdmin:false,
            customers: [],
            projects: [],
            subMemberList: [],
            sendersId: [],
            journalLists:[],
            delay:30,
            miss:100,
            sendersName: [],
            tempSenders: [],
            followJournal:[],
            journalExcel:[],
            showRead: true,
            journalType: "",
            client: "",
            repairDate:[],
            project: "",
            startTime: "",
            manager:'',
            endTime: "",
            isRead: "",
            tip: '',
            errMsg: "",
            repairC:'0',
            loseC:'0',
            totalC:'0',
            showErrMsg: false,
        },
        methods: {
            'searchList': function (data) {
                //todo 从服务器搜索日志
                // console.log(data);
                var $loadingToast = $('#loadingToast');
                if ($loadingToast.css('display') != 'none') return;
                $loadingToast.fadeIn(100);
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/list',
                    data: data,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        // console.log(result);
                        thisVue.$set(thisVue, 'journalList', result.journalList);
                        thisVue.showPage = 'journalList';
                        $loadingToast.fadeOut(100);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'searchRepair':function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/repair',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'repairDate', result.repairDate);
                    thisVue.$set(thisVue,'isAdmin',result.isAdmin);
                });
            },
            'change':function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/money',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue,'delay',result.delay);
                    thisVue.$set(thisVue,'miss',result.miss);
                    thisVue.showPage = 'money';
                });
            },
            'changemoney':function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/changemoney',
                    data: {
                        delay: thisVue.delay ,
                        miss:thisVue.miss
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    alert("修改成功");
                });
            },
            'managerJournal':function (){
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/manager',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'repairC', result.repairC);
                    thisVue.$set(thisVue, 'loseC', result.loseC);
                    thisVue.$set(thisVue, 'totalC', result.totalC);
                    thisVue.$set(thisVue, 'manager', result.manager);
                    thisVue.$set(thisVue,'delay',result.delay);
                    thisVue.$set(thisVue,'miss',result.miss);
                    thisVue.showPage = 'showManager';
                });
            },
            'follow':function () {
                //window.location="/journal/info";
                var thisVue = this;
                jQuery.ajax({
                    type: "post",
                    url: '/journal/follow',
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'followJournal', result.followJournal);
                    thisVue.$set(thisVue,'journalExcel',result.journalExcel);
                    thisVue.$set(thisVue,'delay',result.delay);
                    thisVue.$set(thisVue,'miss',result.miss);
                    thisVue.showPage = 'showFollow';
                });
            },
            'CreateExcel':function (index) {
                window.location="/journal/info?index="+index;
            },
            'repairJournal':function (date) {
                window.location="/journal/edit?repairDate="+date+"&type=DAILY";
            },
            'searchAll': function () {
                this.searchList();
            },
            'searchUnRead': function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    isRead: 0
                };
                this.searchList(data);
            },
            'searchMine': function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    isMine: 1
                };
                this.searchList(data);
            },
            'searchDay': function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType: 'DAILY'
                };
                this.searchList(data);

            },
            'searchWeek': function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType: 'WEEKLY'
                };
                this.searchList(data);
            },
            'searchMonth': function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType: 'MONTHLY'
                };
                this.searchList(data);
            },
            'searchFilter': function () {
                //this.isReadTo0_1 = this.bool2Digit(!this.isRead),
                var senderIds = "";
                var clientId = '', clientName = '',
                    projectId = '', projectName = '';
                for (var i = 0; i < this.sendersId.length; i++) {
                    senderIds += this.sendersId[i] + ',';
                }
                if (this.client != ''){
                    var c = this.client.split(',');
                    clientId = c[0];
                    clientName = c[1];
                }
                if(this.project != ''){
                    var p = this.project.split(',');
                    projectId = p[0];
                    projectName = p[1];
                }
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType: this.journalType,
                    customer: clientId,
                    project: projectId,
                    startTime: this.startTime,
                    senderIds: senderIds,
                    endTime: this.endTime
                    // isRead: Number(!this.isRead)
                };
                this.tip = this.mergeTip(clientName, projectName);
                $('#resultTip').show();
                this.searchList(data);
            },
            'mergeTip': function ( clientName, projectName) {
                var mergeTip = '';
                for(var i = 0; i < this.sendersName.length; i++){
                    mergeTip += this.sendersName[i] + ',';
                }
                if (this.startTime!= '' && this.endTime != '') {
                    mergeTip += this.startTime + '到' + this.endTime ;
                    mergeTip += clientName != ''? '与' + clientName: '';
                } else {
                    mergeTip += this.startTime + this.endTime + clientName ;
                }
                mergeTip += projectName;
                return mergeTip + ((TYPE_NAMES[this.journalType] != null) ? '的' + TYPE_NAMES[this.journalType] : '');
            },
            'addSenders': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/journal/subMemberList',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    thisVue.$set(thisVue, 'subMemberList', result.subMemberList);
                    thisVue.showPage = 'selSender';
                });
            },
            'backToFilter': function () {
                this.sendersId = [];
                this.sendersName = [];
                this.tempSenders = [];
                this.showPage = 'filterDiv';
            },
            'finish': function () {
                this.sendersId = [];
                this.sendersName = [];
                for (var i = 0; i < this.tempSenders.length; i++) {
                    var str = this.tempSenders[i].split(',');
                    this.sendersId[i] = str[0];
                    this.sendersName[i] = str[1];
                }
                this.showPage = 'filterDiv';
            },
            'loadDetail': function (journalId) {
            	var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/userJournal',
                    data: {
                        journalId: journalId.journalId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'journalLists', result.journalLists);
                        thisVue.curJournal = journalId;
                        thisVue.showPage = 'journalDetail';
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'loadDetail2': function(journalId){
                window.location="/journal/edit?journalId="+journalId+"&type=DAILY";
            },
            
            'backToList': function () {
                this.showPage = 'journalList';
                this.cancelAddJournalEvent();
            },
            'journalName': function (journal) {
                var prefixName = journal.user.realName;
                if (journal.isMine) {
                    prefixName = '我';
                }
                return prefixName + '的' + TYPE_NAMES[journal.type];
            },
            'clickAddJournalButton': function () {
                this.searchRepair();
                this.showAddJournalDialog = true;
            },
            'cancelAddJournalEvent': function () {
                this.showAddJournalDialog = false;
            },
            'toFilter': function () {
                this.journalType = '';
                this.client = '';
                this.project = '';
                this.startTime = this.endTime = '';
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/journal/customerAndProjects',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'customers', result.customers);
                        thisVue.$set(thisVue, 'projects', result.opportunities);
                        // console.log(result);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
                this.showPage = 'filterDiv';
            },
            'quit': function () {
                this.showPage = 'journalList';
            },
            'patchAction': function (journalId) {
                jQuery("#patchButtonDiv_" + journalId).css("display", "none");
                jQuery("#patchContent_" + journalId).val("");
                jQuery("#patchDiv_" + journalId).css("display", "block");
            },
            'patchSubmit': function (journalId) {
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/action/journalAttachment',
                    data: {
                        journalId: journalId,
                        content: jQuery("#patchContent_" + journalId).val()
                    },
                    dataType: 'json',
                    cache: false,
                    success: function () {
                        location.reload(true);
                    }
                });
                jQuery("#patchButtonDiv_" + journalId).css("display", "block");
                jQuery("#patchDiv_" + journalId).css("display", "none");
            },
            'patchCancelSubmit': function (journalId) {
                jQuery("#patchButtonDiv_" + journalId).css("display", "block");
                jQuery("#patchDiv_" + journalId).css("display", "none");
            },
            'convertVisitType': function (ty) {
                var str = '错误';
                if (ty === 'VISIT') {
                    str = '拜访';
                } else {
                    str = '电话';
                }
                return str;
            }
        }
    });
    journalListVue.searchAll();
});