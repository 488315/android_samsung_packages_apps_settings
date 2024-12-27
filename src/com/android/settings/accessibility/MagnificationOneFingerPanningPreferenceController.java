package com.android.settings.accessibility;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MagnificationOneFingerPanningPreferenceController
        extends MagnificationFeaturePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    static final String PREF_KEY = "accessibility_single_finger_panning_enabled";
    final ContentObserver mContentObserver;
    final boolean mDefaultValue;
    private TwoStatePreference mSwitchPreference;

    public MagnificationOneFingerPanningPreferenceController(Context context) {
        super(context, PREF_KEY);
        boolean z;
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.accessibility.MagnificationOneFingerPanningPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z2, Uri uri) {
                        MagnificationOneFingerPanningPreferenceController
                                magnificationOneFingerPanningPreferenceController =
                                        MagnificationOneFingerPanningPreferenceController.this;
                        magnificationOneFingerPanningPreferenceController.updateState(
                                magnificationOneFingerPanningPreferenceController
                                        .mSwitchPreference);
                    }
                };
        try {
            z = context.getResources().getBoolean(R.bool.config_faceAuthSupportsSelfIllumination);
        } catch (Resources.NotFoundException unused) {
            z = false;
        }
        this.mDefaultValue = z;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSwitchPreference = twoStatePreference;
        updateState(twoStatePreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isInSetupWizard() ? 2 : 0;
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return com.android.settings.R.string.menu_key_accessibility;
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return !this.mSwitchPreference.isEnabled()
                ? this.mContext.getString(
                        com.android.settings.R.string
                                .accessibility_magnification_one_finger_panning_summary_unavailable)
                : getThreadEnabled()
                        ? this.mContext.getString(
                                com.android.settings.R.string
                                        .accessibility_magnification_one_finger_panning_summary_on)
                        : this.mContext.getString(
                                com.android.settings.R.string
                                        .accessibility_magnification_one_finger_panning_summary_off);
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), PREF_KEY, this.mDefaultValue ? 1 : 0)
                == 1;
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        Context context = this.mContext;
        context.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        Context context = this.mContext;
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("accessibility_magnification_capability"),
                        false,
                        this.mContentObserver);
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean putInt =
                Settings.Secure.putInt(this.mContext.getContentResolver(), PREF_KEY, z ? 1 : 0);
        refreshSummary(this.mSwitchPreference);
        return putInt;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        int capabilities = MagnificationCapabilities.getCapabilities(this.mContext);
        boolean z = true;
        if (capabilities != 1 && capabilities != 3) {
            z = false;
        }
        preference.setEnabled(z);
        refreshSummary(preference);
    }

    @Override // com.android.settings.accessibility.MagnificationFeaturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
