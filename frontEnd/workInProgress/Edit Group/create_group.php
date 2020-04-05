<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Pop-up</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="style.css">


</head>
<body>
	<ol class="breadcrumb">
		<li class="breadcrumb-item active">Rendezvous🌀</li>
	</ol>
	<div class="container">
		<div class="row">
			<h1 class="col-12 mt-4 mb-4">Create a Group</h1>
		</div> <!-- .row -->
	</div> <!-- .container -->
	<div class="container">

<!-- MAILANI'S CODE FOR POP UP -->
		<h2>Popup Form</h2>
		    
		    <div class="open-btn">
		      <button class="open-button" onclick="openForm()">
		      <strong>+/- Edit Group</strong>
		      </button>
		    </div>
		    <div id="loginPopup">
		      <div class="form-popup" id="popupForm">
		        <form action="/action_page.php" class="form-container">
		          <h2>Edit Group</h2>

		         
					<label for="genre-id" class="col-sm-3 col-form-label text-sm-right">Group:</label>
					<div class="col-sm-9">
						<select name="genre" id="genre-id" class="form-control">
							<option value="" selected>-- All --</option>

							

						</select>
					</div>
			

		          <input type="text" id="email" placeholder="CSCI 201 Final Project" name="email" required>
		          <label for="psw">
		          <strong>Password</strong>
		          </label>
		          <input type="password" id="psw" placeholder="Your Password" name="psw" required>
		          <button type="submit" class="btn">Log in</button>
		          <button type="button" class="btn cancel" onclick="closeForm()">Close</button>
		        </form>
		      </div>
		    </div>
<!-- MAILANI'S CODE FOR POP UP -->




		
	</div> <!-- .container -->



	<script src="popup.js"></script>
</body>
</html>
