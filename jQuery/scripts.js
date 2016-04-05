function hideInstructions(){
	$('#instructionsText').toggle();
}

function setIfEmpty(){
	if(window.localStorage.length < 1){
		var workouts = getWorkouts();
		
		var pushups = "Pushups";
		var situps = "Situps";
		var legs = "Legs";
		// Push the new item into the existing list
		workouts.push({
			workout: pushups,
			amount: "50"
		});
		workouts.push({
			workout: situps,
			amount: "50"
		});
		workouts.push({
			workout: legs,
			amount: "75"
		});
		
		// Store the new list
		saveWorkouts(workouts);
		// Reload the page to show the new objects
		window.location.reload();
		
		setWorkouts();
	}else{
		setWorkouts();
	}

}

function getWorkouts(){
	// See if objects is inside localStorage
	if (localStorage.getItem("workouts")){
		// If yes, then load the objects
		workouts = JSON.parse(localStorage.getItem("workouts"));
	}else{
		// Make a new array of objects
		workouts = new Array();
	}
	return workouts;
}

function saveWorkouts(workouts){
	// Save the list into localStorage
	localStorage.setItem("workouts", JSON.stringify(workouts));
}

function setWorkouts(){
	// Fetch the existing objects
	workouts = getWorkouts();

	// Clear the list
	$('#items').find('li').remove();

	// Add every object to the objects list
	$.each(workouts, function(index, item){
		element = '<li class="workouts">'+item.workout+'<div class="amountWorkout" style="display:inline-bock;float:right;">'+item.amount+'</div></li>';	
		$('#items').append(element);
	});
	
   $('#items').listview();
   $('#items').listview("refresh");
}

$(document).on('pagebeforeshow', '#workout', function(event) {
	setIfEmpty();
	console.log(getWorkouts());
});

function unCheckAll(){
	$('.workouts').css({'background-color':'#FFFFFF','color':'#000000'});
}

$(document).on('click', '.workouts', function() {
	$(this).css({'background-color':'#009933','color':'#FFFFFF'});	
});

$(document).on('taphold', '.workouts', function() {
	$(this).css({'background-color':'#009933','color':'#FFFFFF'});	
});

function add(){
	// Retrieve the entered form data
	var workout = $('#workoutName').val();
	var amount = $('#workoutAmount').val();
	
	// Fetch the existing objects
	var workouts = getWorkouts();
	// Push the new item into the existing list
	workouts.push({
		workout: workout,
		amount: amount
	});
	// Store the new list
	saveWorkouts(workouts);
	// Reload the page to show the new objects
	alert("Workout added!");
	window.location.reload();
}

function resetToDefault(){
	localStorage.clear();
	alert("Reset to default workouts");
}

function removeAll(){
	localStorage.clear();
	localStorage.setItem("workouts", "");
	alert("Deleted all workouts");
}