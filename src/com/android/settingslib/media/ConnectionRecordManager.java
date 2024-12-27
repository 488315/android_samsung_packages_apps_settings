package com.android.settingslib.media;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConnectionRecordManager {
    public static ConnectionRecordManager sInstance;
    public static final Object sInstanceSync = new Object();
    public String mLastSelectedDevice;

    public static ConnectionRecordManager getInstance() {
        synchronized (sInstanceSync) {
            try {
                if (sInstance == null) {
                    sInstance = new ConnectionRecordManager();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sInstance;
    }
}
