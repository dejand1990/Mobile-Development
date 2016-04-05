//This is to hide and show the instructions
function hideInstructions(){
	$('#instructionsText').toggle();
}

//Set the values if none are set
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

//Gets the workouts from the localstorage, else creates a empty array
function getWorkouts(){
	if (localStorage.getItem("workouts")){
		workouts = JSON.parse(localStorage.getItem("workouts"));
	}else{
		workouts = new Array();
	}
	return workouts;
}

//Saves a workout to the localstorage
function saveWorkouts(workouts){
	localStorage.setItem("workouts", JSON.stringify(workouts));
}

//Sets workouts in the listview
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

//Calls the function before the pageview
$(document).on('pagebeforeshow', '#workout', function(event) {
	setIfEmpty();
});

//This is to reset the color and "uncheck" all the selected values
function unCheckAll(){
	$('.workouts').css({'background-color':'#FFFFFF','color':'#000000'});
}

//Change color on click
$(document).on('click', '.workouts', function() {
	$(this).css({'background-color':'#009933','color':'#FFFFFF'});	
});

//Same for taphold, just in case
$(document).on('taphold', '.workouts', function() {
	$(this).css({'background-color':'#009933','color':'#FFFFFF'});	
});

//Gets the workout to be added from the inputfields and passes it to saveworkouts
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

//Clears the workouts, which will make the default be set at next pageview
function resetToDefault(){
	localStorage.clear();
	alert("Reset to default workouts");
}

//Sets the workout to nothing, so that the default dont get set.
function removeAll(){
	localStorage.clear();
	localStorage.setItem("workouts", "");
	alert("Deleted all workouts");
}