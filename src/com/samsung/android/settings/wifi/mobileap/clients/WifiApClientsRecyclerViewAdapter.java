package com.samsung.android.settings.wifi.mobileap.clients;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApClientConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApClientsRecyclerViewAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public int mSummaryColor;
    public List mTopHotspotWifiApClientConfigList;
    public int mWarningColor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ClientsViewHolder extends RecyclerView.ViewHolder {
        public final ImageView connectedIconImageView;
        public final TextView dataUsageTextView;
        public final TextView deviceNameTextView;
        public final ImageView progressBarImageView;

        public ClientsViewHolder(View view) {
            super(view);
            this.deviceNameTextView = (TextView) view.findViewById(R.id.title);
            this.dataUsageTextView = (TextView) view.findViewById(R.id.summary);
            this.progressBarImageView = (ImageView) view.findViewById(R.id.icon);
            this.connectedIconImageView =
                    (ImageView)
                            view.findViewById(com.android.settings.R.id.connected_icon_imageview);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mTopHotspotWifiApClientConfigList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int i2;
        ClientsViewHolder clientsViewHolder = (ClientsViewHolder) viewHolder;
        WifiApClientConfig wifiApClientConfig =
                (WifiApClientConfig) this.mTopHotspotWifiApClientConfigList.get(i);
        clientsViewHolder.progressBarImageView.setColorFilter(
                wifiApClientConfig.mProgressbarColorId);
        clientsViewHolder.progressBarImageView.setVisibility(0);
        clientsViewHolder.deviceNameTextView.setText(wifiApClientConfig.mDeviceName);
        clientsViewHolder.deviceNameTextView.setVisibility(0);
        if (this.mContext.getResources().getConfiguration().orientation == 1) {
            clientsViewHolder.deviceNameTextView.setMaxWidth(
                    WifiApSettingsUtils.convertDpToPixel(this.mContext, 155));
        }
        clientsViewHolder.dataUsageTextView.setText(
                new WifiApDataUsageConfig(wifiApClientConfig.mDataUsageForTodayInBytes)
                        .getUsageValueInLocaleString(this.mContext));
        clientsViewHolder.dataUsageTextView.setVisibility(0);
        boolean isClientConnected = wifiApClientConfig.isClientConnected();
        int i3 = this.mSummaryColor;
        if (!isClientConnected) {
            clientsViewHolder.dataUsageTextView.setTextColor(i3);
            clientsViewHolder.connectedIconImageView.setVisibility(8);
            return;
        }
        if (wifiApClientConfig.mDataLimitForEachDayInBytes > 0) {
            i2 =
                    (int)
                            ((new WifiApDataUsageConfig(
                                                            wifiApClientConfig
                                                                    .mDataUsageForTodayInBytes)
                                                    .getUsageValueInMB()
                                            / new WifiApDataUsageConfig(
                                                            wifiApClientConfig
                                                                    .mDataLimitForEachDayInBytes)
                                                    .getUsageValueInMB())
                                    * 100.0d);
        } else {
            i2 = 0;
        }
        clientsViewHolder.connectedIconImageView.setVisibility(0);
        boolean z = wifiApClientConfig.mIsDataPausedByDataLimitCondition;
        int i4 = this.mWarningColor;
        if (z || i2 >= 100) {
            clientsViewHolder.dataUsageTextView.setTextColor(i4);
            SemLog.i(
                    "WifiApClientsRecyclerViewAdapter",
                    "Data limit reached (Default Summary) for : " + wifiApClientConfig.mDeviceName);
            return;
        }
        if (wifiApClientConfig.mIsDataPausedByTimeLimitCondition) {
            clientsViewHolder.dataUsageTextView.setTextColor(i4);
            SemLog.i(
                    "WifiApClientsRecyclerViewAdapter",
                    "Time limit reached (Default Summary) for : " + wifiApClientConfig.mDeviceName);
            return;
        }
        if (i2 < 90) {
            clientsViewHolder.dataUsageTextView.setTextColor(i3);
            return;
        }
        clientsViewHolder.dataUsageTextView.setTextColor(i4);
        SemLog.i(
                "WifiApClientsRecyclerViewAdapter",
                "Data limit almost reached (Default Summary) for : "
                        + wifiApClientConfig.mDeviceName);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ClientsViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        com.android.settings.R.layout.sec_wifi_ap_clients_list_view_holder,
                        viewGroup,
                        false));
    }
}
