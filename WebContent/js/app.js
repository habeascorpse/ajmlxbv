/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var MocApp = angular.module('MocApp', ['ngRoute', 'ngCookies']);
MocApp.config(function ($routeProvider, $locationProvider, $httpProvider)
{
    // remove o # da url
    //$locationProvider.html5Mode(true);
    
    $httpProvider.interceptors.push('tokenInterceptor');
    

    $routeProvider

            .when('/', {
                templateUrl: 'home.html',
                controller: 'UserController'
            })

            .when('/login', {
                templateUrl: 'login.html',
                controller: 'UserController'
            })

            .when('/message', {
                templateUrl: 'talk.html',
                controller: 'MessageController'
            })

            .when('/home', {
                templateUrl: 'home.html',
                controller: 'UserController'
            })
            .otherwise({redirectTo: '/'});
    // caso não seja nenhum desses, redirecione para a rota '/'
    //.otherwise ({ redirectTo: '/' });
});
MocApp.factory('State', function ($cookies) {
    return {
        formData: {
            url: 'http://localhost:8080/hemr/app/'

        },
    };
}).run(function ($rootScope) {
    $rootScope.$on('$viewContentLoaded', function (values) {
        componentHandler.upgradeAllRegistered();
    });
});


