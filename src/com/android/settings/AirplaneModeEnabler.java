package com.android.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.network.GlobalSettingsChangeListener;
import com.android.settings.network.ProxySubscriptionManager;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.WirelessUtils;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class AirplaneModeEnabler extends GlobalSettingsChangeListener {
    public final Context mContext;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final OnAirplaneModeChangedListener mOnAirplaneModeChangedListener;
    PhoneStateListener mPhoneStateListener;
    public final TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnAirplaneModeChangedListener {
        void onAirplaneModeChanged(boolean z);
    }

    public AirplaneModeEnabler(
            Context context, OnAirplaneModeChangedListener onAirplaneModeChangedListener) {
        super(context, "airplane_mode_on");
        this.mContext = context;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mOnAirplaneModeChangedListener = onAirplaneModeChangedListener;
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mPhoneStateListener =
                new PhoneStateListener(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.android.settings.AirplaneModeEnabler.1
                    public final void onRadioPowerStateChanged(int i) {
                        AirplaneModeEnabler airplaneModeEnabler = AirplaneModeEnabler.this;
                        OnAirplaneModeChangedListener onAirplaneModeChangedListener2 =
                                airplaneModeEnabler.mOnAirplaneModeChangedListener;
                        if (onAirplaneModeChangedListener2 != null) {
                            onAirplaneModeChangedListener2.onAirplaneModeChanged(
                                    WirelessUtils.isAirplaneModeOn(airplaneModeEnabler.mContext));
                        }
                    }
                };
    }

    public final boolean isInEcmMode() {
        try {
            if (this.mTelephonyManager.getEmergencyCallbackMode()) {
                return true;
            }
        } catch (UnsupportedOperationException unused) {
        }
        List activeSubscriptionsInfo =
                ProxySubscriptionManager.getInstance(this.mContext)
                        .mSubscriptionMonitor
                        .getActiveSubscriptionsInfo();
        if (activeSubscriptionsInfo == null) {
            return false;
        }
        Iterator it = activeSubscriptionsInfo.iterator();
        while (it.hasNext()) {
            TelephonyManager createForSubscriptionId =
                    this.mTelephonyManager.createForSubscriptionId(
                            ((SubscriptionInfo) it.next()).getSubscriptionId());
            if (createForSubscriptionId != null) {
                try {
                    if (createForSubscriptionId.getEmergencyCallbackMode()) {
                        return true;
                    }
                } catch (UnsupportedOperationException unused2) {
                    continue;
                }
            }
        }
        return false;
    }

    @Override // com.android.settings.network.GlobalSettingsChangeListener
    public final void onChanged$1() {
        OnAirplaneModeChangedListener onAirplaneModeChangedListener =
                this.mOnAirplaneModeChangedListener;
        if (onAirplaneModeChangedListener != null) {
            onAirplaneModeChangedListener.onAirplaneModeChanged(
                    WirelessUtils.isAirplaneModeOn(this.mContext));
        }
    }

    public final void setAirplaneMode(boolean z) {
        if (isInEcmMode()) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "ECM airplane mode=", "AirplaneModeEnabler", z);
        } else {
            this.mMetricsFeatureProvider.action(this.mContext, 177, z);
            setAirplaneModeOn(z);
        }
    }

    public final void setAirplaneModeOn(boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), "airplane_mode_on", z ? 1 : 0);
        OnAirplaneModeChangedListener onAirplaneModeChangedListener =
                this.mOnAirplaneModeChangedListener;
        if (onAirplaneModeChangedListener != null) {
            onAirplaneModeChangedListener.onAirplaneModeChanged(z);
        }
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.putExtra("state", z);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public final void start() {
        this.mTelephonyManager.listen(this.mPhoneStateListener, 8388608);
    }

    public final void stop() {
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
    }
}
