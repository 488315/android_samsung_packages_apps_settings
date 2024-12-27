package com.samsung.android.settings.actions.development;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CommandManager {
    public static final Object mLock = new Object();
    public static CommandManager sInstance;
    public Context mContext;

    public static CommandManager getInstance(Context context) {
        CommandManager commandManager;
        synchronized (mLock) {
            try {
                if (sInstance == null) {
                    CommandManager commandManager2 = new CommandManager();
                    commandManager2.mContext = context;
                    sInstance = commandManager2;
                }
                commandManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return commandManager;
    }
}
