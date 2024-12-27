package com.samsung.android.settings.wifi;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.details.WifiNetworkDetailsFragment;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectedListAdapter extends WifiListAdapter {
    public OnEventListener mListener;
    public boolean mPickerHelperMode;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AccessPointViewHolder extends RecyclerView.ViewHolder {
        public ImageView mDetailIcon;
        public RelativeLayout mDetailIconLayout;
        public View mDivider;
        public StateListDrawable mFrictionSld;
        public ImageView mIcon;
        public RelativeLayout mRelativeLayoutWcm;
        public TextView mSummary;
        public TextView mTitle;
        public View mView;
        public ImageView mWifiTipsImageView;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnEventListener {
        void onItemClicked(WifiEntry wifiEntry);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mWifiEntries;
        if (list != null) {
            return list.size();
        }
        Log.d("WifiList.Connected", "getItemCount failed - mList is NULL");
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return 0;
    }

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final String getLogTag() {
        return "WifiList.Connected";
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (i < 0 || i > this.mWifiEntries.size()) {
            Log.d("WifiList.Connected", "onBindViewHolder failed - invalid index");
            return;
        }
        final WifiEntry wifiEntry = (WifiEntry) this.mWifiEntries.get(i);
        if (wifiEntry == null) {
            Log.d("WifiList.Connected", "onBindViewHolder failed - null WifiEntry");
            return;
        }
        boolean isEnabledUltraPowerSaving = SemWifiUtils.isEnabledUltraPowerSaving(this.mContext);
        boolean canSignIn = wifiEntry.canSignIn();
        if (canSignIn || this.mPickerHelperMode) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    RowView$$ExternalSyntheticOutline0.m(
                            "Settings onClickListener : ", "/", canSignIn),
                    this.mPickerHelperMode,
                    "WifiList.Connected");
            final int i2 = 0;
            viewHolder.itemView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.ConnectedListAdapter$$ExternalSyntheticLambda0
                        public final /* synthetic */ ConnectedListAdapter f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i2) {
                                case 0:
                                    ConnectedListAdapter connectedListAdapter = this.f$0;
                                    WifiEntry wifiEntry2 = wifiEntry;
                                    ConnectedListAdapter.OnEventListener onEventListener =
                                            connectedListAdapter.mListener;
                                    if (onEventListener != null) {
                                        onEventListener.onItemClicked(wifiEntry2);
                                        break;
                                    }
                                    break;
                                default:
                                    ConnectedListAdapter connectedListAdapter2 = this.f$0;
                                    WifiEntry wifiEntry3 = wifiEntry;
                                    SALogging.insertSALog(
                                            connectedListAdapter2.mSAScreenId, "0112");
                                    if (WifiListAdapter.DBG) {
                                        Log.d("WifiList.Connected", "launchNetworkDetailsFragment");
                                    }
                                    WifiConfiguration wifiConfiguration =
                                            wifiEntry3.getWifiConfiguration();
                                    if (WifiUtils.isNetworkLockedDown(
                                                    connectedListAdapter2.mContext,
                                                    wifiConfiguration)
                                            && wifiEntry3.getConnectedInfo() != null) {
                                        Context context = connectedListAdapter2.mContext;
                                        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                                context,
                                                RestrictedLockUtilsInternal.getDeviceOwner(
                                                        context));
                                        break;
                                    } else {
                                        String title = wifiEntry3.getTitle();
                                        Bundle bundle = new Bundle();
                                        bundle.putString(
                                                "key_chosen_wifientry_key", wifiEntry3.getKey());
                                        bundle.putParcelable(
                                                "key_chosen_wifientry_config", wifiConfiguration);
                                        connectedListAdapter2.launchFragment(
                                                WifiNetworkDetailsFragment.class.getName(),
                                                bundle,
                                                title);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
        }
        if (WifiListAdapter.DBG) {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i, "onBindView Connected AP : ", " ");
            m.append(wifiEntry.getTitle());
            m.append(" ");
            m.append(wifiEntry.getSummary(true));
            Log.d("WifiList.Connected", m.toString());
        }
        AccessPointViewHolder accessPointViewHolder = (AccessPointViewHolder) viewHolder;
        updateIcon(accessPointViewHolder.mIcon, accessPointViewHolder.mFrictionSld, wifiEntry);
        accessPointViewHolder.mTitle.setText(wifiEntry.getTitle());
        accessPointViewHolder.mSummary.setText(wifiEntry.getSummary(true));
        accessPointViewHolder.mSummary.setMaxLines(this.mIsMaxSummaryLineOn ? 20 : 4);
        accessPointViewHolder.mTitle.setTextColor(
                this.mContext.getColor(R.color.sec_wifi_ap_activity_label_text_color));
        if (wifiEntry.getConnectedState() == 2) {
            final int i3 = 1;
            accessPointViewHolder.mDetailIconLayout.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.ConnectedListAdapter$$ExternalSyntheticLambda0
                        public final /* synthetic */ ConnectedListAdapter f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i3) {
                                case 0:
                                    ConnectedListAdapter connectedListAdapter = this.f$0;
                                    WifiEntry wifiEntry2 = wifiEntry;
                                    ConnectedListAdapter.OnEventListener onEventListener =
                                            connectedListAdapter.mListener;
                                    if (onEventListener != null) {
                                        onEventListener.onItemClicked(wifiEntry2);
                                        break;
                                    }
                                    break;
                                default:
                                    ConnectedListAdapter connectedListAdapter2 = this.f$0;
                                    WifiEntry wifiEntry3 = wifiEntry;
                                    SALogging.insertSALog(
                                            connectedListAdapter2.mSAScreenId, "0112");
                                    if (WifiListAdapter.DBG) {
                                        Log.d("WifiList.Connected", "launchNetworkDetailsFragment");
                                    }
                                    WifiConfiguration wifiConfiguration =
                                            wifiEntry3.getWifiConfiguration();
                                    if (WifiUtils.isNetworkLockedDown(
                                                    connectedListAdapter2.mContext,
                                                    wifiConfiguration)
                                            && wifiEntry3.getConnectedInfo() != null) {
                                        Context context = connectedListAdapter2.mContext;
                                        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                                context,
                                                RestrictedLockUtilsInternal.getDeviceOwner(
                                                        context));
                                        break;
                                    } else {
                                        String title = wifiEntry3.getTitle();
                                        Bundle bundle = new Bundle();
                                        bundle.putString(
                                                "key_chosen_wifientry_key", wifiEntry3.getKey());
                                        bundle.putParcelable(
                                                "key_chosen_wifientry_config", wifiConfiguration);
                                        connectedListAdapter2.launchFragment(
                                                WifiNetworkDetailsFragment.class.getName(),
                                                bundle,
                                                title);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            accessPointViewHolder.mDivider.setVisibility(0);
            accessPointViewHolder.mDetailIconLayout.setVisibility(0);
        } else {
            accessPointViewHolder.mDivider.setVisibility(8);
            accessPointViewHolder.mDetailIconLayout.setVisibility(8);
        }
        ImageView imageView = accessPointViewHolder.mDetailIcon;
        StringBuilder sb = new StringBuilder();
        sb.append(wifiEntry.getTitle());
        sb.append(" ");
        TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                this.mContext, R.string.wifi_preference_details, sb, " ");
        sb.append(this.mContext.getString(R.string.button_tts));
        imageView.setContentDescription(sb.toString());
        SemWifiManager semWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        final int currentStatusMode =
                semWifiManager != null ? semWifiManager.getCurrentStatusMode() : 0;
        WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
        boolean z =
                (!this.mIsSupportWifiTips || isEnabledUltraPowerSaving || currentStatusMode == 0)
                        ? false
                        : true;
        if (wifiConfiguration != null) {
            z &= !wifiConfiguration.isPasspoint() && wifiConfiguration.carrierId == -1;
        }
        if (!z) {
            accessPointViewHolder.mWifiTipsImageView.setVisibility(8);
            accessPointViewHolder.mRelativeLayoutWcm.setVisibility(8);
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                currentStatusMode, "WCM current Mode : ", "WifiList.Connected");
        accessPointViewHolder.mRelativeLayoutWcm.setVisibility(0);
        ImageView imageView2 = accessPointViewHolder.mWifiTipsImageView;
        if (imageView2 == null) {
            Log.e("WifiList.Connected", "onClick - image is null");
        } else {
            imageView2.setVisibility(0);
            accessPointViewHolder.mWifiTipsImageView.setOnClickListener(
                    new View.OnClickListener() { // from class:
                        // com.samsung.android.settings.wifi.ConnectedListAdapter$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            WifiInfo connectionInfo;
                            ConnectedListAdapter connectedListAdapter = ConnectedListAdapter.this;
                            WifiEntry wifiEntry2 = wifiEntry;
                            int i4 = currentStatusMode;
                            connectedListAdapter.getClass();
                            try {
                                WifiConfiguration wifiConfiguration2 =
                                        wifiEntry2.getWifiConfiguration();
                                if (wifiConfiguration2 == null) {
                                    Log.e(
                                            "WifiList.Connected",
                                            "launchWifiTips failure, config is null");
                                    return;
                                }
                                SemWifiManager semWifiManager2 =
                                        (SemWifiManager)
                                                connectedListAdapter.mContext.getSystemService(
                                                        WiFiManagerExt.SEM_WIFI_SERVICE);
                                String key = wifiConfiguration2.getKey();
                                List configuredNetworks = semWifiManager2.getConfiguredNetworks();
                                SemWifiConfiguration semWifiConfiguration = null;
                                if (configuredNetworks != null) {
                                    Iterator it = configuredNetworks.iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        SemWifiConfiguration semWifiConfiguration2 =
                                                (SemWifiConfiguration) it.next();
                                        if (semWifiConfiguration2.configKey.equals(key)) {
                                            semWifiConfiguration = semWifiConfiguration2;
                                            break;
                                        }
                                    }
                                }
                                int i5 = 3;
                                if (i4 != 1) {
                                    i5 = i4 != 2 ? i4 != 3 ? -1 : 2 : 1;
                                } else if (semWifiConfiguration == null
                                        || !semWifiConfiguration.isNoInternetAccessExpected) {
                                    i5 = 0;
                                }
                                Log.d("WifiList.Connected", "wcm status : " + i5);
                                int i6 = wifiConfiguration2.networkId;
                                if (i6 == -1
                                        && (connectionInfo =
                                                        ((WifiManager)
                                                                        connectedListAdapter
                                                                                .mContext
                                                                                .getSystemService(
                                                                                        ImsProfile
                                                                                                .PDN_WIFI))
                                                                .getConnectionInfo())
                                                != null) {
                                    i6 = connectionInfo.getNetworkId();
                                }
                                Intent intent = new Intent();
                                intent.setPackage("com.samsung.android.net.wifi.wifiguider");
                                intent.setClassName(
                                        "com.samsung.android.net.wifi.wifiguider",
                                        "com.samsung.android.net.wifi.wifiguider.activity.GuideApActivity");
                                intent.putExtra("wcm_status", i5);
                                intent.putExtra(FieldName.CONFIG, wifiConfiguration2);
                                intent.putExtra(
                                        "randomMacSetting",
                                        wifiConfiguration2.macRandomizationSetting);
                                intent.putExtra(
                                        UniversalCredentialUtil.AGENT_TITLE, wifiEntry2.getTitle());
                                intent.putExtra("netid", i6);
                                intent.setFlags(67108864);
                                connectedListAdapter.mContext.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Log.d(
                                        "WifiList.Connected",
                                        "launchWifiTips, ActivityNotFoundException : "
                                                + e.getMessage());
                            }
                        }
                    });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView recyclerView = this.mParentView;
        boolean z = this.mInSetupWizardActivity;
        View inflate =
                z
                        ? LayoutInflater.from(this.mContext)
                                .inflate(
                                        R.layout.sec_wifi_list_preference_setupwizard,
                                        (ViewGroup) recyclerView,
                                        false)
                        : this.mHideLeftRightPadding
                                ? LayoutInflater.from(this.mContext)
                                        .inflate(
                                                R.layout
                                                        .sec_wifi_list_preference_without_side_padding,
                                                (ViewGroup) recyclerView,
                                                false)
                                : LayoutInflater.from(this.mContext)
                                        .inflate(
                                                R.layout.sec_wifi_list_preference,
                                                (ViewGroup) recyclerView,
                                                false);
        AccessPointViewHolder accessPointViewHolder = new AccessPointViewHolder(inflate);
        accessPointViewHolder.mView = inflate;
        accessPointViewHolder.mIcon = (ImageView) inflate.findViewById(R.id.wifi_icon);
        accessPointViewHolder.mTitle = (TextView) inflate.findViewById(R.id.title);
        accessPointViewHolder.mSummary = (TextView) inflate.findViewById(R.id.summary);
        accessPointViewHolder.mDetailIconLayout =
                (RelativeLayout) inflate.findViewById(R.id.layout_details);
        accessPointViewHolder.mDetailIcon = (ImageView) inflate.findViewById(R.id.wifi_details);
        accessPointViewHolder.mFrictionSld =
                z
                        ? (StateListDrawable)
                                this.mContext.getDrawable(R.drawable.wifi_signal_setupwizard)
                        : (StateListDrawable) this.mContext.getDrawable(R.drawable.wifi_signal);
        accessPointViewHolder.mRelativeLayoutWcm =
                (RelativeLayout) inflate.findViewById(R.id.layout_current);
        accessPointViewHolder.mWifiTipsImageView =
                (ImageView) inflate.findViewById(R.id.WifiTipsIcon_connected);
        accessPointViewHolder.mDivider = inflate.findViewById(R.id.wifi_divider_current);
        return accessPointViewHolder;
    }

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final void notifyOnLaunchActivityFinishedIfNeeded() {}

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final void notifyOnLaunchActivityIfNeeded() {}
}
