package com.android.settings.language;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.LocaleList;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocalePicker;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.localepicker.LocaleFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PhoneLanguagePreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private UserManager um;

    public PhoneLanguagePreferenceController(Context context, String str) {
        super(context, str);
        this.um = (UserManager) context.getSystemService("user");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        UserManager userManager = this.um;
        StringBuilder sb = Utils.sBuilder;
        return (!userManager.getUserInfo(userManager.getUserHandle()).isSecureFolder()
                        && !KnoxUtils.checkKnoxCustomSettingsHiddenItem(32)
                        && this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_show_phone_language)
                        && this.mContext.getAssets().getLocales().length > 1)
                ? 0
                : 2;
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
        String sentenceCase;
        if (preference == null) {
            return;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((LocaleFeatureProviderImpl) featureFactoryImpl.localeFeatureProvider$delegate.getValue())
                .getClass();
        LocaleList locales = LocalePicker.getLocales();
        if (locales.isEmpty()) {
            sentenceCase = ApnSettings.MVNO_NONE;
        } else {
            Locale[] localeArr = new Locale[locales.size()];
            for (int i = 0; i < locales.size(); i++) {
                Locale locale = locales.get(i);
                localeArr[i] =
                        new Locale.Builder()
                                .setLocale(locale.stripExtensions())
                                .setUnicodeLocaleKeyword("nu", locale.getUnicodeLocaleType("nu"))
                                .build();
            }
            Locale locale2 = Locale.getDefault();
            sentenceCase =
                    LocaleHelper.toSentenceCase(
                            LocaleHelper.getDisplayLocaleList(
                                    new LocaleList(localeArr), locale2, 2),
                            locale2);
        }
        preference.setSummary(sentenceCase);
        if (preference instanceof SecPreference) {
            SecPreferenceUtils.applySummaryColor((SecPreference) preference, true);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
