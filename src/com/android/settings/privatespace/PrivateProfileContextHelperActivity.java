package com.android.settings.privatespace;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupdesign.util.ThemeHelper;
import com.sec.ims.ImsManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateProfileContextHelperActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityResultRegistry.AnonymousClass2 mAddAccountToPrivateProfile;
    public final ActivityResultRegistry.AnonymousClass2 mSetNewPrivateProfileLock;

    public PrivateProfileContextHelperActivity() {
        final int i = 0;
        registerForActivityResult(
                new ActivityResultContracts$StartActivityForResult(0),
                new ActivityResultCallback(
                        this) { // from class:
                                // com.android.settings.privatespace.PrivateProfileContextHelperActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ PrivateProfileContextHelperActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.activity.result.ActivityResultCallback
                    public final void onActivityResult(Object obj) {
                        PrivateProfileContextHelperActivity privateProfileContextHelperActivity =
                                this.f$0;
                        ActivityResult activityResult = (ActivityResult) obj;
                        switch (i) {
                            case 0:
                                int i2 = PrivateProfileContextHelperActivity.$r8$clinit;
                                privateProfileContextHelperActivity.getClass();
                                if (activityResult == null) {
                                    Log.i(
                                            "PrivateSpaceHelperAct",
                                            "private space account login result null");
                                    privateProfileContextHelperActivity.setResult(0);
                                    privateProfileContextHelperActivity.finish();
                                    return;
                                }
                                int i3 = activityResult.mResultCode;
                                if (i3 == -1) {
                                    Log.i(
                                            "PrivateSpaceHelperAct",
                                            "private space account login success");
                                } else if (i3 == 1) {
                                    Log.i(
                                            "PrivateSpaceHelperAct",
                                            "private space account login skipped");
                                } else {
                                    Log.i(
                                            "PrivateSpaceHelperAct",
                                            "private space account login failed");
                                }
                                privateProfileContextHelperActivity.setResult(
                                        (i3 == -1 || i3 == 1) ? -1 : 0);
                                privateProfileContextHelperActivity.finish();
                                return;
                            default:
                                int i4 = PrivateProfileContextHelperActivity.$r8$clinit;
                                privateProfileContextHelperActivity.getClass();
                                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                                if (featureFactoryImpl == null) {
                                    throw new UnsupportedOperationException(
                                            "No feature factory configured");
                                }
                                LockPatternUtils lockPatternUtils =
                                        featureFactoryImpl
                                                .getSecurityFeatureProvider()
                                                .getLockPatternUtils(
                                                        privateProfileContextHelperActivity);
                                if (activityResult == null
                                        || !lockPatternUtils.isSeparateProfileChallengeEnabled(
                                                privateProfileContextHelperActivity.getUserId())) {
                                    Log.i(
                                            "PrivateSpaceHelperAct",
                                            "separate private space lock not setup");
                                    privateProfileContextHelperActivity.setResult(0);
                                } else {
                                    Log.i(
                                            "PrivateSpaceHelperAct",
                                            "separate private space lock setup success");
                                    privateProfileContextHelperActivity.setResult(-1);
                                }
                                privateProfileContextHelperActivity.finish();
                                return;
                        }
                    }
                });
        final int i2 = 1;
        this.mSetNewPrivateProfileLock =
                (ActivityResultRegistry.AnonymousClass2)
                        registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback(this) { // from class:
                                    // com.android.settings.privatespace.PrivateProfileContextHelperActivity$$ExternalSyntheticLambda0
                                    public final /* synthetic */ PrivateProfileContextHelperActivity
                                            f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        PrivateProfileContextHelperActivity
                                                privateProfileContextHelperActivity = this.f$0;
                                        ActivityResult activityResult = (ActivityResult) obj;
                                        switch (i2) {
                                            case 0:
                                                int i22 =
                                                        PrivateProfileContextHelperActivity
                                                                .$r8$clinit;
                                                privateProfileContextHelperActivity.getClass();
                                                if (activityResult == null) {
                                                    Log.i(
                                                            "PrivateSpaceHelperAct",
                                                            "private space account login result"
                                                                + " null");
                                                    privateProfileContextHelperActivity.setResult(
                                                            0);
                                                    privateProfileContextHelperActivity.finish();
                                                    return;
                                                }
                                                int i3 = activityResult.mResultCode;
                                                if (i3 == -1) {
                                                    Log.i(
                                                            "PrivateSpaceHelperAct",
                                                            "private space account login success");
                                                } else if (i3 == 1) {
                                                    Log.i(
                                                            "PrivateSpaceHelperAct",
                                                            "private space account login skipped");
                                                } else {
                                                    Log.i(
                                                            "PrivateSpaceHelperAct",
                                                            "private space account login failed");
                                                }
                                                privateProfileContextHelperActivity.setResult(
                                                        (i3 == -1 || i3 == 1) ? -1 : 0);
                                                privateProfileContextHelperActivity.finish();
                                                return;
                                            default:
                                                int i4 =
                                                        PrivateProfileContextHelperActivity
                                                                .$r8$clinit;
                                                privateProfileContextHelperActivity.getClass();
                                                FeatureFactoryImpl featureFactoryImpl =
                                                        FeatureFactoryImpl._factory;
                                                if (featureFactoryImpl == null) {
                                                    throw new UnsupportedOperationException(
                                                            "No feature factory configured");
                                                }
                                                LockPatternUtils lockPatternUtils =
                                                        featureFactoryImpl
                                                                .getSecurityFeatureProvider()
                                                                .getLockPatternUtils(
                                                                        privateProfileContextHelperActivity);
                                                if (activityResult == null
                                                        || !lockPatternUtils
                                                                .isSeparateProfileChallengeEnabled(
                                                                        privateProfileContextHelperActivity
                                                                                .getUserId())) {
                                                    Log.i(
                                                            "PrivateSpaceHelperAct",
                                                            "separate private space lock not"
                                                                + " setup");
                                                    privateProfileContextHelperActivity.setResult(
                                                            0);
                                                } else {
                                                    Log.i(
                                                            "PrivateSpaceHelperAct",
                                                            "separate private space lock setup"
                                                                + " success");
                                                    privateProfileContextHelperActivity.setResult(
                                                            -1);
                                                }
                                                privateProfileContextHelperActivity.finish();
                                                return;
                                        }
                                    }
                                });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            setTheme(SetupWizardUtils.getTheme(this, getIntent()));
            ThemeHelper.trySetDynamicColor(this);
            super.onCreate(bundle);
            if (bundle == null) {
                int intExtra = getIntent().getIntExtra(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, -1);
                if (intExtra != 2) {
                    if (intExtra == 1) {
                        Intent intent = new Intent("android.app.action.SET_NEW_PASSWORD");
                        intent.putExtra("android.app.extra.PASSWORD_COMPLEXITY", 65536);
                        intent.putExtra("for_fingerprint_only", true);
                        intent.putExtra(
                                "choose_lock_setup_screen_title",
                                R.string.private_space_lock_setup_title);
                        intent.putExtra(
                                "choose_lock_setup_screen_description",
                                R.string.private_space_lock_setup_description);
                        this.mSetNewPrivateProfileLock.launch(intent);
                        return;
                    }
                    return;
                }
                setContentView(R.layout.private_space_wait_screen);
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                PrivateSpaceLoginFeatureProviderImpl privateSpaceLoginFeatureProviderImpl =
                        (PrivateSpaceLoginFeatureProviderImpl)
                                featureFactoryImpl.privateSpaceLoginFeatureProvider$delegate
                                        .getValue();
                UserHandle privateProfileHandle =
                        PrivateSpaceMaintainer.getInstance(this).getPrivateProfileHandle();
                if (privateProfileHandle == null) {
                    Log.w("PrivateSpaceHelperAct", "Private profile user handle is null");
                    setResult(0);
                    finish();
                } else {
                    createContextAsUser(privateProfileHandle, 0);
                    privateSpaceLoginFeatureProviderImpl.getClass();
                    setResult(-1);
                    finish();
                }
            }
        }
    }
}
