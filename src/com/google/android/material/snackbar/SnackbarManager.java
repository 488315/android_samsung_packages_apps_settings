package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SnackbarManager {
    public static SnackbarManager snackbarManager;
    public final Object lock = new Object();

    public SnackbarManager() {
        new Handler(
                Looper.getMainLooper(),
                new Handler
                        .Callback() { // from class:
                                      // com.google.android.material.snackbar.SnackbarManager.1
                    @Override // android.os.Handler.Callback
                    public final boolean handleMessage(Message message) {
                        if (message.what != 0) {
                            return false;
                        }
                        SnackbarManager snackbarManager2 = SnackbarManager.this;
                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(message.obj);
                        synchronized (snackbarManager2.lock) {
                            throw null;
                        }
                    }
                });
    }
}
