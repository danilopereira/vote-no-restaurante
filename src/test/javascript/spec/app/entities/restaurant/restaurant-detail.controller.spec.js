'use strict';

describe('Restaurant Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRestaurant;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRestaurant = jasmine.createSpy('MockRestaurant');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Restaurant': MockRestaurant
        };
        createController = function() {
            $injector.get('$controller')("RestaurantDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'votenorestauranteApp:restaurantUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
