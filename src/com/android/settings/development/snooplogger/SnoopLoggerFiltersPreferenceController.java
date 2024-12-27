package com.android.settings.development.snooplogger;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.sysprop.BluetoothProperties;

import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SnoopLoggerFiltersPreferenceController extends BasePreferenceController {
    private static final String TAG = "SnoopLoggerFiltersPreferenceController";
    private final String[] mListEntries;
    private final String[] mListValues;

    public SnoopLoggerFiltersPreferenceController(Context context, String str) {
        super(context, str);
        this.mListValues =
                context.getResources().getStringArray(R.array.bt_hci_snoop_log_filters_values);
        this.mListEntries =
                context.getResources().getStringArray(R.array.bt_hci_snoop_log_filters_entries);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
        preferenceGroup.removeAll();
        Context context = preferenceGroup.getContext();
        int i = 0;
        while (true) {
            String[] strArr = this.mListValues;
            if (i >= strArr.length) {
                return;
            }
            preferenceGroup.addPreference(
                    new SnoopLoggerFiltersPreference(context, strArr[i], this.mListEntries[i]));
            i++;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return ((BluetoothProperties.snoop_log_mode_values)
                                BluetoothProperties.snoop_log_mode()
                                        .orElse(BluetoothProperties.snoop_log_mode_values.DISABLED))
                        == BluetoothProperties.snoop_log_mode_values.FILTERED
                ? 0
                : 5;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
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
