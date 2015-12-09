'use strict';

angular.module('votenorestauranteApp')
	.controller('RestaurantDeleteController', function($scope, $modalInstance, entity, Restaurant) {

        $scope.restaurant = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Restaurant.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });