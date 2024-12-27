package com.android.settings.gestures;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.LabeledSeekBarPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class GestureNavigationSettingsFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.gesture_navigation_settings);
    public float[] mBackGestureInsetScales;
    public float mDefaultBackGestureInset;
    public BackGestureIndicatorView mIndicatorView;
    public WindowManager mWindowManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.gestures.GestureNavigationSettingsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SystemNavigationPreferenceController.isGestureAvailable(context);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "GestureNavigationSettingsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1748;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.gesture_navigation_settings;
    }

    public final void initSeekBarPreference(final String str) {
        LabeledSeekBarPreference labeledSeekBarPreference =
                (LabeledSeekBarPreference) getPreferenceScreen().findPreference(str);
        labeledSeekBarPreference.mContinuousUpdates = true;
        labeledSeekBarPreference.mHapticFeedbackMode = 1;
        final String str2 =
                str == "gesture_left_back_sensitivity"
                        ? "back_gesture_inset_scale_left"
                        : "back_gesture_inset_scale_right";
        float f = Settings.Secure.getFloat(getContext().getContentResolver(), str2, 1.0f);
        float f2 = Float.MAX_VALUE;
        int i = -1;
        int i2 = 0;
        while (true) {
            float[] fArr = this.mBackGestureInsetScales;
            if (i2 >= fArr.length) {
                labeledSeekBarPreference.setProgress(i, true);
                labeledSeekBarPreference.setOnPreferenceChangeListener(
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.android.settings.gestures.GestureNavigationSettingsFragment$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj) {
                                GestureNavigationSettingsFragment
                                        gestureNavigationSettingsFragment =
                                                GestureNavigationSettingsFragment.this;
                                int i3 =
                                        (int)
                                                (gestureNavigationSettingsFragment
                                                                .mDefaultBackGestureInset
                                                        * gestureNavigationSettingsFragment
                                                                .mBackGestureInsetScales[
                                                                ((Integer) obj).intValue()]);
                                BackGestureIndicatorView backGestureIndicatorView =
                                        gestureNavigationSettingsFragment.mIndicatorView;
                                BackGestureIndicatorDrawable backGestureIndicatorDrawable =
                                        str == "gesture_left_back_sensitivity"
                                                ? backGestureIndicatorView.mLeftDrawable
                                                : backGestureIndicatorView.mRightDrawable;
                                if (i3 == 0) {
                                    backGestureIndicatorDrawable.mHandler.sendEmptyMessage(3);
                                } else {
                                    BackGestureIndicatorDrawable.AnonymousClass1 anonymousClass1 =
                                            backGestureIndicatorDrawable.mHandler;
                                    anonymousClass1.sendMessage(
                                            anonymousClass1.obtainMessage(1, i3, 0));
                                }
                                return true;
                            }
                        });
                labeledSeekBarPreference.mStopListener =
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.android.settings.gestures.GestureNavigationSettingsFragment$$ExternalSyntheticLambda1
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj) {
                                GestureNavigationSettingsFragment
                                        gestureNavigationSettingsFragment =
                                                GestureNavigationSettingsFragment.this;
                                BackGestureIndicatorView backGestureIndicatorView =
                                        gestureNavigationSettingsFragment.mIndicatorView;
                                (str == "gesture_left_back_sensitivity"
                                                ? backGestureIndicatorView.mLeftDrawable
                                                : backGestureIndicatorView.mRightDrawable)
                                        .mHandler.sendEmptyMessage(3);
                                Settings.Secure.putFloat(
                                        gestureNavigationSettingsFragment
                                                .getContext()
                                                .getContentResolver(),
                                        str2,
                                        gestureNavigationSettingsFragment
                                                .mBackGestureInsetScales[
                                                ((Integer) obj).intValue()]);
                                return true;
                            }
                        };
                return;
            } else {
                float abs = Math.abs(fArr[i2] - f);
                if (abs < f2) {
                    i = i2;
                    f2 = abs;
                }
                i2++;
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIndicatorView = new BackGestureIndicatorView(getActivity());
        this.mWindowManager = (WindowManager) getActivity().getSystemService("window");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        Resources resources = getActivity().getResources();
        this.mDefaultBackGestureInset =
                resources.getDimensionPixelSize(
                        android.R.dimen.config_letterboxTabletopModePositionMultiplier);
        TypedArray obtainTypedArray = resources.obtainTypedArray(android.R.array.config_ntpServers);
        int length = obtainTypedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = obtainTypedArray.getFloat(i, 1.0f);
        }
        obtainTypedArray.recycle();
        this.mBackGestureInsetScales = fArr;
        initSeekBarPreference("gesture_left_back_sensitivity");
        initSeekBarPreference("gesture_right_back_sensitivity");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mWindowManager.removeView(this.mIndicatorView);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        WindowManager windowManager = this.mWindowManager;
        BackGestureIndicatorView backGestureIndicatorView = this.mIndicatorView;
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        backGestureIndicatorView.getClass();
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(
                        2038, (attributes.flags & Integer.MIN_VALUE) | 16777240, -3);
        layoutParams.setTitle("BackGestureIndicatorView");
        layoutParams.token = backGestureIndicatorView.getContext().getActivityToken();
        windowManager.addView(backGestureIndicatorView, layoutParams);
    }
}
