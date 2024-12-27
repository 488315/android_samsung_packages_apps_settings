package com.samsung.android.settings.uwb.labs.common;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UwbStateChangedHandler {
    public static UwbStateChangedHandler instance;
    public final List listeners = new ArrayList();

    public static synchronized UwbStateChangedHandler getInstance() {
        UwbStateChangedHandler uwbStateChangedHandler;
        synchronized (UwbStateChangedHandler.class) {
            try {
                if (instance == null) {
                    instance = new UwbStateChangedHandler();
                }
                uwbStateChangedHandler = instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return uwbStateChangedHandler;
    }
}
