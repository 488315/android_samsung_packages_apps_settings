package com.android.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.dashboard.DashboardFragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.common.collect.ImmutableList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class TrustedCredentialsSettings extends DashboardFragment {
    public static final ImmutableList TABS = ImmutableList.construct(Tab.SYSTEM, Tab.USER);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FragmentAdapter extends FragmentStateAdapter {
        @Override // androidx.viewpager2.adapter.FragmentStateAdapter
        public final Fragment createFragment(int i) {
            TrustedCredentialsFragment trustedCredentialsFragment =
                    new TrustedCredentialsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tab", i);
            trustedCredentialsFragment.setArguments(bundle);
            return trustedCredentialsFragment;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return TrustedCredentialsSettings.TABS.size();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Tab {
        SYSTEM("SYSTEM", true),
        USER("USER", false);

        private final int mLabel;
        final boolean mSwitch;

        Tab(String str, boolean z) {
            this.mLabel = r2;
            this.mSwitch = z;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "TrustedCredentialsSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 92;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.placeholder_preference_screen;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.trusted_credentials);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View findViewById = view.findViewById(R.id.tab_container);
        findViewById.setVisibility(0);
        ViewPager2 viewPager2 = (ViewPager2) findViewById.findViewById(R.id.view_pager);
        viewPager2.setAdapter(new FragmentAdapter(this));
        viewPager2.mUserInputEnabled = false;
        viewPager2.mAccessibilityProvider.updatePageAccessibilityActions();
        Intent intent = getActivity().getIntent();
        if (intent != null
                && "com.android.settings.TRUSTED_CREDENTIALS_USER".equals(intent.getAction())) {
            viewPager2.setCurrentItem(TABS.indexOf(Tab.USER), false);
        }
        new TabLayoutMediator(
                        (TabLayout) findViewById.findViewById(R.id.tabs),
                        viewPager2,
                        false,
                        false,
                        new TrustedCredentialsSettings$$ExternalSyntheticLambda0())
                .attach();
    }
}
