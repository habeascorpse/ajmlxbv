/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('MocApp')
    .factory('tokenInterceptor',
    function($window, $q, $location) {
            
        var interceptor = {};
        
        interceptor.response = function(response) {
            console.log('Recebi resposta');
            var token = response.headers('Authorization');
            if (token) {
                $window.sessionStorage.token = token;
                console.log('Token:'+token);
            }
            
            
            return response;
        };
        
        interceptor.request = function(config) {
            config.headers = config.headers || {};
            if ($window.sessionStorage.token) {
                config.headers['Authorization'] = $window.sessionStorage.token;
                console.log('Token no local storage: '+$window.sessionStorage.token);
            }
            
            return config;
                
        };
        
        interceptor.responseError = function(rejection) {
            if (rejection != null && rejection.status == 401) {
                //redirecionar para o login
                delete $window.sessionStorage.token;
                $location.path('/login');
            }
            return $q.reject(rejection);
            
        }
        
        return interceptor;
    }
);
