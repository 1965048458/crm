
jQuery(document).ready(function () {

    var contactsInfoVue = new Vue({
        el: '#contactsInfoVue',
        data: {
            showPage: "infoPage"
        },
        methods: {
            'switch2InfoPage': function () {
                jQuery('#infoPageBtn').attr("class", "weui-navbar__item weui-bar__item_on");
                jQuery('#followUpPageBtn').attr("class", "weui-navbar__item");
                this.showPage = "infoPage";
            },
            'switch2FollowUpPage': function () {
                this.showPage = "followUpPage";
                jQuery('#infoPageBtn').attr("class", "weui-navbar__item");
                jQuery('#followUpPageBtn').attr("class", "weui-navbar__item weui-bar__item_on");
            },
            'back': function(){

            }
        }
    });

});