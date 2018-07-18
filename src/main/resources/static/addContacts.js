
jQuery(document).ready(function () {

    var addContactsVue = new Vue({
        el: "#addContactsVue",
        data: {
            showErrMsg: false,
            errMsg: '',
            showPage: '',
            realName: '',
            gender: '',
            tel: '',
            phone: '',
            wechat: '',
            qq: '',
            email: '',
            officeAddr: '',
            profile: '',
            specialRelationship: ''
        },
        methods: {
            'cancelAddContacts': function () {

            },
            'confirmAddContacts': function () {

            }
        }
    });
});