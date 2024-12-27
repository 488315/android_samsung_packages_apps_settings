package com.samsung.android.settings.privacy;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PrivacyDashboardTrampoline extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!TextUtils.equals("com.samsung.android.privacydashboard", getCallingPackage())) {
            Log.d("PrivacyDashboardTrampoline", "intents must be called with from allowed package");
            finish();
        }
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("trampolineAction");
        String stringExtra2 = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
        String stringExtra3 = intent.getStringExtra("android.intent.extra.PERMISSION_NAME");
        String stringExtra4 = intent.getStringExtra("android.intent.extra.PERMISSION_GROUP_NAME");
        boolean booleanExtra = intent.getBooleanExtra("hideInfoButton", false);
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
        String stringExtra5 =
                intent.getStringExtra("com.android.permissioncontroller.extra.CALLER_NAME");
        Intent intent2 = new Intent();
        intent2.setAction(stringExtra);
        if (!TextUtils.isEmpty(stringExtra2)) {
            intent2.putExtra("android.intent.extra.PACKAGE_NAME", stringExtra2);
        }
        if (!TextUtils.isEmpty(stringExtra3)) {
            intent2.putExtra("android.intent.extra.PERMISSION_NAME", stringExtra3);
        }
        if (booleanExtra) {
            intent2.putExtra("hideInfoButton", true);
        }
        if (!TextUtils.isEmpty(stringExtra4)) {
            intent2.putExtra("android.intent.extra.PERMISSION_GROUP_NAME", stringExtra4);
        }
        if (userHandle != null) {
            intent2.putExtra("android.intent.extra.USER", userHandle);
        }
        if (!TextUtils.isEmpty(stringExtra5)) {
            intent2.putExtra("com.android.permissioncontroller.extra.CALLER_NAME", stringExtra5);
        }
        try {
            try {
                startActivity(intent2);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Log.d("PrivacyDashboardTrampoline", "Activity not found exception");
            }
        } finally {
            finish();
        }
    }
}
