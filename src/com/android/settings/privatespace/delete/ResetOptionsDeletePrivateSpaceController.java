package com.android.settings.privatespace.delete;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.multiuser.Flags;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;

import com.android.internal.annotations.Initializer;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.privatespace.PrivateSpaceMaintainer;
import com.android.settings.system.ResetDashboardFragment;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ResetOptionsDeletePrivateSpaceController extends BasePreferenceController {
    private static final String TAG = "PrivateSpaceResetCtrl";
    private ResetDashboardFragment mHostFragment;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class DeletePrivateSpaceDialogFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 2076;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            final Context context = getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.private_space_delete_header);
            builder.setMessage(R.string.reset_private_space_delete_dialog);
            final int i = 0;
            builder.setPositiveButton(
                    R.string.private_space_delete_button_label,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.privatespace.delete.ResetOptionsDeletePrivateSpaceController$DeletePrivateSpaceDialogFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ ResetOptionsDeletePrivateSpaceController
                                        .DeletePrivateSpaceDialogFragment
                                f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            switch (i) {
                                case 0:
                                    ResetOptionsDeletePrivateSpaceController
                                                    .DeletePrivateSpaceDialogFragment
                                            deletePrivateSpaceDialogFragment = this.f$0;
                                    Context context2 = context;
                                    deletePrivateSpaceDialogFragment.mMetricsFeatureProvider.action(
                                            context2, 2077, new Pair[0]);
                                    PrivateSpaceMaintainer.getInstance(context2)
                                            .deletePrivateSpace();
                                    dialogInterface.dismiss();
                                    break;
                                default:
                                    ResetOptionsDeletePrivateSpaceController
                                                    .DeletePrivateSpaceDialogFragment
                                            deletePrivateSpaceDialogFragment2 = this.f$0;
                                    deletePrivateSpaceDialogFragment2.mMetricsFeatureProvider
                                            .action(context, 2078, new Pair[0]);
                                    dialogInterface.cancel();
                                    break;
                            }
                        }
                    });
            final int i2 = 1;
            builder.setNegativeButton(
                    R.string.private_space_cancel_label,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.privatespace.delete.ResetOptionsDeletePrivateSpaceController$DeletePrivateSpaceDialogFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ ResetOptionsDeletePrivateSpaceController
                                        .DeletePrivateSpaceDialogFragment
                                f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            switch (i2) {
                                case 0:
                                    ResetOptionsDeletePrivateSpaceController
                                                    .DeletePrivateSpaceDialogFragment
                                            deletePrivateSpaceDialogFragment = this.f$0;
                                    Context context2 = context;
                                    deletePrivateSpaceDialogFragment.mMetricsFeatureProvider.action(
                                            context2, 2077, new Pair[0]);
                                    PrivateSpaceMaintainer.getInstance(context2)
                                            .deletePrivateSpace();
                                    dialogInterface.dismiss();
                                    break;
                                default:
                                    ResetOptionsDeletePrivateSpaceController
                                                    .DeletePrivateSpaceDialogFragment
                                            deletePrivateSpaceDialogFragment2 = this.f$0;
                                    deletePrivateSpaceDialogFragment2.mMetricsFeatureProvider
                                            .action(context, 2078, new Pair[0]);
                                    dialogInterface.cancel();
                                    break;
                            }
                        }
                    });
            return builder.create();
        }
    }

    public ResetOptionsDeletePrivateSpaceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Flags.enablePrivateSpaceFeatures()
                        && Flags.deletePrivateSpaceFromReset()
                        && isPrivateSpaceEntryPointEnabled())
                ? 0
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public DeletePrivateSpaceDialogFragment getDeleteDialogFragment() {
        return new DeletePrivateSpaceDialogFragment();
    }

    public FragmentManager getFragmentManager() {
        return this.mHostFragment.getFragmentManager();
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

    public boolean handleActivityResult(int i, int i2, Intent intent) {
        if (i != 1 || i2 != -1 || intent == null) {
            return false;
        }
        getDeleteDialogFragment()
                .show(getFragmentManager(), DeletePrivateSpaceDialogFragment.class.getName());
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        startAuthenticationForDelete();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isPrivateSpaceEntryPointEnabled() {
        PrivateSpaceMaintainer privateSpaceMaintainer =
                PrivateSpaceMaintainer.getInstance(this.mContext);
        return privateSpaceMaintainer.mUserManager.canAddPrivateProfile()
                || privateSpaceMaintainer.doesPrivateSpaceExist();
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

    @Initializer
    public void setFragment(ResetDashboardFragment resetDashboardFragment) {
        this.mHostFragment = resetDashboardFragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public boolean startAuthenticationForDelete() {
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(
                        this.mHostFragment.getActivity(), this.mHostFragment);
        builder.mRequestCode = 1;
        builder.show();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
