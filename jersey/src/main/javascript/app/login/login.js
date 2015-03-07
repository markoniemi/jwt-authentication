function LoginCtrl(/* store, */$location, $http, $scope, $rootScope, $window, $log,
		loginService) {
	$scope.login = function() {
		loginService.login({
			username : $scope.username,
			password : $scope.password
		}, success, error);
//		$scope.authenticationToken = $window.sessionStorage.token;
		$location.path("/echo/echo");
	}
	success = function(){
		$scope.authenticationToken = $rootScope.authenticationToken
		$location.path("/echo/echo");
	}
	error = function(){
		$location.path("/login/login");
		$scope.errorMessage = "Login error";
	}
	$scope.logout = function() {
		loginService.logout();
		$location.path("/login/login");
	}
}