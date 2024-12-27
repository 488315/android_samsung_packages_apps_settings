package com.android.settingslib.inputmethod;

import android.content.ContentResolver;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.TwoStatePreference;

import java.text.Collator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InputMethodAndSubtypeEnablerManagerCompat
        implements Preference.OnPreferenceChangeListener {
    public final PreferenceFragmentCompat mFragment;
    public boolean mHaveHardKeyboard;
    public final InputMethodManager mImm;
    public final List mInputMethodInfoList;
    public final HashMap mInputMethodAndSubtypePrefsMap = new HashMap();
    public final HashMap mAutoSelectionPrefsMap = new HashMap();
    public final Collator mCollator = Collator.getInstance();

    public InputMethodAndSubtypeEnablerManagerCompat(
            PreferenceFragmentCompat preferenceFragmentCompat) {
        this.mFragment = preferenceFragmentCompat;
        InputMethodManager inputMethodManager =
                (InputMethodManager)
                        preferenceFragmentCompat
                                .getContext()
                                .getSystemService(InputMethodManager.class);
        this.mImm = inputMethodManager;
        this.mInputMethodInfoList = inputMethodManager.getInputMethodList();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(obj instanceof Boolean)) {
            return true;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        for (String str : this.mAutoSelectionPrefsMap.keySet()) {
            if (this.mAutoSelectionPrefsMap.get(str) == preference) {
                TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
                twoStatePreference.setChecked(booleanValue);
                setAutoSelectionSubtypesEnabled(str, twoStatePreference.isChecked());
                return false;
            }
        }
        if (!(preference instanceof InputMethodSubtypePreference)) {
            return true;
        }
        InputMethodSubtypePreference inputMethodSubtypePreference =
                (InputMethodSubtypePreference) preference;
        inputMethodSubtypePreference.setChecked(booleanValue);
        if (!inputMethodSubtypePreference.mChecked) {
            updateAutoSelectionPreferences();
        }
        return false;
    }

    public final void setAutoSelectionSubtypesEnabled(String str, boolean z) {
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) this.mAutoSelectionPrefsMap.get(str);
        if (twoStatePreference == null) {
            return;
        }
        twoStatePreference.setChecked(z);
        for (Preference preference : (List) this.mInputMethodAndSubtypePrefsMap.get(str)) {
            if (preference instanceof TwoStatePreference) {
                preference.setEnabled(!z);
                if (z) {
                    ((TwoStatePreference) preference).setChecked(false);
                }
            }
        }
        if (z) {
            PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
            ContentResolver contentResolver =
                    preferenceFragmentCompat.getContext().getContentResolver();
            List list = this.mInputMethodInfoList;
            boolean z2 = this.mHaveHardKeyboard;
            TextUtils.SimpleStringSplitter simpleStringSplitter =
                    InputMethodAndSubtypeUtilCompat.sStringInputMethodSplitter;
            InputMethodAndSubtypeUtilCompat.saveInputMethodSubtypeListForUserInternal(
                    preferenceFragmentCompat, contentResolver, list, z2, UserHandle.myUserId());
            updateImplicitlyEnabledSubtypes(str);
        }
    }

    public final void updateAutoSelectionPreferences() {
        boolean z;
        for (String str : this.mInputMethodAndSubtypePrefsMap.keySet()) {
            Iterator it = ((List) this.mInputMethodAndSubtypePrefsMap.get(str)).iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                Preference preference = (Preference) it.next();
                if ((preference instanceof TwoStatePreference)
                        && ((TwoStatePreference) preference).isChecked()) {
                    z = false;
                    break;
                }
            }
            setAutoSelectionSubtypesEnabled(str, z);
        }
        updateImplicitlyEnabledSubtypes(null);
    }

    public final void updateImplicitlyEnabledSubtypes(String str) {
        for (InputMethodInfo inputMethodInfo : this.mInputMethodInfoList) {
            String id = inputMethodInfo.getId();
            TwoStatePreference twoStatePreference =
                    (TwoStatePreference) this.mAutoSelectionPrefsMap.get(id);
            if (twoStatePreference != null
                    && twoStatePreference.isChecked()
                    && (id.equals(str) || str == null)) {
                String id2 = inputMethodInfo.getId();
                List<Preference> list = (List) this.mInputMethodAndSubtypePrefsMap.get(id2);
                List<InputMethodSubtype> enabledInputMethodSubtypeList =
                        this.mImm.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
                if (list != null && enabledInputMethodSubtypeList != null) {
                    for (Preference preference : list) {
                        if (preference instanceof TwoStatePreference) {
                            TwoStatePreference twoStatePreference2 =
                                    (TwoStatePreference) preference;
                            twoStatePreference2.setChecked(false);
                            Iterator<InputMethodSubtype> it =
                                    enabledInputMethodSubtypeList.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    InputMethodSubtype next = it.next();
                                    StringBuilder m =
                                            EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                    .m(id2);
                                    m.append(next.hashCode());
                                    if (twoStatePreference2.getKey().equals(m.toString())) {
                                        twoStatePreference2.setChecked(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
