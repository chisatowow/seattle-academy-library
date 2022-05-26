<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>貸出履歴一覧</h1>
        <div class="content_body">
            <div class="rentHis">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th class="th_color">書籍名</th>
                            <th class="th_color">貸出日</th>
                            <th class="th_color">返却日</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bookInfo" items="${rentBookList}">
                            <tr>
                                <td>
                                    <form method="post" class="book_title" action="<%=request.getContextPath()%>/details">
                                        <a class="tb_color" href="javascript:void(0)" onclick="this.parentNode.submit();"> ${bookInfo.title} </a><input type="hidden" name="bookId" value="${bookInfo.bookId}"> <input type="hidden" name="rentBookId" value="${bookDetailsInfo.rentBookId}">
                                    </form>
                                </td>
                                <td>${bookInfo.rentDate}</td>
                                <td>${bookInfo.returnDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</body>
</html>
