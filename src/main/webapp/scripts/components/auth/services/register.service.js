'use strict';

angular.module('votenorestauranteApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


