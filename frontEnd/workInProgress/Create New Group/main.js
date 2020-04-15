// Event handler where ADD MEMBER button is clicked
$(".btn-outline-success").on("click", function(event) {
	let member = $("#username-id").val();
	event.preventDefault();

	if (member == "") {
		alert("Invalid username");
	} else {
		$("#added-member-list").append("<li><i class=\"fas fa-times\" aria-hidden=\"true\"></i><span class=\"added-member\">" + member + "</span></li>");
		$("#username-id").val("");
	}
});

// Event handler where DELETE icon is clicked
$("#added-member-list").on("click", "i", function(event) {
	event.preventDefault();
	// Fade out and remove the list
	$(this).parent().fadeOut(100, function() {
		$(this).remove();
	});
});