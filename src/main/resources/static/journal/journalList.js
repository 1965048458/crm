var TYPE_NAMES = {
    'DAILY': '日报',
    'WEEKLY': '周报'
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
            senders: [],
            showRead: true,
            journalType: "",
            client: "",
            project: "",
            startTime: "",
            endTime: "",
            isRead: "",
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
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType: this.journalType,
                    client: this.client,
                    project: this.project,
                    startTime: this.startTime,
                    endTime: this.endTime,
                    isRead: Number(!this.isRead)
                };
                this.searchList(data);
            },
            'addSenders':function () {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    url:'',
                    //data:
                });
            },
            'finish':function () {
                //
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