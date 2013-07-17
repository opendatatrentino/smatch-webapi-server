<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="include.jsp"%>

<c:set var="pageTitle" value="Test Form | Sweb Web API ${version}" />
<%@include file="header.jsp"%>
<h2>Test Form</h2>
<a href="${path}">${path}</a>
<table>
    <c:forEach var="method" items="${methods}">
      <tr><th><b>${method.key}</b></th></tr>
      <c:forEach var="parameter" items="${method.value}">
        <tr>
            <td>
                ${parameter.name}
            </td>
            <td>
                ${parameter.type}
            </td>
            <td>
                ${parameter.clazz}
            </td>
            <td>
                ${parameter.required}
            </td>
        </tr>
      </c:forEach>
    </c:forEach>
</table>
<%@include file="footer.jsp"%>
