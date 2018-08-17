$(document).ready(function () {
    var projectVue = new Vue({
        el: '#projectVue',
        data: {
            showPage: 'projectList',
            imgFilter: '/images/opportunity/筛选未选中.svg',
            projectList: []
        },
        methods: {
            'back': function () {
                //
            },
            'add': function () {
                window.location = '/project/new';
            },
            'search':function () {
                //
            },
            'filter':function () {
                this.imgFilter = "/images/opportunity/筛选已选中.svg";
            },
            'all':function () {
                this.imgFilter = '/images/opportunity/筛选未选中.svg';
            },
            'before':function () {
                this.imgFilter = '/images/opportunity/筛选未选中.svg';
            },
            'after':function () {
                this.imgFilter = '/images/opportunity/筛选未选中.svg';
            }
        }
    });
});
