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
<link rel="stylesheet" href="${classpath }/css-js/css/login.css">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">


<title>login</title>
</head>
<body>
	<div id="wrapper">
		<form action="${classpath }/login_processing_url" id="form-login"
			method="POST" style="position: relative;">
			<c:if test="${not empty param.login_error }">
				<div class="alert alert-danger" role="alert">Đăng nhập không
					thành công!!!</div>
			</c:if>
			<h1 class="form-heading">Đăng nhập</h1>
			<div class="form-group">
				<i class="far fa-user"></i> <input type="text" class="form-input"
					id="username" name="username" placeholder="Tên đăng nhập">
			</div>
			<div class="form-group">
				<i class="fas fa-key"></i> <input type="password" class="form-input"
					id="password" name="password" placeholder="Mật khẩu">
				<div id="eye">
					<i class="far fa-eye"></i>
				</div>
			</div>
<%-- 			<a href="${classpath }/login_processing" role="button"
				aria-pressed="true"><input type="submit" value="Đăng nhập" class="form-submit">
			</a> --%>
			<input type="submit" value="Đăng nhập" class="form-submit">
			<a role="button" href="${classpath }/signup" class="form-submit" style="position: absolute; margin-top:75px; left:30px; bottom:5px; text-decoration: none; max-width:160px; text-align: center">Đăng ký</a>
			<a role="button" href="#" class="form-submit" style="position: absolute; margin-top:75px; right:30px; bottom:5px; text-decoration: none; max-width: 160px; text-align: center">Quên mật khẩu</a>
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
</html>