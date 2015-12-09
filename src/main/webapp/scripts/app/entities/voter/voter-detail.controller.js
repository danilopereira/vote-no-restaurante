'use strict';

angular.module('votenorestauranteApp')
    .controller('VoterDetailController', function ($scope, $rootScope, $stateParams, entity, Voter) {
        $scope.voter = entity;
        $scope.load = function (id) {
            Voter.get({id: id}, function(result) {
                $scope.voter = result;
            });
        };
        var unsubscribe = $rootScope.$on('votenorestauranteApp:voterUpdate', function(event, result) {
            $scope.voter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
