'use strict';

angular.module('votenorestauranteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('voter', {
                parent: 'entity',
                url: '/voters',
                data: {
//                    authorities: ['ROLE_USER'],
                    pageTitle: 'votenorestauranteApp.voter.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/voter/voters.html',
                        controller: 'VoterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('voter');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('voter.detail', {
                parent: 'entity',
                url: '/voter/{id}',
                data: {
//                    authorities: ['ROLE_USER'],
                    pageTitle: 'votenorestauranteApp.voter.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/voter/voter-detail.html',
                        controller: 'VoterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('voter');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Voter', function($stateParams, Voter) {
                        return Voter.get({id : $stateParams.id});
                    }]
                }
            })
            .state('voter.new', {
                parent: 'voter',
                url: '/new',
                data: {
//                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/voter/voter-dialog.html',
                        controller: 'VoterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    email: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('voter', null, { reload: true });
                    }, function() {
                        $state.go('voter');
                    })
                }]
            })
            .state('voter.edit', {
                parent: 'voter',
                url: '/{id}/edit',
                data: {
//                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/voter/voter-dialog.html',
                        controller: 'VoterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Voter', function(Voter) {
                                return Voter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('voter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('voter.delete', {
                parent: 'voter',
                url: '/{id}/delete',
                data: {
//                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/voter/voter-delete-dialog.html',
                        controller: 'VoterDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Voter', function(Voter) {
                                return Voter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('voter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
