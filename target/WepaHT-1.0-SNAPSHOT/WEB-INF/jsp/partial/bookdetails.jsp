<%-- 
    Document   : bookdetails
    Created on : 24.10.2013, 16:50:30
    Author     : jiri
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="book-title"> ${book.title} </div>
ISBN: ${book.ISBN} <br/>
Authors:
<ul>
    <c:forEach var="author" items="${book.authors}">
        <li>
            ${author}
        </li>
    </c:forEach>
</ul>
Publishers:
<ul>
    <c:forEach var="publisher" items="${book.publishers}">
        <li>
            ${publisher}
        </li>
    </c:forEach>
</ul>
Publishing year: ${book.publishingYear} <br/>
Description: <p class="book-description"> ${book.description} </p>