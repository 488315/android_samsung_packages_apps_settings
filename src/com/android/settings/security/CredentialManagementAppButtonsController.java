package com.android.settings.security;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.widget.ActionButtonsPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CredentialManagementAppButtonsController extends BasePreferenceController {
    private static final String TAG = "CredentialManagementApp";
    private final ExecutorService mExecutor;
    private Fragment mFragment;
    private final Handler mHandler;
    private boolean mHasCredentialManagerPackage;
    private final int mRemoveIcon;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class RemoveCredentialManagementAppDialog extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 1895;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2132084211);
            builder.setTitle(R.string.remove_credential_management_app_dialog_title);
            builder.setMessage(R.string.remove_credential_management_app_dialog_message);
            final int i = 0;
            builder.setPositiveButton(
                    R.string.remove_credential_management_app,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.security.CredentialManagementAppButtonsController$RemoveCredentialManagementAppDialog$$ExternalSyntheticLambda0
                        public final /* synthetic */ CredentialManagementAppButtonsController
                                        .RemoveCredentialManagementAppDialog
                                f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            int i3 = i;
                            CredentialManagementAppButtonsController
                                            .RemoveCredentialManagementAppDialog
                                    removeCredentialManagementAppDialog = this.f$0;
                            switch (i3) {
                                case 0:
                                    removeCredentialManagementAppDialog.getClass();
                                    Executors.newSingleThreadExecutor()
                                            .execute(
                                                    new CredentialManagementAppButtonsController$$ExternalSyntheticLambda1(
                                                            1,
                                                            removeCredentialManagementAppDialog));
                                    break;
                                default:
                                    removeCredentialManagementAppDialog.dismissInternal(
                                            false, false);
                                    break;
                            }
                        }
                    });
            final int i2 = 1;
            builder.setNegativeButton(
                    R.string.cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.security.CredentialManagementAppButtonsController$RemoveCredentialManagementAppDialog$$ExternalSyntheticLambda0
                        public final /* synthetic */ CredentialManagementAppButtonsController
                                        .RemoveCredentialManagementAppDialog
                                f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            int i3 = i2;
                            CredentialManagementAppButtonsController
                                            .RemoveCredentialManagementAppDialog
                                    removeCredentialManagementAppDialog = this.f$0;
                            switch (i3) {
                                case 0:
                                    removeCredentialManagementAppDialog.getClass();
                                    Executors.newSingleThreadExecutor()
                                            .execute(
                                                    new CredentialManagementAppButtonsController$$ExternalSyntheticLambda1(
                                                            1,
                                                            removeCredentialManagementAppDialog));
                                    break;
                                default:
                                    removeCredentialManagementAppDialog.dismissInternal(
                                            false, false);
                                    break;
                            }
                        }
                    });
            return builder.create();
        }
    }

    public CredentialManagementAppButtonsController(Context context, String str) {
        super(context, str);
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mHandler = new Handler(Looper.getMainLooper());
        if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
            this.mRemoveIcon = R.drawable.ic_redo_24;
        } else {
            this.mRemoveIcon = R.drawable.ic_undo_24;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: displayButtons, reason: merged with bridge method [inline-methods] */
    public void lambda$displayPreference$0(PreferenceScreen preferenceScreen) {
        if (this.mHasCredentialManagerPackage) {
            ActionButtonsPreference actionButtonsPreference =
                    (ActionButtonsPreference) preferenceScreen.findPreference(getPreferenceKey());
            actionButtonsPreference.setButton1Text(
                    R.string.uninstall_certs_credential_management_app);
            actionButtonsPreference.setButton1Icon(R.drawable.ic_upload);
            final int i = 0;
            actionButtonsPreference.setButton1OnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.security.CredentialManagementAppButtonsController$$ExternalSyntheticLambda3
                        public final /* synthetic */ CredentialManagementAppButtonsController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i2 = i;
                            CredentialManagementAppButtonsController
                                    credentialManagementAppButtonsController = this.f$0;
                            switch (i2) {
                                case 0:
                                    credentialManagementAppButtonsController
                                            .lambda$displayButtons$2(view);
                                    break;
                                default:
                                    credentialManagementAppButtonsController
                                            .lambda$displayButtons$3(view);
                                    break;
                            }
                        }
                    });
            actionButtonsPreference.setButton2Text(R.string.remove_credential_management_app);
            actionButtonsPreference.setButton2Icon(this.mRemoveIcon);
            final int i2 = 1;
            actionButtonsPreference.setButton2OnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.security.CredentialManagementAppButtonsController$$ExternalSyntheticLambda3
                        public final /* synthetic */ CredentialManagementAppButtonsController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i22 = i2;
                            CredentialManagementAppButtonsController
                                    credentialManagementAppButtonsController = this.f$0;
                            switch (i22) {
                                case 0:
                                    credentialManagementAppButtonsController
                                            .lambda$displayButtons$2(view);
                                    break;
                                default:
                                    credentialManagementAppButtonsController
                                            .lambda$displayButtons$3(view);
                                    break;
                            }
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayButtons$2(View view) {
        uninstallCertificates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayButtons$3(View view) {
        showRemoveCredentialManagementAppDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$1(PreferenceScreen preferenceScreen) {
        try {
            KeyChain.KeyChainConnection bind = KeyChain.bind(this.mContext);
            try {
                this.mHasCredentialManagerPackage = bind.getService().hasCredentialManagementApp();
                bind.close();
            } catch (Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException | InterruptedException unused) {
            Log.e(TAG, "Unable to display credential management app buttons");
        }
        this.mHandler.post(
                new CredentialManagementAppButtonsController$$ExternalSyntheticLambda0(
                        this, preferenceScreen, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uninstallCertificates$4() {
        try {
            KeyChain.KeyChainConnection bind = KeyChain.bind(this.mContext);
            try {
                IKeyChainService service = bind.getService();
                Iterator it = service.getCredentialManagementAppPolicy().getAliases().iterator();
                while (it.hasNext()) {
                    service.removeKeyPair((String) it.next());
                }
                bind.close();
            } catch (Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException | InterruptedException unused) {
            Log.e(TAG, "Unable to uninstall certificates");
        }
    }

    private void showRemoveCredentialManagementAppDialog() {
        new RemoveCredentialManagementAppDialog()
                .show(
                        this.mFragment.getParentFragmentManager(),
                        RemoveCredentialManagementAppDialog.class.getName());
    }

    private void uninstallCertificates() {
        this.mExecutor.execute(
                new CredentialManagementAppButtonsController$$ExternalSyntheticLambda1(0, this));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mExecutor.execute(
                new CredentialManagementAppButtonsController$$ExternalSyntheticLambda0(
                        this, preferenceScreen, 0));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setParentFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
