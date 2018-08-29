/**
 * Created by Administrator on 2018/7/17.
 */
function handleTime(time) {
    if (time < 10)
        return "0" + time;
    else
        return time;
}

$(document).ready(function () {

    var modifyProjectVue = new Vue({
        el: '#modifyProjectVue',
        data: {
            errMsg: undefined,

            show: 'modif',

            project: '',
            content: '',
            agent:'',
            deliverDate: '请选择',
            amount: '',
            contact: '',
            projectName: '',

            stages: ['未开始', '未交付', '交付及回款', '已结束'],
            selStage: '',
            status: '未启动(创建后提交启动申请)',
            lastStatus: 0,


        },
        methods: {
            'showDetailResult': function () {
                var thisVue = this;
                $.ajax({
                    type: 'get',
                    url: '/project/modify/projectDetail',
                    data: {
                        projectId: jQuery("#projectId").val()
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        thisVue.$set(thisVue, 'project', result.project);
                        thisVue.$set(thisVue, 'content', result.project.content);
                        thisVue.$set(thisVue, 'agent', result.project.agent);
                        thisVue.$set(thisVue, 'deliverDate', result.project.clinchDate.substring(0,11));
                        thisVue.$set(thisVue, 'amount', result.project.amount);
                        thisVue.$set(thisVue, 'lastStatus', result.project.status);
                        thisVue.$set(thisVue, 'status', thisVue.stages[result.project.status]);
                        thisVue.$set(thisVue, 'projectName', result.project.projectName);
                        if (result.project.projectContacts != null) {
                            thisVue.$set(thisVue, 'contact', result.project.projectContacts);
                        } else {
                            thisVue.$set(thisVue, 'contact', '');
                        }
                    }
                })
            },
            'detailSubmit': function () {
                var thisVue = this;
                var postData = {
                    opportunityId: jQuery("#projectId").val(),
                    opportunityName: this.projectName,
                    salesStatus: this.lastStatus,
                    amount: this.amount,
                    clinchDate: this.deliverDate,
                    content: this.content,
                    agent:this.agent
                };
                $.ajax({
                    type: 'post',
                    url: '/project/modification',
                    data: JSON.stringify(postData),
                    dataType: 'json',
                    contentType: 'application/json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        $('#toast').fadeIn(100);
                        setTimeout(function () {
                            $('#toast').fadeOut(100);
                            history.back();
                        }, 1000);
                    }
                });

            },
            'deliverDatePicker': function () {
                var thisVue = this;
                const nowDate = new Date();
                weui.datePicker({
                    start: 1990,
                    end: 2030,
                    defaultValue: [nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate()],
                    onChange: function (result) {
                    },
                    onConfirm: function (result) {
                        thisVue.deliverDate = result[0] + '-' + handleTime(result[1]) + '-' + handleTime(result[2]);
                    }
                });
            },
            modifBack: function () {
              history.back();
            },
            'selStatus': function () {
                this.show = 'status';
            },
            'backToInfo': function () {
                this.show = 'modif';
            },
            'done1': function () {
                if (this.selStage === "") {
                    alert("项目状态不能为空！");
                    return;
                }
                this.status = this.stages[this.selStage];
                this.lastStatus = this.selStage;
                this.show = 'modif';
            },
        },
    });
    modifyProjectVue.showDetailResult();
});
