<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="include.jsp"%>

<c:set var="pageTitle" value="Ping - Pong | Sweb Web API ${version}" />
<%@include file="header.jsp"%>

<h3>Sweb web API version <c:out value="${version}"/></h3>
<h3>Available methods:</h3>
click on the link to go on the test page
<br />
<table>
	<c:forEach var="path" items="${servlets}">
		<tr>
			<td>
				<a href="<c:url value="/webapi/testform/path?path=${path.key}&methodType=${path.value}"/>">${path.key}</a>
			</td>
		</tr>	
	</c:forEach>
</table>
<%@include file="footer.jsp"%>
