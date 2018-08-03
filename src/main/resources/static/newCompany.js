/**
 * Created by Administrator on 2018/7/25.
 */
$(document).ready(function () {
    var createVue = new Vue({
        el: '#createVue',
        data: {
            showPage: 'main',
            psList: ['负责人', '管理层', '人事', '财务', '行政', '销售', '技术', '员工', '其他'],
            post: '',
            members: [],
            name: '',
            tel: '',
            companyName: ''
        },
        methods: {
            'selPost': function () {
                this.showPage = 'selPos';
            },
            'backToMe': function () {
                window.location.href = '/company/chooseCompany';
            },
            'back': function () {
                this.showPage = 'main';
            },
            'create': function () {
                var thisVue = this;
                if (this.companyName === "" || this.members.length < 3){
                    $('.weui-toptips').css('display', 'block');
                    setTimeout(function () {
                        $('.weui-toptips').css('display', 'none');
                    }, 2000);
                    return;
                }
                var postData = {
                    companyId: '',
                    companyName: this.companyName,
                    companyUserList: this.members
                };
                $.ajax({
                    type: 'post',
                    url: '/company/addCompany',
                    data: JSON.stringify(postData),
                    dataType: 'json',
                    contentType: 'application/json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            window.location = '/company/chooseCompany';
                        }, 2000);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                });
            },
            'inviteMember': function () {
                this.showPage = 'invite';
            },
            'backToMain': function () {
                this.showPage = 'main';
            },
            'save': function () {
                var member = {};
                if (!this.checkNull()) return;
                member.crmUserName = this.name;
                member.tel = this.tel;
                this.members.push(member);
                this.name = "";
                this.tel = "";
                this.showPage = 'main';
            },
            'saveAndAdd': function () {
                var member = {};
                if (!this.checkNull()) return;
                member.crmUserName = this.name;
                member.tel = this.tel;
                this.members.push(member);
                this.name = "";
                this.tel = "";
            },
            'checkNull': function () {
                if (this.name == "" || this.tel == "") {
                    $('.invite_tip').css('display', 'block');
                    setTimeout(function () {
                        $('.invite_tip').css('display', 'none');
                    }, 2000);
                    return false;
                }
                return true;
            }
        }
    });
});