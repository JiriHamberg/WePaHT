<%-- 
    Document   : bookedit
    Created on : 24.10.2013, 17:41:19
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
        <title>Editing ${bookForm.title}</title>
    </head>
    <body>
        <jsp:include page="partial/menubar.jsp"/>
        <table>
            <tbody>
                <tr>
                    <td>
                        <img src="http://covers.openlibrary.org/b/isbn/${bookForm.ISBN}-M.jpg" /> 
                    </td>
                    <td>
                        <jsp:include page="partial/bookform.jsp"/>
                        <form:form action="${pageContext.request.contextPath}/app/books/${bookId}" method="DELETE">
                            <input type="submit" value="Delete"/>
                        </form:form>
                        <a href="/app/view/${bookId}">Cancel</a> <br/>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
