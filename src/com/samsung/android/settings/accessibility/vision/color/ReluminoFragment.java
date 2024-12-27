package com.samsung.android.settings.accessibility.vision.color;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.accessibility.AccessibilitySettingsContentObserver;
import com.android.settings.accessibility.ToggleFeaturePreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.samsung.android.settings.accessibility.vision.controllers.ReluminoRadioButtonPreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ReluminoFragment extends ToggleFeaturePreferenceFragment
        implements Preference.OnPreferenceChangeListener,
                ReluminoRadioButtonPreferenceController.OnChangeListener {
    public final AnonymousClass2 contentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.vision.color.ReluminoFragment.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    ReluminoFragment.this.updateSwitchBarToggleSwitch();
                }
            };
    public static final List sControllers = new ArrayList();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.accessibility_settings_relumino);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.color.ReluminoFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!SecAccessibilityUtils.isSupportRelumino()) {
                return null;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text = context.getText(R.string.relumino_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityConstant.COMPONENT_NAME_RELUMINO_SHORTCUT.flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SecAccessibilityUtils.isSupportRelumino();
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        if (((ArrayList) sControllers).size() == 0) {
            for (String str : context.getResources().getStringArray(R.array.relumino_keys)) {
                ((ArrayList) sControllers)
                        .add(new ReluminoRadioButtonPreferenceController(context, lifecycle, str));
            }
        }
        return sControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3050;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("top_intro");
        arrayList.add("description_preference");
        arrayList.add(AccessibilityConstant.COMPONENT_NAME_RELUMINO_SHORTCUT.flattenToString());
        arrayList.add("empty_category_1");
        arrayList.add("seek_bar_preference");
        arrayList.add("empty_category_2");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "relumino_adaptive",
                "relumino_black",
                "relumino_white",
                "relumino_green");
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_relumino;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityConstant.COMPONENT_NAME_RELUMINO_SHORTCUT.flattenToString();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        DescriptionPreference descriptionPreference =
                (DescriptionPreference) findPreference("description_preference");
        descriptionPreference.setIcon(R.drawable.relumino_outline_preview);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.addOnConfigurationChangedListener(
                    new SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0(
                            descriptionPreference));
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mComponentName = AccessibilityConstant.COMPONENT_NAME_RELUMINO_SHORTCUT;
        this.mPackageName = getText(R.string.relumino_title);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Iterator it =
                ((ArrayList) buildPreferenceControllers$1(getPrefContext(), getSettingsLifecycle()))
                        .iterator();
        while (it.hasNext()) {
            ((ReluminoRadioButtonPreferenceController) ((AbstractPreferenceController) it.next()))
                    .setOnChangeListener(null);
        }
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SecAccessibilityUtils.setRelumino(getContext());
        return true;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if ((Settings.Secure.getInt(getContentResolver(), "relumino_switch", 0) == 1) == z) {
            return;
        }
        Settings.Secure.putInt(getContentResolver(), "relumino_switch", z ? 1 : 0);
        SecAccessibilityUtils.setRelumino(getContext());
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateSwitchBarToggleSwitch();
        Iterator it =
                ((ArrayList) buildPreferenceControllers$1(getPrefContext(), getSettingsLifecycle()))
                        .iterator();
        while (it.hasNext()) {
            ReluminoRadioButtonPreferenceController reluminoRadioButtonPreferenceController =
                    (ReluminoRadioButtonPreferenceController)
                            ((AbstractPreferenceController) it.next());
            reluminoRadioButtonPreferenceController.setOnChangeListener(this);
            reluminoRadioButtonPreferenceController.displayPreference(getPreferenceScreen());
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("relumino_switch"), false, this.contentObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(this.contentObserver);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View peekDecorView = getActivity().getWindow().peekDecorView();
        if (peekDecorView != null) {
            peekDecorView.setAccessibilityPaneTitle(getString(R.string.relumino_title));
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void registerKeysToObserverCallback(
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add("accessibility_display_relumino_enabled");
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                arrayList,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.samsung.android.settings.accessibility.vision.color.ReluminoFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        List list = ReluminoFragment.sControllers;
                        ReluminoFragment.this.updateSwitchBarToggleSwitch();
                    }
                });
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean z = Settings.Secure.getInt(getContentResolver(), "relumino_switch", 0) == 1;
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != z) {
            this.mToggleServiceSwitchBar.setCheckedInternal(z);
        }
    }
}
