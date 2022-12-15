function myFunc(){
    var x = document.getElementById("myPassword");
    var y = document.getElementById("myB")
    if(x.style.display==="none"){
        x.style.display="block";
        y.innerHTML="Hide Password";
    }else{
        x.style.display="none";
        y.innerHTML="Show Password";
    }
}