package com.android.settings.password;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.preference.Preference;

import com.android.settings.R;

import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupBiometricsChooseLockGeneric extends SetupChooseLockGeneric {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InternalActivity extends SetupChooseLockGeneric.InternalActivity {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class InternalSetupBiometricsChooseLockGenericFragment
                extends SetupChooseLockGeneric.InternalActivity
                        .InternalSetupChooseLockGenericFragment {}

        @Override // com.android.settings.password.SetupChooseLockGeneric.InternalActivity,
                  // com.android.settings.password.ChooseLockGeneric
        public final Class getFragmentClass() {
            return InternalSetupBiometricsChooseLockGenericFragment.class;
        }

        @Override // com.android.settings.password.SetupChooseLockGeneric.InternalActivity,
                  // com.android.settings.password.ChooseLockGeneric,
                  // com.android.settings.SettingsActivity
        public final boolean isValidFragment(String str) {
            return InternalSetupBiometricsChooseLockGenericFragment.class.getName().equals(str);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SetupBiometricsChooseLockGenericFragment
            extends SetupChooseLockGeneric.SetupChooseLockGenericFragment {
        @Override // com.android.settings.password.SetupChooseLockGeneric.SetupChooseLockGenericFragment, com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final Class getInternalActivityClass() {
            return InternalActivity.class;
        }

        @Override // com.android.settings.password.SetupChooseLockGeneric.SetupChooseLockGenericFragment, com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment, androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, Intent intent) {
            SemLog.d(
                    "SetupBiometricsChooseLockGeneric",
                    "onActivityResult : requestCode = " + i + ", resultCode = " + i2);
            super.onActivityResult(i, i2, intent);
        }

        @Override // com.android.settings.password.SetupChooseLockGeneric.SetupChooseLockGenericFragment, com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            int i;
            super.onCreate(bundle);
            if (this.mForFace) {
                i = R.string.bio_face_recognition_title;
            } else if (!this.mForFingerprint) {
                return;
            } else {
                i = R.string.sec_fingerprint;
            }
            setPreferenceTitle$1(ScreenLockType.PIN, i);
            setPreferenceTitle$1(ScreenLockType.PASSWORD, i);
            setPreferenceTitle$1(ScreenLockType.PATTERN, i);
        }

        @Override // com.android.settings.password.SetupChooseLockGeneric.SetupChooseLockGenericFragment, com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            String str;
            int i;
            super.onViewCreated(view, bundle);
            GlifLayout glifLayout = (GlifLayout) view;
            if (getActivity() != null) {
                getActivity().setTitle(R.string.sec_biometrics_set_secure_screen_lock);
            }
            glifLayout.setHeaderText(R.string.sec_biometrics_set_secure_screen_lock);
            if (this.mForFace) {
                i = R.string.sec_biometrics_set_secure_screen_lock_face;
            } else {
                if (!this.mForFingerprint) {
                    str = ApnSettings.MVNO_NONE;
                    glifLayout.setDescriptionText(str);
                    glifLayout.setIcon(
                            getContext().getDrawable(R.drawable.sec_ic_biometric_lock_suw));
                }
                i = R.string.sec_biometrics_set_secure_screen_lock_fingerprints;
            }
            str = getString(i);
            glifLayout.setDescriptionText(str);
            glifLayout.setIcon(getContext().getDrawable(R.drawable.sec_ic_biometric_lock_suw));
        }

        public final void setPreferenceTitle$1(ScreenLockType screenLockType, int i) {
            Preference findPreference = findPreference(screenLockType.preferenceKey);
            if (findPreference != null) {
                String language = getResources().getConfiguration().locale.getLanguage();
                String str =
                        "ar".equals(language)
                                ? "، "
                                : "ja".equals(language) ? "、" : "zh".equals(language) ? "，" : ", ";
                StringBuilder sb = new StringBuilder();
                sb.append(findPreference.getTitle());
                sb.append(str);
                sb.append(getString(i));
                findPreference.setTitle(sb);
                findPreference.setSummary((CharSequence) null);
            }
        }
    }

    @Override // com.android.settings.password.SetupChooseLockGeneric,
              // com.android.settings.password.ChooseLockGeneric
    public final Class getFragmentClass() {
        return SetupBiometricsChooseLockGenericFragment.class;
    }

    @Override // com.android.settings.password.SetupChooseLockGeneric,
              // com.android.settings.password.ChooseLockGeneric,
              // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return SetupBiometricsChooseLockGenericFragment.class.getName().equals(str);
    }
}
