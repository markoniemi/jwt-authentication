app.config([ '$routeProvider', function($routeProvider, $log) {
  $log.debug("route");
	$routeProvider.when('/:group/:page/:id', {
		templateUrl: function(routeParams) {
			return createTemplateUrl(routeParams.group, routeParams.page);
		}
	});
	$routeProvider.when('/:group/:page', {
		templateUrl: function(routeParams) {
			return createTemplateUrl(routeParams.group, routeParams.page);
		}
	});
	$routeProvider.when('/:group', {
		templateUrl: function(routeParams) {
			return createTemplateUrl(routeParams.group, routeParams.group);
		}
	});
	$routeProvider.otherwise({
		redirectTo: '/login/login'
	});
} ]);

function createTemplateUrl(group, page){
	templateUrl = 'app/' + group + '/' + page + '.xhtml';
  $log.debug("route url template: " + templateUrl);
	return templateUrl;
}