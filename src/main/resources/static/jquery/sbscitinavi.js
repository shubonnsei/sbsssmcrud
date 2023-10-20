const pathdeApp = "ssmcrud";
let currentPage = 1;
let totalRecord, totalPages, searchName;

// After the page load, send the ajax request to get page infos.
$(document).ready(function() {
	toSelectedPg(currentPage, searchName);
});

// Transfer to clicked page numbers.
function toSelectedPg(pageNum, searchName) {
	$.ajax({
		url: pathdeApp + '/city',
		data: {
			'pageNum': pageNum,
			'keyword': searchName
		},
		type: 'GET',
		success: function(result) {
			buildCityTable(result);
			buildPageInfos(result);
			buildPageNavi(result);
		}
	})
}

// Analyze and display the data.
function buildCityTable(result) {
	// Emptying the former table.
	$("#cityTableBody").empty();
	let index = result.extend.pageInfo.records;
	$.each(index, (index, item) => {
		let cityName = item.name;
		let nationName = item.nation;
		let districtName = item.district;
		let languageName = item.language;
		let idTd = $("<th scope='row' class='text-center' style='width:70px;vertical-align:bottom;'></th>").append(item.id);
		let nameTd;
		if (cityName.length >= 15) {
			nameTd = $("<td class='text-center' style='width:120px;font-size:10px;vertical-align:bottom;'></td>").append(cityName);
		} else {
			nameTd = $("<td class='text-center' style='width:120px;font-size:15px;vertical-align:bottom;'></td>").append(cityName);
		}
		let continentTd = $("<td class='text-center' style='width:100px;vertical-align:bottom;'></td>").append(item.continent);
		let nationTd;
		if (nationName.length > 12 && nationName.length <= 15) {
			nationTd = $("<td class='text-center' style='width:100px;font-size:12px;vertical-align:bottom;'></td>").append(nationName);
		} else if (nationName.length > 15) {
			nationTd = $("<td class='text-center' style='width:100px;font-size:10px;vertical-align:bottom;'></td>").append(nationName);
		} else {
			nationTd = $("<td class='text-center' style='width:100px;font-size:15px;vertical-align:bottom;'></td>").append(nationName);
		}
		let districtTd;
		if (districtName.length >= 15) {
			districtTd = $("<td class='text-center' style='width:120px;font-size:10px;vertical-align:bottom;'></td>").append(districtName);
		} else {
			districtTd = $("<td class='text-center' style='width:120px;font-size:15px;vertical-align:bottom'></td>").append(districtName);
		}
		let populationTd = $("<td class='text-center' style='width:70px;vertical-align:bottom;'></td>").append(item.population);
		let languageTd;
		if (languageName.length >= 15) {
			languageTd = $("<td class='text-center' style='width:80px;font-size:10px;vertical-align:bottom;'></td>").append(languageName);
		} else {
			languageTd = $("<td class='text-center' style='width:80px;font-size:15px;vertical-align:bottom;'></td>").append(languageName);
		}
		let editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
			.append($("<i class='bi bi-pencil-fill'></i>")).append("編集");
		editBtn.attr("editId", item.id);
		let deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
			.append($("<i class='bi bi-trash'></i>")).append("削除");
		deleteBtn.attr("deleteId", item.id);
		let btnTd = $("<td class='text-center' style='width:120px;vertical-align:bottom;'></td>").append(editBtn).append(" ").append(deleteBtn);
		$("<tr></tr>").append(idTd).append(nameTd).append(continentTd).append(nationTd).append(districtTd).append(populationTd).append(languageTd)
			.append(btnTd).appendTo("#cityTableBody");
	});
}

// Analyze and display the page infos.
function buildPageInfos(result) {
	let pageInfos = $("#pageInfos");
	pageInfos.empty();
	currentPage = result.extend.pageInfo.pageNum;
	totalPages = result.extend.pageInfo.totalPages;
	totalRecord = result.extend.pageInfo.totalRecords;
	pageInfos.append("The " + currentPage + " page in " + totalPages
		+ " pages, " + totalRecord + " records found.");
}

