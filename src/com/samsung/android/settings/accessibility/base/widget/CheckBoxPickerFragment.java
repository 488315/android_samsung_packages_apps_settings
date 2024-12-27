package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.preference.CheckBoxPreference;
import androidx.preference.TwoStatePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CheckBoxPickerFragment extends TwoStateCandidateListFragment {
    public Context context;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DetailCandidateInfo {
        public final Drawable mIcon;
        public final String mKey;
        public final CharSequence mLabel;
        public final CharSequence mSummary;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class Builder {
            public final String mKey;
            public final CharSequence mLabel;
            public Drawable mIcon = null;
            public CharSequence mSummary = null;

            public Builder(String str, String str2) {
                this.mKey = str;
                this.mLabel = str2;
            }
        }

        public DetailCandidateInfo(Builder builder) {
            String str = builder.mKey;
            CharSequence charSequence = builder.mLabel;
            this.mKey = str;
            this.mLabel = charSequence;
            this.mIcon = builder.mIcon;
            this.mSummary = builder.mSummary;
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.TwoStateCandidateListFragment
    public final void bindPreference(
            TwoStatePreference twoStatePreference,
            String str,
            DetailCandidateInfo detailCandidateInfo,
            String str2) {
        super.bindPreference(twoStatePreference, str, detailCandidateInfo, str2);
        if (detailCandidateInfo instanceof DetailCandidateInfo) {
            if (detailCandidateInfo.mIcon != null) {
                twoStatePreference.setIcon(detailCandidateInfo.mIcon);
            }
            CharSequence charSequence = detailCandidateInfo.mSummary;
            if (charSequence != null) {
                twoStatePreference.setSummary(charSequence);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.TwoStateCandidateListFragment
    public CheckBoxPreference getPrefInstance() {
        return new CheckBoxPreference(this.context, null);
    }
}
