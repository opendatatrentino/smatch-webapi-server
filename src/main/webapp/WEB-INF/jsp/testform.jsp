<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="include.jsp"%>

<c:set var="pageTitle" value="Test Form | Sweb Web API ${version}" />
<%@include file="header.jsp"%>

<script type="text/javascript">
jQuery.noConflict();  
var params = new Array();
params[0]="select";
var rowID=0;
var matcher =/files/gi;
jQuery(document).ready(function() {
   	var pathValue = jQuery("#path_id").val();
		var bodyValue = jQuery("#body_id").val();
		var str = jQuery("#method_id").val();
		if(str == 'get' || str == 'delete'){
		//alert("asf");
		deleteTextBoxes();
		jQuery("#body_id").attr('disabled', 'true');
		getParams(pathValue);
		changeFormToSupportJson();
		}else if(str == 'post'){
		deleteTextBoxes();
		if(pathValue.match(matcher)!=null){
                changeForm();
             }
             else{
               changeFormToSupportJson();
             }
		}
		
	jQuery("#method_id").change(function(){
		var pathValue = jQuery("#path_id").val();
		var bodyValue = jQuery("#body_id").val();		
		var str = jQuery("#method_id").val();
		if(str == 'get'|| str == 'delete'){
		deleteTextBoxes();
		jQuery("#body_id").attr('disabled', 'true');
		getParams(pathValue);
		changeFormToSupportJson();
		}else if(str == 'post'){
		deleteTextBoxes();
		jQuery("#body_id").attr('disabled', '');
		   if(pathValue.match(matcher)!=null){
                changeForm();
             }
             else{
               changeFormToSupportJson();
             }
		}
		
	});
	jQuery("#param_id").change(function(){
		addingTextBox(jQuery("#param_id").val(), rowID);		
	});
	jQuery("#path_id").change(function(){
		var pathValue = jQuery("#path_id").val();
		var bodyValue = jQuery("#body_id").val();		
		var str = jQuery("#method_id").val();
		if(str == 'get'|| str == 'delete'){
		deleteTextBoxes();
		jQuery("#body_id").attr('disabled', 'true');
		getParams(pathValue);
		changeFormToSupportJson();
		}else if(str == 'post'){
		deleteTextBoxes();
		jQuery("#body_id").attr('disabled', '');
		   if(pathValue.match(matcher)!=null){
                changeForm();
             }else{
               changeFormToSupportJson();
               }
		}
		
	});
	
	
	
});

// currently we are not using this mehotd
function send(){
var path = jQuery("#path_id").val();
var body = jQuery("#body_id").val();
var method =jQuery("#method_id").val();
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    var cust = xmlhttp.responseText;
    //alert("asfsadf");
    var jsonString = jQuery.parseJSON(cust);
    var myJson = jsonString.response.resonse;
    var myJsonObj = jsonParse(myJson);
    var myFormattedString = FormatJSON(myJsonObj);
    jQuery("#response_id").attr("value",myFormattedString);
    }
  }
xmlhttp.open("GET","${pageContext.request.contextPath}/webapi/testform/submit?path="+path+" &body="+body+" &method="+method,true);
xmlhttp.send();
}
function paramChange(){
addingTextBox(jQuery("#param_id").val(), rowID);
}
function deleteNthRow(row,tableID,val){               
               try { 
               var table = document.getElementById(tableID);
	           var rowCount = table.rows.length;
	          var rowIndex = jQuery("#"+row).parent().parent().parent().children().index(jQuery("#"+row).parent().parent());
    		   table.deleteRow(rowIndex);
	           }catch(e) {
	              alert(e);
	            }
	           jQuery("<option/>").val(val).text(val).appendTo("#param_id");
}
	            
function deleteTextBoxes(){
	deleteRow("formTable");
}
function deleteRow(tableID) {
	            try {
	            var table = document.getElementById(tableID);
	            var rowCount = table.rows.length;
	            for(var i=0; i<rowCount; i++) {
	                var row = table.rows[i];
	                if(i>3) {
	                    table.deleteRow(i);
	                    rowCount--;
	                    i--;
	                }
	                           }
	            }catch(e) {
	                alert(e);
	            }
	            rowID = 3;
      }
      
      function addTextArea(tableID,bodyValue){
      jQuery("#formTable").last()
    .append(jQuery('<tr>')
        .append(jQuery('<td style="width: 100px;">')
          .append(jQuery('<label>'+'RequestBody:'+'</label>')))
         .append(jQuery('<td>')
          .append(jQuery('<textarea/>').attr({'id':'body_id','name':'body','rows':'10','style':'width: 600px;'}))));
      // remove the item from list
       }
      
      function addParametertab(){
            jQuery("#formTable").last()
                      .append(jQuery('<tr>')
                      .append(jQuery('<td style="width: 100px;">')
                      .append(jQuery('<label>'+'Parameters:'+'</label>')))
                       .append(jQuery('<td>')
                     .append(jQuery('<select/>').attr({'id':'param_id','style':'width: 100px;','onChange':'paramChange();','value':"params"}))))
                     .append(jQuery('<tr>')).append(jQuery('<tr>'));
                     rowID++;rowID++;
                     
       }
 
      
	            
//get parameters

