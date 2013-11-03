<%-- 
    Document   : bookform
    Created on : 24.10.2013, 16:37:06
    Author     : jiri
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form commandName="bookForm" action="${pageContext.request.contextPath}/app/books/${bookId}" method="PUT">
    Title:<form:input path="title"  /><form:errors path="title" /><br/>
    ISBN:<form:input path="ISBN" /><form:errors path="ISBN" /><br/>
    Authors:<form:input path="authors" /><form:errors path="authors" /><br/>
    Publishers:<form:input path="publishers" /><form:errors path="publishers" /><br/>
    Publishing year:<form:input path="publishingYear" /><form:errors path="publishingYear" /><br/>
    Description:<form:input path="description" /><form:errors path="description" /><br/>
    <input type="submit" value="${submitText}"><br/>
</form:form>
