<!-- <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
------------ -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- directive của JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>



<!DOCTYPE html>
<html>
<head>
<title>Chat</title>
<link rel="stylesheet" href="${classpath }/css-js/css/chat-app.css"></link>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
	integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.js"></script>
</head>
<!--Coded With Love By Mutiullah Samim-->
<body>
	<div class="container-fluid h-100">
		<div class="row justify-content-center h-100">
			<div class="col-md-4 col-xl-3 chat">
				<div class="card mb-sm-3 mb-md-0 contacts_card">
					<div class="card-header">
						<a href="${classpath}/user/home"><img style="width: 50%" class="logo" src="${classpath}/StorageFolder/images/logo.png" alt=""></a>
						<div class="input-group">
							<input type="text" placeholder="Search..." name=""
								class="form-control search">
							<div class="input-group-prepend">
								<span class="input-group-text search_btn"><i
									class="fas fa-search"></i></span>
							</div>
						</div>
					</div>
					<div class="card-body contacts_body">
						<ul class="contacts">
							<c:forEach items="${users }" var="user">

								<!-- Danh sách partner -->
								<c:if test="${user.id != subjectUser.id }">
									<c:choose>
										<c:when test="${user.id == partnerUser.id }">
											<li class="active">
												<div class="d-flex bd-highlight">
													<div class="img_cont">
														<img src="${classpath }/StorageFolder/${user.avatar }" class="rounded-circle user_img">
														<span class="online_icon"></span>
													</div>
													<div class="user_info">
														<a href="${classpath }/chat-with/${user.id }"> <span>${user.nickname }</span>
														</a>
														<p>${user.nickname } is online</p>
													</div>
												</div>
											</li>
										</c:when>
										<c:when test="${user.id != partnerUser.id }">
											<li>
												<div class="d-flex bd-highlight">
													<div class="img_cont">
														<img src="${classpath }/StorageFolder/${user.avatar }" class="rounded-circle user_img">
														<span class="online_icon offline"></span>
													</div>
													<div class="user_info">
														<a href="${classpath }/chat-with/${user.id }"> <span>${user.nickname }</span>
														</a>
														<p>${user.nickname } is offline</p>
													</div>
												</div>
											</li>
										</c:when>
										<c:otherwise>
											<li>
												<div class="d-flex bd-highlight">
													<div class="img_cont">
														<img src="${classpath }/StorageFolder/${user.avatar }" class="rounded-circle user_img">
														<span class="online_icon"></span>
													</div>
													<div class="user_info">
														<a href="${classpath }/chat-with/${user.id }"> <span>${user.nickname }</span>
														</a>
														<p>${user.nickname } is online</p>
													</div>
												</div>
											</li>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="card-footer"></div>
				</div>
			</div>
			<div class="col-md-8 col-xl-6 chat">
				<sf:form action="${classpath }/send-message" method="post"
					modelAttribute="getMessages">
					<div class="card">
						<!-- thanh thông tin parter -->
						<div class="card-header msg_head">
							<div class="d-flex bd-highlight">
								<div class="img_cont">
									<img src="${classpath }/StorageFolder/${partnerUser.avatar }"
										class="rounded-circle user_img"> <span
										class="online_icon"></span>
								</div>
								<div class="user_info">
									<span>Chat with ${partnerUser.nickname }</span>
									<p>${totalMessages }messages</p>
								</div>
								<div class="video_cam">
									<span><i class="fas fa-video"></i></span> <span><i
										class="fas fa-phone"></i></span>
								</div>
							</div>
							<span id="action_menu_btn"><i class="fas fa-ellipsis-v"></i></span>
							<div class="action_menu">
								<ul>
									<li><i class="fas fa-user-circle"></i> View profile</li>
									<li><i class="fas fa-users"></i> Add to close friends</li>
									<li><i class="fas fa-plus"></i> Add to group</li>
									<li><i class="fas fa-ban"></i> Block</li>
								</ul>
							</div>
						</div>
						<!-- Nội dung tin nhắn -->
						<div class="card-body msg_card_body">
							<c:forEach items="${getMessages }" var="message" varStatus="loop">

								<c:if test="${message.user_message.getId() == partnerUser.id }">
									<div class="d-flex justify-content-start mb-4">
										<div class="img_cont_msg">
											<img src="${classpath }/StorageFolder/${partnerUser.avatar }"
												class="rounded-circle user_img_msg">
										</div>
										<div class="msg_cotainer">
										<span class="parnav">
											${message.content }</span>
											<div style="min-width: 50px; text-align-content: center">
												<span class="msg_time"><fmt:formatDate value="${message.createDate }" pattern="MM-dd HH:mm" /></span>
											</div>
										</div>
									</div>
								</c:if>
								<c:if test="${message.user_message.getId() == subjectUser.id}">
									<div class="d-flex justify-content-end mb-4">
										<div class="msg_cotainer_send">
											<span class="parnav"> ${message.content }
												<ul class="subnav">
													<li style="padding:0; ">
													<a style="text-decoration: none; color: black; padding:0; "
														href="${classpath }/delete-message/${message.id }">delete</a></li>
												</ul>
											</span>
											<div style="min-width: 50px; text-align-content: center">
												<span class="msg_time"><fmt:formatDate value="${message.createDate }" pattern="MM-dd HH:mm" /></span>
											</div>
										</div>
										<div class="img_cont_msg">
											<img src="${classpath }/StorageFolder/${subjectUser.avatar }"
												class="rounded-circle user_img_msg">
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<div class="card-footer">
							<div class="input-group">
								<div class="input-group-append">
									<span class="input-group-text attach_btn"><i
										class="fas fa-paperclip"></i></span>
								</div>
								<textarea name="message" id="message"
									class="form-control type_msg"
									placeholder="Type your message..."></textarea>
								<div class="input-group-append">
									<!--  onclick="sendMessage(${getMessages.get(0) })"-->
									<button onclick="sendMessage(${getMessages.get(0) })"
										type="submit"
										style="height: 100%; width: 100%; color: rgba(100, 0, 0, 0)">
										<span class="input-group-text send_btn"><i
											class="fas fa-location-arrow"></i></span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</sf:form>
			</div>
		</div>
	</div>


	<script>
            $(document).ready(function(){
                $('#action_menu_btn').click(function(){
                    $('.action_menu').toggle();
                });
            });

        </script>

</body>
</html>
