package com.samsung.android.settings.privacy;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardOfflineLockDisclaimerFragment extends Fragment {
    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final boolean isScreenLockEnabled =
                SecurityDashboardUtils.isScreenLockEnabled(getActivity());
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_security_and_privacy_offline_lock_onboard_settings,
                        viewGroup,
                        false);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_set_screen_lock_title);
        View findViewById = inflate.findViewById(R.id.view_set_lock_warning);
        TextView textView2 = (TextView) inflate.findViewById(R.id.tv_set_screen_lock_description);
        if (isScreenLockEnabled) {
            textView.setText(getResources().getString(R.string.theft_protection_screenlock_set));
            textView2.setText(
                    getResources().getString(R.string.theft_protection_screenlock_set_description));
            findViewById.setBackground(getActivity().getDrawable(R.drawable.list_status_green));
        }
        inflate.findViewById(R.id.turn_on_button)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.privacy.SecurityDashboardOfflineLockDisclaimerFragment$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SecurityDashboardOfflineLockDisclaimerFragment
                                        securityDashboardOfflineLockDisclaimerFragment =
                                                SecurityDashboardOfflineLockDisclaimerFragment.this;
                                boolean z = isScreenLockEnabled;
                                FragmentActivity activity =
                                        securityDashboardOfflineLockDisclaimerFragment
                                                .getActivity();
                                Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
                                SharedPreferences.Editor edit =
                                        activity.getSharedPreferences(
                                                        "key_theft_protection_preference", 0)
                                                .edit();
                                edit.putBoolean("key_offline_lock_on_boarding_status", true);
                                edit.commit();
                                Settings.Secure.putInt(
                                        securityDashboardOfflineLockDisclaimerFragment
                                                .getContext()
                                                .getContentResolver(),
                                        "offline_device_lock_setting",
                                        1);
                                securityDashboardOfflineLockDisclaimerFragment
                                        .getActivity()
                                        .getOnBackPressedDispatcher()
                                        .onBackPressed();
                                if (z) {
                                    return;
                                }
                                SecurityDashboardUtils.launchChooseLockScreen(
                                        securityDashboardOfflineLockDisclaimerFragment
                                                .getActivity());
                            }
                        });
        return inflate;
    }
}
