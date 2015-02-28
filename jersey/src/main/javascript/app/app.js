var app = angular.module('jwt-authentication', [ 'ngResource', 'ngRoute',
		'pascalprecht.translate', 'ui.bootstrap' ]);

app.factory('authInterceptor', function ($rootScope, $q, $window, $location) {
    return {
        request: function (config) {
//            config.headers = config.headers || {};
//        	if($window.sessionStorage.token) {
            if($rootScope.authenticationToken) {
            	config.headers['Authorization'] = 'Bearer ' + $rootScope.authenticationToken;
//            	config.headers['Authorization'] = $rootScope.authenticationToken;
//                config.headers['Authorization'] = $window.sessionStorage.token;
            }
            return config;
        },
        responseError: function (response) {
            if(response.status === 401) {
                $location.path('/login/login');
            }
            return $q.reject(response);
        }
    };
});

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
});
