<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- directive của JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- <link rel="stylesheet" href="style.css"> -->
<link rel="stylesheet" href="${classpath }/css-js/css/style.css">
<link rel="stylesheet" href="${classpath }/css-js/css/chat-app-copy-and-change-to-comment.css"></link>
<title>Flowchat</title>
<script src="https://kit.fontawesome.com/ef7e2b893b.js"
	crossorigin="anonymous"></script>
</head>

<body>
	<nav class="navbar">
		<div class="nav-left">
			<a href="${classpath }/user/home">
				<img class="logo" src="${classpath}/StorageFolder/images/logo.png" alt="">
			</a>
			<ul class="navlogo">
				<li><a role="button" href="http://localhost:5555/contact"><img
						src="${classpath}/StorageFolder/images/contact_icon-360x360.png"></a></li>
				<li><a role="button" href="${classpath }/chat-app"><img
						src="${classpath}/StorageFolder/images/message-icon-200x200.png"></a></li>
			</ul>
		</div>
		<div class="nav-right">
			<div class="search-box">
				<img src="${classpath}/StorageFolder/images/search.png" alt="">
				<input type="text" placeholder="Search">
			</div>
			<div class="profile-image online" onclick="UserSettingToggle()">
				<img src="${classpath}/StorageFolder/images/profile-pic.png" alt="">
			</div>

		</div>
		<div class="user-settings">
			<div class="profile-darkButton">
				<div class="user-profile">
					<img src="${classpath}/StorageFolder/${userLogined.avatar }" alt="">
					<div>
						<p>${userLogined.nickname }</p>
						<a href="${classpath}/user/profile">See your profile</a>
					</div>
				</div>
				<div id="dark-button" onclick="darkModeON()">
					<span></span>
				</div>
			</div>
			<hr>
			<div class="user-profile">
				<img src="${classpath}/StorageFolder/images/feedback.png" alt="">
				<div>
					<p>Give Feedback</p>
					<a href="http://localhost:5555/contact">Help us to improve</a>
				</div>
			</div>
			<hr>
			<div class="settings-links">
				<img src="${classpath}/StorageFolder/images/setting.png" alt=""
					class="settings-icon"> <a href="#">Settings & Privary <img
					src="${classpath}/StorageFolder/images/arrow.png" alt=""></a>
			</div>

			<div class="settings-links">
				<img src="${classpath}/StorageFolder/images/help.png" alt=""
					class="settings-icon"> <a href="#">Help & Support <img
					src="${classpath}/StorageFolder/images/arrow.png" alt=""></a>
			</div>

			<div class="settings-links">
				<img src="${classpath}/StorageFolder/images/Display.png" alt=""
					class="settings-icon"> <a href="#">Display &
					Accessibility <img
					src="${classpath}/StorageFolder/images/arrow.png" alt="">
				</a>
			</div>

			<div class="settings-links">
				<img src="${classpath}/StorageFolder/images/logout.png" alt=""
					class="settings-icon"> <a href="${classpath }/logout">Logout <img
					src="${classpath}/StorageFolder/images/arrow.png" alt=""></a>
			</div>

		</div>
	</nav>

	<!-- content-area---------------------------------------------------------------------- -->

	<div class="container">
		
		<div class="left-sidebar">
						<div class="important-links">
				<a href="${classpath}/user/home" style="color: black"><img
					src="${classpath}/StorageFolder/images/news.png" alt="">Tin mới nhât</a> 
				<a href="${classpath}/user/friends" style="color: black"><img
					src="${classpath}/StorageFolder/images/friends.png" alt="">Bạn bè</a>
				<a href="${classpath}/user/group" style="color: black"><img
					src="${classpath}/StorageFolder/images/group.png" alt="">Nhóm</a>
				<a href="${classpath}/user/profile" style="color: black"><img
					src="${classpath}/StorageFolder/images/watch.png" alt="">Trang cá nhân</a>
				<a href="${classpath}/user/remembers" style="color: black"><img
					src="${classpath}/StorageFolder/images/video.png" alt="">Đã lưu</a>
			</div>
		</div>

		<!-- main-content------- -->

		<div class="content-area">


			<!-- =================== =================-->		
			<!-- =================== =================-->	
			<!--========================= mainSposts ==========================-->
			
			 <h2 style="padding-bottom: 15px">Danh sách bạn bè</h2>
             <c:forEach var="user" items="${myFollows }" varStatus="loop">
				<div class="user-profile">
					<a href="${classpath}/user/profile/${user.id }" style="text-decoration: none; color: black"><img
					src="${classpath}/StorageFolder/${user.avatar }" alt="" style="object-fit: cover; height: 45px">
					</a>
					<p style="overflow: hidden;">${user.nickname }</p>
					<p style="overflow: hidden; margin-left: 10px; padding: 0px; opacity: 0.6; font-size: 14px">${user.description }</p>
				</div>
			</c:forEach>
		</div>

		<!-- sidebar------------ -->
		<div class="right-sidebar">

			<div class="heading-link">
				<h4>Hội thoại</h4>
			</div>

			<c:forEach var="user" items="${rightContentUsers }" varStatus="loop">
				<div class="online-list">
					<div class="online">
						<a href="${classpath }/user/profile/${user.id }"><img src="${classpath}/StorageFolder/${user.avatar}" alt=""></a>
					</div>
					<p>${user.nickname }</p>
				</div>
			</c:forEach>
		</div>
	</div>
	

	<script src="${classpath}/css-js/js/function.js"></script>
</body>

	<script type="text/javascript">
		addPost = function() {		
			//$ === jQuery
			jQuery.ajax({
				url : "/user/post",// action
				type : "POST",
				contentType: "application/json",
				data : JSON.stringify(data),
				dataType : "json", //Kieu du lieu tra ve tu controller la json
				
				success : function(jsonResult) {
					alert(jsonResult.code + ": " + jsonResult.status); 
				},
				
				error : function(jqXhr, textStatus, errorMessage) {
					alert(jsonResult.code + ': Luu thanh cong...!')
				}
			});
		}
	</script>
</html>