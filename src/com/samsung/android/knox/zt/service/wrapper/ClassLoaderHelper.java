package com.samsung.android.knox.zt.service.wrapper;

import dalvik.system.PathClassLoader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ClassLoaderHelper {
    public static volatile ClassLoaderHelper sInstance;
    public final ClassLoader mSakClassLoader =
            new PathClassLoader(
                    "/system/framework/samsungkeystoreutils.jar",
                    ClassLoader.getSystemClassLoader());

    public static ClassLoaderHelper getInstance() {
        if (sInstance == null) {
            synchronized (ClassLoaderHelper.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ClassLoaderHelper();
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public ClassLoader getSakClassLoader() {
        return this.mSakClassLoader;
    }
}
