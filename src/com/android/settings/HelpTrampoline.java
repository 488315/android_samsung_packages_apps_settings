package com.android.settings;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settingslib.HelpUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class HelpTrampoline extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        String stringExtra;
        super.onCreate(bundle);
        try {
            stringExtra = getIntent().getStringExtra("android.intent.extra.TEXT");
        } catch (ActivityNotFoundException | Resources.NotFoundException e) {
            Log.w("HelpTrampoline", "Failed to resolve help", e);
        }
        if (TextUtils.isEmpty(stringExtra)) {
            finishAndRemoveTask();
            return;
        }
        Intent helpIntent =
                HelpUtils.getHelpIntent(
                        this,
                        getResources()
                                .getString(
                                        getResources()
                                                .getIdentifier(
                                                        stringExtra, "string", getPackageName())),
                        null);
        if (helpIntent != null) {
            startActivityForResult(helpIntent, 0);
        }
        finish();
    }
}
