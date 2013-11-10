<%-- 
    Document   : menubar
    Created on : 24.10.2013, 20:39:57
    Author     : jiri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="menu-bar">
    <div><a href="/app/books">Books</a></div>
    <sec:authorize access="hasRole('user')">
        <div><a href="/app/add">Add</a></div>
    </sec:authorize>

    <%-- case: logged in --%>
    <sec:authorize access="!hasRole('user')">
        <div><a href="/spring_security_login">Login</a></div>
    </sec:authorize>
    <%-- case: not logged in --%>
    <sec:authorize access="hasRole('user')">
        <div><a href="/j_spring_security_logout">Logout</a></div>
    </sec:authorize>    
    
    <div>
        <form:form cssClass="inline-form" action="${pageContext.request.contextPath}/app/books" method="GET">
            Search by: 
            <select id="searchType" name="searchType">
                <%-- Init a hashmap containing select element bodies and names  --%>
                <jsp:useBean id="map" class="java.util.LinkedHashMap" scope="session">
                    <c:set target="${map}" property="Title" value="title"/>
                    <c:set target="${map}" property="Author" value="author"/>
                    <c:set target="${map}" property="ISBN" value="isbn"/>
                    <c:set target="${map}" property="Publisher" value="publisher"/>
                    <c:set target="${map}" property="Publishing Year" value="publishing_year"/>
                    <c:set target="${map}" property="Description" value="description"/>                                    
                </jsp:useBean>                
                <%-- Create select input and set current option to be selected  --%>
                <c:forEach var="entry" items="${map}">
                    <option 
                        value="${entry.value}"
                        <c:if test="${entry.value == searchType}">selected="selected"</c:if>                                
                    > 
                        ${entry.key}</option> 
                </c:forEach>

            </select>
            <input id="searchInput" type="text" name="searchArg" value="${searchArg}"/>
            <input type="submit" value="Search"/>
        </form:form>            
    </div> 
    <div class="message">
        <c:if test="${not empty message}">
            ${message}
        </c:if>
    </div>
</div>    
