package com.android.settings.development;

import android.app.Flags;
import android.app.IActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GrammaticalGenderPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String GRAMMATICAL_GENDER_PROPERTY = "persist.sys.grammatical_gender";
    public final IActivityManager mActivityManager;
    public final String[] mListSummaries;
    public final String[] mListValues;

    public GrammaticalGenderPreferenceController(
            Context context, IActivityManager iActivityManager) {
        super(context);
        this.mListValues = context.getResources().getStringArray(R.array.grammatical_gender_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.grammatical_gender_entries);
        this.mActivityManager = iActivityManager;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "grammatical_gender";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Flags.systemTermsOfAddressEnabled();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int i = SystemProperties.getInt(GRAMMATICAL_GENDER_PROPERTY, 0);
        SystemProperties.set(GRAMMATICAL_GENDER_PROPERTY, obj.toString());
        updateState(this.mPreference);
        try {
            Configuration configuration = this.mActivityManager.getConfiguration();
            if (configuration.getGrammaticalGender() != i) {
                return true;
            }
            configuration.setGrammaticalGender(Integer.parseInt(obj.toString()));
            this.mActivityManager.updatePersistentConfiguration(configuration);
            return true;
        } catch (RemoteException unused) {
            return true;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String[] strArr;
        ListPreference listPreference = (ListPreference) preference;
        String str = SystemProperties.get(GRAMMATICAL_GENDER_PROPERTY);
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = this.mListValues;
            if (i2 >= strArr.length) {
                break;
            }
            if (TextUtils.equals(str, strArr[i2])) {
                i = i2;
                break;
            }
            i2++;
        }
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
    }
}
