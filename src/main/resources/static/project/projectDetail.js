jQuery(document).ready(function () {

    var projDetailVue = new Vue({
        el: '#projDetailVue',
        data: {
            show: 'projectDetail',
            showPage: 'detailPage',
            keyWord: '',
            chosenExe: '',
            searchBar: false,
            userList: [],
            projectId: ''
        },
        methods: {
            'back': function () {
                window.location.href = '/project/projectList';
            },
            'clearStyle': function () {
                $('#detailBox').attr("class", "weui-grid navi-bar my-weui-grid");
                $('#detail').attr("class", "weui-grid__label");
                $('#relevantBox').attr("class", "weui-grid navi-bar my-weui-grid");
                $('#relevant').attr("class", "weui-grid__label");
                $('#modifBox').attr("class", "weui-grid navi-bar my-weui-grid");
                $('#modif').attr("class", "weui-grid__label");
            },
            'toDetailPage': function () {
                this.showPage = 'detailPage';
                this.clearStyle();
                $('#detailBox').attr("class", "weui-grid navi-bar my-weui-grid weui-grid-select");
                $('#detail').attr("class", "weui-grid__label weui-grid-select-content");
            },
            'toRelevantPage': function () {
                this.showPage = 'relevantPage';
                this.clearStyle();
                $('#relevantBox').attr("class", "weui-grid navi-bar my-weui-grid weui-grid-select");
                $('#relevant').attr("class", "weui-grid__label weui-grid-select-content");
            },
            toModifyPage: function () {
                this.showPage = 'modifyPage';
                this.clearStyle();
                $('#modifBox').attr("class", "weui-grid navi-bar my-weui-grid weui-grid-select");
                $('#modif').attr("class", "weui-grid__label weui-grid-select-content");
            },
            'clickApplySupport': function () {
                window.location = "/opportunity/applySupport?salesOpportunityId=" + $("#salesOpportunityId").val();
            },
            'assignLeader': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/queryCompanyUser',
                    data: {
                        keyword: thisVue.keyWord
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'userList', result.companyUsers);
                        thisVue.show = 'chooseExe';
                    } else {
                        alert("系统出错");
                    }
                });
            },
            'clear': function () {
                this.keyWord = '';
                $('#searchInput').focus();
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.keyWord = '';
                $('#searchInput').blur();
                this.searchBar = false;
            },
            'search': function () {
                this.searchBar = !this.searchBar;
                if (!this.searchBar) {
                    this.keyWord = '';
                }
            },
            'confirm': function () {
                var thisVue = this;
                thisVue.projectId = $("#salesOpportunityId").val();
                $.ajax({
                    type: 'get',
                    url: '/project/assignLeader',
                    data: {
                        projectId: thisVue.projectId,
                        leader: thisVue.chosenExe
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            thisVue.show = 'projectDetail';
                        }, 1000);
                    }
                });
            }
        }
    });

});