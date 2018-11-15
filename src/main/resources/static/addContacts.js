
jQuery(document).ready(function () {

    var addContactsVue = new Vue({
        el: "#addContactsVue",
        data: {
            showErrMsg: false,
            showAddContactsType: false,
            errMsg: '',
            showPage: 'addContactsPage',
            isTopDept: 'false',
            curContactsType: null,
            realName: '',
            gender: null,
            tel: '',
            contactsId:'',
            phone: '',
            title:'添加联系人',
            wechat: '',
            qq: '',
            email: '',
            officeAddr: '',
            profile: '',
            specialRelationship: '',
            destLocation: "/customer/editCustomer?customerId=" + jQuery('#customerId').val(),
            contactsTypeList: []
        },
        methods: {
            'updateContactsTypes': function () {
            	this.title='添加联系人';
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/action/getContactsTypeList',
                    data: {
                        deptId: jQuery('#deptId').val()
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.successFlg) {
                            addContactsVue.$set(addContactsVue, 'contactsTypeList', result.contactsTypes);
                        }
                    }
                });
            },
            'cancelAddContacts': function () {
                window.location = this.destLocation;
            },
            'confirmAddContacts': function () {

                var thisVue = this;
                var uploadData = {
                    deptId: jQuery('#deptId').val(),
                    realName: thisVue.realName,
                    gender: thisVue.gender,
                    tel: thisVue.tel,
                    contactsId:thisVue.contactsId,
                    phone: thisVue.phone,
                    wechat: thisVue.wechat,
                    QQ: thisVue.qq,
                    email: thisVue.email,
                    officeAddr: thisVue.officeAddr,
                    profile: thisVue.profile,
                    specialRelationship: thisVue.specialRelationship,
                    title:thisVue.title           
                };

                    if (this.curContactsType == null) {
                        uploadData['contactsTypeId'] = null;
                    } else {
                        uploadData['contactsTypeId'] = this.curContactsType.contactsTypeId;
                    }

                jQuery.ajax({
                    type: 'post',
                    url: '/customer/action/addContacts',
                    data: uploadData,
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location = thisVue.destLocation;
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'editContacts':function (id) {
            	var thisVue = this;
            	thisVue.title="编辑联系人";
                jQuery.ajax({
                    type: 'get',
                    url: '/customer/action/getContacts',
                    data: {
                    	contactsId: id
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.successFlg) {
                            addContactsVue.$set(addContactsVue, 'curContactsType', result.contacts.contactsType);
                            addContactsVue.$set(addContactsVue, 'realName', result.contacts.realName);
                            addContactsVue.$set(addContactsVue, 'gender', result.contacts.gender);
                            addContactsVue.$set(addContactsVue, 'tel', result.contacts.tel);
                            addContactsVue.$set(addContactsVue, 'phone', result.contacts.phone);
                            addContactsVue.$set(addContactsVue, 'wechat', result.contacts.wechat);
                            addContactsVue.$set(addContactsVue, 'qq', result.contacts.QQ);
                            addContactsVue.$set(addContactsVue, 'email', result.contacts.email);
                            addContactsVue.$set(addContactsVue, 'officeAddr', result.contacts.officeAddr);
                            addContactsVue.$set(addContactsVue, 'profile', result.contacts.profile);
                        }
                    }
                });
            },
            'chooseGender': function () {
                this.showPage = 'selectGenderPage';
            },
            'confirmGender': function () {
                this.showPage = 'addContactsPage';
            },
            'chooseContactsType': function () {
                this.showPage = 'selectContactsTypePage';
            },
            'confirmContactsType': function () {
                this.showPage = 'addContactsPage';
            },
            'clickAddContactsType': function () {
                jQuery('#newContactsTypeName').val("");
                this.showAddContactsType = true;
            },
            'cancelAddContactsType': function () {
                this.showAddContactsType = false;
            },
            'confirmAddContactsType': function () {

                var thisVue = this;

                jQuery.ajax({
                    type: 'post',
                    url: '/customer/action/addContactsType',
                    data: {
                        customerId: jQuery('#customerId').val(),
                        contactsTypeName: jQuery('#newContactsTypeName').val()
                    },
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        if (result.successFlg) {
                            thisVue.updateContactsTypes();
                        } else {
                            thisVue.errMsg=result.errMsg;
                            thisVue.showErrMsg = true;
                        }
                    }
                });


                this.showAddContactsType = false;
            }
        },
        computed: {
            'genderString': function() {
                if (this.gender == null) {
                    return '请选择';
                } else if (this.gender === 'MALE') {
                    return '男';
                } else if (this.gender === 'FEMALE') {
                    return '女';
                } else {
                    return '请选择';
                }
            },
            'contactsTypeString': function() {
                if (this.curContactsType == null) {
                    return '请选择';
                } else {
                    return this.curContactsType.typeName;
                }
            }
        },
        watch : {
        	'phone':function(val) {
  
				var l=val.charAt(val.length-1);
				if (val != "" && val != null&&isNaN(l)) {
					val=val.substring(0,val.length-1);
				}
				else if(val.length===13)
				{
					val=val.substring(0,val.length-1);
				}
				else
				{
					if(val.startsWith("01")||val.startsWith("02")||val.startsWith("85"))
					{
						if(val.length===4 && l!='-')
						{
							val=val.substring(0,val.length-1)+"-"+l;
						}
					}
					else
					{
						if(val.length===5 && l!='-')
						{
							val=val.substring(0,val.length-1)+"-"+l;
						}
					}
				}
				this.phone=val;
               },
               'tel':function(val) {
            	   
   				var l=val.charAt(val.length-1);
   				if (val != "" && val != null&&isNaN(l)) {
   					val=val.substring(0,val.length-1);
   				}
   				else if(val.length===12)
   				{
   					val=val.substring(0,val.length-1);
   				}
   				
   				this.tel=val;
                },
        }
    });

    addContactsVue.updateContactsTypes();
    var contactsId = $("#contactsId").val();
    if(contactsId!=null&&contactsId!='')
    {
    	addContactsVue.contactsId=contactsId;
    	addContactsVue.editContacts(contactsId);
    }
});