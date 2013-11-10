<%-- 
    Document   : bookview
    Created on : 24.10.2013, 17:33:48
    Author     : jiri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/public/css/menu-bar.css"/>
        <link rel="stylesheet" href="/public/css/book.css"/> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${book.title}</title>
    </head>
    <body>
        <jsp:include page="partial/menubar.jsp"/>
        <table>
            <tbody>
                <tr>
                    <td>
                        <img src="http://covers.openlibrary.org/b/isbn/${book.ISBN}-M.jpg" />
                    </td>
                    <td>
                        <jsp:include page="partial/bookdetails.jsp"/>
                        <sec:authorize access="hasRole('user')">
                            <a href="/app/books/${book.id}">Edit</a>
                        </sec:authorize>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
