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
            customers: [],
            projects: [],
            subMemberList: [],
            sendersId: [],
            sendersName: [],
            tempSenders: [],
            showRead: true,
            journalType: "",
            client: "",
            project: "",
            startTime: "",
            endTime: "",
            isRead: "",
            tip: '',
            errMsg: "",
            showErrMsg: false
        },
        methods: {
            'searchList': function (data) {
                //todo 从服务器搜索日志
                console.log(data);
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/list',
                    data: data,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        console.log(result);
                        thisVue.$set(thisVue, 'journalList', result.journalList);
                        thisVue.showPage = 'journalList';
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'searchAll': function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                };
                this.searchList(data);
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
                // var thisVue = this;
                // jQuery.ajax({
                //     type: 'get',
                //     url: '/journal/detail',
                //     data: {
                //         journalId: journalId
                //     },
                //     dataType: 'json',
                //     cache: false
                // }).done(function (result) {
                //     var journal = result.journal;
                //     journal.unread = result.unread;
                //     journal.read = result.read;
                //     thisVue.$set(thisVue, 'curJournal', journal);
                //     thisVue.showPage = 'journalDetail';
                // });
                this.curJournal = journalId;
                this.showPage = 'journalDetail';
            },
            'backToList': function () {
                this.showPage = 'journalList';
            },
            'journalName': function (journal) {
                var prefixName = journal.user.realName;
                if (journal.isMine) {
                    prefixName = '我';
                }
                return prefixName + '的' + TYPE_NAMES[journal.type];
            },
            'clickAddJournalButton': function () {
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
                        console.log(result);
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