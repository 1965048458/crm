$(document).ready(function () {
    var projectVue = new Vue({
        el: '#projectVue',
        data: {
            showPage: 'projectList',
            imgFilter: '/images/opportunity/filterUnchecked.svg',
            projectList: [],
            searchBar: false,
            keyWord: '',
            stages: ['未开始', '未交付', '交付及回款', '已结束'],
            filterPage: false,
            filterCondition: '',
            dateValueStart: '',
            newDate: 'all',
            dateValueEnd: '',
            subMemberList: [],
            tempSub: [],
            subUserId: [],
            subsName: [],
            // subUser: '',
            creatorValue: 'all',
            customerValue: '',
            // customerValueIn: '',
            // creatorV: '',
            stageValue: 'all'
        },
        methods: {
            'showResult': function (data) {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/searchProject',
                    data: data,
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'projectList', result.projectList);
                    } else {
                        thisVue.errMsg = result.errMsg;
                        thisVue.showErrMsg = true;
                    }
                })
            },
            'back': function () {
                //
            },
            'add': function () {
                window.location = '/project/new';
            },
            'search': function () {
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
                this.filterPage = false;
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                this.filterCondition = '';
                this.searchBar = !this.searchBar;
            },
            'filterProject': function (project) {
                return project.projectName.indexOf(this.keyWord) != -1;
            },
            'text': function () {
                $('#searchBar').addClass('weui-search-bar_focusing');
                //$('#searchText').focus();
            },
            'cancelSearch': function () {
                this.keyWord = "";
                $('#searchBar').removeClass('weui-search-bar_focusing');
            },
            'clear': function () {
                this.keyWord = "";
                $('#searchInput').focus();
            },
            'cancel': function () {
                this.cancelSearch();
                $('#searchInput').blur();
                this.searchBar = false;
            },
            'filter': function () {
                this.searchBar = false;
                this.imgFilter = "/images/opportunity/filterChecked.svg";
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                this.filterCondition = 'creator';
                $('#creator').css('background-color', '#FFFFFF');
                this.filterPage = true;
            },
            'all': function () {
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
                this.filterPage = false;
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                this.filterCondition = '';
            },
            'before': function () {
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
                this.filterPage = false;
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                this.filterCondition = '';
            },
            'after': function () {
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
                this.filterPage = false;
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                this.filterCondition = '';
            },
            'cancelMask': function () {
                this.filterPage = false;
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                this.filterCondition = '';
            },
            'selCreator': function () {
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                $('#creator').css('background-color', '#FFFFFF');
                this.filterCondition = 'creator';
            },
            'selDate': function () {
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                $('#date').css('background-color', '#FFFFFF');
                this.filterCondition = 'date';
            },
            'selCustomer': function () {
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                $('#customer').css('background-color', '#FFFFFF');
                this.filterCondition = 'customer';
            },
            'selStatus': function () {
                $('#' + this.filterCondition).css('background-color', '#F5F5F5');
                $('#status').css('background-color', '#FFFFFF');
                this.filterCondition = 'status';
            },
            'dateChecked': function () {
                this.dateValueStart = '';
                this.dateValueEnd = '';
            },
            'removeAll':function () {
                this.newDate = '';
            },
            'creatorChecked': function () {
                // this.creatorV = '';
                // this.subUser = '';
                this.subUserId = [];
                this.subsName = [];
                this.tempSub = [];
            },
            'customerChecked': function () {
                this.customerValue = '';
            },
            'backToFilter': function () {
                this.tempSub = [];
                this.showPage = 'projectList';
            },
            'chooseCreator': function () {
                this.creatorValue = '';
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/opportunity/subMemberList',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'subMemberList', result.subMemberList);
                        thisVue.showPage = 'selectCreator';
                    }
                });

            },
            'finish': function () {
                this.filterPage = false;
                this.imgFilter = '/images/opportunity/filterChecked.svg';
                this.filterCondition = '';
                var data = {
                    creator: this.creatorValue,
                    subMember: this.subUserId,
                    customerName: this.customerValue,
                    startTime: this.dateValueStart,
                    endTime: this.dateValueEnd,
                    status: this.stageValue
                };
                console.log(data);
                // this.showResult(data);
            },
            'reset': function () {
                this.creatorValue = 'all';
                this.stageValue = 'all';
                this.newDate = 'all';
                this.customerValue = '';
                this.dateValueStart = '';
                this.dateValueEnd = '';
                // this.creatorV = '';
                // this.customerValueIn = '';
                // this.subUser = '';
                this.subUserId = [];
                this.subsName = [];
                this.tempSub = [];
            },
            'submit': function () {
                this.subUserId = [];
                this.subsName = [];

                for (var i = 0; i < this.tempSub.length; i++) {
                    var str = this.tempSub[i].split(',');
                    this.subUserId[i] = str[0];
                    this.subsName[i] = str[1];
                }
                /*var tempId = '';
                for (var i = 0; i < this.subUserId.length; i++) {
                    tempId += this.subUserId[i] + ',';
                }
                this.subUser = tempId;*/
                // this.creatorV = this.subsName;
                this.showPage = 'projectList';
            }
        }
    });
});
