'use strict';

angular.module('votenorestauranteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('restaurant', {
                parent: 'entity',
                url: '/restaurants',
                data: {
                    pageTitle: 'votenorestauranteApp.restaurant.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/restaurant/restaurants.html',
                        controller: 'RestaurantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('restaurant');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('restaurant.detail', {
                parent: 'entity',
                url: '/restaurant/{id}',
                data: {
                    pageTitle: 'votenorestauranteApp.restaurant.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-detail.html',
                        controller: 'RestaurantDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('restaurant');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Restaurant', function($stateParams, Restaurant) {
                        return Restaurant.get({id : $stateParams.id});
                    }]
                }
            })
            .state('restaurant.new', {
                parent: 'restaurant',
                url: '/new',
                data: {
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-dialog.html',
                        controller: 'RestaurantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    votes: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('restaurant', null, { reload: true });
                    }, function() {
                        $state.go('restaurant');
                    })
                }]
            })
            .state('restaurant.edit', {
                parent: 'restaurant',
                url: '/{id}/edit',
                data: {
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-dialog.html',
                        controller: 'RestaurantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Restaurant', function(Restaurant) {
                                return Restaurant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('restaurant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('restaurant.delete', {
                parent: 'restaurant',
                url: '/{id}/delete',
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-delete-dialog.html',
                        controller: 'RestaurantDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Restaurant', function(Restaurant) {
                                return Restaurant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('restaurant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
