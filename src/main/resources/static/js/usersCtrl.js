app.controller('users', ['$rootScope', '$scope', '$http', '$resource', 'User', function($rootScope, $scope, $http, $resource, User) {
	console.log('users ctrl');
	
	var allUsers = User.get(null, function () {
		if (allUsers._embedded) {			
			$scope.users = allUsers._embedded.secureUsers;
		}
	});
	
//	$scope.searchUsers = function searchUsers() {
//		
//		if ($rootScope.currentUser) {
//			console.log('current user: ' + JSON.stringify($rootScope.currentUser));
//			
//			if ($scope.byName) {			
//				var UserZones = $resource(getNameUrl(), { name: $scope.byName });
//			} else {			
//				var UserZones = $resource(getAllUrl(), { userId: $rootScope.currentUser.id });
//			}
//			
//			var allZones = UserZones.get(null, function () {
//				$scope.zones = allZones._embedded.timeZones;
//			});
//		}
//	}
//	
//	function getNameUrl() {
//		return '/rest/timeZones/search/name?name=:name';
//	}
//	
//	function getAllUrl() {
//		return isAdmin() ? '/rest/timeZones/': '/rest/timeZones/search/userId?userId=:userId';
//	}
	
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
}]);