 'use strict';

angular.module('votenorestauranteApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-votenorestauranteApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-votenorestauranteApp-params')});
                }
                return response;
            }
        };
    });
