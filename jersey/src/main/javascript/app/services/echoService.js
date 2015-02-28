app.service('echoService', function($http, $rootScope, $window, $log) {
this.echo = function(message, callback) {
    $http({
        method: 'GET',
        url: '/jwt-authentication/api/rest/echo',
        params: message
     }).success(function(message){
    	 $log.debug(message);
    	 callback(message);
    }).error(function (data, status, headers, config) {
    	$log.debug('error');
    });
 }
});
