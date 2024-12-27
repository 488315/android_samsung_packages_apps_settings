package com.samsung.android.settings.wifi.develop.history.model;

import java.time.LocalDateTime;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HistoryLog {
    public String appName;
    public final String data;
    public String targetSsid;
    public final LocalDateTime time;
    public final LogType type;
    public boolean wifiState;

    public HistoryLog(LocalDateTime localDateTime, LogType logType, String str) {
        this.time = localDateTime;
        this.type = logType;
        this.data = str;
    }

    public final String toString() {
        return this.time + " " + this.type + " " + this.data;
    }
}
