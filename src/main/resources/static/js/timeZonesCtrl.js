app.controller('timeZones', ['$rootScope', '$scope', '$resource', 'TimeZone', 'authService', function($rootScope, $scope, $resource, TimeZone, authService) {
	console.log('zones ctrl')
	
	$scope.searchZones = function searchZones() {
		
		if ($rootScope.currentUser) {
			console.log('current user: ' + JSON.stringify($rootScope.currentUser));
			
			if ($scope.fromDate && $scope.toDate) {			
				var UserZones = $resource(getDateUrl(), 
					{
						userId: $rootScope.currentUser.id, 
						from: $scope.fromDate.toISOString().slice(0, 10),
						to: $scope.toDate.toISOString().slice(0, 10)
					}
				);
			} else {			
				var UserZones = $resource(getAllUrl(), {userId:$rootScope.currentUser.id});
			}
			
			var allZones = UserZones.get(null, function () {
				$scope.zones = allZones._embedded.timeZones;
			});
		}
	}	
	
	function getDateUrl() {
		return isAdmin() ? '/rest/timeZones/search/date?from=:from&to=:to': 
			'/rest/timeZones/search/userAndDate?userId=:userId&from=:from&to=:to';
	}
	
	function getAllUrl() {
		return isAdmin() ? '/rest/timeZones/': 
			'/rest/timeZones/search/userId?userId=:userId';
	}
	
	function isAdmin() {
		return authService.getRole() == 'ROLE_ADMIN';
	}
	
	$scope.searchZones();
	
    $scope.addZone = function addZone() {
        $scope.zones.push({
        	'zoneDate' : '',
            'edit' : true,
            'newZone' : true
        });
    };

    $scope.removeZone = function removeZone(zone) {
        var index = $scope.zones.indexOf(zone);
        if (index !== -1 && !zone.newZone) {
        	TimeZone.delete({ id:getId(zone)});
            $scope.zones.splice(index, 1);
        }
    }
    
    $scope.editZone = function editZone(zone) {
    	zone.edit = true;
    }
    
    $scope.saveZone = function saveZone(zone) {
    	var index = $scope.zones.indexOf(zone);
    	
    	zone.edit = false;
    	var zoneToSave = {};
    	zoneToSave.zoneDate = zone.zoneDate;
    	zoneToSave.user = 'rest/users/' + $rootScope.currentUser.id;
    	if (zone.newZone) {
    		TimeZone.save(null, zoneToSave, function(value) {
    			console.log('saved zone: ' + value)
    			$scope.zones[index] = value;
    		});
    		zone.newZone = false;
    	} else {
    		
    		zoneToSave.id = getId(zone);
    		if (isAdmin()) {
//    			get real user id
    			$resource('/rest/timeZones/:zoneId/user', 
					{zoneId:zoneToSave.id})
					.get({ id: getId(zone) }, 
					function (value) {
						zoneToSave.user = 'rest/users/' + getId(value);
						TimeZone.update({ id:zoneToSave.id }, zoneToSave, function() {
		    				console.log('updated zone sucess');
		    			}, function () {
		    				console.log('updated zone error');
		    			});
					}
				);
    		} else {    			
    			TimeZone.update({ id:zoneToSave.id }, zoneToSave, function() {
    				console.log('updated zone sucess');
    			}, function () {
    				console.log('updated zone error');
    			});
    		}
    		
    	}
    }
    
    $scope.cancelZone = function cancelZone(zone) {
    	zone.edit = false;
    	var index = $scope.zones.indexOf(zone);
    	if (zone.newZone) {
    		$scope.zones.splice(index, 1);
    	} else {    
//    		reload
    		TimeZone.get({ id: getId(zone) }, function (value) {
    			$scope.zones[index] = value;
    		});
    	}
    	
    }
	
	$scope.zones = [];
}]);