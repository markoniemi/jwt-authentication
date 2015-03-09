app.service('echoService', function($http, $rootScope, $window, $log) {
this.echo = function(message, callback) {
    $http({
        method: 'POST',
        url: '/jwt-authentication/api/rest/echo',
        data: message
     }).success(function(data){
    	 callback(data.message);
    }).error(function (data, status, headers, config) {
    	$log.debug("error with echoService");
    });
 }
});
