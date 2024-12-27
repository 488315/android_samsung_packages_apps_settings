package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.network.AllowedNetworkTypesListener;
import com.android.settings.widget.PreferenceCategoryController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkPreferenceCategoryController extends PreferenceCategoryController
        implements LifecycleObserver {
    private AllowedNetworkTypesListener mAllowedNetworkTypesListener;
    private PreferenceScreen mPreferenceScreen;
    protected int mSubId;

    public NetworkPreferenceCategoryController(Context context, String str) {
        super(context, str);
        this.mSubId = -1;
        AllowedNetworkTypesListener allowedNetworkTypesListener =
                new AllowedNetworkTypesListener(context.getMainExecutor());
        this.mAllowedNetworkTypesListener = allowedNetworkTypesListener;
        allowedNetworkTypesListener.setAllowedNetworkTypesListener(
                new AllowedNetworkTypesListener
                        .OnAllowedNetworkTypesListener() { // from class:
                                                           // com.android.settings.network.telephony.NetworkPreferenceCategoryController$$ExternalSyntheticLambda0
                    @Override // com.android.settings.network.AllowedNetworkTypesListener.OnAllowedNetworkTypesListener
                    public final void onAllowedNetworkTypesChanged() {
                        NetworkPreferenceCategoryController.this.lambda$new$0();
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updatePreference, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        displayPreference(this.mPreferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public NetworkPreferenceCategoryController init(int i) {
        this.mSubId = i;
        return this;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        AllowedNetworkTypesListener allowedNetworkTypesListener = this.mAllowedNetworkTypesListener;
        Context context = this.mContext;
        int i = this.mSubId;
        allowedNetworkTypesListener.getClass();
        ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                .createForSubscriptionId(i)
                .registerTelephonyCallback(
                        allowedNetworkTypesListener.mExecutor, allowedNetworkTypesListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        AllowedNetworkTypesListener allowedNetworkTypesListener = this.mAllowedNetworkTypesListener;
        Context context = this.mContext;
        int i = this.mSubId;
        allowedNetworkTypesListener.getClass();
        ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                .createForSubscriptionId(i)
                .unregisterTelephonyCallback(allowedNetworkTypesListener);
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
