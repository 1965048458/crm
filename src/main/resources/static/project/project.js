$(document).ready(function () {
    var projectVue = new Vue({
        el: '#projectVue',
        data: {
            showPage: 'projectList',
            imgFilter: '/images/opportunity/filterUnchecked.svg',
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
                this.imgFilter = "/images/opportunity/filterChecked.svg";
            },
            'all':function () {
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
            },
            'before':function () {
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
            },
            'after':function () {
                this.imgFilter = '/images/opportunity/filterUnchecked.svg';
            }
        }
    });
});
