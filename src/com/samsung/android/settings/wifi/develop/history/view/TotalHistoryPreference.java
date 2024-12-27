package com.samsung.android.settings.wifi.develop.history.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.history.HistoryLogDataAdapter;
import com.samsung.android.settings.wifi.develop.history.model.HistoryLog;
import com.samsung.android.settings.wifi.develop.history.model.LogRepository;
import com.samsung.android.settings.wifi.develop.history.model.LogType;
import com.samsung.android.settings.wifi.develop.history.model.OnOffLogParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TotalHistoryPreference extends Preference {
    public final Context mContext;
    public HistoryLogDataAdapter mHistoryLogDataAdapter;
    public RecyclerView mHistoryLogDataRecyclerView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.history.view.TotalHistoryPreference$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    public TotalHistoryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        String str;
        this.mContext = context;
        setLayoutResource(R.layout.sec_wifi_development_history_total_layout);
        LogRepository logRepository = LogRepository.LazyHolder.INSTANCE;
        Context context2 = getContext();
        ArrayList deviceLog = logRepository.getDeviceLog(context2);
        logRepository.mOnOffLog = new HashMap();
        logRepository.mConnectionLog = new HashMap();
        Iterator it = deviceLog.iterator();
        while (it.hasNext()) {
            HistoryLog historyLog = (HistoryLog) it.next();
            LogType logType = historyLog.type;
            LogType logType2 = LogType.DISCONNECTED;
            boolean equals = logType2.equals(logType);
            LogType logType3 = LogType.CONNECTED;
            LogType logType4 = historyLog.type;
            String str2 = ApnSettings.MVNO_NONE;
            if (equals
                    || logType3.equals(logType)
                    || LogType.ASSOC_REJECT.equals(logType)
                    || LogType.L2_CONNECT_FAIL.equals(logType)
                    || LogType.ROAM.equals(logType)) {
                String name = logType.name();
                HashMap hashMap = logRepository.mConnectionLog;
                if (!TextUtils.isEmpty(name)) {
                    if (logType3.equals(logType4) || logType2.equals(logType4)) {
                        new ArrayList();
                        String str3 = historyLog.data;
                        if (str3.contains("ssid=\"")) {
                            int indexOf = str3.indexOf("\"", str3.indexOf("ssid=\"")) + 1;
                            str2 = str3.substring(indexOf, str3.indexOf("\"", indexOf));
                        }
                        historyLog.targetSsid = str2;
                    }
                    if (hashMap.containsKey(name)) {
                        ArrayList arrayList = (ArrayList) hashMap.get(name);
                        arrayList.add(historyLog);
                        hashMap.put(name, arrayList);
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(historyLog);
                        hashMap.put(name, arrayList2);
                    }
                }
            } else {
                LogType logType5 = LogType.WIFI_ON_OFF;
                if (logType5.equals(logType)) {
                    HashMap hashMap2 = logRepository.mOnOffLog;
                    if (logType5.equals(logType4)) {
                        new ArrayList();
                        OnOffLogParser.OnOffLog parseOnOffLog =
                                OnOffLogParser.parseOnOffLog(historyLog);
                        if (!TextUtils.isEmpty(parseOnOffLog.packageName)) {
                            str2 = parseOnOffLog.packageName;
                        }
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        if (logType5.equals(logType4)) {
                            new ArrayList();
                            if (OnOffLogParser.parseOnOffLog(historyLog).wifiState.equals("1")) {
                                historyLog.wifiState = true;
                            } else {
                                historyLog.wifiState = false;
                            }
                        }
                        if (logType5.equals(logType4)) {
                            new ArrayList();
                            String str4 = OnOffLogParser.parseOnOffLog(historyLog).packageName;
                            try {
                                PackageManager packageManager =
                                        context2.getApplicationContext().getPackageManager();
                                str =
                                        (String)
                                                packageManager.getApplicationLabel(
                                                        packageManager.getApplicationInfo(
                                                                str4, 128));
                            } catch (PackageManager.NameNotFoundException unused) {
                                str = "Unknown";
                            }
                            historyLog.appName = str;
                        }
                        if (hashMap2.containsKey(str2)) {
                            ((ArrayList) hashMap2.get(str2)).add(historyLog);
                        } else {
                            ArrayList arrayList3 = new ArrayList();
                            arrayList3.add(historyLog);
                            hashMap2.put(str2, arrayList3);
                        }
                    }
                }
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        LogRepository logRepository = LogRepository.LazyHolder.INSTANCE;
        HashMap hashMap = new HashMap();
        hashMap.putAll(logRepository.mOnOffLog);
        hashMap.putAll(logRepository.mConnectionLog);
        if (hashMap.isEmpty()) {
            view.findViewById(R.id.total_history_log_data_recycler_view).setVisibility(8);
            return;
        }
        view.findViewById(R.id.total_history_notification_no_available_log).setVisibility(8);
        Context context = getContext();
        this.mHistoryLogDataRecyclerView =
                (RecyclerView) view.findViewById(R.id.total_history_log_data_recycler_view);
        HistoryLogDataAdapter historyLogDataAdapter = new HistoryLogDataAdapter();
        this.mHistoryLogDataAdapter = historyLogDataAdapter;
        HashMap hashMap2 = new HashMap();
        hashMap2.putAll(logRepository.mOnOffLog);
        hashMap2.putAll(logRepository.mConnectionLog);
        historyLogDataAdapter.mLogSet.clear();
        historyLogDataAdapter.mLogSet.putAll(hashMap2);
        historyLogDataAdapter.mLogSet.size();
        this.mHistoryLogDataRecyclerView.setAdapter(this.mHistoryLogDataAdapter);
        this.mHistoryLogDataRecyclerView.setLayoutManager(new LinearLayoutManager(1));
        this.mHistoryLogDataRecyclerView.semSetRoundedCorners(15);
        this.mHistoryLogDataRecyclerView.semSetRoundedCornerColor(
                15, context.getColor(R.color.sec_round_and_bgcolor));
        HistoryLogDataAdapter historyLogDataAdapter2 = this.mHistoryLogDataAdapter;
        historyLogDataAdapter2.mItemClickListener = new AnonymousClass1();
        historyLogDataAdapter2.mTargetLog.clear();
        Iterator it = historyLogDataAdapter2.mLogSet.values().iterator();
        while (it.hasNext()) {
            historyLogDataAdapter2.mTargetLog.addAll((ArrayList) it.next());
        }
        historyLogDataAdapter2.mTargetLog.sort(new HistoryLogDataAdapter.AnonymousClass1());
        historyLogDataAdapter2.notifyDataSetChanged();
    }
}
