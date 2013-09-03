$(document).bind("click", function() {
	var txfSearch = $("#txfSearch");
	var btnSearch = $("#btnSearch");
	var searchResult = $("#searchResult");
	var page = 0;
	
	btnSearch.click(function() {
		page = 0;
		$.doajax();
		return false;
	});
	$.doajax = function() {
		var q = txfSearch.val();
		searchResult.empty();
		if (q.length == 0) {
			searchResult.append("<p>Please enter something to search for first.</p>");
			return;
		}
		else if (q.length > 255) {
			searchResult.append("<p>Search result cannot be longer than 255 characters.</p>");
			return;
		}
		searchResult.append("<p>Waiting for response..");
		$.getJSON("https://www.googleapis.com/books/v1/volumes?callback=?", { "q": q, "startIndex": page * 20 }, function(data) {
			searchResult.empty();
			if (data.totalItems == 0) {
				searchResult.append("<h2>No results found for \"" + q + "\".</h2>");
			} else {
				var lastitem;
				if ((page + 1) * 20 < data.totalItems)
					lastitem = (page + 1) * 20;
				else
					lastitem = data.totalItems;
				searchResult.append("<h3>Showing item " + (page * 20 + 1) + " - " + lastitem + " of " + data.totalItems + ".</h3>");
				var pageButtons = "<fieldset data-role=\"controlgroup\" data-type=\"horizontal\" class=\"ui-corner-all ui-controlgroup ui-controlgroup-horizontal\">" +
				(page == 0 ? "" :
					"<a data-role=\"button\" id=\"btnFirstPage\" class=\"ui-btn ui-corner-left ui-btn-up-c\">First</a>" +
					"<a data-role=\"button\" id=\"btnPrevPage\" class=\"ui-btn ui-btn-up-c\">Previous</a>"
				) +
				(page * 20 > data.totalItems ? "" :
					"<a data-role=\"button\" id=\"btnNextPage\" class=\"ui-btn" + 
						(page > 0 ? "" : " ui-corner-left") + " ui-corner-right ui-controlgroup-last ui-btn-up-c\">Next</a>"
				) +
				"</fieldset>";
				searchResult.append(pageButtons);
				searchResult.append("<ul data-role=\"listview\" class=\"ui-listview\"></ul>");
				$.each(data.items, function(idx, book) {
					var tmp = "<li class=\"ul-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-thumb ui-btn-up-c\">" +
							"<div class=\"ui-btn-inner ui-li\" aria-hidden=\"true\">" +
							"<div class=\"ui-btn-text\">";
					tmp += "<a href=\"" + book.volumeInfo.infoLink + "\" class=\"ui-link-inherit\" target=\"blank\">";
					if (book.volumeInfo.imageLinks != undefined && book.volumeInfo.imageLinks.smallThumbnail != undefined)
						tmp += "<img src=\"" + book.volumeInfo.imageLinks.smallThumbnail + "\" class=\"ui-li-thumb\" />";
					tmp += "<h3 class=\"ul-li-heading\">" + book.volumeInfo.title + "</h3>";
					var first = true;
					tmp += "<p class=\"ul-li-desc\">";
					if (book.volumeInfo.authors == undefined)
						tmp += "No author(s)";
					else
						$.each(book.volumeInfo.authors, function(_idx, author) {
							if (first) {
								first = false;
							} else {
								tmp += ", ";
							}
							tmp += author;
						});
					tmp += "</p></a></div></div></li>";
					$("#searchResult > ul").append(tmp);
				});
				$("#btnFirstPage").click(function() {
					page = 0;
					$.doajax();
				});
				$("#btnPrevPage").click(function() {
					page--;
					$.doajax();
				});
				$("#btnNextPage").click(function() {
					page++;
					$.doajax();
				});
			}
		});
	}
});