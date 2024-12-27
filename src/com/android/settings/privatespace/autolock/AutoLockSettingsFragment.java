package com.android.settings.privatespace.autolock;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.privatespace.PrivateSpaceMaintainer;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.TopIntroPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoLockSettingsFragment extends RadioButtonPickerFragment {
    public CharSequence[] mAutoLockRadioOptions;
    public CharSequence[] mAutoLockRadioValues;
    public PrivateSpaceMaintainer mPrivateSpaceMaintainer;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoLockCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;

        public AutoLockCandidateInfo(String str, CharSequence charSequence) {
            super(true);
            this.mLabel = charSequence;
            this.mKey = str;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mKey;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mLabel;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        TopIntroPreference topIntroPreference =
                new TopIntroPreference(preferenceScreen.getContext());
        topIntroPreference.setTitle(R.string.private_space_auto_lock_page_summary);
        preferenceScreen.addPreference(topIntroPreference);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        if (this.mAutoLockRadioValues != null) {
            int i = 0;
            while (true) {
                CharSequence[] charSequenceArr = this.mAutoLockRadioValues;
                if (i >= charSequenceArr.length) {
                    break;
                }
                arrayList.add(
                        new AutoLockCandidateInfo(
                                charSequenceArr[i].toString(), this.mAutoLockRadioOptions[i]));
                i++;
            }
        } else {
            Log.e("PSAutoLockSetting", "Autolock options do not exist.");
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        PrivateSpaceMaintainer privateSpaceMaintainer = this.mPrivateSpaceMaintainer;
        privateSpaceMaintainer.getClass();
        int i = 0;
        if (Flags.allowPrivateProfile()
                && android.multiuser.Flags.supportAutolockForPrivateSpace()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            i =
                    Settings.Secure.getInt(
                            privateSpaceMaintainer.mContext.getContentResolver(),
                            "private_space_auto_lock",
                            0);
        }
        return Integer.toString(i);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2040;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.private_space_auto_lock_settings;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mPrivateSpaceMaintainer = PrivateSpaceMaintainer.getInstance(context);
        this.mAutoLockRadioOptions =
                context.getResources().getStringArray(R.array.private_space_auto_lock_options);
        this.mAutoLockRadioValues =
                context.getResources()
                        .getStringArray(R.array.private_space_auto_lock_options_values);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile()
                && android.multiuser.Flags.supportAutolockForPrivateSpace()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            super.onCreate(bundle);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mPrivateSpaceMaintainer.isPrivateSpaceLocked()) {
            finish();
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            this.mPrivateSpaceMaintainer.setPrivateSpaceAutoLockSetting(parseInt);
            MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
            FragmentActivity activity = getActivity();
            metricsFeatureProvider.getClass();
            metricsFeatureProvider.action(
                    MetricsFeatureProvider.getAttribution(activity),
                    1908,
                    2040,
                    parseInt,
                    "private_space_autolock_mode");
            return true;
        } catch (NumberFormatException e) {
            Log.e("PSAutoLockSetting", "could not persist screen timeout setting", e);
            return true;
        }
    }
}
