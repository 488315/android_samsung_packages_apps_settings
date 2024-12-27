package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class MediaFileManager {
    public static final HashMap extensionToMediaFileTypeMap = new HashMap();
    public static final SparseArray fileTypeToMediaFileTypeMap = new SparseArray();
    public static final ArrayList documentExtension = new ArrayList();
    public static final ArrayList archiveExtension = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MediaFileInfo {
        public final int fileType;

        public MediaFileInfo(int i, String str, String str2) {
            this.fileType = i;
        }
    }

    public static void addFileType(int i, String str, String str2) {
        MediaFileInfo mediaFileInfo = new MediaFileInfo(i, str, str2);
        extensionToMediaFileTypeMap.put(str, mediaFileInfo);
        fileTypeToMediaFileTypeMap.put(i, mediaFileInfo);
        if (i >= 300 && i <= FileType.LAST_DOCUMENT_FILE_TYPE) {
            documentExtension.add(str);
        } else {
            if (i < 410 || i > FileType.LAST_ARCHIVE_FILE_TYPE) {
                return;
            }
            archiveExtension.add(str);
        }
    }
}
