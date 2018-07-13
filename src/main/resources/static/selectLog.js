/**
 * Created by Administrator on 2018/7/11.
 */
function confirm() {
    var type;
    var client;
    var project;
    var startTime;
    var endTime;
    var isRead;
    jQuery('input[name="journal_type"]').each(function () {
        var isChecked = jQuery(this)[0].checked;
        if (isChecked) {
            type = jQuery(this).val();
        }
    });
    client = jQuery("#selectClient").val();
    project = $("#selectProject").val();
    startTime = $("#startTime").val();
    endTime = $("#endTime").val();
    isRead = $("input[name=isReadSwitch]:checked").val();
    if (typeof isRead == 'undefined') isRead = "off";
    window.location = "/journal/search?keyword=" + type
        //+"&client="+client
        //+"&project="+project
        //+"&startTime="+startTime
        //+"&endTime="+endTime
        + "&isRead=" + isRead;


}

function quit() {
    window.location.replace('/journal');

}
