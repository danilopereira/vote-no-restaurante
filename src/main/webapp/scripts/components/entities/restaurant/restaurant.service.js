'use strict';

angular.module('votenorestauranteApp')
    .factory('Restaurant', function ($resource, DateUtils) {
        return $resource('api/restaurants/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
        });
    })
    .factory('Vote', function($resource, DateUtils){
        return $resource("/api/restaurants/vote/:id", {}, {
        'vote':{ method : 'PUT'}
        })
    });
