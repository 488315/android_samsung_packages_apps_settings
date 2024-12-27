package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileNetworkPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver {
    static final String KEY_MOBILE_NETWORK_SETTINGS = "mobile_network_settings";
    public final AnonymousClass1 mAirplanModeChangedReceiver;
    public final boolean mIsSecondaryUser;
    public Preference mPreference;
    MobileNetworkTelephonyCallback mTelephonyCallback;
    public final TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MobileNetworkTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.ServiceStateListener {
        public MobileNetworkTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            MobileNetworkPreferenceController mobileNetworkPreferenceController =
                    MobileNetworkPreferenceController.this;
            mobileNetworkPreferenceController.updateState(
                    mobileNetworkPreferenceController.mPreference);
        }
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.settings.network.MobileNetworkPreferenceController$1] */
    public MobileNetworkPreferenceController(Context context) {
        super(context);
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mIsSecondaryUser = !userManager.isAdminUser();
        this.mAirplanModeChangedReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.network.MobileNetworkPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        MobileNetworkPreferenceController mobileNetworkPreferenceController =
                                MobileNetworkPreferenceController.this;
                        mobileNetworkPreferenceController.updateState(
                                mobileNetworkPreferenceController.mPreference);
                    }
                };
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(KEY_MOBILE_NETWORK_SETTINGS);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_MOBILE_NETWORK_SETTINGS;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        Context context = this.mContext;
        Drawable drawable = MobileNetworkUtils.EMPTY_DRAWABLE;
        int defaultSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        SubscriptionInfo activeSubscriptionInfo =
                subscriptionManager == null
                        ? null
                        : subscriptionManager
                                .createForAllUserProfiles()
                                .getActiveSubscriptionInfo(defaultSubscriptionId);
        if (activeSubscriptionInfo != null) {
            return activeSubscriptionInfo.getCarrierName();
        }
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        return telephonyManager != null ? telephonyManager.getNetworkOperatorName() : null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!KEY_MOBILE_NETWORK_SETTINGS.equals(preference.getKey())) {
            return false;
        }
        Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        this.mContext.startActivity(intent);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return (this.mIsSecondaryUser
                        || RestrictedLockUtilsInternal.hasBaseUserRestriction(
                                this.mContext, UserHandle.myUserId(), "no_config_mobile_networks")
                        || Utils.isWifiOnly(this.mContext))
                ? false
                : true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (isAvailable()) {
            if (this.mTelephonyCallback == null) {
                this.mTelephonyCallback = new MobileNetworkTelephonyCallback();
            }
            this.mTelephonyManager.registerTelephonyCallback(
                    this.mContext.getMainExecutor(), this.mTelephonyCallback);
        }
        AnonymousClass1 anonymousClass1 = this.mAirplanModeChangedReceiver;
        if (anonymousClass1 != null) {
            this.mContext.registerReceiver(
                    anonymousClass1, new IntentFilter("android.intent.action.AIRPLANE_MODE"));
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        MobileNetworkTelephonyCallback mobileNetworkTelephonyCallback = this.mTelephonyCallback;
        if (mobileNetworkTelephonyCallback != null) {
            this.mTelephonyManager.unregisterTelephonyCallback(mobileNetworkTelephonyCallback);
        }
        AnonymousClass1 anonymousClass1 = this.mAirplanModeChangedReceiver;
        if (anonymousClass1 != null) {
            this.mContext.unregisterReceiver(anonymousClass1);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if ((preference instanceof RestrictedPreference)
                && ((RestrictedPreference) preference).mHelper.mDisabledByAdmin) {
            return;
        }
        preference.setEnabled(
                Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                        == 0);
    }
}
