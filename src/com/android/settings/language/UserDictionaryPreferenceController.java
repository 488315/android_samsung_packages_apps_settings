package com.android.settings.language;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.inputmethod.UserDictionaryList;
import com.android.settings.inputmethod.UserDictionaryListPreferenceController;
import com.android.settings.inputmethod.UserDictionarySettings;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.general.GeneralUtils;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserDictionaryPreferenceController extends BasePreferenceController {
    private static final String TAG = "UserDictionaryPreferenceController";
    private final TextServicesManager mTextServicesManager;

    public UserDictionaryPreferenceController(Context context, String str) {
        super(context, str);
        this.mTextServicesManager = (TextServicesManager) context.getSystemService("textservices");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean isInstalledAnySpellCheckerPackage =
                GeneralUtils.isInstalledAnySpellCheckerPackage(this.mContext);
        SemLog.i(TAG, "isInstalledAnySpellCheckerPackage = " + isInstalledAnySpellCheckerPackage);
        if (!isInstalledAnySpellCheckerPackage || this.mTextServicesManager == null) {
            SemLog.i(TAG, "cannot find spellcheckerinfo, tsm=" + this.mTextServicesManager);
            return 2;
        }
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.mContext.getSystemService("input_method");
        boolean isCurrentInputMethodAsSamsungKeyboard =
                inputMethodManager != null
                        ? inputMethodManager.isCurrentInputMethodAsSamsungKeyboard()
                        : false;
        SemLog.d(TAG, "isSamsungKeyboard = " + isCurrentInputMethodAsSamsungKeyboard);
        return isCurrentInputMethodAsSamsungKeyboard ? 2 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public TreeSet<String> getDictionaryLocales() {
        return UserDictionaryListPreferenceController.getUserDictionaryLocalesSet(this.mContext);
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        Class cls;
        if (!isAvailable() || preference == null) {
            return;
        }
        TreeSet<String> dictionaryLocales = getDictionaryLocales();
        Bundle extras = preference.getExtras();
        if (dictionaryLocales.size() <= 1) {
            if (!dictionaryLocales.isEmpty()) {
                extras.putString(SpeechRecognitionConst.Key.LOCALE, dictionaryLocales.first());
            }
            cls = UserDictionarySettings.class;
        } else {
            cls = UserDictionaryList.class;
        }
        preference.setFragment(cls.getCanonicalName());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
