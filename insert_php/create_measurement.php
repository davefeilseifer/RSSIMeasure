<?php

    // include db connect class
    require_once __DIR__ . '/db_connect.php'; 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();


// check for required fields
if (isset($_GET['rssi']) && isset($_GET['time']) ) {
 
    $rssi = $_GET['rssi'];
    $time = $_GET['time'];
 

 
    // connecting to db
    $db = new DB_CONNECT();
 	
	echo "INSERT INTO measurements(rssi, time) VALUES('$rssi', '$time')";
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO measurements(rssi, time) VALUES('$rssi', '$time')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Measurement successfully recorded.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>

