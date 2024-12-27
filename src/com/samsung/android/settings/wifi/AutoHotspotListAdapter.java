package com.samsung.android.settings.wifi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.core.text.PrecomputedTextCompat$Params$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;

import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.settings.ImsProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AutoHotspotListAdapter extends RecyclerView.Adapter {
    public List mBleAccessPoints;
    public Context mContext;
    public RecyclerView mParentView;
    public SemWifiManager mSemWifiManager;
    public WifiManager mWifiManager;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_NONE = new int[0];
    public static final int[] STATE_WIFI6_SECURED = {R.attr.state_wifi_6, R.attr.state_encrypted};
    public static final int[] STATE_WIFI6_NONE = {R.attr.state_wifi_6};
    public static final int[] STATE_WIFI6E_SECURED = {R.attr.state_wifi_6e, R.attr.state_encrypted};
    public static final int[] STATE_WIFI6E_NONE = {R.attr.state_wifi_6e};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoHotspotApViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public StateListDrawable mFrictionSld;
        public ImageView mIcon;
        public TextView mSummary;
        public TextView mTitle;
        public View mView;

        @Override // android.view.View.OnCreateContextMenuListener
        public final void onCreateContextMenu(
                ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Log.i("AutoHotspotListAdapter", "onCreateContextMenu() - Start");
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mBleAccessPoints;
        if (list != null) {
            return list.size();
        }
        Log.i("AutoHotspotListAdapter", "getItemCount() - mBleAccessPoints is null so returning 0");
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        AutoHotspotApViewHolder autoHotspotApViewHolder = (AutoHotspotApViewHolder) viewHolder;
        Log.i("AutoHotspotListAdapter", "onBindViewHolder() - Start");
        if (i < 0 || i >= this.mBleAccessPoints.size()) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onBindViewHolder() - invalid index ", "AutoHotspotListAdapter");
            return;
        }
        final SemWifiApBleScanResult semWifiApBleScanResult =
                (SemWifiApBleScanResult) this.mBleAccessPoints.get(i);
        final int smartApConnectedStatusFromScanResult =
                this.mSemWifiManager.getSmartApConnectedStatusFromScanResult(
                        semWifiApBleScanResult.mWifiMac);
        Log.d(
                "AutoHotspotListAdapter",
                "onBindViewHolder() - Current BleAp values, mWifiMac: "
                        + semWifiApBleScanResult.mWifiMac
                        + ", mSSID: "
                        + semWifiApBleScanResult.mSSID
                        + ", connectedState:"
                        + smartApConnectedStatusFromScanResult);
        ImageView imageView = autoHotspotApViewHolder.mIcon;
        StateListDrawable stateListDrawable = autoHotspotApViewHolder.mFrictionSld;
        Log.i("AutoHotspotListAdapter", "updateIcon() - Triggered");
        int i2 = semWifiApBleScanResult.mBLERssi;
        Preference$$ExternalSyntheticOutline0.m(
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i2, "bleRssiLevel:", ",ap.mNetworkType"),
                semWifiApBleScanResult.mNetworkType,
                "AutoHotspotListAdapter");
        int i3 = i2 >= -55 ? 4 : i2 >= -65 ? 3 : i2 >= -75 ? 2 : i2 >= -85 ? 1 : 0;
        if (stateListDrawable != null) {
            Drawable current = stateListDrawable.getCurrent();
            if (current != null) {
                imageView.setImageDrawable(current);
                current.setLevel(i3);
            } else {
                imageView.setImageDrawable(
                        this.mContext.getResources().getDrawable(R.drawable.ic_wifi_signal_0));
            }
            if (Utils.MHSDBG
                    && !TextUtils.isEmpty(SystemProperties.get("vendor.wifiap.bleap.icon"))) {
                semWifiApBleScanResult.mNetworkType =
                        Integer.parseInt(SystemProperties.get("vendor.wifiap.bleap.icon"));
            }
            int i4 = semWifiApBleScanResult.mNetworkType;
            int i5 = SemWifiApBleScanResult.MHS_WIFI_6_NETWORK;
            int[] iArr = STATE_WIFI6_NONE;
            int[] iArr2 = STATE_WIFI6_SECURED;
            if (i4 != i5) {
                int i6 = SemWifiApBleScanResult.MHS_WIFI_6E_NETWORK;
                if (i4 == i6) {
                    if (semWifiApBleScanResult.mSecurity == 1) {
                        stateListDrawable.setState(STATE_WIFI6E_SECURED);
                    } else {
                        stateListDrawable.setState(STATE_WIFI6E_NONE);
                    }
                } else if (i4 == i6) {
                    if (semWifiApBleScanResult.mSecurity == 1) {
                        stateListDrawable.setState(iArr2);
                    } else {
                        stateListDrawable.setState(iArr);
                    }
                } else if (semWifiApBleScanResult.mSecurity == 1) {
                    stateListDrawable.setState(STATE_SECURED);
                } else {
                    stateListDrawable.setState(STATE_NONE);
                }
            } else if (semWifiApBleScanResult.mSecurity == 1) {
                stateListDrawable.setState(iArr2);
            } else {
                stateListDrawable.setState(iArr);
            }
        }
        int i7 = AutoHotspotApViewHolder.$r8$clinit;
        autoHotspotApViewHolder.mTitle.setText(semWifiApBleScanResult.mSSID);
        autoHotspotApViewHolder.mView.setEnabled(true);
        StringBuilder sb = new StringBuilder();
        sb.append(semWifiApBleScanResult.mUserName);
        if (smartApConnectedStatusFromScanResult == 3) {
            Log.e(
                    "AutoHotspotListAdapter",
                    "onBindViewHolder() - Selected  BleAp is in connected state. THIS SHOULD HAVE"
                        + " BE REMOVED");
        } else if (semWifiApBleScanResult.mBattery <= 15) {
            TooltipPopup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onBindViewHolder() - Selected  BleAp  has low battery: "),
                    semWifiApBleScanResult.mBattery,
                    "AutoHotspotListAdapter");
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.comma, sb, " ");
            sb.append(this.mContext.getString(R.string.wifi_ap_auto_hotspot_low_battery_summary));
            autoHotspotApViewHolder.mView.setEnabled(false);
        } else if (semWifiApBleScanResult.isDataSaverEnabled) {
            Log.i(
                    "AutoHotspotListAdapter",
                    "onBindViewHolder() - Selected  BleAp  DataSaver enabled: ");
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.comma, sb, " ");
            sb.append(
                    this.mContext.getString(
                            R.string.wifi_ap_mobile_hotspot_dialog_data_saver_is_on));
            autoHotspotApViewHolder.mView.setEnabled(false);
        } else if (smartApConnectedStatusFromScanResult == 1
                || smartApConnectedStatusFromScanResult == 2) {
            Log.i(
                    "AutoHotspotListAdapter",
                    "onBindViewHolder() - Selected  BleAp is in Connecting state");
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.comma, sb, " ");
            sb.append(this.mContext.getString(R.string.smart_tethering_ap_connecting_summary));
        } else if (smartApConnectedStatusFromScanResult < 0) {
            Log.i(
                    "AutoHotspotListAdapter",
                    "onBindViewHolder() - Selected  BleAp is in (Failed)/(Connection Timed out)"
                        + " state");
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.comma, sb, " ");
            sb.append(
                    this.mContext.getString(R.string.smart_tethering_ap_connection_failed_summary));
            autoHotspotApViewHolder.mView.setEnabled(false);
        } else if (semWifiApBleScanResult.isMobileDataLimitReached) {
            Log.i(
                    "AutoHotspotListAdapter",
                    "onBindViewHolder() - Selected  BleAp  isMobileDataLimitReached : ");
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.comma, sb, " ");
            sb.append(this.mContext.getString(R.string.wifi_ap_data_limit_reached));
            autoHotspotApViewHolder.mView.setEnabled(false);
        } else if (semWifiApBleScanResult.isNotValidNetwork) {
            Log.i(
                    "AutoHotspotListAdapter",
                    "onBindViewHolder() - Selected  BleAp  isNotValidNetwork : ");
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.comma, sb, " ");
            sb.append(this.mContext.getString(R.string.smart_tethering_internet_not_available));
        } else {
            Log.i("AutoHotspotListAdapter", "onBindViewHolder() - Available Hotspot live device");
        }
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        }
        if (this.mWifiManager.getVerboseLoggingLevel() == 1) {
            StringBuilder m =
                    PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                            PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                    PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                            PrecomputedTextCompat$Params$$ExternalSyntheticOutline0
                                                    .m(
                                                            new StringBuilder(",version:"),
                                                            semWifiApBleScanResult.version,
                                                            sb,
                                                            ",BLE RSSI:"),
                                            semWifiApBleScanResult.mBLERssi,
                                            sb,
                                            ",battery:"),
                                    semWifiApBleScanResult.mBattery,
                                    sb,
                                    ",Security:"),
                            semWifiApBleScanResult.mSecurity,
                            sb,
                            ",hidden:");
            m.append(semWifiApBleScanResult.mhidden);
            sb.append(m.toString());
        }
        autoHotspotApViewHolder.mSummary.setText(sb.toString());
        if (autoHotspotApViewHolder.mView.isEnabled()) {
            autoHotspotApViewHolder.mView.setClickable(true);
            autoHotspotApViewHolder.mTitle.setAlpha(1.0f);
            autoHotspotApViewHolder.mSummary.setAlpha(1.0f);
            autoHotspotApViewHolder.mIcon.setAlpha(1.0f);
        } else {
            Log.d("AutoHotspotListAdapter", "onBindViewHolder(): BleAp item is disabled");
            autoHotspotApViewHolder.mView.setClickable(false);
            autoHotspotApViewHolder.mTitle.setAlpha(0.4f);
            autoHotspotApViewHolder.mSummary.setAlpha(0.4f);
            autoHotspotApViewHolder.mIcon.setAlpha(0.4f);
        }
        autoHotspotApViewHolder.mView.setOnClickListener(
                new View.OnClickListener() { // from class:
                    // com.samsung.android.settings.wifi.AutoHotspotListAdapter.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        StringBuilder sb2 =
                                new StringBuilder(
                                        "currentHolder.mView.setOnClickListener`s onClick() -"
                                            + " Clicked BleAp values, mWifiMac: ");
                        sb2.append(semWifiApBleScanResult.mWifiMac);
                        sb2.append(", mSSID: ");
                        sb2.append(semWifiApBleScanResult.mSSID);
                        sb2.append(", connectedState:");
                        sb2.append(smartApConnectedStatusFromScanResult);
                        sb2.append(",bleAp.version:");
                        Preference$$ExternalSyntheticOutline0.m(
                                sb2, semWifiApBleScanResult.version, "AutoHotspotListAdapter");
                        SemWifiManager semWifiManager = AutoHotspotListAdapter.this.mSemWifiManager;
                        SemWifiApBleScanResult semWifiApBleScanResult2 = semWifiApBleScanResult;
                        semWifiManager.connectToSmartMHS(
                                semWifiApBleScanResult2.mDevice,
                                semWifiApBleScanResult2.mMHSdeviceType,
                                semWifiApBleScanResult2.mhidden,
                                semWifiApBleScanResult2.mSecurity,
                                semWifiApBleScanResult2.mWifiMac,
                                semWifiApBleScanResult2.mUserName,
                                semWifiApBleScanResult2.version,
                                semWifiApBleScanResult2.isWifiProfileShareEnabled);
                    }
                });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.i("AutoHotspotListAdapter", "onCreateViewHolder() - Start");
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_wifi_list_preference,
                                (ViewGroup) this.mParentView,
                                false);
        AutoHotspotApViewHolder autoHotspotApViewHolder = new AutoHotspotApViewHolder(inflate);
        Log.i("AutoHotspotListAdapter", "AutoHotspotApViewHolder() - initiated");
        autoHotspotApViewHolder.mIcon = (ImageView) inflate.findViewById(R.id.wifi_icon);
        autoHotspotApViewHolder.mTitle = (TextView) inflate.findViewById(R.id.title);
        autoHotspotApViewHolder.mSummary = (TextView) inflate.findViewById(R.id.summary);
        autoHotspotApViewHolder.mView = inflate;
        autoHotspotApViewHolder.mFrictionSld =
                (StateListDrawable) this.mContext.getDrawable(R.drawable.wifi_signal);
        return autoHotspotApViewHolder;
    }
}
