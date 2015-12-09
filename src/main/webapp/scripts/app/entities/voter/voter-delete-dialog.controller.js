'use strict';

angular.module('votenorestauranteApp')
	.controller('VoterDeleteController', function($scope, $modalInstance, entity, Voter) {

        $scope.voter = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Voter.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });