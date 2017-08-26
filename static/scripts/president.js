(function() {
    angular.module("myApp").controller("presidentController", presidentController);

    function presidentController($http, $location) {
        var vm = this;
        
        console.log("hey");
        vm.sessions = [{"id" : 0, "date" : new Date()}, {"id" : 0, "date" : new Date()}, {"id" : 0, "date" : new Date()}];

    }
})();
