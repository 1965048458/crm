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
            receivers: []

        },
        methods: {
            'selPost': function () {
                this.showPage = 'selPos';
            },
            'back': function () {
                this.showPage = 'main';
            },
            'create': function () {

            },
            'inviteMember':function () {
                this.showPage = 'invite';
            },
            'backToMain':function () {
                this.showPage = 'main';
            },
            'save':function () {
                
            }
        }
    })
});