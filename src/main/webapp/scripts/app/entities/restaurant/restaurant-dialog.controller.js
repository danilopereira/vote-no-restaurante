'use strict';

angular.module('votenorestauranteApp').controller('RestaurantDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Restaurant',
        function($scope, $stateParams, $modalInstance, entity, Restaurant) {

        $scope.restaurant = entity;
        $scope.load = function(id) {
            Restaurant.get({id : id}, function(result) {
                $scope.restaurant = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('votenorestauranteApp:restaurantUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.restaurant.id != null) {
                Restaurant.update($scope.restaurant, onSaveSuccess, onSaveError);
            } else {
                Restaurant.save($scope.restaurant, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
