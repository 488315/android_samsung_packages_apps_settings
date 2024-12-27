package com.samsung.android.settings.analyzestorage.ui.dialog;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/ChooseAccountDialogFragment;",
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/AbsDialog;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class ChooseAccountDialogFragment extends AbsDialog {
    public static final AtomicBoolean progressing = new AtomicBoolean(false);
    public CloudConstants$CloudType cloudType;

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final AlertDialog createDialog$2() {
        AccountManager accountManager = AccountManager.get(getBaseContext());
        CloudConstants$CloudType cloudConstants$CloudType = this.cloudType;
        Account[] accountsByType =
                accountManager.getAccountsByType(
                        cloudConstants$CloudType != null
                                ? cloudConstants$CloudType.getAccountType()
                                : null);
        Intrinsics.checkNotNullExpressionValue(accountsByType, "getAccountsByType(...)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        ArrayList arrayList = new ArrayList(accountsByType.length);
        for (Account account : accountsByType) {
            arrayList.add(account.name);
        }
        final String[] strArr = (String[]) arrayList.toArray(new String[0]);
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.analyzestorage.ui.dialog.ChooseAccountDialogFragment$createDialog$onClickListener$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =
                                new Intent(
                                        ChooseAccountDialogFragment.this.cloudType
                                                        == CloudConstants$CloudType.GOOGLE_DRIVE
                                                ? "com.sec.android.app.myfiles.ENTER_GOOGLE_DRIVE"
                                                : "com.sec.android.app.myfiles.ENTER_ONEDRIVE");
                        String[] strArr2 = strArr;
                        intent.setClassName(
                                "com.sec.android.app.myfiles",
                                "com.sec.android.app.myfiles.ui.AnalyzeStorageActivity");
                        intent.putExtra("account", strArr2[i].toString());
                        intent.putExtra("open_cloud_from_manage_storage", true);
                        ChooseAccountDialogFragment.this.getBaseContext().startActivity(intent);
                        ChooseAccountDialogFragment chooseAccountDialogFragment =
                                ChooseAccountDialogFragment.this;
                        UserInteractionDialog$Callback userInteractionDialog$Callback =
                                chooseAccountDialogFragment.callback;
                        if (userInteractionDialog$Callback != null) {
                            userInteractionDialog$Callback.onOk();
                            chooseAccountDialogFragment.clearDataFromViewModel();
                        }
                    }
                };
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mItems = strArr;
        alertParams.mOnClickListener = onClickListener;
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putSerializable("cloudType", this.cloudType);
        outState.putBoolean("processing", progressing.get());
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        progressing.set(false);
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final void restoreStateOnCreate(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
        Serializable serializable = savedInstanceState.getSerializable("cloudType");
        this.cloudType =
                serializable instanceof CloudConstants$CloudType
                        ? (CloudConstants$CloudType) serializable
                        : null;
        progressing.set(savedInstanceState.getBoolean("processing"));
    }
}
