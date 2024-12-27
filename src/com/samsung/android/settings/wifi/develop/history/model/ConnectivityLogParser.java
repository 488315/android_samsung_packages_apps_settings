package com.samsung.android.settings.wifi.develop.history.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectivityLogParser extends LogParser {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectingLog {
        public String akmSuite;
        public String groupCipher;
        public String pairwiseCipher;
        public String ssid;
    }

    public static ConnectingLog parseConnectingLog(HistoryLog historyLog) {
        ConnectingLog connectingLog = new ConnectingLog();
        connectingLog.ssid = LogParser.getSsid(historyLog);
        connectingLog.pairwiseCipher = LogParser.getAttributeFromLog("pairwise=", historyLog);
        connectingLog.groupCipher = LogParser.getAttributeFromLog("group=", historyLog);
        connectingLog.akmSuite = LogParser.getAttributeFromLog("akm=", historyLog);
        return connectingLog;
    }

    @Override // com.samsung.android.settings.wifi.develop.history.model.LogParser
    public final HistoryLog parseLog(String str) {
        LocalDateTime parse =
                LocalDateTime.parse(
                        str.substring(0, LogParser.getNthSpace(2, str)).trim(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        int i = 0;
        for (int i2 = 0; i2 < 2; i2++) {
            i = str.indexOf("]", i) + 1;
        }
        int i3 = i + 1;
        int indexOf = str.indexOf(" ", i3);
        return new HistoryLog(
                parse, LogType.getLogType(str.substring(i3, indexOf)), str.substring(indexOf + 1));
    }
}
