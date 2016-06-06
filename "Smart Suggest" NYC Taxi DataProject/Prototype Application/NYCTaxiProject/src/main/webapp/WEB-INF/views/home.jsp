<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>Home</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
 
    <!-- Bootstrap core CSS -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/css/bootstrap.css" rel="stylesheet" media="screen">
    <!-- BOOTSTRAP STYLES-->
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" />
 
    <style>
#map-canvas {
  width:100%;
    height:100%;
}
      html,body{
    height:100%;
}

 #over_map { position: absolute; top: 30%; right: 10px; z-index: 99; float: right; }
 
 .thumbnail{background-color:black;}
 
a:visited {
    color: white;
}

a:active {
    color: white;
}

a {
    color: white;
}
 
    </style>
	
	
	
	
	<!--  <script src="resources/js/country.js"></script> -->
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script>
	
    
    
    </script>
    
</head>
<body>
<!--  input type="button" name="submit" value="Submit" id="plotDriverLocation"/> -->

  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand " style="color: #FFFFFF" href="#">$mart $uggest</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
       <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
         <ul class="nav navbar-nav">
            <!--<li class="active"><a href="#">Link</a></li>-->
            <li><a style="color: #FFFFFF" href="#">Current Location :</a></li>

          </ul>
            <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
              <input type="text" class="form-control" placeholder="Latitude" id="driverLat">
              <input type="text" class="form-control" placeholder="Longtitude" id="driverLng">
            </div>
            <button type="button" class="btn btn-primary" id="plotDriverLocation">Map it!</button>
            <div class="form-group">
             <button type="button" class="btn btn-danger" id="showZone">Show Zones</button>
            </div>
          </form>
            
          

        </div><!-- /.navbar-collapse -->
     </div><!-- /.container-fluid -->
   </nav>

    <div id="map-canvas"></div>

    <div id="over_map">
      <div class="thumbnail">
       <div class="caption">
        <h3 style="color: #FFFFFF; align=middle;">Requests</h3>
        
  <div class="form-group">
    <input type="email" class="form-control" id="pickupLat" placeholder="Start Latitude">
    <input type="email" class="form-control" id="pickupLng" placeholder="Start Longtitude">
  </div>
  <div class="form-group">
        <input type="email" class="form-control" id="dropLat" placeholder="Dest Latitude">
    <input type="email" class="form-control" id="dropLng" placeholder="Dest Longtitude">
  </div>

  <button type="button" class="btn btn-primary" id="getScore">Send Request</button>
      </div>
    </div>
   </div>



