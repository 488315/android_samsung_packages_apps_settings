package com.android.settings.inputmethod;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.ListFormatter;
import android.text.BidiFormatter;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VirtualKeyboardPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final DevicePolicyManager mDpm;
    public final InputMethodManager mImm;
    public final PackageManager mPm;

    public VirtualKeyboardPreferenceController(Context context) {
        super(context);
        this.mPm = this.mContext.getPackageManager();
        this.mDpm = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mImm = (InputMethodManager) this.mContext.getSystemService("input_method");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "virtual_keyboard_pref";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_virtual_keyboard_pref);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        List<InputMethodInfo> enabledInputMethodList = this.mImm.getEnabledInputMethodList();
        if (enabledInputMethodList == null) {
            preference.setSummary(R.string.summary_empty);
            return;
        }
        List permittedInputMethodsForCurrentUser =
                this.mDpm.getPermittedInputMethodsForCurrentUser();
        ArrayList arrayList = new ArrayList();
        for (InputMethodInfo inputMethodInfo : enabledInputMethodList) {
            if (permittedInputMethodsForCurrentUser == null
                    || permittedInputMethodsForCurrentUser.contains(
                            inputMethodInfo.getPackageName())) {
                arrayList.add(inputMethodInfo.loadLabel(this.mPm).toString());
            }
        }
        if (arrayList.isEmpty()) {
            preference.setSummary(R.string.summary_empty);
            return;
        }
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(bidiFormatter.unicodeWrap((String) it.next()));
        }
        preference.setSummary(ListFormatter.getInstance().format(arrayList2));
    }
}
