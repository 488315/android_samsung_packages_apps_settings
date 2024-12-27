package com.android.settings.connecteddevice.threadnetwork;

import android.net.thread.ThreadNetworkController;

import kotlin.jvm.internal.Intrinsics;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ThreadNetworkUtils$getThreadNetworkController$1
        implements BaseThreadNetworkController {
    public final /* synthetic */ ThreadNetworkController $controller;

    public ThreadNetworkUtils$getThreadNetworkController$1(
            ThreadNetworkController threadNetworkController) {
        this.$controller = threadNetworkController;
    }

    @Override // com.android.settings.connecteddevice.threadnetwork.BaseThreadNetworkController
    public final void registerStateCallback(
            Executor executor, ThreadNetworkController.StateCallback callback) {
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.$controller.registerStateCallback(executor, callback);
    }

    @Override // com.android.settings.connecteddevice.threadnetwork.BaseThreadNetworkController
    public final void setEnabled(
            boolean z,
            Executor executor,
            ThreadNetworkToggleController$setChecked$1 threadNetworkToggleController$setChecked$1) {
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.$controller.setEnabled(z, executor, threadNetworkToggleController$setChecked$1);
    }

    @Override // com.android.settings.connecteddevice.threadnetwork.BaseThreadNetworkController
    public final void unregisterStateCallback(ThreadNetworkController.StateCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.$controller.unregisterStateCallback(callback);
    }
}
