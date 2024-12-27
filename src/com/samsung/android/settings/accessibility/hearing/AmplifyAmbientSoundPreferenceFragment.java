package com.samsung.android.settings.accessibility.hearing;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchIndexableData;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutContentObserver;
import com.samsung.android.settings.accessibility.base.search.AccessibilityBaseSearchIndexProvider;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AmplifyAmbientSoundPreferenceFragment extends AccessibilityShortcutPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(
                    R.xml.amplify_ambient_sound,
                    "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment",
                    "amplify_ambient_sound");
    public ShortcutContentObserver.AnonymousClass1 shortcutContentObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.hearing.AmplifyAmbientSoundPreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityBaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!isPageSearchEnabled(context)) {
                return null;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text = context.getText(R.string.amplify_ambient_sound_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT
                            .flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }
    }

    public AmplifyAmbientSoundPreferenceFragment() {
        super(null);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final ComponentName getComponentName() {
        return AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final CharSequence getLabelName() {
        return getResources().getText(R.string.amplify_ambient_sound_title);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AmplifyAmbientSoundPreferenceFragmentFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4008;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.amplify_ambient_sound;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT
                .flattenToString();
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context prefContext = getPrefContext();
        int i = ShortcutContentObserver.$r8$clinit;
        this.shortcutContentObserver =
                new ShortcutContentObserver.AnonymousClass1(
                        new Handler(prefContext.getMainLooper()), prefContext, 1);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        ShortcutContentObserver.AnonymousClass1 anonymousClass1 = this.shortcutContentObserver;
        if (anonymousClass1 != null) {
            getPrefContext().getContentResolver().unregisterContentObserver(anonymousClass1);
        }
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        ShortcutContentObserver.AnonymousClass1 anonymousClass1 = this.shortcutContentObserver;
        if (anonymousClass1 != null) {
            anonymousClass1.register(getPrefContext().getContentResolver());
        }
    }
}
