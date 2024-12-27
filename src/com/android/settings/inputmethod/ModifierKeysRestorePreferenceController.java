package com.android.settings.inputmethod;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.Utils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModifierKeysRestorePreferenceController extends BasePreferenceController {
    private static final String KEY_TAG = "modifier_keys_restore_dialog_tag";
    private FragmentManager mFragmentManager;
    private Fragment mParent;
    private PreferenceScreen mScreen;

    public ModifierKeysRestorePreferenceController(Context context, String str) {
        super(context, str);
    }

    private int getColorOfMaterialColorPrimary() {
        return Utils.getColorAttrDefaultColor(this.mParent.getActivity(), R.^attr-private.materialColorPrimaryFixedDim);
    }

    private void setResetKeyColor() {
        Preference findPreference = this.mScreen.findPreference(getPreferenceKey());
        SpannableString spannableString = new SpannableString(this.mParent.getActivity().getString(com.android.settings.R.string.modifier_keys_reset_title));
        spannableString.setSpan(new ForegroundColorSpan(getColorOfMaterialColorPrimary()), 0, spannableString.length(), 0);
        findPreference.setTitle(spannableString);
    }

    private void showResetDialog() {
        this.mFragmentManager = this.mParent.getFragmentManager();
        ModifierKeysResetDialogFragment modifierKeysResetDialogFragment = new ModifierKeysResetDialogFragment();
        modifierKeysResetDialogFragment.setTargetFragment(this.mParent, 0);
        modifierKeysResetDialogFragment.show(this.mFragmentManager, KEY_TAG);
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mParent == null) {
            return;
        }
        this.mScreen = preferenceScreen;
        setResetKeyColor();
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

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(getPreferenceKey())) {
            return false;
        }
        showResetDialog();
        return true;
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragment(Fragment fragment) {
        this.mParent = fragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
    }
}
