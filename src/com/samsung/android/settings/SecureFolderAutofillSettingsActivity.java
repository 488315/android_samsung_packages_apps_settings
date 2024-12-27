package com.samsung.android.settings;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecureFolderAutofillSettingsActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null
                || getCallingActivity() == null
                || !getCallingActivity().getPackageName().equals("com.samsung.knox.securefolder")) {
            finish();
            return;
        }
        String stringExtra = intent.getStringExtra("autofill_package");
        String stringExtra2 = intent.getStringExtra("autofill_settings_activity");
        if (stringExtra == null || stringExtra2 == null) {
            return;
        }
        android.util.secutil.Log.secD(
                "SecureFolderAutofillSettingsActivity",
                "autofill servicePackage in securefolder = ".concat(stringExtra));
        android.util.secutil.Log.secD(
                "SecureFolderAutofillSettingsActivity",
                "autofill servicePackage in securefolder = ".concat(stringExtra2));
        startActivity(
                new Intent("android.intent.action.MAIN")
                        .setComponent(new ComponentName(stringExtra, stringExtra2)));
        finish();
    }
}
