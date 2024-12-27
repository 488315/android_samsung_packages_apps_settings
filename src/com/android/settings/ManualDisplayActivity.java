package com.android.settings;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ManualDisplayActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!getResources().getBoolean(R.bool.config_show_manual)) {
            finish();
        }
        File file = new File("/system/etc/MANUAL.html.gz");
        if (!file.exists() || file.length() == 0) {
            Log.e(
                    "SettingsManualActivity",
                    "Manual file /system/etc/MANUAL.html.gz does not exist");
            Toast.makeText(this, R.string.settings_manual_activity_unavailable, 1).show();
            finish();
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "text/html");
        intent.putExtra(
                "android.intent.extra.TITLE", getString(R.string.settings_manual_activity_title));
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage("com.android.htmlviewer");
        try {
            startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
            Log.e("SettingsManualActivity", "Failed to find viewer", e);
            Toast.makeText(this, R.string.settings_manual_activity_unavailable, 1).show();
            finish();
        }
    }
}
