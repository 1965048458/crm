jQuery(document).ready(function () {
    var addCustomerVue = new Vue({
        el: '#addCustomerVue',
        data: {
            showPage: 'addCustomer',
            customerType:'',
            schoolType: '',
            name:'',
        },
        methods: {
            'selectCus': function (data) {
                this.showPage = 'selCusType';
            },
            'selectSch':function () {
                this.showPage = 'selSchType';
            },
            'selectNam':function () {
                this.showPage = 'selName';
            },
            'cancel':function () {
                this.showPage = 'addCustomer';
            },
            'submit':function () {
                this.showPage = 'addCustomer';
            },
            'searchWeek':function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType:'WEEKLY'
                };
                this.searchList(data);
            },
            'searchMonth':function () {
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType:'month'
                };
                this.searchList(data);
            },
            'searchFilter':function () {
                //this.isReadTo0_1 = this.bool2Digit(!this.isRead),
                var data = {
                    //userId:"00284bca325c4e77b9f30c5671ec1c44",
                    journalType:this.journalType,
                    client:this.client,
                    project:this.project,
                    startTime:this.startTime,
                    endTime:this.endTime,
                    isRead:Number(!this.isRead)
                };
                this.searchList(data);
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
            },
        }
    });
});