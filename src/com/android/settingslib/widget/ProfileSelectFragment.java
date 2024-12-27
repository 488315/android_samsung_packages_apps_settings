package com.android.settingslib.widget;

import android.content.pm.UserProperties;
import android.os.Build;
import android.os.Bundle;
import android.os.FeatureFlagsImpl;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.os.BuildCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ProfileSelectFragment extends Fragment {
    public ViewGroup mContentView;
    public final ArrayMap mProfileTabsByUsers = new ArrayMap();
    public boolean mUsingUserIds = false;
    public ViewPager2 mViewPager;

    public abstract Fragment createFragment(int i);

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ArrayList<Integer> integerArrayList;
        int i = 0;
        this.mContentView =
                (ViewGroup) layoutInflater.inflate(R.layout.tab_fragment, viewGroup, false);
        getActivity();
        Bundle arguments = getArguments();
        if (arguments != null
                && (integerArrayList = arguments.getIntegerArrayList(":settings:list_user_ids"))
                        != null
                && !integerArrayList.isEmpty()) {
            this.mUsingUserIds = true;
            UserManager userManager =
                    (UserManager) getContext().getSystemService(UserManager.class);
            for (UserHandle userHandle : userManager.getUserProfiles()) {
                if (integerArrayList.contains(Integer.valueOf(userHandle.getIdentifier()))) {
                    if (userHandle.isSystem()) {
                        this.mProfileTabsByUsers.put(userHandle, 0);
                    } else if (userManager.isManagedProfile(userHandle.getIdentifier())) {
                        this.mProfileTabsByUsers.put(userHandle, 1);
                    } else {
                        int i2 = BuildCompat.$r8$clinit;
                        String CODENAME = Build.VERSION.CODENAME;
                        Intrinsics.checkNotNullExpressionValue(CODENAME, "CODENAME");
                        if ("REL".equals(CODENAME)) {
                            continue;
                        } else {
                            Locale locale = Locale.ROOT;
                            String upperCase = CODENAME.toUpperCase(locale);
                            Intrinsics.checkNotNullExpressionValue(
                                    upperCase,
                                    "this as java.lang.String).toUpperCase(Locale.ROOT)");
                            String upperCase2 = "VanillaIceCream".toUpperCase(locale);
                            Intrinsics.checkNotNullExpressionValue(
                                    upperCase2,
                                    "this as java.lang.String).toUpperCase(Locale.ROOT)");
                            if (upperCase.compareTo(upperCase2) < 0) {
                                continue;
                            } else {
                                if (!FeatureFlagsImpl.profile_experiences_is_cached) {
                                    try {
                                        FeatureFlagsImpl.allowPrivateProfile =
                                                DeviceConfig.getProperties(
                                                                "profile_experiences",
                                                                new String[0])
                                                        .getBoolean(
                                                                "android.os.allow_private_profile",
                                                                false);
                                        FeatureFlagsImpl.profile_experiences_is_cached = true;
                                    } catch (NullPointerException e) {
                                        throw new RuntimeException(
                                                "Cannot read value from namespace"
                                                    + " profile_experiences from DeviceConfig. It"
                                                    + " could be that the code using flag executed"
                                                    + " before SettingsProvider initialization."
                                                    + " Please use fixed read-only flag by adding"
                                                    + " is_fixed_read_only: true in flag"
                                                    + " declaration.",
                                                e);
                                    }
                                }
                                if (FeatureFlagsImpl.allowPrivateProfile) {
                                    try {
                                        UserManager userManager2 =
                                                (UserManager)
                                                        getContext()
                                                                .createContextAsUser(userHandle, 0)
                                                                .getSystemService(
                                                                        UserManager.class);
                                        if (userManager2.isPrivateProfile()) {
                                            UserProperties userProperties =
                                                    userManager2.getUserProperties(userHandle);
                                            if (!userManager2.isQuietModeEnabled(userHandle)
                                                    || userProperties.getShowInQuietMode() != 1) {
                                                this.mProfileTabsByUsers.put(userHandle, 2);
                                            }
                                        }
                                    } catch (IllegalStateException unused) {
                                        Log.i(
                                                "ProfileSelectFragment",
                                                "Ignoring this user as the calling package not"
                                                    + " available in this user.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        View findViewById = this.mContentView.findViewById(R.id.tab_container);
        ViewPager2 viewPager2 = (ViewPager2) findViewById.findViewById(R.id.view_pager);
        this.mViewPager = viewPager2;
        viewPager2.setAdapter(new ProfileViewPagerAdapter(this));
        TabLayout tabLayout = (TabLayout) findViewById.findViewById(R.id.tabs);
        new TabLayoutMediator(
                        tabLayout,
                        this.mViewPager,
                        new TabLayoutMediator
                                .TabConfigurationStrategy() { // from class:
                                                              // com.android.settingslib.widget.ProfileSelectFragment$$ExternalSyntheticLambda0
                            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
                            public final void onConfigureTab(TabLayout.Tab tab, int i3) {
                                ProfileSelectFragment profileSelectFragment =
                                        ProfileSelectFragment.this;
                                if (profileSelectFragment.mUsingUserIds) {
                                    i3 =
                                            ((Integer)
                                                            profileSelectFragment
                                                                    .mProfileTabsByUsers.valueAt(
                                                                    i3))
                                                    .intValue();
                                }
                                tab.setText(
                                        i3 == 1
                                                ? profileSelectFragment
                                                        .getContext()
                                                        .getString(
                                                                R.string.settingslib_category_work)
                                                : i3 == 2
                                                        ? profileSelectFragment
                                                                .getContext()
                                                                .getString(
                                                                        R.string
                                                                                .settingslib_category_private)
                                                        : profileSelectFragment.getString(
                                                                R.string
                                                                        .settingslib_category_personal));
                            }
                        })
                .attach();
        findViewById.setVisibility(0);
        Bundle arguments2 = getArguments();
        if (arguments2 != null) {
            int i3 = arguments2.getInt(":settings:show_fragment_user_id", -10000);
            if (i3 != -10000) {
                i = this.mProfileTabsByUsers.indexOfKey(UserHandle.of(i3));
            } else {
                int i4 = arguments2.getInt(":settings:show_fragment_tab", -1);
                if (i4 != -1) {
                    i = i4;
                }
            }
        }
        tabLayout.getTabAt(i).select();
        return this.mContentView;
    }
}