// Analyze and display the navigated pages.
function buildPageNavi(result) {
	$("#pageNavi").empty();
	let ul = $("<ul></ul>").addClass("pagination");
	let firstPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("最初のページへ").attr("href", "#"));
	let previousPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("&laquo;").attr("href", "#"));
	if (!result.extend.pageInfo.hasPreviousPage) {
		firstPageLi.addClass("disabled");
		previousPageLi.addClass("disabled");
	} else {
		firstPageLi.click(function() {
			toSelectedPg(1, searchName);
		});
		previousPageLi.click(function() {
			toSelectedPg(currentPage - 1, searchName);
		});
	}
	let nextPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("&raquo;").attr("href", "#"));
	let lastPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("最後のページへ").attr("href", "#"));
	if (!result.extend.pageInfo.hasNextPage) {
		nextPageLi.addClass("disabled");
		lastPageLi.addClass("disabled");
	} else {
		lastPageLi.addClass("success");
		nextPageLi.click(function() {
			toSelectedPg(currentPage + 1, searchName);
		});
		lastPageLi.click(function() {
			toSelectedPg(totalPages, searchName);
		});
	}
	ul.append(firstPageLi).append(previousPageLi);
	$.each(result.extend.pageInfo.navigatePageNums, (index, item) => {
		let numsLi = $("<li class='page-item'></li>").append(
			$("<a class='page-link'></a>").append(item).attr("href", "#"));
		if (currentPage === item) {
			numsLi.attr("href", "#").addClass("active");
		}
		numsLi.click(function() {
			toSelectedPg(item, searchName);
		});
		ul.append(numsLi);
	});
	ul.append(nextPageLi).append(lastPageLi);
	let navElement = $("<nav></nav>").append(ul);
	navElement.appendTo("#pageNavi");
}

// Add click function to search btn.
$("#searchBtn").on('click', function() {
	searchName = $("#keywordInput").val().trim().toString();
	toSelectedPg(1, searchName);
});

// Add click function to the input modal.
$("#cityAddModalBtn").on('click', function() {
	formReset("#cityAddModal form");
	getContinent("#continentInput");
	getNations($("#nationInput"), 'Africa');
	$("#cityAddModal").modal({
		backdrop: 'static'
	});
});

// Add click function to select of continent.
$("#continentInput").on('change', function() {
	let continentVal = $("#continentInput option:selected").val();
	getNations($("#nationInput"), continentVal);
})

// Get the name of continent.
function getContinent(element) {
	$(element).empty();
	$.ajax({
		url: pathdeApp + '/continents',
		type: 'GET',
		success: function(result) {
			$.each(result.extend.continents, function() {
				let optionElement = $("<option></option>").append(this).attr(
					"value", this);
				optionElement.appendTo(element);
			});
		}
	});
}

// Get the name of country.
function getNations(element, continentVal) {
	$(element).empty();
	$.ajax({
		url: pathdeApp + '/countries',
		data: 'continentVal=' + continentVal,
		type: 'GET',
		success: function(result) {
			$.each(result.extend.nations, function() {
				let optionElement = $("<option></option>").append(this).attr("value", this);
				optionElement.appendTo(element);
			});
		}
	});
}

// Analyze the input name is duplicate or not.
$("#nameInput").change(
	function() {
		let cityName = this.value;
		$.ajax({
			url: pathdeApp + '/checklist',
			data: 'cityName=' + cityName,
			type: 'GET',
			success: function(result) {
				if (result.code === 200) {
					showValidationMsg("#nameInput", "success", "");
					$("#cityInfoSaveBtn").attr("ajax-va", "success");
				} else {
					showValidationMsg("#nameInput", "error", result.extend.validatedMsg);
					$("#cityInfoSaveBtn").attr("ajax-va", "error");
				}
			}
		});
	});

// Click, save and check the input data format.
$("#cityInfoSaveBtn").on('click', function() {
	let inputDistrict = $("#districtInput").val().trim();
	let inputPopulation = $("#populationInput").val().trim();
	let regularDistrict = /^[a-zA-Z-\s]{2,33}$/;
	let regularPopulation = /^\d{4,18}$/;
	if ($(this).attr("ajax-va") === "error") {
		return false;
	} else if (!regularDistrict.test(inputDistrict)
		&& !regularPopulation.test(inputPopulation)) {
		showValidationMsg("#districtInput", "error",
			"Districts' name should be in 2~33 Latin alphabets.");
		showValidationMsg("#populationInput", "error",
			"Population should be in 4~18 numbers.");
		return false;
	} else if (!regularDistrict.test(inputDistrict)
		&& regularPopulation.test(inputPopulation)) {
		showValidationMsg("#districtInput", "error",
			"Districts' name should be in 2~33 Latin alphabets.");
		showValidationMsg("#populationInput", "success", "√");
		return false;
	} else if (regularDistrict.test(inputDistrict)
		&& !regularPopulation.test(inputPopulation)) {
		showValidationMsg("#districtInput", "success", "√");
		showValidationMsg("#populationInput", "error",
			"Population should be in 4~18 numbers.");
		return false;
	} else {
		showValidationMsg("#districtInput", "success", "√");
		showValidationMsg("#populationInput", "success", "√");
		// Send an ajax request to commit save options.
		$.ajax({
			url: pathdeApp + '/city',
			type: 'POST',
			contentType: 'application/json;charset=UTF-8',
			dataType: 'json',
			data: JSON
				.stringify({
					'name': $("#nameInput").val().trim(),
					'continent': $("#continentInput option:selected").val(),
					'nation': $("#nationInput option:selected").val(),
					'district': inputDistrict,
					'population': inputPopulation
				}),
			success: function(result) {
				if (result.code === 200) {
					$("#cityAddModal").modal('hide');
					// To last page.
					toSelectedPg(totalPages, searchName);
				} else if (undefined !== result.extend.errorFields.name) {
					showValidationMsg("#nameInput", "error", result.extend.errorFields.name);
				}
			}
		});
	}
});

