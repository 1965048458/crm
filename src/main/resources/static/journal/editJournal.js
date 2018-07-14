
var editTitleMap = {
    'DAILY': '填写日报',
    'WEEKLY': '填写周报',
    'MONTHLY': '填写月报'
};
var summaryLabels = {
    'DAILY': '今日总结',
    'WEEKLY': '本周总结',
    'MONTHLY': '本月总结'
};
jQuery(document).ready(function () {
    var journalType = jQuery('#journalType').val();
   var editJournalVue = new Vue({
       el: "#editJournalVue",
       data: {
           showErrMsg: false,
           errMsg: '',
           journalType: journalType,
           showPage: 'journalPage',
           summary: '',
           plan: '',
           visits: [],
           curVisit: {},
           receivers: [],
           receiversTmp: [],
           customers: [{name: '浙江大学', contactsFold: true, contactsGroup:[{realName: '李四'}]},
               {name: '浙江工商大学', contactsFold: true}],
           chosenContactsTmp: [],
           visitTypeTmp: 'VISIT',
           colleagues: [{userId: "userId1", realName: '用户1',avatarUrl: '/images/journal/defaultUserIcon.png'},
               {userId: "userId2", realName: '用户2', avatarUrl: '/images/journal/defaultUserIcon.png'}]
       },
       methods: {
           'backToList': function () {
               jQuery('#draftDiv').show();
           },
           'cancelBackToList': function () {
               jQuery('#draftDiv').hide();
           },
           'saveDraft': function () {
               var postData = {
                   //todo
               };
               this.doSaveJournal(postData);
           },
           'doSaveJournal': function (postData) {
               var thisVue = this;
               jQuery.ajax({
                   type: 'post',
                   url: '/journal/action/editSubmit',
                   data: JSON.stringify(postData),
                   dataType: 'json',
                   contentType: 'application/json',
                   cache: false
               }).done(function (result){
                   if (result.successFlg) {
                       thisVue.doToList();
                   } else {
                       thisVue.errMsg = result.errMsg;
                       thisVue.showErrMsg = true;
                   }
               });
           },
           'doToList': function () {
               window.location = '/journal/toList';
           },
           'deleteVisit': function (index) {
               this.visits.splice(index, 1);
           },
           'addVisit': function () {
               this.visits.push({result: '', visitType: 'VISIT'});
           },
           'addVisitContacts': function (index) {
               this.curVisit = this.visits[index];
               this.$set(this, 'chosenContactsTmp', this.curVisit.chosenContacts);
               this.showPage = 'selectContacts';
           },
           'changeContactsFold': function (index) {
               this.customers[index].contactsFold = !this.customers[index].contactsFold;
           },
           'choseVisitType': function (index) {
               this.curVisit = this.visits[index];
               this.visitTypeTmp = this.curVisit.visitType;
               this.showPage='selectVisitType';
           },
           'editReceivers': function () {
               this.receiversTmp = this.receivers;
               this.showPage='selectReceiver';
           },
           'submitJournal': function () {
               var postData = {
                   //todo
               };
               this.doSaveJournal(postData);
           },
           'cancelSelectContacts': function () {
               this.$set(this, 'chosenContactsTmp', []);
               this.showPage = 'journalPage';
           },
           'confirmSelectContacts': function () {
               this.curVisit.chosenContacts = this.chosenContactsTmp;
               this.$set(this, 'chosenContactsTmp', []);
               this.showPage = 'journalPage';
           },
           'cancelSelectVisitType': function () {
               this.visitTypeTmp = 'VISIT';
               this.showPage = 'journalPage';
           },
           'confirmSelectVisitType': function () {
               this.curVisit.visitType = this.visitTypeTmp;
               this.visitTypeTmp = 'VISIT';
               this.showPage = 'journalPage';
           },
           'cancelSelectReceiver': function () {
               this.showPage='journalPage';
           },
           'confirmSelectReceiver': function () {
               this.receivers = this.receiversTmp;
               this.showPage = 'journalPage';
           }
       },
       computed: {
           'editTitle': function () {
               return editTitleMap[this.journalType];
           },
           'summaryLabel': function () {
               return summaryLabels[this.journalType];
           }
       }
   });
});

function setHasSubmitted() {
    $('#hasSubmitted').val('true');
}

function sendAjax() {
    var postData = {
        "type": "DAILY",
        "summary": "今天做了什么",
        "plan": "明日计划",
        "hasSubmitted": false,
        "visitRecords": [{
            "visitName": "今日拜访1",
            "contactsIds": [""],
            "visitType": "PHONE",
            "visitResult": "拜访结果"
        }],
        "receivers": [
            {"userId": "001c52e79ee0484ca8158e926b5c05a3"},
            {"userId": "0022287b3f7a404d8fcca44aa76842c2"}
        ]
    };
    $.ajax({
        url: "/journal/testJson",
        cache: false,
        data: JSON.stringify(postData),
        contentType: "application/json; charset=utf-8",
        type: "POST"
    }).done(function (res) {
        console.log(res);
    });
}