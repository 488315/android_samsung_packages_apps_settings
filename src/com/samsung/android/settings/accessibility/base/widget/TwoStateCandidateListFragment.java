package com.samsung.android.settings.accessibility.base.widget;

import android.os.Bundle;
import android.util.ArrayMap;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class TwoStateCandidateListFragment extends DashboardFragment
        implements Preference.OnPreferenceChangeListener {
    public final Map mCandidates = new ArrayMap();

    public void bindPreference(
            TwoStatePreference twoStatePreference,
            String str,
            CheckBoxPickerFragment.DetailCandidateInfo detailCandidateInfo,
            String str2) {
        twoStatePreference.setTitle(detailCandidateInfo.mLabel);
        twoStatePreference.setKey(detailCandidateInfo.mKey);
    }

    public abstract List getCandidates();

    public CharSequence getDescription() {
        return null;
    }

    public abstract TwoStatePreference getPrefInstance();

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        if (getPreferenceScreenResId() <= 0) {
            setPreferenceScreen(getPreferenceManager().createPreferenceScreen(getPrefContext()));
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        CharSequence description = getDescription();
        if (description != null) {
            DescriptionPreference descriptionPreference =
                    new DescriptionPreference(getPrefContext());
            descriptionPreference.setTitle(description);
            preferenceScreen.addPreference(descriptionPreference);
        }
        ((ArrayMap) this.mCandidates).clear();
        List candidates = getCandidates();
        Iterator it = ((ArrayList) candidates).iterator();
        while (it.hasNext()) {
            CheckBoxPickerFragment.DetailCandidateInfo detailCandidateInfo =
                    (CheckBoxPickerFragment.DetailCandidateInfo) it.next();
            ((ArrayMap) this.mCandidates).put(detailCandidateInfo.mKey, detailCandidateInfo);
        }
        PreferenceScreen preferenceScreen2 = getPreferenceScreen();
        Iterator it2 = ((ArrayList) candidates).iterator();
        while (it2.hasNext()) {
            CheckBoxPickerFragment.DetailCandidateInfo detailCandidateInfo2 =
                    (CheckBoxPickerFragment.DetailCandidateInfo) it2.next();
            TwoStatePreference prefInstance = getPrefInstance();
            bindPreference(
                    prefInstance,
                    detailCandidateInfo2.mKey,
                    detailCandidateInfo2,
                    ApnSettings.MVNO_NONE);
            prefInstance.setOnPreferenceChangeListener(this);
            preferenceScreen2.addPreference(prefInstance);
        }
    }
}
