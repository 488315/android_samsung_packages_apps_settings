package com.samsung.android.settings.wifi.develop.nearbywifi.adapter;

import android.net.wifi.ScanResult;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.BssidInfo;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.MloLink;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.MultiLink;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Rnr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BssidListAdapter extends RecyclerView.Adapter {
    public List mBssidList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NearbyWifiViewHolder extends RecyclerView.ViewHolder {
        public final TextView bssid;
        public final TextView ch;
        public final TextView cu;
        public final TextView extra;
        public final TextView freq;
        public final TextView mlo;
        public final View mloLayout;
        public final TextView rssi;
        public final TextView security;
        public final TextView sta;

        public NearbyWifiViewHolder(View view) {
            super(view);
            this.bssid = (TextView) view.findViewById(R.id.recycle_bssid);
            this.freq = (TextView) view.findViewById(R.id.recycle_freq);
            this.ch = (TextView) view.findViewById(R.id.recycle_ch);
            this.rssi = (TextView) view.findViewById(R.id.recycle_rssi);
            this.cu = (TextView) view.findViewById(R.id.recycle_cu);
            this.sta = (TextView) view.findViewById(R.id.recycle_sta);
            this.security = (TextView) view.findViewById(R.id.recycle_security);
            this.extra = (TextView) view.findViewById(R.id.recycle_extra);
            this.mloLayout = view.findViewById(R.id.mlo_layout);
            this.mlo = (TextView) view.findViewById(R.id.mlo);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mBssidList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return -1L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String str;
        String str2;
        String sb;
        NearbyWifiViewHolder nearbyWifiViewHolder = (NearbyWifiViewHolder) viewHolder;
        BssidInfo bssidInfo = (BssidInfo) this.mBssidList.get(i);
        nearbyWifiViewHolder.bssid.setText(bssidInfo.bssid);
        TextView textView = nearbyWifiViewHolder.freq;
        Locale locale = Locale.US;
        StringBuilder sb2 = new StringBuilder();
        int i2 = bssidInfo.freq;
        sb2.append(i2);
        textView.setText(sb2.toString());
        TextView textView2 = nearbyWifiViewHolder.ch;
        int convertFrequencyMhzToChannelIfSupported =
                ScanResult.convertFrequencyMhzToChannelIfSupported(i2);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(convertFrequencyMhzToChannelIfSupported);
        textView2.setText(sb3.toString());
        TextView textView3 = nearbyWifiViewHolder.rssi;
        int i3 = bssidInfo.rssi;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(i3);
        textView3.setText(sb4.toString());
        TextView textView4 = nearbyWifiViewHolder.cu;
        int i4 = bssidInfo.cu;
        String str3 = "-";
        if (i4 == -1) {
            str = "-";
        } else {
            str = i4 + "%";
        }
        textView4.setText(str);
        TextView textView5 = nearbyWifiViewHolder.sta;
        int i5 = bssidInfo.sta;
        if (i5 != -1) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(i5);
            str3 = sb5.toString();
        }
        textView5.setText(str3);
        nearbyWifiViewHolder.security.setText(bssidInfo.security);
        TextView textView6 = nearbyWifiViewHolder.extra;
        StringBuilder sb6 = new StringBuilder("Wi-Fi standard: ");
        int i6 = bssidInfo.wifiStandard;
        if (i6 != 1) {
            switch (i6) {
                case 4:
                    str2 = "11n";
                    break;
                case 5:
                    str2 = "11ac";
                    break;
                case 6:
                    str2 = "11ax";
                    break;
                case 7:
                    str2 = "11ad";
                    break;
                case 8:
                    str2 = "11be";
                    break;
                default:
                    str2 = "unknown";
                    break;
            }
        } else {
            str2 = "legacy";
        }
        sb6.append(str2);
        sb6.append(", bandwidth: ");
        sb6.append(bssidInfo.channelWidth);
        sb6.append("MHz\n");
        sb6.append(
                String.format(
                        locale,
                        "11e(%c) 11k(%c) 11v(%c) 11r(%c) 11w(%s)",
                        Character.valueOf(bssidInfo.extra11e ? 'O' : 'X'),
                        Character.valueOf(bssidInfo.extra11k ? 'O' : 'X'),
                        Character.valueOf(bssidInfo.extra11v ? 'O' : 'X'),
                        Character.valueOf(bssidInfo.extra11r ? 'O' : 'X'),
                        bssidInfo.pmfRequired
                                ? "Required"
                                : bssidInfo.pmfCapable ? "Capable" : "X"));
        textView6.setText(sb6.toString());
        MultiLink multiLink = bssidInfo.multiLink;
        if (multiLink == null || !multiLink.mPresent) {
            nearbyWifiViewHolder.mloLayout.setVisibility(8);
            return;
        }
        nearbyWifiViewHolder.mloLayout.setVisibility(0);
        TextView textView7 = nearbyWifiViewHolder.mlo;
        if (bssidInfo.multiLink == null) {
            sb = ApnSettings.MVNO_NONE;
        } else {
            StringBuilder sb7 = new StringBuilder("MLD Address: ");
            sb7.append(bssidInfo.multiLink.mMldMacAddress);
            sb7.append("\n");
            MultiLink multiLink2 = bssidInfo.multiLink;
            StringBuilder sb8 = new StringBuilder("Features: ");
            sb8.append((multiLink2.mEmlCapabilities & 1) != 0 ? "EMLSR(O) " : "EMLSR(X) ");
            short s = multiLink2.mMldCapabilities;
            sb8.append(
                    ((s & 64) != 0 ? "T2LM(O) " : "T2LM(X) ")
                            + "SimultaneousLinks("
                            + (s & 15)
                            + ") ");
            sb7.append(sb8.toString());
            ArrayList arrayList = new ArrayList();
            MultiLink multiLink3 = bssidInfo.multiLink;
            if (multiLink3 != null && multiLink3.mPresent) {
                arrayList.addAll(multiLink3.mAffiliatedLinks);
            }
            Rnr rnr = bssidInfo.rnr;
            if (rnr != null && rnr.mPresent) {
                arrayList.addAll(rnr.mAffiliatedMloLinks);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                MloLink mloLink = (MloLink) it.next();
                sb7.append("\nID(");
                sb7.append(mloLink.linkId);
                sb7.append(") ");
                sb7.append(mloLink.bssid);
                sb7.append(" freq=");
                sb7.append(mloLink.freq);
            }
            sb = sb7.toString();
        }
        textView7.setText(sb);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NearbyWifiViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_wifi_development_nearbywifi_bssid_item,
                        viewGroup,
                        false));
    }
}
