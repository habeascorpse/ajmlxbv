/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
MocApp.controller('MessageController', function ($scope, $http, $location, State, $cookies, $window) {

    
    $scope.textMessage = {text: ""};
    $scope.selectedGroup = {name: null};
    messageTime = 1000; // 1 seconds

    $scope.getGroups = function () {


        $http.get(State.formData['url'] + 'group?maxResult=10').
                success(function (data) {
                    $scope.groups = data;

                })
                .error(function () {
                    $scope.msg = "Unable to load group list";
                    setTimeout($scope.cleanMessage, messageTime);
                });


    };

    $scope.getMessagesFromGroup = function (group) {

        $scope.selectedGroup = group;
        if ((group !== null) && (group.name !== null)) {


            $http.get(State.formData['url'] + 'message?group=' + group.name+'&maxResult='+50).
                    success(function (data) {
                        $scope.messages = data;
                        $scope.messages.forEach(function(entry) {
                            entry.sendDate = new Date(entry.sendDate);
                            entry.sendDate = entry.sendDate.getHours() +":" + entry.sendDate.getMinutes() +":" + entry.sendDate.getSeconds();
                        });
                        rolar();

                    })
                    .error(function () {
                        $scope.msg = "Unable to load messages from this group";
                        setTimeout($scope.cleanMessage, messageTime);
                    });
        }
        rolar();


    };

    $scope.sendMessage = function (group) {
        if ((group !== null) && (group.name !== null)) {

            $http.post(State.formData['url'] + 'message?group=' + group.name, $scope.textMessage).
                    success(function (data, status, headers, config) {
                        $scope.textMessage.text = "";
                        //$scope.getMessagesGromGroup(group);
                    })
                    .error(function () {
                        $scope.msg = "Unable to send message from this group";
                        setTimeout($scope.cleanMessage, messageTime);
                    });
        }


    };

    $scope.searchContact = function (search) {
        $scope.contacts = null;
        $http.get(State.formData['url'] + 'contact?search=' + search ).
                success(function (data) {
                    $scope.contacts = data;

                })
                .error(function () {
                    $scope.msg = "Contact is not found!";
                    setTimeout($scope.cleanMessage, messageTime);
                });
        
    };

    $scope.main = function () {
        
        $scope.getMessagesFromGroup($scope.selectedGroup);
        $scope.getGroups();
        setTimeout($scope.main, 2000);
        


    };

    $scope.addContact = function (contact) {
        
        var req = {
            method: 'POST',
            url: State.formData['url'] + 'contact?name='+contact.login
        };

        $http(req).
            success(function (data, status, headers, config) {
                $scope.msg = "Contact added!";
                $scope.contacts = null;
                $scope.getGroups();
                
            })
            .error(function () {
                $scope.msg = "Unable to add contact!";
            });
            
       setTimeout($scope.cleanMessage, messageTime);

    };
    
    
    $scope.cleanMessage = function () {
        $scope.msg = null;
    };
    
    $scope.logoff = function() {
        delete $window.sessionStorage.token;
        $location.path('login').replace();  
    };
    
    $scope.getClassTalkBox = function(message) {
        
        if (message.userGroup.mocUser.login === $cookies.get('user'))
            return "me";
        else
            return "their";
        
    };
    


});

