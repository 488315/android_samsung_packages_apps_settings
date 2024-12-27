package com.android.settings.deviceinfo;

import android.app.admin.DevicePolicyManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardMoveProgress extends StorageWizardBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mCallback = new AnonymousClass1();
    public int mMoveId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deviceinfo.StorageWizardMoveProgress$1, reason: invalid class name */
    public final class AnonymousClass1 extends PackageManager.MoveCallback {
        public AnonymousClass1() {}

        public final void onStatusChanged(int i, int i2, long j) {
            String string;
            if (StorageWizardMoveProgress.this.mMoveId != i) {
                return;
            }
            if (!PackageManager.isMoveStatusFinished(i2)) {
                StorageWizardMoveProgress.this.setCurrentProgress(i2);
                return;
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i2, "Finished with status ", "StorageWizardMoveProgress");
            if (i2 != -100) {
                final StorageWizardMoveProgress storageWizardMoveProgress =
                        StorageWizardMoveProgress.this;
                if (i2 != -8) {
                    string =
                            i2 != -5
                                    ? i2 != -3
                                            ? i2 != -2
                                                    ? i2 != -1
                                                            ? storageWizardMoveProgress.getString(
                                                                    R.string.insufficient_storage)
                                                            : storageWizardMoveProgress.getString(
                                                                    R.string.insufficient_storage)
                                                    : storageWizardMoveProgress.getString(
                                                            R.string.does_not_exist)
                                            : storageWizardMoveProgress.getString(
                                                    R.string.system_package)
                                    : storageWizardMoveProgress.getString(
                                            R.string.invalid_location);
                } else {
                    storageWizardMoveProgress.getClass();
                    string =
                            ((DevicePolicyManager)
                                            storageWizardMoveProgress.getSystemService(
                                                    DevicePolicyManager.class))
                                    .getResources()
                                    .getString(
                                            "Settings.ERROR_MOVE_DEVICE_ADMIN",
                                            new Supplier() { // from class:
                                                             // com.android.settings.deviceinfo.StorageWizardMoveProgress$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    StorageWizardMoveProgress
                                                            storageWizardMoveProgress2 =
                                                                    StorageWizardMoveProgress.this;
                                                    int i3 = StorageWizardMoveProgress.$r8$clinit;
                                                    return storageWizardMoveProgress2.getString(
                                                            R.string.move_error_device_admin);
                                                }
                                            });
                }
                Toast.makeText(storageWizardMoveProgress, string, 1).show();
            }
            if (Utils.isTablet()) {
                StorageWizardMoveProgress.this.finish();
            } else {
                StorageWizardMoveProgress.this.finishAffinity();
            }
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
        this.mMoveId = getIntent().getIntExtra("android.content.pm.extra.MOVE_ID", -1);
        String stringExtra = getIntent().getStringExtra("android.intent.extra.TITLE");
        String bestVolumeDescription = this.mStorage.getBestVolumeDescription(this.mVolume);
        setIcon(R.drawable.ic_swap_horiz);
        setHeaderTextNoCaretStr(R.string.storage_wizard_move_progress_title, stringExtra);
        setBodyTextNoCaretStr(
                R.string.storage_wizard_move_progress_body, bestVolumeDescription, stringExtra);
        this.mBack.setVisibility(4);
        this.mNext.setVisibility(4);
        PackageManager packageManager = getPackageManager();
        Handler handler = new Handler();
        AnonymousClass1 anonymousClass1 = this.mCallback;
        packageManager.registerMoveCallback(anonymousClass1, handler);
        anonymousClass1.onStatusChanged(
                this.mMoveId, getPackageManager().getMoveStatus(this.mMoveId), -1L);
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        getPackageManager().unregisterMoveCallback(this.mCallback);
    }
}
