package com.samsung.android.settings.wifi.develop.history.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LogRepository {
    public HashMap mConnectionLog;
    public String mLastConnectedSsid;
    public HashMap mLogBySsid;
    public HashMap mOnOffLog;
    public boolean mSupportConnectivityLog;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class LazyHolder {
        public static final LogRepository INSTANCE;

        static {
            LogRepository logRepository = new LogRepository();
            logRepository.mSupportConnectivityLog = true;
            INSTANCE = logRepository;
        }
    }

    public static String convertAkmSuite(String str) {
        str.getClass();
        switch (str) {
            case "0xfac01":
                return "8021X";
            case "0xfac02":
                return "PSK";
            case "0xfac03":
                return "FT_8021X";
            case "0xfac04":
                return "FT_PSK";
            case "0xfac05":
                return "SHA256_1X";
            case "0xfac06":
                return "SHA256_PSK";
            case "0xfac08":
                return "SAE_SHA256";
            case "0xfac0B":
                return "8021X_SUITE_B";
            case "0xfac0C":
                return "8021X_SUITE_B_192";
            case "0xfac0D":
                return "FT_8021X_SHA384";
            case "0xfac0E":
                return "FILS_SHA256";
            case "0xfac0F":
                return "FILS_SHA384";
            case "0xfac10":
                return "FT_FILS_SHA256";
            case "0xfac11":
                return "FT_FILS_SHA384";
            case "0xfac12":
                return "OWE";
            case "0x0":
                return "OPEN";
            default:
                return "Not found";
        }
    }

    public static String convertCipher(String str) {
        str.getClass();
        switch (str) {
            case "0xfac00":
            case "0x0":
                return "NONE";
            case "0xfac01":
                return "WEP40";
            case "0xfac02":
                return "TKIP";
            case "0xfac03":
                return "Reserved";
            case "0xfac04":
                return "CCMP128";
            case "0xfac05":
                return "WEP104";
            case "0xfac06":
                return "BIPCMAC128";
            case "0xfac07":
                return "Group addressed traffic not allowed";
            case "0xfac08":
                return "GCMP128";
            case "0xfac09":
                return "GCMP256";
            case "0xfac0a":
                return "CCMP256";
            case "0xfac0b":
                return "BIPGMAC128";
            case "0xfac0c":
                return "BIPGMAC256";
            case "0xfac0d":
                return "BIPCMAC256";
            default:
                return "Not found";
        }
    }

    public static String getNetworkKey(HistoryLog historyLog) {
        if (LogType.CONNECTING.equals(historyLog.type)) {
            new ArrayList();
            ConnectivityLogParser.ConnectingLog parseConnectingLog =
                    ConnectivityLogParser.parseConnectingLog(historyLog);
            if (TextUtils.isEmpty(parseConnectingLog.ssid)) {
                return ApnSettings.MVNO_NONE;
            }
            return parseConnectingLog.ssid
                    + "("
                    + convertAkmSuite(parseConnectingLog.akmSuite)
                    + ")";
        }
        if (!LogType.TRY_TO_CONNECT.equals(historyLog.type)) {
            return ApnSettings.MVNO_NONE;
        }
        new ArrayList();
        IssueDetectorLogParser.TryToConnectLog parseTryToConnectLog =
                IssueDetectorLogParser.parseTryToConnectLog(historyLog);
        if (TextUtils.isEmpty(parseTryToConnectLog.ssid)) {
            return ApnSettings.MVNO_NONE;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(parseTryToConnectLog.ssid);
        sb.append("(");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                sb, parseTryToConnectLog.security, ")");
    }

    public final ArrayList getDeviceLog(Context context) {
        if (context == null) {
            Log.e("LogRepository", "Fail to get context");
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        SemWifiManager semWifiManager =
                (SemWifiManager)
                        context.getApplicationContext()
                                .getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        if (semWifiManager == null) {
            Log.e("LogRepository", "Fail to get semWifiManager");
            return new ArrayList();
        }
        String[] strArr = {"[CONN]", "[ROAM]", "[BTM]", "[EAPOL]"};
        for (int i = 0; i < 4; i++) {
            arrayList.addAll(
                    new ConnectivityLogParser()
                            .parseRawData(semWifiManager.getConnectivityLog(strArr[i])));
        }
        if (arrayList.isEmpty()) {
            this.mSupportConnectivityLog = false;
        } else {
            this.mSupportConnectivityLog = true;
        }
        if (this.mSupportConnectivityLog) {
            IssueDetectorLogParser issueDetectorLogParser = new IssueDetectorLogParser();
            for (String str : semWifiManager.getIssueDetectorDump(200).split("\n")) {
                if (str.length() != 0) {
                    HistoryLog parseLog = issueDetectorLogParser.parseLog(str);
                    LogType logType = LogType.NONE;
                    LogType logType2 = parseLog.type;
                    if (!logType.equals(logType2)
                            && ((!LogType.CONNECTING.equals(logType2)
                                            || !parseLog.data.startsWith("FAIL"))
                                    && !LogType.TRY_TO_CONNECT.equals(logType2))) {
                        issueDetectorLogParser.mHistoryLogs.add(parseLog);
                    }
                }
            }
            arrayList.addAll(issueDetectorLogParser.mHistoryLogs);
        } else {
            arrayList.addAll(
                    new IssueDetectorLogParser()
                            .parseRawData(semWifiManager.getIssueDetectorDump(200)));
        }
        arrayList.sort(Comparator.comparing(new HistoryLog$$ExternalSyntheticLambda0()));
        return arrayList;
    }

    public final void updateLogBySsid(ArrayList arrayList) {
        String networkKey = getNetworkKey((HistoryLog) arrayList.get(0));
        if (networkKey.length() > 0) {
            if (!this.mLogBySsid.containsKey(networkKey)) {
                this.mLogBySsid.put(networkKey, new ArrayList(arrayList));
                return;
            }
            ArrayList arrayList2 = (ArrayList) this.mLogBySsid.get(networkKey);
            arrayList2.addAll(arrayList);
            this.mLogBySsid.put(networkKey, arrayList2);
        }
    }

    public final void updateRouterHistoryLog(Context context) {
        ArrayList deviceLog = getDeviceLog(context);
        this.mLogBySsid = new HashMap();
        LogType logType =
                this.mSupportConnectivityLog ? LogType.CONNECTING : LogType.TRY_TO_CONNECT;
        Iterator it = deviceLog.iterator();
        while (it.hasNext() && !logType.equals(((HistoryLog) it.next()).type)) {
            it.remove();
        }
        if (deviceLog.isEmpty()) {
            Log.i("LogRepository", "There is no enough log");
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = deviceLog.iterator();
        while (it2.hasNext()) {
            HistoryLog historyLog = (HistoryLog) it2.next();
            if (arrayList.isEmpty()) {
                arrayList.add(historyLog);
            } else {
                LogType logType2 = historyLog.type;
                if (logType.equals(logType2)) {
                    arrayList.clear();
                    arrayList.add(historyLog);
                } else {
                    arrayList.add(historyLog);
                    if (LogType.DISCONNECTED.equals(logType2)
                            || LogType.ASSOC_REJECT.equals(logType2)
                            || LogType.L2_CONNECT_FAIL.equals(logType2)) {
                        updateLogBySsid(arrayList);
                        arrayList.clear();
                    }
                }
            }
        }
        this.mLastConnectedSsid = ApnSettings.MVNO_NONE;
        if (arrayList.isEmpty()) {
            return;
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            if (logType.equals(((HistoryLog) it3.next()).type)) {
                updateLogBySsid(arrayList);
                this.mLastConnectedSsid = getNetworkKey((HistoryLog) arrayList.get(0));
            }
        }
    }
}
