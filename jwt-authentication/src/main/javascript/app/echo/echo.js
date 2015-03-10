function EchoCtrl($location, $scope, echoService, $log) {
	$scope.message = 'Message';
	callback = function(message){
		$scope.messageFromServer = message;
		$log.debug($scope.messageFromServer);
	}
	$scope.echo = function() {
		$scope.messageFromServer = echoService.echo({
			message : $scope.message
		}, callback);
//		$location.path("/echo/echo");
	}
}