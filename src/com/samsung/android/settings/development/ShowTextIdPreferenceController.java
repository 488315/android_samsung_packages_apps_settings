package com.samsung.android.settings.development;

import android.os.LocaleList;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.settings.R;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ShowTextIdPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "show_text_id";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        setShowTextIdCheckState(false, true);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return setShowTextIdCheckState(((Boolean) obj).booleanValue(), false);
    }

    public final boolean setShowTextIdCheckState(boolean z, boolean z2) {
        Locale locale = Locale.getDefault();
        if (TextUtils.equals(locale.getLanguage() + "-" + locale.getCountry(), "en-DI")) {
            if (!z2) {
                Toast.makeText(this.mContext, R.string.show_text_id_toast_warning, 1).show();
            }
            return false;
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "show_text_id", z ? 1 : 0);
        if (!z) {
            ArrayList arrayList = new ArrayList();
            LocaleList locales = LocalePicker.getLocales();
            for (int i = 0; i < locales.size(); i++) {
                arrayList.add(LocaleStore.getLocaleInfo(locales.get(i)));
            }
            int i2 = 0;
            while (true) {
                if (i2 >= arrayList.size()) {
                    i2 = -1;
                    break;
                }
                if (TextUtils.equals(
                        ((LocaleStore.LocaleInfo) arrayList.get(i2)).getId(), "en-DI")) {
                    break;
                }
                i2++;
            }
            if (i2 == -1) {
                return true;
            }
            arrayList.remove((LocaleStore.LocaleInfo) arrayList.get(i2));
            int size = arrayList.size();
            Locale[] localeArr = new Locale[size];
            for (int i3 = 0; i3 < size; i3++) {
                localeArr[i3] = ((LocaleStore.LocaleInfo) arrayList.get(i3)).getLocale();
            }
            LocaleList localeList = new LocaleList(localeArr);
            LocaleList.setDefault(localeList);
            LocalePicker.updateLocales(localeList);
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        Settings.System.getInt(
                                        this.mContext.getContentResolver(), "show_text_id", 0)
                                != 0);
    }
}
