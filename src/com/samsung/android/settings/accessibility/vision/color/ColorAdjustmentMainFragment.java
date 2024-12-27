package com.samsung.android.settings.accessibility.vision.color;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.viewpager.widget.ViewPager;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.accessibility.ToggleFeaturePreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.base.widget.A11ySeekBarPreference;
import com.samsung.android.settings.accessibility.base.widget.DividerItemDecorator;
import com.samsung.android.settings.accessibility.vision.controllers.ColorAdjustmentRadioButtonPreferenceController;
import com.samsung.android.settings.widget.SecWrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ColorAdjustmentMainFragment extends ToggleFeaturePreferenceFragment
        implements Preference.OnPreferenceChangeListener,
                ColorAdjustmentRadioButtonPreferenceController.OnChangeListener {
    public ColorPreviewAdapter colorPreviewAdapter;
    public ColorAdjustmentResetRadioPreference mPersonalizedPref;
    public LinearLayout mPointArea;
    public LinearLayout mPreviewLayout;
    public A11ySeekBarPreference mSeekBarPref;
    public SecWrapContentHeightViewPager viewPager;
    public static final List sControllers = new ArrayList();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass4(R.xml.accessibility_settings_color_adjustment);
    public final AnonymousClass1 contentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    ColorAdjustmentMainFragment.this.updateSwitchBarToggleSwitch();
                }
            };
    public final AnonymousClass5 mResetButtonClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment.5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context context = ColorAdjustmentMainFragment.this.getContext();
                    if (ColorAdjustmentMainFragment.this.mPersonalizedPref == null
                            || context == null) {
                        return;
                    }
                    AlertDialog create =
                            new AlertDialog.Builder(context)
                                    .setTitle(R.string.start_personalize_color_dialog_title)
                                    .setMessage(R.string.start_personalize_color_dialog_message)
                                    .setPositiveButton(
                                            R.string.start_personalize_color_dialog_button,
                                            new DialogInterface
                                                    .OnClickListener() { // from class:
                                                                         // com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment.5.2
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i) {
                                                    ColorAdjustmentResetRadioPreference
                                                            colorAdjustmentResetRadioPreference =
                                                                    ColorAdjustmentMainFragment.this
                                                                            .mPersonalizedPref;
                                                    if (colorAdjustmentResetRadioPreference
                                                                    .mResetBtnClickListener
                                                            != null) {
                                                        colorAdjustmentResetRadioPreference
                                                                        .mResetBtnClickListener =
                                                                null;
                                                        colorAdjustmentResetRadioPreference
                                                                .notifyChanged();
                                                    }
                                                    SubSettingLauncher subSettingLauncher =
                                                            new SubSettingLauncher(
                                                                    ColorAdjustmentMainFragment.this
                                                                            .getContext());
                                                    String name =
                                                            ColorAdjustmentTestFragment.class
                                                                    .getName();
                                                    SubSettingLauncher.LaunchRequest launchRequest =
                                                            subSettingLauncher.mLaunchRequest;
                                                    launchRequest.mDestinationName = name;
                                                    subSettingLauncher.setTitleRes(
                                                            R.string.personalized_color, null);
                                                    launchRequest.mSourceMetricsCategory = 3080;
                                                    subSettingLauncher.launch();
                                                }
                                            })
                                    .setNegativeButton(
                                            android.R.string.cancel, new AnonymousClass1())
                                    .create();
                    ColorAdjustmentResetRadioPreference colorAdjustmentResetRadioPreference =
                            ColorAdjustmentMainFragment.this.mPersonalizedPref;
                    HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
                    Rect rect = new Rect();
                    colorAdjustmentResetRadioPreference.seslGetPreferenceBounds(rect);
                    create.semSetAnchor((rect.left + rect.right) / 2, rect.bottom);
                    View rootView = create.findViewById(android.R.id.content).getRootView();
                    if (rootView != null) {
                        rootView.addOnLayoutChangeListener(
                                new SecAccessibilityUtils$$ExternalSyntheticLambda0(
                                        create, colorAdjustmentResetRadioPreference));
                    }
                    create.show();
                }

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                /* renamed from: com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment$5$1, reason: invalid class name */
                public final class AnonymousClass1 implements DialogInterface.OnClickListener {
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {}
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!SecAccessibilityUtils.isSupportColorAdjustment(context)) {
                return null;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            CharSequence text = context.getText(R.string.color_adjustment_title);
            ((SearchIndexableData) searchIndexableRaw).key =
                    AccessibilityConstant.COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT
                            .flattenToString();
            searchIndexableRaw.title =
                    context.getString(R.string.accessibility_shortcut_title, text);
            searchIndexableRaw.screenTitle = text.toString();
            return List.of(searchIndexableRaw);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SecAccessibilityUtils.isSupportColorAdjustment(context);
        }
    }

    public static List buildPreferenceControllers(Context context, Lifecycle lifecycle) {
        if (((ArrayList) sControllers).size() == 0) {
            for (String str : context.getResources().getStringArray(R.array.color_adjust_keys)) {
                ((ArrayList) sControllers)
                        .add(
                                new ColorAdjustmentRadioButtonPreferenceController(
                                        context, lifecycle, str));
            }
        }
        return sControllers;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getExclusiveTaskName() {
        return "color_blind";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3080;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("top_intro");
        arrayList.add("description_preference");
        arrayList.add(
                AccessibilityConstant.COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT.flattenToString());
        arrayList.add("empty_category_1");
        arrayList.add("seek_bar_preference");
        arrayList.add("empty_category_2");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "use_service",
                "color_adjustment_gray_scale",
                "color_adjustment_protan",
                "color_adjustment_deutan");
        arrayList.add("color_adjustment_tritan");
        arrayList.add("color_adjustment_personalized");
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_color_adjustment;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityConstant.COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT.flattenToString();
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
        ColorAdjustmentResetRadioPreference colorAdjustmentResetRadioPreference;
        AnonymousClass5 anonymousClass5;
        this.mComponentName = AccessibilityConstant.COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT;
        this.mPackageName = getText(R.string.color_adjustment_title);
        this.mPackageNameResourceId = R.string.color_adjustment_title;
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        A11ySeekBarPreference a11ySeekBarPreference =
                (A11ySeekBarPreference) findPreference("seek_bar_preference");
        this.mSeekBarPref = a11ySeekBarPreference;
        if (a11ySeekBarPreference != null) {
            a11ySeekBarPreference.setOnPreferenceChangeListener(this);
            A11ySeekBarPreference a11ySeekBarPreference2 = this.mSeekBarPref;
            CharSequence text = getText(R.string.intensity_seekbar_weak_label);
            if (!TextUtils.equals(text, a11ySeekBarPreference2.leftLabel)) {
                a11ySeekBarPreference2.leftLabel = text;
                a11ySeekBarPreference2.notifyChanged();
            }
            A11ySeekBarPreference a11ySeekBarPreference3 = this.mSeekBarPref;
            CharSequence text2 = getText(R.string.intensity_seekbar_strong_label);
            if (!TextUtils.equals(text2, a11ySeekBarPreference3.rightLabel)) {
                a11ySeekBarPreference3.rightLabel = text2;
                a11ySeekBarPreference3.notifyChanged();
            }
        }
        this.mPersonalizedPref =
                (ColorAdjustmentResetRadioPreference)
                        findPreference("color_adjustment_personalized");
        if (Settings.System.getInt(getContext().getContentResolver(), "color_blind_test", 0) != 0
                && (colorAdjustmentResetRadioPreference = this.mPersonalizedPref) != null
                && colorAdjustmentResetRadioPreference.mResetBtnClickListener
                        != (anonymousClass5 = this.mResetButtonClickListener)) {
            colorAdjustmentResetRadioPreference.mResetBtnClickListener = anonymousClass5;
            colorAdjustmentResetRadioPreference.notifyChanged();
        }
        this.colorPreviewAdapter = new ColorPreviewAdapter(getContext());
        SecWrapContentHeightViewPager secWrapContentHeightViewPager =
                (SecWrapContentHeightViewPager) onCreateView.findViewById(R.id.pager);
        this.viewPager = secWrapContentHeightViewPager;
        secWrapContentHeightViewPager.setAdapter(this.colorPreviewAdapter);
        this.viewPager.setOffscreenPageLimit(this.colorPreviewAdapter.mDescriptions.size());
        this.viewPager.setOnPageChangeListener(
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment.2
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i) {
                        int i2 = 0;
                        while (true) {
                            ColorAdjustmentMainFragment colorAdjustmentMainFragment =
                                    ColorAdjustmentMainFragment.this;
                            if (i2 >= colorAdjustmentMainFragment.mPointArea.getChildCount()) {
                                ((ImageView) colorAdjustmentMainFragment.mPointArea.getChildAt(i))
                                        .setImageResource(
                                                R.drawable.sec_screen_mode_page_indicator_selected);
                                return;
                            } else {
                                ((ImageView) colorAdjustmentMainFragment.mPointArea.getChildAt(i2))
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
        LinearLayout linearLayout = (LinearLayout) onCreateView.findViewById(R.id.image_area);
        this.mPreviewLayout = linearLayout;
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            this.mPreviewLayout.semSetRoundedCornerColor(
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
                                                 // com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment.3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ColorAdjustmentMainFragment.this.viewPager.setCurrentItem(i);
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
            ((ColorAdjustmentRadioButtonPreferenceController)
                            ((AbstractPreferenceController) it.next()))
                    .setOnChangeListener(null);
        }
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Integer num = (Integer) obj;
        int intValue = num.intValue();
        int i = Settings.Secure.getInt(getContentResolver(), "color_adjustment_type", 2);
        Context context = getContext();
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        if (i < 1 || i > 4) {
            Log.w("A11yUtils", "setColorAdjustmentIntensity - wrong type entered.");
        } else {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "setColorAdjustmentIntensity - type: ",
                    " / intensity: ",
                    i,
                    intValue,
                    "A11yUtils");
            if (i == 4) {
                Settings.Secure.putFloat(
                        context.getContentResolver(),
                        "color_blind_user_parameter",
                        intValue / 10.0f);
            } else {
                String string =
                        Settings.Secure.getString(
                                context.getContentResolver(), "predefined_color_blind_intensity");
                if (TextUtils.isEmpty(string)) {
                    string = "00,00,00";
                }
                String format = String.format(Locale.US, "%02d", num);
                String[] split = string.split(",");
                split[i - 1] = format;
                Settings.Secure.putString(
                        context.getContentResolver(),
                        "predefined_color_blind_intensity",
                        TextUtils.join(",", split));
            }
        }
        SecAccessibilityUtils.setColorAdjustment(getContext());
        return true;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if ((Settings.System.getInt(getContentResolver(), "color_blind", 0) == 1) == z) {
            return;
        }
        Settings.System.putInt(getContentResolver(), "color_blind", z ? 1 : 0);
        SecAccessibilityUtils.setColorAdjustment(getContext());
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        ColorAdjustmentResetRadioPreference colorAdjustmentResetRadioPreference;
        AnonymousClass5 anonymousClass5;
        super.onResume();
        updateSwitchBarToggleSwitch();
        if (Settings.System.getInt(getContext().getContentResolver(), "color_blind_test", 0) != 0
                && (colorAdjustmentResetRadioPreference = this.mPersonalizedPref) != null
                && colorAdjustmentResetRadioPreference.mResetBtnClickListener
                        != (anonymousClass5 = this.mResetButtonClickListener)) {
            colorAdjustmentResetRadioPreference.mResetBtnClickListener = anonymousClass5;
            colorAdjustmentResetRadioPreference.notifyChanged();
        }
        updateSeekBarPreference();
        Iterator it =
                ((ArrayList) buildPreferenceControllers(getPrefContext(), getSettingsLifecycle()))
                        .iterator();
        while (it.hasNext()) {
            ColorAdjustmentRadioButtonPreferenceController
                    colorAdjustmentRadioButtonPreferenceController =
                            (ColorAdjustmentRadioButtonPreferenceController)
                                    ((AbstractPreferenceController) it.next());
            colorAdjustmentRadioButtonPreferenceController.setOnChangeListener(this);
            colorAdjustmentRadioButtonPreferenceController.displayPreference(getPreferenceScreen());
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
                        Settings.System.getUriFor("color_blind"), false, this.contentObserver);
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
            peekDecorView.setAccessibilityPaneTitle(getString(R.string.color_adjustment_title));
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

    public final void updateSeekBarPreference() {
        int i = Settings.Secure.getInt(getContentResolver(), "color_adjustment_type", 2);
        A11ySeekBarPreference a11ySeekBarPreference = this.mSeekBarPref;
        if (a11ySeekBarPreference == null) {
            return;
        }
        if (i == 0) {
            a11ySeekBarPreference.setEnabled(false);
            return;
        }
        a11ySeekBarPreference.setEnabled(true);
        this.mSeekBarPref.setMax(i == 4 ? 10 : 24);
        this.mSeekBarPref.setValueInternal(
                SecAccessibilityUtils.getColorAdjustmentIntensity(getContext(), i), true);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean z = Settings.System.getInt(getContentResolver(), "color_blind", 0) == 1;
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != z) {
            this.mToggleServiceSwitchBar.setCheckedInternal(z);
        }
    }
}
