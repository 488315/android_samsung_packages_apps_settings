package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.SubscriptionsChangeListener;
import com.android.settings.network.ims.WifiCallingQueryImsState;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkProviderWifiCallingGroup extends AbstractPreferenceController
        implements LifecycleObserver,
                SubscriptionsChangeListener.SubscriptionsChangeListenerClient {
    protected CarrierConfigManager mCarrierConfigManager;
    public SubscriptionsChangeListener mChangeListener;
    public PreferenceGroup mPreferenceGroup;
    public String mPreferenceGroupKey;
    public Map mSimCallManagerList;
    public List mSubInfoListForWfc;
    public SubscriptionManager mSubscriptionManager;
    public PhoneCallStateTelephonyCallback mTelephonyCallback;
    public Map mTelephonyManagerList;
    public Map mWifiCallingForSubPreferences;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PhoneCallStateTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.CallStateListener {
        public TelephonyManager mTelephonyManager;

        public PhoneCallStateTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            NetworkProviderWifiCallingGroup.this.update$3$1();
        }
    }

    public static /* synthetic */ void $r8$lambda$RN47xIqNaj73GlT_prCIMisg8uw(
            NetworkProviderWifiCallingGroup networkProviderWifiCallingGroup,
            SubscriptionInfo subscriptionInfo) {
        networkProviderWifiCallingGroup.getClass();
        Intent intent =
                new Intent(
                        networkProviderWifiCallingGroup.mContext,
                        (Class<?>) Settings.WifiCallingSettingsActivity.class);
        intent.putExtra("android.provider.extra.SUB_ID", subscriptionInfo.getSubscriptionId());
        networkProviderWifiCallingGroup.mContext.startActivity(intent);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(this.mPreferenceGroupKey);
        update$3$1();
    }

    public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) {
        return (PhoneAccountHandle) ((HashMap) this.mSimCallManagerList).get(Integer.valueOf(i));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "provider_model_wfc_group";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        List list = this.mSubInfoListForWfc;
        if (list != null) {
            return ((ArrayList) list).size() >= 1;
        }
        Log.d("NetworkProviderWifiCallingGroup", "No active subscriptions, hide the controller");
        return false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        TelephonyManager telephonyManager;
        SubscriptionsChangeListener subscriptionsChangeListener = this.mChangeListener;
        if (subscriptionsChangeListener != null) {
            subscriptionsChangeListener.stop();
        }
        PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback = this.mTelephonyCallback;
        if (phoneCallStateTelephonyCallback == null
                || (telephonyManager = phoneCallStateTelephonyCallback.mTelephonyManager) == null) {
            return;
        }
        telephonyManager.unregisterTelephonyCallback(phoneCallStateTelephonyCallback);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        SubscriptionsChangeListener subscriptionsChangeListener = this.mChangeListener;
        if (subscriptionsChangeListener != null) {
            subscriptionsChangeListener.start();
        }
        updateListener();
        update$3$1();
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onSubscriptionsChanged() {
        Log.d("NetworkProviderWifiCallingGroup", "onSubscriptionsChanged:");
        setSubscriptionInfoList(this.mContext);
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        if (preferenceGroup != null) {
            preferenceGroup.setVisible(isAvailable());
        }
        updateListener();
        update$3$1();
    }

    public WifiCallingQueryImsState queryImsState(int i) {
        return new WifiCallingQueryImsState(this.mContext, i);
    }

    public final void setSubscriptionInfoList(final Context context) {
        ArrayList arrayList =
                new ArrayList(SubscriptionUtil.getActiveSubscriptions(this.mSubscriptionManager));
        this.mSubInfoListForWfc = arrayList;
        arrayList.removeIf(
                new Predicate() { // from class:
                                  // com.android.settings.network.telephony.NetworkProviderWifiCallingGroup$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        NetworkProviderWifiCallingGroup networkProviderWifiCallingGroup =
                                NetworkProviderWifiCallingGroup.this;
                        Context context2 = context;
                        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                        networkProviderWifiCallingGroup.getClass();
                        int subscriptionId = subscriptionInfo.getSubscriptionId();
                        ((HashMap) networkProviderWifiCallingGroup.mTelephonyManagerList)
                                .put(
                                        Integer.valueOf(subscriptionId),
                                        ((TelephonyManager)
                                                        context2.getSystemService(
                                                                TelephonyManager.class))
                                                .createForSubscriptionId(subscriptionId));
                        ((HashMap) networkProviderWifiCallingGroup.mSimCallManagerList)
                                .put(
                                        Integer.valueOf(subscriptionId),
                                        ((TelecomManager)
                                                        context2.getSystemService(
                                                                TelecomManager.class))
                                                .getSimCallManagerForSubscription(subscriptionId));
                        return !networkProviderWifiCallingGroup.shouldShowWifiCallingForSub(
                                        subscriptionId)
                                && ((ArrayList) networkProviderWifiCallingGroup.mSubInfoListForWfc)
                                        .contains(subscriptionInfo);
                    }
                });
        Log.d(
                "NetworkProviderWifiCallingGroup",
                "setSubscriptionInfoList: mSubInfoListForWfc size:"
                        + this.mSubInfoListForWfc.size());
    }

    public boolean shouldShowWifiCallingForSub(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        Context context = this.mContext;
        WifiCallingQueryImsState queryImsState = queryImsState(i);
        Drawable drawable = MobileNetworkUtils.EMPTY_DRAWABLE;
        if (queryImsState == null) {
            queryImsState = new WifiCallingQueryImsState(context, i);
        }
        return queryImsState.isReadyToWifiCalling();
    }

    public final void update$3$1() {
        if (this.mPreferenceGroup == null) {
            Log.d("NetworkProviderWifiCallingGroup", "mPreferenceGroup == null");
            return;
        }
        if (!isAvailable()) {
            Iterator it = ((ArrayMap) this.mWifiCallingForSubPreferences).values().iterator();
            while (it.hasNext()) {
                this.mPreferenceGroup.removePreference((Preference) it.next());
            }
            ((ArrayMap) this.mWifiCallingForSubPreferences).clear();
            return;
        }
        Map map = this.mWifiCallingForSubPreferences;
        this.mWifiCallingForSubPreferences = new ArrayMap();
        Iterator it2 = ((ArrayList) this.mSubInfoListForWfc).iterator();
        int i = 10;
        while (it2.hasNext()) {
            final SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it2.next();
            int subscriptionId = subscriptionInfo.getSubscriptionId();
            if (shouldShowWifiCallingForSub(subscriptionId)) {
                Preference preference =
                        (Preference) ((ArrayMap) map).remove(Integer.valueOf(subscriptionId));
                if (preference == null) {
                    preference = new Preference(this.mPreferenceGroup.getContext());
                    this.mPreferenceGroup.addPreference(preference);
                }
                CharSequence uniqueSubscriptionDisplayName =
                        SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                this.mContext, subscriptionInfo);
                if (getPhoneAccountHandleForSubscriptionId(subscriptionId) != null) {
                    Context context = this.mContext;
                    PhoneAccountHandle phoneAccountHandleForSubscriptionId =
                            getPhoneAccountHandleForSubscriptionId(subscriptionId);
                    Intent buildConfigureIntent =
                            MobileNetworkUtils.buildConfigureIntent(
                                    context,
                                    phoneAccountHandleForSubscriptionId,
                                    "android.telecom.action.CONFIGURE_PHONE_ACCOUNT");
                    if (buildConfigureIntent == null) {
                        buildConfigureIntent =
                                MobileNetworkUtils.buildConfigureIntent(
                                        context,
                                        phoneAccountHandleForSubscriptionId,
                                        "android.telecom.action.CONNECTION_SERVICE_CONFIGURE");
                    }
                    if (buildConfigureIntent != null) {
                        PackageManager packageManager = this.mContext.getPackageManager();
                        uniqueSubscriptionDisplayName =
                                packageManager
                                        .queryIntentActivities(buildConfigureIntent, 0)
                                        .get(0)
                                        .loadLabel(packageManager);
                        preference.setIntent(buildConfigureIntent);
                    }
                }
                preference.setTitle(uniqueSubscriptionDisplayName);
                preference.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.network.telephony.NetworkProviderWifiCallingGroup$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference2) {
                                NetworkProviderWifiCallingGroup
                                        .$r8$lambda$RN47xIqNaj73GlT_prCIMisg8uw(
                                                NetworkProviderWifiCallingGroup.this,
                                                subscriptionInfo);
                                return true;
                            }
                        });
                preference.setEnabled(
                        ((TelephonyManager)
                                                ((HashMap) this.mTelephonyManagerList)
                                                        .get(Integer.valueOf(subscriptionId)))
                                        .getCallStateForSubscription()
                                == 0);
                int i2 = i + 1;
                preference.setOrder(i);
                preference.setSummary(
                        queryImsState(subscriptionId).isEnabledByUser()
                                ? R.string.calls_sms_wfc_summary
                                : 17043585);
                ((ArrayMap) this.mWifiCallingForSubPreferences)
                        .put(Integer.valueOf(subscriptionId), preference);
                i = i2;
            }
        }
        Iterator it3 = ((ArrayMap) map).values().iterator();
        while (it3.hasNext()) {
            this.mPreferenceGroup.removePreference((Preference) it3.next());
        }
    }

    public final void updateListener() {
        Iterator it = ((ArrayList) this.mSubInfoListForWfc).iterator();
        while (it.hasNext()) {
            int subscriptionId = ((SubscriptionInfo) it.next()).getSubscriptionId();
            PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback =
                    this.mTelephonyCallback;
            if (phoneCallStateTelephonyCallback != null) {
                Context context = this.mContext;
                TelephonyManager telephonyManager =
                        (TelephonyManager)
                                ((HashMap)
                                                NetworkProviderWifiCallingGroup.this
                                                        .mTelephonyManagerList)
                                        .get(Integer.valueOf(subscriptionId));
                phoneCallStateTelephonyCallback.mTelephonyManager = telephonyManager;
                telephonyManager.registerTelephonyCallback(
                        context.getMainExecutor(), phoneCallStateTelephonyCallback);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        update$3$1();
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onAirplaneModeChanged(boolean z) {}
}
