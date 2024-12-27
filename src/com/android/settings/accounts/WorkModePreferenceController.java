package com.android.settings.accounts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.CompoundButton;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SettingsMainSwitchPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WorkModePreferenceController extends BasePreferenceController
        implements CompoundButton.OnCheckedChangeListener,
                DefaultLifecycleObserver,
                ManagedProfileQuietModeEnabler.QuietModeChangeListener {
    protected SettingsMainSwitchPreference mPreference;
    private final ManagedProfileQuietModeEnabler mQuietModeEnabler;

    public WorkModePreferenceController(Context context, String str) {
        super(context, str);
        this.mQuietModeEnabler = new ManagedProfileQuietModeEnabler(context, this);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SettingsMainSwitchPreference settingsMainSwitchPreference =
                (SettingsMainSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = settingsMainSwitchPreference;
        settingsMainSwitchPreference.addOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mQuietModeEnabler.mManagedProfile != null ? 0 : 4;
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
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_accounts;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getSliceType() {
        return 1;
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

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        ManagedProfileQuietModeEnabler managedProfileQuietModeEnabler = this.mQuietModeEnabler;
        boolean z2 = !z;
        if (managedProfileQuietModeEnabler.mManagedProfile != null
                && managedProfileQuietModeEnabler.isQuietModeEnabled() != z2) {
            managedProfileQuietModeEnabler.mUserManager.requestQuietModeEnabled(
                    z2, managedProfileQuietModeEnabler.mManagedProfile);
        }
        updateState(this.mPreference);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // com.android.settings.accounts.ManagedProfileQuietModeEnabler.QuietModeChangeListener
    public void onQuietModeChanged() {
        updateState(this.mPreference);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this.mQuietModeEnabler);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().removeObserver(this.mQuietModeEnabler);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        SettingsMainSwitchPreference settingsMainSwitchPreference = this.mPreference;
        boolean z = !this.mQuietModeEnabler.isQuietModeEnabled();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsMainSwitchPreference.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setTitle(settingsMainSwitchPreference.getTitle());
            settingsMainSwitchPreference.mMainSwitchBar.show();
        }
        settingsMainSwitchPreference.setChecked(z);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
