package com.samsung.android.settings.deviceinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.deviceinfo.storage.StorageUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class StorageSDCardEncryptionWarnDialog extends AlertActivity {
    public static AlertDialog mSDCardEncryptionWarnDialog;
    public StorageSDCardEncryptionWarnDialog mContext;
    public VolumeInfo mVolume;

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        StorageManager storageManager = (StorageManager) getSystemService(StorageManager.class);
        String stringExtra = getIntent().getStringExtra("android.os.storage.extra.VOLUME_ID");
        if (stringExtra == null) {
            Log.w("StorageSettings", "EXTRA_VOLUME_ID is null");
            return;
        }
        VolumeInfo findVolumeById = storageManager.findVolumeById(stringExtra);
        this.mVolume = findVolumeById;
        if (findVolumeById == null) {
            Log.w("StorageSettings", "VolumeInfo ID is null");
            return;
        }
        AlertDialog alertDialog = mSDCardEncryptionWarnDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            mSDCardEncryptionWarnDialog = null;
            StringBuilder sb = new StringBuilder();
            if (Utils.isTablet()) {
                sb.append(
                        getString(
                                R.string.sec_sd_card_encryption_warn_dialog_description_tablet_1));
                sb.append("\n\n");
                sb.append(
                        getString(
                                R.string.sec_sd_card_encryption_warn_dialog_description_tablet_2));
            } else {
                sb.append(getString(R.string.sec_sd_card_encryption_warn_dialog_description_1));
                sb.append("\n\n");
                sb.append(getString(R.string.sec_sd_card_encryption_warn_dialog_description_2));
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setCancelable(true);
            builder.setTitle(R.string.sec_sd_card_encryption_warn_dialog_title);
            builder.setMessage(sb.toString());
            final int i = 0;
            builder.setPositiveButton(
                    R.string.sec_sd_card_encryption_warn_dialog_unmount,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.deviceinfo.StorageSDCardEncryptionWarnDialog.1
                        public final /* synthetic */ StorageSDCardEncryptionWarnDialog this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            switch (i) {
                                case 0:
                                    StorageSDCardEncryptionWarnDialog
                                            storageSDCardEncryptionWarnDialog = this.this$0;
                                    new StorageUtils.MountTask(
                                                    (Context)
                                                            storageSDCardEncryptionWarnDialog
                                                                    .mContext,
                                                    storageSDCardEncryptionWarnDialog.mVolume)
                                            .execute(new Void[0]);
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.mContext.startActivity(
                                            new Intent(
                                                    "com.sec.app.action.START_SDCARD_ENCRYPTION"));
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    });
            builder.setOnCancelListener(
                    new DialogInterface
                            .OnCancelListener() { // from class:
                                                  // com.samsung.android.settings.deviceinfo.StorageSDCardEncryptionWarnDialog.2
                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            StorageSDCardEncryptionWarnDialog.this.finish();
                        }
                    });
            final int i2 = 1;
            builder.setNeutralButton(
                    R.string.cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.deviceinfo.StorageSDCardEncryptionWarnDialog.1
                        public final /* synthetic */ StorageSDCardEncryptionWarnDialog this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            switch (i2) {
                                case 0:
                                    StorageSDCardEncryptionWarnDialog
                                            storageSDCardEncryptionWarnDialog = this.this$0;
                                    new StorageUtils.MountTask(
                                                    (Context)
                                                            storageSDCardEncryptionWarnDialog
                                                                    .mContext,
                                                    storageSDCardEncryptionWarnDialog.mVolume)
                                            .execute(new Void[0]);
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.mContext.startActivity(
                                            new Intent(
                                                    "com.sec.app.action.START_SDCARD_ENCRYPTION"));
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    });
            final int i3 = 2;
            builder.setNegativeButton(
                    R.string.sec_sd_card_encryption_warn_dialog_decrypt,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.deviceinfo.StorageSDCardEncryptionWarnDialog.1
                        public final /* synthetic */ StorageSDCardEncryptionWarnDialog this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            switch (i3) {
                                case 0:
                                    StorageSDCardEncryptionWarnDialog
                                            storageSDCardEncryptionWarnDialog = this.this$0;
                                    new StorageUtils.MountTask(
                                                    (Context)
                                                            storageSDCardEncryptionWarnDialog
                                                                    .mContext,
                                                    storageSDCardEncryptionWarnDialog.mVolume)
                                            .execute(new Void[0]);
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.mContext.startActivity(
                                            new Intent(
                                                    "com.sec.app.action.START_SDCARD_ENCRYPTION"));
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    });
            AlertDialog create = builder.create();
            mSDCardEncryptionWarnDialog = create;
            create.show();
        }
    }
}
