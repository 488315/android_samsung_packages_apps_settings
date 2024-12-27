package com.android.settings;

import android.R;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.TrustedCredentialsFragment.AliasOperation;

import com.samsung.android.settings.logging.LoggingHelper;

import java.util.function.IntConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class TrustedCredentialsDialogBuilder extends AlertDialog.Builder {
    public final DialogEventHandler mDialogEventHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DelegateInterface {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DialogEventHandler
            implements DialogInterface.OnShowListener, View.OnClickListener {
        public static int SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_OK;
        public final Activity mActivity;
        public final DelegateInterface mDelegate;
        public AlertDialog mDialog;
        public final DevicePolicyManager mDpm;
        public boolean mNeedsApproval;
        public Button mNegativeButton;
        public Button mPositiveButton;
        public final LinearLayout mRootContainer;
        public final UserManager mUserManager;
        public int mCurrentCertIndex = -1;
        public TrustedCredentialsFragment.CertHolder[] mCertHolders =
                new TrustedCredentialsFragment.CertHolder[0];
        public View mCurrentCertLayout = null;

        public DialogEventHandler(
                FragmentActivity fragmentActivity, DelegateInterface delegateInterface) {
            this.mActivity = fragmentActivity;
            this.mDpm =
                    (DevicePolicyManager)
                            fragmentActivity.getSystemService(DevicePolicyManager.class);
            this.mUserManager = (UserManager) fragmentActivity.getSystemService(UserManager.class);
            this.mDelegate = delegateInterface;
            LinearLayout linearLayout = new LinearLayout(fragmentActivity);
            this.mRootContainer = linearLayout;
            linearLayout.setOrientation(1);
            SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_OK = 4517;
        }

        public final TrustedCredentialsFragment.CertHolder getCurrentCertInfo() {
            int i = this.mCurrentCertIndex;
            TrustedCredentialsFragment.CertHolder[] certHolderArr = this.mCertHolders;
            if (i < certHolderArr.length) {
                return certHolderArr[i];
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:58:0x0192  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x01d8  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0201  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0207  */
        /* JADX WARN: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:91:0x01e4  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x0194  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void nextOrDismiss() {
            /*
                Method dump skipped, instructions count: 584
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.TrustedCredentialsDialogBuilder.DialogEventHandler.nextOrDismiss():void");
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (view == this.mPositiveButton) {
                if (this.mNeedsApproval) {
                    onClickTrust();
                    return;
                }
                TrustedCredentialsFragment.CertHolder currentCertInfo = getCurrentCertInfo();
                if (currentCertInfo != null && currentCertInfo.isSystemCert()) {
                    LoggingHelper.insertFlowLogging(SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_OK);
                }
                nextOrDismiss();
                return;
            }
            if (view == this.mNegativeButton) {
                final TrustedCredentialsFragment.CertHolder currentCertInfo2 = getCurrentCertInfo();
                DialogInterface.OnClickListener onClickListener =
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.TrustedCredentialsDialogBuilder.DialogEventHandler.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                DelegateInterface delegateInterface =
                                        DialogEventHandler.this.mDelegate;
                                TrustedCredentialsFragment.CertHolder certHolder = currentCertInfo2;
                                TrustedCredentialsFragment trustedCredentialsFragment =
                                        (TrustedCredentialsFragment) delegateInterface;
                                trustedCredentialsFragment.getClass();
                                trustedCredentialsFragment.new AliasOperation(certHolder)
                                        .execute(new Void[0]);
                                DialogEventHandler.this.nextOrDismiss();
                            }
                        };
                if (currentCertInfo2.isSystemCert()) {
                    onClickListener.onClick(null, -1);
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
                builder.setMessage(R.string.trusted_credentials_remove_confirmation);
                builder.setPositiveButton(R.string.ok, onClickListener);
                builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
                builder.show();
            }
        }

        public final void onClickTrust() {
            TrustedCredentialsFragment.CertHolder currentCertInfo = getCurrentCertInfo();
            DelegateInterface delegateInterface = this.mDelegate;
            int i = currentCertInfo.mProfileId;
            IntConsumer intConsumer =
                    new IntConsumer() { // from class:
                                        // com.android.settings.TrustedCredentialsDialogBuilder$DialogEventHandler$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            TrustedCredentialsDialogBuilder.DialogEventHandler dialogEventHandler =
                                    TrustedCredentialsDialogBuilder.DialogEventHandler.this;
                            if (dialogEventHandler.mDialog.isShowing()
                                    && dialogEventHandler.mNeedsApproval
                                    && dialogEventHandler.getCurrentCertInfo() != null
                                    && dialogEventHandler.getCurrentCertInfo().mProfileId == i2) {
                                dialogEventHandler.onClickTrust();
                            }
                        }
                    };
            TrustedCredentialsFragment trustedCredentialsFragment =
                    (TrustedCredentialsFragment) delegateInterface;
            boolean z = false;
            if (!trustedCredentialsFragment.mConfirmedCredentialUsers.contains(
                    Integer.valueOf(i))) {
                Intent createConfirmDeviceCredentialIntent =
                        trustedCredentialsFragment.mKeyguardManager
                                .createConfirmDeviceCredentialIntent(null, null, i);
                if (createConfirmDeviceCredentialIntent != null) {
                    trustedCredentialsFragment.mConfirmingCredentialUser = i;
                    trustedCredentialsFragment.startActivityForResult(
                            createConfirmDeviceCredentialIntent, 1);
                    z = true;
                }
                if (z) {
                    trustedCredentialsFragment.mConfirmingCredentialListener = intConsumer;
                }
            }
            if (z) {
                return;
            }
            this.mDpm.approveCaCert(currentCertInfo.mAlias, currentCertInfo.mProfileId, true);
            nextOrDismiss();
        }

        @Override // android.content.DialogInterface.OnShowListener
        public final void onShow(DialogInterface dialogInterface) {
            nextOrDismiss();
        }
    }

    public TrustedCredentialsDialogBuilder(
            FragmentActivity fragmentActivity, DelegateInterface delegateInterface) {
        super(fragmentActivity);
        DialogEventHandler dialogEventHandler =
                new DialogEventHandler(fragmentActivity, delegateInterface);
        this.mDialogEventHandler = dialogEventHandler;
        setTitle(17043110);
        setView(dialogEventHandler.mRootContainer);
        setPositiveButton(
                R.string.trusted_credentials_trust_label, (DialogInterface.OnClickListener) null);
        setNegativeButton(R.string.ok, (DialogInterface.OnClickListener) null);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    public final AlertDialog create() {
        AlertDialog create = super.create();
        DialogEventHandler dialogEventHandler = this.mDialogEventHandler;
        create.setOnShowListener(dialogEventHandler);
        dialogEventHandler.mDialog = create;
        return create;
    }
}
