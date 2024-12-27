package com.android.settings.regionalpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.NumberingSystem;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
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
public class NumberingSystemItemController extends BasePreferenceController {
    static final String ARG_VALUE_LANGUAGE_SELECT = "arg_value_language_select";
    static final String ARG_VALUE_NUMBERING_SYSTEM_SELECT = "arg_value_numbering_system_select";
    private static final String DISPLAY_KEYWORD_NUMBERING_SYSTEM = "numbers";
    static final String KEY_SELECTED_LANGUAGE = "key_selected_language";
    private static final String TAG = "NumberingSystemItemController";
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private String mOption;
    private DashboardFragment mParentFragment;
    private PreferenceScreen mPreferenceScreen;
    private String mSelectedLanguage;

    public NumberingSystemItemController(Context context, Bundle bundle) {
        super(context, "no_key");
        this.mOption = ApnSettings.MVNO_NONE;
        this.mSelectedLanguage = ApnSettings.MVNO_NONE;
        LocaleStore.fillCache(context);
        this.mOption = bundle.getString("arg_key_regional_preference", ApnSettings.MVNO_NONE);
        this.mSelectedLanguage = bundle.getString(KEY_SELECTED_LANGUAGE, ApnSettings.MVNO_NONE);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private static String getNumberingSystem(Locale locale) {
        return new ULocale.Builder()
                .setUnicodeLocaleKeyword("nu", NumberingSystem.getInstance(locale).getName())
                .build()
                .getDisplayKeywordValue(
                        DISPLAY_KEYWORD_NUMBERING_SYSTEM, ULocale.forLocale(locale));
    }

    private void handleLanguageSelect(Preference preference) {
        String key = preference.getKey();
        this.mMetricsFeatureProvider.action(this.mContext, 1828, new Pair[0]);
        Bundle bundle = new Bundle();
        bundle.putString("arg_key_regional_preference", ARG_VALUE_NUMBERING_SYSTEM_SELECT);
        bundle.putString(KEY_SELECTED_LANGUAGE, key);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(preference.getContext());
        String name = NumberingPreferencesFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 2012;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    private void handleNumberSystemSelect(Preference preference) {
        for (int i = 0; i < this.mPreferenceScreen.getPreferenceCount(); i++) {
            TickButtonPreference tickButtonPreference =
                    (TickButtonPreference) this.mPreferenceScreen.getPreference(i);
            Log.i(TAG, "[onPreferenceClick] key is " + tickButtonPreference.getKey());
            if (tickButtonPreference.getKey().equals(preference.getKey())) {
                String key = tickButtonPreference.getKey();
                tickButtonPreference.setSelected(true);
                Locale saveNumberingSystemToLocale =
                        saveNumberingSystemToLocale(
                                Locale.forLanguageTag(this.mSelectedLanguage), key);
                this.mMetricsFeatureProvider.action(this.mContext, 1829, new Pair[0]);
                Bundle bundle = new Bundle();
                bundle.putString("arg_key_regional_preference", ARG_VALUE_NUMBERING_SYSTEM_SELECT);
                bundle.putString(
                        KEY_SELECTED_LANGUAGE,
                        saveNumberingSystemToLocale != null
                                ? saveNumberingSystemToLocale.toLanguageTag()
                                : ApnSettings.MVNO_NONE);
                this.mParentFragment.setArguments(bundle);
            } else {
                tickButtonPreference.setSelected(false);
            }
        }
    }

    private void initLanguageOptionsUi(PreferenceScreen preferenceScreen) {
        LocaleList localeList = LocaleList.getDefault();
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            if (LocaleStore.getLocaleInfo(locale).hasNumberingSystems()) {
                Preference preference = new Preference(this.mContext);
                preference.setTitle(
                        LocaleHelper.getDisplayName(locale.stripExtensions(), locale, true));
                preference.setKey(locale.toLanguageTag());
                preference.setSummary(getNumberingSystem(locale));
                preferenceScreen.addPreference(preference);
            }
        }
    }

    private void initNumberingSystemOptionsUi(PreferenceScreen preferenceScreen, Locale locale) {
        for (String str : LocalePicker.getSupportedLocales(this.mContext)) {
            Locale forLanguageTag = Locale.forLanguageTag(str);
            if (isSameBaseLocale(locale, forLanguageTag)) {
                TickButtonPreference tickButtonPreference = new TickButtonPreference(this.mContext);
                tickButtonPreference.setTitle(getNumberingSystem(forLanguageTag));
                String unicodeLocaleType = forLanguageTag.getUnicodeLocaleType("nu");
                if (unicodeLocaleType == null) {
                    unicodeLocaleType = "default";
                }
                tickButtonPreference.setKey(unicodeLocaleType);
                tickButtonPreference.setSelected(isSameNumberingSystem(locale, forLanguageTag));
                preferenceScreen.addPreference(tickButtonPreference);
            }
        }
    }

    private static boolean isSameBaseLocale(Locale locale, Locale locale2) {
        return locale.stripExtensions().equals(locale2.stripExtensions());
    }

    private static boolean isSameNumberingSystem(Locale locale, Locale locale2) {
        return TextUtils.equals(
                NumberingSystem.getInstance(locale).getName(),
                NumberingSystem.getInstance(locale2).getName());
    }

    private Locale saveNumberingSystemToLocale(Locale locale, String str) {
        LocaleList locales = LocalePicker.getLocales();
        Locale[] localeArr = new Locale[locales.size()];
        Locale locale2 = null;
        for (int i = 0; i < locales.size(); i++) {
            Locale locale3 = locales.get(i);
            if (locale.equals(locale3)) {
                if ("default".equals(str)) {
                    str = null;
                }
                locale2 =
                        new Locale.Builder()
                                .setLocale(locale3)
                                .setUnicodeLocaleKeyword("nu", str)
                                .build();
                localeArr[i] = locale2;
            } else {
                localeArr[i] = locales.get(i);
            }
        }
        LocalePicker.updateLocales(new LocaleList(localeArr));
        return locale2;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        if (this.mOption.equals(ARG_VALUE_LANGUAGE_SELECT)) {
            initLanguageOptionsUi(preferenceScreen);
        } else if (this.mOption.equals(ARG_VALUE_NUMBERING_SYSTEM_SELECT)) {
            initNumberingSystemOptionsUi(
                    preferenceScreen, Locale.forLanguageTag(this.mSelectedLanguage));
        }
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (this.mOption.equals(ARG_VALUE_LANGUAGE_SELECT)) {
            handleLanguageSelect(preference);
            return true;
        }
        if (!this.mOption.equals(ARG_VALUE_NUMBERING_SYSTEM_SELECT)) {
            return true;
        }
        handleNumberSystemSelect(preference);
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setParentFragment(DashboardFragment dashboardFragment) {
        this.mParentFragment = dashboardFragment;
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
