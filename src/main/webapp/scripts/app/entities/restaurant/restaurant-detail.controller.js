'use strict';

angular.module('votenorestauranteApp')
    .controller('RestaurantDetailController', function ($scope, $rootScope, $stateParams, entity, Restaurant) {
        $scope.restaurant = entity;
        $scope.load = function (id) {
            Restaurant.get({id: id}, function(result) {
                $scope.restaurant = result;
            });
        };
        var unsubscribe = $rootScope.$on('votenorestauranteApp:restaurantUpdate', function(event, result) {
            $scope.restaurant = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
