package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.inputmethod.KeyboardSettingsFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ModifierKeysPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String KEY_PREFERENCE_ALT = "modifier_keys_alt";
    private static final String KEY_PREFERENCE_CAPS_LOCK = "modifier_keys_caps_lock";
    private static final String KEY_PREFERENCE_CTRL = "modifier_keys_ctrl";
    private static final String KEY_PREFERENCE_ESC = "modifier_keys_esc";
    private static final String KEY_PREFERENCE_META = "modifier_keys_meta";
    private SecDropDownPreference mAltDropDownPref;
    private SecDropDownPreference mCapsLockDropDownPref;
    private SecDropDownPreference mCtrlDropDownPref;
    private Drawable mDrawable;
    private SecDropDownPreference mEscDropDownPref;
    private final InputManager mIm;
    private final String[] mKeyNames;
    private SecDropDownPreference mMetaDropDownPref;
    private Fragment mParent;
    private final List<int[]> mRemappableKeyList;
    private final Map<String, int[]> mRemappableKeyMap;
    private final List<Integer> mRemappableKeys;
    private PreferenceScreen mScreen;
    CharSequence[] modifierKeys;
    List<String> modifierKeys1;
    CharSequence[] modifierKeysEsc;

    public ModifierKeysPreferenceController(Context context, String str) {
        super(context, str);
        this.mRemappableKeys =
                new ArrayList(Arrays.asList(113, 114, 117, 118, 57, 58, 115, 111, 4, 0));
        this.mKeyNames =
                new String[] {
                    this.mContext.getString(R.string.modifier_keys_ctrl),
                    this.mContext.getString(R.string.modifier_keys_ctrl),
                    this.mContext.getString(R.string.modifier_keys_meta),
                    this.mContext.getString(R.string.modifier_keys_meta),
                    this.mContext.getString(R.string.modifier_keys_alt),
                    this.mContext.getString(R.string.modifier_keys_alt),
                    this.mContext.getString(R.string.modifier_keys_caps_lock),
                    this.mContext.getString(R.string.modifier_keys_esc),
                    this.mContext.getString(R.string.modifier_keys_back),
                    this.mContext.getString(R.string.modifier_keys_none)
                };
        this.modifierKeys =
                new CharSequence[] {
                    this.mContext.getString(R.string.modifier_keys_caps_lock),
                    this.mContext.getString(R.string.modifier_keys_ctrl),
                    this.mContext.getString(R.string.modifier_keys_meta),
                    this.mContext.getString(R.string.modifier_keys_alt),
                    this.mContext.getString(R.string.modifier_keys_none)
                };
        this.modifierKeysEsc =
                new CharSequence[] {
                    this.mContext.getString(R.string.modifier_keys_esc),
                    this.mContext.getString(R.string.modifier_keys_back),
                    this.mContext.getString(R.string.modifier_keys_none)
                };
        this.modifierKeys1 =
                new ArrayList(
                        Arrays.asList(
                                this.mContext.getString(R.string.modifier_keys_caps_lock),
                                this.mContext.getString(R.string.modifier_keys_ctrl),
                                this.mContext.getString(R.string.modifier_keys_meta),
                                this.mContext.getString(R.string.modifier_keys_alt),
                                this.mContext.getString(R.string.modifier_keys_esc),
                                this.mContext.getString(R.string.modifier_keys_back),
                                this.mContext.getString(R.string.modifier_keys_none)));
        this.mRemappableKeyList =
                new ArrayList(
                        Arrays.asList(
                                new int[] {115},
                                new int[] {113, 114},
                                new int[] {117, 118},
                                new int[] {57, 58},
                                new int[] {111},
                                new int[] {4},
                                new int[] {0}));
        this.mRemappableKeyMap = new HashMap();
        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
        this.mIm = inputManager;
        Objects.requireNonNull(inputManager, "InputManager service cannot be null");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((KeyboardSettingsFeatureProviderImpl)
                        featureFactoryImpl.keyboardSettingsFeatureProvider$delegate.getValue())
                .getClass();
        this.mDrawable = null;
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

    private static boolean isEsc(int i) {
        return i == 111;
    }

    private static boolean isKeyCapsLock(Context context, String str) {
        return str.equals(context.getString(R.string.modifier_keys_caps_lock));
    }

    private static boolean isKeyEscape(Context context, String str) {
        return str.equals(context.getString(R.string.modifier_keys_esc));
    }

    private boolean isKeyNone(Context context, String str) {
        return str.equals(context.getString(R.string.modifier_keys_none));
    }

    private static boolean isMeta(int i) {
        return i == 117 || i == 118;
    }

    private void updateVal(Preference preference, String str) {
        for (int i = 0; i < this.modifierKeys1.size(); i++) {
            this.mRemappableKeyMap.put(this.modifierKeys1.get(i), this.mRemappableKeyList.get(i));
        }
        CharSequence title = preference.getTitle();
        Objects.requireNonNull(title);
        String charSequence = title.toString();
        if (str.equals(charSequence)) {
            for (int i2 : this.mRemappableKeyMap.get(charSequence)) {
                this.mIm.remapModifierKey(i2, i2);
            }
        } else {
            int[] iArr = this.mRemappableKeyMap.get(charSequence);
            int[] iArr2 = this.mRemappableKeyMap.get(str);
            if (isKeyCapsLock(this.mContext, charSequence)
                    || isKeyEscape(this.mContext, charSequence)) {
                this.mIm.remapModifierKey(iArr[0], iArr2[0]);
            } else if (!isKeyCapsLock(this.mContext, charSequence)
                    && (isKeyCapsLock(this.mContext, str) || isKeyNone(this.mContext, str))) {
                this.mIm.remapModifierKey(iArr[0], iArr2[0]);
                this.mIm.remapModifierKey(iArr[1], iArr2[0]);
            } else if (!isKeyCapsLock(this.mContext, charSequence)
                    && !isKeyCapsLock(this.mContext, str)) {
                this.mIm.remapModifierKey(iArr[0], iArr2[0]);
                this.mIm.remapModifierKey(iArr[1], iArr2[1]);
            }
        }
        preference.setSummary(str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
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
        if (!(preference instanceof SecDropDownPreference)) {
            return true;
        }
        updateVal(preference, obj.toString());
        return true;
    }

    public void refreshUi() {
        this.mCapsLockDropDownPref =
                (SecDropDownPreference) this.mScreen.findPreference(KEY_PREFERENCE_CAPS_LOCK);
        this.mCtrlDropDownPref =
                (SecDropDownPreference) this.mScreen.findPreference(KEY_PREFERENCE_CTRL);
        this.mMetaDropDownPref =
                (SecDropDownPreference) this.mScreen.findPreference(KEY_PREFERENCE_META);
        this.mAltDropDownPref =
                (SecDropDownPreference) this.mScreen.findPreference(KEY_PREFERENCE_ALT);
        this.mEscDropDownPref =
                (SecDropDownPreference) this.mScreen.findPreference(KEY_PREFERENCE_ESC);
        this.mCapsLockDropDownPref.setOnPreferenceChangeListener(this);
        this.mCtrlDropDownPref.setOnPreferenceChangeListener(this);
        this.mMetaDropDownPref.setOnPreferenceChangeListener(this);
        this.mAltDropDownPref.setOnPreferenceChangeListener(this);
        this.mEscDropDownPref.setOnPreferenceChangeListener(this);
        this.mCapsLockDropDownPref.setEntries(this.modifierKeys);
        SecDropDownPreference secDropDownPreference = this.mCapsLockDropDownPref;
        secDropDownPreference.mEntryValues = this.modifierKeys;
        SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
        this.mCtrlDropDownPref.setEntries(this.modifierKeys);
        SecDropDownPreference secDropDownPreference2 = this.mCtrlDropDownPref;
        secDropDownPreference2.mEntryValues = this.modifierKeys;
        SecPreferenceUtils.applySummaryColor(secDropDownPreference2, true);
        this.mMetaDropDownPref.setEntries(this.modifierKeys);
        SecDropDownPreference secDropDownPreference3 = this.mMetaDropDownPref;
        secDropDownPreference3.mEntryValues = this.modifierKeys;
        SecPreferenceUtils.applySummaryColor(secDropDownPreference3, true);
        this.mAltDropDownPref.setEntries(this.modifierKeys);
        SecDropDownPreference secDropDownPreference4 = this.mAltDropDownPref;
        secDropDownPreference4.mEntryValues = this.modifierKeys;
        SecPreferenceUtils.applySummaryColor(secDropDownPreference4, true);
        this.mEscDropDownPref.setEntries(this.modifierKeysEsc);
        SecDropDownPreference secDropDownPreference5 = this.mEscDropDownPref;
        secDropDownPreference5.mEntryValues = this.modifierKeysEsc;
        SecPreferenceUtils.applySummaryColor(secDropDownPreference5, true);
        HashMap hashMap = new HashMap();
        for (int[] iArr : this.mRemappableKeyList) {
            for (int i : iArr) {
                hashMap.put(Integer.valueOf(i), Boolean.FALSE);
            }
        }
        for (Map.Entry entry : this.mIm.getModifierKeyRemapping().entrySet()) {
            Integer num = (Integer) entry.getKey();
            int intValue = num.intValue();
            Integer num2 = (Integer) entry.getValue();
            num2.getClass();
            int indexOf = this.mRemappableKeys.indexOf(num2);
            hashMap.put(num, Boolean.TRUE);
            if (isCtrl(intValue) && this.mRemappableKeys.contains(num2)) {
                this.mCtrlDropDownPref.setValue(this.mKeyNames[indexOf]);
                this.mCtrlDropDownPref.setSummary(this.mKeyNames[indexOf]);
            } else if (isMeta(intValue) && this.mRemappableKeys.contains(num2)) {
                this.mMetaDropDownPref.setValue(this.mKeyNames[indexOf]);
                this.mMetaDropDownPref.setSummary(this.mKeyNames[indexOf]);
            } else if (isAlt(intValue) && this.mRemappableKeys.contains(num2)) {
                this.mAltDropDownPref.setValue(this.mKeyNames[indexOf]);
                this.mAltDropDownPref.setSummary(this.mKeyNames[indexOf]);
            } else if (isCapLock(intValue) && this.mRemappableKeys.contains(num2)) {
                this.mCapsLockDropDownPref.setValue(this.mKeyNames[indexOf]);
                this.mCapsLockDropDownPref.setSummary(this.mKeyNames[indexOf]);
            } else if (isEsc(intValue) && this.mRemappableKeys.contains(num2)) {
                this.mEscDropDownPref.setValue(this.mKeyNames[indexOf]);
                this.mEscDropDownPref.setSummary(this.mKeyNames[indexOf]);
            }
        }
        for (Integer num3 : hashMap.keySet()) {
            if (!Boolean.TRUE.equals(hashMap.get(num3))) {
                int indexOf2 = this.mRemappableKeys.indexOf(num3);
                if (isCtrl(num3.intValue())) {
                    this.mCtrlDropDownPref.setValue(this.mKeyNames[indexOf2]);
                    this.mCtrlDropDownPref.setSummary(this.mKeyNames[indexOf2]);
                } else if (isMeta(num3.intValue())) {
                    this.mMetaDropDownPref.setValue(this.mKeyNames[indexOf2]);
                    this.mMetaDropDownPref.setSummary(this.mKeyNames[indexOf2]);
                } else if (isAlt(num3.intValue())) {
                    this.mAltDropDownPref.setValue(this.mKeyNames[indexOf2]);
                    this.mAltDropDownPref.setSummary(this.mKeyNames[indexOf2]);
                } else if (isCapLock(num3.intValue())) {
                    this.mCapsLockDropDownPref.setValue(this.mKeyNames[indexOf2]);
                    this.mCapsLockDropDownPref.setSummary(this.mKeyNames[indexOf2]);
                } else if (isEsc(num3.intValue())) {
                    this.mEscDropDownPref.setValue(this.mKeyNames[indexOf2]);
                    this.mEscDropDownPref.setSummary(this.mKeyNames[indexOf2]);
                }
            }
        }
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
