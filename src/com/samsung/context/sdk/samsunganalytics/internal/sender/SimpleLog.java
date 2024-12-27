package com.samsung.context.sdk.samsunganalytics.internal.sender;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SimpleLog {
    public String _id;
    public String data;
    public long timestamp;
    public LogType type;

    public SimpleLog(long j, String str, LogType logType) {
        this(ApnSettings.MVNO_NONE, j, str, logType);
    }

    public SimpleLog(String str, long j, String str2, LogType logType) {
        this._id = str;
        this.timestamp = j;
        this.data = str2;
        this.type = logType;
    }
}
