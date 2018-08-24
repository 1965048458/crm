/**
 * Created by Administrator on 2018/8/21.
 */

$(document).ready(function () {
    var examAndApprovalVue = new Vue({
        el:"#examAndApprovalVue",
        data:{
            enclosureApplyList:[],
        },
        methods:{
        }
    });
    
    Vue.component("query-check",{
        template:'#query-check',
        data:function(){
            return{
            }
        },
        methods:{

        }
    });

});