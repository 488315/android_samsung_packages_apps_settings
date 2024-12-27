package com.android.settings.dashboard.profileselector;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.privatespace.PrivateSpaceMaintainer;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ProfileSelectFragment extends DashboardFragment {
    public ViewGroup mContentView;
    public ViewPager2 mViewPager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface FragmentConstructor {
        Fragment constructAndGetFragment();
    }

    public static Fragment[] getFragments(
            Context context,
            Bundle bundle,
            FragmentConstructor fragmentConstructor,
            FragmentConstructor fragmentConstructor2,
            FragmentConstructor fragmentConstructor3) {
        Fragment[] fragmentArr = new Fragment[0];
        ArrayList arrayList = new ArrayList();
        try {
            for (UserInfo userInfo :
                    ((UserManager) context.getSystemService(UserManager.class))
                            .getProfiles(UserHandle.myUserId())) {
                if (userInfo.isMain()) {
                    Bundle bundle2 = bundle != null ? bundle : new Bundle();
                    bundle2.putInt(ImsProfile.SERVICE_PROFILE, 1);
                    Fragment constructAndGetFragment =
                            fragmentConstructor.constructAndGetFragment();
                    constructAndGetFragment.setArguments(bundle2);
                    arrayList.add(constructAndGetFragment);
                } else if (userInfo.isManagedProfile() && !userInfo.isSecureFolder()) {
                    Bundle deepCopy = bundle != null ? bundle.deepCopy() : new Bundle();
                    deepCopy.putInt(ImsProfile.SERVICE_PROFILE, 2);
                    Fragment constructAndGetFragment2 =
                            fragmentConstructor2.constructAndGetFragment();
                    constructAndGetFragment2.setArguments(deepCopy);
                    arrayList.add(constructAndGetFragment2);
                } else if (!Flags.allowPrivateProfile()
                        || !android.multiuser.Flags.enablePrivateSpaceFeatures()
                        || !userInfo.isPrivateProfile()) {
                    Log.d(
                            "ProfileSelectFragment",
                            "Not showing tab for unsupported user " + userInfo);
                } else if (!PrivateSpaceMaintainer.getInstance(context).isPrivateSpaceLocked()) {
                    Bundle deepCopy2 = bundle != null ? bundle.deepCopy() : new Bundle();
                    deepCopy2.putInt(ImsProfile.SERVICE_PROFILE, 4);
                    Fragment constructAndGetFragment3 =
                            fragmentConstructor3.constructAndGetFragment();
                    constructAndGetFragment3.setArguments(deepCopy2);
                    arrayList.add(constructAndGetFragment3);
                }
            }
            fragmentArr = new Fragment[arrayList.size()];
            arrayList.toArray(fragmentArr);
            return fragmentArr;
        } catch (Exception unused) {
            Log.e("ProfileSelectFragment", "Failed to create fragment");
            return fragmentArr;
        }
    }

    public abstract Fragment[] getFragments();

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public String getTAG() {
        return "ProfileSelectFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public int getPreferenceScreenResId() {
        return R.xml.placeholder_preference_screen;
    }

    public int getTabId(Activity activity, Bundle bundle) {
        if (bundle != null) {
            int i = bundle.getInt(":settings:show_fragment_tab", -1);
            if (i != -1) {
                ViewPagerAdapter viewPagerAdapter =
                        (ViewPagerAdapter) this.mViewPager.mRecyclerView.getAdapter();
                int i2 = 0;
                while (true) {
                    Fragment[] fragmentArr = viewPagerAdapter.mChildFragments;
                    if (i2 >= fragmentArr.length) {
                        WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                .m(
                                        i,
                                        "position requested for an unknown profile tab ",
                                        "ProfileSelectFragment");
                        return 0;
                    }
                    Bundle arguments = fragmentArr[i2].getArguments();
                    if (arguments != null) {
                        int i3 = arguments.getInt(ImsProfile.SERVICE_PROFILE);
                        if ((i3 == 2 ? 1 : i3 == 4 ? 2 : 0) == i) {
                            return i2;
                        }
                    }
                    i2++;
                }
            } else {
                UserHandle mainUser =
                        ((UserManager) getSystemService(UserManager.class)).getMainUser();
                if (mainUser == null) {
                    mainUser = UserHandle.SYSTEM;
                }
                int i4 = bundle.getInt("android.intent.extra.USER_ID", mainUser.getIdentifier());
                if (UserManager.get(activity).isManagedProfile(i4)) {
                    return 1;
                }
                UserInfo userInfo = UserManager.get(activity).getUserInfo(i4);
                if (Flags.allowPrivateProfile()
                        && android.multiuser.Flags.enablePrivateSpaceFeatures()
                        && userInfo != null
                        && userInfo.isPrivateProfile()) {
                    return 2;
                }
            }
        }
        int contentUserHint = activity.getIntent().getContentUserHint();
        if (UserManager.get(activity).isManagedProfile(contentUserHint)) {
            return 1;
        }
        UserInfo userInfo2 = UserManager.get(activity).getUserInfo(contentUserHint);
        if (Flags.allowPrivateProfile()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()
                && userInfo2 != null
                && userInfo2.isPrivateProfile()) {
            return 2;
        }
        return UserManager.get(activity).isManagedProfile(getActivity().getUser().getIdentifier())
                ? 1
                : 0;
    }

    public int getTitleResId() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        FragmentActivity activity = getActivity();
        int titleResId = getTitleResId();
        if (titleResId > 0) {
            activity.setTitle(titleResId);
        }
        View findViewById = this.mContentView.findViewById(R.id.tab_container);
        ViewPager2 viewPager2 = (ViewPager2) findViewById.findViewById(R.id.view_pager);
        this.mViewPager = viewPager2;
        viewPager2.semSetRoundedCorners(12);
        this.mViewPager.semSetRoundedCornerColor(
                12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mViewPager.setAdapter(new ViewPagerAdapter(this));
        TabLayout tabLayout = (TabLayout) findViewById.findViewById(R.id.tabs);
        tabLayout.setLayoutDirection(TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()));
        new TabLayoutMediator(
                        tabLayout,
                        this.mViewPager,
                        new TabLayoutMediator.TabConfigurationStrategy() { // from class:
                            // com.android.settings.dashboard.profileselector.ProfileSelectFragment$$ExternalSyntheticLambda0
                            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
                            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                                String string;
                                final ProfileSelectFragment profileSelectFragment =
                                        ProfileSelectFragment.this;
                                DevicePolicyManager devicePolicyManager =
                                        (DevicePolicyManager)
                                                profileSelectFragment
                                                        .getContext()
                                                        .getSystemService(
                                                                DevicePolicyManager.class);
                                if (Flags.allowPrivateProfile()
                                        && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
                                    int tabForPosition =
                                            ((ProfileSelectFragment.ViewPagerAdapter)
                                                            profileSelectFragment.mViewPager
                                                                    .mRecyclerView.getAdapter())
                                                    .getTabForPosition(i);
                                    if (tabForPosition == 1) {
                                        final int i2 = 0;
                                        string =
                                                devicePolicyManager
                                                        .getResources()
                                                        .getString(
                                                                "Settings.WORK_CATEGORY_HEADER",
                                                                new Supplier() { // from class:
                                                                    // com.android.settings.dashboard.profileselector.ProfileSelectFragment$$ExternalSyntheticLambda1
                                                                    @Override // java.util.function.Supplier
                                                                    public final Object get() {
                                                                        int i3 = i2;
                                                                        ProfileSelectFragment
                                                                                profileSelectFragment2 =
                                                                                        profileSelectFragment;
                                                                        switch (i3) {
                                                                            case 0:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_work);
                                                                            case 1:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_private);
                                                                            case 2:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_work);
                                                                            default:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_personal);
                                                                        }
                                                                    }
                                                                });
                                    } else {
                                        if (tabForPosition == 2) {
                                            final int i3 = 1;
                                            string =
                                                    devicePolicyManager
                                                            .getResources()
                                                            .getString(
                                                                    "Settings.PRIVATE_CATEGORY_HEADER",
                                                                    new Supplier() { // from class:
                                                                        // com.android.settings.dashboard.profileselector.ProfileSelectFragment$$ExternalSyntheticLambda1
                                                                        @Override // java.util.function.Supplier
                                                                        public final Object get() {
                                                                            int i32 = i3;
                                                                            ProfileSelectFragment
                                                                                    profileSelectFragment2 =
                                                                                            profileSelectFragment;
                                                                            switch (i32) {
                                                                                case 0:
                                                                                    return profileSelectFragment2
                                                                                            .getContext()
                                                                                            .getString(
                                                                                                    R
                                                                                                            .string
                                                                                                            .category_work);
                                                                                case 1:
                                                                                    return profileSelectFragment2
                                                                                            .getContext()
                                                                                            .getString(
                                                                                                    R
                                                                                                            .string
                                                                                                            .category_private);
                                                                                case 2:
                                                                                    return profileSelectFragment2
                                                                                            .getContext()
                                                                                            .getString(
                                                                                                    R
                                                                                                            .string
                                                                                                            .category_work);
                                                                                default:
                                                                                    return profileSelectFragment2
                                                                                            .getContext()
                                                                                            .getString(
                                                                                                    R
                                                                                                            .string
                                                                                                            .category_personal);
                                                                            }
                                                                        }
                                                                    });
                                        }
                                        final int i4 = 3;
                                        string =
                                                devicePolicyManager
                                                        .getResources()
                                                        .getString(
                                                                "Settings.PERSONAL_CATEGORY_HEADER",
                                                                new Supplier() { // from class:
                                                                    // com.android.settings.dashboard.profileselector.ProfileSelectFragment$$ExternalSyntheticLambda1
                                                                    @Override // java.util.function.Supplier
                                                                    public final Object get() {
                                                                        int i32 = i4;
                                                                        ProfileSelectFragment
                                                                                profileSelectFragment2 =
                                                                                        profileSelectFragment;
                                                                        switch (i32) {
                                                                            case 0:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_work);
                                                                            case 1:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_private);
                                                                            case 2:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_work);
                                                                            default:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_personal);
                                                                        }
                                                                    }
                                                                });
                                    }
                                } else {
                                    if (i == 1) {
                                        final int i5 = 2;
                                        string =
                                                devicePolicyManager
                                                        .getResources()
                                                        .getString(
                                                                "Settings.WORK_CATEGORY_HEADER",
                                                                new Supplier() { // from class:
                                                                    // com.android.settings.dashboard.profileselector.ProfileSelectFragment$$ExternalSyntheticLambda1
                                                                    @Override // java.util.function.Supplier
                                                                    public final Object get() {
                                                                        int i32 = i5;
                                                                        ProfileSelectFragment
                                                                                profileSelectFragment2 =
                                                                                        profileSelectFragment;
                                                                        switch (i32) {
                                                                            case 0:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_work);
                                                                            case 1:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_private);
                                                                            case 2:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_work);
                                                                            default:
                                                                                return profileSelectFragment2
                                                                                        .getContext()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .category_personal);
                                                                        }
                                                                    }
                                                                });
                                    }
                                    final int i42 = 3;
                                    string =
                                            devicePolicyManager
                                                    .getResources()
                                                    .getString(
                                                            "Settings.PERSONAL_CATEGORY_HEADER",
                                                            new Supplier() { // from class:
                                                                // com.android.settings.dashboard.profileselector.ProfileSelectFragment$$ExternalSyntheticLambda1
                                                                @Override // java.util.function.Supplier
                                                                public final Object get() {
                                                                    int i32 = i42;
                                                                    ProfileSelectFragment
                                                                            profileSelectFragment2 =
                                                                                    profileSelectFragment;
                                                                    switch (i32) {
                                                                        case 0:
                                                                            return profileSelectFragment2
                                                                                    .getContext()
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .category_work);
                                                                        case 1:
                                                                            return profileSelectFragment2
                                                                                    .getContext()
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .category_private);
                                                                        case 2:
                                                                            return profileSelectFragment2
                                                                                    .getContext()
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .category_work);
                                                                        default:
                                                                            return profileSelectFragment2
                                                                                    .getContext()
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .category_personal);
                                                                    }
                                                                }
                                                            });
                                }
                                tab.setText(string);
                            }
                        })
                .attach();
        this.mViewPager.registerOnPageChangeCallback(
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.android.settings.dashboard.profileselector.ProfileSelectFragment.1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i) {
                        ViewPagerAdapter viewPagerAdapter;
                        View view;
                        ProfileSelectFragment profileSelectFragment = ProfileSelectFragment.this;
                        profileSelectFragment.getClass();
                        if ((profileSelectFragment instanceof ProfileSelectLocationServicesFragment)
                                && (viewPagerAdapter =
                                                (ViewPagerAdapter)
                                                        profileSelectFragment.mViewPager
                                                                .mRecyclerView.getAdapter())
                                        != null) {
                            Fragment[] fragmentArr = viewPagerAdapter.mChildFragments;
                            if (fragmentArr.length > i
                                    && (view = fragmentArr[i].getView()) != null) {
                                view.measure(
                                        View.MeasureSpec.makeMeasureSpec(
                                                view.getWidth(), 1073741824),
                                        View.MeasureSpec.makeMeasureSpec(0, 0));
                                int i2 = profileSelectFragment.mViewPager.getLayoutParams().height;
                                int measuredHeight = view.getMeasuredHeight();
                                if (measuredHeight == 0 || i2 == measuredHeight) {
                                    return;
                                }
                                ViewGroup.LayoutParams layoutParams =
                                        profileSelectFragment.mViewPager.getLayoutParams();
                                layoutParams.height = measuredHeight;
                                profileSelectFragment.mViewPager.setLayoutParams(layoutParams);
                            }
                        }
                    }
                });
        findViewById.setVisibility(0);
        tabLayout.getTabAt(getTabId(activity, getArguments())).select();
        ((FrameLayout) this.mContentView.findViewById(android.R.id.list_container))
                .setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        RecyclerView listView = getListView();
        listView.setOverScrollMode(2);
        Utils.setActionBarShadowAnimation(activity, getSettingsLifecycle(), listView);
        return this.mContentView;
    }

    public void setViewPager(ViewPager2 viewPager2) {
        this.mViewPager = viewPager2;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewPagerAdapter extends FragmentStateAdapter {
        public final Fragment[] mChildFragments;

        public ViewPagerAdapter(ProfileSelectFragment profileSelectFragment) {
            super(profileSelectFragment);
            this.mChildFragments = profileSelectFragment.getFragments();
        }

        @Override // androidx.viewpager2.adapter.FragmentStateAdapter
        public final Fragment createFragment(int i) {
            return this.mChildFragments[i];
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mChildFragments.length;
        }

        public int getTabForPosition(int i) {
            Fragment[] fragmentArr = this.mChildFragments;
            if (i >= fragmentArr.length) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "tab requested for out of bound position ", "ProfileSelectFragment");
                return 0;
            }
            int i2 = fragmentArr[i].getArguments().getInt(ImsProfile.SERVICE_PROFILE);
            if (i2 == 2) {
                return 1;
            }
            return i2 == 4 ? 2 : 0;
        }

        public ViewPagerAdapter(
                FragmentManager fragmentManager,
                Lifecycle lifecycle,
                ProfileSelectFragment profileSelectFragment) {
            super(fragmentManager, lifecycle);
            this.mChildFragments = profileSelectFragment.getFragments();
        }
    }
}
