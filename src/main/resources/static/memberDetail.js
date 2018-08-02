/**
 * Created by Administrator on 2018/7/31.
 */
$(document).ready(function () {
    var memberDetail = new Vue({
        el: "#detailVue",
        data: {
            showPage: 'mInfo',
            companyUser: {},
            deptList: ['研发部门', '销售部门', '财务部门', '后勤部门'],
            department: '',
            position: ''
        },
        methods: {
            'back': function () {
                //todo
                //window.location = "/company/newCompany";
            },
            'setting': function () {
                this.showPage = 'mSetting';
            },
            'delete': function () {
                //todo
            },
            'update': function () {
                //todo
            },
            'backToMain': function () {
                this.showPage = 'mInfo';
            },
            'setDept': function () {
                this.showPage = 'selDept';
            },
            'backToSetting': function () {
                this.showPage = 'mSetting';
            }
        }
    });
});