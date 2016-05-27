// Javascript function to do Ajax call for zipcodes

function getZipcode(zipcode) {
	// DEBUG
	//console.log("zipcode: " + zipcode);

    $.post("php/zipcode.php",
    {
        zip: zipcode
    },

    function(data){
    	// DEBUG
        //console.log(data);
        var jData = jQuery.parseJSON(data);
        // DEBUG
        //console.log("JSON state: " + jData["state"]);

        document.getElementById('state').value = jData["state"];
        document.getElementById('city').value = jData["city"];
    });
}