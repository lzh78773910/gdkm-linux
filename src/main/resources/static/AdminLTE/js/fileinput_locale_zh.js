/*!
 * FileInput Chinese Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author kangqf <kangqingfei@gmail.com>
 *
 * NOTE: this file must be saved in UTF-8 encoding.
 */
(function ($) {
    "use strict";

    $.fn.fileinputLocales['zh'] = {
        fileSingle: '�ļ�',
        filePlural: '���ļ�',
        browseLabel: 'ѡ�� &hellip;',
        removeLabel: '�Ƴ�',
        removeTitle: '���ѡ���ļ�',
        cancelLabel: 'ȡ��',
        cancelTitle: 'ȡ�������е��ϴ�',
        pauseLabel: 'Pause',
        pauseTitle: 'Pause ongoing upload',
        uploadLabel: '�ϴ�',
        uploadTitle: '�ϴ�ѡ���ļ�',
        msgNo: 'û��',
        msgNoFilesSelected: 'δѡ���ļ�',
        msgPaused: 'Paused',
        msgCancelled: 'ȡ��',
        msgPlaceholder: 'ѡ�� {files}...',
        msgZoomModalHeading: '��ϸԤ��',
        msgFileRequired: '����ѡ��һ���ļ��ϴ�.',
        msgSizeTooSmall: '�ļ� "{name}" (<b>{size} KB</b>) ��������޶���С <b>{minSize} KB</b>.',
        msgSizeTooLarge: '�ļ� "{name}" (<b>{size} KB</b>) �����������С <b>{maxSize} KB</b>.',
        msgFilesTooLess: '�����ѡ������ <b>{n}</b> {files} ���ϴ�. ',
        msgFilesTooMany: 'ѡ����ϴ��ļ����� <b>({n})</b> ��������ļ������Ƹ��� <b>{m}</b>.',
        msgFileNotFound: '�ļ� "{name}" δ�ҵ�!',
        msgFileSecured: '��ȫ���ƣ�Ϊ�˷�ֹ��ȡ�ļ� "{name}".',
        msgFileNotReadable: '�ļ� "{name}" ���ɶ�.',
        msgFilePreviewAborted: 'ȡ�� "{name}" ��Ԥ��.',
        msgFilePreviewError: '��ȡ "{name}" ʱ������һ������.',
        msgInvalidFileName: '�ļ��� "{name}" �����Ƿ��ַ�.',
        msgInvalidFileType: '����ȷ������ "{name}". ֻ֧�� "{types}" ���͵��ļ�.',
        msgInvalidFileExtension: '����ȷ���ļ���չ�� "{name}". ֻ֧�� "{extensions}" ���ļ���չ��.',
        msgFileTypes: {
            'image': 'image',
            'html': 'HTML',
            'text': 'text',
            'video': 'video',
            'audio': 'audio',
            'flash': 'flash',
            'pdf': 'PDF',
            'object': 'object'
        },
        msgUploadAborted: '���ļ��ϴ�����ֹ',
        msgUploadThreshold: '������...',
        msgUploadBegin: '���ڳ�ʼ��...',
        msgUploadEnd: '���',
        msgUploadResume: 'Resuming upload...',
        msgUploadEmpty: '��Ч���ļ��ϴ�.',
        msgUploadError: 'Upload Error',
        msgDeleteError: 'Delete Error',
        msgProgressError: '�ϴ�����',
        msgValidationError: '��֤����',
        msgLoading: '���ص� {index} �ļ� �� {files} &hellip;',
        msgProgress: '���ص� {index} �ļ� �� {files} - {name} - {percent}% ���.',
        msgSelected: '{n} {files} ѡ��',
        msgFoldersNotAllowed: 'ֻ֧����ק�ļ�! ���� {n} ��ק���ļ���.',
        msgImageWidthSmall: 'ͼ���ļ���"{name}"�Ŀ�ȱ���������{size}����.',
        msgImageHeightSmall: 'ͼ���ļ���"{name}"�ĸ߶ȱ�������Ϊ{size}����.',
        msgImageWidthLarge: 'ͼ���ļ�"{name}"�Ŀ�Ȳ��ܳ���{size}����.',
        msgImageHeightLarge: 'ͼ���ļ�"{name}"�ĸ߶Ȳ��ܳ���{size}����.',
        msgImageResizeError: '�޷���ȡ��ͼ��ߴ������',
        msgImageResizeException: '����ͼ���Сʱ��������<pre>{errors}</pre>',
        msgAjaxError: '{operation} ��������. ������!',
        msgAjaxProgressError: '{operation} ʧ��',
        msgDuplicateFile: 'File "{name}" of same size "{size} KB" has already been selected earlier. Skipping duplicate selection.',
        msgResumableUploadRetriesExceeded:  'Upload aborted beyond <b>{max}</b> retries for file <b>{file}</b>! Error Details: <pre>{error}</pre>',
        msgPendingTime: '{time} remaining',
        msgCalculatingTime: 'calculating time remaining',
        ajaxOperations: {
            deleteThumb: 'ɾ���ļ�',
            uploadThumb: '�ϴ��ļ�',
            uploadBatch: '�����ϴ�',
            uploadExtra: '�������ϴ�'
        },
        dropZoneTitle: '��ק�ļ������� &hellip;<br>֧�ֶ��ļ�ͬʱ�ϴ�',
        dropZoneClickTitle: '<br>(����{files}��ťѡ���ļ�)',
        fileActionSettings: {
            removeTitle: 'ɾ���ļ�',
            uploadTitle: '�ϴ��ļ�',
            downloadTitle: '�����ļ�',
            uploadRetryTitle: '����',
            zoomTitle: '�鿴����',
            dragTitle: '�ƶ� / ����',
            indicatorNewTitle: 'û���ϴ�',
            indicatorSuccessTitle: '�ϴ�',
            indicatorErrorTitle: '�ϴ�����',
            indicatorPausedTitle: 'Upload Paused',
            indicatorLoadingTitle:  '�ϴ� ...'
        },
        previewZoomButtonTitles: {
            prev: 'Ԥ����һ���ļ�',
            next: 'Ԥ����һ���ļ�',
            toggleheader: '����',
            fullscreen: 'ȫ��',
            borderless: '�ޱ߽�ģʽ',
            close: '�رյ�ǰԤ��'
        }
    };
})(window.jQuery);
