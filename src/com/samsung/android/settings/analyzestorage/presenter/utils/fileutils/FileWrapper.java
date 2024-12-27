package com.samsung.android.settings.analyzestorage.presenter.utils.fileutils;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;

import java.io.File;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FileWrapper extends File {
    public static final FileWrapper$$ExternalSyntheticLambda0 TRASH_FOLDER_FILTER =
            new FileWrapper$$ExternalSyntheticLambda0();

    @Override // java.io.File
    public final boolean isHidden() {
        return getName().charAt(0) == '.';
    }

    @Override // java.io.File
    public final String[] list() {
        String[] list = super.list();
        if (list != null) {
            String absolutePath = getAbsolutePath();
            String str = StoragePathUtils.sInternalStorageRoot;
            if ((StoragePathUtils.getRootPath(StoragePathUtils.getDomainType(absolutePath))
                            + "/Android")
                    .equals(absolutePath)) {
                ArrayList arrayList = new ArrayList();
                for (String str2 : list) {
                    if (TRASH_FOLDER_FILTER.accept(this, str2)) {
                        arrayList.add(str2);
                    }
                }
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
        }
        return list;
    }

    @Override // java.io.File
    public final File[] listFiles() {
        String absolutePath = getAbsolutePath();
        String str = StoragePathUtils.sInternalStorageRoot;
        StringBuilder sb = new StringBuilder();
        sb.append(StoragePathUtils.getRootPath(StoragePathUtils.getDomainType(absolutePath)));
        sb.append("/Android");
        return sb.toString().equals(absolutePath)
                ? super.listFiles(TRASH_FOLDER_FILTER)
                : super.listFiles();
    }

    @Override // java.io.File
    public final boolean renameTo(File file) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean renameTo = super.renameTo(file);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (currentTimeMillis2 > 1000) {
            Log.w("FileWrapper", "renameTo ] takes too long, executeTime = " + currentTimeMillis2);
        }
        return renameTo;
    }
}
