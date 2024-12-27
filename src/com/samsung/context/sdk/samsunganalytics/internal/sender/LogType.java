package com.samsung.context.sdk.samsunganalytics.internal.sender;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum LogType {
    DEVICE("dvc"),
    UIX("uix");

    String abbrev;

    LogType(String str) {
        this.abbrev = str;
    }

    public final String getAbbrev() {
        return this.abbrev;
    }
}