// Add click function to the edit modal.
$(document).on('click', '.edit_btn', function() {
	let editId = $(this).attr("editId");
	formReset("#cityEditModal form");
	getCityInfo(editId);
	$("#cityInfoChangeBtn").attr("editId", editId);
	$("#cityEditModal").modal({
		backdrop: 'static'
	});
});

// Get the selected city infos.
function getCityInfo(id) {
	$.ajax({
		url: pathdeApp + '/city/' + id,
		type: 'GET',
		dataType: 'json',
		success: function(result) {
			let cityData = result.extend.citySelected;
			$("#nameEdit").text(cityData.name);
			$("#continentEdit").text(cityData.continent);
			$("#languageEdit").text(cityData.language);
			$("#districtEdit").val(cityData.district);
			$("#populationEdit").val(cityData.population);
			getNations("#nationEdit", id);
		}
	});
}

// Add click function to select of nation.
$("#nationEdit").on('change', function() {
	let nationVal = $("#nationEdit option:selected").val();
	getLanguage($("#languageEdit"), nationVal);
})

// Get the name of country.
function getLanguage(element, nationVal) {
	$(element).empty();
	$.ajax({
		url: pathdeApp + '/language',
		data: 'nationVal=' + nationVal,
		type: 'GET',
		success: function(result) {
			$("#languageEdit").text(result.extend.languages);
		}
	});
}

// Submit the change of city infos.
$("#cityInfoChangeBtn").on('click', function() {
	let inputDistrict = $("#districtEdit").val().trim();
	let inputPopulation = $("#populationEdit").val().trim();
	let regularDistrict = /^[a-zA-Z-\s]{2,33}$/;
	let regularPopulation = /^\d{4,18}$/;
	let editId = $(this).attr("editId");
	if (!regularDistrict.test(inputDistrict)
		&& !regularPopulation.test(inputPopulation)) {
		showValidationMsg("#districtEdit", "error",
			"Districts' name should be in 2~33 Latin alphabets.");
		showValidationMsg("#populationEdit", "error",
			"Population should be in 4~18 numbers.");
		return false;
	}
	if (!regularDistrict.test(inputDistrict)
		&& regularPopulation.test(inputPopulation)) {
		showValidationMsg("#districtEdit", "error",
			"Districts' name should be in 2~33 Latin alphabets.");
		showValidationMsg("#populationEdit", "success", "√");
		return false;
	}
	if (regularDistrict.test(inputDistrict)
		&& !regularPopulation.test(inputPopulation)) {
		showValidationMsg("#districtEdit", "success", "√");
		showValidationMsg("#populationEdit", "error",
			"Population should be in 4~18 numbers.");
		return false;
	}
	showValidationMsg("#districtEdit", "success", "√");
	showValidationMsg("#populationEdit", "success", "√");
	$.ajax({
		url: pathdeApp + '/city/' + editId,
		type: 'PUT',
		contentType: 'application/json;charset=UTF-8',
		dataType: 'json',
		data: JSON.stringify({
			'id': editId,
			'name': $("#nameEdit").text(),
			'continent': $("#continentEdit").text(),
			'nation': $("#nationEdit option:selected").val(),
			'district': inputDistrict,
			'population': inputPopulation
		}),
		success: function() {
			$("#cityEditModal").modal('hide');
			toSelectedPg(currentPage, searchName);
		}
	});
});

// Add click function to the delete modal.
$(document).on('click', '.delete_btn', function() {
	let cityName = $(this).parents("tr").find("td:eq(0)").text().trim();
	let cityId = $(this).attr("deleteId");
	if (confirm("この" + cityName + "という都市の情報を削除する、よろしいでしょうか。")) {
		$.ajax({
			url: pathdeApp + "/city/" + cityId,
			type: "DELETE",
			success: function() {
				toSelectedPg(currentPage, searchName);
			}
		});
	}
});

// Reset the modal form.
function formReset(element) {
	$(element)[0].reset();
	$(element).find(".form-control").removeClass("is-valid is-invalid");
	$(element).find(".form-text").removeClass("valid-feedback invalid-feedback");
	$(element).find(".form-text").text("");
}

// Add status color to the modal form input rows.
function showValidationMsg(element, status, msg) {
	$(element).removeClass("is-valid is-invalid");
	$(element).next("span").removeClass("valid-feedback invalid-feedback");
	$(element).next("span").text("");
	if (status === "success") {
		$(element).addClass("is-valid");
		$(element).next("span").addClass("valid-feedback");
	} else if (status === "error") {
		$(element).addClass("is-invalid");
		$(element).next("span").addClass("invalid-feedback").text(msg);
	}
}