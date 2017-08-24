(function() {
    angular.module("myApp").controller("loginController", loginController);

    function loginController($http, $location,  $sessionStorage) {
        var vm = this;

        vm.login = login;

        function login() {
            $http.post("/user/login", {"username" : vm.username, "password" : vm.password}).then(
            function(response) {
            	$sessionStorage.user = response.data;
            },
            function(response) {
            	alert("Login Failed! Error:  " + response.status);
            });
        }

    }
})();
