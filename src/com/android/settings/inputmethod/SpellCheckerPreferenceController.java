package com.android.settings.inputmethod;

import android.content.Context;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.TextServicesManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtilCompat;

import com.samsung.android.settings.general.GeneralUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SpellCheckerPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final TextServicesManager mTextServicesManager;

    public SpellCheckerPreferenceController(Context context) {
        super(context);
        this.mTextServicesManager = (TextServicesManager) context.getSystemService("textservices");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference("spellcheckers_settings");
        if (findPreference != null) {
            InputMethodAndSubtypeUtilCompat.removeUnnecessaryNonPersistentPreference(
                    findPreference);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "spellcheckers_settings";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return GeneralUtils.isSupportSpellCheckerService(this.mContext);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (preference == null) {
            return;
        }
        if (!this.mTextServicesManager.isSpellCheckerEnabled()) {
            preference.setSummary(R.string.switch_off_text);
            return;
        }
        SpellCheckerInfo currentSpellChecker = this.mTextServicesManager.getCurrentSpellChecker();
        if (currentSpellChecker != null) {
            preference.setSummary(currentSpellChecker.loadLabel(this.mContext.getPackageManager()));
        } else {
            preference.setSummary(R.string.spell_checker_not_selected);
        }
    }
}
