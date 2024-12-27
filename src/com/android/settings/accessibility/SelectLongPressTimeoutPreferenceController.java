package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SelectLongPressTimeoutPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private int mLongPressTimeoutDefault;
    private final Map<String, String> mLongPressTimeoutValueToTitleMap;

    public SelectLongPressTimeoutPreferenceController(Context context, String str) {
        super(context, str);
        this.mLongPressTimeoutValueToTitleMap = new HashMap();
        initLongPressTimeoutValueToTitleMap();
    }

    private String getLongPressTimeoutValue() {
        return String.valueOf(
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "long_press_timeout",
                        this.mLongPressTimeoutDefault));
    }

    private void initLongPressTimeoutValueToTitleMap() {
        if (this.mLongPressTimeoutValueToTitleMap.size() == 0) {
            String[] stringArray =
                    this.mContext
                            .getResources()
                            .getStringArray(R.array.long_press_timeout_selector_values);
            this.mLongPressTimeoutDefault = Integer.parseInt(stringArray[0]);
            String[] stringArray2 =
                    this.mContext
                            .getResources()
                            .getStringArray(R.array.long_press_timeout_selector_titles);
            int length = stringArray.length;
            for (int i = 0; i < length; i++) {
                this.mLongPressTimeoutValueToTitleMap.put(stringArray[i], stringArray2[i]);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof ListPreference)) {
            return false;
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "long_press_timeout",
                Integer.parseInt((String) obj));
        return true;
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
    public void updateState(Preference preference) {
        super.updateState(preference);
        ((ListPreference) preference).setValue(getLongPressTimeoutValue());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
