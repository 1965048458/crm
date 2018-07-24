jQuery(document).ready(function () {
    var newProject = new Vue({
        el: '#newProject',
        data: {
            showPage: 'addCustomer',
            name: '',
            content:'',
            agent:'',
            person:'',
            background: '',
        },
        methods: {
            'submit':function () {
                if(this.name ==''|| this.content =='' || this.agent ==''){
                    alert("内容填写不完整！");
                }else{
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/project/add',
                        data: {
                            name:thisVue.name,
                            content:thisVue.content,
                            agent:thisVue.agent,
                            person:thisVue.person,
                            background:thisVue.background,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result){
                        if (result.successFlg) {
                            alert("创建成功");
                        }else {
                            alert("请填写正确的信息");
                        }
                    });
                };
            },
        },
    });
});