<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script>  
    
    //Global variables used to plot the markers on the map
    var var_map;
    var curr_marker;
    var pickups = [];
    var dropoffs = [];
    var taxi_image = "resources/images/taxi.png";
    var request_image = "resources/images/request.png";
    var final_image = "resources/images/recomend.png";
    var flag_image = "resources/images/flag.png";
   	var counter = 0;

      
    function init_map() {
    var var_location = new google.maps.LatLng(40.7655101,-73.89188969999998);
 
        var var_mapoptions = {
          center: var_location,
          zoom: 12
        };
 
    var var_marker = new google.maps.Marker({
      position: var_location,
      map: var_map,
      title:"NY City"});
 
       var_map = new google.maps.Map(document.getElementById("map-canvas"),
            var_mapoptions);

    
		
		
    //var_marker.setMap(var_map); 
    }
    
    google.maps.event.addDomListener(window, 'load', init_map);
        
 
  
    	
        $(document).ready(function(){
        	
        	//Plot driver location
            $("#plotDriverLocation").click(function(){
                  var currLat = $("#driverLat").val();
                  var currLong = $("#driverLng").val();
                  if(curr_marker != null){
                    curr_marker.setMap(null);
                  }

                  curr_marker = new google.maps.Marker({
                      position: new google.maps.LatLng(currLat, currLong),
                      map: var_map,
                      animation: google.maps.Animation.DROP,
                      title: 'Current Location',
                      icon: taxi_image
                    });  
  					curr_marker.setMap(var_map);  
                    
                  var_map.setCenter(new google.maps.LatLng(currLat, currLong));
                });
     
               //Plot request location
              /*
               $("#getScore").click(function(){
       
                  var pickupLat = $("#pickupLat").val();
                  var pickupLong = $("#pickupLng").val();
                  pickups.push(new google.maps.Marker({
                                    position: new google.maps.LatLng(pickupLat, pickupLong),
                                    map: var_map,
                                    animation: google.maps.Animation.DROP,
                                    icon:request_image
                  }));

                 
                });*/

				
				
        		
          });
        
        
        
        var scores = [];
        var max = -1;
        var pointer;


        //define zones 
       	var flag=false;
       	
       	var cor1b= [
                    {lat: 40.850, lng: -73.950},
                    {lat: 40.800, lng: -73.950},
                    {lat: 40.800, lng: -74.000},
                    {lat: 40.850, lng: -74.000}
					];
       	var poly_1b;

       	var cor1c= [
                    {lat: 40.850, lng: -73.900},
                    {lat: 40.800, lng: -73.900},
                    {lat: 40.800, lng: -73.950},
                    {lat: 40.850, lng: -73.950}
					];
       	var poly_1c;

    	var cor1d= [
                    {lat: 40.900, lng: -73.900},
                    {lat: 40.850, lng: -73.900},
                    {lat: 40.850, lng: -73.950},
                    {lat: 40.900, lng: -73.950}
					];
       	var poly_1d;
	
       	var cor2a= [
                    {lat: 40.900, lng: -73.850},
                    {lat: 40.850, lng: -73.850},
                    {lat: 40.850, lng: -73.900},
                    {lat: 40.900, lng: -73.900}
					];
       	var poly_2a;

       	var cor2b= [
                    {lat: 40.850, lng: -73.850},
                    {lat: 40.800, lng: -73.850},
                    {lat: 40.800, lng: -73.900},
                    {lat: 40.850, lng: -73.900}
					];
       	var poly_2b;

       	var cor2c= [
                    {lat: 40.850, lng: -73.800},
                    {lat: 40.800, lng: -73.800},
                    {lat: 40.800, lng: -73.850},
                    {lat: 40.850, lng: -73.850}
					];
       	var poly_2c;

       	var cor2d= [
                    {lat: 40.900, lng: -73.800},
                    {lat: 40.850, lng: -73.800},
                    {lat: 40.850, lng: -73.850},
                    {lat: 40.900, lng: -73.850}
					];
       	var poly_2d;

       	var cor3b= [
                    {lat: 40.750, lng: -74.000},
                    {lat: 40.700, lng: -74.000},
                    {lat: 40.700, lng: -74.050},
                    {lat: 40.750, lng: -74.050}
					];
       	var poly_3b;

       	var cor4a= [
                    {lat: 40.800, lng: -73.950},
                    {lat: 40.750, lng: -73.950},
                    {lat: 40.750, lng: -74.000},
                    {lat: 40.800, lng: -74.000}
					];
       	var poly_4a;

    	var cor4b= [
                    {lat: 40.750, lng: -73.950},
                    {lat: 40.700, lng: -73.950},
                    {lat: 40.700, lng: -74.000},
                    {lat: 40.750, lng: -74.000}
					];
       	var poly_4b;

       	var cor4c= [
                    {lat: 40.750, lng: -73.900},
                    {lat: 40.700, lng: -73.900},
                    {lat: 40.700, lng: -73.950},
                    {lat: 40.750, lng: -73.950}
					];
       	var poly_4c;

       	var cor4d= [
                    {lat: 40.800, lng: -73.900},
                    {lat: 40.750, lng: -73.900},
                    {lat: 40.750, lng: -73.950},
                    {lat: 40.800, lng: -73.950}
					];
       	var poly_4d;

       	var cor5a= [
                    {lat: 40.800, lng: -73.850},
                    {lat: 40.750, lng: -73.850},
                    {lat: 40.750, lng: -73.900},
                    {lat: 40.800, lng: -73.900}
					];
       	var poly_5a;

       	var cor5b= [
                    {lat: 40.750, lng: -73.850},
                    {lat: 40.700, lng: -73.850},
                    {lat: 40.700, lng: -73.900},
                    {lat: 40.750, lng: -73.900}
					];
       	var poly_5b;

       	var cor5c= [
                    {lat: 40.750, lng: -73.800},
                    {lat: 40.700, lng: -73.800},
                    {lat: 40.700, lng: -73.850},
                    {lat: 40.750, lng: -73.850}
					];
       	var poly_5c;

       	var cor5d= [
                    {lat: 40.800, lng: -73.800},
                    {lat: 40.750, lng: -73.800},
                    {lat: 40.750, lng: -73.850},
                    {lat: 40.800, lng: -73.850}
					];
       	var poly_5d;
	
       	var cor6a= [
                    {lat: 40.800, lng: -73.750},
                    {lat: 40.750, lng: -73.750},
                    {lat: 40.750, lng: -73.800},
                    {lat: 40.800, lng: -73.800}
					];
       	var poly_6a;

       	var cor6b= [
                    {lat: 40.750, lng: -73.750},
                    {lat: 40.700, lng: -73.750},
                    {lat: 40.700, lng: -73.800},
                    {lat: 40.750, lng: -73.800}
					];
       	var poly_6b;

       	var cor6c= [
                    {lat: 40.750, lng: -73.700},
                    {lat: 40.700, lng: -73.700},
                    {lat: 40.700, lng: -73.750},
                    {lat: 40.750, lng: -73.750}
					];
       	var poly_6c;
	
       	var cor6d= [
                    {lat: 40.800, lng: -73.700},
                    {lat: 40.750, lng: -73.700},
                    {lat: 40.750, lng: -73.750},
                    {lat: 40.800, lng: -73.750}
					];
       	var poly_6d;

       	var cor7c= [
                    {lat: 40.600, lng: -73.900},
                    {lat: 40.650, lng: -73.900},
                    {lat: 40.650, lng: -73.950},
                    {lat: 40.600, lng: -73.950}
					];
       	var poly_7c;

       	var cor7b= [
                    {lat: 40.650, lng: -73.950},
                    {lat: 40.650, lng: -74.000},
                    {lat: 40.600, lng: -74.000},
                    {lat: 40.600, lng: -73.950}
					];
       	var poly_7b;

       	var cor7a= [
                    {lat: 40.700, lng: -73.950},
                    {lat: 40.650, lng: -73.950},
                    {lat: 40.650, lng: -74.000},
                    {lat: 40.700, lng: -74.000}
					];
       	var poly_7a;

    	var cor7d= [
                    {lat: 40.700, lng: -73.900},
                    {lat: 40.650, lng: -73.900},
                    {lat: 40.650, lng: -73.950},
                    {lat: 40.700, lng: -73.950}
					];
       	var poly_7d;

       	var cor8a= [
                    {lat: 40.700, lng: -73.850},
                    {lat: 40.650, lng: -73.850},
                    {lat: 40.650, lng: -73.900},
                    {lat: 40.700, lng: -73.900}
					];
       	var poly_8a;

       	var cor8b= [
                    {lat: 40.650, lng: -73.850},
                    {lat: 40.600, lng: -73.850},
                    {lat: 40.600, lng: -73.900},
                    {lat: 40.650, lng: -73.900}
					];
       	var poly_8b;

       	var cor8d= [
                    {lat: 40.700, lng: -73.800},
                    {lat: 40.650, lng: -73.800},
                    {lat: 40.650, lng: -73.850},
                    {lat: 40.700, lng: -73.850}
					];
       	var poly_8d;

       	var cor9a= [
                    {lat: 40.700, lng: -73.750},
                    {lat: 40.650, lng: -73.750},
                    {lat: 40.650, lng: -73.800},
                    {lat: 40.700, lng: -73.800}
					];
       	var poly_9a;

    	var cor9b= [
                    {lat: 40.650, lng: -73.750},
                    {lat: 40.600, lng: -73.750},
                    {lat: 40.600, lng: -73.800},
                    {lat: 40.650, lng: -73.800}
					];
       	var poly_9b;

       	var cor9c= [
                    {lat: 40.650, lng: -73.700},
                    {lat: 40.600, lng: -73.700},
                    {lat: 40.600, lng: -73.750},
                    {lat: 40.650, lng: -73.750}
					];
       	var poly_9c;

       	var cor9d= [
                    {lat: 40.700, lng: -73.700},
                    {lat: 40.650, lng: -73.700},
                    {lat: 40.650, lng: -73.750},
                    {lat: 40.700, lng: -73.750}
					];
       	var poly_9d;

       	var cor10a= [
                    {lat: 40.600, lng: -73.950},
                    {lat: 40.550, lng: -73.950},
                    {lat: 40.550, lng: -74.000},
                    {lat: 40.600, lng: -74.000}
					];
       	var poly_10a;

       	var cor10d= [
                     {lat: 40.600, lng: -73.900},
                     {lat: 40.550, lng: -73.900},
                     {lat: 40.550, lng: -73.950},
                     {lat: 40.600, lng: -73.950}
 					];
        var poly_10d;
       	
        var cor11a= [
                     {lat: 40.650, lng: -74.050},
                     {lat: 40.600, lng: -74.050},
                     {lat: 40.600, lng: -74.100},
                     {lat: 40.650, lng: -74.100}
 					];
        var poly_11a;

        var cor11d= [
                     {lat: 40.600, lng: -74.050},
                     {lat: 40.600, lng: -74.000},
                     {lat: 40.650, lng: -74.000},
                     {lat: 40.650, lng: -74.050}
 					];
        var poly_11d;

        var cor12a= [
                     {lat: 40.650, lng: -74.200},
                     {lat: 40.650, lng: -74.150},
                     {lat: 40.600, lng: -74.150},
                     {lat: 40.600, lng: -74.200}
 					];
        var poly_12a;

        var cor12b= [
                     {lat: 40.600, lng: -74.200},
                     {lat: 40.600, lng: -74.150},
                     {lat: 40.550, lng: -74.150},
                     {lat: 40.550, lng: -74.200}
 					];
        var poly_12b;

        var cor12c= [
                     {lat: 40.600, lng: -74.150},
                     {lat: 40.550, lng: -74.150},
                     {lat: 40.550, lng: -74.100},
                     {lat: 40.600, lng: -74.100}
 					];
        var poly_12c;

        var cor12d= [
                     {lat: 40.650, lng: -74.100},
                     {lat: 40.600, lng: -74.100},
                     {lat: 40.600, lng: -74.150},
                     {lat: 40.650, lng: -74.150}
 					];
        var poly_12d;

        var cor13a= [
                     {lat: 40.550, lng: -74.250},
                     {lat: 40.550, lng: -74.200},
                     {lat: 40.500, lng: -74.200},
                     {lat: 40.500, lng: -74.250}
 					];
        var poly_13a;
        var mark_1b;
        var mark_1c;
        var mark_1d;
        var mark_2a;
        var mark_2b;
        var mark_2c;
        var mark_2d;
        var mark_3b;
        var mark_4a;
        var mark_4b;
        var mark_4c;
        var mark_4d;
        var mark_5a;
        var mark_5b;
        var mark_5c;
        var mark_5d;
        var mark_6a;
        var mark_6b;
        var mark_6c;
        var mark_6d;
        var mark_7a;
        var mark_7b;
        var mark_7c;
        var mark_7d;
        var mark_8a;
        var mark_8b;
        var mark_8d;
        var mark_9a;
        var mark_9b;
        var mark_9c;
        var mark_9d;
        var mark_10a;
        var mark_10d;
        var mark_11a;
        var mark_11d;
        var mark_12a;
        var mark_12b;
        var mark_12c;
        var mark_12d;
        var mark_13a;
        
        
        


        
        
           	
        
        $(document).ready(function() {  

        	$("#showZone").click(function(){

				
				if(flag){
					poly_1b.setMap(null);
					poly_1c.setMap(null);
					poly_1d.setMap(null);
					poly_2a.setMap(null);
					poly_2b.setMap(null);
					poly_2c.setMap(null);
					poly_2d.setMap(null);
					poly_3b.setMap(null);
					poly_4a.setMap(null);
					poly_4b.setMap(null);
					poly_4c.setMap(null);
					poly_4d.setMap(null);
					poly_5a.setMap(null);
					poly_5b.setMap(null);
					poly_5c.setMap(null);
					poly_5d.setMap(null);
					poly_6a.setMap(null);
					poly_6b.setMap(null);
					poly_6c.setMap(null);
					poly_6d.setMap(null);
					poly_7a.setMap(null);
					poly_7b.setMap(null);
					poly_7c.setMap(null);
					poly_7d.setMap(null);
					poly_8a.setMap(null);
					poly_8b.setMap(null);
					poly_8d.setMap(null);
					poly_9a.setMap(null);
					poly_9b.setMap(null);
					poly_9c.setMap(null);
					poly_9d.setMap(null);
					poly_10a.setMap(null);
					poly_10d.setMap(null);
					poly_11a.setMap(null);
					poly_11d.setMap(null);
					poly_12a.setMap(null);
					poly_12b.setMap(null);
					poly_12c.setMap(null);
					poly_12d.setMap(null);
					poly_13a.setMap(null);

					mark_1b.setMap(null);
			         mark_1c.setMap(null);
			         mark_1d.setMap(null);
			         mark_2a.setMap(null);
			         mark_2b.setMap(null);
			         mark_2c.setMap(null);
			         mark_2d.setMap(null);
			         mark_3b.setMap(null);
			         mark_4a.setMap(null);
			         mark_4b.setMap(null);
			         mark_4c.setMap(null);
			         mark_4d.setMap(null);
			         mark_5a.setMap(null);
			         mark_5b.setMap(null);
			         mark_5c.setMap(null);
			         mark_5d.setMap(null);
			         mark_6a.setMap(null);
			         mark_6b.setMap(null);
			         mark_6c.setMap(null);
			         mark_6d.setMap(null);
			         mark_7a.setMap(null);
			         mark_7b.setMap(null);
			         mark_7c.setMap(null);
			         mark_7d.setMap(null);
			         mark_8a.setMap(null);
			         mark_8b.setMap(null);
			         mark_8d.setMap(null);
			         mark_9a.setMap(null);
			         mark_9b.setMap(null);
			         mark_9c.setMap(null);
			         mark_9d.setMap(null);
			         mark_10a.setMap(null);
			         mark_10d.setMap(null);
			         mark_11a.setMap(null);
			         mark_11d.setMap(null);
			         mark_12a.setMap(null);
			         mark_12b.setMap(null);
			         mark_12c.setMap(null);
			         mark_12d.setMap(null);
			        mark_13a.setMap(null);

					

					
					flag =false;
					return;
					}
	             
				flag =true;		

        	       
        	       // Construct the polygon.
        	       poly_1b = new google.maps.Polygon({
        	         paths: cor1b,
        	         strokeColor: '#FF0000',
        	         strokeOpacity: 0.8,
        	         strokeWeight: 2,
        	         fillColor: '#FF0000',
        	         fillOpacity: 0.35
        	       });
        	      poly_1b.setMap(var_map);
					
        	      poly_1c = new google.maps.Polygon({
         	         paths: cor1c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_1c.setMap(var_map);

         	     poly_1d = new google.maps.Polygon({
         	         paths: cor1d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_1d.setMap(var_map);

         	     poly_2a = new google.maps.Polygon({
         	         paths: cor2a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_2a.setMap(var_map);


         	     poly_2b = new google.maps.Polygon({
         	         paths: cor2b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_2b.setMap(var_map); 

         	     poly_2c = new google.maps.Polygon({
         	         paths: cor2c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_2c.setMap(var_map);

         	     poly_2d = new google.maps.Polygon({
         	         paths: cor2d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_2d.setMap(var_map);

         	     poly_3b = new google.maps.Polygon({
         	         paths: cor3b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_3b.setMap(var_map);

         	     poly_4a = new google.maps.Polygon({
         	         paths: cor4a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_4a.setMap(var_map);


         	     poly_4b = new google.maps.Polygon({
         	         paths: cor4b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_4b.setMap(var_map);


         	     poly_4c = new google.maps.Polygon({
         	         paths: cor4c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_4c.setMap(var_map);

         	     poly_4d = new google.maps.Polygon({
         	         paths: cor4d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_4d.setMap(var_map);


         	     poly_5a = new google.maps.Polygon({
         	         paths: cor5a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_5a.setMap(var_map);

         	     poly_5b = new google.maps.Polygon({
         	         paths: cor5b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_5b.setMap(var_map); 

         	     poly_5c = new google.maps.Polygon({
         	         paths: cor5c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_5c.setMap(var_map);


         	     poly_5d = new google.maps.Polygon({
         	         paths: cor5d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_5d.setMap(var_map);

         	     poly_6a = new google.maps.Polygon({
         	         paths: cor6a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_6a.setMap(var_map);

         	     poly_6b = new google.maps.Polygon({
         	         paths: cor6b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_6b.setMap(var_map); 

         	     poly_6c = new google.maps.Polygon({
         	         paths: cor6c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_6c.setMap(var_map); 

         	     poly_6d = new google.maps.Polygon({
         	         paths: cor6d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_6d.setMap(var_map);

         	     poly_7c = new google.maps.Polygon({
         	         paths: cor7c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_7c.setMap(var_map);

         	     poly_7b = new google.maps.Polygon({
         	         paths: cor7b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_7b.setMap(var_map); 

         	     poly_7a = new google.maps.Polygon({
         	         paths: cor7a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_7a.setMap(var_map);   

         	     poly_7d = new google.maps.Polygon({
         	         paths: cor7d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_7d.setMap(var_map);  

         	     poly_8a = new google.maps.Polygon({
         	         paths: cor8a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_8a.setMap(var_map); 

         	     poly_8b = new google.maps.Polygon({
         	         paths: cor8b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_8b.setMap(var_map); 

         	     poly_8d = new google.maps.Polygon({
         	         paths: cor8d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_8d.setMap(var_map);

         	     poly_9a = new google.maps.Polygon({
         	         paths: cor9a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_9a.setMap(var_map); 

         	     poly_9b = new google.maps.Polygon({
         	         paths: cor9b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_9b.setMap(var_map);  

         	     poly_9c = new google.maps.Polygon({
         	         paths: cor9c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_9c.setMap(var_map);

         	     poly_9d = new google.maps.Polygon({
         	         paths: cor9d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_9d.setMap(var_map);

         	     poly_10a = new google.maps.Polygon({
         	         paths: cor10a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_10a.setMap(var_map);         

         	     poly_10d = new google.maps.Polygon({
         	         paths: cor10d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_10d.setMap(var_map); 

         	     poly_11a = new google.maps.Polygon({
         	         paths: cor11a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_11a.setMap(var_map);  

         	     poly_11d = new google.maps.Polygon({
         	         paths: cor11d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_11d.setMap(var_map);
         	               
         	     poly_12a = new google.maps.Polygon({
         	         paths: cor12a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_12a.setMap(var_map);

         	     poly_12b = new google.maps.Polygon({
         	         paths: cor12b,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_12b.setMap(var_map);


         	     poly_12c = new google.maps.Polygon({
         	         paths: cor12c,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_12c.setMap(var_map); 


         	     poly_12d = new google.maps.Polygon({
         	         paths: cor12d,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_12d.setMap(var_map); 

         	     poly_13a = new google.maps.Polygon({
         	         paths: cor13a,
         	         strokeColor: '#FF0000',
         	         strokeOpacity: 0.8,
         	         strokeWeight: 2,
         	         fillColor: '#FF0000',
         	         fillOpacity: 0.35
         	       });
         	      poly_13a.setMap(var_map);       

         	   //place zone markers
              	 mark_1b = new google.maps.Marker({
                      position: new google.maps.LatLng(40.825, -73.975),
                      map: var_map,
                      icon:"resources/images/1b.png"
                    });  
              	mark_1b.setMap(var_map);

              	 mark_1c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.825, -73.925),
                    map: var_map,
                    icon:"resources/images/1c.png"
                  });  
            	mark_1c.setMap(var_map);

            	 mark_1d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.875, -73.925),
                    map: var_map,
                    icon:"resources/images/1d.png"
                  });  
            	mark_1d.setMap(var_map);

            	 mark_2a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.875, -73.875),
                    map: var_map,
                    icon:"resources/images/2a.png"
                  });  
            	mark_2a.setMap(var_map);

            	 mark_2b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.825, -73.875),
                    map: var_map,
                    icon:"resources/images/2b.png"
                  });  
            	mark_2b.setMap(var_map);

            	 mark_2c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.825, -73.825),
                    map: var_map,
                    icon:"resources/images/2c.png"
                  });  
            	mark_2c.setMap(var_map);

            	 mark_2d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.875, -73.825),
                    map: var_map,
                    icon:"resources/images/2d.png"
                  });  
            	mark_2d.setMap(var_map);

            	 mark_3b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.725, -74.025),
                    map: var_map,
                    icon:"resources/images/3b.png"
                  });  
            	mark_3b.setMap(var_map);

            	 mark_4a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.775, -73.975),
                    map: var_map,
                    icon:"resources/images/4a.png"
                  });  
            	mark_4a.setMap(var_map);

            	 mark_4b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.725, -73.975),
                    map: var_map,
                    icon:"resources/images/4b.png"
                  });  
            	mark_4b.setMap(var_map);

            	 mark_4c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.725, -73.925),
                    map: var_map,
                    icon:"resources/images/4c.png"
                  });  
            	mark_4c.setMap(var_map);

            	 mark_4d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.775, -73.925),
                    map: var_map,
                    icon:"resources/images/4d.png"
                  });  
            	mark_4d.setMap(var_map);

            	 mark_5a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.775, -73.875),
                    map: var_map,
                    icon:"resources/images/5a.png"
                  });  
            	mark_5a.setMap(var_map);

            	 mark_5b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.725, -73.875),
                    map: var_map,
                    icon:"resources/images/5b.png"
                  });  
            	mark_5b.setMap(var_map);

            	 mark_5c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.725, -73.825),
                    map: var_map,
                    icon:"resources/images/5c.png"
                  });  
            	mark_5c.setMap(var_map);

            	 mark_5d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.775, -73.825),
                    map: var_map,
                    icon:"resources/images/5d.png"
                  });  
            	mark_5d.setMap(var_map);

            	 mark_6a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.775, -73.775),
                    map: var_map,
                    icon:"resources/images/6a.png"
                  });  
            	mark_6a.setMap(var_map);

            	 mark_6b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.725, -73.775),
                    map: var_map,
                    icon:"resources/images/6b.png"
                  });  
            	mark_6b.setMap(var_map);

            	 mark_6c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.725, -73.725),
                    map: var_map,
                    icon:"resources/images/6c.png"
                  });  
            	mark_6c.setMap(var_map);

            	 mark_6d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.775, -73.725),
                    map: var_map,
                    icon:"resources/images/6d.png"
                  });  
            	mark_6d.setMap(var_map);

            	 mark_7c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -73.925),
                    map: var_map,
                    icon:"resources/images/7c.png"
                  });  
            	mark_7c.setMap(var_map);
            	 mark_7b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -73.975),
                    map: var_map,
                    icon:"resources/images/7b.png"
                  });  
            	mark_7b.setMap(var_map);

            	 mark_7a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.675, -73.975),
                    map: var_map,
                    icon:"resources/images/7a.png"
                  });  
            	mark_7a.setMap(var_map);

            	 mark_7d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.675, -73.925),
                    map: var_map,
                    icon:"resources/images/7d.png"
                  });  
            	mark_7d.setMap(var_map);

            	 mark_8a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.675, -73.875),
                    map: var_map,
                    icon:"resources/images/8a.png"
                  });  
            	mark_8a.setMap(var_map);

            	 mark_8b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -73.875),
                    map: var_map,
                    icon:"resources/images/8b.png"
                  });  
            	mark_8b.setMap(var_map);

            	 mark_8d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.675, -73.825),
                    map: var_map,
                    icon:"resources/images/8d.png"
                  });  
            	mark_8d.setMap(var_map);

            	 mark_9a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.675, -73.775),
                    map: var_map,
                    icon:"resources/images/9a.png"
                  });  
            	mark_9a.setMap(var_map);

            	 mark_9b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -73.775),
                    map: var_map,
                    icon:"resources/images/9b.png"
                  });  
            	mark_9b.setMap(var_map);

            	 mark_9c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -73.725),
                    map: var_map,
                    icon:"resources/images/9c.png"
                  });  
            	mark_9c.setMap(var_map);

            	 mark_9d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.675, -73.725),
                    map: var_map,
                    icon:"resources/images/9d.png"
                  });  
            	mark_9d.setMap(var_map);

            	 mark_10a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.575, -73.975),
                    map: var_map,
                    icon:"resources/images/10a.png"
                  });  
            	mark_10a.setMap(var_map);

            	 mark_10d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.575, -73.925),
                    map: var_map,
                    icon:"resources/images/10d.png"
                  });  
            	mark_10d.setMap(var_map);

            	 mark_11a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -74.075),
                    map: var_map,
                    icon:"resources/images/11a.png"
                  });  
            	mark_11a.setMap(var_map);

            	 mark_11d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -74.025),
                    map: var_map,
                    icon:"resources/images/11d.png"
                  });  
            	mark_11d.setMap(var_map);

            	 mark_12a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -74.175),
                    map: var_map,
                    icon:"resources/images/12a.png"
                  });  
            	mark_12a.setMap(var_map);

            	 mark_12b = new google.maps.Marker({
                    position: new google.maps.LatLng(40.575, -74.175),
                    map: var_map,
                    icon:"resources/images/12b.png"
                  });  
            	mark_12b.setMap(var_map);

            	 mark_12c = new google.maps.Marker({
                    position: new google.maps.LatLng(40.575, -74.125),
                    map: var_map,
                    icon:"resources/images/12c.png"
                  });  
            	mark_12c.setMap(var_map);

            	 mark_12d = new google.maps.Marker({
                    position: new google.maps.LatLng(40.625, -74.125),
                    map: var_map,
                    icon:"resources/images/12d.png"
                  });  
            	mark_12d.setMap(var_map);

            	 mark_13a = new google.maps.Marker({
                    position: new google.maps.LatLng(40.525, -74.225),
                    map: var_map,
                    icon:"resources/images/13a.png"
                  });  
            	mark_13a.setMap(var_map);
            	


            	
            	

	
            	

            		
                  	

            	});

        	

            
        	
        	//Sends the driver location to the controller
    		$("#plotDriverLocation").click(function(){
    			var driverLat=$("#driverLat").val();
    	    	var driverLng=$("#driverLng").val();
    	    	//alert(driverLat);
    			$.ajax({
    				 
    		        url: "recordDriverLocation.htm",
    		        type: "GET",
    		        data : {
    		        	driverLat: driverLat,
    		        	driverLng: driverLng
    		        },
    		        success : function(response){
    		        	//alert(response);
    		        	
    	                 //$("#all-reviews").html(response);				                         
    		        }
    			});
        	});
    		
    		
    		//Sends the request location to controller and fetches score
    		$("#getScore").click(function(){
    			var pickupLat=$("#pickupLat").val();
    	    	var pickupLng=$("#pickupLng").val();
    	    	var dropLat=$("#dropLat").val();
    	    	var dropLng=$("#dropLng").val();
    	    	 var temp_score;
    	    	counter = counter + 1;
    	    	 var infowindow = new google.maps.InfoWindow();
    			$.ajax({
    				 
    		        url: "getScore.htm",
    		        type: "GET",
    		        data : {
    		        	pickupLat: pickupLat,
    		        	pickupLng: pickupLng,
    		        	dropLat: dropLat,
    					dropLng: dropLng
    		        },
    		        success : function(response){
    		        	scores.push(response);
    		        	alert(scores.length);
    		        	temp_score = response;
    		        	//set all the markers to default request icon
    		        	for (var i = 0; i < pickups.length; i++){
    		        		pickups[i].setIcon(request_image);
    		        		
    		        	}
    		        	//calculate max score
    		        	for (var i = 0; i < scores.length; i++) { 
    	    				if(scores[i] > max){
    	    					max = scores[i];
    	    					pointer = i;
    	    					//alert(max);
    	    				}
    	    			    //text += cars[i] + "<br>";
    	    			}

						//place the marker
						var marker = new google.maps.Marker({
                            position: new google.maps.LatLng(pickupLat, pickupLng),
                            map: var_map,
                            animation: google.maps.Animation.DROP,
                            icon:request_image
         					});
						 pickups.push(marker);
						alert("Pickups Length = "+pickups.length);

						 google.maps.event.addListener(marker, 'click', function() {
					            infowindow.setContent("Score: "+temp_score+"\n Request no: "+counter);
					            infowindow.open(var_map, this);
					          });
					                 		

    	    			
    	    			//alert(pointer);
    	    			var marker1 = pickups[pointer];
    	    			//alert(marker);
    	    			//pickups[pointer] = null;
    	    			//(google.maps.Marker)marker);
    	    			//alert(marker.getPosition());
    	    			//alert(pickups[pointer]);
    	    			marker1.setIcon(final_image);

    	    			//Set the destination flag for each request
    	    			        var destinfowindow = new google.maps.InfoWindow();
    	    			        
    	    			        var dropLat = $("#dropLat").val();
    	    			                      var dropLng = $("#dropLng").val();
    	    			                      var destmarker = new google.maps.Marker({
    	    			                              position: new google.maps.LatLng(dropLat, dropLng),
    	    			                              map: var_map,
    	    			                              animation: google.maps.Animation.DROP,
    	    			                              icon:flag_image
    	    			                      });
    	    			                      dropoffs.push(destmarker);
    	    			                      google.maps.event.addListener(destmarker, 'click', function() {
    	    			                     
    	    			                      destinfowindow.setContent("Request No="+counter);
    	    			                      destinfowindow.open(var_map, this);
    	    			          });


    	    			
    		        }
    		        
    			});
    			
    			
    		});

 
		
    		
        });
        
   		
     
      
    </script>
     <!-- BOOTSTRAP SCRIPTS -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>
