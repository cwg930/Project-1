<?php

$dbhost = '50.62.209.77:3306';
$dbuser = 'database_admin';
$dbpass = '1hnjNF6m!';
$dbname = 'ucfsurveys';
$link = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error();
}

if(!empty($_POST)){

	$query = "INSERT INTO SURVEY_1_RESULTS(question_id,answer) VALUES (?,?)";
	$stmt = mysqli_stmt_init($link);

	$numQuestions = $_POST['numQuestions'];
	if(mysqli_stmt_prepare($stmt,$query)){
		for($id = 0; $id < $numQuestions; $id++){
			$text = $_POST["answer$id"];
			mysqli_stmt_bind_param($stmt, "is", $id, $text);
			mysqli_stmt_execute();
		}
	}


	echo "worked!";
}

?>