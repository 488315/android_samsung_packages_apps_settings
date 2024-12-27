package com.samsung.android.settings.wifi.develop.history.model;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class OnOffLogParser extends LogParser {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnOffLog {
        public String packageName;
        public String wifiState;
    }

    public static OnOffLog parseOnOffLog(HistoryLog historyLog) {
        OnOffLog onOffLog = new OnOffLog();
        String str = historyLog.data;
        onOffLog.packageName =
                str.contains("callBy=")
                        ? str.substring(7, str.indexOf(" ", str.indexOf("callBy=")))
                        : ApnSettings.MVNO_NONE;
        onOffLog.wifiState = LogParser.getAttributeFromLog("wifiState=", historyLog);
        return onOffLog;
    }
}
