function menuCtrl($scope, $localStorage) {
    $scope.credentials = $localStorage.credentials;
    $scope.$on('login', function(event, credentials) {
        $scope.credentials = credentials;
    });
    $scope.$on('logout', function() {
        $scope.credentials = null;
    });
}