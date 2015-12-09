'use strict';

angular.module('votenorestauranteApp').controller('VoterDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Voter',
        function($scope, $stateParams, $modalInstance, entity, Voter) {

        $scope.voter = entity;
        $scope.load = function(id) {
            Voter.get({id : id}, function(result) {
                $scope.voter = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('votenorestauranteApp:voterUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.voter.id != null) {
                Voter.update($scope.voter, onSaveSuccess, onSaveError);
            } else {
                Voter.save($scope.voter, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
