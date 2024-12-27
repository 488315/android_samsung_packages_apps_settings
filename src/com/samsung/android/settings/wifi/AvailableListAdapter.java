package com.samsung.android.settings.wifi;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;
import com.android.settings.wifi.WifiSettings;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.wifitrackerlib.OsuWifiEntry;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.scloud.SCloudWifiDataManager;
import com.samsung.android.settings.wifi.details.WifiNetworkConnectFragment;
import com.samsung.android.settings.wifi.details.WifiNetworkModifyFragment;
import com.samsung.android.settingslib.wifi.WifiWarningDialogController;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AvailableListAdapter extends WifiListAdapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Intent mDisableIntent;
    public boolean mHideContextMenus;
    public boolean mIsTipsIconClicked;
    public boolean mIsTipsIconCreated;
    public WifiSettings.AnonymousClass6 mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AccessPointViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        public final View mDivider;
        public final StateListDrawable mFrictionSld;
        public final ImageView mIcon;
        public final TextView mSummary;
        public final RelativeLayout mTipsDisabledIconLayout;
        public final TextView mTitle;
        public final View mView;
        public WifiEntry mWifiEntry;
        public final ImageView mWifiTipsImageView;
        public final AnonymousClass1 onMenuItemClickListener;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.wifi.AvailableListAdapter$AccessPointViewHolder$1, reason: invalid class name */
        public final class AnonymousClass1 implements MenuItem.OnMenuItemClickListener {
            public AnonymousClass1() {}

            @Override // android.view.MenuItem.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                if (AccessPointViewHolder.this.mWifiEntry == null) {
                    Log.d("WifiList.Available", "onMenuItemClick but ignored. WifiEntry is null");
                    return false;
                }
                int itemId = menuItem.getItemId();
                if (itemId != 1) {
                    if (itemId == 2) {
                        AccessPointViewHolder accessPointViewHolder = AccessPointViewHolder.this;
                        if (AvailableListAdapter.this.mListener != null) {
                            SCloudWifiDataManager.getInstance(
                                            AvailableListAdapter.this.mContext
                                                    .getApplicationContext())
                                    .syncToRemove(
                                            accessPointViewHolder.mWifiEntry
                                                    .getWifiConfiguration());
                            AccessPointViewHolder accessPointViewHolder2 =
                                    AccessPointViewHolder.this;
                            WifiSettings.AnonymousClass6 anonymousClass6 =
                                    AvailableListAdapter.this.mListener;
                            WifiEntry wifiEntry = accessPointViewHolder2.mWifiEntry;
                            anonymousClass6.getClass();
                            if (WifiSettings.DBG()) {
                                Log.d("WifiSettings", "onForget");
                            }
                            WifiSettings.this.forget$1(wifiEntry);
                            SALogging.insertSALog(AvailableListAdapter.this.mSAScreenId, "0121");
                        }
                        return true;
                    }
                    if (itemId != 3) {
                        if (itemId != 4) {
                            return false;
                        }
                        AccessPointViewHolder.this.mWifiEntry.setAutoJoinEnabled(false);
                        Settings.Global.putInt(
                                AvailableListAdapter.this.mContext.getContentResolver(),
                                "sem_wifi_carrier_network_offload_enabled",
                                0);
                        return true;
                    }
                    AccessPointViewHolder accessPointViewHolder3 = AccessPointViewHolder.this;
                    AvailableListAdapter availableListAdapter = AvailableListAdapter.this;
                    WifiEntry wifiEntry2 = accessPointViewHolder3.mWifiEntry;
                    int i = AvailableListAdapter.$r8$clinit;
                    availableListAdapter.getClass();
                    Bundle bundle = new Bundle();
                    bundle.putString("key_modify_wifientry_key", wifiEntry2.getKey());
                    availableListAdapter.launchFragment(
                            WifiNetworkModifyFragment.class.getName(),
                            bundle,
                            wifiEntry2.getTitle());
                    SALogging.insertSALog(AvailableListAdapter.this.mSAScreenId, "0122");
                    return true;
                }
                boolean isSaved = AccessPointViewHolder.this.mWifiEntry.isSaved();
                SALogging.insertSALog(AvailableListAdapter.this.mSAScreenId, "0124");
                WifiEntry wifiEntry3 = AccessPointViewHolder.this.mWifiEntry;
                wifiEntry3.getClass();
                if (wifiEntry3 instanceof OsuWifiEntry) {
                    AccessPointViewHolder.this.mWifiEntry.connect(
                            new WifiEntry
                                    .ConnectCallback() { // from class:
                                                         // com.samsung.android.settings.wifi.AvailableListAdapter$AccessPointViewHolder$1$$ExternalSyntheticLambda0
                                @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
                                public final void onConnectResult(int i2) {
                                    AvailableListAdapter.AccessPointViewHolder.AnonymousClass1
                                            anonymousClass1 =
                                                    AvailableListAdapter.AccessPointViewHolder
                                                            .AnonymousClass1.this;
                                    if (i2 == 2) {
                                        Toast.makeText(
                                                        AvailableListAdapter.this.mContext,
                                                        R.string.wifi_failed_connect_message,
                                                        0)
                                                .show();
                                    } else {
                                        anonymousClass1.getClass();
                                    }
                                }
                            });
                    return true;
                }
                if (!SemWifiEntryFlags.isWepAllowed(AvailableListAdapter.this.mContext)
                        && AccessPointViewHolder.this.mWifiEntry.getSecurityTypes().contains(1)) {
                    Context context = AvailableListAdapter.this.mContext;
                    String ssid = AccessPointViewHolder.this.mWifiEntry.getSsid();
                    Intent wifiWarningIntent = WifiWarningDialogController.getWifiWarningIntent();
                    wifiWarningIntent.putExtra("req_type", 0);
                    wifiWarningIntent.putExtra("extra_type", 9);
                    wifiWarningIntent.putExtra("ssid", ssid);
                    try {
                        context.startActivity(wifiWarningIntent);
                    } catch (ActivityNotFoundException unused) {
                    }
                    return true;
                }
                if (isSaved) {
                    AccessPointViewHolder accessPointViewHolder4 = AccessPointViewHolder.this;
                    WifiSettings.AnonymousClass6 anonymousClass62 =
                            AvailableListAdapter.this.mListener;
                    if (anonymousClass62 != null) {
                        WifiEntry wifiEntry4 = accessPointViewHolder4.mWifiEntry;
                        if (WifiSettings.DBG()) {
                            Log.d("WifiSettings", "onConnect");
                        }
                        WifiSettings wifiSettings = WifiSettings.this;
                        if (wifiSettings.mRetryPopupController
                                .checkToShowRetryForIncorrectPasswordLength(wifiEntry4)) {
                            wifiSettings.showConnectFragmentForRetry(wifiEntry4);
                        } else {
                            wifiSettings.mRetryPopupController.getClass();
                            wifiSettings.mManualConnectingNetwork =
                                    WifiRetryPopupController.isSecurityTypeSupportRetry(wifiEntry4)
                                            ? wifiEntry4
                                            : null;
                            if (wifiEntry4.mSemFlags.isOpenRoamingNetwork) {
                                if (!wifiSettings.mOpenRoamingSettings
                                        .isUserAllowedOAuthAgreement()) {
                                    wifiSettings.launchOpenRoamingFragment();
                                } else if (SemWifiUtils.isValidOpenRoamingToken(
                                        wifiSettings.mContext)) {
                                    wifiSettings.mWifiManager.enableNetwork(
                                            wifiEntry4.getWifiConfiguration().networkId, false);
                                } else {
                                    Context context2 = wifiSettings.mContext;
                                    ApnPreference$$ExternalSyntheticOutline0.m(
                                            context2,
                                            R.string.wifi_openroaming_invalid_token_summary,
                                            context2,
                                            1);
                                }
                            } else if (wifiEntry4.isSaved() && !wifiEntry4.semIsEphemeral()) {
                                WifiConfiguration wifiConfiguration =
                                        wifiEntry4.getWifiConfiguration();
                                if (wifiConfiguration.BSSID != null) {
                                    Log.d(
                                            "WifiSettings",
                                            "clear bssid info "
                                                    + wifiConfiguration.getKey()
                                                    + "-"
                                                    + wifiSettings.mLog.getPrintableLog(
                                                            wifiConfiguration.BSSID));
                                    wifiConfiguration.BSSID = "any";
                                    wifiSettings.mWifiManager.updateNetwork(wifiConfiguration);
                                }
                            }
                            wifiSettings.connect(wifiEntry4);
                        }
                    }
                } else if (WifiEnterpriseRestrictionUtils.isAddWifiConfigAllowed(
                        AvailableListAdapter.this.mContext)) {
                    AccessPointViewHolder accessPointViewHolder5 = AccessPointViewHolder.this;
                    AvailableListAdapter availableListAdapter2 = AvailableListAdapter.this;
                    WifiEntry wifiEntry5 = accessPointViewHolder5.mWifiEntry;
                    int i2 = AvailableListAdapter.$r8$clinit;
                    availableListAdapter2.getClass();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("key_connect_wifientry_key", wifiEntry5.getKey());
                    bundle2.putString("bssid", wifiEntry5.semGetBssid());
                    availableListAdapter2.launchFragment(
                            WifiNetworkConnectFragment.class.getName(),
                            bundle2,
                            wifiEntry5.getTitle());
                }
                return true;
            }
        }

        public AccessPointViewHolder(View view) {
            super(view);
            this.onMenuItemClickListener = new AnonymousClass1();
            this.mIcon = (ImageView) view.findViewById(R.id.wifi_icon);
            this.mTitle = (TextView) view.findViewById(R.id.title);
            this.mSummary = (TextView) view.findViewById(R.id.summary);
            if (!AvailableListAdapter.this.mHideContextMenus) {
                view.setLongClickable(true);
                view.setOnCreateContextMenuListener(this);
            }
            this.mView = view;
            this.mFrictionSld =
                    AvailableListAdapter.this.mInSetupWizardActivity
                            ? (StateListDrawable)
                                    AvailableListAdapter.this.mContext.getDrawable(
                                            R.drawable.wifi_signal_setupwizard)
                            : (StateListDrawable)
                                    AvailableListAdapter.this.mContext.getDrawable(
                                            R.drawable.wifi_signal);
            this.mTipsDisabledIconLayout = (RelativeLayout) view.findViewById(R.id.layout_disable);
            this.mWifiTipsImageView = (ImageView) view.findViewById(R.id.WifiTipsIcon);
            this.mDivider = view.findViewById(R.id.wifi_divider);
        }

        public final boolean canModifyNetwork() {
            if (this.mWifiEntry.isSaved()) {
                WifiEntry wifiEntry = this.mWifiEntry;
                SemWifiEntryFlags semWifiEntryFlags = wifiEntry.mSemFlags;
                if (!semWifiEntryFlags.isCarrierNetwork
                        && !semWifiEntryFlags.isLocked
                        && ((!wifiEntry.isSubscription() || !this.mWifiEntry.isSuggestion())
                                && !WifiUtils.isBlockedByEnterprise(
                                        AvailableListAdapter.this.mContext,
                                        this.mWifiEntry.getSsid())
                                && !WifiUtils.isNetworkLockedDown(
                                        AvailableListAdapter.this.mContext,
                                        this.mWifiEntry.getWifiConfiguration()))) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.view.View.OnCreateContextMenuListener
        public final void onCreateContextMenu(
                ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            AvailableListAdapter availableListAdapter = AvailableListAdapter.this;
            if (availableListAdapter.mHideContextMenus) {
                return;
            }
            if (this.mWifiEntry == null) {
                Log.d("WifiList.Available", "Failed to create context menu. WifiEntry is NULL");
                return;
            }
            SALogging.insertSALog(availableListAdapter.mSAScreenId, "0120");
            contextMenu.setHeaderTitle(this.mWifiEntry.getTitle());
            contextMenu
                    .add(0, 1, 0, R.string.wifi_menu_connect)
                    .setOnMenuItemClickListener(this.onMenuItemClickListener);
            if (WifiUtils.isNetworkLockedDown(
                    AvailableListAdapter.this.mContext, this.mWifiEntry.getWifiConfiguration())) {
                return;
            }
            WifiEntry wifiEntry = this.mWifiEntry;
            if (wifiEntry.mSemFlags.isOpenRoamingNetwork) {
                return;
            }
            if (wifiEntry.isSaved() && !this.mWifiEntry.isSuggestion()) {
                if (!Rune.isShopDemo(AvailableListAdapter.this.mContext)) {
                    WifiEntry wifiEntry2 = this.mWifiEntry;
                    wifiEntry2.getClass();
                    if (!(wifiEntry2 instanceof OsuWifiEntry) && canModifyNetwork()) {
                        contextMenu
                                .add(0, 2, 0, R.string.wifi_menu_forget)
                                .setOnMenuItemClickListener(this.onMenuItemClickListener);
                    }
                }
                if (!this.mWifiEntry.isSubscription()
                        && this.mWifiEntry.getConnectedInfo() == null
                        && canModifyNetwork()) {
                    contextMenu
                            .add(0, 3, 0, R.string.wifi_menu_modify)
                            .setOnMenuItemClickListener(this.onMenuItemClickListener);
                }
            }
            if (this.mWifiEntry.mSemFlags.isCarrierNetwork
                    && Settings.Global.getInt(
                                    AvailableListAdapter.this.mContext.getContentResolver(),
                                    "sem_wifi_carrier_network_offload_enabled",
                                    1)
                            == 1
                    && this.mWifiEntry.isAutoJoinEnabled()) {
                contextMenu
                        .add(
                                0,
                                4,
                                0,
                                AvailableListAdapter.this
                                        .mContext
                                        .getResources()
                                        .getString(
                                                R.string.wifi_menu_turn_off,
                                                AvailableListAdapter.this
                                                        .mContext
                                                        .getResources()
                                                        .getString(R.string.wifi_auto_reconnect)))
                        .setOnMenuItemClickListener(this.onMenuItemClickListener);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AddNetworkViewHolder extends RecyclerView.ViewHolder {
        public final TextView mSummary;
        public final View mView;

        public AddNetworkViewHolder(View view) {
            super(view);
            this.mView = view;
            this.mSummary = (TextView) view.findViewById(R.id.summary);
        }
    }

    public static SemWifiConfiguration getSemWifiConfiguration(
            Context context, WifiConfiguration wifiConfiguration) {
        int i;
        List<SemWifiConfiguration> configuredNetworks =
                ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                        .getConfiguredNetworks();
        if (configuredNetworks == null) {
            return null;
        }
        String key = wifiConfiguration.getKey();
        if (key.contains(WifiPolicy.SECURITY_TYPE_WPA_PSK)) {
            String m =
                    ComponentActivity$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder(),
                            wifiConfiguration.SSID,
                            WifiPolicy.SECURITY_TYPE_SAE);
            for (SemWifiConfiguration semWifiConfiguration : configuredNetworks) {
                if (semWifiConfiguration.configKey.equals(m)
                        && ((i = semWifiConfiguration.networkDisableReason) == 6
                                || i == 7
                                || i == 1
                                || semWifiConfiguration.disableTimeByWcm != 0
                                || semWifiConfiguration.disableTimeByEle != 0)) {
                    Log.d("WifiList.Available", "SAE type is disabled ".concat(key));
                    return semWifiConfiguration;
                }
            }
        }
        for (SemWifiConfiguration semWifiConfiguration2 : configuredNetworks) {
            if (semWifiConfiguration2.configKey.equals(key)) {
                return semWifiConfiguration2;
            }
        }
        return null;
    }

    public static void hideTipsDisabledIcon(AccessPointViewHolder accessPointViewHolder) {
        accessPointViewHolder.mWifiTipsImageView.setVisibility(8);
        accessPointViewHolder.mDivider.setVisibility(8);
        accessPointViewHolder.mTipsDisabledIconLayout.setVisibility(8);
    }

    public final void addItem(WifiEntry wifiEntry, int i) {
        if (i >= this.mWifiEntries.size()) {
            this.mWifiEntries.add(wifiEntry);
            i = this.mWifiEntries.indexOf(wifiEntry);
        } else {
            this.mWifiEntries.add(i, wifiEntry);
        }
        notifyItemInserted(i);
        if (WifiListAdapter.DBG) {
            StringBuilder m = ListPopupWindow$$ExternalSyntheticOutline0.m(i, "add_", ": ");
            m.append(wifiEntry.getKey());
            Log.d("WifiList.Available", m.toString());
        }
    }

    public final int deleteItem(WifiEntry wifiEntry) {
        int indexOf = this.mWifiEntries.indexOf(wifiEntry);
        boolean z = WifiListAdapter.DBG;
        if (indexOf >= 0) {
            this.mWifiEntries.remove(indexOf);
            notifyItemRemoved(indexOf);
            if (z) {
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(indexOf, "delete_", ": ");
                m.append(wifiEntry.getKey());
                Log.d("WifiList.Available", m.toString());
            }
        } else if (z) {
            Log.e("WifiList.Available", "delete_error: not found");
        }
        return indexOf;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mWifiEntries.size() + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return i == this.mWifiEntries.size() ? 1 : 0;
    }

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final String getLogTag() {
        return "WifiList.Available";
    }

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final void notifyOnLaunchActivityFinishedIfNeeded() {
        WifiSettings.AnonymousClass6 anonymousClass6 = this.mListener;
        if (anonymousClass6 != null) {
            WifiSettings.this.mIsLaunching = false;
        }
    }

    @Override // com.samsung.android.settings.wifi.WifiListAdapter
    public final void notifyOnLaunchActivityIfNeeded() {
        WifiSettings.AnonymousClass6 anonymousClass6 = this.mListener;
        if (anonymousClass6 != null) {
            anonymousClass6.getClass();
            long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
            WifiSettings wifiSettings = WifiSettings.this;
            wifiSettings.mScrollTimer = currentThreadTimeMillis;
            wifiSettings.mIsLaunching = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x011f, code lost:

       if (r3 == 4) goto L52;
    */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(
            androidx.recyclerview.widget.RecyclerView.ViewHolder r19, int r20) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.AvailableListAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder,"
                    + " int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        AccessPointViewHolder accessPointViewHolder;
        boolean z = this.mInSetupWizardActivity;
        RecyclerView recyclerView = this.mParentView;
        if (z) {
            if (i == 1) {
                return new AddNetworkViewHolder(
                        LayoutInflater.from(this.mContext)
                                .inflate(
                                        R.layout.sec_wifi_add_network_setupwizard,
                                        (ViewGroup) recyclerView,
                                        false));
            }
            accessPointViewHolder =
                    new AccessPointViewHolder(
                            LayoutInflater.from(this.mContext)
                                    .inflate(
                                            R.layout.sec_wifi_list_preference_setupwizard,
                                            (ViewGroup) recyclerView,
                                            false));
        } else {
            if (i == 1) {
                return new AddNetworkViewHolder(
                        LayoutInflater.from(this.mContext)
                                .inflate(
                                        R.layout.sec_wifi_add_network,
                                        (ViewGroup) recyclerView,
                                        false));
            }
            accessPointViewHolder =
                    new AccessPointViewHolder(
                            LayoutInflater.from(this.mContext)
                                    .inflate(
                                            R.layout.sec_wifi_list_preference,
                                            (ViewGroup) recyclerView,
                                            false));
        }
        return accessPointViewHolder;
    }
}
