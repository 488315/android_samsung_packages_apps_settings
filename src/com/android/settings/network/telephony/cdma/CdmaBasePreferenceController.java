package com.android.settings.network.telephony.cdma;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.android.settings.network.AllowedNetworkTypesListener;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.TelephonyBasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CdmaBasePreferenceController extends TelephonyBasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private AllowedNetworkTypesListener mAllowedNetworkTypesListener;
    protected Preference mPreference;
    protected PreferenceManager mPreferenceManager;
    protected TelephonyManager mTelephonyManager;

    public CdmaBasePreferenceController(Context context, String str) {
        super(context, str);
        this.mSubId = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updatePreference, reason: merged with bridge method [inline-methods] */
    public void lambda$init$0() {
        Preference preference = this.mPreference;
        if (preference != null) {
            updateState(preference);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        if (findPreference instanceof CdmaListPreference) {
            CdmaListPreference cdmaListPreference = (CdmaListPreference) findPreference;
            cdmaListPreference.mTelephonyManager =
                    ((TelephonyManager)
                                    cdmaListPreference
                                            .getContext()
                                            .getSystemService(TelephonyManager.class))
                            .createForSubscriptionId(this.mSubId);
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        return MobileNetworkUtils.isCdmaOptions(this.mContext, i) ? 0 : 2;
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

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(PreferenceManager preferenceManager, int i) {
        this.mPreferenceManager = preferenceManager;
        this.mSubId = i;
        this.mTelephonyManager =
                ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(this.mSubId);
        if (this.mAllowedNetworkTypesListener == null) {
            AllowedNetworkTypesListener allowedNetworkTypesListener =
                    new AllowedNetworkTypesListener(this.mContext.getMainExecutor());
            this.mAllowedNetworkTypesListener = allowedNetworkTypesListener;
            allowedNetworkTypesListener.setAllowedNetworkTypesListener(
                    new AllowedNetworkTypesListener
                            .OnAllowedNetworkTypesListener() { // from class:
                                                               // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController$$ExternalSyntheticLambda0
                        @Override // com.android.settings.network.AllowedNetworkTypesListener.OnAllowedNetworkTypesListener
                        public final void onAllowedNetworkTypesChanged() {
                            CdmaBasePreferenceController.this.lambda$init$0();
                        }
                    });
        }
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        AllowedNetworkTypesListener allowedNetworkTypesListener = this.mAllowedNetworkTypesListener;
        if (allowedNetworkTypesListener != null) {
            Context context = this.mContext;
            ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                    .createForSubscriptionId(this.mSubId)
                    .registerTelephonyCallback(
                            allowedNetworkTypesListener.mExecutor, allowedNetworkTypesListener);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        AllowedNetworkTypesListener allowedNetworkTypesListener = this.mAllowedNetworkTypesListener;
        if (allowedNetworkTypesListener != null) {
            Context context = this.mContext;
            ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                    .createForSubscriptionId(this.mSubId)
                    .unregisterTelephonyCallback(allowedNetworkTypesListener);
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void init(int i) {
        init(null, i);
    }
}
