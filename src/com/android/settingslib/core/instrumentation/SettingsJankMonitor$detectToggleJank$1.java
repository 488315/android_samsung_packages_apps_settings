package com.android.settingslib.core.instrumentation;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsJankMonitor$detectToggleJank$1 implements Runnable {
    public static final SettingsJankMonitor$detectToggleJank$1 INSTANCE =
            new SettingsJankMonitor$detectToggleJank$1();

    @Override // java.lang.Runnable
    public final void run() {
        SettingsJankMonitor.jankMonitor.end(57);
    }
}
