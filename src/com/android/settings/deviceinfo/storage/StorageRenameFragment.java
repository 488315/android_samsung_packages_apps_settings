package com.android.settings.deviceinfo.storage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeRecord;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageRenameFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 563;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity activity = getActivity();
        final StorageManager storageManager =
                (StorageManager) activity.getSystemService(StorageManager.class);
        final String string = getArguments().getString("android.os.storage.extra.FS_UUID");
        VolumeRecord findRecordByUuid = storageManager.findRecordByUuid(string);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View inflate =
                LayoutInflater.from(builder.P.mContext)
                        .inflate(R.layout.dialog_edittext, (ViewGroup) null, false);
        final EditText editText = (EditText) inflate.findViewById(R.id.edittext);
        editText.setText(findRecordByUuid.getNickname());
        editText.requestFocus();
        builder.setTitle(R.string.storage_rename_title);
        builder.setView(inflate);
        builder.setPositiveButton(
                R.string.save,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.deviceinfo.storage.StorageRenameFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        storageManager.setVolumeNickname(string, editText.getText().toString());
                    }
                });
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
