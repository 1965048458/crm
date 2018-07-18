
jQuery(document).ready(function () {

    var addContactsVue = new Vue({
        el: "#addContactsVue",
        data: {
            showErrMsg: false,
            errMsg: '',
            showPage: 'addContactsPage',
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
            destLocation: "/customer/organization"
        },
        methods: {
            'cancelAddContacts': function () {
                window.location = this.destLocation;
            },
            'confirmAddContacts': function () {

                var thisVue = this;

                jQuery.ajax({
                    type: 'post',
                    url: '/customer/action/addContacts',
                    data: {
                        deptId: jQuery('#deptId').val(),
                        contactsTypeId: thisVue.contactsTypeId,
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
                    },
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
            }

        }
    });
});