'use strict';

angular.module('votenorestauranteApp')
    .controller('MainController', function ($scope, Principal, Restaurant) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.restaurants = Restaurant.query();
    });
