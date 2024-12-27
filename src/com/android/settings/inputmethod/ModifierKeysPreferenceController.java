package com.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.LayoutPreference;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModifierKeysPreferenceController extends BasePreferenceController {
    private static final String KEY_PREFERENCE_ALT = "modifier_keys_alt";
    private static final String KEY_PREFERENCE_CAPS_LOCK = "modifier_keys_caps_lock";
    private static final String KEY_PREFERENCE_CTRL = "modifier_keys_ctrl";
    private static final String KEY_PREFERENCE_META = "modifier_keys_meta";
    private static final String KEY_RESTORE_PREFERENCE = "modifier_keys_restore";
    private static final String KEY_TAG = "modifier_keys_dialog_tag";
    private Drawable mDrawable;
    private FragmentManager mFragmentManager;
    private final InputManager mIm;
    private String[] mKeyNames;
    private final List<Pair<String, Integer>> mKeys;
    private Fragment mParent;
    private final List<Integer> mRemappableKeys;
    private PreferenceScreen mScreen;

    public ModifierKeysPreferenceController(Context context, String str) {
        super(context, str);
        this.mRemappableKeys = new ArrayList(Arrays.asList(113, 114, 117, 118, 57, 58, 115));
        this.mKeys = new ArrayList(Arrays.asList(Pair.create(KEY_PREFERENCE_CTRL, Integer.valueOf(R.string.modifier_keys_ctrl)), Pair.create(KEY_PREFERENCE_META, Integer.valueOf(R.string.modifier_keys_meta)), Pair.create(KEY_PREFERENCE_ALT, Integer.valueOf(R.string.modifier_keys_alt)), Pair.create(KEY_PREFERENCE_CAPS_LOCK, Integer.valueOf(R.string.modifier_keys_caps_lock))));
        this.mKeyNames = new String[]{this.mContext.getString(R.string.modifier_keys_ctrl), this.mContext.getString(R.string.modifier_keys_ctrl), this.mContext.getString(R.string.modifier_keys_meta), this.mContext.getString(R.string.modifier_keys_meta), this.mContext.getString(R.string.modifier_keys_alt), this.mContext.getString(R.string.modifier_keys_alt), this.mContext.getString(R.string.modifier_keys_caps_lock)};
        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
        this.mIm = inputManager;
        Objects.requireNonNull(inputManager, "InputManager service cannot be null");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((KeyboardSettingsFeatureProviderImpl) featureFactoryImpl.keyboardSettingsFeatureProvider$delegate.getValue()).getClass();
        this.mDrawable = null;
    }

    private Spannable changeSummaryColor(String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(getColorOfMaterialColorPrimary()), 0, spannableString.length(), 0);
        return spannableString;
    }

    private int getColorOfMaterialColorPrimary() {
        return Utils.getColorAttrDefaultColor(this.mContext, android.R.^attr-private.materialColorPrimaryFixedDim);
    }

    private void initDefaultKeysName() {
        Drawable drawable;
        for (Pair<String, Integer> pair : this.mKeys) {
            LayoutPreference layoutPreference = (LayoutPreference) this.mScreen.findPreference((CharSequence) pair.first);
            TextView textView = (TextView) layoutPreference.mRootView.findViewById(R.id.title);
            TextView textView2 = (TextView) layoutPreference.mRootView.findViewById(R.id.summary);
            textView.setText(((Integer) pair.second).intValue());
            textView2.setText(R.string.modifier_keys_default_summary);
            if (((String) pair.first).equals(KEY_PREFERENCE_META) && (drawable = this.mDrawable) != null) {
                setActionKeyIcon(layoutPreference, drawable);
            }
        }
    }

    private static boolean isAlt(int i) {
        return i == 57 || i == 58;
    }

    private static boolean isCapLock(int i) {
        return i == 115;
    }

    private static boolean isCtrl(int i) {
        return i == 113 || i == 114;
    }

    private static boolean isMeta(int i) {
        return i == 117 || i == 118;
    }

    private void refreshUi() {
        initDefaultKeysName();
        for (Map.Entry entry : this.mIm.getModifierKeyRemapping().entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            Integer num = (Integer) entry.getValue();
            num.intValue();
            int indexOf = this.mRemappableKeys.indexOf(num);
            if (isCtrl(intValue) && this.mRemappableKeys.contains(num)) {
                setSummaryColor(KEY_PREFERENCE_CTRL, indexOf);
            }
            if (isMeta(intValue) && this.mRemappableKeys.contains(num)) {
                setSummaryColor(KEY_PREFERENCE_META, indexOf);
            }
            if (isAlt(intValue) && this.mRemappableKeys.contains(num)) {
                setSummaryColor(KEY_PREFERENCE_ALT, indexOf);
            }
            if (isCapLock(intValue) && this.mRemappableKeys.contains(num)) {
                setSummaryColor(KEY_PREFERENCE_CAPS_LOCK, indexOf);
            }
        }
    }

    private static void setActionKeyIcon(LayoutPreference layoutPreference, Drawable drawable) {
        TextView textView = (TextView) layoutPreference.mRootView.findViewById(R.id.modifier_key_left_bracket);
        TextView textView2 = (TextView) layoutPreference.mRootView.findViewById(R.id.modifier_key_right_bracket);
        ImageView imageView = (ImageView) layoutPreference.mRootView.findViewById(R.id.modifier_key_action_key_icon);
        textView.setText("(");
        textView2.setText(")");
        imageView.setImageDrawable(drawable);
    }

    private void setSummaryColor(String str, int i) {
        ((TextView) ((LayoutPreference) this.mScreen.findPreference(str)).mRootView.findViewById(R.id.summary)).setText(changeSummaryColor(this.mKeyNames[i]));
    }

    private void showModifierKeysDialog(Preference preference) {
        this.mFragmentManager = this.mParent.getFragmentManager();
        ModifierKeysPickerDialogFragment modifierKeysPickerDialogFragment = new ModifierKeysPickerDialogFragment();
        modifierKeysPickerDialogFragment.setTargetFragment(this.mParent, 0);
        Bundle bundle = new Bundle();
        LayoutPreference layoutPreference = (LayoutPreference) preference;
        TextView textView = (TextView) layoutPreference.mRootView.findViewById(R.id.title);
        TextView textView2 = (TextView) layoutPreference.mRootView.findViewById(R.id.summary);
        bundle.putString("default_key", textView.getText().toString());
        bundle.putString("delection_key", textView2.getText().toString());
        modifierKeysPickerDialogFragment.setArguments(bundle);
        modifierKeysPickerDialogFragment.show(this.mFragmentManager, KEY_TAG);
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mParent == null) {
            return;
        }
        this.mScreen = preferenceScreen;
        refreshUi();
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
        if (preference.getKey().equals(KEY_RESTORE_PREFERENCE)) {
            return false;
        }
        showModifierKeysDialog(preference);
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
