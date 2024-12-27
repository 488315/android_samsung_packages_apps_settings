package com.samsung.android.settings.bixby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.settings.bixby.utils.BixbyUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BixbyPopOver extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            throw new IllegalArgumentException("intent is null");
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            r0 = extras.containsKey("menu") ? extras.getString("menu") : null;
            if (extras.containsKey("targetTaskId")) {
                extras.getInt("targetTaskId");
            }
        }
        if (TextUtils.isEmpty(r0)) {
            BixbyUtils.startSettingsMain(this);
            finish();
        }
        finish();
    }
}
