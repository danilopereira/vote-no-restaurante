'use strict';

angular.module('votenorestauranteApp')
    .controller('RestaurantController', function ($scope, $state, $modal, Restaurant) {

     $scope.compare = {};

        $scope.restaurants = [];
        $scope.loadAll = function() {
            Restaurant.query(function(result) {
               $scope.restaurants = result;
               $scope.restaurants.sort($scope.compare);
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.restaurant = {
                name: null,
                votes: null,
                id: null
            };
        };

        $scope.compare = function(value1, value2){
            if(value1.votes < value2.votes){
                return 1;
            }
            if(value1.votes > value2.votes){
                return -1;
            }
            return 0;
        }
    });
