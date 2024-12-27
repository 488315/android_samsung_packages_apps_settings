package com.android.settings.utils;

import android.view.ContextThemeWrapper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalClassLoaderContextThemeWrapper extends ContextThemeWrapper {
    public Class mLocalClass;

    @Override // android.content.ContextWrapper, android.content.Context
    public final ClassLoader getClassLoader() {
        return this.mLocalClass.getClassLoader();
    }
}
