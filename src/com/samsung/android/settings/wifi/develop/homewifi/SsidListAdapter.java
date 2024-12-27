package com.samsung.android.settings.wifi.develop.homewifi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.BssidInfo;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.SsidInfo;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SsidListAdapter extends RecyclerView.Adapter {
    public final HashMap mSsidSearchHelper = new HashMap();
    public final ArrayList mData = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Data {
        public final ArrayList bssids;
        public boolean expanded;
        public boolean selected;
        public final String ssid;

        public Data(SsidInfo ssidInfo) {
            ArrayList arrayList = new ArrayList();
            this.bssids = arrayList;
            this.ssid = ssidInfo.ssid;
            arrayList.addAll(ssidInfo.bssids);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView band;
        public final View baseLayout;
        public final TextView bssid;
        public final View bssidLayout;
        public Data data;
        public final TextView rssi;
        public final CheckBox selected;
        public final TextView ssid;
        public final ImageView toggleBssid;

        public ViewHolder(View view) {
            super(view);
            this.baseLayout = view.findViewById(R.id.base_layout);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.selected);
            this.selected = checkBox;
            TextView textView = (TextView) view.findViewById(R.id.ssid);
            this.ssid = textView;
            ImageView imageView = (ImageView) view.findViewById(R.id.expand_bssid);
            this.toggleBssid = imageView;
            this.bssidLayout = view.findViewById(R.id.bssid_layout);
            this.bssid = (TextView) view.findViewById(R.id.bssid);
            this.band = (TextView) view.findViewById(R.id.band);
            this.rssi = (TextView) view.findViewById(R.id.rssi);
            checkBox.setOnCheckedChangeListener(
                    new CompoundButton
                            .OnCheckedChangeListener() { // from class:
                                                         // com.samsung.android.settings.wifi.develop.homewifi.SsidListAdapter$ViewHolder$$ExternalSyntheticLambda0
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(
                                CompoundButton compoundButton, boolean z) {
                            SsidListAdapter.ViewHolder viewHolder = SsidListAdapter.ViewHolder.this;
                            viewHolder.data.selected = z;
                            viewHolder.setBssidExpansion(z);
                        }
                    });
            final int i = 0;
            imageView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.develop.homewifi.SsidListAdapter$ViewHolder$$ExternalSyntheticLambda1
                        public final /* synthetic */ SsidListAdapter.ViewHolder f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            int i2 = i;
                            SsidListAdapter.ViewHolder viewHolder = this.f$0;
                            switch (i2) {
                                case 0:
                                    viewHolder.setBssidExpansion(!viewHolder.data.expanded);
                                    break;
                                default:
                                    viewHolder.selected.setChecked(!r0.isChecked());
                                    break;
                            }
                        }
                    });
            textView.setClickable(true);
            final int i2 = 1;
            textView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.develop.homewifi.SsidListAdapter$ViewHolder$$ExternalSyntheticLambda1
                        public final /* synthetic */ SsidListAdapter.ViewHolder f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            int i22 = i2;
                            SsidListAdapter.ViewHolder viewHolder = this.f$0;
                            switch (i22) {
                                case 0:
                                    viewHolder.setBssidExpansion(!viewHolder.data.expanded);
                                    break;
                                default:
                                    viewHolder.selected.setChecked(!r0.isChecked());
                                    break;
                            }
                        }
                    });
        }

        public final void setBssidExpansion(boolean z) {
            this.data.expanded = z;
            if (!z) {
                this.toggleBssid.animate().rotation(0.0f).setDuration(200L);
                this.bssidLayout.setVisibility(8);
            } else {
                this.toggleBssid.animate().rotation(180.0f).setDuration(200L);
                this.bssidLayout.setVisibility(0);
                this.bssidLayout.setAlpha(0.0f);
                this.bssidLayout.animate().alpha(1.0f).setDuration(200L);
            }
        }
    }

    public SsidListAdapter(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SsidInfo ssidInfo = (SsidInfo) it.next();
            this.mSsidSearchHelper.put(ssidInfo.ssid, Integer.valueOf(this.mData.size()));
            this.mData.add(new Data(ssidInfo));
        }
        SemLog.i("HomeWifi.SsidListAdapter", "Initial # of SSIDs: " + this.mData.size());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mData.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        Data data = (Data) this.mData.get(i);
        viewHolder2.data = data;
        viewHolder2.ssid.setText(SemWifiUtils.removeDoubleQuotes(data.ssid));
        TextView textView = viewHolder2.bssid;
        ArrayList arrayList = data.bssids;
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BssidInfo bssidInfo = (BssidInfo) it.next();
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(bssidInfo.bssid);
        }
        textView.setText(sb.toString());
        TextView textView2 = viewHolder2.band;
        ArrayList arrayList2 = data.bssids;
        StringBuilder sb2 = new StringBuilder();
        Iterator it2 = arrayList2.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            BssidInfo bssidInfo2 = (BssidInfo) it2.next();
            if (sb2.length() > 0) {
                sb2.append("\n");
            }
            int band = Repository.toBand(bssidInfo2.freq);
            sb2.append(
                    band != 1
                            ? band != 2 ? band != 8 ? ApnSettings.MVNO_NONE : "6G" : "5G"
                            : "2.4G");
        }
        textView2.setText(sb2.toString());
        TextView textView3 = viewHolder2.rssi;
        ArrayList arrayList3 = data.bssids;
        StringBuilder sb3 = new StringBuilder();
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            BssidInfo bssidInfo3 = (BssidInfo) it3.next();
            if (sb3.length() > 0) {
                sb3.append("\n");
            }
            sb3.append(bssidInfo3.rssi);
        }
        textView3.setText(sb3.toString());
        viewHolder2.bssidLayout.setVisibility(data.expanded ? 0 : 8);
        viewHolder2.selected.setChecked(data.selected);
        viewHolder2.toggleBssid.animate().rotation(data.expanded ? 180.0f : 0.0f).setDuration(0L);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_wifi_development_homewifi_ssid_item,
                        viewGroup,
                        false));
    }
}
