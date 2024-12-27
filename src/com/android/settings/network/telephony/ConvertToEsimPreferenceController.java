package com.android.settings.network.telephony;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.telephony.util.TelephonyUtils;
import com.android.settings.development.DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0;
import com.android.settings.network.MobileNetworkRepository;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConvertToEsimPreferenceController extends TelephonyBasePreferenceController
        implements LifecycleObserver, MobileNetworkRepository.MobileNetworkCallback {
    private static final String TAG = "ConvertToEsimPreference";
    private static int sQueryFlag = 269484096;
    private LifecycleOwner mLifecycleOwner;
    private MobileNetworkRepository mMobileNetworkRepository;
    private Preference mPreference;
    private SubscriptionInfoEntity mSubscriptionInfoEntity;
    private List<SubscriptionInfoEntity> mSubscriptionInfoEntityList;

    public ConvertToEsimPreferenceController(
            Context context,
            String str,
            Lifecycle lifecycle,
            LifecycleOwner lifecycleOwner,
            int i) {
        this(context, str);
        this.mSubId = i;
        this.mLifecycleOwner = lifecycleOwner;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    private boolean findConversionSupportComponent() {
        Intent intent = new Intent("android.service.euicc.action.CONVERT_TO_EMBEDDED_SUBSCRIPTION");
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentActivities =
                packageManager.queryIntentActivities(intent, sQueryFlag);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            return false;
        }
        Iterator<ResolveInfo> it = queryIntentActivities.iterator();
        while (it.hasNext() && !isValidEuiccComponent(packageManager, it.next())) {}
        return true;
    }

    private boolean isActiveSubscription(int i) {
        return ((SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class))
                        .getActiveSubscriptionInfo(i)
                != null;
    }

    private boolean isValidEuiccComponent(PackageManager packageManager, ResolveInfo resolveInfo) {
        String str;
        IntentFilter intentFilter;
        ComponentInfo componentInfo = TelephonyUtils.getComponentInfo(resolveInfo);
        if (packageManager.checkPermission(
                        "android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS",
                        new ComponentName(componentInfo.packageName, componentInfo.name)
                                .getPackageName())
                != 0) {
            return false;
        }
        if (!(componentInfo instanceof ServiceInfo)) {
            if (componentInfo instanceof ActivityInfo) {
                str = ((ActivityInfo) componentInfo).permission;
            }
        }
        str = ((ServiceInfo) componentInfo).permission;
        return (!TextUtils.equals(str, "android.permission.BIND_EUICC_SERVICE")
                        || (intentFilter = resolveInfo.filter) == null
                        || intentFilter.getPriority() == 0)
                ? false
                : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActiveSubInfoChanged$0(
            SubscriptionInfoEntity subscriptionInfoEntity) {
        if (Integer.parseInt(subscriptionInfoEntity.subId) == this.mSubId) {
            this.mSubscriptionInfoEntity = subscriptionInfoEntity;
            update();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        SubscriptionInfoEntity subscriptionInfoEntity;
        SubscriptionInfo activeSubscriptionInfo =
                ((SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class))
                        .getActiveSubscriptionInfo(i);
        if (activeSubscriptionInfo == null) {
            return 2;
        }
        try {
            if (((EuiccManager) this.mContext.getSystemService("euicc"))
                    .isPsimConversionSupported(activeSubscriptionInfo.getCarrierId())) {
                return (findConversionSupportComponent()
                                && (subscriptionInfoEntity = this.mSubscriptionInfoEntity) != null
                                && subscriptionInfoEntity.isActiveSubscriptionId
                                && !subscriptionInfoEntity.isEmbedded
                                && isActiveSubscription(i))
                        ? 0
                        : 2;
            }
            Log.i(
                    TAG,
                    "subId is not matched with pSIM conversion supported carriers:"
                            + activeSubscriptionInfo.getCarrierId());
            return 2;
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Fail to check pSIM conversion supported carrier: "), TAG);
        }
        return 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        Intent intent =
                new Intent("android.telephony.euicc.action.CONVERT_TO_EMBEDDED_SUBSCRIPTION");
        intent.putExtra("subId", this.mSubId);
        this.mContext.startActivity(intent);
        ((Activity) this.mContext).finish();
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(int i, SubscriptionInfoEntity subscriptionInfoEntity) {
        this.mSubId = i;
        this.mSubscriptionInfoEntity = subscriptionInfoEntity;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public void onActiveSubInfoChanged(List<SubscriptionInfoEntity> list) {
        this.mSubscriptionInfoEntityList = list;
        list.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.network.telephony.ConvertToEsimPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ConvertToEsimPreferenceController.this.lambda$onActiveSubInfoChanged$0(
                                (SubscriptionInfoEntity) obj);
                    }
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mMobileNetworkRepository.addRegister(this.mLifecycleOwner, this, this.mSubId);
        this.mMobileNetworkRepository.updateEntity();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mMobileNetworkRepository.removeRegister(this);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setSubscriptionInfoEntity(SubscriptionInfoEntity subscriptionInfoEntity) {
        this.mSubscriptionInfoEntity = subscriptionInfoEntity;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    public void update() {
        Preference preference = this.mPreference;
        if (preference == null) {
            return;
        }
        preference.setVisible(getAvailabilityStatus(this.mSubId) == 0);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public ConvertToEsimPreferenceController(Context context, String str) {
        super(context, str);
        this.mSubscriptionInfoEntityList = new ArrayList();
        this.mMobileNetworkRepository = MobileNetworkRepository.getInstance(context);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAirplaneModeChanged(boolean z) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllMobileNetworkInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllUiccInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAvailableSubInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onCallStateChanged(int i) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onDataRoamingChanged(int i, boolean z) {}
}
