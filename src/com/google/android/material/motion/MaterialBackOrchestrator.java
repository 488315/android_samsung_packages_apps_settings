package com.google.android.material.motion;

import android.view.View;
import android.window.OnBackInvokedCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MaterialBackOrchestrator {
    public final Api34BackCallbackDelegate backCallbackDelegate = new Api34BackCallbackDelegate();
    public final MaterialBackHandler backHandler;
    public final View view;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Api34BackCallbackDelegate {
        public OnBackInvokedCallback onBackInvokedCallback;
    }

    public MaterialBackOrchestrator(MaterialBackHandler materialBackHandler, View view) {
        this.backHandler = materialBackHandler;
        this.view = view;
    }
}
