/**
 * Created by Administrator on 2018/8/7.
 */

jQuery(document).ready(function () {
    var memberSettingVue = new Vue({
       el:"#memberSettingVue",
        data:{
           showPage:'showMember',
            companyId:'',
            memberList:[],
            siblingsList:[],
            showMemberRelationship:false,
            showMemberInfo:false,
            showMembership:false,
            showOptionalMember:false,
            lowerMemberId:[],
            upperMemberId:''
        },
        methods:{
           init:function (companyId) {
               this.showPage='showMember';
               this.companyId=companyId;
           },
           getMemberList:function () {
               var thisVue = this;
               $.ajax({
                   type:'get',
                   url:'/member/relationship',
                   data:{
                       companyId:thisVue.companyId
                   },
                   dataType:'json',
                   cache:false
               }).done(function (result) {
                   console.log(result);
                   thisVue.$set(thisVue, 'memberList', result.memberList);
                   thisVue.showMemberRelationship=true;
                   thisVue.showMemberInfo=false;
               });
           },
            getMemberInfo:function () {
              this.showMemberInfo=true;
              this.showMemberRelationship=false;
            },
            editMemberShip:function () {
               this.showPage='showMemberRelationEdit';
               this.showMembership=true;
               this.showOptionalMember=false;
                document.getElementById('editMemberShip').style.opacity='1';
                $("#actionSheet").hide();
                $('#iosMask').hide();

            },
            memberEdit2Member:function () {
                this.showPage='showMember';
                document.getElementById('editMemberShip').style.opacity='1';
                $("#actionSheet").hide();
                $('#iosMask').hide();
            },
            addSubMember:function () {
               var thisVue = this;
               $.ajax({
                   type:'get',
                   url:'/member/searchSiblings',
                   data:{
                       memberId:thisVue.upperMemberId
                   },
                   dataType:'json',
                   cache:false
               }).done(function (result) {
                   console.log(result);
                   thisVue.$set(thisVue, 'siblingsList', result.siblingsList);
                   console.log(thisVue.siblingsList);
                   thisVue.showOptionalMember=true;
                   thisVue.showMembership=false;
                   $("#actionSheet").hide();
                   $('#iosMask').hide();

               });
            },
            deleteLeader:function () {
                var thisVue = this;
                $.ajax({
                    type:'get',
                    url:'/member/deleteLeader',
                    data:{
                        memberId:thisVue.upperMemberId
                    },
                    dataType:'json',
                    cache:false
                }).done(function (result) {
                    console.log(result);
                    thisVue.showOptionalMember=false;
                    thisVue.showMembership=false;
                    thisVue.getMemberList();
                    thisVue.showPage='showMember';
                    $("#actionSheet").hide();
                    $('#iosMask').hide();
                });
            },
            membershipEditCheck:function () {
                var thisVue = this;
                var lowerMemberId = '';
                for(var i=0;i<this.lowerMemberId.length;i++){
                    lowerMemberId+=this.lowerMemberId[i]+",";
                }
                console.log(this.lowerMemberId);
                $.ajax({
                    type:'get',
                    url:'member/addSubMember',
                    data:{
                        upperMemberId:thisVue.upperMemberId,
                        lowerMemberId:lowerMemberId
                    },
                    dataType:'json',
                    cache:false
                }).done(function (result) {
                    console.log(result);
                    document.getElementById('editMemberShip').style.opacity='1';
                    thisVue.showMembership=true;
                    thisVue.showOptionalMember=false;
                    thisVue.showPage='showMember';
                    thisVue.getMemberList();
                });
            },
            chooseGenderImg:function (gender) {
                if(gender=='FEMALE')
                    return "/images/customer/FEMALE.svg";
                else
                    return "/images/customer/MALE.svg";
            },
            showActionSheet:function (upperMemberId) {

               this.upperMemberId = upperMemberId;
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
            addSubMemberNum:function (memberNum) {

                return "下属 "+memberNum+" 人"
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
            addSubMemberNum:function (memberNum) {

                return "下属 "+memberNum+" 人"
            },
            addSubMember:function (upperMemberId) {
                memberSettingVue.addSubMember(upperMemberId);
            },
            showActionSheet:function (upperMemberId) {
                memberSettingVue.showActionSheet(upperMemberId);
            }
        }
    });

    var companyId = $("#companyId").val();

    memberSettingVue.init(companyId);

    memberSettingVue.getMemberList();
});