'use strict';

angular.module('votenorestauranteApp')
    .controller('MainController', function ($scope, Principal, Restaurant, Vote) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.restaurants = [];
        $scope.searchRandonRest = {};
        $scope.vote = {};

        $scope.searchRandonRest = function(){
//        Restaurant.get({id: 2}, function(result) {
//                        console.log(result.votes);
//                    });
            var id1 = Math.floor((Math.random() * 5));
            var id2 = Math.floor((Math.random() * 5));
            Restaurant.query(function(result) {
                        $scope.restaurants.push(result[id1]);
                        $scope.restaurants.push(result[id2]);
                        console.log($scope.restaurants);
            });



        }

        $scope.vote.doClick = function(itemId){
            console.log(itemId);
            Vote.vote(itemId);
        }


        $scope.searchRandonRest();
    });
