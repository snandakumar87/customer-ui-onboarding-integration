if (!!window.EventSource) {

    var eventSource = new EventSource("/onboarding/stream");

    eventSource.onmessage = function(event) {

        data = JSON.parse(event.data);

        var row = '<tr><td>' + data.requestId+ '</td><td>' + data.Documents + '</td><td>' ;


 row+='<td><button class="btn btn-icon btn-primary" type="button" id="ajaxSubmit" onclick="checkout('+data+')"><span class="btn-inner--icon">Details<i class="ni ni-bold-right"></i></span></button>';



         row+='</tr><tr width="200px" id="svg" style="width:100%"></tr>';

        $('#tbody').append(row);
    };



} else {
    window.alert("EventSource not available on this browser.")
}

function checkout(data) {

window.open("/UploadDocumentation.html?requestId="+data.requestId+"&institutionName="+data.institutionName+"&entityType="+data.entityType+"&officerName="+data.officerName+
"&size="+data.size+"&stateOfIncorporation="+data.stateOfIncorporation+"&productType="+data.productType+"&creditCheck="+data.creditCheck+"&dueDiligence="+data.dueDiligence+"&documents="+data.Documents, '_blank');


}