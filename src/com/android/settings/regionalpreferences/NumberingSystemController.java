package com.android.settings.regionalpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.LocaleList;

import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocaleStore;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NumberingSystemController extends BasePreferenceController {
    private static final String TAG = "NumberingSystemController";
    private LocaleList mLocaleList;

    public NumberingSystemController(Context context, String str) {
        super(context, str);
        LocaleStore.fillCache(context);
        this.mLocaleList = getNumberingSystemLocale();
    }

    private static LocaleList convertToLocaleList(Set<Locale> set) {
        return set.isEmpty()
                ? LocaleList.getEmptyLocaleList()
                : new LocaleList(
                        (Locale[])
                                set.stream()
                                        .toArray(
                                                new NumberingSystemController$$ExternalSyntheticLambda0()));
    }

    private static LocaleList getNumberingSystemLocale() {
        LocaleList localeList = LocaleList.getDefault();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            if (LocaleStore.getLocaleInfo(locale).hasNumberingSystems()) {
                hashSet.add(locale);
            }
        }
        return convertToLocaleList(hashSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Locale[] lambda$convertToLocaleList$0(int i) {
        return new Locale[i];
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mLocaleList.isEmpty() ? 2 : 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        LocaleList numberingSystemLocale = getNumberingSystemLocale();
        if (numberingSystemLocale.isEmpty()) {
            return ApnSettings.MVNO_NONE;
        }
        Locale[] localeArr = new Locale[numberingSystemLocale.size()];
        for (int i = 0; i < numberingSystemLocale.size(); i++) {
            Locale locale = numberingSystemLocale.get(i);
            localeArr[i] =
                    new Locale.Builder()
                            .setLocale(locale.stripExtensions())
                            .setUnicodeLocaleKeyword("nu", locale.getUnicodeLocaleType("nu"))
                            .build();
        }
        Locale locale2 = Locale.getDefault();
        return LocaleHelper.toSentenceCase(
                LocaleHelper.getDisplayLocaleList(new LocaleList(localeArr), locale2, 2), locale2);
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
