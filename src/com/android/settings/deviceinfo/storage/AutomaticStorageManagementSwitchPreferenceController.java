package com.android.settings.deviceinfo.storage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.provider.Settings;

import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.deletionhelper.ActivationWarningFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.GenericSwitchController;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.Utils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutomaticStorageManagementSwitchPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, SwitchWidgetController.OnSwitchChangeListener {
    static final String STORAGE_MANAGER_ENABLED_BY_DEFAULT_PROPERTY = "ro.storage_manager.enabled";
    private FragmentManager mFragmentManager;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private PrimarySwitchPreference mSwitch;
    private GenericSwitchController mSwitchController;

    public AutomaticStorageManagementSwitchPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSwitch =
                (PrimarySwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mContext.getResources().getBoolean(R.bool.config_show_smart_storage_toggle)
                        && !ActivityManager.isLowRamDeviceStatic())
                ? 0
                : 3;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (isAvailable()) {
            this.mSwitch.setChecked(Utils.isStorageManagerEnabled(this.mContext));
            PrimarySwitchPreference primarySwitchPreference = this.mSwitch;
            if (primarySwitchPreference != null) {
                GenericSwitchController genericSwitchController =
                        new GenericSwitchController(primarySwitchPreference);
                this.mSwitchController = genericSwitchController;
                genericSwitchController.mListener = this;
                genericSwitchController.startListening();
            }
        }
    }

    @Override // com.android.settings.widget.SwitchWidgetController.OnSwitchChangeListener
    public boolean onSwitchToggled(boolean z) {
        this.mMetricsFeatureProvider.action(this.mContext, 489, z);
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "automatic_storage_manager_enabled", z ? 1 : 0);
        boolean z2 =
                SystemProperties.getBoolean(STORAGE_MANAGER_ENABLED_BY_DEFAULT_PROPERTY, false);
        boolean z3 =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "automatic_storage_manager_turned_off_by_policy",
                                0)
                        != 0;
        if (z && (!z2 || z3)) {
            new ActivationWarningFragment()
                    .show(this.mFragmentManager, "ActivationWarningFragment");
        }
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public AutomaticStorageManagementSwitchPreferenceController setFragmentManager(
            FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        return this;
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
