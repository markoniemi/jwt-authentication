function MenuCtrl($scope, $log, $localStorage) {
	$scope.credentials = $localStorage.credentials;
	$scope.$on('login', function(event, credentials) {
		$scope.credentials = credentials;
	});
	$scope.$on('logout', function(event, credentials) {
		$scope.credentials = null;
	});
}