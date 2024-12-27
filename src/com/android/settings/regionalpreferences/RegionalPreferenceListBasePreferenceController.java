package com.android.settings.regionalpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.LocaleList;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.app.LocalePicker;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.TickButtonPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RegionalPreferenceListBasePreferenceController
        extends BasePreferenceController {
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private PreferenceCategory mPreferenceCategory;

    public RegionalPreferenceListBasePreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private void initPreferences() {
        if (this.mPreferenceCategory == null) {
            return;
        }
        for (final String str : getUnitValues()) {
            final TickButtonPreference tickButtonPreference =
                    new TickButtonPreference(this.mContext);
            this.mPreferenceCategory.addPreference(tickButtonPreference);
            Context context = this.mContext;
            String extensionTypes = getExtensionTypes();
            String string =
                    Settings.System.getString(context.getContentResolver(), "locale_preferences");
            final String unicodeLocaleType =
                    !TextUtils.isEmpty(string)
                            ? Locale.forLanguageTag(string).getUnicodeLocaleType(extensionTypes)
                            : ApnSettings.MVNO_NONE;
            if (TextUtils.isEmpty(unicodeLocaleType)) {
                unicodeLocaleType =
                        Locale.getDefault(Locale.Category.FORMAT)
                                .getUnicodeLocaleType(extensionTypes);
            }
            if (unicodeLocaleType == null) {
                unicodeLocaleType = "default";
            }
            tickButtonPreference.setTitle(getPreferenceTitle(str));
            tickButtonPreference.setKey(str);
            tickButtonPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            boolean lambda$initPreferences$0;
                            lambda$initPreferences$0 =
                                    RegionalPreferenceListBasePreferenceController.this
                                            .lambda$initPreferences$0(
                                                    tickButtonPreference,
                                                    str,
                                                    unicodeLocaleType,
                                                    preference);
                            return lambda$initPreferences$0;
                        }
                    });
            tickButtonPreference.setSelected(
                    !unicodeLocaleType.isEmpty() && str.equals(unicodeLocaleType));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$initPreferences$0(
            TickButtonPreference tickButtonPreference,
            String str,
            String str2,
            Preference preference) {
        setSelected(tickButtonPreference);
        Context context = this.mContext;
        String extensionTypes = getExtensionTypes();
        String str3 = str.equals("default") ? null : str;
        String string =
                Settings.System.getString(context.getContentResolver(), "locale_preferences");
        String str4 = ApnSettings.MVNO_NONE;
        if (string == null) {
            string = ApnSettings.MVNO_NONE;
        }
        Settings.System.putString(
                context.getContentResolver(),
                "locale_preferences",
                new Locale.Builder()
                        .setLocale(Locale.forLanguageTag(string))
                        .setUnicodeLocaleKeyword(extensionTypes, str3)
                        .build()
                        .toLanguageTag());
        LocaleList localeList = LocaleList.getDefault();
        Locale[] localeArr = new Locale[localeList.size()];
        for (int i = 0; i < localeList.size(); i++) {
            localeArr[i] =
                    new Locale.Builder()
                            .setLocale(localeList.get(i))
                            .setUnicodeLocaleKeyword(extensionTypes, str3)
                            .build();
        }
        LocalePicker.updateLocales(new LocaleList(localeArr));
        if (getMetricsActionKey() != 1827) {
            str4 = getPreferenceTitle(str2) + " > " + getPreferenceTitle(str);
        }
        this.mMetricsFeatureProvider.action(this.mContext, getMetricsActionKey(), str4);
        return true;
    }

    private void setSelected(TickButtonPreference tickButtonPreference) {
        for (int i = 0; i < this.mPreferenceCategory.getPreferenceCount(); i++) {
            TickButtonPreference tickButtonPreference2 =
                    (TickButtonPreference) this.mPreferenceCategory.getPreference(i);
            if (tickButtonPreference2.getKey().equals(tickButtonPreference.getKey())) {
                tickButtonPreference2.setSelected(true);
            } else {
                tickButtonPreference2.setSelected(false);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceCategoryKey());
        initPreferences();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public abstract String getExtensionTypes();

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public abstract int getMetricsActionKey();

    public abstract String getPreferenceCategoryKey();

    public abstract String getPreferenceTitle(String str);

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    public abstract String[] getUnitValues();

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

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

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
