'use strict';

angular.module('votenorestauranteApp')
    .controller('MainController', function ($scope, Principal, Restaurant, Vote, $location) {

        $scope.restaurants = [];
        $scope.restView = [];
        $scope.searchRandonRest = {};
        $scope.vote = {};
        $scope.count1 = 0;
        $scope.count2 = $scope.count1 + 1;

        $scope.searchRandonRest = function(){
//        Restaurant.get({id: 2}, function(result) {
//                        console.log(result.votes);
//                    });
            $scope.restView = [];
            Restaurant.query(function(result) {
                        $scope.restaurants = result;

                        $scope.restView.push($scope.restaurants[$scope.count1]);
                        $scope.restView.push($scope.restaurants[$scope.count2]);


            });
        }

        $scope.vote.doClick = function(item){
            if($scope.count2 < $scope.restaurants.length -1){
                $scope.count2++;

            }
            else if($scope.count1 < $scope.restaurants.length -2){
                 $scope.count1++;
                 $scope.count2 = $scope.count1 +1;
            }
            else{
                $location.path("/voters");
            }
            if(item.votes === null){
                item.votes = 1;
            }
            else{
                item.votes++;
            }
            Restaurant.update(item);
            console.log(item);
            $scope.searchRandonRest();

        }


        $scope.searchRandonRest();
    });
