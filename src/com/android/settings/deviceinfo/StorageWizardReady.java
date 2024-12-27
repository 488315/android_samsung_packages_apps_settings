package com.android.settings.deviceinfo;

import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.security.SemSdCardEncryption;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardReady extends StorageWizardBase {
    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mDisk == null) {
            finish();
            return;
        }
        setContentView(R.layout.storage_wizard_generic);
        setHeaderTextNoCaretStr(R.string.storage_wizard_ready_title, getDiskShortDescription());
        if (findFirstVolume(1) == null) {
            setBodyTextNoCaretStr(
                    R.string.storage_wizard_ready_v2_external_body, getDiskDescription());
        } else if (getIntent().getBooleanExtra("migrate_skip", false)) {
            setBodyTextNoCaretStr(
                    R.string.storage_wizard_ready_v2_internal_body, getDiskDescription());
        } else {
            setBodyTextNoCaretStr(
                    R.string.storage_wizard_ready_v2_internal_moved_body,
                    getDiskDescription(),
                    getDiskShortDescription());
        }
        ((ImageView) findViewById(R.id.storage_wizard_body_image))
                .setImageResource(R.drawable.ic_storage_wizard_ready);
        setIcon(R.drawable.ic_test_tick);
        setNextButtonText(R.string.sec_common_done, new CharSequence[0]);
        this.mBack.setVisibility(4);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService("device_policy");
        SemSdCardEncryption semSdCardEncryption = new SemSdCardEncryption(getApplicationContext());
        boolean z =
                devicePolicyManager != null
                        && devicePolicyManager.semGetRequireStorageCardEncryption(null);
        boolean z2 =
                semSdCardEncryption.isEncryptionSupported()
                        && semSdCardEncryption.isSdCardEncryped();
        Utils$$ExternalSyntheticOutline0.m653m(
                "isAdminPolicyApplied: ", z, " / isSdCardEncrypted: ", z2, "StorageWizardReady");
        if (!z || z2) {
            return;
        }
        Intent intent = new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION");
        intent.setFlags(4325376);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("StorageWizardReady", "Failed to start intent activity" + e);
        }
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateNext(View view) {
        finishAffinity();
    }
}
