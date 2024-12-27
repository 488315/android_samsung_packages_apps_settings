package com.google.android.setupcompat.util;

import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Logger {
    public final String prefix;

    public Logger(String str) {
        this.prefix = ComposerKt$$ExternalSyntheticOutline0.m("[", str, "] ");
    }

    public final void atInfo(String str) {
        if (Log.isLoggable("SetupLibrary", 4)) {
            Log.i("SetupLibrary", this.prefix.concat(str));
        }
    }

    public final void e(String str) {
        Log.e("SetupLibrary", this.prefix.concat(str));
    }

    public final void w(String str) {
        Log.w("SetupLibrary", this.prefix.concat(str));
    }

    public final void e(String str, Throwable th) {
        Log.e("SetupLibrary", this.prefix.concat(str), th);
    }
}
