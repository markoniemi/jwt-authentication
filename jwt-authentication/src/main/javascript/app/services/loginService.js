app.service('loginService', function($http, $localStorage, $log, jwtHelper) {
this.login = function(credentials, success, error) {
    $http({
        method: 'POST',
        url: '/jwt-authentication/api/rest/login',
        data: {username: credentials.username, password: credentials.password}
     }).success(function(authenticationToken){
    	 $localStorage.authenticationToken = authenticationToken;
    	 $localStorage.credentials = jwtHelper.decodeToken(authenticationToken);
    	 $log.debug(credentials);
    	 $log.debug("login successful");
    	 success();
    }).error(function (data, status, headers, config) {
        // Erase the token if the user fails to login
        delete $localStorage.authenticationToken;
        $log.debug("login error");
        error();
    });
 }
this.logout = function(callbackFunc) {
	$http({
		method: 'POST',
		url: '/jwt-authentication/api/rest/logout'
	}).success(function(authenticationToken){
//		$localStorage.$reset();
		delete $localStorage.authenticationToken;
		$log.debug("log out successful");
	}).error(function(){
		$log.debug("unable log out");
	});
}
});
