package com.samsung.android.settings.wifi.develop.homewifi;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.BssidInfo;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BssidListAdapter extends RecyclerView.Adapter {
    public int mPrimaryTextColor;
    public int mPrimaryTextColorB;
    public int mPrimaryTextColorG;
    public int mPrimaryTextColorR;
    public final HashMap mSearchHelper = new HashMap();
    public final ArrayList mData = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView band;
        public final TextView bssid;
        public final View bssidLayout;
        public final View circle;
        public final TextView rssi;
        public final TextView ssid;
        public final View ssidLayout;

        public ViewHolder(View view) {
            super(view);
            this.ssidLayout = view.findViewById(R.id.ssid_layout);
            this.ssid = (TextView) view.findViewById(R.id.ssid);
            this.bssidLayout = view.findViewById(R.id.bssid_layout);
            this.circle = view.findViewById(R.id.circle);
            this.bssid = (TextView) view.findViewById(R.id.bssid);
            this.band = (TextView) view.findViewById(R.id.band);
            this.rssi = (TextView) view.findViewById(R.id.rssi);
        }
    }

    public BssidListAdapter(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) it.next();
            if (arrayList2.size() == 0
                    || !((String) AlertController$$ExternalSyntheticOutline0.m(1, arrayList2))
                            .equals(extendedBssidInfo.ssid)) {
                arrayList2.add(extendedBssidInfo.ssid);
            }
        }
        Iterator it2 = arrayList.iterator();
        int i = 0;
        while (it2.hasNext()) {
            ExtendedBssidInfo extendedBssidInfo2 = (ExtendedBssidInfo) it2.next();
            if (i == 0
                    || (i < arrayList2.size()
                            && !extendedBssidInfo2.ssid.equals(arrayList2.get(i - 1)))) {
                this.mData.add(new Data((String) arrayList2.get(i)));
                i++;
            }
            this.mSearchHelper.put(extendedBssidInfo2.bssid, Integer.valueOf(this.mData.size()));
            this.mData.add(new Data(extendedBssidInfo2));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mData.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        Data data = (Data) this.mData.get(i);
        if (data.ssid.length() > 0) {
            viewHolder2.ssidLayout.setVisibility(0);
            viewHolder2.bssidLayout.setVisibility(8);
            viewHolder2.ssid.setText(SemWifiUtils.removeDoubleQuotes(data.ssid));
            return;
        }
        viewHolder2.ssidLayout.setVisibility(8);
        viewHolder2.bssidLayout.setVisibility(0);
        BssidInfo bssidInfo = data.bssid;
        if (bssidInfo != null) {
            viewHolder2.bssid.setText(bssidInfo.bssid);
            TextView textView = viewHolder2.band;
            int band = Repository.toBand(data.bssid.freq);
            textView.setText(
                    band != 1
                            ? band != 2 ? band != 8 ? ApnSettings.MVNO_NONE : "6G" : "5G"
                            : "2.4G");
            int i2 = data.bssid.rssi;
            if (i2 <= -99) {
                viewHolder2.rssi.setText("---");
            } else {
                TextView textView2 = viewHolder2.rssi;
                Locale locale = Locale.US;
                StringBuilder sb = new StringBuilder();
                sb.append(i2);
                textView2.setText(sb.toString());
            }
            TextView textView3 = viewHolder2.rssi;
            int i3 = data.bssid.rssi;
            textView3.setTextColor(i3 <= -70 ? Color.rgb(255, 0, 0) : this.mPrimaryTextColor);
            textView3.setTypeface(null, i3 <= -70 ? 1 : 0);
            viewHolder2.circle.setBackgroundTintList(ColorStateList.valueOf(data.bssidColor));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_wifi_development_homewifi_bssid_item,
                        viewGroup,
                        false));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Data {
        public BssidInfo bssid;
        public int bssidColor;
        public final String ssid;

        public Data(String str) {
            this.ssid = str;
        }

        public Data(ExtendedBssidInfo extendedBssidInfo) {
            this.ssid = ApnSettings.MVNO_NONE;
            this.bssid = extendedBssidInfo;
        }
    }
}
