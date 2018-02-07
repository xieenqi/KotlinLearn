$(function() {
	var files = [];
	var currentFileName;

	var uploadQueue = [];
	var currentQueueIndex = 0;
	var isUploading = false;
	var getProgressReties = 0;

	var html5Uploader = null;
	var items = {};

	function initPageStrings() {
		document.title = STRINGS.WIFI_TRANS_TITLE;
		$('.content_title').text(STRINGS.FILES_ON_DEVICE);
		$('.table_header .filename').text(STRINGS.FILENAME);
		$('.table_header .size').text(STRINGS.FILE_SIZE);
		$('.table_header .operate').text(STRINGS.FILE_OPER);
	}

	function deleteBook(_event) {
		if (!confirm(STRINGS.CONFIRM_DELETE_BOOK)) {
			return;
		}
		var target = $(_event.currentTarget).parent().parent();
		var fileName = $($(target.children('.column-name')[0]).children('p')[0]).text();
		var deleteUrl = "files/" + encodeURI(fileName);
		$.post(deleteUrl, { '_method' : 'delete' }, function() {
		    target.remove();
		});
	}

	function downloadBook(_event) {
		var target = $(_event.currentTarget).parent().parent();
        var fileName = $($(target.children('.column-name')[0]).children('p')[0]).text();
		var url = "files/" + fileName;
		window.location =  url;
	}

	function loadFileList() {
		var now = new Date();
		var url = "files?";
		$.getJSON(url + now.getTime(), function(data) {
			files = data;
			fillFilesContainer();
			//$(".download").click(downloadBook);
			//$(".trash").click(deleteBook);
		});
	}

	function fillFilesContainer() {
		var filesContainer = $("#listing");
		filesContainer.empty();
		for (var i = 0; i < files.length;i ++) {
			var row = $('<tr class="row-file"></tr>');
			var fileInfo = files[i];
			row.append('<td class="column-name"><p filename="'+ fileInfo.name +'">' + fileInfo.name +'</p></td>');
			row.append('<td class="column-size"><p>' + fileInfo.size + '</p></td>');
			row.append('<td class="column-icon"><div class="download"></div></td>');
			row.append('<td class="column-delete"><div class="trash"></div></td>');
			filesContainer.append(row);
		}

		// return height;
	}

	function checkFileName(fileName) {

		var arr = fileName.split("\\");
		fileName = arr[arr.length - 1];

		var hasFile = false;
		var existFile = $("#listing .row-file [filename='" + escape(fileName) + "']");
		if (existFile.length > 0) {
            $(this).val("");
			if (existFile.parent().hasClass('progress_wrapper')) {
				return STRINGS.FILE_IN_QUEUE;
			} else {
				return STRINGS.FILE_EXISTS;
			}
		}
		return null;
	}

	function uploadFiles(files) {
		var uploader = getHtml5Uploader();
		if (files.length == 1) {
			var msg = checkFileName(files[0].name || files[0].fileName);
//			if (msg) {
//				alert(msg);
//				$('#newfile_0').val('');
//				return;
//			}
//			if (!files[0].type) {
//			    alert("不支持的文件类型！");
//            	$('#newfile_0').val('');
//            	return;
//			}
			uploader.add(files[0]);
			$('body').css({'overflow': 'hidden'})
			handlerProgress(true);
			return;
		}

		var totalFiles = files.length;
		var actualFiles = 0;
        for (var i = 0; i < files.length; ++i) {
			if (!checkFileName(files[i].name || files[i].fileName)) {
				uploader.add(files[i]);
				actualFiles ++;
			}
        }
		if (totalFiles != actualFiles) {
			var msg = "只支持单个文件上传!";
			alert(msg);
		}
	}

	function bindAjaxUpload(fileSelector) {
        $(fileSelector).unbind();
        $(fileSelector).change(function () {
            if (this.files) {
                uploadFiles(this.files);
                //优先使用HTML5上传方式
                return;
            }
            var fileName = $(this).val();
            //alert(fileName);

            var msg = checkFileName(fileName);
//            if (msg) {
//                alert(msg);
//                return;
//            }

            var arr = fileName.split("\\");
            fileName = arr[arr.length - 1];

            var row = $('<div class="file progress_wrapper"></div>');
            row.append('<div class="progress"></div>');
            row.append('<div class="column filename" filename="' + escape(fileName) + '">' + fileName + '</div>');
            row.append('<div class="column size"> - </div>');
            row.append('<div class="column precent">0%</div>');
            $("#right .files").prepend(row);

            uploadQueue.push(fileSelector);
            $(fileSelector).css({top: '-9999px', left: '-9999px'});
            $('.file_upload_warper').append('<input type="file" name="newfile" value="" id="newfile_' + uploadQueue.length + '" class="file_upload" />');
            bindAjaxUpload('#newfile_' + uploadQueue.length);
        });

    }

	function formatFileSize(value) {
	    var multiplyFactor = 0;
	    var tokens = ["bytes","KB","MB","GB","TB"];

	    while (value > 1024) {
	        value /= 1024;
	        multiplyFactor++;
	    }

	    return value.toFixed(1) + " " + tokens[multiplyFactor];
	}

    function cancelUpload() {
        var uploader = getHtml5Uploader();
        var fileName = $(this).parent().find('.filename').attr('filename');
        if (fileName) {
            item = items[fileName];
            if (item) {
                uploader.abort(item);
            }
        }
    }

    function handlerProgress(isShow) {
		if (isShow) {
			$('#upload_progress').fadeIn();
		}else {
            $('#upload_progress').fadeOut(function() {
                $('#progress').css({'width': "0%"});
                $('#progress_text').text('0%');
            });
		}
    }

	function getHtml5Uploader() {
		if (!html5Uploader) {
			html5Uploader = new bitcandies.FileUploader({
				url: 'files',
				maxconnections: 1,
				fieldname: 'newfile',
                enqueued: function (item) {
                },
                progress: function (item, loaded, total) {
	                var progress = Math.round(loaded / total * 100);
	                if (progress) {
	                    $('#progress').css({'width': progress + "%"});
                        $('#progress_text').text(progress + "%");
	                }

                },
                success: function (item) {
                    $('#newfile_0').val('');
                    $('body').css({'overflow': 'auto'});
                    handlerProgress(false);
                    loadFileList();
                },
                error: function (item) {
                    $('#newfile_0').val('');
                    $('body').css({'overflow': 'auto'});
                    handlerProgress(false);
                    alert(STRINGS.UPLOAD_FAILED);
                },
                aborted: function (item) {
//                    var fileName = item.getFilename();
//					var row = $("#right .file [filename='" + escape(fileName) + "']").parent();
//					row.remove();
                }
			});
		}
		return html5Uploader;
	}

    function handleDragOver(evt) {
        evt.stopPropagation();
        evt.preventDefault();
    }

    function handleDragLeave(evt) {
        evt.stopPropagation();
        evt.preventDefault();
    }

    function handleDrop(evt) {
        evt.stopPropagation();
        evt.preventDefault();

		if (evt.dataTransfer && evt.dataTransfer.files) {
			uploadFiles(evt.dataTransfer.files);
		}
    }

	$(document).ready(function() {
		// events delegate
		$('#listing').on('click', '.trash', deleteBook);
		$('#listing').on('click', '.download', downloadBook);

		initPageStrings();
		fillFilesContainer();
		loadFileList();
		$(window).resize(function() {
			fillFilesContainer();
		});
		bindAjaxUpload('#newfile_0');

		if (document && document.addEventListener) {
			document.addEventListener('drop', handleDrop, false);
		   	document.addEventListener('dragover', handleDragOver, false);
		   	document.addEventListener('dragleave', handleDragLeave, false);
        }

		$('#reload').click(function() {
		    location.reload();
		});

		$(document).ajaxError(function(event, request, settings){
			alert(STRINGS.CANNOT_CONNECT_SERVER);
			$('.progress_wrapper, .progress').css( { 'background':'#f00' });
		});
	});
});