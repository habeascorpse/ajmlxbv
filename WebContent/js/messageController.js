/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
MocApp.controller('MessageController', function ($scope, $http, $location, State, $cookies) {

    //$scope.voucher = State.formData['voucher'];
    $scope.textMessage = {text: ""};
    $scope.selectedGroup = {name: null};
    messageTime = 5000; // 5 seconds

    $scope.getGroups = function () {


        $http.get(State.formData['url'] + 'group/all/' + $cookies.get('voucher')).
                success(function (data, status, headers, config) {
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


            $http.get(State.formData['url'] + 'message/get/' + group.name + '/' + $cookies.get('voucher')).
                    success(function (data, status, headers, config) {
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

            $http.post(State.formData['url'] + 'message/send/' + group.name + '/' + $cookies.get('voucher'), $scope.textMessage).
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
        $http.get(State.formData['url'] + 'contact/find/' + search + '/' + $cookies.get('voucher')).
                success(function (data, status, headers, config) {
                    $scope.contacts = data;

                })
                .error(function () {
                    $scope.msg = "Contact is not found!";
                    setTimeout($scope.cleanMessage, messageTime);
                });
        
    };

    $scope.main = function () {
        
        if ($cookies.get('voucher') === '' || $cookies.get('voucher') === null ) {
            $location.path('login').replace();
        }

        $scope.getMessagesFromGroup($scope.selectedGroup);
        $scope.getGroups();
        setTimeout($scope.main, 2000);
        


    };

    $scope.addContact = function (contact) {
        
        var req = {
            method: 'POST',
            url: State.formData['url'] + 'contact/add/' + $cookies.get('voucher'),
            headers: {
                'Content-Type': 'text/plain'
            },
            data : contact.login
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
        $cookies.put('voucher','');
        $location.path('login').replace();  
    };
    
    $scope.getClassTalkBox = function(message) {
        
        if (message.userGroup.mocUser.login === $cookies.get('user'))
            return "me";
        else
            return "their";
        
    };
    


});

