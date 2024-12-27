package com.samsung.android.sdk.command;

import com.samsung.android.settings.actions.SettingsCommandActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CommandSdk {
    public static final Object sWaitLock = new Object();
    public SettingsCommandActionHandler mActionHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class LazyHolder {
        public static final CommandSdk INSTANCE = new CommandSdk();
    }
}
