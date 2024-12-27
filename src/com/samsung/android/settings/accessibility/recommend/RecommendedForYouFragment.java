package com.samsung.android.settings.accessibility.recommend;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettingsContentObserver;

import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RecommendedForYouFragment extends AccessibilityDashboardFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilitySettingsContentObserver mSettingsContentObserver;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "RecommendedForYouFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1003;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_recommended_for_you;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                new AccessibilitySettingsContentObserver(new Handler(Looper.myLooper()));
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        final int i = 0;
        AccessibilitySettingsContentObserver.ContentObserverCallback contentObserverCallback =
                new AccessibilitySettingsContentObserver.ContentObserverCallback(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.recommend.RecommendedForYouFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ RecommendedForYouFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        RecommendedForYouFragment recommendedForYouFragment = this.f$0;
                        switch (i) {
                            case 0:
                                int i2 = RecommendedForYouFragment.$r8$clinit;
                                recommendedForYouFragment.updatePreferenceStates();
                                break;
                            default:
                                int i3 = RecommendedForYouFragment.$r8$clinit;
                                recommendedForYouFragment.updatePreferenceStates();
                                break;
                        }
                    }
                };
        ((HashMap) accessibilitySettingsContentObserver.mUrisToCallback)
                .put(Collections.emptyList(), contentObserverCallback);
        final int i2 = 1;
        this.mSettingsContentObserver.registerKeysToObserverCallback(
                List.of(
                        "accessibility_button_targets",
                        "accessibility_shortcut_target_service",
                        "accessibility_direct_access_target_service"),
                new AccessibilitySettingsContentObserver.ContentObserverCallback(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.recommend.RecommendedForYouFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ RecommendedForYouFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        RecommendedForYouFragment recommendedForYouFragment = this.f$0;
                        switch (i2) {
                            case 0:
                                int i22 = RecommendedForYouFragment.$r8$clinit;
                                recommendedForYouFragment.updatePreferenceStates();
                                break;
                            default:
                                int i3 = RecommendedForYouFragment.$r8$clinit;
                                recommendedForYouFragment.updatePreferenceStates();
                                break;
                        }
                    }
                });
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        getListView().seslSetFillBottomEnabled(true);
        getListView()
                .seslSetFillBottomColor(
                        getPrefContext().getColor(R.color.sec_widget_round_and_bgcolor));
        return onCreateView;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mSettingsContentObserver.register(getContentResolver());
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                this.mSettingsContentObserver;
        ContentResolver contentResolver = getContentResolver();
        accessibilitySettingsContentObserver.getClass();
        contentResolver.unregisterContentObserver(accessibilitySettingsContentObserver);
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment
    public final void update(Uri uri) {
        updatePreferenceStates();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final void writePreferenceClickMetric(Preference preference) {}
}
