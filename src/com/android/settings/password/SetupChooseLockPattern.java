package com.android.settings.password;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SetupRedactionInterstitial;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.settings.lockscreen.LockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupChooseLockPattern extends ChooseLockPattern {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SetupChooseLockPatternFragment
            extends ChooseLockPattern.ChooseLockPatternFragment
            implements ChooseLockTypeDialogFragment.OnLockTypeSelectedListener {
        @Override // com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment
        public final Intent getRedactionInterstitialIntent(FragmentActivity fragmentActivity) {
            SetupRedactionInterstitial.setEnabled(fragmentActivity, true);
            return null;
        }

        @Override // com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment,
                  // androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
            FragmentActivity activity = getActivity();
            if (!LockUtils.isApplyingTabletGUI(activity)) {
                onCreateView.setBackgroundColor(
                        getResources().getColor(R.color.sec_lock_suw_background_color, null));
            }
            activity.getWindow()
                    .setStatusBarColor(
                            getResources().getColor(R.color.sec_lock_suw_background_color, null));
            activity.getWindow()
                    .setNavigationBarColor(
                            getResources().getColor(R.color.sec_lock_suw_background_color, null));
            return onCreateView;
        }

        @Override // com.android.settings.password.ChooseLockTypeDialogFragment.OnLockTypeSelectedListener
        public final void onLockTypeSelected(ScreenLockType screenLockType) {
            if (ScreenLockType.PATTERN == screenLockType) {
                return;
            }
            ChooseLockTypeDialogFragment.OnLockTypeSelectedListener.startChooseLockActivity(
                    screenLockType, getActivity());
        }

        @Override // com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment
        public final void onSkipOrClearButtonClick() {
            handleLeftButton();
        }

        @Override // com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment
        public final void updateStage(ChooseLockPattern.ChooseLockPatternFragment.Stage stage) {
            super.updateStage(stage);
            getResources().getBoolean(R.bool.config_lock_pattern_minimal_ui);
            if (stage.leftMode == ChooseLockPattern.ChooseLockPatternFragment.LeftButtonMode.Gone
                    && stage == ChooseLockPattern.ChooseLockPatternFragment.Stage.Introduction) {
                throw null;
            }
        }
    }

    @Override // com.android.settings.password.ChooseLockPattern
    public final Class getFragmentClass() {
        return SetupChooseLockPatternFragment.class;
    }

    @Override // com.android.settings.password.ChooseLockPattern,
              // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return SetupChooseLockPatternFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.password.ChooseLockPattern,
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
