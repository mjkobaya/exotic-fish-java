function validateForm(pid) {
    console.log("JFKSLDJFLKSJD");
   // var pid = document.getElementById("pid").value;
    // DEBUG
    console.log("pid is " + pid);
  
   
    
    var quantity = parseInt(document.getElementById("quantity").value);
    
    // DEBUG
    console.log("quantity is " + quantity);
    if (quantity > 10) {
        alert("We're sorry, you cannot order more than 10 at this time.");
        return false;
    }
    else if (quantity <= 0) {
        alert("Quantity must be greater than 0.");
        return false;
    }
    
    var fname = document.getElementById("fullname").value;
    var fnameMatches = fname.match(/\d+/g);
    if (fnameMatches != null) {
        // DEBUG
        console.log("fname " + fname + " contains a number");
        alert("First name shouldn't have numeric digits.");
        return false;
    }
 if (fname.length <1) {
        alert("Enter Full Name");
        return false;
        //no letters
    }
    
    var phoneNum = parseInt(document.getElementById("phone").value);
    
    if (phoneNum.length > 10) {
        alert("Phone number should be 10 digits.");
        return false;
        //no letters
    }
       if (phoneNum.length < 7) {
        alert("Phone number should be at least 7 digits.");
        return false;
        //no letters
    }
    var creditCard = document.getElementById("creditcard").value;
    
    if(isNaN(creditCard)){
        alert("Invalid Credit Card Number");
        return false;
    }
    if (creditCard.length != 16) {
        alert("Credit card number should be 16 digits");
        return false;
        //no letters
    }

    creditCard = creditCard.substring(12);
    var address = parseInt(document.getElementById("address").value);
    var city = parseInt(document.getElementById("city").value);

    var cityMatches = city.match(/\d+/g);
    if (cityMatches != null) {
        // DEBUG
        alert("Invalid City");
        return false;
    }

if (address.length == 0) {
        // DEBUG
        alert("Invalid Address");
        return false;
    }
    if (city.length == 0) {
        // DEBUG
        alert("Invalid City");
        return false;
    }
    var state = document.getElementById("state").value;

   if (state.length == 0) {
        // DEBUG
        alert("Invalid State");
        return false;
    }
    var stateMatches = state.match(/\d+/g);
    if (stateMatches != null) {
        // DEBUG
        alert("Invalid State");
        return false;
    }
    if (fnameMatches != null) {
        // DEBUG
        console.log("fname " + fname + " contains a number");
        alert("First name shouldn't have numeric digits.");
        return false;
    }
    var zip = parseInt(document.getElementById("zip").value);
    if(isNaN(zip) || zip.length!=5){
        alert("Invalid Zip Code");
        return false;
    }
    var shipping = parseFloat(document.getElementById("shipping").value);
    if(shipping==0.00){
        shipping = "Ground"
    }
    if(shipping==9.99){
        shipping = "2-Day"
    }
    if(shipping==19.99){
        shipping = "Overnight"
    }
    var total = document.getElementById("total").value;
    if(total == null){
        var total = document.getElementById("price").value;
    }

    document.getElementById('id01').style.display='none';
    var email = "mjkobaya@uci.edu";
    var subject = "Exotic Fish Order!";
    var body = "Product ID: " + pid + 
    "%0AQuantity: " + quantity + 
    "%0AFull Name: " + fname + 
    "%0AAddress: " + address + 
    "%0ACity: "+ city + 
    "%0AState: "+ state + 
    "%0AZip: "+ zip + 
    "%0APhone Number: " + phoneNum +
    "%0ACredit Card Number: XXXX-XXXX-XXXX-" + creditCard + 
    "%0AShipping: " + shipping +
    "%0ATotal Cost: " + total 
    ;
    
    // This doesn't work for some reason
    //window.location.href = "mailto:"+email+'?subject='+subject+'&body='+pid;
    // This one works
    //window.open("mailto:"+email+'?subject='+subject+'&body='+body);

}

