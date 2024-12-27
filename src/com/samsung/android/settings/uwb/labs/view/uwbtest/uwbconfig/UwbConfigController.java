package com.samsung.android.settings.uwb.labs.view.uwbtest.uwbconfig;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.DropDownPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraTestFragmentController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbConfigController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private final String TAG;
    private Context mContext;
    private UwbConfigListener mController;
    private String mPreferenceKey;
    private Preference mThisPreference;

    public UwbConfigController(Context context, String str, UwbConfigListener uwbConfigListener) {
        super(context, str);
        this.TAG = "UwbConfigController";
        this.mContext = context;
        AbsAdapter$$ExternalSyntheticOutline0.m("Created key:", str, "UwbConfigController");
        this.mPreferenceKey = str;
        this.mController = uwbConfigListener;
    }

    private void saveValue(Object obj) {
        UwbConfigListener uwbConfigListener = this.mController;
        String str = this.mPreferenceKey;
        UwbFiraTestFragmentController uwbFiraTestFragmentController =
                (UwbFiraTestFragmentController) uwbConfigListener;
        uwbFiraTestFragmentController.getClass();
        Log.d("UwbFiraTestFragmentController", "onConfigChanged: " + str + " " + obj.toString());
        uwbFiraTestFragmentController.setValue(obj, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mThisPreference = findPreference;
        findPreference.seslSetSummaryColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.wifi_ap_primary_text_color, this.mContext.getTheme()));
        Preference preference = this.mThisPreference;
        if (preference instanceof DropDownPreference) {
            DropDownPreference dropDownPreference = (DropDownPreference) preference;
            int findIndexOfValue =
                    dropDownPreference.findIndexOfValue(
                            (String)
                                    ((UwbFiraTestFragmentController) this.mController)
                                            .getDefaultValue(this.mPreferenceKey));
            if (findIndexOfValue != -1) {
                dropDownPreference.setValueIndex(findIndexOfValue);
                dropDownPreference.setSummary(dropDownPreference.mEntries[findIndexOfValue]);
                return;
            } else {
                dropDownPreference.setValueIndex(0);
                dropDownPreference.setSummary(dropDownPreference.mEntries[0]);
                return;
            }
        }
        if (preference instanceof EditTextPreference) {
            ((EditTextPreference) preference)
                    .setSummary(
                            (String)
                                    ((UwbFiraTestFragmentController) this.mController)
                                            .getDefaultValue(this.mPreferenceKey));
        } else if (preference instanceof SwitchPreference) {
            SwitchPreference switchPreference = (SwitchPreference) preference;
            switchPreference.setChecked(
                    ((Boolean)
                                    ((UwbFiraTestFragmentController) this.mController)
                                            .getDefaultValue(this.mPreferenceKey))
                            .booleanValue());
            switchPreference.setSummary(
                    ((UwbFiraTestFragmentController) this.mController)
                            .getDefaultValue(this.mPreferenceKey)
                            .toString());
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
        Log.i("UwbConfigController", "onPreferenceChange New Value : " + obj.toString());
        Preference preference2 = this.mThisPreference;
        if (preference2 instanceof DropDownPreference) {
            DropDownPreference dropDownPreference = (DropDownPreference) preference2;
            dropDownPreference.setValueIndex(dropDownPreference.findIndexOfValue(obj.toString()));
            dropDownPreference.setSummary(
                    dropDownPreference
                            .mEntries[dropDownPreference.findIndexOfValue(obj.toString())]);
        } else if (preference2 instanceof EditTextPreference) {
            ((EditTextPreference) preference2).setSummary(obj.toString());
        } else if (preference2 instanceof SwitchPreference) {
            ((SwitchPreference) preference2).setSummary(obj.toString());
        }
        saveValue(obj);
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
