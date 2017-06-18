(function() {
    angular.module("myApp").controller("loginController", loginController);

    function loginController($http, $location) {
        var vm = this;

        vm.login = login;

        function login() {
            $http.post("/users/login", {"username" : vm.username, "password" : vm.password})
                .then(function(response) {
                    sessionStorage.user = response.data;
                    if(response.data.uloga === "ODBORNIK")
                        $location.path("/alderman");
                    else
                        $location.path("/president");
            });
        }

    }
})();
