function echoCtrl($scope, $localStorage, echoService, $log) {
    $scope.$storage = $localStorage;
    $scope.message = 'Message';
    callback = function(message) {
        $scope.messageFromServer = message;
        $log.debug($scope.messageFromServer);
    };
    $scope.echo = function() {
        echoService.echo({
            message : $scope.message
        }, callback);
    };
}