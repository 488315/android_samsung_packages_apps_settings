package com.android.settings.password;

import android.app.KeyguardManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;

import com.google.android.setupdesign.util.ThemeHelper;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ConfirmDeviceCredentialBaseActivity extends SettingsActivity {
    public boolean mReturnCredentials;
    public boolean mIsKeyguardLocked = false;
    public boolean mFrp = false;
    public boolean mRemoteValidation = false;

    @Override // com.android.settings.core.SettingsBaseActivity
    public final boolean enforceEdgeToEdge() {
        try {
            int credentialOwnerProfile =
                    ((UserManager) getSystemService(UserManager.class))
                            .getCredentialOwnerProfile(
                                    Utils.getUserIdFromBundle(
                                            this, getIntent().getExtras(), isInternalActivity()));
            if (!SemPersonaManager.isKnoxId(credentialOwnerProfile)
                    || (!KnoxUtils.isPwdChangeEnforced(this, credentialOwnerProfile)
                            && this.mReturnCredentials)) {
                return false;
            }
            Log.d("ConfirmDeviceCredentialBaseActivity", "enforceEdgeToEdge : isKnoxId");
            return true;
        } catch (SecurityException e) {
            Log.e("ConfirmDeviceCredentialBaseActivity", "Invalid user Id supplied", e);
            return false;
        }
    }

    @Override // android.app.Activity
    public final void finish() {
        super.finish();
        if (getIntent()
                .getBooleanExtra(
                        "com.android.settings.ConfirmCredentials.useFadeAnimation", false)) {
            overridePendingTransition(0, R.anim.confirm_credential_biometric_transition_exit);
        }
    }

    public final boolean isInternalActivity() {
        return (this instanceof ConfirmLockPassword.InternalActivity)
                || (this instanceof ConfirmLockPattern.InternalActivity);
    }

    @Override // com.android.settings.core.SettingsBaseActivity
    public final boolean isLaunchableInTaskModePinned() {
        return true;
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity,
              // android.view.ContextThemeWrapper
    public void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        try {
            int credentialOwnerProfile =
                    ((UserManager) getSystemService(UserManager.class))
                            .getCredentialOwnerProfile(
                                    Utils.getUserIdFromBundle(
                                            this, getIntent().getExtras(), isInternalActivity()));
            this.mReturnCredentials = getIntent().getBooleanExtra("return_credentials", false);
            if (SemPersonaManager.isKnoxId(credentialOwnerProfile)
                    && (KnoxUtils.isPwdChangeEnforced(this, credentialOwnerProfile)
                            || !this.mReturnCredentials)) {
                i =
                        (KnoxUtils.isTablet() || KnoxUtils.isFoldableProduct())
                                ? R.style.KnoxWorkChallengeTabletTheme
                                : R.style.KnoxWorkChallengeTheme;
            }
            super.onApplyThemeResource(theme, i, z);
        } catch (SecurityException e) {
            Log.e("ConfirmDeviceCredentialBaseActivity", "Invalid user Id supplied", e);
            finish();
        }
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        try {
            int credentialOwnerProfile =
                    ((UserManager) getSystemService(UserManager.class))
                            .getCredentialOwnerProfile(
                                    Utils.getUserIdFromBundle(
                                            this, getIntent().getExtras(), isInternalActivity()));
            UserManager.get(this).isManagedProfile(credentialOwnerProfile);
            this.mFrp =
                    Utils.getUserIdFromBundle(this, getIntent().getExtras(), isInternalActivity())
                            == -9999;
            if (getIntent()
                    .getBooleanExtra(
                            "com.android.settings.ConfirmCredentials.isRemoteLockscreenValidation",
                            false)) {
                this.mRemoteValidation = true;
            }
            ThemeHelper.trySetDynamicColor(this);
            super.onCreate(bundle);
            if (!LockUtils.isEmTokenAllowed(this)) {
                getWindow().addFlags(8192);
            }
            boolean isKeyguardLocked =
                    bundle == null
                            ? ((KeyguardManager) getSystemService(KeyguardManager.class))
                                    .isKeyguardLocked()
                            : bundle.getBoolean("STATE_IS_KEYGUARD_LOCKED", false);
            this.mIsKeyguardLocked = isKeyguardLocked;
            if (isKeyguardLocked
                    && getIntent()
                            .getBooleanExtra(
                                    "com.android.settings.ConfirmCredentials.showWhenLocked", false)
                    && getIntent()
                            .getBooleanExtra("knox.container.proxy.EXTRA_SHOW_WHEN_LOCKED", true)) {
                UserInfo profileParent =
                        UserManager.get(this)
                                .getProfileParent(
                                        Utils.getUserIdFromBundle(
                                                this,
                                                getIntent().getExtras(),
                                                isInternalActivity()));
                if (profileParent != null
                        && UserManager.get(this).isUserUnlocked(profileParent.getUserHandle())) {
                    Log.d("ConfirmDeviceCredentialBaseActivity", "set flag show when locked");
                    getWindow().addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
                }
            }
            setTitle(getIntent().getStringExtra("com.android.settings.ConfirmCredentials.title"));
            if (getActionBar() != null) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setHomeButtonEnabled(true);
            }
            boolean z =
                    SemPersonaManager.isKnoxId(credentialOwnerProfile)
                            && (KnoxUtils.isPwdChangeEnforced(this, credentialOwnerProfile)
                                    || !this.mReturnCredentials);
            boolean isApplyingTabletGUI = LockUtils.isApplyingTabletGUI(this);
            if (z || isApplyingTabletGUI || this.mFrp || this.mRemoteValidation) {
                hideAppBar();
            } else {
                LockUtils.addHorizontalSpacing(
                        this,
                        (LinearLayout) findViewById(R.id.content_layout),
                        (FrameLayout) findViewById(R.id.content_frame));
            }
            if (z || isApplyingTabletGUI) {
                if (!z && isApplyingTabletGUI) {
                    Window window = getWindow();
                    window.setAttributes(window.getAttributes());
                    if (!this.mFrp) {
                        getWindow().addFlags(512);
                    }
                }
                CoordinatorLayout coordinatorLayout =
                        (CoordinatorLayout) findViewById(R.id.coordinator);
                if (coordinatorLayout != null) {
                    coordinatorLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                }
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_layout);
                if (linearLayout != null) {
                    linearLayout.setLayoutParams(
                            new CoordinatorLayout.LayoutParams(-1, z ? -1 : -2));
                }
            }
            if (isApplyingTabletGUI && getWindow().isFloating()) {
                getWindow().clearFlags(Integer.MIN_VALUE);
                getWindow().getAttributes().gravity = 80;
                View findViewById = findViewById(R.id.round_corner);
                if (findViewById != null) {
                    findViewById.setVisibility(8);
                }
            }
            ActionBar supportActionBar = getSupportActionBar();
            if (getIntent().getBooleanExtra("lockscreen.password_isenforced", false)
                    && supportActionBar != null) {
                supportActionBar.setHomeButtonEnabled();
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
            if (this.mFrp || this.mRemoteValidation) {
                LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.content_layout);
                linearLayout2.semSetRoundedCorners(0);
                linearLayout2.setFitsSystemWindows(true);
                if (supportActionBar != null) {
                    supportActionBar.setHomeButtonEnabled();
                    supportActionBar.setDisplayHomeAsUpEnabled(false);
                    supportActionBar.setBackgroundDrawable(
                            new ColorDrawable(
                                    getResources()
                                            .getColor(
                                                    R.color.sec_lock_suw_background_color, null)));
                }
            }
            disableExtendedAppBar();
        } catch (SecurityException e) {
            Log.e("ConfirmDeviceCredentialBaseActivity", "Invalid user Id supplied", e);
            finish();
        }
    }

    @Override // com.android.settings.SettingsActivity, androidx.appcompat.app.AppCompatActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        new Handler(Looper.myLooper())
                .postDelayed(
                        new ConfirmDeviceCredentialBaseActivity$$ExternalSyntheticLambda0(), 5000L);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    @Override // com.android.settings.SettingsActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        isChangingConfigurations();
    }

    @Override // com.android.settings.SettingsActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("STATE_IS_KEYGUARD_LOCKED", this.mIsKeyguardLocked);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        super.onStop();
        boolean booleanExtra = getIntent().getBooleanExtra("foreground_only", false);
        if (isChangingConfigurations() || !booleanExtra) {
            return;
        }
        finish();
    }
}
