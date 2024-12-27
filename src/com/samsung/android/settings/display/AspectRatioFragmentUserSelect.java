package com.samsung.android.settings.display;

import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.display.Adapters.AspectRatioViewPagerAdapter;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AspectRatioFragmentUserSelect extends DashboardFragment {
    public static int mTabCount = 2;
    public AspectRatioViewPagerAdapter mAdapter;
    public ViewGroup mContentView;
    public FragmentActivity mContext;
    public TabLayout mTabs;
    public UserManager mUserManager;
    public ArrayList mUserInfoList = new ArrayList();
    public String mKeyAppName = null;
    public int mSelectedTab = -1;

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AspectRatioFragmentUserSelect";
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
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mKeyAppName = arguments.getString(":settings:fragment_args_key");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0123, code lost:

       if (r11 > 42) goto L31;
    */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014f A[LOOP:3: B:47:0x00e8->B:58:0x014f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0156 A[EDGE_INSN: B:59:0x0156->B:18:0x0156 BREAK  A[LOOP:3: B:47:0x00e8->B:58:0x014f], SYNTHETIC] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onCreateView(
            android.view.LayoutInflater r18, android.view.ViewGroup r19, android.os.Bundle r20) {
        /*
            Method dump skipped, instructions count: 579
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.display.AspectRatioFragmentUserSelect.onCreateView(android.view.LayoutInflater,"
                    + " android.view.ViewGroup, android.os.Bundle):android.view.View");
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
