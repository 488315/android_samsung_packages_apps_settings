package com.samsung.android.settings.eternal.scloud;

import android.content.Context;

import com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient;
import com.samsung.android.settings.eternal.utils.FileUtils;

import java.io.File;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityBackupRestoreClient implements ISCloudQBNRClient {
    public static void removeRestoreFile(Context context) {
        FileUtils.deleteFile(
                new File(context.getFilesDir().getPath() + "/Cloud_Restore_A11Y.xml"),
                "AccessibilityBackupRestoreClient",
                "removeRestoreFile()");
    }
}
