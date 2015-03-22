app.service('loginService', function($rootScope, $http, $localStorage, $log, jwtHelper) {
    this.login = function(credentials, success, error) {
        $http({
            method : 'POST',
            url : '/jwt-authentication/api/rest/login',
            data : {
                username : credentials.username,
                password : credentials.password
            }
        }).success(function(authenticationToken) {
            $localStorage.authenticationToken = authenticationToken;
            $localStorage.credentials = jwtHelper.decodeToken(authenticationToken);
            $rootScope.$broadcast('login', $localStorage.credentials);
            $log.debug("login successful: " + credentials.username);
            success();
        }).error(function() {
            // Erase the token if the user fails to login
            delete $localStorage.authenticationToken;
            delete $localStorage.credentials;
            $log.debug("login error");
            error();
        });
    };
    this.logout = function() {
        $http({
            method : 'POST',
            url : '/jwt-authentication/api/rest/logout'
        }).success(function() {
            // $localStorage.$reset();
            delete $localStorage.authenticationToken;
            delete $localStorage.credentials;
            $log.debug("log out successful");
            $rootScope.$broadcast('logout');
        }).error(function() {
            $log.debug("unable log out");
        });
    };
});
