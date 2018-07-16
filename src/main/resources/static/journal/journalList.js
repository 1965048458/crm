var TYPE_NAMES = {
    'DAILY': '日报',
    'WEEKLY': '月报'
};
var journalList = [
    {
        journalId: 1,
        createTs: '2018-07-09 12:11:11',
        type: 'DAILY',
        summary: '今日总结',
        plan: '明日计划',
        user: {
            realName: '李明'
        }
    },
    {
        journalId: 2,
        createTs: '2018-02-22 12:22:22',
        type: 'WEEKLY',
        summary: '今天做了什么',
        plan: '明日计划',
        user: {
            realName: '吴凡'
        }
    }];
jQuery(document).ready(function () {
    var journalListVue = new Vue({
        el: '#journalVue',
        data: {
            showPage: 'journalList',
            journalList: journalList,
            curJournal: {
                user: {}
            },
            showRead: true
        },
        methods: {
            'searchList': function () {
                //todo 从服务器搜索日志
                this.$set(this, 'journalList', journalList);
                this.showPage = 'journalList';
            },
            'loadDetail': function (journalId) {
                var thisVue = this;
                jQuery.ajax({
                    type: 'get',
                    url: '/journal/detail',
                    data: {
                        journalId: journalId
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    var journal = result.journal;
                    journal.unread = result.unread;
                    journal.read = result.read;
                    thisVue.$set(thisVue, 'curJournal', journal);
                    thisVue.showPage = 'journalDetail';
                });
            },
            'backToList': function () {
                this.showPage = 'journalList';
            },
            'journalName': function (journal) {
                return journal.user.realName + '的' + TYPE_NAMES[journal.type];
            }
        }
    });
    journalListVue.searchList();
});