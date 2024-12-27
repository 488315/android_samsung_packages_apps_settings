package com.samsung.android.settings.bixby.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.activityembedding.ActivityEmbeddingUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BixbyTrampoline extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            Log.e("BixbyTrampoline", "intent is null");
            finish();
            return;
        }
        if (intent.getExtras() == null || !intent.getExtras().containsKey("query")) {
            Intent intent2 = (Intent) intent.getParcelableExtra("android.intent.extra.INTENT");
            if (intent2 == null) {
                Log.e("BixbyTrampoline", "startIntent is null");
                finish();
            }
            startActivityForResult(intent2, 100);
        } else if (ActivityEmbeddingUtils.isSettingsSplitEnabled(getApplicationContext())) {
            String stringExtra = intent.getStringExtra("query");
            Intent intent3 = new Intent("com.android.settings.SEARCH_RESULT_TRAMPOLINE");
            intent3.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
            intent3.putExtra("targetAction", "com.android.settings.action.SETTINGS_SEARCH");
            intent3.putExtra("targetPackage", "com.android.settings.intelligence");
            intent3.putExtra(
                    "targetClass", "com.android.settings.intelligence.search.SearchActivity");
            intent3.putExtra("query", stringExtra);
            intent3.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
            startActivityForResult(intent3, 100);
        } else {
            intent.setComponent(
                    new ComponentName(
                            "com.android.settings.intelligence",
                            "com.android.settings.intelligence.search.SearchActivity"));
            intent.addFlags(67108864);
            startActivity(intent);
        }
        finish();
    }
}
