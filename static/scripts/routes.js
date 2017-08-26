angular
    .module("myApp", ['ngResource', 'ngRoute', 'restangular', 'lodash', 'ngMessages', 'ngStorage', 'angularCSS'])
    .config(["$routeProvider", function($routeProvider) {
        $routeProvider
            .when("/", {
                templateUrl: "../login.html",
                controller: "loginController",
                controllerAs: "loginCtrl",
                css: "../styles/login.css"
            })
            .when("/alderman", {
                templateUrl: "../alderman.html",
                controller: "aldermanController",
                controllerAs: "aldermanCtrl"
            })
            .when("/president", {
                templateUrl: "../president.html",
                controller: "presidentController",
                controllerAs: "presidentCtrl"
            })
            .when("/citizen", {
                templateUrl: "../citizen.html",
                controller: "citizenController",
                controllerAs: "citizenCtrl"
            })
            .otherwise({
                redirectTo: "/"
            });
    }])
    .run(['Restangular', '$log', function(Restangular, $log) {
        Restangular.setBaseUrl("api");
        Restangular.setErrorInterceptor(function (response) {
            if (response.status === 500) {
                $log.info("internal server error");
            }
            return true;
        });
    }]);