package com.samsung.android.settings.accessibility.vision.color;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.accessibility.ToggleFeaturePreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.base.widget.A11ySeekBarPreference;
import com.samsung.android.settings.accessibility.base.widget.DividerItemDecorator;
import com.samsung.android.settings.accessibility.vision.controllers.ColorLensRadioButtonPreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ColorLensFragment extends ToggleFeaturePreferenceFragment
        implements Preference.OnPreferenceChangeListener,
                ColorLensRadioButtonPreferenceController.OnChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.accessibility_settings_color_lens);
    public A11ySeekBarPreference mSeekBarPref;
    public final List controllers = new ArrayList();
    public final AnonymousClass1 contentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.vision.color.ColorLensFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    if (uri.equals(Settings.Secure.getUriFor("color_lens_switch"))) {
                        ColorLensFragment.this.updateSwitchBarToggleSwitch();
                        return;
                    }
                    if (uri.equals(Settings.Secure.getUriFor("color_lens_opacity"))) {
                        ColorLensFragment colorLensFragment = ColorLensFragment.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                ColorLensFragment.SEARCH_INDEX_DATA_PROVIDER;
                        A11ySeekBarPreference a11ySeekBarPreference =
                                colorLensFragment.mSeekBarPref;
                        if (a11ySeekBarPreference != null) {
                            a11ySeekBarPreference.setValueInternal(
                                    Settings.Secure.getInt(
                                            colorLensFragment.getContentResolver(),
                                            "color_lens_opacity",
                                            2),
                                    true);
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.color.ColorLensFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text = context.getText(R.string.colour_lens_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityConstant.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }
    }

    public final List buildPreferenceControllers(Context context, Lifecycle lifecycle) {
        if (((ArrayList) this.controllers).size() == 0) {
            for (String str : context.getResources().getStringArray(R.array.color_lens_keys)) {
                ((ArrayList) this.controllers)
                        .add(new ColorLensRadioButtonPreferenceController(context, lifecycle, str));
            }
        }
        return this.controllers;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getExclusiveTaskName() {
        return "color_lens";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3100;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("description_preference");
        arrayList.add(AccessibilityConstant.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString());
        arrayList.add("seek_bar_preference");
        arrayList.add("use_service");
        arrayList.add("color_blue");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList, "color_azure", "color_cyan", "color_spring_green", "color_green");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList, "color_chartreuse_green", "color_yellow", "color_orange", "color_red");
        arrayList.add("color_rose");
        arrayList.add("color_magenta");
        arrayList.add("color_violet");
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_color_lens;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityConstant.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        A11ySeekBarPreference a11ySeekBarPreference =
                (A11ySeekBarPreference) findPreference("seek_bar_preference");
        this.mSeekBarPref = a11ySeekBarPreference;
        if (a11ySeekBarPreference != null) {
            a11ySeekBarPreference.setOnPreferenceChangeListener(this);
            A11ySeekBarPreference a11ySeekBarPreference2 = this.mSeekBarPref;
            String formatPercentage = Utils.formatPercentage(20);
            if (!TextUtils.equals(formatPercentage, a11ySeekBarPreference2.leftLabel)) {
                a11ySeekBarPreference2.leftLabel = formatPercentage;
                a11ySeekBarPreference2.notifyChanged();
            }
            A11ySeekBarPreference a11ySeekBarPreference3 = this.mSeekBarPref;
            String formatPercentage2 = Utils.formatPercentage(60);
            if (!TextUtils.equals(formatPercentage2, a11ySeekBarPreference3.rightLabel)) {
                a11ySeekBarPreference3.rightLabel = formatPercentage2;
                a11ySeekBarPreference3.notifyChanged();
            }
            A11ySeekBarPreference a11ySeekBarPreference4 = this.mSeekBarPref;
            if (a11ySeekBarPreference4 != null) {
                a11ySeekBarPreference4.setValueInternal(
                        Settings.Secure.getInt(getContentResolver(), "color_lens_opacity", 2),
                        true);
            }
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mComponentName = AccessibilityConstant.COMPONENT_NAME_COLOR_LENS_SHORTCUT;
        this.mPackageName = getText(R.string.colour_lens_title);
        this.mPackageNameResourceId = R.string.colour_lens_title;
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Iterator it =
                ((ArrayList) buildPreferenceControllers(getPrefContext(), getSettingsLifecycle()))
                        .iterator();
        while (it.hasNext()) {
            ((ColorLensRadioButtonPreferenceController) ((AbstractPreferenceController) it.next()))
                    .setOnChangeListener(null);
        }
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Secure.putInt(
                getContentResolver(), "color_lens_opacity", ((Integer) obj).intValue());
        A11ySeekBarPreference a11ySeekBarPreference = this.mSeekBarPref;
        if (a11ySeekBarPreference == null) {
            return true;
        }
        a11ySeekBarPreference.setStateDescription(
                Utils.formatPercentage(
                        (Settings.Secure.getInt(getContentResolver(), "color_lens_opacity", 2) * 5)
                                + 20));
        return true;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if ((Settings.Secure.getInt(getContentResolver(), "color_lens_switch", 0) == 1) == z) {
            return;
        }
        Settings.Secure.putInt(getContentResolver(), "color_lens_switch", z ? 1 : 0);
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
                ((ArrayList) buildPreferenceControllers(getPrefContext(), getSettingsLifecycle()))
                        .iterator();
        while (it.hasNext()) {
            ColorLensRadioButtonPreferenceController colorLensRadioButtonPreferenceController =
                    (ColorLensRadioButtonPreferenceController)
                            ((AbstractPreferenceController) it.next());
            colorLensRadioButtonPreferenceController.setOnChangeListener(this);
            colorLensRadioButtonPreferenceController.displayPreference(getPreferenceScreen());
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
                        Settings.Secure.getUriFor("color_lens_switch"),
                        false,
                        this.contentObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("color_lens_opacity"),
                        false,
                        this.contentObserver);
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
            peekDecorView.setAccessibilityPaneTitle(getString(R.string.colour_lens_title));
        }
        setDivider(null);
        Context context = view.getContext();
        getListView()
                .addItemDecoration(
                        new DividerItemDecorator(
                                context,
                                context.getResources()
                                        .getDimensionPixelSize(R.dimen.controller_item_area)));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean z = Settings.Secure.getInt(getContentResolver(), "color_lens_switch", 0) == 1;
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != z) {
            this.mToggleServiceSwitchBar.setCheckedInternal(z);
        }
    }
}
