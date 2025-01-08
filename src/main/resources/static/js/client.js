function deleteClient(id) {
	swal({
		title: "Êtes-vous sûr ?",
		text: "Une fois supprimé, vous ne pourrez pas le récupérer",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				$.ajax({
					url: "/clients/delete-ajax",
					type: "POST",
					data: { id: id },
					success: function() {
						$("#" + id).remove();  
					}
				});
			} else {
				swal("Le client est en sécurité !");
			}
		});
}
