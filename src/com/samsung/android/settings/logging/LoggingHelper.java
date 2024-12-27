package com.samsung.android.settings.logging;

import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class LoggingHelper {
    public static void insertEntranceLogging(int i) {
        insertEventLogging(i, 9000);
    }

    public static void insertEventLogging(int i, int i2) {
        SALogging.insertSALog(String.valueOf(i), String.valueOf(i2));
    }

    public static void insertFlowLogging(int i) {
        SALogging.insertSALog(String.valueOf(i));
    }

    public static void insertEventLogging(int i, String str) {
        SALogging.insertSALog(String.valueOf(i), str);
    }

    public static void insertEventLogging(int i, int i2, long j) {
        SALogging.insertSALog(j, String.valueOf(i), String.valueOf(i2));
    }

    public static void insertEventLogging(String str, int i) {
        SALogging.insertSALog(str, String.valueOf(i));
    }

    public static void insertEventLogging(int i, int i2, String str) {
        SALogging.insertSALog(String.valueOf(i), String.valueOf(i2), str);
    }

    public static void insertEventLogging(int i, int i2, boolean z) {
        SALogging.insertSALog(
                String.valueOf(i),
                String.valueOf(i2),
                z ? "1000" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
    }

    public static void insertEventLogging(int i, int i2, long j, String str) {
        SALogging.insertSALog(j, String.valueOf(i), String.valueOf(i2), String.valueOf(str));
    }
}
