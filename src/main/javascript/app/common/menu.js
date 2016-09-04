function menuCtrl($scope, $localStorage) {
    $scope.user = $localStorage.user;
    $scope.$on('login', function(event, user) {
        $scope.user = user;
    });
    $scope.$on('logout', function() {
        $scope.user = null;
    });
}