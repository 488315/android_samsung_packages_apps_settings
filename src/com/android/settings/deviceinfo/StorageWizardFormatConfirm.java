package com.android.settings.deviceinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardFormatConfirm extends InstrumentedDialogFragment {
    public static StorageWizardInit mCallerActivity;

    public static void show(FragmentActivity fragmentActivity, String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("android.os.storage.extra.DISK_ID", str);
        bundle.putString("format_forget_uuid", null);
        bundle.putBoolean("format_private", z);
        StorageWizardFormatConfirm storageWizardFormatConfirm = new StorageWizardFormatConfirm();
        storageWizardFormatConfirm.setArguments(bundle);
        storageWizardFormatConfirm.show(
                fragmentActivity.getSupportFragmentManager(), "format_warning");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1375;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        final Context context = getContext();
        Bundle arguments = getArguments();
        final String string = arguments.getString("android.os.storage.extra.DISK_ID");
        final String string2 = arguments.getString("format_forget_uuid");
        final boolean z = arguments.getBoolean("format_private", false);
        DiskInfo findDiskById =
                ((StorageManager) context.getSystemService(StorageManager.class))
                        .findDiskById(string);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String format =
                String.format(
                        getString(R.string.storage_wizard_format_confirm_v2_title),
                        findDiskById.getShortDescription());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = format;
        alertParams.mMessage =
                String.format(
                        getString(R.string.storage_wizard_format_confirm_v2_body),
                        findDiskById.getDescription(),
                        findDiskById.getShortDescription());
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(
                String.format(
                        getString(R.string.storage_wizard_format_confirm_v2_action),
                        findDiskById.getShortDescription()),
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.deviceinfo.StorageWizardFormatConfirm$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        Context context2 = context;
                        String str = string;
                        String str2 = string2;
                        boolean z2 = z;
                        Intent intent =
                                new Intent(context2, (Class<?>) StorageWizardFormatProgress.class);
                        intent.putExtra("android.os.storage.extra.DISK_ID", str);
                        intent.putExtra("format_forget_uuid", str2);
                        intent.putExtra("format_private", z2);
                        context2.startActivity(intent);
                    }
                });
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        StorageWizardInit storageWizardInit = mCallerActivity;
        if (storageWizardInit != null) {
            storageWizardInit.finish();
            mCallerActivity = null;
        }
    }
}
