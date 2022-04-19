if (!!window.EventSource) {

    var eventSource = new EventSource("/onboarding/stream");

    eventSource.onmessage = function(event) {

        data = JSON.parse(event.data);

        var row = '<tr><td>' + data.requestId+ '</td><td>' + data.Documents + '</td><td>' ;


 row+='<td><button type="button" id="ajaxSubmit" onclick="checkout('+data.requestId+','+data.institutionName+','+data.entityType+','+data.officerName+','+data.size+","+data.stateOfIncorporation+","+data.productType+","+data.creditCheck+","+data.dueDiligence+","+data.documents)"><span class="btn-inner--icon">Details<i class="ni ni-bold-right"></i></span></button></td>';



         row+='</tr><tr width="200px" id="svg" style="width:100%"></tr>';

        $('#tbody').append(row);
    };



} else {
    window.alert("EventSource not available on this browser.")
}

function checkout(requestId, institutionName, entityType, officerName,size,stateOfIncorporation,productType,creditCheck,dueDiligence,documents) {


window.open("/UploadDocumentation.html?requestId="+requestId+"&institutionName="+institutionName+"&entityType="+entityType+"&officerName="+officerName+
"&size="+size+"&stateOfIncorporation="+stateOfIncorporation+"&productType="+productType+"&creditCheck="+creditCheck+"&dueDiligence="+dueDiligence+"&documents="+documents, '_blank');


}