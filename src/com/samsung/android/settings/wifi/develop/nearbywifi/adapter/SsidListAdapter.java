package com.samsung.android.settings.wifi.develop.nearbywifi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.settings.wifi.develop.nearbywifi.model.SsidInfo;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.SsidInfo$$ExternalSyntheticLambda0;
import com.samsung.android.settings.wifi.develop.nearbywifi.view.NearbyWifiPreference;
import com.samsung.android.settings.wifi.develop.nearbywifi.view.NearbyWifiPreference$$ExternalSyntheticLambda0;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SsidListAdapter extends RecyclerView.Adapter {
    public NearbyWifiPreference$$ExternalSyntheticLambda0 mListener;
    public ArrayList mSsidList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NearbyWifiViewHolder extends RecyclerView.ViewHolder {
        public final TextView[] bands;
        public final TextView bestRssi;
        public final TextView countBssid;
        public final TextView ssid;

        public NearbyWifiViewHolder(View view) {
            super(view);
            this.bands =
                    new TextView[] {
                        (TextView) view.findViewById(R.id.recycle_band2),
                        (TextView) view.findViewById(R.id.recycle_band5),
                        (TextView) view.findViewById(R.id.recycle_band6)
                    };
            this.ssid = (TextView) view.findViewById(R.id.recycle_ssid);
            this.countBssid = (TextView) view.findViewById(R.id.recycle_count_bssid);
            this.bestRssi = (TextView) view.findViewById(R.id.recycle_best_rssi);
            view.findViewById(R.id.recycle_layout)
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.wifi.develop.nearbywifi.adapter.SsidListAdapter$NearbyWifiViewHolder$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    int bindingAdapterPosition;
                                    SsidListAdapter.NearbyWifiViewHolder nearbyWifiViewHolder =
                                            SsidListAdapter.NearbyWifiViewHolder.this;
                                    SsidListAdapter ssidListAdapter = SsidListAdapter.this;
                                    if (ssidListAdapter.mListener == null
                                            || (bindingAdapterPosition =
                                                            nearbyWifiViewHolder
                                                                    .getBindingAdapterPosition())
                                                    == -1
                                            || bindingAdapterPosition
                                                    >= ssidListAdapter.mSsidList.size()) {
                                        return;
                                    }
                                    NearbyWifiPreference$$ExternalSyntheticLambda0
                                            nearbyWifiPreference$$ExternalSyntheticLambda0 =
                                                    ssidListAdapter.mListener;
                                    SsidInfo ssidInfo =
                                            (SsidInfo)
                                                    ssidListAdapter.mSsidList.get(
                                                            bindingAdapterPosition);
                                    NearbyWifiPreference nearbyWifiPreference =
                                            nearbyWifiPreference$$ExternalSyntheticLambda0.f$0;
                                    if (nearbyWifiPreference.mSsidTitle == null
                                            || nearbyWifiPreference.mBssidTitle == null) {
                                        return;
                                    }
                                    nearbyWifiPreference.mSelectedSsid = ssidInfo.ssid;
                                    nearbyWifiPreference.changeMode();
                                }
                            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mSsidList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return -1L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        NearbyWifiViewHolder nearbyWifiViewHolder = (NearbyWifiViewHolder) viewHolder;
        SsidInfo ssidInfo = (SsidInfo) this.mSsidList.get(i);
        nearbyWifiViewHolder.ssid.setText(SemWifiUtils.removeDoubleQuotes(ssidInfo.ssid));
        TextView textView = nearbyWifiViewHolder.countBssid;
        Locale locale = Locale.US;
        int size = ssidInfo.bssids.size();
        StringBuilder sb = new StringBuilder();
        sb.append(size);
        textView.setText(sb.toString());
        TextView textView2 = nearbyWifiViewHolder.bestRssi;
        int orElse =
                ssidInfo.bssids.stream()
                        .mapToInt(new SsidInfo$$ExternalSyntheticLambda0())
                        .max()
                        .orElse(0);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(orElse);
        textView2.setText(sb2.toString());
        if (ssidInfo.existBand24) {
            nearbyWifiViewHolder.bands[0].setVisibility(0);
        } else {
            nearbyWifiViewHolder.bands[0].setVisibility(8);
        }
        if (ssidInfo.existBand5) {
            nearbyWifiViewHolder.bands[1].setVisibility(0);
        } else {
            nearbyWifiViewHolder.bands[1].setVisibility(8);
        }
        if (ssidInfo.existBand6) {
            nearbyWifiViewHolder.bands[2].setVisibility(0);
        } else {
            nearbyWifiViewHolder.bands[2].setVisibility(8);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NearbyWifiViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_wifi_development_nearbywifi_ssid_item,
                        viewGroup,
                        false));
    }
}
