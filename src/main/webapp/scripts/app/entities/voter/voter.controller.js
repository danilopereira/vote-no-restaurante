'use strict';

angular.module('votenorestauranteApp')
    .controller('VoterController', function($scope, Voter, $location) {

                                         $scope.save = function () {
                                             $scope.isSaving = true;
                                             Voter.save($scope.voter);
                                             $location.path("/restaurants");

                                         };

                                 });

