package com.android.settings.password;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BiometricFragment extends InstrumentedFragment {
    public boolean mAuthenticating;
    public BiometricPrompt mBiometricPrompt;
    public CancellationSignal mCancellationSignal;
    public BiometricPrompt.AuthenticationCallback mClientCallback;
    public Executor mClientExecutor;
    public boolean mIsInternal;
    public int mUserId;
    public final AnonymousClass1 mAuthenticationCallback = new AnonymousClass1();
    public final AnonymousClass2 mNegativeButtonListener =
            new DialogInterface
                    .OnClickListener() { // from class:
                                         // com.android.settings.password.BiometricFragment.2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    BiometricFragment.this.mAuthenticationCallback.onAuthenticationError(
                            13,
                            BiometricFragment.this
                                    .getArguments()
                                    .getParcelable("prompt_info")
                                    .getNegativeButtonText());
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.password.BiometricFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BiometricPrompt.AuthenticationCallback {
        public AnonymousClass1() {}

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public final void onAuthenticationError(final int i, final CharSequence charSequence) {
            BiometricFragment biometricFragment = BiometricFragment.this;
            biometricFragment.mAuthenticating = false;
            biometricFragment.mClientExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.settings.password.BiometricFragment$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricFragment.AnonymousClass1 anonymousClass1 =
                                    BiometricFragment.AnonymousClass1.this;
                            BiometricFragment.this.mClientCallback.onAuthenticationError(
                                    i, charSequence);
                        }
                    });
            BiometricFragment.this.cleanup();
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public final void onAuthenticationFailed() {
            BiometricFragment.this.mClientExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.settings.password.BiometricFragment$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricFragment.this.mClientCallback.onAuthenticationFailed();
                        }
                    });
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public final void onAuthenticationSucceeded(
                final BiometricPrompt.AuthenticationResult authenticationResult) {
            BiometricFragment biometricFragment = BiometricFragment.this;
            biometricFragment.mAuthenticating = false;
            biometricFragment.mClientExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.settings.password.BiometricFragment$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricFragment.AnonymousClass1 anonymousClass1 =
                                    BiometricFragment.AnonymousClass1.this;
                            BiometricFragment.this.mClientCallback.onAuthenticationSucceeded(
                                    authenticationResult);
                        }
                    });
            BiometricFragment.this.cleanup();
        }

        public final void onSystemEvent(final int i) {
            BiometricFragment.this.mClientExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.settings.password.BiometricFragment$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BiometricFragment.AnonymousClass1 anonymousClass1 =
                                    BiometricFragment.AnonymousClass1.this;
                            BiometricFragment.this.mClientCallback.onSystemEvent(i);
                        }
                    });
        }
    }

    public final void cleanup() {
        if (getActivity() != null) {
            FragmentManagerImpl supportFragmentManager = getActivity().getSupportFragmentManager();
            supportFragmentManager.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.remove(this);
            backStackRecord.commitInternal(true);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1585;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        setRetainInstance(true);
        Bundle arguments = getArguments();
        PromptInfo parcelable = arguments.getParcelable("prompt_info");
        BiometricPrompt.Builder componentNameForConfirmDeviceCredentialActivity =
                new BiometricPrompt.Builder(getContext())
                        .setTitle(parcelable.getTitle())
                        .setUseDefaultTitle()
                        .setAllowedAuthenticators(parcelable.getAuthenticators())
                        .setSubtitle(parcelable.getSubtitle())
                        .setDescription(parcelable.getDescription())
                        .setTextForDeviceCredential(
                                parcelable.getDeviceCredentialTitle(),
                                parcelable.getDeviceCredentialSubtitle(),
                                parcelable.getDeviceCredentialDescription())
                        .setConfirmationRequired(parcelable.isConfirmationRequested())
                        .setDisallowBiometricsIfPolicyExists(
                                parcelable.isDisallowBiometricsIfPolicyExists())
                        .setShowEmergencyCallButton(parcelable.isShowEmergencyCallButton())
                        .setReceiveSystemEvents(true)
                        .setComponentNameForConfirmDeviceCredentialActivity(
                                (ComponentName) arguments.getParcelable("calling_activity"));
        componentNameForConfirmDeviceCredentialActivity.semSetPrivilegedFlag(8);
        if (parcelable.getLogoRes() != 0) {
            componentNameForConfirmDeviceCredentialActivity.setLogoRes(parcelable.getLogoRes());
        }
        String logoDescription = parcelable.getLogoDescription();
        if (!TextUtils.isEmpty(logoDescription)) {
            componentNameForConfirmDeviceCredentialActivity.setLogoDescription(logoDescription);
        }
        BiometricPrompt.Builder allowBackgroundAuthentication =
                (Flags.allowPrivateProfile()
                                && android.multiuser.Flags.enablePrivateSpaceFeatures()
                                && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace())
                        ? componentNameForConfirmDeviceCredentialActivity
                                .setAllowBackgroundAuthentication(
                                        true,
                                        parcelable.shouldUseParentProfileForDeviceCredential())
                        : componentNameForConfirmDeviceCredentialActivity
                                .setAllowBackgroundAuthentication(true);
        if (parcelable.isUseDefaultSubtitle()) {
            allowBackgroundAuthentication.setUseDefaultSubtitle();
        }
        if ((parcelable.getAuthenticators() & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0) {
            allowBackgroundAuthentication.setNegativeButton(
                    parcelable.getNegativeButtonText(),
                    getContext().getMainExecutor(),
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.password.BiometricFragment$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            BiometricFragment.this.mAuthenticationCallback.onAuthenticationError(
                                    10, null);
                        }
                    });
        }
        if (SemPersonaManager.isKnoxId(this.mUserId)
                && this.mUserId != UserHandle.myUserId()
                && this.mIsInternal) {
            if (KnoxUtils.isMultifactorEnabledForWork(getContext(), this.mUserId)
                    || (Rune.isSamsungDexMode(getContext())
                            && Rune.isSamsungDexOnPCMode(getContext()))) {
                allowBackgroundAuthentication.setAllowedAuthenticators(15);
                allowBackgroundAuthentication.setDeviceCredentialAllowed(false);
                allowBackgroundAuthentication.setNegativeButton(
                        getResources().getString(R.string.common_cancel),
                        this.mClientExecutor,
                        this.mNegativeButtonListener);
            }
            boolean isFingerprintEnabled =
                    KnoxUtils.isFingerprintEnabled(getContext(), this.mUserId);
            int i2 = isFingerprintEnabled;
            if (KnoxUtils.isFaceEnabled(getContext(), this.mUserId)) {
                i2 = (isFingerprintEnabled ? 1 : 0) | 2;
            }
            allowBackgroundAuthentication.semSetBiometricType(i2);
            if (i2 != 0) {
                allowBackgroundAuthentication.setConfirmationRequired(false);
                i = 36;
            } else {
                i = 32;
            }
            if (KnoxUtils.isMultifactorEnabledForWork(getContext(), this.mUserId)) {
                i |= 64;
            }
            allowBackgroundAuthentication.semSetPrivilegedFlag(i);
        }
        this.mBiometricPrompt = allowBackgroundAuthentication.build();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mCancellationSignal == null) {
            this.mAuthenticating = true;
            CancellationSignal cancellationSignal = new CancellationSignal();
            this.mCancellationSignal = cancellationSignal;
            this.mBiometricPrompt.authenticateUser(
                    cancellationSignal,
                    this.mClientExecutor,
                    this.mAuthenticationCallback,
                    this.mUserId);
        }
    }
}
