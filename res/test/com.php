<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<style>
		#messages{
			font-family: Consolas;
		}
	</style>
</head>
<body>

<form action="com_post.php" method="post">
	<input type="text" name="commande" id="commande">
	<input type="submit" name="submit" value="Entrer" id="envoi" />
</form>

<div id="messages">

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script>
	$('#envoi').click(function(e){
		e.preventDefault();

		$.ajax({
			url : "com_post.php",
			type : "POST",
			data : "commande=" + $('#commande').val(),
			success : function(html){
				$('#messages').prepend(html);
			}
		});
	});
</script>

</body>
</html>
