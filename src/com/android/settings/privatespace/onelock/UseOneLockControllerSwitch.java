package com.android.settings.privatespace.onelock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.privatespace.PrivateProfileContextHelperActivity;
import com.android.settings.privatespace.PrivateSpaceMaintainer;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.MainSwitchPreference;

import com.samsung.android.knox.accounts.HostAuth;
import com.sec.ims.ImsManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UseOneLockControllerSwitch extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public LockscreenCredential mCurrentDevicePassword;
    public LockscreenCredential mCurrentProfilePassword;
    public final SettingsPreferenceFragment mHost;
    public final LockPatternUtils mLockPatternUtils;
    public final String mPreferenceKey;
    public final int mProfileUserId;
    public MainSwitchPreference mUnifyProfile;
    public final UserHandle mUserHandle;
    public final UserManager mUserManager;

    /* renamed from: $r8$lambda$NsaaL-Vh1HXHoAmkymvczD_hKoU, reason: not valid java name */
    public static /* synthetic */ void m996$r8$lambda$NsaaLVh1HXHoAmkymvczD_hKoU(
            UseOneLockControllerSwitch useOneLockControllerSwitch) {
        useOneLockControllerSwitch.getClass();
        Intent intent =
                new Intent(
                        useOneLockControllerSwitch.mContext,
                        (Class<?>) PrivateProfileContextHelperActivity.class);
        intent.putExtra(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, 1);
        ((Activity) useOneLockControllerSwitch.mContext)
                .startActivityForResultAsUser(
                        intent, 2, null, useOneLockControllerSwitch.mUserHandle);
    }

    public UseOneLockControllerSwitch(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment) {
        super(context);
        this.mHost = settingsPreferenceFragment;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mLockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
        UserHandle privateProfileHandle =
                PrivateSpaceMaintainer.getInstance(context).getPrivateProfileHandle();
        this.mUserHandle = privateProfileHandle;
        this.mProfileUserId =
                privateProfileHandle != null ? privateProfileHandle.getIdentifier() : -1;
        this.mCurrentDevicePassword = LockscreenCredential.createNone();
        this.mCurrentProfilePassword = LockscreenCredential.createNone();
        this.mPreferenceKey = "private_lock_unification";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mUnifyProfile =
                (MainSwitchPreference) preferenceScreen.findPreference(this.mPreferenceKey);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPreferenceKey;
    }

    public final boolean handleActivityResult(int i, int i2, Intent intent) {
        if (i != 2 || i2 != -1 || intent == null) {
            if (i != 1 || i2 != -1 || intent == null) {
                return false;
            }
            this.mCurrentProfilePassword = intent.getParcelableExtra(HostAuth.PASSWORD);
            unifyLocks$1();
            return true;
        }
        this.mCurrentDevicePassword = intent.getParcelableExtra(HostAuth.PASSWORD);
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.USER_ID", this.mProfileUserId);
        bundle.putParcelable(HostAuth.PASSWORD, this.mCurrentDevicePassword);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = this.mHost.getMetricsCategory();
        launchRequest.mArguments = bundle;
        launchRequest.mTransitionType = 1;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Context context = this.mContext;
        UserManager userManager = this.mUserManager;
        int i = this.mProfileUserId;
        if (Utils.startQuietModeDialogIfNecessary(context, userManager, i)) {
            return false;
        }
        if (((Boolean) obj).booleanValue()) {
            SettingsPreferenceFragment settingsPreferenceFragment = this.mHost;
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(
                            settingsPreferenceFragment.getActivity(), settingsPreferenceFragment);
            builder.mRequestCode = 1;
            builder.mReturnCredentials = true;
            builder.mUserId = i;
            if (!builder.show()) {
                unifyLocks$1();
            }
        } else if (this.mUserHandle == null) {
            Log.e("UseOneLockSwitch", "Private profile user handle is not expected to be null");
            this.mUnifyProfile.setChecked(true);
        } else {
            final int i2 = 0;
            AlertDialog.Builder positiveButton =
                    new AlertDialog.Builder(this.mContext)
                            .setTitle(R.string.private_space_new_lock_title)
                            .setMessage(R.string.private_space_new_lock_message)
                            .setPositiveButton(
                                    R.string.private_space_set_lock_label,
                                    new DialogInterface.OnClickListener(
                                            this) { // from class:
                                                    // com.android.settings.privatespace.onelock.UseOneLockControllerSwitch$$ExternalSyntheticLambda0
                                        public final /* synthetic */ UseOneLockControllerSwitch f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i3) {
                                            int i4 = i2;
                                            UseOneLockControllerSwitch useOneLockControllerSwitch =
                                                    this.f$0;
                                            switch (i4) {
                                                case 0:
                                                    UseOneLockControllerSwitch
                                                            .m996$r8$lambda$NsaaLVh1HXHoAmkymvczD_hKoU(
                                                                    useOneLockControllerSwitch);
                                                    break;
                                                default:
                                                    useOneLockControllerSwitch.mUnifyProfile
                                                            .setChecked(true);
                                                    dialogInterface.dismiss();
                                                    break;
                                            }
                                        }
                                    });
            final int i3 = 1;
            positiveButton
                    .setNegativeButton(
                            R.string.private_space_cancel_label,
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.privatespace.onelock.UseOneLockControllerSwitch$$ExternalSyntheticLambda0
                                public final /* synthetic */ UseOneLockControllerSwitch f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(
                                        DialogInterface dialogInterface, int i32) {
                                    int i4 = i3;
                                    UseOneLockControllerSwitch useOneLockControllerSwitch =
                                            this.f$0;
                                    switch (i4) {
                                        case 0:
                                            UseOneLockControllerSwitch
                                                    .m996$r8$lambda$NsaaLVh1HXHoAmkymvczD_hKoU(
                                                            useOneLockControllerSwitch);
                                            break;
                                        default:
                                            useOneLockControllerSwitch.mUnifyProfile.setChecked(
                                                    true);
                                            dialogInterface.dismiss();
                                            break;
                                    }
                                }
                            })
                    .setOnCancelListener(
                            new DialogInterface
                                    .OnCancelListener() { // from class:
                                                          // com.android.settings.privatespace.onelock.UseOneLockControllerSwitch$$ExternalSyntheticLambda2
                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    UseOneLockControllerSwitch.this.mUnifyProfile.setChecked(true);
                                    dialogInterface.dismiss();
                                }
                            })
                    .show();
        }
        return true;
    }

    public final void unifyLocks$1() {
        this.mLockPatternUtils.setSeparateProfileChallengeEnabled(
                this.mProfileUserId, false, this.mCurrentProfilePassword);
        LockscreenCredential lockscreenCredential = this.mCurrentDevicePassword;
        if (lockscreenCredential != null) {
            lockscreenCredential.zeroize();
            this.mCurrentDevicePassword = null;
        }
        LockscreenCredential lockscreenCredential2 = this.mCurrentProfilePassword;
        if (lockscreenCredential2 != null) {
            lockscreenCredential2.zeroize();
            this.mCurrentProfilePassword = null;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mUnifyProfile != null) {
            this.mUnifyProfile.setChecked(
                    !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mProfileUserId));
        }
    }
}
