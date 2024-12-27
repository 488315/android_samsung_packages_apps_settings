package com.samsung.android.settings.languagepack.appsstub;

import android.os.Environment;

import com.samsung.android.settings.languagepack.logger.Log;

import java.io.File;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AppStubUtils {
    public static boolean isUTModeTest() {
        boolean isDirectory =
                new File(Environment.getExternalStorageDirectory(), "android/obb/go_to_utmode.test")
                        .isDirectory();
        Log.d("AppStubUtils", "isUTModeTest() : " + isDirectory);
        return isDirectory;
    }

    public static boolean usingPreDeployServer() {
        boolean isDirectory =
                new File(Environment.getExternalStorageDirectory(), "go_to_andromeda.test")
                        .isDirectory();
        Log.d("AppStubUtils", "usingPreDeployServer() : " + isDirectory);
        return isDirectory;
    }
}
