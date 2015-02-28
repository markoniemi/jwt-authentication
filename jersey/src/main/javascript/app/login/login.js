function LoginCtrl(/* store, */$location, $http, $scope, $rootScope, $window,
		loginService) {
	$scope.login = function() {
		loginService.login({
			username : $scope.username,
			password : $scope.password
		});
//		$scope.authenticationToken = $window.sessionStorage.token;
		$scope.authenticationToken = $rootScope.authenticationToken
		$location.path("/echo/echo");
	}
	$scope.logout = function() {
		loginService.logout();
		$location.path("/login/login");
	}
}