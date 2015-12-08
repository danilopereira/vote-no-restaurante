'use strict';

angular.module('votenorestauranteApp')
    .controller('MainController', function ($scope, Principal, Restaurant, Vote) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.restaurants = [];
        $scope.restAux = [];
        $scope.searchRandonRest = {};
        $scope.vote = {};

        $scope.searchRandonRest = function(){
//        Restaurant.get({id: 2}, function(result) {
//                        console.log(result.votes);
//                    });
            Restaurant.query(function(result) {
                        $scope.restaurants = result;
                        $scope.restAux = result;
                        console.log($scope.restaurants);
            });



        }

        $scope.vote.doClick = function(itemId){
            console.log(itemId);
            Vote.vote(itemId);
        }


        $scope.searchRandonRest();
    });
