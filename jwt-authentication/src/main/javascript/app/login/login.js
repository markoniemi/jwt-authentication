function LoginCtrl($location, $http, $scope, $localStorage, $log,
		loginService) {
	$scope.$storage = $localStorage;
	$scope.login = function() {
		loginService.login({
			username : $scope.username,
			password : $scope.password
		}, success, error);
		$location.path("/echo/echo");
	}
	success = function(){
		$scope.authenticationToken = $localStorage.authenticationToken
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