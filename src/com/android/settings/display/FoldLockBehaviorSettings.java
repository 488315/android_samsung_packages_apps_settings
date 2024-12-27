package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.settings.R;
import com.android.settings.utils.CandidateInfoExtra;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FoldLockBehaviorSettings extends RadioButtonPickerFragment {
    public static final HashSet SETTING_VALUES =
            new HashSet(
                    Set.of(
                            "stay_awake_on_fold_key",
                            "selective_stay_awake_key",
                            "sleep_on_fold_key"));
    public Context mContext;
    public final FoldGracePeriodProvider mFoldGracePeriodProvider = new FoldGracePeriodProvider();

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        if (candidateInfo instanceof CandidateInfoExtra) {
            selectorWithWidgetPreference.setSummary(((CandidateInfoExtra) candidateInfo).mSummary);
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new CandidateInfoExtra(
                        resourceToString(R.string.stay_awake_on_fold_title),
                        resourceToString(R.string.stay_awake_on_fold_summary),
                        "stay_awake_on_fold_key"));
        if (this.mFoldGracePeriodProvider.isEnabled()) {
            arrayList.add(
                    new CandidateInfoExtra(
                            resourceToString(R.string.stay_awake_on_lockscreen_title),
                            resourceToString(R.string.stay_awake_on_lockscreen_summary),
                            "selective_stay_awake_key"));
        } else {
            arrayList.add(
                    new CandidateInfoExtra(
                            resourceToString(R.string.selective_stay_awake_title),
                            resourceToString(R.string.selective_stay_awake_summary),
                            "selective_stay_awake_key"));
        }
        arrayList.add(
                new CandidateInfoExtra(
                        resourceToString(R.string.sleep_on_fold_title),
                        resourceToString(R.string.sleep_on_fold_summary),
                        "sleep_on_fold_key"));
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        String stringForUser =
                Settings.System.getStringForUser(
                        this.mContext.getContentResolver(), "fold_lock_behavior_setting", -2);
        if (stringForUser == null) {
            stringForUser = "selective_stay_awake_key";
        }
        if (SETTING_VALUES.contains(stringForUser)) {
            return stringForUser;
        }
        Log.e(
                "FoldLockBehaviorSetting",
                "getDefaultKey: Invalid setting value, returning default setting value");
        return "selective_stay_awake_key";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2038;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.fold_lock_behavior_settings;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        setIllustrationLottieAnimation(getDefaultKey());
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void onSelectionPerformed(boolean z) {
        if (z) {
            setIllustrationLottieAnimation(getDefaultKey());
            updateCandidates();
        }
    }

    public final String resourceToString(int i) {
        return this.mContext.getText(i).toString();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        if (!SETTING_VALUES.contains(str)) {
            Log.e("FoldLockBehaviorSetting", "setDefaultKey: Can not set invalid key: " + str);
            str = "selective_stay_awake_key";
        }
        Settings.System.putStringForUser(
                this.mContext.getContentResolver(), "fold_lock_behavior_setting", str, -2);
        return true;
    }

    public final void setIllustrationLottieAnimation(String str) {
        RadioButtonPickerFragment.IllustrationType illustrationType;
        illustrationType = RadioButtonPickerFragment.IllustrationType.LOTTIE_ANIMATION;
        switch (str) {
            case "stay_awake_on_fold_key":
                this.mIllustrationId = R.raw.fold_setting_stay_awake_on_fold_lottie;
                this.mIllustrationType = illustrationType;
                break;
            case "sleep_on_fold_key":
                this.mIllustrationId = R.raw.fold_setting_sleep_on_fold_lottie;
                this.mIllustrationType = illustrationType;
                break;
            case "selective_stay_awake_key":
                if (!this.mFoldGracePeriodProvider.isEnabled()) {
                    this.mIllustrationId = R.raw.fold_setting_selective_stay_awake_lottie;
                    this.mIllustrationType = illustrationType;
                    break;
                } else {
                    this.mIllustrationId =
                            R.raw.fold_setting_grace_period_selective_stay_awake_lottie;
                    this.mIllustrationType = illustrationType;
                    break;
                }
        }
    }
}
