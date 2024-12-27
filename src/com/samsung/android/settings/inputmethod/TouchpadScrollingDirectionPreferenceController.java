package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchpadScrollingDirectionPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String KEY_TOUCHPAD_SCROLLING_DIRECTION =
            "key_touchpad_scrolling_direction";
    private static final String TAG = "TouchpadScrollingDirectionPreferenceController";
    private SecDropDownPreference mDropDownPref;

    public TouchpadScrollingDirectionPreferenceController(Context context, String str) {
        super(context, KEY_TOUCHPAD_SCROLLING_DIRECTION);
    }

    private String setSummary(int i) {
        return i == 0
                ? this.mContext.getString(R.string.down_gesture_scrolls_up)
                : this.mContext.getString(R.string.down_gesture_scrolls_down);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mDropDownPref = secDropDownPreference;
        if (secDropDownPreference != null) {
            int i =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(), "touchpad_scrolling_direction", 0);
            this.mDropDownPref.setEntries(
                    new CharSequence[] {
                        this.mContext.getString(R.string.down_gesture_scrolls_up),
                        this.mContext.getString(R.string.down_gesture_scrolls_down)
                    });
            SecDropDownPreference secDropDownPreference2 = this.mDropDownPref;
            secDropDownPreference2.mEntryValues =
                    new CharSequence[] {DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "1"};
            secDropDownPreference2.setValueIndex(i);
            this.mDropDownPref.setSummary(setSummary(i));
            this.mDropDownPref.setOnPreferenceChangeListener(this);
            SecDropDownPreference secDropDownPreference3 = this.mDropDownPref;
            secDropDownPreference3.getClass();
            SecPreferenceUtils.applySummaryColor(secDropDownPreference3, true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return TouchPadGestureSettingsController.isPogoKeyboardConnected(this.mContext) ? 0 : 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_TOUCHPAD_SCROLLING_DIRECTION;
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
        int parseInt = Integer.parseInt((String) obj);
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        Settings.System.putInt(
                this.mContext.getContentResolver(), "touchpad_scrolling_direction", parseInt);
        SecDropDownPreference secDropDownPreference = this.mDropDownPref;
        if (secDropDownPreference == null) {
            return true;
        }
        secDropDownPreference.setSummary(setSummary(parseInt));
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
