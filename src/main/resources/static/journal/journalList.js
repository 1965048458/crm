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
            journalList:'',
            curJournal: {
                user: {}
            },
            showRead: true,
            journalType: "",
            client:"",
            project:"",
            startTime:"",
            endTime:"",
            isRead:"",
            isReadTo0_1:""


        },
        methods: {
            'searchList': function (data) {
                //todo 从服务器搜索日志
                console.log(data);
                var thisVue = this;
                jQuery.ajax({
                    type:'get',
                    url:'/journal/list',
                    data:data,
                    dataType:'json',
                    cache:false
                }).done(function(result){
                    //var journalList = result;
                    //journal.unread = result.unread;
                    //journal.read = result.read;

                    thisVue.$set(thisVue, 'journalList', result.journalList);
                    thisVue.showPage = 'journalList';
                })
                //this.$set(this, 'journalList', journalList);
                //this.showPage = 'journalList';
            },
            'searchAll':function () {
                var data = {};
                this.searchList(data);

            },
            'searchUnRead':function () {
                var data = {
                    isRead:0
                };
                this.searchList(data);

            },
            'searchMine':function () {
                var data = {
                    isMy:1
                };
                this.searchList(data);

            },
            'searchDay':function () {
                var data = {
                    userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType:'DAILY'
                };
                this.searchList(data);

            },
            'searchWeek':function () {
                var data = {
                    journalType:'week'
                };
                this.searchList(data);
            },
            'searchMonth':function () {
                var data = {
                    journalType:'month'
                };
                this.searchList(data);
            },
            'searchFilter':function () {
                //this.isReadTo0_1 = this.bool2Digit(!this.isRead),
                var data = {
                    journalType:this.journalType,
                    client:this.client,
                    project:this.project,
                    startTime:this.startTime,
                    endTime:this.endTime,
                    isRead:Number(!this.isRead)
                };
                this.searchList(data);
            },
            'bool2Digit':function (bool) {
                if(bool==false)this.isReadTo0_1=0;
                if(bool==true)this.isReadTo0_1=1;
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
            },
            'toFilter':function () {
                this.showPage = 'filterDiv';
            },
            'quit':function () {
                this.showPage = 'journalList';

            }
        }
    });
    journalListVue.searchList();
});