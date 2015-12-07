'use strict';

angular.module('votenorestauranteApp')
    .controller('MainController', function ($scope, Principal, Restaurant) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.restaurants = [];
        $scope.searchRandonRest = {};

        $scope.searchRandonRest = function(){
            var id = Math.floor((Math.random() * 5) + 1);
            var element = Restaurant.get({id:id});
            $scope.restaurants.push(element);
            id = Math.floor((Math.random() * 5) + 1);
            element = Restaurant.get({id:id});
            $scope.restaurants.push(element);
        }


        $scope.searchRandonRest();
    });
