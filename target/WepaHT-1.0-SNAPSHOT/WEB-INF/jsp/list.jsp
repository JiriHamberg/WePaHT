<%-- 
    Document   : list
    Created on : 23.10.2013, 18:39:39
    Author     : jiri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/public/css/page-bar.css"/>
        <link rel="stylesheet" href="/public/css/menu-bar.css"/>
        <link rel="stylesheet" href="/public/css/book.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTpF-8">
        <title>Books</title>
    </head>
    <body>
        <jsp:include page="partial/menubar.jsp"/>
        <h1>List of books</h1>
    <table class="book-table">
        <tbody>
            <c:forEach var="book" items="${books.getContent()}">
                <tr>
                    <td class="book-cover">
                        <img src="http://covers.openlibrary.org/b/isbn/${book.ISBN}-S.jpg" /> <br/>
                        <a href="/app/view/${book.id}">View</a><br/>
                        <sec:authorize access="hasRole('user')">
                            <a href="/app/books/${book.id}">Edit</a>
                        </sec:authorize>
                    </td>
                    <td class="book-info">        
                            <div class="book-title">  ${book.title} </div>
                            <div>
                            ISBN: ${book.ISBN} <br/>
                            Authors:
                            <ul>
                                <c:forEach var="author" items="${book.authors}">
                                    <li>
                                        ${author}
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>  
    <jsp:include page="partial/pagebar.jsp"/>
    </body>
</html>
