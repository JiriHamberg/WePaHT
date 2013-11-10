<%-- 
    Document   : pagebar
    Created on : 24.10.2013, 1:21:52
    Author     : jiri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-bar">
    <div>
        <c:if test="${page > 1}">
            <a href="?searchType=${searchType}&searchArg=${searchArg}&page=${page - 1}"> prev </a>
        </c:if>
        Page ${page} / ${pageCount}
        <c:if test="${page < pageCount}">
            <a href="?searchType=${searchType}&searchArg=${searchArg}&page=${page + 1}"> next </a>
        </c:if>
    </div>
</div>    
