app.service('loginService', function($http, $rootScope, $window, $log) {
this.login = function(credentials, success, error) {
    $http({
        method: 'POST',
        url: '/jwt-authentication/api/rest/login',
        data: {username: credentials.username, password: credentials.password}
     }).success(function(authenticationToken){
    	 //$cookieStore.put('authToken', authenticationToken);
    	 $rootScope.authenticationToken = authenticationToken;
//    	 $window.sessionStorage.token = authenticationToken;
    	 $log.debug("success");
    	 success();
    }).error(function (data, status, headers, config) {
        // Erase the token if the user fails to login
//        delete $window.sessionStorage.token;
        $rootScope.authenticationToken = null;
        $log.debug("error");
        error();
    });
 }
this.logout = function(callbackFunc) {
	$http({
		method: 'POST',
		url: '/jwt-authentication/api/rest/logout'
	}).success(function(authenticationToken){
		//$cookieStore.put('authToken', authenticationToken);
//		delete $window.sessionStorage.token;
		$rootScope.authenticationToken = null;
	}).error(function(){
		$log.debug('error');
	});
}
});
