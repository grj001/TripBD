<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/6
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/static/echarts.js"></script>
    <script type="text/javascript" src="/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <style type="text/css" href="/static/bootstrap-3.3.7-dist/css/bootstrap.css"></style>
</head>
<body>
<table>
    <tr>
        <td>ID</td>
        <td>日期</td>
        <td>酒店id</td>
        <td>酒店名称</td>
        <td>房型id</td>
        <td>房型名称</td>
        <td>房间数</td>
        <td>当月预订数</td>
        <td>当月入住数</td>
    </tr>
    <%--<%--%>
    <%--List<HotelRoomTypeRate> list =--%>
    <%--request.getAttribute()--%>
    <%--%>>--%>
    <c:forEach var="hotelInfo" items="${hotelrtInfos}">
        <tr>
            <td>${hotelrtInfos.rrId}</td>
            <td>${hotelrtInfos.datedateMonth}</td>
            <td>${hotelrtInfos.hotelId}</td>
            <td>${hotelrtInfos.hotelName}</td>
            <td>${hotelrtInfos.roomTypeId}</td>
            <td>${hotelrtInfos.roomtypeName}</td>

            <td>${hotelrtInfos.roomNum}</td>
            <td>${hotelrtInfos.bookNum}</td>
            <td>${hotelrtInfos.checkinNum}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
