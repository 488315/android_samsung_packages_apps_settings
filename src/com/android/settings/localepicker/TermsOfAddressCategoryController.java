package com.android.settings.localepicker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.LocaleList;
import android.util.Log;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.app.LocaleStore;
import com.android.settings.R;
import com.android.settings.widget.PreferenceCategoryController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TermsOfAddressCategoryController extends PreferenceCategoryController {
    private static final String KEY_CATEGORY_TERMS_OF_ADDRESS = "key_category_terms_of_address";
    private static final String KEY_TERMS_OF_ADDRESS = "key_terms_of_address";
    private static final String TAG = "TermsOfAddressCategory";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public TermsOfAddressCategoryController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (((PreferenceCategory) preferenceScreen.findPreference(KEY_CATEGORY_TERMS_OF_ADDRESS))
                == null) {
            Log.d(TAG, "displayPreference(), can not find the category.");
        } else if (isAvailable()) {
            new TermsOfAddressController(this.mContext, KEY_TERMS_OF_ADDRESS)
                    .displayPreference(preferenceScreen);
        }
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        LocaleStore.LocaleInfo localeInfo = LocaleStore.getLocaleInfo(Locale.getDefault());
        List asList =
                Arrays.asList(
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.terms_of_address_supported_languages));
        List asList2 =
                Arrays.asList(
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.terms_of_address_unsupported_locales));
        Locale stripExtensions = localeInfo.getLocale().stripExtensions();
        String language = stripExtensions.getLanguage();
        String languageTag = stripExtensions.toLanguageTag();
        if (DEBUG) {
            Log.d(TAG, "current language: " + language);
            Log.d(TAG, "current locale tag: " + languageTag);
        }
        return ((!asList.contains(language) || asList2.contains(languageTag))
                        && !LocaleList.isPseudoLocale(stripExtensions))
                ? 2
                : 0;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
