package com.samsung.android.settings.wifi.develop.history.model;

import java.util.Arrays;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum LogType {
    NONE("NONE", 300),
    DISCONNECTED("rid=1", 100),
    CONNECTED("rid=2", 101),
    ROAM("rid=3", 102),
    TRY_TO_CONNECT("rid=11", 103),
    L2_CONNECT_FAIL("rid=13", 104),
    ASSOC_REJECT("rid=14", 105),
    /* JADX INFO: Fake field, exist only in values array */
    DHCP("rid=300", 106),
    WIFI_ON_OFF("rid=201", 107),
    CONNECTING("CONNECTING", 200),
    /* JADX INFO: Fake field, exist only in values array */
    ROAM_SCAN_TRIGGER("SCAN_START", 201),
    /* JADX INFO: Fake field, exist only in values array */
    ROAM_SCAN_RESULT("RESULT", 202),
    /* JADX INFO: Fake field, exist only in values array */
    DEAUTH("DEAUTH", 203),
    /* JADX INFO: Fake field, exist only in values array */
    DISASSOC("DISASSOC", 204),
    /* JADX INFO: Fake field, exist only in values array */
    BEACON_LOSS("DISCONN", 205);

    private final int priority;
    private final String type;

    LogType(String str, int i) {
        this.type = str;
        this.priority = i;
    }

    public static LogType getLogType(final String str) {
        return (LogType)
                Arrays.stream(values())
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.wifi.develop.history.model.LogType$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean equals;
                                        equals = ((LogType) obj).type.equals(str);
                                        return equals;
                                    }
                                })
                        .findFirst()
                        .orElse(NONE);
    }
}
