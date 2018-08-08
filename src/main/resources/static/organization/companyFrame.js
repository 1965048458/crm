/**
 * Created by Administrator on 2018/8/7.
 */

jQuery(document).ready(function () {
    var memberSettingVue = new Vue({
       el:"#memberSettingVue",
        data:{
           showPage:'showMember',
            memberList:[],
        },
        methods:{
           init:function () {
               this.showPage='showMember'
           },
           getMemberList:function (companyId) {
               var thisVue = this;
               $.ajax({
                   type:'get',
                   url:'/member/relationship',
                   data:{
                       companyId:companyId
                   },
                   dataType:'json',
                   cache:false
               }).done(function (result) {
                   console.log(result);
                   thisVue.$set(thisVue, 'memberList', result.memberList);
               });
           },
            editMemberShip:function () {
               this.showPage='showMemberRelationEdit';

            },
            memberEdit2Member:function () {
                this.showPage='showMember';
            }

        }
    });

    Vue.component('member',{
        template:'#member',
        props:['member'],
        data:function () {
            return{
                showSub:false,
                foldImg:"/images/customer/fold.svg",
            };
            
        },
        methods:{
            chooseGenderImg:function (gender) {
                if(gender=='FEMALE')
                    return "/images/customer/FEMALE.svg";
                else
                    return "/images/customer/MALE.svg";
            },
            changeSubFold:function () {
                this.showSub = !this.showSub;
                this.chooseFoldImg();
            },
            chooseFoldImg:function () {
                if(this.showSub)this.foldImg="/images/customer/unfold.svg";
                else this.foldImg="/images/customer/fold.svg";
            },
            addSubMemberNum:function (member) {
                var tolNum;
                while(!(member.subMemberList.length==0 || member.subMemberList===undefined)){
                    tolNum += member.subMemberList.length;
                }
                return "下属 "+tolNum+" 人"
            },

        }
    });

    Vue.component('membership',{
        template:'#membership',
        props:['member'],
        data:function () {
            return{
                showSub:false,
                foldImg:"/images/customer/fold.svg",
            };
        },
        methods:{
            chooseGenderImg:function (gender) {
                if(gender=='FEMALE')
                    return "/images/customer/FEMALE.svg";
                else
                    return "/images/customer/MALE.svg";
            },
            changeSubFold:function () {
                this.showSub = !this.showSub;
                this.chooseFoldImg();
            },
            chooseFoldImg:function () {
                if(this.showSub)this.foldImg="/images/customer/unfold.svg";
                else this.foldImg="/images/customer/fold.svg";
            },
            addSubMemberNum:function (member) {
                var tolNum;
                while(!(member.subMemberList.length==0 || member.subMemberList===undefined)){
                    tolNum += member.subMemberList.length;
                }
                return "下属 "+tolNum+" 人"
            },
            addSubMember:function () {

            }


        }
    });



    var companyId = $("#companyId").val();

    memberSettingVue.init();

    memberSettingVue.getMemberList(companyId);
});