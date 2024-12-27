package com.android.settingslib.collapsingtoolbar;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.android.settings.R;

import com.google.android.material.appbar.AppBarLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BasePreferencesFragment extends PreferenceFragmentCompat {
    public abstract CharSequence getTitle();

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getTitle());
            AppBarLayout appBarLayout = (AppBarLayout) activity.findViewById(R.id.app_bar);
            if (appBarLayout != null) {
                appBarLayout.setExpanded(true);
            }
        }
    }
}
