
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
var planLabels = {
    'DAILY': '明日计划',
    'WEEKLY': '下周总结',
    'MONTHLY': '下月总结'
};

jQuery(document).ready(function () {

    var journalId = jQuery('#journalId').val();
    var journalType = jQuery('#journalType').val();

   var editJournalVue = new Vue({
       el: "#editJournalVue",
       data: {
           showErrMsg: false,
           errMsg: '',
           journalType: journalType,
           journalId: journalId,
           showPage: 'journalPage',
           summary: '',
           plan: '',
           visits: [],
           curVisit: {},
           receivers: [],
           opportunityTmp: '',
           opportunities: [],
           receiversTmp: [],
           customers: [{name: '浙江大学', contactsFold: true, contactsGroup:[{realName: '李四'}, {realName: '李五'}, {realName: '李六'}]},
               {name: '浙江工商大学', contactsFold: true, contactsGroup:[{realName:'张三'}, {realName: '张斯'}, {realName: '张武'}]}],
           chosenContactsTmp: [],
           visitTypeTmp: 'VISIT',
           colleagues: [{userId: "userId1", realName: '用户1',avatarUrl: '/images/journal/defaultUserIcon.png'},
               {userId: "userId2", realName: '用户2', avatarUrl: '/images/journal/defaultUserIcon.png'}],
           isJournalSubmitted: false,
           imgPath:"/images/customer/fold.svg"
       },
       methods: {
           'backToList': function () {
               window.location = "/journal/toList";
               //jQuery('#draftDiv').show();
           },
           'cancelBackToList': function () {
               jQuery('#draftDiv').hide();
           },
           'saveDraft': function () {
               if (!this.isJournalSubmitted) {
                   var result = this.prepareData();
                   result.hasSubmitted = false;
                   this.doSaveJournal(result);
               }
           },
           'doSaveJournal': function (postData) {
               console.log(postData);
               var thisVue = this;
                if(this.summary=='' && this.plan ==''){
                    return;
                }else {
                    jQuery.ajax({
                        type: 'post',
                        url: '/journal/action/editSubmit',
                        data: JSON.stringify(postData),
                        dataType: 'json',
                        contentType: 'application/json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            thisVue.doToList();
                        } else {
                            thisVue.errMsg = result.errMsg;
                            thisVue.showErrMsg = true;
                        }
                    });
                    this.isJournalSubmitted = true;
                }
           },
           'doToList': function () {
               window.location = '/journal/toList';
           },
           'deleteVisit': function (index) {
               this.visits.splice(index, 1);
           },
           'addVisit': function () {
               this.visits.push({visitResult: '', visitType: 'VISIT',chosenContacts:[], opportunityId:''});
           },
           'addVisitContacts': function (index) {
               this.curVisit = this.visits[index];
               if (this.curVisit.chosenContacts == null) {
                   this.curVisit.chosenContacts = [];
               }
               this.$set(this, 'chosenContactsTmp', this.curVisit.chosenContacts);
               this.showPage = 'selectContacts';
           },
           'changeContactsFold': function (index) {
               this.customers[index].contactsFold = !this.customers[index].contactsFold;
               this.setImgPath(index);
           },
           'setImgPath':function (index) {
               if(this.customers[index].contactsFold){
                   this.imgPath = "/images/customer/fold.svg";
               }else {
                   this.imgPath = "/images/customer/unfold.svg";
               }
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
               var result = this.prepareData();
               result.hasSubmitted = true;
               this.doSaveJournal(result);
           },
           'cancelSelectContacts': function () {
               this.$set(this, 'chosenContactsTmp', []);
               this.showPage = 'journalPage';
               this.curVisit.chosenContacts = [];
           },
           'confirmSelectContacts': function () {
               this.curVisit.chosenContacts = this.chosenContactsTmp;
               console.log(this.chosenContactsTmp);
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
           },
           'prepareData': function () {
               var result = {
                   type: this.journalType,
                   summary: this.summary,
                   plan: this.plan,
                   hasSubmitted: false,
                   visitRecords: this.visits,
                   receivers: this.receivers
               };
               if (this.journalId !== '0') {
                   result['journalId'] = this.journalId;
               }
               return result;
           },
           'getVisitTypeName': function (visitType) {
               if (visitType === 'VISIT') {
                   return "拜访";
               } else if (visitType === 'PHONE') {
                   return "电话";
               } else {
                   return "未定义";
               }
           },
           'calcVisitCustomerName': function (visit) {
               var contacts = visit.chosenContacts;
               if (contacts == null || contacts.length === 0) {
                   return "无";
               }
               var str = contacts[0].realName;
               for (var i = 1; i < contacts.length && i < 3; i=i+1) {
                   str = str + "、" + contacts[i].realName;
               }
               if (contacts.length > 3) {
                   str = str + "等";
               }
               return str;
           },
           'calculateOpportunityName': function (opportunityId) {
                if (opportunityId == null || opportunityId === '') {
                    return "请选择";
                }
                for (var i in this.opportunities) {
                    var o = this.opportunities[i];
                    if (opportunityId === o.opportunityId) {
                        return o.opportunityName;
                    }
                }
                return "无";
           },
           'queryContacts': function (contactsId) {
               for (var i in this.customers) {
                   var customer = this.customers[i];
                   var contactsGroup = customer.contactsGroup;
                   for (var j in contactsGroup) {
                       if (contactsGroup[j].contactsId === contactsId)
                           return contactsGroup[j];
                   }
               }
               return null;
           },
           'chooseOpportunity': function (index) {
               this.curVisit = this.visits[index];
               this.opportunityTmp = this.curVisit.opportunityId;
               this.showPage = 'selectOpportunity';
           },
           'cancelSelectOpportunity': function () {
               this.opportunityTmp = '';
               this.showPage = 'journalPage';
           },
           'confirmSelectOpportunity': function () {
               this.curVisit.opportunityId = this.opportunityTmp;
               this.opportunityTmp = '';
               this.showPage = 'journalPage';
           },
           'onTransferValue': function (contactsInfo) {
               this.chosenContactsTmp=this.chosenContactsTmp.concat(contactsInfo);
           }
       },
       computed: {
           'editTitle': function () {
               return editTitleMap[this.journalType];
           },
           'summaryLabel': function () {
               return summaryLabels[this.journalType];
           },
           'planLabel': function () {
               return planLabels[this.journalType];
           },
           'summaryPlaceHolder': function () {
               return "请输入" + summaryLabels[this.journalType];
           },
           'planPlaceHolder': function () {
               return "请输入" + planLabels[this.journalType];
           }
       },
       components:{
           'customer':{
               template:'#customer',
               props:['customer','index'],
               data:function () {
                   return{
                       chosenContactsTmp:[],
                       showSub:false,
                       imgPath:"/images/customer/fold.svg"
                   }
               },
               methods:{
                   'changeSubFold' : function () {
                       this.showSub = !this.showSub;
                       this.setImgPath();
                   },
                   'setImgPath':function () {
                       if(this.showSub == false){
                           this.imgPath = "/images/customer/fold.svg";
                       }else {
                           this.imgPath = "/images/customer/unfold.svg";
                       }
                   }
               },
               watch: {
                   'chosenContactsTmp': function () {

                       this.$emit('transfer_value', this.chosenContactsTmp);
                   }
               }
           }
       }
   });

    if (journalId === '0') {
        jQuery.ajax({
            type: 'get',
            url: '/journal/action/getColleagueList',
            dataType: 'json',
            cache: false,
            success: function(result) {
                editJournalVue.$set(editJournalVue, 'colleagues', result.colleagues);
                editJournalVue.$set(editJournalVue, 'customers', result.customer);
                editJournalVue.$set(editJournalVue, 'opportunities', result.opportunities);
            }
        });
    } else {
        jQuery.ajax({
            type: 'get',
            url: '/journal/query?journalId='+journalId,
            dataType: 'json',
            cache: false,
            success: function(result) {
                editJournalVue.$set(editJournalVue, 'summary', result.journal.summary);
                editJournalVue.$set(editJournalVue, 'plan', result.journal.plan);
                editJournalVue.$set(editJournalVue, 'visits', result.journal.visitRecords);
                editJournalVue.$set(editJournalVue, 'colleagues', result.colleagues);
                editJournalVue.$set(editJournalVue, 'customers', result.customer);
                editJournalVue.$set(editJournalVue, 'opportunities', result.opportunities);
                console.log(editJournalVue.customers);
                console.log(result.journal.receivers);
                console.log(result.colleagues);
                for (var revId in result.journal.receivers) {
                    for (var colId in result.colleagues) {
                        if (result.journal.receivers[revId].userId === result.colleagues[colId].userId) {
                            editJournalVue.receivers.push(result.colleagues[colId]);
                        }
                    }
                }
                for (var visitId in editJournalVue.visits) {
                    var visit = editJournalVue.visits[visitId];
                    var chosenContacts = visit.chosenContacts;
                    for (var ind in chosenContacts) {
                        chosenContacts[ind] = editJournalVue.queryContacts(chosenContacts[ind].contactsId);
                    }
                }
            }
        });
    }

    window.onbeforeunload = function() {
        editJournalVue.saveDraft();
    }

});