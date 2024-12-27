package com.samsung.android.settings.analyzestorage.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Button;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastType;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/FormatDialogFragment;",
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/AbsDialog;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class FormatDialogFragment extends AbsDialog {
    public final FormatDialogFragment$mStorageEjectListener$1 mStorageEjectListener =
            new BroadcastListener() { // from class:
                                      // com.samsung.android.settings.analyzestorage.ui.dialog.FormatDialogFragment$mStorageEjectListener$1
                @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                    if (StorageVolumeManager.mounted(1)) {
                        return;
                    }
                    FormatDialogFragment.this.cancel();
                }
            };

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final AlertDialog createDialog$2() {
        SparseArray sparseArray = BroadcastReceiveCenter.sInstanceMap;
        BroadcastReceiveCenter.InstanceHolder.INSTANCE.addDynamicListener(
                BroadcastType.MEDIA_EJECTED, this.mStorageEjectListener);
        String string = getString(R.string.as_sd_card);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        String string2 = getString(R.string.as_format_title, string);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string2;
        alertParams.mMessage = getString(R.string.as_format_body, string);
        final int i = 0;
        builder.setNegativeButton(
                R.string.as_dialog_cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.ui.dialog.FormatDialogFragment$createDialog$1
                    public final /* synthetic */ FormatDialogFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        switch (i) {
                            case 0:
                                this.this$0.cancel();
                                break;
                            default:
                                FormatDialogFragment formatDialogFragment = this.this$0;
                                UserInteractionDialog$Callback userInteractionDialog$Callback =
                                        formatDialogFragment.callback;
                                if (userInteractionDialog$Callback != null) {
                                    userInteractionDialog$Callback.onOk();
                                    formatDialogFragment.clearDataFromViewModel();
                                }
                                FormatDialogFragment formatDialogFragment2 = this.this$0;
                                formatDialogFragment2.getClass();
                                FormatProgressDialogFragment formatProgressDialogFragment =
                                        new FormatProgressDialogFragment();
                                formatProgressDialogFragment.baseFragmentManager =
                                        formatDialogFragment2.baseFragmentManager;
                                formatProgressDialogFragment.showDialog(null);
                                break;
                        }
                    }
                });
        final int i2 = 1;
        builder.setPositiveButton(
                R.string.as_button_format,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.ui.dialog.FormatDialogFragment$createDialog$1
                    public final /* synthetic */ FormatDialogFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        switch (i2) {
                            case 0:
                                this.this$0.cancel();
                                break;
                            default:
                                FormatDialogFragment formatDialogFragment = this.this$0;
                                UserInteractionDialog$Callback userInteractionDialog$Callback =
                                        formatDialogFragment.callback;
                                if (userInteractionDialog$Callback != null) {
                                    userInteractionDialog$Callback.onOk();
                                    formatDialogFragment.clearDataFromViewModel();
                                }
                                FormatDialogFragment formatDialogFragment2 = this.this$0;
                                formatDialogFragment2.getClass();
                                FormatProgressDialogFragment formatProgressDialogFragment =
                                        new FormatProgressDialogFragment();
                                formatProgressDialogFragment.baseFragmentManager =
                                        formatDialogFragment2.baseFragmentManager;
                                formatProgressDialogFragment.showDialog(null);
                                break;
                        }
                    }
                });
        return builder.create();
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        SparseArray sparseArray = BroadcastReceiveCenter.sInstanceMap;
        BroadcastReceiveCenter.InstanceHolder.INSTANCE.removeDynamicListener(
                BroadcastType.MEDIA_EJECTED, this.mStorageEjectListener);
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        Button button;
        super.onStart();
        AlertDialog alertDialog = this.baseDialog;
        if (alertDialog == null || (button = alertDialog.getButton(-1)) == null) {
            return;
        }
        button.setTextColor(
                getBaseContext().getColor(R.color.as_basic_dialog_positive_button_color_red));
    }
}
