package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;
import com.android.settings.core.SettingsBaseActivity;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.sec.ims.im.ImIntent;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VibPickerContainer extends SettingsBaseActivity {
    public Context mContext;
    public boolean mIsFromContact;
    public PagerAdapter mPagerAdapter;
    public final ArrayList mPickerList = new ArrayList();
    public final ArrayList mSelectedItemList = new ArrayList();
    public final AnonymousClass2 mSelectedListener =
            new TabLayout
                    .OnTabSelectedListener() { // from class:
                                               // com.samsung.android.settings.asbase.vibration.VibPickerContainer.2
                @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                public final void onTabSelected(TabLayout.Tab tab) {
                    VibPickerContainer vibPickerContainer = VibPickerContainer.this;
                    vibPickerContainer.mViewPager.setCurrentItem(tab.position);
                    ((VibPickerActivity)
                                    vibPickerContainer
                                            .getSupportFragmentManager()
                                            .mFragmentStore
                                            .getFragments()
                                            .get(tab.position))
                            .updateSelectedItemOtherSim();
                }

                @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                public final void onTabUnselected(TabLayout.Tab tab) {
                    int i = tab.position;
                    VibPickerContainer vibPickerContainer = VibPickerContainer.this;
                    VibPickerActivity vibPickerActivity =
                            (VibPickerActivity) vibPickerContainer.mPickerList.get(i);
                    Vibrator vibrator = vibPickerActivity.mVibrator;
                    if (vibrator != null) {
                        vibrator.cancel();
                    }
                    Ringtone ringtone = vibPickerActivity.mRingtone;
                    if (ringtone != null) {
                        ringtone.stop();
                    }
                    VibPickerActivity vibPickerActivity2 =
                            (VibPickerActivity) vibPickerContainer.mPickerList.get(i);
                    SecVibPickerIntensitySettings secVibPickerIntensitySettings =
                            vibPickerActivity2.mVibIntensitySeekBar;
                    if (secVibPickerIntensitySettings != null) {
                        secVibPickerIntensitySettings.onPause();
                        vibPickerActivity2.mVibIntensitySeekBar.onResume();
                    }
                }
            };
    public int mSelectedTab;
    public TabLayout mTabLayout;
    public int mType;
    public ViewPager mViewPager;

    public static VibPickerActivity getVibPickerActivity(int i) {
        VibPickerActivity vibPickerActivity = new VibPickerActivity();
        Bundle bundle = new Bundle();
        bundle.putInt("sim_slot", i);
        vibPickerActivity.setArguments(bundle);
        return vibPickerActivity;
    }

    public final boolean isMultiSimEnabled() {
        return (!SimUtils.isMultiSimEnabled(this.mContext)
                        || this.mIsFromContact
                        || this.mType == 1)
                ? false
                : true;
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sec_vib_picker_container);
        this.mContext = getApplicationContext();
        this.mType = getIntent().getIntExtra("vibration_type", 0);
        this.mIsFromContact =
                getIntent().getBooleanExtra("android.intent.extra.pattern.FROM_CONTACT", false);
        "call_setting_ringtones".equals(getIntent().getStringExtra(ImIntent.Extras.EXTRA_FROM));
        this.mSelectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);
        if (!this.mSelectedItemList.isEmpty()) {
            this.mSelectedItemList.clear();
        }
        if (isMultiSimEnabled()) {
            VibPickerItem vibPickerItem =
                    new VibPickerItem(
                            0,
                            0,
                            "Custom",
                            this.mType,
                            1,
                            ApnSettings.MVNO_NONE,
                            "Custom",
                            0,
                            null,
                            0);
            this.mSelectedItemList.add(vibPickerItem);
            this.mSelectedItemList.add(vibPickerItem);
        }
        if (isMultiSimEnabled()) {
            this.mPickerList.add(getVibPickerActivity(0));
            this.mPickerList.add(getVibPickerActivity(1));
        } else if (!SimUtils.isEnabledSIM2Only() || this.mIsFromContact || this.mType == 1) {
            this.mPickerList.add(getVibPickerActivity(0));
        } else {
            this.mPickerList.add(getVibPickerActivity(1));
        }
        int i =
                this.mType == 0
                        ? R.string.sec_vibration_call_title
                        : R.string.sec_vibration_notification_title;
        setTitle(i);
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
        }
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_app_bar);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(getString(i));
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.vib_tab_container);
        this.mTabLayout = tabLayout;
        tabLayout.seslSetSubTabStyle();
        ViewPager viewPager = (ViewPager) findViewById(R.id.vib_picker_container);
        this.mViewPager = viewPager;
        viewPager.semSetRoundedCorners(15);
        this.mViewPager.semSetRoundedCornerColor(
                15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
        this.mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (isMultiSimEnabled()) {
            for (int i2 = 0; i2 < 2; i2++) {
                TabLayout tabLayout2 = this.mTabLayout;
                TabLayout.Tab newTab = tabLayout2.newTab();
                newTab.setText(SimUtils.getSimName(this.mContext, i2));
                tabLayout2.addTab(newTab, tabLayout2.tabs.isEmpty());
                ((ArrayList) this.mPagerAdapter.mFragmentList)
                        .add((Fragment) this.mPickerList.get(i2));
            }
            this.mViewPager.addOnPageChangeListener(
                    new TabLayout.TabLayoutOnPageChangeListener(this.mTabLayout));
            this.mTabLayout.addOnTabSelectedListener$1(this.mSelectedListener);
            if (this.mSelectedTab != 0) {
                new Handler(Looper.getMainLooper())
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.asbase.vibration.VibPickerContainer.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        VibPickerContainer vibPickerContainer =
                                                VibPickerContainer.this;
                                        TabLayout tabLayout3 = vibPickerContainer.mTabLayout;
                                        tabLayout3.selectTab(
                                                tabLayout3.getTabAt(
                                                        vibPickerContainer.mSelectedTab),
                                                true);
                                    }
                                },
                                20L);
            }
        } else {
            this.mTabLayout.setVisibility(8);
            ((ArrayList) this.mPagerAdapter.mFragmentList).add((Fragment) this.mPickerList.get(0));
        }
        this.mViewPager.setAdapter(this.mPagerAdapter);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332
                || !"bixby".equals(getIntent().getStringExtra(ImIntent.Extras.EXTRA_FROM))) {
            return super.onOptionsItemSelected(menuItem);
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$SoundSettingsActivity");
        startActivity(intent);
        finish();
        return true;
    }
}
