
MocApp.controller('UserController', function ($scope, $http, $location, State, $cookies) {
    

    $scope.user = {
        name: '',
        email: '',
        login: '',
        password: '',
        country: {
            id: 0
        }
    };

    $scope.createUser = function () {

        $scope.user.country["id"] = $scope.selectedCountry;
        var req = {
            method: 'POST',
            url: State.formData['url'] + 'users',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.user
        };

        $http(req)
                .success(function (data) {
                    $scope.user = {
                        name: '',
                        email: '',
                        login: '',
                        password: '',
                        country: {
                            id: 0
                        }
                    };
                    $scope.msg = 'Created, please check your mail to confirm your account!';
                })
                .error(function (data, status, headers, config) {
                    if (status == 409) { 
                        $scope.user.login = '';
                        $scope.user.email = '';
                        $scope.msg = "Login already exist, please change it!";
                    }
                    $scope.msg = "Login already exist, please change it!";
                });

    };

    $scope.getCountries = function () {
        $http.get(State.formData['url'] +'users/countries').success(function (data) {
            $scope.countries = data;
        });
    };
    
    $scope.authenticate = function() {
        
        
        
        $http.post(State.formData['url'] + 'authentication',$scope.user ).
        success(function() {
            $location.path('message').replace();
            $cookies.put('user',$scope.user.login);
        })
        .error(function() {
            $scope.msg = "Authentication failure!";
        });
    };
});
