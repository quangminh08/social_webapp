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
<!-- variables -->
<jsp:include page="/WEB-INF/views/common/variables.jsp"></jsp:include>

<!-- Custome css resource file -->
<!-- <link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous"> -->
	<link rel="stylesheet" href="${classpath }/css-js/css/login.css">
	<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
<!-- <script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
	crossorigin="anonymous"></script> -->

<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Signup</title>
</head>
<body style="background: rgba(220, 220, 220, 0.6)">

	<div id="wrapper">
		<form action="${classpath }/register-manager" method="post" id="form-login"
			style="position: relative;">
<%-- 			<c:if test="${not empty param.login_error }">
				<div class="alert alert-danger" role="alert">Đăng ký không
					thành công!!!</div>
			</c:if> --%>
			<h1 class="form-heading">Tạo tài khoản người quản lý</h1>
			<div class="form-group">
				<i class="far fa-user"></i> <input type="text" class="form-input"
					id="username" name="username" placeholder="Tên đăng nhập">
			</div>
			<div class="form-group" style="padding-bottom: 0px; margin-bottom:0px">
				<i class="fas fa-key"></i> <input type="password" class="form-input"
					id="password" name="password" placeholder="Mật khẩu">
				<div id="eye">
					<i class="far fa-eye"></i>
				</div>
			</div>
			<small id="passwordHelpBlock" class="form-text" style="color: white; font-size:10px">Mật khẩu có ít
							nhất 8 ký tự bao gồm một số và một ký tự đặc biệt.</small>
			<div class="form-group">
				<i class="fas fa-key"></i> <input type="password" class="form-input"
					id="retypePassword" name="retypePassword" placeholder="Nhập lại mật khẩu">
				<div id="eye2">
					<i class="far fa-eye" style="padding: 0px;"></i>
				</div>
			</div>
			<div class="form-group">
				<i class="far fa-user"></i> <input type="text" class="form-input"
					id="email" name="email" placeholder="email">
			</div>
			<div class="form-group">
				<i class="far fa-user"></i> <input type="text" class="form-input"
					id="nickname" name="nickname" placeholder="Tên người dùng">
			</div>
			<input type="submit" value="Đăng ký" class="form-submit">
			<a href="${classpath }/user/home"><input type="button" value="Về trang chủ" class="form-submit"></a>
		</form>
	</div>

</body>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#eye').click(function() {
			$(this).toggleClass('open');
			$(this).children('i').toggleClass('fa-eye-slash fa-eye');
			if ($(this).hasClass('open')) {
				$(this).prev().attr('type', 'text');
			} else {
				$(this).prev().attr('type', 'password');
			}
		});
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#eye2').click(function() {
			$(this).toggleClass('open');
			$(this).children('i').toggleClass('fa-eye-slash fa-eye');
			if ($(this).hasClass('open')) {
				$(this).prev().attr('type', 'text');
			} else {
				$(this).prev().attr('type', 'password');
			}
		});
	});
</script>
</html>