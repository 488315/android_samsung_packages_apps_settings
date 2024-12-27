package com.samsung.android.settings.wifi.develop.history.model;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class LogParser {
    public final ArrayList mHistoryLogs = new ArrayList();

    public static String getAttributeFromLog(String str, HistoryLog historyLog) {
        for (String str2 : historyLog.data.split(" ")) {
            if (str2.contains(str)) {
                return str2.replace(str, ApnSettings.MVNO_NONE);
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public static int getNthSpace(int i, String str) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = str.indexOf(" ", i2) + 1;
        }
        return i2;
    }

    public static String getSsid(HistoryLog historyLog) {
        String str = historyLog.data;
        if (!str.contains("ssid=\"")) {
            return ApnSettings.MVNO_NONE;
        }
        int indexOf = str.indexOf("\"", str.indexOf("ssid=\"")) + 1;
        return str.substring(indexOf, str.indexOf("\"", indexOf));
    }

    public abstract HistoryLog parseLog(String str);

    public final ArrayList parseRawData(String str) {
        for (String str2 : str.split("\n")) {
            if (str2.length() != 0) {
                HistoryLog parseLog = parseLog(str2);
                if (!LogType.NONE.equals(parseLog.type)
                        && (!LogType.CONNECTING.equals(parseLog.type)
                                || !parseLog.data.startsWith("FAIL"))) {
                    this.mHistoryLogs.add(parseLog);
                }
            }
        }
        return this.mHistoryLogs;
    }
}
