function deleteCompte(rib) {
	swal({
		title: "Êtes-vous sûr ?",
		text: "Une fois supprimé, vous ne pourrez pas le récupérer",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	}).then((willDelete) => {
		if (willDelete) {
			$.ajax({
				url: "/comptes/delete-ajax",
				type: "POST",
				data: { rib: rib },
				success: function() {
					swal("Compte supprimé avec succès !", {
						icon: "success",
					}).then(() => {
						location.reload(); 
					});
				},
				error: function() {
					swal("Erreur lors de la suppression du compte.", {
						icon: "error",
					});
				}
			});
		} else {
			swal("Le compte est en sécurité !");
		}
	});
}
