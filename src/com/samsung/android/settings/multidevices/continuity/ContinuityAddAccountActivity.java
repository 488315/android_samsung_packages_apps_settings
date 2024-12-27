package com.samsung.android.settings.multidevices.continuity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ContinuityAddAccountActivity extends Activity {
    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1) {
            return;
        }
        if (i2 == -1) {
            ContinuitySettings.setContinuitySettingValue(this, 1);
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int intExtra = getIntent().getIntExtra("requestCode", 0);
        Intent intent = new Intent("com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT");
        intent.setPackage("com.osp.app.signin");
        intent.putExtra("mypackage", getPackageName());
        intent.putExtra("client_id", "s5d189ajvs");
        intent.putExtra("OSP_VER", "OSP_02");
        intent.putExtra("MODE", "ADD_ACCOUNT");
        intent.putExtra("Is_From_SA_Verify", true);
        try {
            startActivityForResult(intent, intExtra);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
