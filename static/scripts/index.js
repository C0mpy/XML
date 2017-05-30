(function() {
    angular.module("myApp").controller("indexController", indexController);

    function indexController() {
        var vm = this;

        vm.msg = "hello from index ctrl!";
        console.log("is sunny here in indexCtrl!");
    }
})();