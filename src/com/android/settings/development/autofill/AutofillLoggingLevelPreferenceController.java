package com.android.settings.development.autofill;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;
import android.view.autofill.AutofillManager;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutofillLoggingLevelPreferenceController
        extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin,
                Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnDestroy {
    public final String[] mListSummaries;
    public final String[] mListValues;
    public final AutofillDeveloperSettingsObserver mObserver;

    public AutofillLoggingLevelPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        Resources resources = context.getResources();
        this.mListValues = resources.getStringArray(R.array.autofill_logging_level_values);
        this.mListSummaries = resources.getStringArray(R.array.autofill_logging_level_entries);
        AutofillDeveloperSettingsObserver autofillDeveloperSettingsObserver =
                new AutofillDeveloperSettingsObserver(
                        this.mContext,
                        new Runnable() { // from class:
                                         // com.android.settings.development.autofill.AutofillLoggingLevelPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AutofillLoggingLevelPreferenceController.this.updateOptions();
                            }
                        });
        this.mObserver = autofillDeveloperSettingsObserver;
        autofillDeveloperSettingsObserver.register();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "autofill_logging_level";
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        AutofillDeveloperSettingsObserver autofillDeveloperSettingsObserver = this.mObserver;
        autofillDeveloperSettingsObserver.mResolver.unregisterContentObserver(
                autofillDeveloperSettingsObserver);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putInt(this.mContext.getContentResolver(), "autofill_logging_level", 0);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "autofill_logging_level",
                obj instanceof String ? Integer.parseInt((String) obj) : 0);
        updateOptions();
        return true;
    }

    public final void updateOptions() {
        if (this.mPreference == null) {
            Log.v(
                    "AutofillLoggingLevelPreferenceController",
                    "ignoring Settings update because UI is gone");
            return;
        }
        int i =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "autofill_logging_level",
                        AutofillManager.DEFAULT_LOGGING_LEVEL);
        char c = 2;
        if (i == 2) {
            c = 1;
        } else if (i != 4) {
            c = 0;
        }
        ListPreference listPreference = (ListPreference) this.mPreference;
        listPreference.setValue(this.mListValues[c]);
        listPreference.setSummary(this.mListSummaries[c]);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateOptions();
    }
}
