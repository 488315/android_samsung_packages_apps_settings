package com.android.settings.core.instrumentation;

import android.util.StatsEvent;
import android.util.StatsLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsStatsLog {
    public static void write(int i, int i2, int i3, int i4) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeInt(i4);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, int i3, long j, long j2, String str) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(97);
        newBuilder.writeInt(i);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeString(str);
        newBuilder.writeLong(j);
        newBuilder.writeLong(j2);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, long j, String str) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(272);
        newBuilder.writeLong(j);
        newBuilder.writeInt(i);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeString(str);
        newBuilder.writeInt(i2);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
