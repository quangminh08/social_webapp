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
<%-- 	<form action="${classpath }/register" method="post">
		<div class="container text-dark">
			<h2 class="text-center p-3">SIGN UP</h2>
			<div class="row">
				<div class="col-md-6">
					<div class="col-md-12">
						<label for="account" class="form-label">Tài khoản</label> <input
							type="text" id="username" name="username" class="form-control"
							aria-describedby="passwordHelpBlock" placeholder="your username">
					</div>
					<div class="col-md-12 mt-4">
						<label for="inputPassword5" class="form-label">Mật khẩu</label> <input
							type="password" id="password" name="password"
							class="form-control" aria-describedby="passwordHelpBlock"
							placeholder="your passsword">
						<div id="passwordHelpBlock" class="form-text">Mật khẩu có ít
							nhất 8 ký tự bao gồm một số và một ký tự đặc biệt.</div>
					</div>
					<div class="col-md-12 mt-4">
						<label for="inputPassword5" class="form-label">Nhập lại
							mật khẩu </label> <input type="password" id="retypePassword"
							class="form-control" aria-describedby="passwordHelpBlock"
							placeholder="retype passsword">
					</div>
					<div class="col-md-12 mt-4">
						<label for="account" class="form-label">Email</label> <input
							type="text" id="email" name="email" class="form-control"
							aria-describedby="passwordHelpBlock" placeholder="your email">
					</div>
					<div class="col-md-12">
						<label for="nickname" class="form-label">Tài khoản</label> <input
							type="text" id="nickname" name="nickname" class="form-control"
							aria-describedby="passwordHelpBlock" placeholder="your nickname">
					</div>
					<div class="row">
						<button type="submit" class="btn btn-info ml-4 mt-2"
							style="margin-left: 82%; max-width: 100px">Register</button>
					</div>
				</div>
								<div class="col-md-6" style="max-height: 400px">
					<img alt="" src="${classpath }/user/img/products/4.jpeg"
						class="h-55"
						style="margin-top: 40px; height: 100%; overflow: hidden;">
				</div>

			</div>
		</div>
	</form> --%>

	<div id="wrapper">
		<form action="${classpath }/register" method="post" id="form-login"
			style="position: relative;">
<%-- 			<c:if test="${not empty param.login_error }">
				<div class="alert alert-danger" role="alert">Đăng ký không
					thành công!!!</div>
			</c:if> --%>
			<h1 class="form-heading">Đăng ký</h1>
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