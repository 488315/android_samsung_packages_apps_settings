package com.android.settings.network;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.slices.CustomSliceable;
import com.android.settingslib.Utils;
import com.android.settingslib.utils.ThreadUtils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProviderModelSliceHelper {
    public final Context mContext;
    public final CustomSliceable mSliceable;
    public final SubscriptionManager mSubscriptionManager;
    public TelephonyManager mTelephonyManager;

    public ProviderModelSliceHelper(Context context, CustomSliceable customSliceable) {
        this.mContext = context;
        this.mSliceable = customSliceable;
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
    }

    public Drawable getMobileDrawable(Drawable drawable) throws Throwable {
        ServiceState serviceState;
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            Log.d("ProviderModelSlice", "mTelephonyManager == null");
            return drawable;
        }
        ServiceState serviceState2 = telephonyManager.getServiceState();
        NetworkRegistrationInfo networkRegistrationInfo =
                serviceState2 == null ? null : serviceState2.getNetworkRegistrationInfo(2, 1);
        if ((networkRegistrationInfo == null ? false : networkRegistrationInfo.isRegistered())
                || ((serviceState = this.mTelephonyManager.getServiceState()) != null
                        && serviceState.getState() == 0)) {
            final Semaphore semaphore = new Semaphore(0);
            final AtomicReference atomicReference = new AtomicReference();
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.android.settings.network.ProviderModelSliceHelper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i;
                            ProviderModelSliceHelper providerModelSliceHelper =
                                    ProviderModelSliceHelper.this;
                            AtomicReference atomicReference2 = atomicReference;
                            Semaphore semaphore2 = semaphore;
                            SignalStrength signalStrength =
                                    providerModelSliceHelper.mTelephonyManager.getSignalStrength();
                            int level = signalStrength == null ? 0 : signalStrength.getLevel();
                            if (providerModelSliceHelper.mSubscriptionManager != null) {
                                int defaultDataSubscriptionId =
                                        SubscriptionManager.getDefaultDataSubscriptionId();
                                CarrierConfigManager carrierConfigManager =
                                        (CarrierConfigManager)
                                                providerModelSliceHelper.mContext.getSystemService(
                                                        CarrierConfigManager.class);
                                PersistableBundle configForSubId =
                                        carrierConfigManager != null
                                                ? carrierConfigManager.getConfigForSubId(
                                                        defaultDataSubscriptionId)
                                                : null;
                                if (configForSubId != null
                                        && configForSubId.getBoolean(
                                                "inflate_signal_strength_bool", false)) {
                                    level++;
                                    i = 6;
                                    atomicReference2.set(
                                            MobileNetworkUtils.getSignalStrengthIcon(
                                                    providerModelSliceHelper.mContext,
                                                    level,
                                                    i,
                                                    0,
                                                    false,
                                                    false));
                                    semaphore2.release();
                                }
                            }
                            i = 5;
                            atomicReference2.set(
                                    MobileNetworkUtils.getSignalStrengthIcon(
                                            providerModelSliceHelper.mContext,
                                            level,
                                            i,
                                            0,
                                            false,
                                            false));
                            semaphore2.release();
                        }
                    });
            semaphore.acquire();
            drawable = (Drawable) atomicReference.get();
        }
        drawable.setTint(Utils.getColorAttrDefaultColor(this.mContext, R.attr.colorControlNormal));
        if (MobileNetworkUtils.activeNetworkIsCellular(this.mContext)) {
            drawable.setTint(Utils.getColorAttrDefaultColor(this.mContext, R.attr.colorAccent));
        }
        return drawable;
    }

    public final String getMobileTitle() {
        String charSequence =
                this.mContext
                        .getText(com.android.settings.R.string.mobile_data_settings_title)
                        .toString();
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        if (subscriptionManager == null) {
            return charSequence;
        }
        SubscriptionInfo activeSubscriptionInfo =
                subscriptionManager.getActiveSubscriptionInfo(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        return activeSubscriptionInfo != null
                ? SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                this.mContext, activeSubscriptionInfo)
                        .toString()
                : charSequence;
    }
}
