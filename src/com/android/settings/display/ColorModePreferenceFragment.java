package com.android.settings.display;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.hardware.display.ColorDisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.LayoutPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ColorModePreferenceFragment extends RadioButtonPickerFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass3(R.xml.color_mode_settings);
    public ColorDisplayManager mColorDisplayManager;
    public AnonymousClass1 mContentObserver;
    public ImageView[] mDotIndicators;
    public ArrayList mPageList;
    public Resources mResources;
    public View mViewArrowNext;
    public View mViewArrowPrevious;
    public ViewPager mViewPager;
    public View[] mViewPagerImages;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.ColorModePreferenceFragment$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            int[] intArray =
                    context.getResources()
                            .getIntArray(android.R.array.config_notificationSignalExtractors);
            return (intArray == null
                            || intArray.length <= 0
                            || ColorDisplayManager.areAccessibilityTransformsEnabled(context))
                    ? false
                    : true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ColorModeCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;

        public ColorModeCandidateInfo(String str, CharSequence charSequence) {
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

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ColorPagerAdapter extends PagerAdapter {
        public final ArrayList mPageViewList;

        public ColorPagerAdapter(ArrayList arrayList) {
            this.mPageViewList = arrayList;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (this.mPageViewList.get(i) != null) {
                viewGroup.removeView((View) this.mPageViewList.get(i));
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return this.mPageViewList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            viewGroup.addView((View) this.mPageViewList.get(i));
            return this.mPageViewList.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return obj == view;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        LayoutPreference layoutPreference =
                new LayoutPreference(preferenceScreen.getContext(), R.layout.color_mode_preview);
        configureAndInstallPreview(layoutPreference, preferenceScreen);
        ArrayList<Integer> viewPagerResource = getViewPagerResource();
        this.mViewPager = (ViewPager) layoutPreference.mRootView.findViewById(R.id.viewpager);
        this.mViewPagerImages = new View[3];
        for (int i = 0; i < viewPagerResource.size(); i++) {
            this.mViewPagerImages[i] =
                    getLayoutInflater()
                            .inflate(viewPagerResource.get(i).intValue(), (ViewGroup) null);
        }
        ArrayList arrayList = new ArrayList();
        this.mPageList = arrayList;
        arrayList.add(this.mViewPagerImages[0]);
        this.mPageList.add(this.mViewPagerImages[1]);
        this.mPageList.add(this.mViewPagerImages[2]);
        this.mViewPager.setAdapter(new ColorPagerAdapter(this.mPageList));
        View findViewById = layoutPreference.mRootView.findViewById(R.id.arrow_previous);
        this.mViewArrowPrevious = findViewById;
        final int i2 = 0;
        findViewById.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.display.ColorModePreferenceFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ ColorModePreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i3 = i2;
                        ColorModePreferenceFragment colorModePreferenceFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                colorModePreferenceFragment.mViewPager.setCurrentItem(
                                        colorModePreferenceFragment.mViewPager.getCurrentItem() - 1,
                                        true);
                                break;
                            default:
                                colorModePreferenceFragment.mViewPager.setCurrentItem(
                                        colorModePreferenceFragment.mViewPager.getCurrentItem() + 1,
                                        true);
                                break;
                        }
                    }
                });
        View findViewById2 = layoutPreference.mRootView.findViewById(R.id.arrow_next);
        this.mViewArrowNext = findViewById2;
        final int i3 = 1;
        findViewById2.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.display.ColorModePreferenceFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ ColorModePreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i32 = i3;
                        ColorModePreferenceFragment colorModePreferenceFragment = this.f$0;
                        switch (i32) {
                            case 0:
                                colorModePreferenceFragment.mViewPager.setCurrentItem(
                                        colorModePreferenceFragment.mViewPager.getCurrentItem() - 1,
                                        true);
                                break;
                            default:
                                colorModePreferenceFragment.mViewPager.setCurrentItem(
                                        colorModePreferenceFragment.mViewPager.getCurrentItem() + 1,
                                        true);
                                break;
                        }
                    }
                });
        this.mViewPager.addOnPageChangeListener(
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.android.settings.display.ColorModePreferenceFragment.2
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i4, int i5, float f) {
                        ColorModePreferenceFragment colorModePreferenceFragment =
                                ColorModePreferenceFragment.this;
                        if (f == 0.0f) {
                            colorModePreferenceFragment.mViewPagerImages[i4].setContentDescription(
                                    colorModePreferenceFragment
                                            .getContext()
                                            .getString(
                                                    R.string.colors_viewpager_content_description));
                            colorModePreferenceFragment.updateIndicator(i4);
                        } else {
                            for (int i6 = 0;
                                    i6 < colorModePreferenceFragment.mPageList.size();
                                    i6++) {
                                colorModePreferenceFragment.mViewPagerImages[i6].setVisibility(0);
                            }
                        }
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i4) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i4) {}
                });
        ViewGroup viewGroup = (ViewGroup) layoutPreference.mRootView.findViewById(R.id.viewGroup);
        this.mDotIndicators = new ImageView[this.mPageList.size()];
        for (int i4 = 0; i4 < this.mPageList.size(); i4++) {
            ImageView imageView = new ImageView(getContext());
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    new ViewGroup.MarginLayoutParams(12, 12);
            marginLayoutParams.setMargins(6, 0, 6, 0);
            imageView.setLayoutParams(marginLayoutParams);
            this.mDotIndicators[i4] = imageView;
            viewGroup.addView(imageView);
        }
        updateIndicator(this.mViewPager.getCurrentItem());
    }

    public void configureAndInstallPreview(
            LayoutPreference layoutPreference, PreferenceScreen preferenceScreen) {
        layoutPreference.setSelectable(false);
        preferenceScreen.addPreference(layoutPreference);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        Map colorModeMapping = ColorModeUtils.getColorModeMapping(this.mResources);
        ArrayList arrayList = new ArrayList();
        for (int i :
                this.mResources.getIntArray(android.R.array.config_notificationSignalExtractors)) {
            arrayList.add(
                    new ColorModeCandidateInfo(
                            getKeyForColorMode(i),
                            (CharSequence) ((ArrayMap) colorModeMapping).get(Integer.valueOf(i))));
        }
        return arrayList;
    }

    public int getColorMode() {
        return this.mColorDisplayManager.getColorMode();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        int colorMode = getColorMode();
        return (colorMode == 0
                        || colorMode == 1
                        || colorMode == 2
                        || colorMode == 3
                        || (colorMode >= 256 && colorMode <= 511))
                ? getKeyForColorMode(colorMode)
                : getKeyForColorMode(0);
    }

    public String getKeyForColorMode(int i) {
        return SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "color_mode_");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1143;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.color_mode_settings;
    }

    public ArrayList<Integer> getViewPagerResource() {
        return new ArrayList<>(
                Arrays.asList(
                        Integer.valueOf(R.layout.color_mode_view1),
                        Integer.valueOf(R.layout.color_mode_view2),
                        Integer.valueOf(R.layout.color_mode_view3)));
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.display.ColorModePreferenceFragment$1] */
    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mColorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
        this.mResources = context.getResources();
        ContentResolver contentResolver = context.getContentResolver();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.display.ColorModePreferenceFragment.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        super.onChange(z, uri);
                        if (ColorDisplayManager.areAccessibilityTransformsEnabled(
                                ColorModePreferenceFragment.this.getContext())) {
                            ColorModePreferenceFragment.this.getActivity().finish();
                        }
                    }
                };
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_display_inversion_enabled"),
                false,
                this.mContentObserver,
                this.mUserId);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_display_daltonizer_enabled"),
                false,
                this.mContentObserver,
                this.mUserId);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            int i = bundle.getInt("page_viewer_selection_index");
            this.mViewPager.setCurrentItem(i);
            updateIndicator(i);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        if (this.mContentObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
        }
        super.onDetach();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("page_viewer_selection_index", this.mViewPager.getCurrentItem());
    }

    public void setColorMode(int i) {
        this.mColorDisplayManager.setColorMode(i);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        int parseInt = Integer.parseInt(str.substring(str.lastIndexOf("_") + 1));
        if (parseInt == 0
                || parseInt == 1
                || parseInt == 2
                || parseInt == 3
                || (parseInt >= 256 && parseInt <= 511)) {
            setColorMode(parseInt);
        }
        return true;
    }

    public final void updateIndicator(int i) {
        for (int i2 = 0; i2 < this.mPageList.size(); i2++) {
            if (i == i2) {
                this.mDotIndicators[i2].setBackgroundResource(
                        R.drawable.ic_color_page_indicator_focused);
                this.mViewPagerImages[i2].setVisibility(0);
            } else {
                this.mDotIndicators[i2].setBackgroundResource(
                        R.drawable.ic_color_page_indicator_unfocused);
                this.mViewPagerImages[i2].setVisibility(4);
            }
        }
        if (i == 0) {
            this.mViewArrowPrevious.setVisibility(4);
            this.mViewArrowNext.setVisibility(0);
        } else if (i == this.mPageList.size() - 1) {
            this.mViewArrowPrevious.setVisibility(0);
            this.mViewArrowNext.setVisibility(4);
        } else {
            this.mViewArrowPrevious.setVisibility(0);
            this.mViewArrowNext.setVisibility(0);
        }
    }
}