function getParams(path) {
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    var cust = xmlhttp.responseText;
    //alert(cust);
    var jsonString = jQuery.parseJSON(cust);
       //alert(jsonString);
       var names = jsonString.response.params;
       //alert(names);
       if(names!=null && names.length>0){
       addParametertab();
       jQuery("<option/>").val("select").text("select").appendTo("#param_id");
       }
       if(names!=null){
       for(var i=0;i<names.length;i++){
       jQuery("<option/>").val(names[i]).text(names[i]).appendTo("#param_id");
       }
       }
       
    }
  }
xmlhttp.open("GET","${pageContext.request.contextPath}/webapi/testform/params?path="+path,true);
xmlhttp.send();
}

function addingTextBox(textName,id){
    jQuery("#formTable").last()
    .append(jQuery('<tr>')
        .append(jQuery('<td>')
          .append(jQuery('<label>'+textName+'</label>')))
         .append(jQuery('<td>')
           .append(jQuery('<input />')
              .attr({'type':'text','name':textName, 'id':textName,'style':'width: 500px;'}))      
            .append(jQuery('<input />')
              .attr({'type':'button','name':textName, 'id':rowID,'value':'-','onClick':"deleteNthRow(id,'formTable',name);"}))));
            rowID++;
            jQuery("#param_id option:selected").remove();
}

// Adding support for files so we need to change the type of form for that -->

function addingfileBox(textName){
    jQuery("#formTable").last()
    .append(jQuery('<tr>')
        .append(jQuery('<td>')
          .append(jQuery('<label>'+textName+'</label>')))
         .append(jQuery('<td>')
           .append(jQuery('<input />')
              .attr({'type':'file','name':textName, 'id':textName}))      
              ));
            rowID++;
}

function changeFormToSupportJson(){
//alert("change form to json");
var form = document.getElementById("testForm");
form.removeAttribute("enctype");
}
function changeForm(){
//alert("change form to multi ");
var form = document.getElementById("testForm");
form.setAttribute("enctype", "multipart/form-data");
addingfileBox("file");
}

</script>



<!-- This method is used to format the json after page load (currently commented because we already format the json in spring)-->
<!--<script type="text/javascript"> 
		window.onload = function() {
	   alert("asdf");
	   var resp = jQuery("#response_id").val();
       if(resp!=null && resp.length > 10 ){
       var myJsonObj = jsonParse(resp);
       var myFormattedString = FormatJSON(myJsonObj);
       jQuery("#response_id").attr("value",myFormattedString);
   }};
		</script>
-->

<h2>Test Form</h2>
<form id ="testForm" action="<c:url value="/webapi/testform"/>" method="post">
	<table id="formTable" style="width: 700px; border: 1px grey dashed;">
		<tr>
			<th colspan="2" style="border-bottom: 1px grey dashed;">Request</th>
		</tr>
		<tr><!-- <c:if test="${method != 'null'}">disabled=true</c:if> -->
			<td style="width: 100px;">Method:</td>
			<td>
				<select id="method_id" name="method">
					<option value="post" <c:if test="${method == 'post'}">selected="selected"</c:if>>POST</option>
					<option value="get" <c:if test="${method == 'get'}">selected="selected"</c:if>>GET</option>
					<option value="delete" <c:if test="${method == 'delete'}">selected="selected"</c:if>>DELETE</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="width: 100px;">URL:</td>
			<td>
				<input id="path_id" style="width: 600px;" type="text" name="path" value="${path}"/>
			</td>
		</tr>
		<tr>
			<td style="width: 100px;">Request Body:</td>
			<td>
				<textarea id="body_id" name="body" rows="10" cols="" style="width: 600px;"><c:out value="${body}"/></textarea>
			</td>
		</tr>
	</table>
	<table style="width: 700px; border: 0px grey dashed;">
	<tr>
			<th colspan="2" style="border-bottom: 0px grey dashed;"></th>
		</tr>
		<tr>
			<!--  <td colspan="2" align="right">
				<input id="send_button_id" type="button" name="submit" value="Send" onclick = "send()"/>
			</td> -->
			<td colspan="2" align="right">
				<input id="send_button_id" type="Submit" name="Submit" value="Send"/>
			</td> 
		</tr>
	</table>
</form>

<br />
<table style="width: 700px; border: 1px grey dashed;">
	<tr>
		<th style="border-bottom: 1px grey dashed;">Response</th>
	</tr>
	<c:if test="${!empty errorMessage}">
		<tr>
			<td>
				<span style="color: #ff0000;">${errorMessage}</span>
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty status}">
		<tr>
			<td>
				<c:if test="${status == 200}">
					<span style="color: #22cc22;">HTTP Status ${status}</span>
				</c:if>
				<c:if test="${status != 200}">
					<span style="color: #cc2222;">HTTP Status ${status}</span>
				</c:if>
			</td>
		</tr>
	</c:if>
	<tr>
		<td>
			<textarea id="response_id" rows="20" cols="" style="width: 700px;">${resp}</textarea>
		</td>
			</tr>
</table>

<%--
<script type="text/javascript">
	function sendQuery() {
		var method = $('#method_id').val();
		var path = $('#path_id').val();
		var body = $('#body_id').val();
		alert(body);
		$.post(path, { body },
			function(data) {
				$("#response_id").empty();
				//$("#response_id").html(data);
			});
	}
	
	$('#send_button_id').click(function () {
		sendQuery();
	});
</script>
 --%>
<%@include file="footer.jsp"%>
