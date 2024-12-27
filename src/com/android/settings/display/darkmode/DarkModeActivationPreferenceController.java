package com.android.settings.display.darkmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.MainSwitchPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DarkModeActivationPreferenceController extends BasePreferenceController
        implements CompoundButton.OnCheckedChangeListener {
    private TimeFormatter mFormat;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private MainSwitchPreference mPreference;
    private final UiModeManager mUiModeManager;

    public DarkModeActivationPreferenceController(Context context, String str) {
        super(context, str);
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        this.mFormat = new TimeFormatter(context);
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
        MainSwitchPreference mainSwitchPreference =
                (MainSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = mainSwitchPreference;
        mainSwitchPreference.addOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        boolean z = (this.mContext.getResources().getConfiguration().uiMode & 32) != 0;
        int nightMode = this.mUiModeManager.getNightMode();
        if (nightMode == 0) {
            return this.mContext.getString(
                    z
                            ? R.string.dark_ui_summary_on_auto_mode_auto
                            : R.string.dark_ui_summary_off_auto_mode_auto);
        }
        if (nightMode != 3) {
            return this.mContext.getString(
                    z
                            ? R.string.dark_ui_summary_on_auto_mode_never
                            : R.string.dark_ui_summary_off_auto_mode_never);
        }
        if (this.mUiModeManager.getNightModeCustomType() == 1) {
            return this.mContext.getString(
                    z
                            ? R.string.dark_ui_summary_on_auto_mode_custom_bedtime
                            : R.string.dark_ui_summary_off_auto_mode_custom_bedtime);
        }
        return this.mContext.getString(
                z
                        ? R.string.dark_ui_summary_on_auto_mode_custom
                        : R.string.dark_ui_summary_off_auto_mode_custom,
                this.mFormat.of(
                        z
                                ? this.mUiModeManager.getCustomNightModeEnd()
                                : this.mUiModeManager.getCustomNightModeStart()));
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
        this.mMetricsFeatureProvider.logClickedPreference(this.mPreference, getMetricsCategory());
        this.mUiModeManager.setNightModeActivated(
                !((this.mContext.getResources().getConfiguration().uiMode & 32) != 0));
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
        this.mPreference.updateStatus(
                (this.mContext.getResources().getConfiguration().uiMode & 32) != 0);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public DarkModeActivationPreferenceController(
            Context context, String str, TimeFormatter timeFormatter) {
        this(context, str);
        this.mFormat = timeFormatter;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
