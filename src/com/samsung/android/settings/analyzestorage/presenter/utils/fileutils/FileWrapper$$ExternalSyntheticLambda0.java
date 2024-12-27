package com.samsung.android.settings.analyzestorage.presenter.utils.fileutils;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class FileWrapper$$ExternalSyntheticLambda0 implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        FileWrapper$$ExternalSyntheticLambda0 fileWrapper$$ExternalSyntheticLambda0 =
                FileWrapper.TRASH_FOLDER_FILTER;
        return !".Trash".equalsIgnoreCase(str);
    }
}
