package com.android.settings.dream;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.ArraySet;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.dream.DreamBackend;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DreamHomeControlsPreferenceController extends TogglePreferenceController {
    public static final String PREF_KEY = "dream_home_controls_toggle";
    private final DreamBackend mBackend;

    public DreamHomeControlsPreferenceController(Context context, DreamBackend dreamBackend) {
        super(context, PREF_KEY);
        this.mBackend = dreamBackend;
    }

    private boolean controlsEnabledOnLockscreen() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "lockscreen_show_controls", 0)
                != 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean contains = this.mBackend.mSupportedComplications.contains(6);
        if (controlsEnabledOnLockscreen()) {
            return contains ? 0 : 2;
        }
        return 5;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        if (controlsEnabledOnLockscreen()) {
            DreamBackend dreamBackend = this.mBackend;
            ArraySet arraySet =
                    dreamBackend.getComplicationsEnabled()
                            ? new ArraySet(dreamBackend.mSupportedComplications)
                            : new ArraySet();
            if (!dreamBackend.getHomeControlsEnabled()) {
                arraySet.remove(6);
            } else if (dreamBackend.mSupportedComplications.contains(6)) {
                arraySet.add(6);
            }
            if (arraySet.contains(6)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        DreamBackend dreamBackend = this.mBackend;
        Settings.Secure.putInt(
                dreamBackend.mContext.getContentResolver(),
                "screensaver_home_controls_enabled",
                z ? 1 : 0);
        dreamBackend.logDreamSettingChangeToStatsd(5);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
