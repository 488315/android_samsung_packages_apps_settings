package com.samsung.android.settings.wifi;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.wifi.SemEasySetupWifiScanSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class EasySetupListAdapter extends WifiListAdapter {
    public final WifiPickerTracker mWifiPickerTracker;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EasySetupViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIcon;
        public final TextView mSummary;
        public final TextView mTitle;
        public final View mView;

        public EasySetupViewHolder(EasySetupListAdapter easySetupListAdapter, View view) {
            super(view);
            this.mView = view;
            this.mIcon = (ImageView) view.findViewById(R.id.wifi_icon);
            this.mTitle = (TextView) view.findViewById(R.id.title);
            this.mSummary = (TextView) view.findViewById(R.id.summary);
        }
    }

    public EasySetupListAdapter(
            Context context,
            RecyclerView recyclerView,
            ArrayList arrayList,
            boolean z,
            String str,
            WifiPickerTracker wifiPickerTracker) {
        super(context, recyclerView, arrayList, z, str, false);
        this.mWifiPickerTracker = wifiPickerTracker;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mWifiEntries;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final String getLogTag() {
        return "WifiList.EasySetup";
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (i < 0 || i > this.mWifiEntries.size()) {
            Log.d("WifiList.EasySetup", "onBindViewHolder failed - invalid index");
            return;
        }
        final WifiEntry wifiEntry = (WifiEntry) this.mWifiEntries.get(i);
        if (wifiEntry == null) {
            Log.d("WifiList.EasySetup", "onBindViewHolder failed - null WifiEntry");
            return;
        }
        if (WifiListAdapter.DBG) {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i, "onBindView Connected AP : ", " ");
            m.append(wifiEntry.getTitle());
            m.append(" ");
            m.append(wifiEntry.getSummary(true));
            Log.d("WifiList.EasySetup", m.toString());
        }
        EasySetupViewHolder easySetupViewHolder = (EasySetupViewHolder) viewHolder;
        easySetupViewHolder.mIcon.setImageDrawable(
                this.mContext.getDrawable(R.drawable.sec_ic_smart_things));
        easySetupViewHolder.mTitle.setText(wifiEntry.getTitle());
        easySetupViewHolder.mSummary.setVisibility(8);
        easySetupViewHolder.mView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.EasySetupListAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EasySetupListAdapter easySetupListAdapter = EasySetupListAdapter.this;
                        WifiEntry wifiEntry2 = wifiEntry;
                        LoggingHelper.insertEventLogging(easySetupListAdapter.mSAScreenId, 3138);
                        List list =
                                easySetupListAdapter.mWifiPickerTracker.mSemEasySetupScanSettings;
                        if (list == null) {
                            list = new ArrayList();
                        }
                        String ssid = wifiEntry2.getSsid();
                        SemEasySetupWifiScanSettings semEasySetupWifiScanSettings = null;
                        if (ssid != null) {
                            Iterator it = list.iterator();
                            loop0:
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                SemEasySetupWifiScanSettings semEasySetupWifiScanSettings2 =
                                        (SemEasySetupWifiScanSettings) it.next();
                                if (semEasySetupWifiScanSettings2 != null) {
                                    Iterator it2 =
                                            semEasySetupWifiScanSettings2.ssidPatterns.iterator();
                                    while (it2.hasNext()) {
                                        if (ssid.matches((String) it2.next())) {
                                            semEasySetupWifiScanSettings =
                                                    semEasySetupWifiScanSettings2;
                                            break loop0;
                                        }
                                    }
                                }
                            }
                        }
                        if (semEasySetupWifiScanSettings == null) {
                            Log.d(
                                    "WifiList.EasySetup",
                                    "Failed to start - cannot find matched setting");
                            return;
                        }
                        PendingIntent pendingIntent =
                                semEasySetupWifiScanSettings.pendingIntentForSettings;
                        if (pendingIntent == null) {
                            Log.d("WifiList.EasySetup", "Failed to start - pendingIntent is null");
                            return;
                        }
                        Intent intent = pendingIntent.getIntent();
                        intent.putExtra("ssid", wifiEntry2.getSsid());
                        intent.putExtra("sender", "GLOBAL_WIFI_SETTINGS");
                        try {
                            pendingIntent.send(
                                    easySetupListAdapter.mContext, 0, intent, null, null);
                        } catch (PendingIntent.CanceledException unused) {
                            Log.w(
                                    "WifiList.EasySetup",
                                    "Pending intent " + pendingIntent + " canceled");
                        }
                    }
                });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new EasySetupViewHolder(
                this,
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_wifi_list_preference,
                                (ViewGroup) this.mParentView,
                                false));
    }

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final void notifyOnLaunchActivityFinishedIfNeeded() {}

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final void notifyOnLaunchActivityIfNeeded() {}
}
