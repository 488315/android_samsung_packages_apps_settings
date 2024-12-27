package com.android.settings.privatespace;

import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.SetScreenLockDialogActivity;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.activityembedding.EmbeddedDeepLinkUtils;
import com.android.settings.core.SubSettingLauncher;

import com.google.android.setupdesign.util.ThemeHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceAuthenticationActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public KeyguardManager mKeyguardManager;
    public PrivateSpaceMaintainer mPrivateSpaceMaintainer;
    public final ActivityResultRegistry.AnonymousClass2 mSetDeviceLock;
    public final ActivityResultRegistry.AnonymousClass2 mVerifyDeviceLock;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Injector {}

    public PrivateSpaceAuthenticationActivity() {
        final int i = 0;
        this.mSetDeviceLock =
                (ActivityResultRegistry.AnonymousClass2)
                        registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback(
                                        this) { // from class:
                                                // com.android.settings.privatespace.PrivateSpaceAuthenticationActivity$$ExternalSyntheticLambda3
                                    public final /* synthetic */ PrivateSpaceAuthenticationActivity
                                            f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        PrivateSpaceAuthenticationActivity
                                                privateSpaceAuthenticationActivity = this.f$0;
                                        ActivityResult activityResult = (ActivityResult) obj;
                                        switch (i) {
                                            case 0:
                                                int i2 =
                                                        PrivateSpaceAuthenticationActivity
                                                                .$r8$clinit;
                                                if (activityResult == null) {
                                                    privateSpaceAuthenticationActivity.getClass();
                                                    break;
                                                } else {
                                                    if (privateSpaceAuthenticationActivity
                                                                    .mKeyguardManager
                                                            == null) {
                                                        privateSpaceAuthenticationActivity
                                                                        .mKeyguardManager =
                                                                (KeyguardManager)
                                                                        privateSpaceAuthenticationActivity
                                                                                .getSystemService(
                                                                                        KeyguardManager
                                                                                                .class);
                                                    }
                                                    if (!privateSpaceAuthenticationActivity
                                                            .mKeyguardManager.isDeviceSecure()) {
                                                        privateSpaceAuthenticationActivity.finish();
                                                        break;
                                                    } else {
                                                        privateSpaceAuthenticationActivity
                                                                .onLockAuthentication(
                                                                        privateSpaceAuthenticationActivity);
                                                        break;
                                                    }
                                                }
                                            default:
                                                int i3 =
                                                        PrivateSpaceAuthenticationActivity
                                                                .$r8$clinit;
                                                if (activityResult != null) {
                                                    privateSpaceAuthenticationActivity.getClass();
                                                    if (activityResult.mResultCode == -1) {
                                                        privateSpaceAuthenticationActivity
                                                                .onLockAuthentication(
                                                                        privateSpaceAuthenticationActivity);
                                                        break;
                                                    }
                                                }
                                                privateSpaceAuthenticationActivity.finish();
                                                break;
                                        }
                                    }
                                });
        final int i2 = 1;
        this.mVerifyDeviceLock =
                (ActivityResultRegistry.AnonymousClass2)
                        registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback(
                                        this) { // from class:
                                                // com.android.settings.privatespace.PrivateSpaceAuthenticationActivity$$ExternalSyntheticLambda3
                                    public final /* synthetic */ PrivateSpaceAuthenticationActivity
                                            f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        PrivateSpaceAuthenticationActivity
                                                privateSpaceAuthenticationActivity = this.f$0;
                                        ActivityResult activityResult = (ActivityResult) obj;
                                        switch (i2) {
                                            case 0:
                                                int i22 =
                                                        PrivateSpaceAuthenticationActivity
                                                                .$r8$clinit;
                                                if (activityResult == null) {
                                                    privateSpaceAuthenticationActivity.getClass();
                                                    break;
                                                } else {
                                                    if (privateSpaceAuthenticationActivity
                                                                    .mKeyguardManager
                                                            == null) {
                                                        privateSpaceAuthenticationActivity
                                                                        .mKeyguardManager =
                                                                (KeyguardManager)
                                                                        privateSpaceAuthenticationActivity
                                                                                .getSystemService(
                                                                                        KeyguardManager
                                                                                                .class);
                                                    }
                                                    if (!privateSpaceAuthenticationActivity
                                                            .mKeyguardManager.isDeviceSecure()) {
                                                        privateSpaceAuthenticationActivity.finish();
                                                        break;
                                                    } else {
                                                        privateSpaceAuthenticationActivity
                                                                .onLockAuthentication(
                                                                        privateSpaceAuthenticationActivity);
                                                        break;
                                                    }
                                                }
                                            default:
                                                int i3 =
                                                        PrivateSpaceAuthenticationActivity
                                                                .$r8$clinit;
                                                if (activityResult != null) {
                                                    privateSpaceAuthenticationActivity.getClass();
                                                    if (activityResult.mResultCode == -1) {
                                                        privateSpaceAuthenticationActivity
                                                                .onLockAuthentication(
                                                                        privateSpaceAuthenticationActivity);
                                                        break;
                                                    }
                                                }
                                                privateSpaceAuthenticationActivity.finish();
                                                break;
                                        }
                                    }
                                });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Intent createConfirmDeviceCredentialIntent;
        super.onCreate(bundle);
        if (!Flags.allowPrivateProfile() || !android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            finish();
            return;
        }
        Intent intent = getIntent();
        String string = getString(R.string.menu_key_security);
        if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this)
                && ((isTaskRoot() || (intent.getFlags() & 268435456) != 0)
                        && intent.getAction() != null
                        && EmbeddedDeepLinkUtils.tryStartMultiPaneDeepLink(this, intent, string))) {
            finish();
            return;
        }
        ThemeHelper.trySetDynamicColor(this);
        this.mPrivateSpaceMaintainer = PrivateSpaceMaintainer.getInstance(getApplicationContext());
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
        }
        if (!this.mKeyguardManager.isDeviceSecure()) {
            Log.d(
                    "PrivateSpaceAuthCheck",
                    "Show prompt to set device lock before using private space feature");
            if (android.multiuser.Flags.showSetScreenLockDialog()) {
                startActivity(SetScreenLockDialogActivity.createBaseIntent(2));
                finish();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this, 2132084211);
            builder.setTitle(R.string.no_device_lock_title);
            builder.setMessage(R.string.no_device_lock_summary);
            final int i = 0;
            builder.setPositiveButton(
                    R.string.no_device_lock_action_label,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.privatespace.PrivateSpaceAuthenticationActivity$$ExternalSyntheticLambda0
                        public final /* synthetic */ PrivateSpaceAuthenticationActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            int i3 = i;
                            PrivateSpaceAuthenticationActivity privateSpaceAuthenticationActivity =
                                    this.f$0;
                            switch (i3) {
                                case 0:
                                    int i4 = PrivateSpaceAuthenticationActivity.$r8$clinit;
                                    privateSpaceAuthenticationActivity.getClass();
                                    Log.d(
                                            "PrivateSpaceAuthCheck",
                                            "Start activity to set new device lock");
                                    privateSpaceAuthenticationActivity.mSetDeviceLock.launch(
                                            new Intent("android.app.action.SET_NEW_PASSWORD"));
                                    break;
                                default:
                                    int i5 = PrivateSpaceAuthenticationActivity.$r8$clinit;
                                    privateSpaceAuthenticationActivity.finish();
                                    break;
                            }
                        }
                    });
            final int i2 = 1;
            builder.setNegativeButton(
                    R.string.no_device_lock_cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.privatespace.PrivateSpaceAuthenticationActivity$$ExternalSyntheticLambda0
                        public final /* synthetic */ PrivateSpaceAuthenticationActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            int i3 = i2;
                            PrivateSpaceAuthenticationActivity privateSpaceAuthenticationActivity =
                                    this.f$0;
                            switch (i3) {
                                case 0:
                                    int i4 = PrivateSpaceAuthenticationActivity.$r8$clinit;
                                    privateSpaceAuthenticationActivity.getClass();
                                    Log.d(
                                            "PrivateSpaceAuthCheck",
                                            "Start activity to set new device lock");
                                    privateSpaceAuthenticationActivity.mSetDeviceLock.launch(
                                            new Intent("android.app.action.SET_NEW_PASSWORD"));
                                    break;
                                default:
                                    int i5 = PrivateSpaceAuthenticationActivity.$r8$clinit;
                                    privateSpaceAuthenticationActivity.finish();
                                    break;
                            }
                        }
                    });
            builder.P.mOnCancelListener =
                    new DialogInterface
                            .OnCancelListener() { // from class:
                                                  // com.android.settings.privatespace.PrivateSpaceAuthenticationActivity$$ExternalSyntheticLambda2
                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            PrivateSpaceAuthenticationActivity privateSpaceAuthenticationActivity =
                                    PrivateSpaceAuthenticationActivity.this;
                            int i3 = PrivateSpaceAuthenticationActivity.$r8$clinit;
                            privateSpaceAuthenticationActivity.finish();
                        }
                    };
            builder.show();
            return;
        }
        if (bundle == null) {
            if (this.mPrivateSpaceMaintainer.doesPrivateSpaceExist()) {
                unlockAndLaunchPrivateSpaceSettings(this);
                return;
            }
            PrivateSpaceMaintainer privateSpaceMaintainer = this.mPrivateSpaceMaintainer;
            synchronized (privateSpaceMaintainer) {
                createConfirmDeviceCredentialIntent =
                        (privateSpaceMaintainer.doesPrivateSpaceExist()
                                        && privateSpaceMaintainer.mKeyguardManager.isDeviceSecure(
                                                privateSpaceMaintainer.mUserHandle.getIdentifier()))
                                ? privateSpaceMaintainer.mKeyguardManager
                                        .createConfirmDeviceCredentialIntent(
                                                null,
                                                null,
                                                privateSpaceMaintainer.mUserHandle.getIdentifier())
                                : privateSpaceMaintainer.mKeyguardManager
                                        .createConfirmDeviceCredentialIntent(null, null);
            }
            if (createConfirmDeviceCredentialIntent == null) {
                Log.e(
                        "PrivateSpaceAuthCheck",
                        "verifyCredentialIntent is null even though device lock is set");
                finish();
            } else {
                if (android.multiuser.Flags.usePrivateSpaceIconInBiometricPrompt()) {
                    createConfirmDeviceCredentialIntent.putExtra("custom_logo_res_id", 17304493);
                    createConfirmDeviceCredentialIntent.putExtra(
                            "custom_logo_description", getApplicationContext().getString(17042483));
                }
                this.mVerifyDeviceLock.launch(createConfirmDeviceCredentialIntent);
            }
        }
    }

    @VisibleForTesting
    public void onLockAuthentication(Context context) {
        if (this.mPrivateSpaceMaintainer.doesPrivateSpaceExist()) {
            unlockAndLaunchPrivateSpaceSettings(context);
        } else {
            startActivity(new Intent(context, (Class<?>) PrivateSpaceSetupActivity.class));
            finish();
        }
    }

    @VisibleForTesting
    public void setPrivateSpaceMaintainer(Injector injector) {
        injector.getClass();
        this.mPrivateSpaceMaintainer = PrivateSpaceMaintainer.getInstance(this);
    }

    public final void unlockAndLaunchPrivateSpaceSettings(Context context) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = PrivateSpaceDashboardFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mTransitionType = 1;
        launchRequest.mSourceMetricsCategory = 2040;
        if (this.mPrivateSpaceMaintainer.isPrivateSpaceLocked()) {
            ActivityOptions pendingIntentCreatorBackgroundActivityStartMode =
                    ActivityOptions.makeBasic()
                            .setPendingIntentCreatorBackgroundActivityStartMode(1);
            PrivateSpaceMaintainer privateSpaceMaintainer = this.mPrivateSpaceMaintainer;
            IntentSender intentSender =
                    PendingIntent.getActivity(
                                    context,
                                    0,
                                    subSettingLauncher
                                            .toIntent()
                                            .putExtra("extra_show_private_space_unlocked", true),
                                    67108864,
                                    pendingIntentCreatorBackgroundActivityStartMode.toBundle())
                            .getIntentSender();
            synchronized (privateSpaceMaintainer) {
                UserHandle userHandle = privateSpaceMaintainer.mUserHandle;
                if (userHandle != null) {
                    privateSpaceMaintainer.mUserManager.requestQuietModeEnabled(
                            false, userHandle, intentSender);
                }
            }
        } else {
            Log.i("PrivateSpaceAuthCheck", "Launch private space settings");
            subSettingLauncher.launch();
        }
        finish();
    }
}
