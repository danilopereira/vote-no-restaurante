'use strict';

angular.module('votenorestauranteApp')
    .controller('VoterController', function ($scope, $state, $modal, Voter) {

        $scope.voters = [];
        $scope.loadAll = function() {
            Voter.query(function(result) {
               $scope.voters = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.voter = {
                name: null,
                email: null,
                id: null
            };
        };
    });
