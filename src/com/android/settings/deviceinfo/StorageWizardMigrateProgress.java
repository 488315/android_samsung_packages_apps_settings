package com.android.settings.deviceinfo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardMigrateProgress extends StorageWizardBase {
    public final AnonymousClass1 mCallback = new AnonymousClass1();
    public int mMoveId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deviceinfo.StorageWizardMigrateProgress$1, reason: invalid class name */
    public final class AnonymousClass1 extends PackageManager.MoveCallback {
        public AnonymousClass1() {}

        public final void onStatusChanged(int i, int i2, long j) {
            StorageWizardMigrateProgress storageWizardMigrateProgress =
                    StorageWizardMigrateProgress.this;
            if (storageWizardMigrateProgress.mMoveId != i) {
                return;
            }
            if (!PackageManager.isMoveStatusFinished(i2)) {
                StorageWizardMigrateProgress.this.setCurrentProgress(i2);
                return;
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i2, "Finished with status ", "StorageWizardMigrateProgress");
            if (i2 != -100) {
                Toast.makeText(
                                storageWizardMigrateProgress,
                                StorageWizardMigrateProgress.this.getString(
                                        R.string.insufficient_storage),
                                1)
                        .show();
            } else if (StorageWizardMigrateProgress.this.mDisk != null) {
                Intent intent = new Intent("com.android.systemui.action.FINISH_WIZARD");
                intent.setPackage("com.android.systemui");
                intent.addFlags(1073741824);
                StorageWizardMigrateProgress.this.sendBroadcast(intent);
                if (!StorageWizardMigrateProgress.this.isFinishing()) {
                    Intent intent2 =
                            new Intent(
                                    storageWizardMigrateProgress,
                                    (Class<?>) StorageWizardReady.class);
                    intent2.putExtra(
                            "android.os.storage.extra.DISK_ID",
                            StorageWizardMigrateProgress.this.mDisk.getId());
                    StorageWizardMigrateProgress.this.startActivity(intent2);
                }
            }
            StorageWizardMigrateProgress.this.finishAffinity();
        }
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mVolume == null) {
            finish();
            return;
        }
        setContentView(R.layout.storage_wizard_progress);
        getWindow().getDecorView().setSystemUiVisibility(2);
        getWindow().setFlags(16, 16);
        this.mMoveId = getIntent().getIntExtra("android.content.pm.extra.MOVE_ID", -1);
        setIcon(R.drawable.ic_swap_horiz);
        setHeaderText(R.string.storage_wizard_migrate_progress_v2_title, new CharSequence[0]);
        setAuxChecklist();
        this.mBack.setVisibility(4);
        this.mNext.setVisibility(4);
        PackageManager packageManager = getPackageManager();
        Handler handler = new Handler();
        AnonymousClass1 anonymousClass1 = this.mCallback;
        packageManager.registerMoveCallback(anonymousClass1, handler);
        anonymousClass1.onStatusChanged(
                this.mMoveId, getPackageManager().getMoveStatus(this.mMoveId), -1L);
    }
}
