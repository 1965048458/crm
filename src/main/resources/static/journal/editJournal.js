function handleTime(time) {
    if (time < 10)
        return "0" + time;
    else
        return time;
}
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
    var repairDate=jQuery('#repairDate').val();
    console.log(repairDate);

   var editJournalVue = new Vue({
       el: "#editJournalVue",
       data: {
           showErrMsg: false,
           errMsg: '',
           journalType: journalType,
           journalId: journalId,
           repairDate:repairDate,
           createTs:null,
           showPage: 'journalPage',
           summary: '',
           content:'',
           preDate:'',
           searchWord:' ',
           lastStage:'',
           showOrganization: true,
           selStage: '',
           deliverDate:'',
           saleStage:'',
           plan: '',
           opportunityName:'',
           contact:'',
           amount:'',
           visits: [],
           curVisit: {},
           receivers: [],
           opportunityTmp: '',
           opportunities: [],
           stages: ['拿到老师手机及微信号', '提交方案', '以我方提供参数挂标', '中标'],
           ind: ['A', 'B', 'C', 'D'],
           opportunity:'',
           receiversTmp: [],
           customers: [{name: '浙江大学', contactsFold: true, contactsGroup:[{realName: '李四'}, {realName: '李五'}, {realName: '李六'}]},
               {name: '浙江工商大学', contactsFold: true, contactsGroup:[{realName:'张三'}, {realName: '张斯'}, {realName: '张武'}]}],
           ccustomers: [{name: '浙江大', contactsFold: true, contactsGroup:[{realName: '李四'}]}],
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
           'modifBack2': function () {
        	   this.showPage = 'selectOpportunity';
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
                       setTimeout(function () {
                           //thisVue.showDetailResult();
                           thisVue.start();
                           thisVue.showPage = 'selectOpportunity';

                       }, 1000);
                   }
               });
           },
           searchCompany: function () {
               var thisVue = this;
               jQuery.ajax({
                   type: 'get',
                   url: '/journal/searchCompany',
                   data: {
                   	searchWord:this.searchWord,
                   },
                   dataType: 'json',
                   cache: false
               }).done(function (result) {
                   if (result.successFlg) {
                           thisVue.$set(thisVue, 'ccustomers', result.ccustomer);
   
                   }
               });

           },
           'deliverDatePicker':function (){
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
           'selSaleStage':function (){
        	   this.showPage = 'saleStage';
           },
           'chooseBack': function () {
               this.showPage = 'modif';
           },
           clear:function () {
               this.searchWord="";
               $('#searchInput').focus();
           },
           text:function () {
               $('#searchBar').addClass('weui-search-bar_focusing');
               $('#searchText').focus();
               $('#searchResult').show();
               this.searchWord='';
               this.showOrganization = false;
           },
           cancel:function () {
               this.cancelSearch();
               this.showOrganization = true;
               $('#searchInput').blur();
           },
           cancelSearch:function () {
               this.hideSearchResult();
               $('#searchBar').removeClass('weui-search-bar_focusing');
               $('#searchText').show();

           },
           hideSearchResult:function () {
               $('#searchResult').hide();
               this.searchWord = "";
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
               this.showPage = 'modif';
           },
           'modif': function () {
        	   this.showPage = 'modif';
               this.preDate = this.opportunity.checkDateString;
               this.deliverDate = this.opportunity.clinchDateString;
               if (this.opportunity.salesStatus == 'F') {
                   this.saleStage = '输单';
               } else {
                   this.saleStage = this.opportunity.salesStatus + '阶段';
               }
               this.content = this.opportunity.content;
               this.opportunityName=this.opportunity.opportunityName;
               this.amount=this.opportunity.amount;
           },
           'showDetailResult': function (index) {
               var thisVue = this;
               $.ajax({
                   type: 'get',
                   url: '/opportunity/opportunityDetail',
                   data: {
                       opportunityId: thisVue.opportunities[index].opportunityId,
                   },
                   dataType: 'json',
                   cache: false
               }).done(function (result) {
                   if (result.successFlg) {
                       thisVue.$set(thisVue, 'opportunity', result.opportunity);
                       thisVue.$set(thisVue, 'lastStage', result.opportunity.salesStatus);
                       if (result.contact != null) {
                           thisVue.$set(thisVue, 'contact', result.contact);
                       } else {
                           thisVue.$set(thisVue, 'contact', '');
                       }
                       thisVue.modif();
                   }
               })
           },
           'cancelBackToList': function () {
               jQuery('#draftDiv').hide();
           },
           'saveDraft': function () {
               /*if (!this.isJournalSubmitted) {
                   var result = this.prepareData();
                   result.hasSubmitted = true;
                   this.doSaveJournal(result);
               }*/
           },
           'modifBack': function () {
        	   this.showPage = 'selectOpportunity';
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
           },
           'changeContactsFold2': function (index) {
               this.ccustomers[index].contactsFold = !this.ccustomers[index].contactsFold;
           },
           'setImgPath':function (index) {
               if(this.customers[index].contactsFold){
                   return "/images/customer/fold.svg";
               }else {
                   return"/images/customer/unfold.svg";
               }
           },
           'setImgPath2':function (index) {
               if(!this.customers[index].contactsFold){
                   return "/images/customer/fold.svg";
               }else {
                   return"/images/customer/unfold.svg";
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
               if (this.createTs!==null)
               {
                   result['createTs']=this.createTs;
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
                   var depts = customer.depts;
                   for (var j in depts) {
                       var  a1=depts[j].contactsList;
                       for (var k in a1)
                       {
                           if (a1[k].contactsId === contactsId)
                               return a1[k];
                       }
                       var  d1=depts[j].departmentList;
                       for (var n in d1)
                       {
                           var a2=d1[n].contactsList;
                           for (var m in a2)
                           {
                               if (a2[m].contactsId === contactsId)
                                   return a2[m];
                           }
                       }
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
           'onTransferValue': function (contactsInfo,contactsInfo2) {
        	   this.chosenContactsTmp=contactsInfo.concat(contactsInfo2);
           },
           'start':function(){
        	   if (journalId === '0') {
        	        jQuery.ajax({
        	            type: 'get',
        	            url: '/journal/action/getColleagueList',
                        data:
                            {
                                repairDate:this.repairDate
                            },
        	            dataType: 'json',
        	            cache: false,
        	            success: function(result) {
        	                editJournalVue.$set(editJournalVue, 'createTs', result.createTs);
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
/*           'customer':{
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
           },*/           
        },
        watch:{
        	'searchWord': function () {
                var thisVue = this;
                this.searchCompany();
            },
            'chosenContactsTmp': function () {

                this.$emit('transfer_value', this.chosenContactsTmp,this.chosenContactsTmp1);
            }
        }
   });
   Vue.component('ddd',{
	   template: '#customer3',
       props: ['ccc'],
       data: function () {
           return {
               showSub: false,
               chosenContactsTmp:[],
               chosenContactsTmp1:[],
               showCustomerOrganization:true,
               showOrganization: false,
               showApplyDialog: false,
               showApply: true,
               imgPath:"/images/customer/fold.svg",

           };
       },
       methods: {

           'changeSubFold' : function (status) {
               if(status == 'MINE'|| (status == '' || status == undefined)){

                  this.showSub= !this.showSub;
                   
                   this.setImgPath();
               } else {
                   this.showSub = false;
                   this.setImgPath();
               }

           },
           'onTransferValue': function (contactsInfo,contactsInfo2) {
        	   if(contactsInfo2==null||contactsInfo2.length==0)
        		{
        		   this.chosenContactsTmp1=contactsInfo;
        		}
        	    else
        	    {
        	    	this.chosenContactsTmp1=contactsInfo.concat(contactsInfo2);
        	    }
           },
           'setImgPath':function () {
               if(this.showSub == false){
                   this.imgPath = "/images/customer/fold.svg";
               }else {
                   this.imgPath = "/images/customer/unfold.svg";
               }
           },
           'checkGender':function(gender){
               if(gender == 'FEMALE'){
                   return "/images/customer/FEMALE.svg";
               }else{
                   return "/images/customer/MALE.svg";
               }
           },
           'addNumBrackets':function (number,status) {
               if(status == 'ENCLOSURE' || number == '0'){
                   return '';
               }else{
                   return "( "+number+" )";
               }
           },
           'addMineBrackets':function (status,applyName) {
               if (status == 'MINE' ){
                   if(applyName == '' || applyName == undefined){
                       return "[ 我的 ]"
                   }else {
                       return "[ "+ applyName + "]"
                   }

               }
           },
           'addEnclosureBrackets':function (status) {
               if(status == 'ENCLOSURE'){
                   return "[ 别人正在申请 ]"
               }
           },
           'addNormalBrackets':function (status) {
               if(status == 'NORMAL'){
                   return "[ 未圈 ]"
               }
           },
           'addApplyingBrackets':function (status,applyName) {
               if(status == 'APPLYING'){
                   if(applyName == '' || applyName == undefined){
                       return "[ 待审核 ]";
                   }else {
                       return "["+applyName+"] [ 待审核 ]";
                   }

               }
           },
           'addOpenSeaWarning':function (warning) {
               if(warning != null){
                   if(warning.isDelayApplied == true){
                       return "!!已申请延期";
                   }else {
                       return "!!即将进入公海";
                   }
               }
               else {
                   return '';
               }
           },
           'apply':function (name,id) {
               organizationVue.apply(name,id);
               console.log(name,id);
           },
           'openSeaWarning':function (warning) {
               console.log("component.warning")
               organizationVue.openSeaWarningDetail(warning);
               
           },
           'toContactDetail':function (contactsId) {
               window.location = '/customer/contactsInfo?contactsId='+contactsId;
           },
           'addTypeName':function (contact) {
               if(contact.typeName == undefined || contact.typeName==''){
                   $("#type_name_label_"+contact.contactsId).css("border","hidden");
                   return '';
               }else {
                   return contact.typeName;
               }
               
           }
     },
     watch: {
         'chosenContactsTmp': function () {

             this.$emit('transfer_value', this.chosenContactsTmp,this.chosenContactsTmp1);
         },
         'chosenContactsTmp1': function () {

             this.$emit('transfer_value', this.chosenContactsTmp,this.chosenContactsTmp1);
         }
     }
   });
   Vue.component('bbb',{
	   template: '#customer3',
       props: ['ccc'],
       data: function () {
           return {
               showSub: true,
               chosenContactsTmp:[],
               chosenContactsTmp1:[],
               showCustomerOrganization:true,
               showOrganization: false,
               showApplyDialog: false,
               showApply: false,
               imgPath:"/images/customer/unfold.svg",

           };
       },
       methods: {
           'changeSubFold' : function (status) {
               if(status == 'MINE'|| (status == '' || status == undefined)){

                  this.showSub= !this.showSub;
                   
                   this.setImgPath();
               } else {
                   this.showSub = false;
                   this.setImgPath();
               }

           },
           'onTransferValue': function (contactsInfo,contactsInfo2) {
        	   if(contactsInfo2==null||contactsInfo2.length==0)
        		{
        		   this.chosenContactsTmp1=contactsInfo;
        		}
        	    else
        	    {
        	    	this.chosenContactsTmp1=contactsInfo.concat(contactsInfo2);
        	    }
           },
           'setImgPath':function () {
               if(this.showSub == false){
                   this.imgPath = "/images/customer/fold.svg";
               }else {
                   this.imgPath = "/images/customer/unfold.svg";
               }
           },
           'checkGender':function(gender){
               if(gender == 'FEMALE'){
                   return "/images/customer/FEMALE.svg";
               }else{
                   return "/images/customer/MALE.svg";
               }
           },
           'addNumBrackets':function (number,status) {
               if(status == 'ENCLOSURE' || number == '0'){
                   return '';
               }else{
                   return "( "+number+" )";
               }
           },
           'addMineBrackets':function (status,applyName) {
               if (status == 'MINE' ){
                   if(applyName == '' || applyName == undefined){
                       return "[ 我的 ]"
                   }else {
                       return "[ "+ applyName + "]"
                   }

               }
           },
           'addEnclosureBrackets':function (status) {
               if(status == 'ENCLOSURE'){
                   return "[ 别人正在申请 ]"
               }
           },
           'addNormalBrackets':function (status) {
               if(status == 'NORMAL'){
                   return "[ 未圈 ]"
               }
           },
           'addApplyingBrackets':function (status,applyName) {
               if(status == 'APPLYING'){
                   if(applyName == '' || applyName == undefined){
                       return "[ 待审核 ]";
                   }else {
                       return "["+applyName+"] [ 待审核 ]";
                   }

               }
           },
           'addOpenSeaWarning':function (warning) {
               if(warning != null){
                   if(warning.isDelayApplied == true){
                       return "!!已申请延期";
                   }else {
                       return "!!即将进入公海";
                   }
               }
               else {
                   return '';
               }
           },
           'apply':function (name,id) {
               organizationVue.apply(name,id);
               console.log(name,id);
           },
           'openSeaWarning':function (warning) {
               console.log("component.warning")
               organizationVue.openSeaWarningDetail(warning);
               
           },
           'toContactDetail':function (contactsId) {
               window.location = '/customer/contactsInfo?contactsId='+contactsId;
           },
           'addTypeName':function (contact) {
               if(contact.typeName == undefined || contact.typeName==''){
                   $("#type_name_label_"+contact.contactsId).css("border","hidden");
                   return '';
               }else {
                   return contact.typeName;
               }
               
           }
     },
     watch: {
         'chosenContactsTmp': function () {

             this.$emit('transfer_value', this.chosenContactsTmp,this.chosenContactsTmp1);
         },
         'chosenContactsTmp1': function () {

             this.$emit('transfer_value', this.chosenContactsTmp,this.chosenContactsTmp1);
         }
     }
   });
   editJournalVue.start();
    window.onbeforeunload = function() {
        editJournalVue.saveDraft();
    }

});