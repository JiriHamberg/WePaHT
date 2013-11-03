<%-- 
    Document   : bookform
    Created on : 23.10.2013, 18:39:06
    Author     : jiri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/public/css/menu-bar.css"/>
        <link rel="stylesheet" href="/public/css/book.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="partial/menubar.jsp"/>
        <h1>Add a new book</h1>
        
        Search data by ISBN: </br>
        <form:form action="${pageContext.request.contextPath}/app/remote/get/isbn" method="GET">
            ISBN:<input type="text" name="isbn"/>
            <input type="submit" value="Search">
        </form:form>
        <br/>
        
        Search data by title: </br>
        <form:form action="${pageContext.request.contextPath}/app/remote/get/title" method="GET">
            Title:<input type="text" name="title"/>
            <input type="submit" value="Search">
        </form:form>
        <br/>
        
        
       <form:form commandName="bookForm" action="${pageContext.request.contextPath}/app/books" method="POST">
           Title:<form:input path="title" /><form:errors path="title" /><br/>
           ISBN:<form:input path="ISBN" /><form:errors path="ISBN" /><br/>
           Authors:<form:input path="authors" /><form:errors path="authors" /><br/>
           Publishers:<form:input path="publishers" /><form:errors path="publishers" /><br/>
           Publishing year:<form:input path="publishingYear" /><form:errors path="publishingYear" /><br/>
           Description:<form:input path="description" /><form:errors path="description" /><br/>
           <input type="submit">
       </form:form>
       
    </body>
</html>
