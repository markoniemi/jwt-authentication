app.service('searchService', function($http, $rootScope, $window, $log) {
    this.search = function(username, callback) {
        $http({
            method : 'GET',
            url : '/jwt-authentication/api/rest/search',
            data : username
        }).success(function(data) {
            callback(data);
        }).error(function() {
            $log.debug("error with searchService");
        });
    };
});
