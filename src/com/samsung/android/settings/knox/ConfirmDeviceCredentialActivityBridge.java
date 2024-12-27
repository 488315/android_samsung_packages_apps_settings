package com.samsung.android.settings.knox;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.password.ConfirmDeviceCredentialActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ConfirmDeviceCredentialActivityBridge extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            String launchedFromPackage =
                    ActivityManager.getService().getLaunchedFromPackage(getActivityToken());
            if (launchedFromPackage != null) {
                if (launchedFromPackage.equals("com.samsung.knox.securefolder")) {
                    Intent intent =
                            new Intent(
                                    this,
                                    (Class<?>)
                                            ConfirmDeviceCredentialActivity.InternalActivity.class);
                    intent.setFlags(33554432);
                    if (getIntent().getAction() != null) {
                        intent.setAction(getIntent().getAction());
                    }
                    if (getIntent().getExtras() != null) {
                        intent.putExtras(getIntent().getExtras());
                    }
                    Log.d(
                            "KKG::ConfirmDeviceCredentialActivityBridge",
                            "Calling from knox, user id = "
                                    + getIntent()
                                            .getIntExtra("android.intent.extra.USER_ID", -10000));
                    startActivity(intent, ActivityOptions.makeBasic().toBundle());
                    finish();
                    return;
                }
            }
        } catch (Exception unused) {
        }
        finish();
    }
}
