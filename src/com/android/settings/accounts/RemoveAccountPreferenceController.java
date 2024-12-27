package com.android.settings.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.widget.SecRoundButtonView;

import java.io.IOException;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RemoveAccountPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, View.OnClickListener {
    public Account mAccount;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final Fragment mParentFragment;
    public LayoutPreference mRemoveAccountPreference;
    public UserHandle mUserHandle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConfirmRemoveAccountDialog extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        public Account mAccount;
        public AlertDialog mDialog;
        public Fragment mFrag;
        public SecRoundButtonView mRemoveAccountButton;
        public UserHandle mUserHandle;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.accounts.RemoveAccountPreferenceController$ConfirmRemoveAccountDialog$1, reason: invalid class name */
        public final class AnonymousClass1 implements View.OnLayoutChangeListener {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ InstrumentedDialogFragment this$0;

            public /* synthetic */ AnonymousClass1(
                    InstrumentedDialogFragment instrumentedDialogFragment, int i) {
                this.$r8$classId = i;
                this.this$0 = instrumentedDialogFragment;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(
                    View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Fragment fragment;
                Fragment fragment2;
                switch (this.$r8$classId) {
                    case 0:
                        AlertDialog alertDialog =
                                ((ConfirmRemoveAccountDialog) this.this$0).mDialog;
                        if (alertDialog != null
                                && alertDialog.isShowing()
                                && (fragment = ((ConfirmRemoveAccountDialog) this.this$0).mFrag)
                                        != null
                                && fragment.getView() != null) {
                            ConfirmRemoveAccountDialog confirmRemoveAccountDialog =
                                    (ConfirmRemoveAccountDialog) this.this$0;
                            confirmRemoveAccountDialog.mRemoveAccountButton =
                                    (SecRoundButtonView)
                                            confirmRemoveAccountDialog
                                                    .mFrag
                                                    .getView()
                                                    .findViewById(R.id.button);
                            ConfirmRemoveAccountDialog confirmRemoveAccountDialog2 =
                                    (ConfirmRemoveAccountDialog) this.this$0;
                            SecRoundButtonView secRoundButtonView =
                                    confirmRemoveAccountDialog2.mRemoveAccountButton;
                            if (secRoundButtonView != null) {
                                confirmRemoveAccountDialog2.mDialog.semSetAnchor(
                                        secRoundButtonView);
                                break;
                            }
                        }
                        break;
                    default:
                        AlertDialog alertDialog2 =
                                ((RemoveAccountFailureDialog) this.this$0).mDialog;
                        if (alertDialog2 != null
                                && alertDialog2.isShowing()
                                && (fragment2 =
                                                ((RemoveAccountFailureDialog) this.this$0)
                                                        .mFragment)
                                        != null
                                && fragment2.getView() != null) {
                            RemoveAccountFailureDialog removeAccountFailureDialog =
                                    (RemoveAccountFailureDialog) this.this$0;
                            removeAccountFailureDialog.mRemoveAccountButton =
                                    (SecRoundButtonView)
                                            removeAccountFailureDialog
                                                    .mFragment
                                                    .getView()
                                                    .findViewById(R.id.button);
                            RemoveAccountFailureDialog removeAccountFailureDialog2 =
                                    (RemoveAccountFailureDialog) this.this$0;
                            SecRoundButtonView secRoundButtonView2 =
                                    removeAccountFailureDialog2.mRemoveAccountButton;
                            if (secRoundButtonView2 != null) {
                                removeAccountFailureDialog2.mDialog.semSetAnchor(
                                        secRoundButtonView2);
                                break;
                            }
                        }
                        break;
                }
            }
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 585;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            if (this.mAccount == null) {
                return;
            }
            FragmentActivity activity = getTargetFragment().getActivity();
            if (!"com.osp.app.signin".equals(this.mAccount.type)) {
                AccountManager.get(activity)
                        .removeAccountAsUser(
                                this.mAccount,
                                activity,
                                new AccountManagerCallback() { // from class:
                                    // com.android.settings.accounts.RemoveAccountPreferenceController$ConfirmRemoveAccountDialog$$ExternalSyntheticLambda1
                                    @Override // android.accounts.AccountManagerCallback
                                    public final void run(
                                            AccountManagerFuture accountManagerFuture) {
                                        RemoveAccountPreferenceController.ConfirmRemoveAccountDialog
                                                confirmRemoveAccountDialog =
                                                        RemoveAccountPreferenceController
                                                                .ConfirmRemoveAccountDialog.this;
                                        FragmentActivity activity2 =
                                                confirmRemoveAccountDialog
                                                        .getTargetFragment()
                                                        .getActivity();
                                        if (activity2 == null || activity2.isFinishing()) {
                                            Log.w(
                                                    "RemoveAccountPrefController",
                                                    "Activity is no longer alive, skipping"
                                                        + " results");
                                            return;
                                        }
                                        boolean z = true;
                                        try {
                                            z =
                                                    true
                                                            ^ ((Bundle)
                                                                            accountManagerFuture
                                                                                    .getResult())
                                                                    .getBoolean("booleanResult");
                                        } catch (AuthenticatorException
                                                | OperationCanceledException
                                                | IOException e) {
                                            Log.w(
                                                    "RemoveAccountPrefController",
                                                    "Remove account error: " + e);
                                        }
                                        AbsAdapter$$ExternalSyntheticOutline0.m(
                                                "failed: ", "RemoveAccountPrefController", z);
                                        if (!z) {
                                            activity2.finish();
                                            return;
                                        }
                                        Fragment targetFragment =
                                                confirmRemoveAccountDialog.getTargetFragment();
                                        if (targetFragment.isAdded()) {
                                            RemoveAccountPreferenceController
                                                            .RemoveAccountFailureDialog
                                                    removeAccountFailureDialog =
                                                            new RemoveAccountPreferenceController
                                                                    .RemoveAccountFailureDialog();
                                            removeAccountFailureDialog.setTargetFragment(
                                                    targetFragment, 0);
                                            try {
                                                removeAccountFailureDialog.show(
                                                        targetFragment.getFragmentManager(),
                                                        "removeAccountFailed");
                                            } catch (IllegalStateException e2) {
                                                Log.w(
                                                        "RemoveAccountPrefController",
                                                        "Can't show RemoveAccountFailureDialog. "
                                                                + e2.getMessage());
                                            }
                                        }
                                    }
                                },
                                null,
                                this.mUserHandle);
                return;
            }
            UserHandle userHandle = this.mUserHandle;
            Intent intent =
                    new Intent("com.samsung.android.samsungaccount.action.REMOVE_CONFIRM_VIEW");
            intent.setPackage("com.osp.app.signin");
            intent.putExtra("client_id", "s5d189ajvs");
            intent.putExtra("account_mode", "ACCOUNT_DELETE_FROM_SETTING");
            activity.startActivityAsUser(intent, userHandle);
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Bundle arguments = getArguments();
            this.mFrag = getTargetFragment();
            this.mAccount = (Account) arguments.getParcelable("account");
            this.mUserHandle = (UserHandle) arguments.getParcelable("android.intent.extra.USER");
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            final FragmentActivity activity = getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.really_remove_account_title);
            builder.setMessage(R.string.really_remove_account_message);
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            builder.setPositiveButton(R.string.remove, this);
            this.mDialog = builder.create();
            Fragment fragment = this.mFrag;
            if (fragment != null && fragment.getView() != null) {
                SecRoundButtonView secRoundButtonView =
                        (SecRoundButtonView) this.mFrag.getView().findViewById(R.id.button);
                this.mRemoveAccountButton = secRoundButtonView;
                if (secRoundButtonView != null) {
                    this.mDialog.semSetAnchor(secRoundButtonView);
                }
                this.mFrag.getView().addOnLayoutChangeListener(new AnonymousClass1(this, 0));
            }
            AlertDialog alertDialog = this.mDialog;
            if (alertDialog != null) {
                alertDialog.setOnShowListener(
                        new DialogInterface
                                .OnShowListener() { // from class:
                                                    // com.android.settings.accounts.RemoveAccountPreferenceController$ConfirmRemoveAccountDialog$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnShowListener
                            public final void onShow(DialogInterface dialogInterface) {
                                ((AlertDialog) dialogInterface)
                                        .getButton(-1)
                                        .setTextColor(
                                                activity.getColor(
                                                        R.color
                                                                .sec_biometrics_dialog_remove_btn_color));
                            }
                        });
            }
            return this.mDialog;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RemoveAccountFailureDialog extends InstrumentedDialogFragment {
        public AlertDialog mDialog;
        public Fragment mFragment;
        public SecRoundButtonView mRemoveAccountButton;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 586;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mFragment = getTargetFragment();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.remove_account_label);
            builder.P.mMessage =
                    ((DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class))
                            .getResources()
                            .getString(
                                    "Settings.REMOVE_ACCOUNT_FAILED_ADMIN_RESTRICTION",
                                    new Supplier() { // from class:
                                                     // com.android.settings.accounts.RemoveAccountPreferenceController$RemoveAccountFailureDialog$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return RemoveAccountPreferenceController
                                                    .RemoveAccountFailureDialog.this
                                                    .getString(R.string.remove_account_failed);
                                        }
                                    });
            builder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
            this.mDialog = builder.create();
            Fragment fragment = this.mFragment;
            if (fragment != null && fragment.getView() != null) {
                SecRoundButtonView secRoundButtonView =
                        (SecRoundButtonView) this.mFragment.getView().findViewById(R.id.button);
                this.mRemoveAccountButton = secRoundButtonView;
                if (secRoundButtonView != null) {
                    this.mDialog.semSetAnchor(secRoundButtonView);
                }
                this.mFragment
                        .getView()
                        .addOnLayoutChangeListener(
                                new ConfirmRemoveAccountDialog.AnonymousClass1(this, 1));
            }
            return this.mDialog;
        }
    }

    public RemoveAccountPreferenceController(Context context, Fragment fragment) {
        super(context);
        this.mParentFragment = fragment;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("remove_account");
        this.mRemoveAccountPreference = layoutPreference;
        ((SecRoundButtonView) layoutPreference.mRootView.findViewById(R.id.button))
                .setOnClickListener(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "remove_account";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced;
        SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                this.mMetricsFeatureProvider;
        LayoutPreference layoutPreference = this.mRemoveAccountPreference;
        Fragment fragment = this.mParentFragment;
        settingsMetricsFeatureProvider.getClass();
        settingsMetricsFeatureProvider.logClickedPreference(
                layoutPreference, MetricsFeatureProvider.getMetricsCategory(fragment));
        UserHandle userHandle = this.mUserHandle;
        if (userHandle != null
                && (checkIfRestrictionEnforced =
                                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                        this.mContext,
                                        userHandle.getIdentifier(),
                                        "no_modify_accounts"))
                        != null) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    this.mContext, checkIfRestrictionEnforced);
            return;
        }
        Fragment fragment2 = this.mParentFragment;
        Account account = this.mAccount;
        UserHandle userHandle2 = this.mUserHandle;
        if (fragment2.isAdded()) {
            ConfirmRemoveAccountDialog confirmRemoveAccountDialog =
                    new ConfirmRemoveAccountDialog();
            Bundle bundle = new Bundle();
            bundle.putParcelable("account", account);
            bundle.putParcelable("android.intent.extra.USER", userHandle2);
            confirmRemoveAccountDialog.setArguments(bundle);
            confirmRemoveAccountDialog.setTargetFragment(fragment2, 0);
            confirmRemoveAccountDialog.show(fragment2.getFragmentManager(), "confirmRemoveAccount");
        }
    }
}
