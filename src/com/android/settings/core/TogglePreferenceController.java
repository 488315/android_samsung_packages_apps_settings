package com.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.TwoStateButtonPreference;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.instrumentation.SettingsJankMonitor;
import com.android.settingslib.widget.MainSwitchPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TogglePreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "TogglePrefController";

    public TogglePreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(
            CompoundButton compoundButton, boolean z) {
        SettingsJankMonitor.detectToggleJank(compoundButton, getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof MainSwitchPreference) {
            ((MainSwitchPreference) findPreference)
                    .addOnSwitchChangeListener(
                            new CompoundButton
                                    .OnCheckedChangeListener() { // from class:
                                                                 // com.android.settings.core.TogglePreferenceController$$ExternalSyntheticLambda0
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(
                                        CompoundButton compoundButton, boolean z) {
                                    TogglePreferenceController.this.lambda$displayPreference$0(
                                            compoundButton, z);
                                }
                            });
        }
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getControlType() {
        return 1;
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_network;
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
    public ControlValue getValue() {
        CharSequence summary = getSummary();
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(getThreadEnabled()));
        builder.mSummary = summary == null ? null : (String) summary;
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    /* renamed from: isChecked */
    public abstract boolean getThreadEnabled();

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isPublicSlice() {
        return false;
    }

    public boolean isSliceable() {
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if ((preference instanceof PrimarySwitchPreference)
                || (preference instanceof TwoStateButtonPreference)) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl
                    .getMetricsFeatureProvider()
                    .logClickedPreference(preference, getMetricsCategory());
        }
        if (FeatureFactoryImpl._factory == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        if (preference instanceof SecSwitchPreferenceScreen) {
            SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                    (SecSwitchPreferenceScreen) preference;
            if (secSwitchPreferenceScreen.mSwitch != null) {
                SettingsJankMonitor.detectToggleJank(
                        secSwitchPreferenceScreen.mSwitch, secSwitchPreferenceScreen.getKey());
            }
        }
        return setChecked(((Boolean) obj).booleanValue());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public abstract boolean setChecked(boolean z);

    public boolean setCheckedForAction(boolean z) {
        return setChecked(z);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        boolean parseBoolean = Boolean.parseBoolean(controlValue.getValue());
        if (parseBoolean != getThreadEnabled()) {
            if (needUserInteraction(Boolean.valueOf(parseBoolean))
                            != Controllable$ControllableType.NO_INTERACTION
                    && controlValue.mForceChange) {
                ignoreUserInteraction();
            }
            if (!setChecked(parseBoolean)) {
                if (getControlErrorCode() != ControlResult.ErrorCode.REQUEST_USER_INTERACTION) {
                    builder.mResultCode = ControlResult.ResultCode.FAIL;
                    builder.mErrorCode = getControlErrorCode();
                    builder.setErrorMsg(getControlErrorMessage());
                    return new ControlResult(builder);
                }
                builder.mResultCode = ControlResult.ResultCode.REQUEST_SUCCESS;
            }
        }
        return new ControlResult(builder);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof TwoStatePreference) {
            ((TwoStatePreference) preference).setChecked(getThreadEnabled());
            return;
        }
        if (preference instanceof PrimarySwitchPreference) {
            ((PrimarySwitchPreference) preference).setChecked(getThreadEnabled());
        } else if (preference instanceof TwoStateButtonPreference) {
            ((TwoStateButtonPreference) preference).setChecked(getThreadEnabled());
        } else {
            refreshSummary(preference);
        }
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
