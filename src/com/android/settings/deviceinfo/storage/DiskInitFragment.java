package com.android.settings.deviceinfo.storage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.deviceinfo.StorageWizardInit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DiskInitFragment extends InstrumentedDialogFragment {
    public static void show(Fragment fragment, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.TEXT", R.string.storage_dialog_unmountable);
        bundle.putString("android.os.storage.extra.DISK_ID", str);
        DiskInitFragment diskInitFragment = new DiskInitFragment();
        diskInitFragment.setArguments(bundle);
        diskInitFragment.setTargetFragment(fragment, 0);
        diskInitFragment.show(fragment.getFragmentManager(), "disk_init");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 561;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        final FragmentActivity activity = getActivity();
        StorageManager storageManager =
                (StorageManager) activity.getSystemService(StorageManager.class);
        int i = getArguments().getInt("android.intent.extra.TEXT");
        final String string = getArguments().getString("android.os.storage.extra.DISK_ID");
        DiskInfo findDiskById = storageManager.findDiskById(string);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.P.mMessage = TextUtils.expandTemplate(getText(i), findDiskById.getDescription());
        builder.setPositiveButton(
                R.string.storage_menu_set_up,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.deviceinfo.storage.DiskInitFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        DiskInitFragment diskInitFragment = DiskInitFragment.this;
                        Context context = activity;
                        String str = string;
                        diskInitFragment.getClass();
                        Intent intent = new Intent(context, (Class<?>) StorageWizardInit.class);
                        intent.putExtra("android.os.storage.extra.DISK_ID", str);
                        diskInitFragment.startActivity(intent);
                    }
                });
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
