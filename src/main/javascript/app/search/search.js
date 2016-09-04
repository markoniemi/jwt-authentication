function searchCtrl($scope, $localStorage, searchService, $log, $location) {
    $scope.$storage = $localStorage;
    $scope.search = function() {
        searchService.search({
            username : $scope.username
        }, function(users) {
            $log.debug(users);
            $scope.users = users;
            $location.path("/search/search");
        });
    };
}