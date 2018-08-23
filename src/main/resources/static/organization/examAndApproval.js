/**
 * Created by Administrator on 2018/8/21.
 */

$(document).ready(function () {
    var examAndApprovalVue = new Vue({
        el:"#examAndApprovalVue",
        data:{
            applyList:[],
        },
        methods:{
            'init':function () {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    url:'/message/applyList',
                    data:{},
                    dataType:'json',
                    cache:false
                }).done(function (result) {
                    console.log(result);
                    thisVue.applyList=result.applyList;
                })
            }
        }
    });

    Vue.component('enclosure-apply-component',{
        template:'#enclosureApplyComponent',
        props:['apply'],
        data:function () {
            return{

            }
        },
        methods:{

        }
    });

    Vue.component('enclosure-delay-apply-component',{
        template:'#enclosureDelayApplyComponent',
        props:['apply'],
        data:function () {
            return{

            }
        },
        methods:{

        }
    });

    examAndApprovalVue.init();
});