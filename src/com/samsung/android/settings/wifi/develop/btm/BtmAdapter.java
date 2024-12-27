package com.samsung.android.settings.wifi.develop.btm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.wifi.SemWifiConfiguration;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BtmAdapter extends RecyclerView.Adapter {
    public final List mConfigs = new ArrayList();
    public final Context mContext;

    public BtmAdapter(Context context) {
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return ((ArrayList) this.mConfigs).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BtmViewHolder btmViewHolder = (BtmViewHolder) viewHolder;
        SemWifiConfiguration semWifiConfiguration =
                (SemWifiConfiguration) ((ArrayList) this.mConfigs).get(i);
        String str = semWifiConfiguration.configKey;
        btmViewHolder.mConfigKey = str;
        btmViewHolder.configKey.setText(str.substring(1, str.lastIndexOf("\"")));
        if (semWifiConfiguration.personalizedConnectionOption == 3) {
            btmViewHolder.reason.setText("BTM option changed");
            btmViewHolder.btmSwitch.setChecked(true);
        } else {
            btmViewHolder.reason.setText("Default BTM option");
            btmViewHolder.btmSwitch.setChecked(false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new BtmViewHolder(
                this.mContext,
                LayoutInflater.from(this.mContext)
                        .inflate(R.layout.sec_wifi_development_btm_item, viewGroup, false));
    }
}
