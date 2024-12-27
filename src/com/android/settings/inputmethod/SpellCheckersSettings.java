package com.android.settings.inputmethod;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.SpellCheckerSubtype;
import android.view.textservice.TextServicesManager;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SpellCheckersSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SpellCheckerInfo mCurrentSci;
    public AlertDialog mDialog = null;
    public SpellCheckerInfo[] mEnabledScis;
    public Preference mSpellCheckerLanaguagePref;
    public SettingsMainSwitchBar mSwitchBar;
    public TextServicesManager mTsm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.inputmethod.SpellCheckersSettings$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SpellCheckersSettings this$0;
        public final /* synthetic */ SpellCheckerInfo val$currentSci;

        public /* synthetic */ AnonymousClass1(
                SpellCheckersSettings spellCheckersSettings,
                SpellCheckerInfo spellCheckerInfo,
                int i) {
            this.$r8$classId = i;
            this.this$0 = spellCheckersSettings;
            this.val$currentSci = spellCheckerInfo;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            int i2 = 0;
            switch (this.$r8$classId) {
                case 0:
                    if (i != 0) {
                        int i3 = SpellCheckersSettings.$r8$clinit;
                        i2 = this.val$currentSci.getSubtypeAt(i - 1).hashCode();
                    }
                    SpellCheckersSettings spellCheckersSettings = this.this$0;
                    int i4 = SpellCheckersSettings.$r8$clinit;
                    Settings.Secure.putInt(
                            spellCheckersSettings.getContentResolver(),
                            "selected_spell_checker_subtype",
                            i2);
                    dialogInterface.dismiss();
                    this.this$0.updatePreferenceScreen();
                    break;
                default:
                    SpellCheckersSettings spellCheckersSettings2 = this.this$0;
                    SpellCheckerInfo spellCheckerInfo = this.val$currentSci;
                    int i5 = SpellCheckersSettings.$r8$clinit;
                    Settings.Secure.putString(
                            spellCheckersSettings2.getContentResolver(),
                            "selected_spell_checker",
                            spellCheckerInfo.getId());
                    Settings.Secure.putInt(
                            spellCheckersSettings2.getContentResolver(),
                            "selected_spell_checker_subtype",
                            0);
                    spellCheckersSettings2.updatePreferenceScreen();
                    break;
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 59;
    }

    public final CharSequence getSpellCheckerSubtypeLabel(
            SpellCheckerInfo spellCheckerInfo, SpellCheckerSubtype spellCheckerSubtype) {
        return spellCheckerInfo == null
                ? getString(R.string.spell_checker_not_selected)
                : spellCheckerSubtype == null
                        ? getString(R.string.use_system_language_to_select_input_method_subtypes)
                        : spellCheckerSubtype.getDisplayName(
                                getActivity(),
                                spellCheckerInfo.getPackageName(),
                                spellCheckerInfo.getServiceInfo().applicationInfo);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Secure.putInt(getContentResolver(), "spell_checker_enabled", z ? 1 : 0);
        updatePreferenceScreen();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.spellchecker_prefs);
        this.mSpellCheckerLanaguagePref = findPreference("spellchecker_language");
        TextServicesManager textServicesManager =
                (TextServicesManager) getSystemService("textservices");
        this.mTsm = textServicesManager;
        this.mCurrentSci = textServicesManager.getCurrentSpellChecker();
        this.mEnabledScis = this.mTsm.getEnabledSpellCheckers();
        SpellCheckerPreference spellCheckerPreference =
                new SpellCheckerPreference(getPrefContext(), this.mEnabledScis);
        spellCheckerPreference.setTitle(R.string.default_spell_checker);
        SpellCheckerInfo[] spellCheckerInfoArr = this.mEnabledScis;
        if ((spellCheckerInfoArr == null ? 0 : spellCheckerInfoArr.length) > 0) {
            spellCheckerPreference.setSummary("%s");
        } else {
            spellCheckerPreference.setSummary(R.string.spell_checker_not_selected);
        }
        spellCheckerPreference.setKey("default_spellchecker");
        spellCheckerPreference.setOnPreferenceChangeListener(this);
        getPreferenceScreen().addPreference(spellCheckerPreference);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SpellCheckerInfo spellCheckerInfo = (SpellCheckerInfo) obj;
        if ((spellCheckerInfo.getServiceInfo().applicationInfo.flags & 1) != 0) {
            Settings.Secure.putString(
                    getContentResolver(), "selected_spell_checker", spellCheckerInfo.getId());
            Settings.Secure.putInt(getContentResolver(), "selected_spell_checker_subtype", 0);
            updatePreferenceScreen();
            return true;
        }
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(android.R.string.dialog_alert_title);
        String string =
                getString(
                        R.string.spellchecker_security_warning,
                        spellCheckerInfo.loadLabel(getPackageManager()));
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string;
        alertParams.mCancelable = true;
        builder.setPositiveButton(
                android.R.string.ok, new AnonymousClass1(this, spellCheckerInfo, 1));
        builder.setNegativeButton(android.R.string.cancel, new AnonymousClass3());
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.show();
        return false;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!"spellchecker_language".equals(preference.getKey())) {
            return super.onPreferenceTreeClick(preference);
        }
        writePreferenceClickMetric(preference);
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        SpellCheckerInfo currentSpellChecker = this.mTsm.getCurrentSpellChecker();
        if (currentSpellChecker == null) {
            return true;
        }
        int i = 0;
        SpellCheckerSubtype currentSpellCheckerSubtype =
                this.mTsm.getCurrentSpellCheckerSubtype(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.phone_language);
        int subtypeCount = currentSpellChecker.getSubtypeCount();
        CharSequence[] charSequenceArr = new CharSequence[subtypeCount + 1];
        charSequenceArr[0] =
                getString(R.string.use_system_language_to_select_input_method_subtypes);
        while (true) {
            int i2 = i;
            while (i < subtypeCount) {
                SpellCheckerSubtype subtypeAt = currentSpellChecker.getSubtypeAt(i);
                i++;
                charSequenceArr[i] = getSpellCheckerSubtypeLabel(currentSpellChecker, subtypeAt);
                if (subtypeAt.equals(currentSpellCheckerSubtype)) {
                    break;
                }
            }
            builder.setSingleChoiceItems(
                    charSequenceArr, i2, new AnonymousClass1(this, currentSpellChecker, 0));
            AlertDialog create = builder.create();
            this.mDialog = create;
            create.show();
            return true;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.addOnSwitchChangeListener(this);
        updatePreferenceScreen();
    }

    public final void updatePreferenceScreen() {
        this.mCurrentSci = this.mTsm.getCurrentSpellChecker();
        boolean isSpellCheckerEnabled = this.mTsm.isSpellCheckerEnabled();
        this.mSwitchBar.setChecked(isSpellCheckerEnabled);
        boolean z = false;
        this.mSpellCheckerLanaguagePref.setSummary(
                getSpellCheckerSubtypeLabel(
                        this.mCurrentSci,
                        this.mCurrentSci != null
                                ? this.mTsm.getCurrentSpellCheckerSubtype(false)
                                : null));
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int preferenceCount = preferenceScreen.getPreferenceCount();
        int i = 0;
        while (true) {
            if (i >= preferenceCount) {
                break;
            }
            Preference preference = preferenceScreen.getPreference(i);
            preference.setEnabled(isSpellCheckerEnabled);
            if (preference instanceof SpellCheckerPreference) {
                SpellCheckerPreference spellCheckerPreference = (SpellCheckerPreference) preference;
                SpellCheckerInfo spellCheckerInfo = this.mCurrentSci;
                if (spellCheckerInfo == null) {
                    spellCheckerPreference.setValue(null);
                } else {
                    int i2 = 0;
                    while (true) {
                        SpellCheckerInfo[] spellCheckerInfoArr = spellCheckerPreference.mScis;
                        if (i2 >= spellCheckerInfoArr.length) {
                            break;
                        }
                        if (spellCheckerInfoArr[i2].getId().equals(spellCheckerInfo.getId())) {
                            spellCheckerPreference.setValueIndex(i2);
                            break;
                        }
                        i2++;
                    }
                }
                spellCheckerPreference.setEnabled(this.mEnabledScis != null);
            }
            i++;
        }
        Preference preference2 = this.mSpellCheckerLanaguagePref;
        if (isSpellCheckerEnabled && this.mCurrentSci != null) {
            z = true;
        }
        preference2.setEnabled(z);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.inputmethod.SpellCheckersSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {}
    }
}
