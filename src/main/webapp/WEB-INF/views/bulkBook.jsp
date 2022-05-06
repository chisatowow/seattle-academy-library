<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>一括登録｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/thumbnail.js"></script>
</head>
<body class = "wrapper">
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
        <form action="<%=request.getContextPath()%>/registed" method="post" enctype="multipart/form-data" id="data_upload_form">
            <h1>一括登録</h1>
            <div class="bulk_form">
                <h2>CSVファイルをアップロードすることで書籍を一括で登録できます。</h2>
                <div class="caution">
                    <p>「書籍名,著者名,出版社,出版日,ISBN」の形式で記載してください。</p>
                    <p>※サムネイル画像は一括登録できません。編集画面で１冊編集してください。</p> 
                </div>                                                 
            </div>            
            <div class="bulk_form"> 
                <input type="file" accept=".csv" name="File" id="File">        
                <div class="btn_bulkRegist">
                    <button type="submit" class="btn_bulkRegist">一括登録</button>
                </div> 
                <div class="error_msg">
                    <c:if test="${!empty booksEmpty}" >                        
                        <p class="error">${booksEmpty}</p>                       
                    </c:if>
                    <c:if test="${!empty booksError}">
                    <div class="error">
                    <c:forEach var="bookError" items="${booksError}">
                        <p>${bookError}</p>
                    </c:forEach>
                    </div>                        
                    </c:if>
                </div>
            </div>
        </form>
    </main>
</body>
</html>