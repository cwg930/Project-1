<?php


$dbhost = '50.62.209.77:3306';
$dbuser = 'database_admin';
$dbpass = '1hnjNF6m!';
$dbname = 'ucfsurveys';
$link = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$response = array();

//results for Question1
$response[0]['question_type'] = 'MC';
$query2 = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=0 ");
$resultTotal = mysqli_fetch_assoc($query2);
$response[0]['answers'][] = $resultTotal['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=0 AND answer=1 "); 
$result1 = mysqli_fetch_assoc($query);
$response[0]['answers'][] = $result1['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=0 AND answer=2 "); 
$result1 = mysqli_fetch_assoc($query);
$response[0]['answers'][] = $result1['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=0 AND answer=3 "); 
$result1 = mysqli_fetch_assoc($query);
$response[0]['answers'][] = $result1['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=0 AND answer=4 "); 
$result1 = mysqli_fetch_assoc($query);
$response[0]['answers'][] = $result1['c'];

//results for Question2
$response[1]['question_type'] = 'MC';
$query2 = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=1 ");
$resultTotal = mysqli_fetch_assoc($query2);
$response[1]['total'] = $resultTotal['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=1 AND answer=1 "); 
$result1 = mysqli_fetch_assoc($query);
$response[1]['answers'][] = $result1['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=1 AND answer=2 "); 
$result1 = mysqli_fetch_assoc($query);
$response[1]['answers'][] = $result1['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=1 AND answer=3 "); 
$result1 = mysqli_fetch_assoc($query);
$response[1]['answers'][] = $result1['c'];

$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=1 AND answer=4 "); 
$result1 = mysqli_fetch_assoc($query);
$response[1]['answers'][] = $result1['c'];


//results for Question 3
$response[2]['question_type'] = 'TX';
$query = mysqli_query($link,"SELECT * FROM SURVEY_3_RESULTS WHERE question_id=2 LIMIT 10");
while($row = mysqli_fetch_assoc($query)){
	$response[2]['answers'][] = $row['answer'];
}


//results for Question4
$response[3]['question_type'] = 'MC';
$query2 = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=3 ");
$resultTotal = mysqli_fetch_assoc($query2);
$response[3]['total'] = $resultTotal['c'];
$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=3 AND answer=1 "); 
$result1 = mysqli_fetch_assoc($query);
$response[3]['answers'][] = $result1['c'];
$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=3 AND answer=2 "); 
$result1 = mysqli_fetch_assoc($query);
$response[3]['answers'][] = $result1['c'];
$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=3 AND answer=3 "); 
$result1 = mysqli_fetch_assoc($query);
$response[3]['answers'][] = $result1['c'];
$query = mysqli_query($link,"SELECT COUNT(*) AS c FROM `SURVEY_3_RESULTS` WHERE question_id=3 AND answer=4 "); 
$result1 = mysqli_fetch_assoc($query);
$response[3]['answers'][] = $result1['c'];
echo json_encode($response);




?>