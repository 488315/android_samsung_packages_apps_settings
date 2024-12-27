package com.android.settings.accessibility;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.ListPreference;

import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocalePreference extends ListPreference {
    public LocalePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public final void init(Context context) {
        int i = 0;
        List allAssetLocales = LocalePicker.getAllAssetLocales(context, false);
        int size = allAssetLocales.size();
        int i2 = size + 1;
        CharSequence[] charSequenceArr = new CharSequence[i2];
        CharSequence[] charSequenceArr2 = new CharSequence[i2];
        charSequenceArr[0] = context.getResources().getString(R.string.locale_default);
        charSequenceArr2[0] = ApnSettings.MVNO_NONE;
        while (i < size) {
            LocalePicker.LocaleInfo localeInfo = (LocalePicker.LocaleInfo) allAssetLocales.get(i);
            i++;
            charSequenceArr[i] =
                    LocaleStore.getLocaleInfo(localeInfo.getLocale()).getFullNameNative();
            charSequenceArr2[i] = localeInfo.getLocale().toString();
        }
        this.mEntries = charSequenceArr;
        this.mEntryValues = charSequenceArr2;
    }

    public LocalePreference(Context context) {
        super(context, null);
        init(context);
    }
}
