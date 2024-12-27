package com.samsung.android.settings.display;

import android.content.Context;
import android.content.pm.SemUserInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.display.Adapters.CameraCutoutViewPagerAdapter;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CameraCutoutFragmentUserSelect extends DashboardFragment {
    public static int mTabCount = 2;
    public CameraCutoutViewPagerAdapter mAdapter;
    public ViewGroup mContentView;
    public FragmentActivity mContext;
    public TabLayout mTabs;
    public UserManager mUserManager;
    public ArrayList mUserInfoList = new ArrayList();
    public int mSelectedTab = -1;

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "CameraCutoutFragmentUserSelect";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.placeholder_preference_screen;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return context.getString(R.string.menu_key_display);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        activity.getApplicationContext().getPackageManager();
        this.mUserManager = UserManager.get(this.mContext);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        FragmentActivity activity = getActivity();
        if (bundle == null || !bundle.containsKey("key_selected_tab")) {
            this.mSelectedTab = -1;
        } else {
            this.mSelectedTab = bundle.getInt("key_selected_tab");
        }
        View findViewById = this.mContentView.findViewById(R.id.tab_container);
        final ViewPager2 viewPager2 = (ViewPager2) findViewById.findViewById(R.id.view_pager);
        CameraCutoutViewPagerAdapter cameraCutoutViewPagerAdapter =
                new CameraCutoutViewPagerAdapter(this);
        cameraCutoutViewPagerAdapter.mContext = getContext();
        getActivity().getPackageManager();
        this.mUserInfoList = (ArrayList) this.mUserManager.semGetUsers();
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mUserInfoList.iterator();
        while (it.hasNext()) {
            Parcelable parcelable = (SemUserInfo) it.next();
            Log.d(
                    "CameraCutoutFragmentUserSelect",
                    "user: "
                            + parcelable.getUserHandle().getIdentifier()
                            + " "
                            + ((SemUserInfo) parcelable).name);
            int identifier = parcelable.getUserHandle().getIdentifier();
            if (identifier == 0 || (identifier >= 10 && identifier <= 42)) {
                CameraCutoutFragment cameraCutoutFragment = new CameraCutoutFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("userInfo", parcelable);
                cameraCutoutFragment.setArguments(bundle2);
                arrayList.add(cameraCutoutFragment);
            }
            if (arrayList.size() == 2) {
                break;
            }
        }
        int size = arrayList.size();
        Fragment[] fragmentArr = new Fragment[size];
        for (int i = 0; i < size; i++) {
            fragmentArr[i] = (Fragment) arrayList.get(i);
        }
        cameraCutoutViewPagerAdapter.mChildFragments = fragmentArr;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                size, "no of fragments ", "CameraCutoutViewPagerAdapter");
        this.mAdapter = cameraCutoutViewPagerAdapter;
        viewPager2.setAdapter(cameraCutoutViewPagerAdapter);
        this.mTabs = (TabLayout) findViewById.findViewById(R.id.tabs);
        for (int i2 = 0; i2 < this.mAdapter.mChildFragments.length; i2++) {
            TabLayout tabLayout = this.mTabs;
            TabLayout.Tab newTab = tabLayout.newTab();
            newTab.setText(
                    this.mAdapter
                            .mContext
                            .getResources()
                            .getString(
                                    CameraCutoutViewPagerAdapter.LABEL[
                                            CameraCutoutViewPagerAdapter.convertPosition(i2)]));
            tabLayout.addTab(newTab, tabLayout.tabs.isEmpty());
        }
        this.mTabs.addOnTabSelectedListener$1(
                new TabLayout
                        .OnTabSelectedListener() { // from class:
                                                   // com.samsung.android.settings.display.CameraCutoutFragmentUserSelect.1
                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabSelected(TabLayout.Tab tab) {
                        ViewPager2.this.setCurrentItem(tab.position, true);
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabUnselected(TabLayout.Tab tab) {}
                });
        viewPager2.registerOnPageChangeCallback(
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.samsung.android.settings.display.CameraCutoutFragmentUserSelect.2
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i3) {
                        TabLayout tabLayout2 = CameraCutoutFragmentUserSelect.this.mTabs;
                        tabLayout2.selectTab(tabLayout2.getTabAt(i3), true);
                    }
                });
        findViewById.setVisibility(0);
        mTabCount = viewPager2.mRecyclerView.getAdapter().getItemCount();
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("view page count: "),
                mTabCount,
                "CameraCutoutFragmentUserSelect");
        if (viewPager2.mRecyclerView.getAdapter().getItemCount() < 2) {
            this.mTabs.setVisibility(8);
        }
        int i3 = this.mSelectedTab;
        if (i3 == -1) {
            i3 = 0;
        }
        if (mTabCount > i3) {
            TabLayout.Tab tabAt =
                    this.mTabs.getTabAt(CameraCutoutViewPagerAdapter.convertPosition(i3));
            if (tabAt != null) {
                tabAt.select();
            }
            if (mTabCount == 1) {
                viewPager2.getChildAt(0).setOverScrollMode(2);
            }
        }
        ((FrameLayout) this.mContentView.findViewById(android.R.id.list_container))
                .setVisibility(8);
        RecyclerView listView = getListView();
        listView.setOverScrollMode(2);
        Utils.setActionBarShadowAnimation(activity, getSettingsLifecycle(), listView);
        return this.mContentView;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        int selectedTabPosition = this.mTabs.getSelectedTabPosition();
        this.mSelectedTab = selectedTabPosition;
        bundle.putInt("key_selected_tab", selectedTabPosition);
    }
}
