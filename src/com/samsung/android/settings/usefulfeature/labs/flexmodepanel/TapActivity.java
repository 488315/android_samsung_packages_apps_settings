package com.samsung.android.settings.usefulfeature.labs.flexmodepanel;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;
import com.android.settings.Utils;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TapActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SparseArray mItemList;
    public LinearLayout mNextButtonLayout;
    public TextView mPageText;
    public LinearLayout mPreviousButtonLayout;
    public ViewPager mTipsViewPager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingPage {
        public final String mContext;
        public final String mJsonRes;
        public final String mTitle;

        public SettingPage(String str, String str2, String str3) {
            this.mJsonRes = str;
            this.mTitle = str2;
            this.mContext = str3;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TouchPadTipsPagerAdapter extends PagerAdapter {
        public final Context mContext;
        public final SparseArray mItemList;

        public TouchPadTipsPagerAdapter(SparseArray sparseArray, Context context) {
            this.mItemList = sparseArray;
            this.mContext = context;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return this.mItemList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getItemPosition() {
            return -2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.layout.sec_touchpad_tips, viewGroup, false);
            Context context = this.mContext;
            int i2 = TapActivity.$r8$clinit;
            if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
                inflate.setScaleX(-1.0f);
            }
            TextView textView = (TextView) inflate.findViewById(R.id.tips_feature_title);
            TextView textView2 = (TextView) inflate.findViewById(R.id.tips_feature_context);
            LottieAnimationView lottieAnimationView =
                    (LottieAnimationView) inflate.findViewById(R.id.description_lottie_animation);
            View findViewById = inflate.findViewById(R.id.lottie_animation_container);
            SettingPage settingPage = (SettingPage) this.mItemList.valueAt(i);
            textView.setText(settingPage.mTitle);
            textView2.setText(settingPage.mContext);
            lottieAnimationView.setAnimation(settingPage.mJsonRes);
            int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
            ((ScrollView) inflate.findViewById(R.id.scroll_view_root_layout))
                    .setPaddingRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
            viewGroup.addView(inflate);
            return inflate;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Bundle extras;
        final int i = 1;
        super.onCreate(bundle);
        setContentView(R.layout.sec_touchpad_gesture_tap);
        final int i2 = 0;
        int i3 =
                (getIntent() == null || (extras = getIntent().getExtras()) == null)
                        ? 0
                        : extras.getInt("page_position", 0);
        if (this.mItemList == null) {
            this.mItemList = new SparseArray();
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mItemList.put(
                0,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_tap_dark_flip.json"
                                : "sec_tap_light_flip.json",
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_tap),
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_tap_summary)));
        this.mItemList.put(
                1,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_tap_with_two_finger_dark_flip.json"
                                : "sec_tap_with_two_finger_light_flip.json",
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_tap_two_finger),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_two_finger_summary)));
        this.mItemList.put(
                2,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_swipe_with_two_finger_dark_flip.json"
                                : "sec_swipe_with_two_finger_light_flip.json",
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_swipe_with_two_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_swipe_with_two_fingers_summary)));
        this.mItemList.put(
                3,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_pinch_with_two_finger_dark_flip.json"
                                : "sec_pinch_with_two_finger_light_flip.json",
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_pinch_with_two_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_pinch_with_two_fingers_summary)));
        this.mItemList.put(
                4,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_touch_hold_dark_flip.json"
                                : "sec_touch_hold_light_flip.json",
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_touch_and_hold),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_touch_and_hold_summary)));
        this.mItemList.put(
                5,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_touch_hold_drag_dark_flip.json"
                                : "sec_touch_hold_drag_light_flip.json",
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_touch_hold_drag),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_touch_hold_drag_summary)));
        this.mItemList.put(
                6,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_tap_with_three_finger_dark_flip.json"
                                : "sec_tap_with_three_finger_light_flip.json",
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_three_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_three_fingers_summary)));
        this.mItemList.put(
                7,
                new SettingPage(
                        Utils.isNightMode(this)
                                ? "sec_tap_with_four_finger_dark_flip.json"
                                : "sec_tap_with_four_finger_light_flip.json",
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_four_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_four_fingers_summary)));
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
            SettingPage settingPage = (SettingPage) this.mItemList.get(i3);
            if (settingPage != null) {
                supportActionBar.setTitle(settingPage.mTitle);
            }
        }
        this.mTipsViewPager = (ViewPager) findViewById(R.id.tips_view_pager);
        if (getResources().getConfiguration().getLayoutDirection() == 1) {
            this.mTipsViewPager.setScaleX(-1.0f);
        }
        this.mPageText = (TextView) findViewById(R.id.page_count);
        this.mPreviousButtonLayout = (LinearLayout) findViewById(R.id.previous_page_button_layout);
        this.mNextButtonLayout = (LinearLayout) findViewById(R.id.next_page_button_layout);
        this.mPageText.setText(
                String.format(
                        Locale.getDefault(),
                        "%d/%d",
                        Integer.valueOf(this.mTipsViewPager.getCurrentItem() + 1),
                        Integer.valueOf(this.mItemList.size())));
        this.mTipsViewPager.setAdapter(new TouchPadTipsPagerAdapter(this.mItemList, this));
        this.mTipsViewPager.addOnPageChangeListener(
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.TapActivity.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i4, int i5, float f) {
                        TapActivity tapActivity = TapActivity.this;
                        int i6 = i4 + 1;
                        tapActivity.mPageText.setText(
                                String.format(
                                        Locale.getDefault(),
                                        "%d/%d",
                                        Integer.valueOf(i6),
                                        Integer.valueOf(tapActivity.mItemList.size())));
                        SettingPage settingPage2 = (SettingPage) tapActivity.mItemList.get(i4);
                        if (settingPage2 != null) {
                            supportActionBar.setTitle(settingPage2.mTitle);
                        }
                        if (i4 == 0) {
                            tapActivity.mPreviousButtonLayout.setVisibility(4);
                        } else if (i6 == tapActivity.mItemList.size()) {
                            tapActivity.mNextButtonLayout.setVisibility(4);
                        } else {
                            tapActivity.mPreviousButtonLayout.setVisibility(0);
                            tapActivity.mNextButtonLayout.setVisibility(0);
                        }
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i4) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i4) {}
                });
        ((ImageView) findViewById(R.id.previous_page_button)).getDrawable().setAutoMirrored(true);
        this.mPreviousButtonLayout.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.TapActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ TapActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i4 = i2;
                        TapActivity tapActivity = this.f$0;
                        switch (i4) {
                            case 0:
                                ViewPager viewPager = tapActivity.mTipsViewPager;
                                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                                LoggingHelper.insertEventLogging(68528, 68530);
                                break;
                            default:
                                ViewPager viewPager2 = tapActivity.mTipsViewPager;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
                                LoggingHelper.insertEventLogging(68528, 68531);
                                break;
                        }
                    }
                });
        ((ImageView) findViewById(R.id.next_page_button)).getDrawable().setAutoMirrored(true);
        this.mNextButtonLayout.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.TapActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ TapActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i4 = i;
                        TapActivity tapActivity = this.f$0;
                        switch (i4) {
                            case 0:
                                ViewPager viewPager = tapActivity.mTipsViewPager;
                                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                                LoggingHelper.insertEventLogging(68528, 68530);
                                break;
                            default:
                                ViewPager viewPager2 = tapActivity.mTipsViewPager;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
                                LoggingHelper.insertEventLogging(68528, 68531);
                                break;
                        }
                    }
                });
        this.mTipsViewPager.setCurrentItem(i3);
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        LoggingHelper.insertEventLogging(68528, (String) null);
        super.onResume();
    }
}
