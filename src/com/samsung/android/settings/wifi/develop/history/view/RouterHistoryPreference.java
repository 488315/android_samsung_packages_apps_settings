package com.samsung.android.settings.wifi.develop.history.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.history.HistoryLogDataAdapter;
import com.samsung.android.settings.wifi.develop.history.model.ConnectivityLogParser;
import com.samsung.android.settings.wifi.develop.history.model.HistoryLog;
import com.samsung.android.settings.wifi.develop.history.model.LogRepository;
import com.samsung.android.settings.wifi.develop.history.model.LogType;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RouterHistoryPreference extends Preference {
    public TextView lastConnectedTime;
    public HistoryLogDataAdapter mHistoryLogDataAdapter;
    public RecyclerView mHistoryLogDataRecyclerView;
    public String mSelectedSsid;
    public Spinner mSpinner;
    public TextView securityAkm;
    public TextView securityGroup;
    public TextView securityPairwise;
    public LinearLayout securitySummary;
    public TextView status;

    public RouterHistoryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_wifi_development_history_router_layout);
        LogRepository.LazyHolder.INSTANCE.updateRouterHistoryLog(getContext());
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        LogRepository logRepository = LogRepository.LazyHolder.INSTANCE;
        if (logRepository.mLogBySsid.isEmpty()) {
            view.findViewById(R.id.router_spinner).setVisibility(8);
            view.findViewById(R.id.history_log_summary_status).setVisibility(8);
            view.findViewById(R.id.history_log_summary_last_connected_time).setVisibility(8);
            view.findViewById(R.id.history_log_summary_security).setVisibility(8);
            view.findViewById(R.id.log_data_recycler_view).setVisibility(8);
            return;
        }
        view.findViewById(R.id.notification_no_available_log).setVisibility(8);
        Context context = getContext();
        this.mSpinner = (Spinner) view.findViewById(R.id.router_spinner);
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(
                        context,
                        android.R.layout.simple_spinner_item,
                        (String[])
                                logRepository
                                        .mLogBySsid
                                        .keySet()
                                        .toArray(new String[logRepository.mLogBySsid.size()]));
        arrayAdapter.setDropDownViewResource(R.layout.sec_simple_spinner_dropdown_item);
        this.mSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.mSpinner.setOnItemSelectedListener(
                new AdapterView
                        .OnItemSelectedListener() { // from class:
                                                    // com.samsung.android.settings.wifi.develop.history.view.RouterHistoryPreference.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view2, int i, long j) {
                        String str;
                        String str2;
                        String str3;
                        RouterHistoryPreference routerHistoryPreference =
                                RouterHistoryPreference.this;
                        String obj = routerHistoryPreference.mSpinner.getSelectedItem().toString();
                        HistoryLogDataAdapter historyLogDataAdapter =
                                routerHistoryPreference.mHistoryLogDataAdapter;
                        historyLogDataAdapter.mTargetLog =
                                (ArrayList) historyLogDataAdapter.mLogSet.get(obj);
                        historyLogDataAdapter.notifyDataSetChanged();
                        LogRepository logRepository2 = LogRepository.LazyHolder.INSTANCE;
                        routerHistoryPreference.status.setText(
                                "Status: "
                                        .concat(
                                                logRepository2.mLastConnectedSsid.equals(obj)
                                                        ? "Connected"
                                                        : "Disconnected"));
                        ArrayList arrayList = (ArrayList) logRepository2.mLogBySsid.get(obj);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        if (!arrayList.isEmpty()) {
                            routerHistoryPreference.lastConnectedTime.setText(
                                    "Last connected time: "
                                            + ((HistoryLog)
                                                            AlertController$$ExternalSyntheticOutline0
                                                                    .m(1, arrayList))
                                                    .time.format(
                                                            DateTimeFormatter.ofPattern(
                                                                    "yyyy-MM-dd HH:mm:ss")));
                        }
                        if (logRepository2.mSupportConnectivityLog) {
                            Iterator it =
                                    ((ArrayList) logRepository2.mLogBySsid.get(obj)).iterator();
                            while (it.hasNext()) {
                                HistoryLog historyLog = (HistoryLog) it.next();
                                if (LogType.CONNECTING.equals(historyLog.type)) {
                                    new ArrayList();
                                    ConnectivityLogParser.ConnectingLog parseConnectingLog =
                                            ConnectivityLogParser.parseConnectingLog(historyLog);
                                    str2 =
                                            LogRepository.convertCipher(
                                                    parseConnectingLog.pairwiseCipher);
                                    str3 =
                                            LogRepository.convertCipher(
                                                    parseConnectingLog.groupCipher);
                                    str =
                                            LogRepository.convertAkmSuite(
                                                    parseConnectingLog.akmSuite);
                                    break;
                                }
                            }
                        }
                        str = ApnSettings.MVNO_NONE;
                        str2 = str;
                        str3 = str2;
                        if (ApnSettings.MVNO_NONE.equals(str2)
                                || ApnSettings.MVNO_NONE.equals(str3)
                                || ApnSettings.MVNO_NONE.equals(str)) {
                            routerHistoryPreference.securitySummary.setVisibility(8);
                            return;
                        }
                        routerHistoryPreference.securitySummary.setVisibility(0);
                        routerHistoryPreference.securityPairwise.setText(
                                "pairwise cipher: ".concat(str2));
                        routerHistoryPreference.securityGroup.setText(
                                "group cipher: ".concat(str3));
                        routerHistoryPreference.securityAkm.setText("akm suite: ".concat(str));
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {}
                });
        this.mSpinner.semSetRoundedCorners(15);
        this.mSpinner.semSetRoundedCornerColor(15, context.getColor(R.color.sec_round_and_bgcolor));
        this.securitySummary = (LinearLayout) view.findViewById(R.id.history_log_summary_security);
        this.status = (TextView) view.findViewById(R.id.history_log_summary_status);
        this.lastConnectedTime =
                (TextView) view.findViewById(R.id.history_log_summary_last_connected_time);
        this.securityPairwise = (TextView) view.findViewById(R.id.router_security_pairwise);
        this.securityGroup = (TextView) view.findViewById(R.id.router_security_group);
        this.securityAkm = (TextView) view.findViewById(R.id.router_security_akm);
        Context context2 = getContext();
        this.mHistoryLogDataRecyclerView =
                (RecyclerView) view.findViewById(R.id.log_data_recycler_view);
        HistoryLogDataAdapter historyLogDataAdapter = new HistoryLogDataAdapter();
        this.mHistoryLogDataAdapter = historyLogDataAdapter;
        HashMap hashMap = logRepository.mLogBySsid;
        historyLogDataAdapter.mLogSet.clear();
        historyLogDataAdapter.mLogSet.putAll(hashMap);
        historyLogDataAdapter.mLogSet.size();
        this.mHistoryLogDataRecyclerView.setAdapter(this.mHistoryLogDataAdapter);
        this.mHistoryLogDataRecyclerView.setLayoutManager(new LinearLayoutManager(1));
        this.mHistoryLogDataRecyclerView.semSetRoundedCorners(15);
        this.mHistoryLogDataRecyclerView.semSetRoundedCornerColor(
                15, context2.getColor(R.color.sec_round_and_bgcolor));
        String str = this.mSelectedSsid;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int count = this.mSpinner.getCount();
        for (int i = 0; i < count; i++) {
            if (this.mSpinner.getItemAtPosition(i).toString().startsWith(str)) {
                this.mSpinner.setSelection(i);
            }
        }
    }
}
