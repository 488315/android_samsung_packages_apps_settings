package com.android.settings.homepage.contextualcards.conditional;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.UserHandle;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.network.tether.TetherSettings;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HotspotConditionController implements ConditionalCardController {
    public static final int ID = Objects.hash("HotspotConditionController");
    public static final IntentFilter WIFI_AP_STATE_FILTER =
            new IntentFilter(
                    WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED);
    public final Context mAppContext;
    public final ConditionManager mConditionManager;
    public final Receiver mReceiver = new Receiver();
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED.equals(
                    intent.getAction())) {
                HotspotConditionController.this.mConditionManager.onConditionChanged();
            }
        }
    }

    public HotspotConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_off);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object) this.mAppContext.getText(R.string.condition_hotspot_title));
        builder.mTitleText = this.mAppContext.getText(R.string.condition_hotspot_title).toString();
        SoftApConfiguration softApConfiguration = this.mWifiManager.getSoftApConfiguration();
        builder.mSummaryText =
                (softApConfiguration == null
                                ? ApnSettings.MVNO_NONE
                                : softApConfiguration.getSsid())
                        .toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_hotspot);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        return this.mWifiManager.isWifiApEnabled();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mAppContext, UserHandle.myUserId(), "no_config_tethering");
        if (checkIfRestrictionEnforced != null) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    this.mAppContext, checkIfRestrictionEnforced);
        } else {
            ((ConnectivityManager) this.mAppContext.getSystemService("connectivity"))
                    .stopTethering(0);
        }
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = TetherSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 35;
        subSettingLauncher.setTitleRes(R.string.tether_settings_title_all, null);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mAppContext.registerReceiver(this.mReceiver, WIFI_AP_STATE_FILTER);
        this.mConditionManager.onConditionChanged();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mAppContext.unregisterReceiver(this.mReceiver);
    }
}
