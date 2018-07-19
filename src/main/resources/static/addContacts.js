
jQuery(document).ready(function () {

    var addContactsVue = new Vue({
        el: "#addContactsVue",
        data: {
            showErrMsg: false,
            errMsg: '',
            showPage: 'addContactsPage',
            isTopDept: jQuery('#isTopDept').val(),
            contactsTypeId: null,
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
            destLocation: "/customer/organization",
            contactsTypeList: []
        },
        methods: {
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

                if (jQuery('#isTopDept').val() === 'true') {
                    //doNoting
                } else {
                    uploadData['contactsTypeId'] = this.contactsTypeId;
                }

                jQuery.ajax({
                    type: 'post',
                    url: '/customer/action/addContacts',
                    data: uploadData,
                    dataType: 'json',
                    cache: false
                }).done(function (result){
                    if (result.successFlg) {
                        window.location = thisVue.location;
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
            }
        }
    });

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
});