package com.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImeAwareEditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl;
import com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.TwoTargetPreference;

import com.google.android.setupdesign.util.DeviceHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintSettings extends SubSettings {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class FingerprintPreference extends TwoTargetPreference {
        public View mDeleteView;
        public Fingerprint mFingerprint;
        public final OnDeleteClickListener mOnDeleteClickListener;
        public View mView;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public interface OnDeleteClickListener {}

        public FingerprintPreference(Context context, OnDeleteClickListener onDeleteClickListener) {
            super(context);
            this.mOnDeleteClickListener = onDeleteClickListener;
        }

        @Override // com.android.settingslib.widget.TwoTargetPreference
        public final int getSecondTargetResId() {
            return R.layout.preference_widget_delete;
        }

        @Override // com.android.settingslib.widget.TwoTargetPreference,
                  // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            View view = preferenceViewHolder.itemView;
            this.mView = view;
            View findViewById = view.findViewById(R.id.delete_button);
            this.mDeleteView = findViewById;
            if (this.mFingerprint != null) {
                findViewById.setContentDescription(
                        ((Object) this.mDeleteView.getContentDescription())
                                + " "
                                + this.mFingerprint.getName().toString());
            }
            this.mDeleteView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintPreference$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            FingerprintSettings.FingerprintPreference fingerprintPreference =
                                    FingerprintSettings.FingerprintPreference.this;
                            FingerprintSettings.FingerprintPreference.OnDeleteClickListener
                                    onDeleteClickListener =
                                            fingerprintPreference.mOnDeleteClickListener;
                            if (onDeleteClickListener != null) {
                                FingerprintSettings.FingerprintSettingsFragment
                                        fingerprintSettingsFragment =
                                                (FingerprintSettings.FingerprintSettingsFragment)
                                                        onDeleteClickListener;
                                boolean z =
                                        fingerprintSettingsFragment
                                                        .mFingerprintManager
                                                        .getEnrolledFingerprints(
                                                                fingerprintSettingsFragment.mUserId)
                                                        .size()
                                                > 1;
                                Parcelable parcelable = fingerprintPreference.mFingerprint;
                                if (!z) {
                                    FingerprintSettings.FingerprintSettingsFragment
                                                    .ConfirmLastDeleteDialog
                                            confirmLastDeleteDialog =
                                                    new FingerprintSettings
                                                            .FingerprintSettingsFragment
                                                            .ConfirmLastDeleteDialog();
                                    boolean isManagedProfile =
                                            UserManager.get(
                                                            fingerprintSettingsFragment
                                                                    .getContext())
                                                    .isManagedProfile(
                                                            fingerprintSettingsFragment.mUserId);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("fingerprint", parcelable);
                                    bundle.putBoolean("isProfileChallengeUser", isManagedProfile);
                                    confirmLastDeleteDialog.setArguments(bundle);
                                    confirmLastDeleteDialog.setTargetFragment(
                                            fingerprintSettingsFragment, 0);
                                    confirmLastDeleteDialog.show(
                                            fingerprintSettingsFragment.getFragmentManager(),
                                            FingerprintSettings.FingerprintSettingsFragment
                                                    .ConfirmLastDeleteDialog.class
                                                    .getName());
                                    return;
                                }
                                if (fingerprintSettingsFragment.mRemovalSidecar.inProgress()) {
                                    Log.d(
                                            "FingerprintSettings",
                                            "Fingerprint delete in progress, skipping");
                                    return;
                                }
                                FingerprintSettings.FingerprintSettingsFragment
                                                .DeleteFingerprintDialog
                                        deleteFingerprintDialog =
                                                new FingerprintSettings.FingerprintSettingsFragment
                                                        .DeleteFingerprintDialog();
                                Bundle bundle2 = new Bundle();
                                bundle2.putParcelable("fingerprint", parcelable);
                                deleteFingerprintDialog.setArguments(bundle2);
                                deleteFingerprintDialog.setTargetFragment(
                                        fingerprintSettingsFragment, 0);
                                deleteFingerprintDialog.show(
                                        fingerprintSettingsFragment.getFragmentManager(),
                                        FingerprintSettings.FingerprintSettingsFragment
                                                .DeleteFingerprintDialog.class
                                                .getName());
                            }
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class FingerprintSettingsFragment extends DashboardFragment
            implements Preference.OnPreferenceChangeListener,
                    FingerprintPreference.OnDeleteClickListener {
        static final int ADD_FINGERPRINT_REQUEST = 10;
        static final int CHOOSE_LOCK_GENERIC_REQUEST = 102;
        static final String KEY_FINGERPRINT_ADD = "key_fingerprint_add";
        static final String KEY_REQUIRE_SCREEN_ON_TO_AUTH =
                "security_settings_require_screen_on_to_auth";
        public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
                new AnonymousClass1(R.xml.security_settings_fingerprint);
        public Preference mAddFingerprintPreference;
        public FingerprintAuthenticateSidecar mAuthenticateSidecar;
        public long mChallenge;
        public List mControllers;
        public FingerprintManager mFingerprintManager;
        public PreferenceCategory mFingerprintUnlockCategory;
        public FingerprintUnlockCategoryController mFingerprintUnlockCategoryPreferenceController;
        public PreferenceCategory mFingerprintUnlockFooter;
        public FingerprintUpdater mFingerprintUpdater;
        public PreferenceCategory mFingerprintsEnrolledCategory;
        public HashMap mFingerprintsRenaming;
        public Drawable mHighlightDrawable;
        public boolean mInFingerprintLockout;
        public boolean mIsEnrolling;
        public boolean mLaunchedConfirm;
        public FingerprintRemoveSidecar mRemovalSidecar;
        public RestrictedSwitchPreference mRequireScreenOnToAuthPreference;
        public FingerprintSettingsRequireScreenOnToAuthPreferenceController
                mRequireScreenOnToAuthPreferenceController;
        public List mSensorProperties;
        public byte[] mToken;
        public int mUserId;
        public boolean mHasFirstEnrolled = true;
        public final List mFooterColumns = new ArrayList();
        public final AnonymousClass2 mAuthenticateListener = new AnonymousClass2();
        public final AnonymousClass3 mRemovalListener = new AnonymousClass3();
        public final AnonymousClass4 mHandler =
                new Handler() { // from class:
                                // com.android.settings.biometrics.fingerprint.FingerprintSettings.FingerprintSettingsFragment.4
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        boolean isAvailable;
                        final View view;
                        FragmentActivity activity;
                        FragmentActivity activity2;
                        int i = message.what;
                        FingerprintSettingsFragment fingerprintSettingsFragment =
                                FingerprintSettingsFragment.this;
                        if (i == 1000) {
                            int i2 = message.arg1;
                            fingerprintSettingsFragment.getClass();
                            String genKey = FingerprintSettingsFragment.genKey(i2);
                            Preference findPreference =
                                    fingerprintSettingsFragment.findPreference(genKey);
                            if (findPreference == null) {
                                MotionLayout$$ExternalSyntheticOutline0.m(
                                        "Can't find preference to remove: ",
                                        genKey,
                                        "FingerprintSettings");
                            } else if (!fingerprintSettingsFragment
                                    .getPreferenceScreen()
                                    .removePreference(findPreference)) {
                                MotionLayout$$ExternalSyntheticOutline0.m(
                                        "Failed to remove preference with key ",
                                        genKey,
                                        "FingerprintSettings");
                            }
                            fingerprintSettingsFragment.updateAddPreference();
                            if (fingerprintSettingsFragment.isSfps()
                                    && fingerprintSettingsFragment.mFingerprintUnlockCategory
                                                    .isVisible()
                                            != (isAvailable =
                                                    fingerprintSettingsFragment
                                                            .mFingerprintUnlockCategoryPreferenceController
                                                            .isAvailable())) {
                                fingerprintSettingsFragment.mFingerprintUnlockCategory.setVisible(
                                        isAvailable);
                            }
                            fingerprintSettingsFragment.updatePreferences();
                            return;
                        }
                        if (i == 1001) {
                            int i3 = message.arg1;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    FingerprintSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                            fingerprintSettingsFragment.getClass();
                            FingerprintPreference fingerprintPreference =
                                    (FingerprintPreference)
                                            fingerprintSettingsFragment.findPreference(
                                                    FingerprintSettingsFragment.genKey(i3));
                            if (fingerprintSettingsFragment.mHighlightDrawable == null
                                    && (activity = fingerprintSettingsFragment.getActivity())
                                            != null) {
                                fingerprintSettingsFragment.mHighlightDrawable =
                                        activity.getDrawable(R.drawable.preference_highlight);
                            }
                            Drawable drawable = fingerprintSettingsFragment.mHighlightDrawable;
                            if (drawable != null
                                    && fingerprintPreference != null
                                    && (view = fingerprintPreference.mView) != null) {
                                drawable.setHotspot(view.getWidth() / 2, view.getHeight() / 2);
                                view.setBackground(drawable);
                                view.setPressed(true);
                                view.setPressed(false);
                                fingerprintSettingsFragment.mHandler.postDelayed(
                                        new Runnable() { // from class:
                                                         // com.android.settings.biometrics.fingerprint.FingerprintSettings.FingerprintSettingsFragment.5
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                view.setBackground(null);
                                            }
                                        },
                                        500L);
                            }
                            fingerprintSettingsFragment.retryFingerprint();
                            return;
                        }
                        if (i != 1003) {
                            return;
                        }
                        int i4 = message.arg1;
                        CharSequence charSequence = (CharSequence) message.obj;
                        fingerprintSettingsFragment.getClass();
                        if (i4 != 5) {
                            if (i4 == 7) {
                                fingerprintSettingsFragment.mInFingerprintLockout = true;
                                if (!fingerprintSettingsFragment.mHandler.hasCallbacks(
                                        fingerprintSettingsFragment.mFingerprintLockoutReset)) {
                                    fingerprintSettingsFragment.mHandler.postDelayed(
                                            fingerprintSettingsFragment.mFingerprintLockoutReset,
                                            30000L);
                                }
                            } else if (i4 == 9) {
                                fingerprintSettingsFragment.mInFingerprintLockout = true;
                            } else if (i4 == 10) {
                                return;
                            }
                            if (fingerprintSettingsFragment.mInFingerprintLockout
                                    && (activity2 = fingerprintSettingsFragment.getActivity())
                                            != null) {
                                Toast.makeText(activity2, charSequence, 0).show();
                            }
                            fingerprintSettingsFragment.retryFingerprint();
                        }
                    }
                };
        public final AnonymousClass6 mFingerprintLockoutReset =
                new Runnable() { // from class:
                                 // com.android.settings.biometrics.fingerprint.FingerprintSettings.FingerprintSettingsFragment.6
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintSettingsFragment fingerprintSettingsFragment =
                                FingerprintSettingsFragment.this;
                        fingerprintSettingsFragment.mInFingerprintLockout = false;
                        fingerprintSettingsFragment.retryFingerprint();
                    }
                };

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$1, reason: invalid class name */
        public final class AnonymousClass1 extends BaseSearchIndexProvider {
            @Override // com.android.settings.search.BaseSearchIndexProvider
            public final List createPreferenceControllers(Context context) {
                return FingerprintSettingsFragment.createThePreferenceControllers(context);
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$2, reason: invalid class name */
        public final class AnonymousClass2 implements FingerprintAuthenticateSidecar.Listener {
            public AnonymousClass2() {}
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$3, reason: invalid class name */
        public final class AnonymousClass3 implements FingerprintRemoveSidecar.Listener {
            public AnonymousClass3() {}

            public final void updateDialog() {
                RenameDialog renameDialog =
                        (RenameDialog)
                                FingerprintSettingsFragment.this
                                        .getFragmentManager()
                                        .findFragmentByTag(RenameDialog.class.getName());
                if (renameDialog != null) {
                    renameDialog.mDeleteInProgress = false;
                    AlertDialog alertDialog = renameDialog.mAlertDialog;
                    if (alertDialog != null) {
                        alertDialog.getButton(-2).setEnabled(true);
                    }
                }
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$7, reason: invalid class name */
        public final class AnonymousClass7 implements InputFilter {
            @Override // android.text.InputFilter
            public final CharSequence filter(
                    CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                while (i < i2) {
                    if (charSequence.charAt(i) < ' ') {
                        return ApnSettings.MVNO_NONE;
                    }
                    i++;
                }
                return null;
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class ConfirmLastDeleteDialog extends InstrumentedDialogFragment {
            public Fingerprint mFp;

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$ConfirmLastDeleteDialog$1, reason: invalid class name */
            public final class AnonymousClass1 implements DialogInterface.OnClickListener {
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            @Override // com.android.settingslib.core.instrumentation.Instrumentable
            public final int getMetricsCategory() {
                return 571;
            }

            @Override // androidx.fragment.app.DialogFragment
            public final Dialog onCreateDialog(Bundle bundle) {
                this.mFp = getArguments().getParcelable("fingerprint");
                boolean z = getArguments().getBoolean("isProfileChallengeUser");
                String string = getString(R.string.fingerprint_delete_title, this.mFp.getName());
                final String string2 =
                        getString(R.string.fingerprint_v2_delete_message, this.mFp.getName());
                DevicePolicyManager devicePolicyManager =
                        (DevicePolicyManager)
                                getContext().getSystemService(DevicePolicyManager.class);
                String str =
                        z
                                ? "Settings.WORK_PROFILE_FINGERPRINT_LAST_DELETE_MESSAGE"
                                : PeripheralBarcodeConstants.Symbology.UNDEFINED;
                final int i =
                        z
                                ? R.string.fingerprint_last_delete_message_profile_challenge
                                : R.string.fingerprint_last_delete_message;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertController.AlertParams alertParams = builder.P;
                alertParams.mTitle = string;
                alertParams.mMessage =
                        devicePolicyManager
                                .getResources()
                                .getString(
                                        str,
                                        new Supplier() { // from class:
                                                         // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$ConfirmLastDeleteDialog$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                FingerprintSettings.FingerprintSettingsFragment
                                                                .ConfirmLastDeleteDialog
                                                        confirmLastDeleteDialog =
                                                                FingerprintSettings
                                                                        .FingerprintSettingsFragment
                                                                        .ConfirmLastDeleteDialog
                                                                        .this;
                                                String str2 = string2;
                                                int i2 = i;
                                                confirmLastDeleteDialog.getClass();
                                                return str2
                                                        + "\n\n"
                                                        + confirmLastDeleteDialog
                                                                .getContext()
                                                                .getString(i2);
                                            }
                                        });
                builder.setPositiveButton(
                        R.string.security_settings_fingerprint_enroll_dialog_delete,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.biometrics.fingerprint.FingerprintSettings.FingerprintSettingsFragment.ConfirmLastDeleteDialog.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                ((FingerprintSettingsFragment)
                                                ConfirmLastDeleteDialog.this.getTargetFragment())
                                        .deleteFingerPrint(ConfirmLastDeleteDialog.this.mFp);
                                dialogInterface.dismiss();
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new AnonymousClass1());
                return builder.create();
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class DeleteFingerprintDialog extends InstrumentedDialogFragment
                implements DialogInterface.OnClickListener {
            public Fingerprint mFp;

            @Override // com.android.settingslib.core.instrumentation.Instrumentable
            public final int getMetricsCategory() {
                return 570;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                if (i == -1) {
                    int biometricId = this.mFp.getBiometricId();
                    Log.v("FingerprintSettings", "Removing fpId=" + biometricId);
                    this.mMetricsFeatureProvider.action(
                            getContext(),
                            IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList,
                            biometricId);
                    ((FingerprintSettingsFragment) getTargetFragment()).deleteFingerPrint(this.mFp);
                }
            }

            @Override // androidx.fragment.app.DialogFragment
            public final Dialog onCreateDialog(Bundle bundle) {
                Fingerprint parcelable = getArguments().getParcelable("fingerprint");
                this.mFp = parcelable;
                String string = getString(R.string.fingerprint_delete_title, parcelable.getName());
                String string2 =
                        getString(R.string.fingerprint_v2_delete_message, this.mFp.getName());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertController.AlertParams alertParams = builder.P;
                alertParams.mTitle = string;
                alertParams.mMessage = string2;
                builder.setPositiveButton(
                        R.string.security_settings_fingerprint_enroll_dialog_delete, this);
                builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
                return builder.create();
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class FooterColumn {
            public CharSequence mTitle = null;
            public CharSequence mLearnMoreOverrideText = null;
            public View.OnClickListener mLearnMoreClickListener = null;
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class RenameDialog extends InstrumentedDialogFragment {
            public AlertDialog mAlertDialog;
            public boolean mDeleteInProgress;
            public ImeAwareEditText mDialogTextField;
            public DialogInterface.OnDismissListener mDismissListener;
            public Fingerprint mFp;

            @Override // com.android.settingslib.core.instrumentation.Instrumentable
            public final int getMetricsCategory() {
                return 570;
            }

            @Override // androidx.fragment.app.DialogFragment,
                      // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                DialogInterface.OnDismissListener onDismissListener = this.mDismissListener;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(dialogInterface);
                }
            }

            @Override // androidx.fragment.app.DialogFragment
            public final Dialog onCreateDialog(Bundle bundle) {
                final String str;
                final int i;
                this.mFp = getArguments().getParcelable("fingerprint");
                final int i2 = -1;
                if (bundle != null) {
                    str = bundle.getString("fingerName");
                    int i3 = bundle.getInt("startSelection", -1);
                    i = bundle.getInt("endSelection", -1);
                    i2 = i3;
                } else {
                    str = null;
                    i = -1;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(R.layout.fingerprint_rename_dialog);
                builder.setPositiveButton(
                        R.string.security_settings_fingerprint_enroll_dialog_ok,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.biometrics.fingerprint.FingerprintSettings.FingerprintSettingsFragment.RenameDialog.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                String editable =
                                        RenameDialog.this.mDialogTextField.getText().toString();
                                CharSequence name = RenameDialog.this.mFp.getName();
                                if (!TextUtils.equals(editable, name)) {
                                    Log.d(
                                            "FingerprintSettings",
                                            "rename " + ((Object) name) + " to " + editable);
                                    RenameDialog renameDialog = RenameDialog.this;
                                    renameDialog.mMetricsFeatureProvider.action(
                                            renameDialog.getContext(),
                                            254,
                                            RenameDialog.this.mFp.getBiometricId());
                                    FingerprintSettingsFragment fingerprintSettingsFragment =
                                            (FingerprintSettingsFragment)
                                                    RenameDialog.this.getTargetFragment();
                                    int biometricId = RenameDialog.this.mFp.getBiometricId();
                                    fingerprintSettingsFragment.mFingerprintManager.rename(
                                            biometricId,
                                            fingerprintSettingsFragment.mUserId,
                                            editable);
                                    if (!TextUtils.isEmpty(editable)) {
                                        fingerprintSettingsFragment.mFingerprintsRenaming.put(
                                                Integer.valueOf(biometricId), editable);
                                    }
                                    fingerprintSettingsFragment.updatePreferences();
                                }
                                DialogInterface.OnDismissListener onDismissListener =
                                        RenameDialog.this.mDismissListener;
                                if (onDismissListener != null) {
                                    onDismissListener.onDismiss(dialogInterface);
                                }
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog create = builder.create();
                this.mAlertDialog = create;
                create.setOnShowListener(
                        new DialogInterface
                                .OnShowListener() { // from class:
                                                    // com.android.settings.biometrics.fingerprint.FingerprintSettings.FingerprintSettingsFragment.RenameDialog.2
                            @Override // android.content.DialogInterface.OnShowListener
                            public final void onShow(DialogInterface dialogInterface) {
                                int i4;
                                RenameDialog renameDialog = RenameDialog.this;
                                renameDialog.mDialogTextField =
                                        renameDialog.mAlertDialog.findViewById(
                                                R.id.fingerprint_rename_field);
                                CharSequence charSequence = str;
                                if (charSequence == null) {
                                    charSequence = RenameDialog.this.mFp.getName();
                                }
                                RenameDialog.this.mDialogTextField.setText(charSequence);
                                ImeAwareEditText imeAwareEditText =
                                        RenameDialog.this.mDialogTextField;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        FingerprintSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                                imeAwareEditText.setFilters(
                                        new InputFilter[] {new AnonymousClass7()});
                                int i5 = i2;
                                if (i5 == -1 || (i4 = i) == -1) {
                                    RenameDialog.this.mDialogTextField.selectAll();
                                } else {
                                    RenameDialog.this.mDialogTextField.setSelection(i5, i4);
                                }
                                RenameDialog renameDialog2 = RenameDialog.this;
                                if (renameDialog2.mDeleteInProgress) {
                                    renameDialog2.mAlertDialog.getButton(-2).setEnabled(false);
                                }
                                RenameDialog.this.mDialogTextField.requestFocus();
                                RenameDialog.this.mDialogTextField.scheduleShowSoftInput();
                            }
                        });
                return this.mAlertDialog;
            }

            @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
            public final void onSaveInstanceState(Bundle bundle) {
                super.onSaveInstanceState(bundle);
                ImeAwareEditText imeAwareEditText = this.mDialogTextField;
                if (imeAwareEditText != null) {
                    bundle.putString("fingerName", imeAwareEditText.getText().toString());
                    bundle.putInt("startSelection", this.mDialogTextField.getSelectionStart());
                    bundle.putInt("endSelection", this.mDialogTextField.getSelectionEnd());
                }
            }
        }

        public static List createThePreferenceControllers(Context context) {
            ArrayList arrayList = new ArrayList();
            FingerprintManager fingerprintManagerOrNull =
                    Utils.getFingerprintManagerOrNull(context);
            if (fingerprintManagerOrNull == null
                    || !fingerprintManagerOrNull.isHardwareDetected()) {
                return null;
            }
            if (fingerprintManagerOrNull.isPowerbuttonFps()) {
                arrayList.add(
                        new FingerprintUnlockCategoryController(
                                context, "security_settings_fingerprint_unlock_category"));
                arrayList.add(
                        new FingerprintSettingsRequireScreenOnToAuthPreferenceController(
                                context, KEY_REQUIRE_SCREEN_ON_TO_AUTH));
            }
            arrayList.add(
                    new FingerprintsEnrolledCategoryPreferenceController(
                            context, "security_settings_fingerprints_enrolled"));
            return arrayList;
        }

        public static String genKey(int i) {
            return SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "key_fingerprint_item_");
        }

        public final void addFirstFingerprint(Long l) {
            Intent intent = new Intent();
            intent.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    FeatureFlagUtils.isEnabled(getActivity(), "settings_biometrics2_enrollment")
                            ? FingerprintEnrollmentActivity.InternalActivity.class.getName()
                            : FingerprintEnrollIntroductionInternal.class.getName());
            intent.putExtra("from_settings_summary", true);
            intent.putExtra("page_transition_type", 1);
            intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
            if (l != null) {
                intent.putExtra("gk_pw_handle", l.longValue());
            } else {
                intent.putExtra("hw_auth_token", this.mToken);
                intent.putExtra("challenge", this.mChallenge);
            }
            startActivityForResult(intent, 11);
        }

        @Override // com.android.settings.dashboard.DashboardFragment
        public final List createPreferenceControllers(Context context) {
            if (!FingerprintSettings.isFingerprintHardwareDetected(context)) {
                Log.e("FingerprintSettings", "Fingerprint hardware is not detected");
                this.mControllers = Collections.emptyList();
                return null;
            }
            List createThePreferenceControllers = createThePreferenceControllers(context);
            if (isSfps()) {
                Iterator it = ((ArrayList) createThePreferenceControllers).iterator();
                while (it.hasNext()) {
                    AbstractPreferenceController abstractPreferenceController =
                            (AbstractPreferenceController) it.next();
                    if (abstractPreferenceController.getPreferenceKey()
                            == "security_settings_fingerprint_unlock_category") {
                        this.mFingerprintUnlockCategoryPreferenceController =
                                (FingerprintUnlockCategoryController) abstractPreferenceController;
                    } else if (abstractPreferenceController.getPreferenceKey()
                            == KEY_REQUIRE_SCREEN_ON_TO_AUTH) {
                        this.mRequireScreenOnToAuthPreferenceController =
                                (FingerprintSettingsRequireScreenOnToAuthPreferenceController)
                                        abstractPreferenceController;
                    }
                }
            }
            this.mControllers = createThePreferenceControllers;
            return createThePreferenceControllers;
        }

        public void deleteFingerPrint(Fingerprint fingerprint) {
            FingerprintRemoveSidecar fingerprintRemoveSidecar = this.mRemovalSidecar;
            int i = this.mUserId;
            if (fingerprintRemoveSidecar.mFingerprintRemoving != null) {
                Log.e("FingerprintRemoveSidecar", "Remove already in progress");
            } else {
                fingerprintRemoveSidecar.mFingerprintRemoving = fingerprint;
                FingerprintUpdater fingerprintUpdater =
                        fingerprintRemoveSidecar.mFingerprintUpdater;
                fingerprintUpdater.mFingerprintManager.remove(
                        fingerprint,
                        i,
                        new FingerprintUpdater.NotifyingRemovalCallback(
                                fingerprintUpdater.mContext,
                                fingerprintRemoveSidecar.mRemoveCallback));
            }
            Preference findPreference = findPreference(genKey(fingerprint.getBiometricId()));
            if (findPreference != null) {
                findPreference.setEnabled(false);
            }
            updateAddPreference();
        }

        @Override // com.android.settings.dashboard.DashboardFragment
        /* renamed from: getLogTag */
        public final String getTAG() {
            return "FingerprintSettings";
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 49;
        }

        @Override // com.android.settings.dashboard.DashboardFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment
        public final int getPreferenceScreenResId() {
            return R.xml.security_settings_fingerprint;
        }

        public final boolean isSfps() {
            FingerprintManager fingerprintManagerOrNull =
                    Utils.getFingerprintManagerOrNull(getActivity());
            this.mFingerprintManager = fingerprintManagerOrNull;
            if (fingerprintManagerOrNull == null) {
                return false;
            }
            List sensorPropertiesInternal = fingerprintManagerOrNull.getSensorPropertiesInternal();
            this.mSensorProperties = sensorPropertiesInternal;
            Iterator it = sensorPropertiesInternal.iterator();
            while (it.hasNext()) {
                if (((FingerprintSensorPropertiesInternal) it.next()).isAnySidefpsType()) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.settings.dashboard.DashboardFragment,
                  // androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, final Intent intent) {
            super.onActivityResult(i, i2, intent);
            if (i == 101 || i == 102) {
                this.mLaunchedConfirm = false;
                if (i2 != 1 && i2 != -1) {
                    Log.d("FingerprintSettings", "Password not confirmed");
                    finish();
                    return;
                }
                if (!BiometricUtils.containsGatekeeperPasswordHandle(intent)) {
                    Log.d("FingerprintSettings", "Data null or GK PW missing");
                    finish();
                    return;
                } else {
                    if (this.mHasFirstEnrolled || this.mIsEnrolling) {
                        this.mFingerprintManager.generateChallenge(
                                this.mUserId,
                                new FingerprintManager
                                        .GenerateChallengeCallback() { // from class:
                                                                       // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$$ExternalSyntheticLambda3
                                    public final void onChallengeGenerated(int i3, int i4, long j) {
                                        byte[] gatekeeperHAT;
                                        FingerprintSettings.FingerprintSettingsFragment
                                                fingerprintSettingsFragment =
                                                        FingerprintSettings
                                                                .FingerprintSettingsFragment.this;
                                        Intent intent2 = intent;
                                        BaseSearchIndexProvider baseSearchIndexProvider =
                                                FingerprintSettings.FingerprintSettingsFragment
                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                        FragmentActivity activity =
                                                fingerprintSettingsFragment.getActivity();
                                        if (activity == null || activity.isFinishing()) {
                                            Log.w(
                                                    "FingerprintSettings",
                                                    "activity detach or finishing");
                                            return;
                                        }
                                        LockPatternUtils lockPatternUtils =
                                                new LockPatternUtils(activity);
                                        int i5 = fingerprintSettingsFragment.mUserId;
                                        if (!GatekeeperPasswordProvider
                                                .containsGatekeeperPasswordHandle(intent2)) {
                                            throw new IllegalStateException(
                                                    "Gatekeeper Password is missing!!");
                                        }
                                        VerifyCredentialResponse verifyGatekeeperPasswordHandle =
                                                lockPatternUtils.verifyGatekeeperPasswordHandle(
                                                        intent2.getLongExtra("gk_pw_handle", 0L),
                                                        j,
                                                        i5);
                                        if (verifyGatekeeperPasswordHandle.isMatched()) {
                                            gatekeeperHAT =
                                                    verifyGatekeeperPasswordHandle
                                                            .getGatekeeperHAT();
                                        } else {
                                            Log.e(
                                                    "GatekeeperPasswordProvider",
                                                    "Unable to request Gatekeeper HAT");
                                            gatekeeperHAT = null;
                                        }
                                        fingerprintSettingsFragment.mToken = gatekeeperHAT;
                                        fingerprintSettingsFragment.mChallenge = j;
                                        if (GatekeeperPasswordProvider
                                                .containsGatekeeperPasswordHandle(intent2)) {
                                            lockPatternUtils.removeGatekeeperPasswordHandle(
                                                    intent2.getLongExtra("gk_pw_handle", 0L));
                                            Log.d("GatekeeperPasswordProvider", "Removed handle");
                                        }
                                        fingerprintSettingsFragment.updateAddPreference();
                                    }
                                });
                        return;
                    }
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        activity.overridePendingTransition(
                                R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
                    }
                    this.mIsEnrolling = true;
                    addFirstFingerprint(
                            Long.valueOf(BiometricUtils.getGatekeeperPasswordHandle(intent)));
                    return;
                }
            }
            if (i == 10) {
                this.mIsEnrolling = false;
                if (i2 == 3) {
                    FragmentActivity activity2 = getActivity();
                    activity2.setResult(i2);
                    activity2.finish();
                    return;
                }
                return;
            }
            if (i == 11) {
                if (i2 != 1) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i2,
                            "Add first fingerprint, fail or null data, result:",
                            "FingerprintSettings");
                    if (i2 == 3) {
                        setResult(i2);
                    }
                    finish();
                    return;
                }
                if (this.mToken == null && intent != null) {
                    this.mToken = intent.getByteArrayExtra("hw_auth_token");
                }
                if (this.mToken == null) {
                    Log.w("FingerprintSettings", "Add first fingerprint, null token");
                    finish();
                    return;
                }
                if (this.mChallenge == -1 && intent != null) {
                    this.mChallenge = intent.getLongExtra("challenge", -1L);
                }
                if (this.mChallenge == -1) {
                    Log.w("FingerprintSettings", "Add first fingerprint, invalid challenge");
                    finish();
                } else {
                    this.mIsEnrolling = false;
                    this.mHasFirstEnrolled = true;
                    updateAddPreference();
                }
            }
        }

        @Override // com.android.settings.dashboard.DashboardFragment,
                  // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            final FragmentActivity activity = getActivity();
            FingerprintManager fingerprintManagerOrNull =
                    Utils.getFingerprintManagerOrNull(activity);
            this.mFingerprintManager = fingerprintManagerOrNull;
            this.mFingerprintUpdater = new FingerprintUpdater(activity, fingerprintManagerOrNull);
            this.mSensorProperties = fingerprintManagerOrNull.getSensorPropertiesInternal();
            this.mToken = getIntent().getByteArrayExtra("hw_auth_token");
            this.mChallenge = activity.getIntent().getLongExtra("challenge", -1L);
            FingerprintAuthenticateSidecar fingerprintAuthenticateSidecar =
                    (FingerprintAuthenticateSidecar)
                            getFragmentManager().findFragmentByTag("authenticate_sidecar");
            this.mAuthenticateSidecar = fingerprintAuthenticateSidecar;
            if (fingerprintAuthenticateSidecar == null) {
                this.mAuthenticateSidecar = new FingerprintAuthenticateSidecar();
                FragmentManager fragmentManager = getFragmentManager();
                BackStackRecord m =
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                fragmentManager, fragmentManager);
                m.doAddOp(0, this.mAuthenticateSidecar, "authenticate_sidecar", 1);
                m.commitInternal(false);
            }
            this.mAuthenticateSidecar.mFingerprintManager = this.mFingerprintManager;
            FingerprintRemoveSidecar fingerprintRemoveSidecar =
                    (FingerprintRemoveSidecar)
                            getFragmentManager().findFragmentByTag("removal_sidecar");
            this.mRemovalSidecar = fingerprintRemoveSidecar;
            if (fingerprintRemoveSidecar == null) {
                this.mRemovalSidecar = new FingerprintRemoveSidecar();
                FragmentManager fragmentManager2 = getFragmentManager();
                BackStackRecord m2 =
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                fragmentManager2, fragmentManager2);
                m2.doAddOp(0, this.mRemovalSidecar, "removal_sidecar", 1);
                m2.commitInternal(false);
            }
            FingerprintRemoveSidecar fingerprintRemoveSidecar2 = this.mRemovalSidecar;
            fingerprintRemoveSidecar2.mFingerprintUpdater = this.mFingerprintUpdater;
            fingerprintRemoveSidecar2.setListener(this.mRemovalListener);
            RenameDialog renameDialog =
                    (RenameDialog)
                            getFragmentManager().findFragmentByTag(RenameDialog.class.getName());
            if (renameDialog != null) {
                renameDialog.mDeleteInProgress = this.mRemovalSidecar.inProgress();
            }
            this.mFingerprintsRenaming = new HashMap();
            int intExtra =
                    getActivity()
                            .getIntent()
                            .getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
            this.mUserId = intExtra;
            this.mHasFirstEnrolled = this.mFingerprintManager.hasEnrolledFingerprints(intExtra);
            if (bundle != null) {
                this.mFingerprintsRenaming =
                        (HashMap) bundle.getSerializable("mFingerprintsRenaming");
                this.mToken = bundle.getByteArray("hw_auth_token");
                this.mLaunchedConfirm = bundle.getBoolean("launched_confirm", false);
                this.mIsEnrolling = bundle.getBoolean("is_enrolled", this.mIsEnrolling);
                this.mHasFirstEnrolled =
                        bundle.getBoolean("has_first_enrolled", this.mHasFirstEnrolled);
            }
            if (!this.mLaunchedConfirm && !this.mIsEnrolling) {
                if (this.mToken == null) {
                    this.mLaunchedConfirm = true;
                    Intent intent = new Intent();
                    ChooseLockSettingsHelper.Builder builder =
                            new ChooseLockSettingsHelper.Builder(getActivity(), this);
                    builder.mRequestCode = 101;
                    builder.mTitle =
                            getString(R.string.security_settings_fingerprint_preference_title);
                    builder.mRequestGatekeeperPasswordHandle = true;
                    builder.mUserId = this.mUserId;
                    builder.mForegroundOnly = true;
                    builder.mReturnCredentials = true;
                    if (!builder.show()) {
                        intent.setClassName(
                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                ChooseLockGeneric.class.getName());
                        intent.putExtra("hide_insecure_options", true);
                        intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                        intent.putExtra("request_gk_pw_handle", true);
                        intent.putExtra("for_fingerprint", true);
                        startActivityForResult(intent, 102);
                    }
                } else if (!this.mHasFirstEnrolled) {
                    this.mIsEnrolling = true;
                    addFirstFingerprint(null);
                }
            }
            getPreferenceScreen().removeAll();
            addPreferencesFromResource(R.xml.security_settings_fingerprint);
            final RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                    RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                            activity, 32, this.mUserId);
            final Intent helpIntent =
                    HelpUtils.getHelpIntent(
                            activity,
                            getString(R.string.help_url_fingerprint),
                            activity.getClass().getName());
            final int i = 0;
            View.OnClickListener onClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i) {
                                case 0:
                                    Activity activity2 = activity;
                                    Intent intent2 = (Intent) helpIntent;
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            FingerprintSettings.FingerprintSettingsFragment
                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                    activity2.startActivityForResult(intent2, 0);
                                    break;
                                default:
                                    Activity activity3 = activity;
                                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin =
                                            (RestrictedLockUtils.EnforcedAdmin) helpIntent;
                                    BaseSearchIndexProvider baseSearchIndexProvider2 =
                                            FingerprintSettings.FingerprintSettingsFragment
                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                            activity3, enforcedAdmin);
                                    break;
                            }
                        }
                    };
            ((ArrayList) this.mFooterColumns).clear();
            if (checkIfKeyguardFeaturesDisabled == null) {
                FooterColumn footerColumn = new FooterColumn();
                footerColumn.mTitle =
                        getString(
                                Utils.isPrivateProfile(getContext(), this.mUserId)
                                        ? R.string
                                                .private_space_fingerprint_enroll_introduction_message
                                        : R.string
                                                .security_settings_fingerprint_enroll_introduction_v3_message,
                                DeviceHelper.getDeviceName(getActivity()));
                footerColumn.mLearnMoreClickListener = onClickListener;
                footerColumn.mLearnMoreOverrideText =
                        getText(R.string.security_settings_fingerprint_settings_footer_learn_more);
                ((ArrayList) this.mFooterColumns).add(footerColumn);
                return;
            }
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
            FooterColumn footerColumn2 = new FooterColumn();
            footerColumn2.mTitle =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.FINGERPRINT_UNLOCK_DISABLED_EXPLANATION",
                                    new Supplier() { // from class:
                                                     // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            FingerprintSettings.FingerprintSettingsFragment
                                                    fingerprintSettingsFragment =
                                                            FingerprintSettings
                                                                    .FingerprintSettingsFragment
                                                                    .this;
                                            BaseSearchIndexProvider baseSearchIndexProvider =
                                                    FingerprintSettings.FingerprintSettingsFragment
                                                            .SEARCH_INDEX_DATA_PROVIDER;
                                            return fingerprintSettingsFragment.getString(
                                                    R.string
                                                            .security_fingerprint_disclaimer_lockscreen_disabled_1);
                                        }
                                    });
            final int i2 = 1;
            footerColumn2.mLearnMoreClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i2) {
                                case 0:
                                    Activity activity2 = activity;
                                    Intent intent2 = (Intent) checkIfKeyguardFeaturesDisabled;
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            FingerprintSettings.FingerprintSettingsFragment
                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                    activity2.startActivityForResult(intent2, 0);
                                    break;
                                default:
                                    Activity activity3 = activity;
                                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin =
                                            (RestrictedLockUtils.EnforcedAdmin)
                                                    checkIfKeyguardFeaturesDisabled;
                                    BaseSearchIndexProvider baseSearchIndexProvider2 =
                                            FingerprintSettings.FingerprintSettingsFragment
                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                            activity3, enforcedAdmin);
                                    break;
                            }
                        }
                    };
            footerColumn2.mLearnMoreOverrideText = getText(R.string.admin_support_more_info);
            ((ArrayList) this.mFooterColumns).add(footerColumn2);
            FooterColumn footerColumn3 = new FooterColumn();
            footerColumn3.mTitle =
                    getText(R.string.security_fingerprint_disclaimer_lockscreen_disabled_2);
            if (isSfps()) {
                footerColumn3.mLearnMoreOverrideText =
                        getText(R.string.security_settings_fingerprint_settings_footer_learn_more);
            }
            footerColumn3.mLearnMoreClickListener = onClickListener;
            ((ArrayList) this.mFooterColumns).add(footerColumn3);
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onDestroy() {
            super.onDestroy();
            if (getActivity().isFinishing()) {
                this.mFingerprintManager.revokeChallenge(this.mUserId, this.mChallenge);
            }
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            FingerprintRemoveSidecar fingerprintRemoveSidecar = this.mRemovalSidecar;
            if (fingerprintRemoveSidecar != null) {
                fingerprintRemoveSidecar.setListener(null);
            }
            FingerprintAuthenticateSidecar fingerprintAuthenticateSidecar =
                    this.mAuthenticateSidecar;
            if (fingerprintAuthenticateSidecar != null) {
                fingerprintAuthenticateSidecar.setListener(null);
                FingerprintAuthenticateSidecar fingerprintAuthenticateSidecar2 =
                        this.mAuthenticateSidecar;
                CancellationSignal cancellationSignal =
                        fingerprintAuthenticateSidecar2.mCancellationSignal;
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    fingerprintAuthenticateSidecar2.mCancellationSignal = null;
                }
                removeCallbacks(this.mFingerprintLockoutReset);
            }
        }

        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            String key = preference.getKey();
            if ("fingerprint_enable_keyguard_toggle".equals(key)) {
                return true;
            }
            Log.v("FingerprintSettings", "Unknown key:" + key);
            return true;
        }

        @Override // com.android.settings.dashboard.DashboardFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat,
                  // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
        public final boolean onPreferenceTreeClick(Preference preference) {
            if (KEY_FINGERPRINT_ADD.equals(preference.getKey())) {
                this.mIsEnrolling = true;
                Intent intent = new Intent();
                if (FeatureFlagUtils.isEnabled(getContext(), "settings_biometrics2_enrollment")) {
                    intent.setClassName(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            FingerprintEnrollmentActivity.InternalActivity.class.getName());
                    intent.putExtra("skip_find_sensor", true);
                } else {
                    intent.setClassName(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            FingerprintEnrollEnrolling.class.getName());
                }
                intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                intent.putExtra("hw_auth_token", this.mToken);
                startActivityForResult(intent, 10);
            } else if (preference instanceof FingerprintPreference) {
                Parcelable parcelable = ((FingerprintPreference) preference).mFingerprint;
                RenameDialog renameDialog = new RenameDialog();
                Bundle bundle = new Bundle();
                if (this.mFingerprintsRenaming.containsKey(
                        Integer.valueOf(parcelable.getBiometricId()))) {
                    bundle.putParcelable(
                            "fingerprint",
                            new Fingerprint(
                                    (CharSequence)
                                            this.mFingerprintsRenaming.get(
                                                    Integer.valueOf(parcelable.getBiometricId())),
                                    parcelable.getGroupId(),
                                    parcelable.getBiometricId(),
                                    parcelable.getDeviceId()));
                } else {
                    bundle.putParcelable("fingerprint", parcelable);
                }
                renameDialog.mDismissListener =
                        new DialogInterface
                                .OnDismissListener() { // from class:
                                                       // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$$ExternalSyntheticLambda4
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                FingerprintSettings.FingerprintSettingsFragment
                                        fingerprintSettingsFragment =
                                                FingerprintSettings.FingerprintSettingsFragment
                                                        .this;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        FingerprintSettings.FingerprintSettingsFragment
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                fingerprintSettingsFragment.retryFingerprint();
                            }
                        };
                renameDialog.mDeleteInProgress = this.mRemovalSidecar.inProgress();
                renameDialog.setArguments(bundle);
                renameDialog.setTargetFragment(this, 0);
                renameDialog.show(getFragmentManager(), RenameDialog.class.getName());
                FingerprintAuthenticateSidecar fingerprintAuthenticateSidecar =
                        this.mAuthenticateSidecar;
                CancellationSignal cancellationSignal =
                        fingerprintAuthenticateSidecar.mCancellationSignal;
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    fingerprintAuthenticateSidecar.mCancellationSignal = null;
                }
            }
            return super.onPreferenceTreeClick(preference);
        }

        @Override // com.android.settings.dashboard.DashboardFragment,
                  // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            this.mInFingerprintLockout = false;
            updatePreferences();
            FingerprintRemoveSidecar fingerprintRemoveSidecar = this.mRemovalSidecar;
            if (fingerprintRemoveSidecar != null) {
                fingerprintRemoveSidecar.setListener(this.mRemovalListener);
            }
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            FingerprintFeatureProviderImpl fingerprintFeatureProvider =
                    featureFactoryImpl.getFingerprintFeatureProvider();
            getActivity().getApplicationContext();
            fingerprintFeatureProvider.getClass();
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            bundle.putByteArray("hw_auth_token", this.mToken);
            bundle.putBoolean("launched_confirm", this.mLaunchedConfirm);
            bundle.putSerializable("mFingerprintsRenaming", this.mFingerprintsRenaming);
            bundle.putBoolean("is_enrolled", this.mIsEnrolling);
            bundle.putBoolean("has_first_enrolled", this.mHasFirstEnrolled);
        }

        @Override // com.android.settings.dashboard.DashboardFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onStop() {
            super.onStop();
            if (getActivity().isChangingConfigurations()
                    || this.mLaunchedConfirm
                    || this.mIsEnrolling) {
                return;
            }
            setResult(3);
            getActivity().finish();
        }

        public final void retryFingerprint() {
            Iterator it = this.mSensorProperties.iterator();
            while (it.hasNext()) {
                if (((FingerprintSensorPropertiesInternal) it.next()).isAnyUdfpsType()) {
                    return;
                }
            }
            if (this.mRemovalSidecar.inProgress()
                    || this.mFingerprintManager.getEnrolledFingerprints(this.mUserId).size() == 0
                    || this.mLaunchedConfirm
                    || this.mInFingerprintLockout) {
                return;
            }
            FingerprintAuthenticateSidecar fingerprintAuthenticateSidecar =
                    this.mAuthenticateSidecar;
            int i = this.mUserId;
            fingerprintAuthenticateSidecar.getClass();
            CancellationSignal cancellationSignal = new CancellationSignal();
            fingerprintAuthenticateSidecar.mCancellationSignal = cancellationSignal;
            fingerprintAuthenticateSidecar.mFingerprintManager.authenticate(
                    (FingerprintManager.CryptoObject) null,
                    cancellationSignal,
                    fingerprintAuthenticateSidecar.mAuthenticationCallback,
                    (Handler) null,
                    i);
            this.mAuthenticateSidecar.setListener(this.mAuthenticateListener);
        }

        public final void updateAddPreference() {
            if (getActivity() == null) {
                return;
            }
            Preference findPreference = findPreference(KEY_FINGERPRINT_ADD);
            this.mAddFingerprintPreference = findPreference;
            if (findPreference == null) {
                return;
            }
            int integer =
                    getContext()
                            .getResources()
                            .getInteger(
                                    android.R.integer.config_lowMemoryKillerMinFreeKbytesAdjust);
            boolean z = false;
            boolean z2 =
                    this.mFingerprintManager.getEnrolledFingerprints(this.mUserId).size()
                            >= integer;
            boolean inProgress = this.mRemovalSidecar.inProgress();
            boolean z3 =
                    RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                                    getContext(), 32, this.mUserId)
                            != null;
            this.mAddFingerprintPreference.setSummary(
                    z2
                            ? getContext()
                                    .getString(
                                            R.string.fingerprint_add_max, Integer.valueOf(integer))
                            : ApnSettings.MVNO_NONE);
            Preference preference = this.mAddFingerprintPreference;
            if (!z3 && !z2 && !inProgress && this.mToken != null) {
                z = true;
            }
            preference.setEnabled(z);
        }

        public final void updatePreferences() {
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) findPreference("security_settings_fingerprints_enrolled");
            this.mFingerprintsEnrolledCategory = preferenceCategory;
            if (preferenceCategory != null) {
                preferenceCategory.removeAll();
            }
            List enrolledFingerprints =
                    this.mFingerprintManager.getEnrolledFingerprints(this.mUserId);
            int size = enrolledFingerprints.size();
            String str = KEY_FINGERPRINT_ADD;
            for (int i = 0; i < size; i++) {
                Fingerprint fingerprint = (Fingerprint) enrolledFingerprints.get(i);
                FingerprintPreference fingerprintPreference =
                        new FingerprintPreference(preferenceScreen.getContext(), this);
                String genKey = genKey(fingerprint.getBiometricId());
                if (i == 0) {
                    str = genKey;
                }
                fingerprintPreference.setKey(genKey);
                fingerprintPreference.setTitle(fingerprint.getName());
                fingerprintPreference.mFingerprint = fingerprint;
                fingerprintPreference.setPersistent();
                fingerprintPreference.setIcon(R.drawable.ic_fingerprint_24dp);
                FingerprintRemoveSidecar fingerprintRemoveSidecar = this.mRemovalSidecar;
                int biometricId = fingerprint.getBiometricId();
                if (fingerprintRemoveSidecar.inProgress()
                        && fingerprintRemoveSidecar.mFingerprintRemoving.getBiometricId()
                                == biometricId) {
                    fingerprintPreference.setEnabled(false);
                }
                if (this.mFingerprintsRenaming.containsKey(
                        Integer.valueOf(fingerprint.getBiometricId()))) {
                    fingerprintPreference.setTitle(
                            (CharSequence)
                                    this.mFingerprintsRenaming.get(
                                            Integer.valueOf(fingerprint.getBiometricId())));
                }
                this.mFingerprintsEnrolledCategory.addPreference(fingerprintPreference);
                fingerprintPreference.setOnPreferenceChangeListener(this);
            }
            Preference findPreference = findPreference(KEY_FINGERPRINT_ADD);
            this.mAddFingerprintPreference = findPreference;
            findPreference.setOnPreferenceChangeListener(this);
            updateAddPreference();
            for (AbstractPreferenceController abstractPreferenceController : this.mControllers) {
                if (abstractPreferenceController
                        instanceof FingerprintSettingsPreferenceController) {
                    ((FingerprintSettingsPreferenceController) abstractPreferenceController)
                            .setUserId(this.mUserId);
                } else if (abstractPreferenceController
                        instanceof FingerprintUnlockCategoryController) {
                    ((FingerprintUnlockCategoryController) abstractPreferenceController)
                            .setUserId(this.mUserId);
                }
            }
            if (isSfps()) {
                scrollToPreference(str);
                this.mFingerprintUnlockCategory =
                        (PreferenceCategory)
                                findPreference("security_settings_fingerprint_unlock_category");
                RestrictedSwitchPreference restrictedSwitchPreference =
                        (RestrictedSwitchPreference) findPreference(KEY_REQUIRE_SCREEN_ON_TO_AUTH);
                this.mRequireScreenOnToAuthPreference = restrictedSwitchPreference;
                restrictedSwitchPreference.setChecked(
                        this.mRequireScreenOnToAuthPreferenceController.getThreadEnabled());
                this.mRequireScreenOnToAuthPreference.setOnPreferenceChangeListener(
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintSettingsFragment$$ExternalSyntheticLambda5
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj) {
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        FingerprintSettings.FingerprintSettingsFragment
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                FingerprintSettings.FingerprintSettingsFragment
                                        fingerprintSettingsFragment =
                                                FingerprintSettings.FingerprintSettingsFragment
                                                        .this;
                                fingerprintSettingsFragment.getClass();
                                fingerprintSettingsFragment
                                        .mRequireScreenOnToAuthPreferenceController.setChecked(
                                        !((TwoStatePreference) preference).isChecked());
                                return true;
                            }
                        });
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                FingerprintFeatureProviderImpl fingerprintFeatureProvider =
                        featureFactoryImpl.getFingerprintFeatureProvider();
                getContext();
                if (fingerprintFeatureProvider.mSfpsRestToUnlockFeature == null) {
                    fingerprintFeatureProvider.mSfpsRestToUnlockFeature =
                            new SfpsRestToUnlockFeatureImpl();
                }
                SfpsRestToUnlockFeatureImpl sfpsRestToUnlockFeatureImpl =
                        fingerprintFeatureProvider.mSfpsRestToUnlockFeature;
                Context context = getContext();
                sfpsRestToUnlockFeatureImpl.getClass();
                Intrinsics.checkNotNullParameter(context, "context");
                boolean isAvailable =
                        this.mFingerprintUnlockCategoryPreferenceController.isAvailable();
                if (this.mFingerprintUnlockCategory.isVisible() != isAvailable) {
                    this.mFingerprintUnlockCategory.setVisible(isAvailable);
                }
            }
            FragmentActivity activity = getActivity();
            if (activity != null) {
                PreferenceCategory preferenceCategory2 =
                        (PreferenceCategory) findPreference("security_settings_fingerprint_footer");
                this.mFingerprintUnlockFooter = preferenceCategory2;
                if (preferenceCategory2 != null) {
                    preferenceCategory2.removeAll();
                }
                for (int i2 = 0; i2 < ((ArrayList) this.mFooterColumns).size(); i2++) {
                    FooterColumn footerColumn =
                            (FooterColumn) ((ArrayList) this.mFooterColumns).get(i2);
                    CharSequence charSequence = footerColumn.mTitle;
                    FooterPreference footerPreference = new FooterPreference(activity);
                    footerPreference.setSelectable(false);
                    if (TextUtils.isEmpty(charSequence)) {
                        throw new IllegalArgumentException("Footer title cannot be empty!");
                    }
                    footerPreference.setTitle(charSequence);
                    if (!TextUtils.isEmpty(null)) {
                        footerPreference.setKey(null);
                    }
                    if (!TextUtils.isEmpty(null)
                            && !TextUtils.equals(footerPreference.mContentDescription, null)) {
                        footerPreference.mContentDescription = null;
                        footerPreference.notifyChanged();
                    }
                    if (!TextUtils.isEmpty(null)) {
                        footerPreference.setLearnMoreText(null);
                    }
                    if (i2 > 0) {
                        footerPreference.setIconVisibility();
                    }
                    View.OnClickListener onClickListener = footerColumn.mLearnMoreClickListener;
                    if (onClickListener != null) {
                        footerPreference.setLearnMoreAction(onClickListener);
                        if (!TextUtils.isEmpty(footerColumn.mLearnMoreOverrideText)) {
                            footerPreference.setLearnMoreText(footerColumn.mLearnMoreOverrideText);
                        }
                    }
                    this.mFingerprintUnlockFooter.addPreference(footerPreference);
                }
            }
            setPreferenceScreen(preferenceScreen);
            retryFingerprint();
        }
    }

    public static boolean isFingerprintHardwareDetected(Context context) {
        boolean isHardwareDetected;
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(context);
        if (fingerprintManagerOrNull == null) {
            Log.d("FingerprintSettings", "FingerprintManager is null");
            isHardwareDetected = false;
        } else {
            isHardwareDetected = fingerprintManagerOrNull.isHardwareDetected();
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "FingerprintManager is not null. Hardware detected: ",
                    "FingerprintSettings",
                    isHardwareDetected);
        }
        return fingerprintManagerOrNull != null && isHardwareDetected;
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", FingerprintSettingsFragment.class.getName());
        return intent;
    }

    @Override // com.android.settings.SubSettings, com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return FingerprintSettingsFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(
                getText(
                        Utils.isPrivateProfile(
                                        this,
                                        getIntent()
                                                .getIntExtra(
                                                        "android.intent.extra.USER_ID",
                                                        UserHandle.myUserId()))
                                ? R.string.private_space_fingerprint_unlock_title
                                : R.string.security_settings_fingerprint_preference_title));
    }
}
