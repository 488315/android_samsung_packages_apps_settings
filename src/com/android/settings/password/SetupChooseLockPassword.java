package com.android.settings.password;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SetupRedactionInterstitial;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.settings.lockscreen.LockUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupChooseLockPassword extends ChooseLockPassword {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SetupChooseLockPasswordFragment
            extends ChooseLockPassword.ChooseLockPasswordFragment
            implements ChooseLockTypeDialogFragment.OnLockTypeSelectedListener {
        @Override // com.android.settings.password.ChooseLockPassword.ChooseLockPasswordFragment
        public final Intent getRedactionInterstitialIntent(FragmentActivity fragmentActivity) {
            SetupRedactionInterstitial.setEnabled(fragmentActivity, true);
            return null;
        }

        @Override // com.android.settings.password.ChooseLockTypeDialogFragment.OnLockTypeSelectedListener
        public final void onLockTypeSelected(ScreenLockType screenLockType) {
            if (screenLockType
                    == (this.mIsAlphaMode ? ScreenLockType.PASSWORD : ScreenLockType.PIN)) {
                return;
            }
            ChooseLockTypeDialogFragment.OnLockTypeSelectedListener.startChooseLockActivity(
                    screenLockType, getActivity());
        }

        @Override // com.android.settings.password.ChooseLockPassword.ChooseLockPasswordFragment,
                  // androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            FragmentActivity activity = getActivity();
            ChooseLockGenericController.Builder builder =
                    new ChooseLockGenericController.Builder(activity, this.mUserId);
            builder.mHideInsecureScreenLockTypes = true;
            boolean z =
                    ((ArrayList) builder.build().getVisibleAndEnabledScreenLockTypes()).size() > 0;
            activity.getIntent().getBooleanExtra("show_options_button", false);
            if (!z) {
                Log.w("SetupChooseLockPassword", "Visible screen lock types is empty!");
            }
            if (!LockUtils.isApplyingTabletGUI(activity)) {
                view.setBackgroundColor(
                        getResources().getColor(R.color.sec_lock_suw_background_color, null));
            }
            activity.getWindow()
                    .setStatusBarColor(
                            getResources().getColor(R.color.sec_lock_suw_background_color, null));
            activity.getWindow()
                    .setNavigationBarColor(
                            getResources().getColor(R.color.sec_lock_suw_background_color, null));
        }
    }

    @Override // com.android.settings.password.ChooseLockPassword
    public final Class getFragmentClass() {
        return SetupChooseLockPasswordFragment.class;
    }

    @Override // com.android.settings.password.ChooseLockPassword,
              // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return SetupChooseLockPasswordFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.password.ChooseLockPassword,
              // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        findViewById(R.id.content_parent).setFitsSystemWindows(true);
        if (LockUtils.isApplyingTabletGUI(this)) {
            return;
        }
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setBackgroundColor(
                getResources().getColor(R.color.sec_lock_suw_background_color, null));
        appBarLayout.setVisibility(0);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setBackgroundDrawable(
                    new ColorDrawable(
                            getResources().getColor(R.color.sec_lock_suw_background_color, null)));
        }
    }
}
