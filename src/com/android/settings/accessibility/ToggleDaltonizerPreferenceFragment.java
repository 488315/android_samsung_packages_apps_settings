package com.android.settings.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.vision.color.ColorPreviewAdapter;
import com.samsung.android.settings.widget.SecWrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleDaltonizerPreferenceFragment extends ToggleFeaturePreferenceFragment
        implements DaltonizerRadioButtonPreferenceController.OnChangeListener {
    static final String KEY_DEUTERANOMALY = "daltonizer_mode_deuteranomaly";
    static final String KEY_GRAYSCALE = "daltonizer_mode_grayscale";
    static final String KEY_PROTANOMALY = "daltonizer_mode_protanomaly";
    static final String KEY_SATURATION = "daltonizer_saturation";
    static final String KEY_TRITANOMEALY = "daltonizer_mode_tritanomaly";
    public ColorPreviewAdapter colorPreviewAdapter;
    public LinearLayout mImageArea;
    public LinearLayout mPointArea;
    public SecWrapContentHeightViewPager viewPager;
    public static final List sControllers = new ArrayList();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass3(R.xml.accessibility_daltonizer_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accessibility.ToggleDaltonizerPreferenceFragment$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!SecAccessibilityUtils.isSupportColorCorrection(context)) {
                return null;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text =
                    context.getText(R.string.accessibility_display_daltonizer_preference_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            ((SearchIndexableData) searchIndexableRaw).className =
                    ToggleDaltonizerPreferenceFragment.class.getName();
            return List.of(searchIndexableRaw);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SecAccessibilityUtils.isSupportColorCorrection(context);
        }
    }

    public static List buildPreferenceControllers(Context context, Lifecycle lifecycle) {
        if (((ArrayList) sControllers).size() == 0) {
            for (String str : context.getResources().getStringArray(R.array.daltonizer_mode_keys)) {
                ((ArrayList) sControllers)
                        .add(
                                new DaltonizerRadioButtonPreferenceController(
                                        context, lifecycle, str));
            }
        }
        return sControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ToggleDaltonizerPreferenceFragment";
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3070;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("top_intro");
        arrayList.add("description_preference");
        arrayList.remove("daltonizer_preview");
        arrayList.add("empty_category");
        arrayList.add("use_service");
        arrayList.add(KEY_SATURATION);
        arrayList.add(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString());
        arrayList.add("sec_saturation_category");
        arrayList.add("sec_daltonizer_saturation");
        arrayList.add("empty_category");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList, KEY_DEUTERANOMALY, KEY_PROTANOMALY, KEY_TRITANOMEALY, KEY_GRAYSCALE);
        arrayList.remove("general_categories");
        arrayList.remove("html_description");
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_daltonizer_settings;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final CharSequence getShortcutTitle() {
        return getString(
                R.string.accessibility_shortcut_title,
                getText(R.string.accessibility_display_daltonizer_preference_title));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            if (Utils.isTablet()) {
                HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
                if (ActivityEmbeddingController.getInstance(activity)
                        .isActivityEmbedded(activity)) {
                    activity.setTheme(2132084370);
                }
            }
            activity.setTheme(2132084372);
        }
        super.onAttach(context);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mComponentName = AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME;
        this.mPackageName = getText(R.string.accessibility_display_daltonizer_preference_title);
        this.mPackageNameResourceId = R.string.accessibility_display_daltonizer_preference_title;
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.colorPreviewAdapter = new ColorPreviewAdapter(getContext());
        SecWrapContentHeightViewPager secWrapContentHeightViewPager =
                (SecWrapContentHeightViewPager) onCreateView.findViewById(R.id.pager);
        this.viewPager = secWrapContentHeightViewPager;
        secWrapContentHeightViewPager.setAdapter(this.colorPreviewAdapter);
        this.viewPager.setOffscreenPageLimit(this.colorPreviewAdapter.mDescriptions.size());
        this.viewPager.setOnPageChangeListener(
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.android.settings.accessibility.ToggleDaltonizerPreferenceFragment.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i) {
                        int i2 = 0;
                        while (true) {
                            ToggleDaltonizerPreferenceFragment toggleDaltonizerPreferenceFragment =
                                    ToggleDaltonizerPreferenceFragment.this;
                            if (i2
                                    >= toggleDaltonizerPreferenceFragment.mPointArea
                                            .getChildCount()) {
                                ((ImageView)
                                                toggleDaltonizerPreferenceFragment.mPointArea
                                                        .getChildAt(i))
                                        .setImageResource(
                                                R.drawable.sec_screen_mode_page_indicator_selected);
                                return;
                            } else {
                                ((ImageView)
                                                toggleDaltonizerPreferenceFragment.mPointArea
                                                        .getChildAt(i2))
                                        .setImageResource(
                                                R.drawable
                                                        .sec_screen_mode_page_indicator_unselected);
                                i2++;
                            }
                        }
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i, int i2, float f) {}
                });
        RelativeLayout relativeLayout =
                (RelativeLayout) onCreateView.findViewById(R.id.preview_layout);
        if (relativeLayout != null) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
            int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
            layoutParams.setMarginStart(listHorizontalPadding);
            layoutParams.setMarginEnd(listHorizontalPadding);
            relativeLayout.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout = (LinearLayout) onCreateView.findViewById(R.id.image_area);
        this.mImageArea = linearLayout;
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            this.mImageArea.semSetRoundedCornerColor(
                    15, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mPointArea = (LinearLayout) onCreateView.findViewById(R.id.point_area);
        int size = this.colorPreviewAdapter.mDescriptions.size();
        final int i = 0;
        while (i < size) {
            ImageView imageView =
                    (ImageView)
                            layoutInflater.inflate(
                                    R.layout.accessibility_pager_circle, (ViewGroup) null);
            int i2 = i + 1;
            imageView.setContentDescription(
                    String.format(
                            getContext()
                                    .getResources()
                                    .getString(R.string.page_description_template),
                            Integer.valueOf(i2),
                            Integer.valueOf(size)));
            imageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.accessibility.ToggleDaltonizerPreferenceFragment.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ToggleDaltonizerPreferenceFragment.this.viewPager.setCurrentItem(i);
                        }
                    });
            this.mPointArea.addView(imageView);
            i = i2;
        }
        if (this.mPointArea.getChildCount() > 0) {
            ((ImageView) this.mPointArea.getChildAt(0))
                    .setImageResource(R.drawable.sec_screen_mode_page_indicator_selected);
        }
        if (this.mPointArea.getChildCount() == 1) {
            this.mPointArea.setVisibility(8);
        }
        if (Utils.isRTL(getContext())) {
            this.viewPager.setCurrentItem(size);
        }
        return onCreateView;
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
            ((DaltonizerRadioButtonPreferenceController) ((AbstractPreferenceController) it.next()))
                    .setOnChangeListener(null);
        }
        super.onPause();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if (z
                == (Settings.Secure.getInt(
                                getContentResolver(), "accessibility_display_daltonizer_enabled", 0)
                        == 1)) {
            return;
        }
        AccessibilityStatsLogUtils.logAccessibilityServiceEnabled(this.mComponentName, z);
        Settings.Secure.putInt(
                getContentResolver(), "accessibility_display_daltonizer_enabled", z ? 1 : 0);
        this.mToggleServiceSwitchPreference.setTitle(
                z ? R.string.switch_on_text : R.string.switch_off_text);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onRemoveSwitchPreferenceToggleSwitch() {
        this.mToggleServiceSwitchBar.removeOnSwitchChangeListener(this);
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
            DaltonizerRadioButtonPreferenceController daltonizerRadioButtonPreferenceController =
                    (DaltonizerRadioButtonPreferenceController)
                            ((AbstractPreferenceController) it.next());
            daltonizerRadioButtonPreferenceController.setOnChangeListener(this);
            daltonizerRadioButtonPreferenceController.displayPreference(getPreferenceScreen());
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View peekDecorView = getActivity().getWindow().peekDecorView();
        if (peekDecorView != null) {
            peekDecorView.setAccessibilityPaneTitle(
                    getString(R.string.accessibility_display_daltonizer_preference_title));
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void registerKeysToObserverCallback(
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add("accessibility_display_daltonizer_enabled");
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                arrayList,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.ToggleDaltonizerPreferenceFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        List list = ToggleDaltonizerPreferenceFragment.sControllers;
                        ToggleDaltonizerPreferenceFragment.this.updateSwitchBarToggleSwitch();
                    }
                });
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean z =
                Settings.Secure.getInt(
                                getContentResolver(), "accessibility_display_daltonizer_enabled", 0)
                        == 1;
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != z) {
            this.mToggleServiceSwitchBar.setChecked(z);
        }
    }
}
