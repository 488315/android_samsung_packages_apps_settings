package com.samsung.android.settings.wifi.develop.history.model;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.time.Instant;
import java.time.ZoneId;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class IssueDetectorLogParser extends LogParser {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TryToConnectLog {
        public String security;
        public String ssid;
    }

    public static TryToConnectLog parseTryToConnectLog(HistoryLog historyLog) {
        String str;
        TryToConnectLog tryToConnectLog = new TryToConnectLog();
        tryToConnectLog.ssid = LogParser.getSsid(historyLog);
        String str2 = historyLog.data;
        if (str2.contains("configKey=\"")) {
            int indexOf = str2.indexOf("\"", str2.indexOf("\"", str2.indexOf("configKey=\"")) + 1);
            str = str2.substring(indexOf + 1, str2.indexOf(" ", indexOf));
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        tryToConnectLog.security = str;
        return tryToConnectLog;
    }

    /* JADX WARN: Type inference failed for: r3v8, types: [java.time.LocalDateTime] */
    @Override // com.samsung.android.settings.wifi.develop.history.model.LogParser
    public final HistoryLog parseLog(String str) {
        return new HistoryLog(
                Instant.ofEpochMilli(
                                Long.parseLong(
                                        str.substring(
                                                        LogParser.getNthSpace(1, str),
                                                        LogParser.getNthSpace(2, str))
                                                .replace("time=", ApnSettings.MVNO_NONE)
                                                .trim()))
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime(),
                LogType.getLogType(
                        str.substring(LogParser.getNthSpace(2, str), LogParser.getNthSpace(3, str))
                                .trim()),
                str.substring(LogParser.getNthSpace(3, str)));
    }
}
