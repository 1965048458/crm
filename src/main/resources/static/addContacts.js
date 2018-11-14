
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
            phone: '',
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
                    phone: thisVue.phone,
                    wechat: thisVue.wechat,
                    QQ: thisVue.qq,
                    email: thisVue.email,
                    officeAddr: thisVue.officeAddr,
                    profile: thisVue.profile,
                    specialRelationship: thisVue.specialRelationship
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

               }
        }
    });

    addContactsVue.updateContactsTypes();


});