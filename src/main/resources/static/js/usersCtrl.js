app.controller('users', ['$rootScope', '$scope', '$http', '$resource', 'User', function($rootScope, $scope, $http, $resource, User) {
	console.log('users ctrl');
	
	var allUsers = User.get(null, function () {
		if (allUsers._embedded) {			
			$scope.users = allUsers._embedded.secureUsers;
		}
	});
	
	$scope.searchUsers = function searchUsers() {
		
		if ($rootScope.currentUser) {
			console.log('current user: ' + JSON.stringify($rootScope.currentUser));
			
			if ($scope.byName) {			
				var User = $resource(getNameUrl(), { name: $scope.byName });
				var users = User.get(null, function () {
					console.debug('users: ' + users);
					$scope.users = users.name ? [users] : [];
				});
			} else {			
				var User = $resource(getAllUrl());
				var users = User.get(null, function () {
					$scope.users = users._embedded.secureUsers;
				});
			}
		}
	}
	
	function getNameUrl() {
		return '/rest/users/search/name/:name';
	}
	
	function getAllUrl() {
		return '/rest/users/';
	}
	
    $scope.addUser = function addUser() {
        $scope.users.push({
        	'name' : '',
            'role' : 'ROLE_USER',
            'edit' : true,
            'newUser' : true
        });
    };

    $scope.removeUser = function removeUser(user) {
        var index = $scope.users.indexOf(user);
        if (index !== -1 && !user.newUser) {
        	User.delete({ id:getId(user)});
            $scope.users.splice(index, 1);
        }
    }
    
    $scope.editUser = function editUser(user) {
        user.edit = true;
    }
    
    $scope.saveUser = function saveUser(user) {
    	var index = $scope.users.indexOf(user);
    	
    	user.edit = false;
    	var userToSave = new User();
    	userToSave.name = user.name;
    	userToSave.role = user.role;
    	if (user.newUser) {
    		userToSave.$save(null, function(value) {
    			console.log('saved user: ' + value.name)
    			$scope.users[index] = value;
    		}, function () {
    			console.log('saved user error');
    			$scope.error = true;
    		});
    		user.newUser = false;
    	} else {
    		userToSave.id = getId(user);
    		User.update({ id:userToSave.id }, userToSave);
    	}
    }
    
    $scope.cancelUser = function cancelUser(user) {
    	user.edit = false;
    	var index = $scope.users.indexOf(user);
    	if (user.newUser) {
    		$scope.users.splice(index, 1);
    	} else {    
//    		reload
    		User.get({ id: getId(user) }, function (value) {
    			$scope.users[index] = value;
    		});
    	}
    	
    }
	
	$scope.users = [];
	$scope.error = false;
}]);