package com.android.settings.notification.syncacrossdevices;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SyncAcrossDevicesPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private PreferenceGroup mPreferenceGroup;
    private SyncAcrossDevicesFeatureUpdater mSyncAcrossDevicesFeatureUpdater;
    private static final String TAG = "SyncXDevicesPrefCtr";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public SyncAcrossDevicesPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((SyncAcrossDevicesFeatureProviderImpl)
                        featureFactoryImpl.syncAcrossDevicesFeatureProvider$delegate.getValue())
                .getClass();
        this.mSyncAcrossDevicesFeatureUpdater =
                new SyncAcrossDevicesFeatureProviderImpl.AnonymousClass1();
    }

    private void updatePreferenceVisibility() {
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        preferenceGroup.setVisible(preferenceGroup.getPreferenceCount() > 0);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.setVisible(false);
        if (isAvailable()) {
            this.mSyncAcrossDevicesFeatureUpdater.getClass();
            this.mSyncAcrossDevicesFeatureUpdater.getClass();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mSyncAcrossDevicesFeatureUpdater != null ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void onFeatureAdded(Preference preference) {
        if (preference != null) {
            this.mPreferenceGroup.addPreference(preference);
            updatePreferenceVisibility();
        } else if (DEBUG) {
            Log.d(TAG, "onFeatureAdded receives null preference. Ignore.");
        }
    }

    public void onFeatureRemoved(Preference preference) {
        if (preference != null) {
            this.mPreferenceGroup.removePreference(preference);
            updatePreferenceVisibility();
        } else if (DEBUG) {
            Log.d(TAG, "onFeatureRemoved receives null preference. Ignore.");
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setPreferenceGroup(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
