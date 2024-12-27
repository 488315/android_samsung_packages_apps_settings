package com.samsung.android.settings.homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.SettingsActivity;

import com.samsung.android.settings.Rune;

import java.net.URISyntaxException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDeepLinkHomepageActivity extends SettingsActivity {
    public Intent mDeepLinkIntent;

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = this.mDeepLinkIntent;
        return intent != null ? intent : new Intent();
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        if (Rune.FEATURE_DEEP_LINK_HOMEPAGE) {
            Intent intent = super.getIntent();
            String stringExtra =
                    intent.getStringExtra(
                            "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI");
            if (TextUtils.isEmpty(stringExtra)) {
                Log.e(
                        "SecDeepLinkHomepageActivity",
                        "No EXTRA_SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI to deep link");
            } else {
                try {
                    Intent parseUri = Intent.parseUri(stringExtra, 1);
                    parseUri.setData(
                            (Uri)
                                    intent.getParcelableExtra(
                                            "settings_large_screen_deep_link_intent_data"));
                    String stringExtra2 =
                            intent.getStringExtra(
                                    "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY");
                    if (!TextUtils.isEmpty(stringExtra2)) {
                        this.mHighlightMenuKey = stringExtra2;
                    }
                    if (parseUri.resolveActivity(getPackageManager()) != null) {
                        this.mDeepLinkIntent = parseUri;
                        super.onCreate(bundle);
                        if (isFinishing()) {
                            return;
                        }
                        Intent intent2 = this.mDeepLinkIntent;
                        if (intent2 != null) {
                            startActivity(intent2);
                        }
                        finish();
                        return;
                    }
                    Log.e(
                            "SecDeepLinkHomepageActivity",
                            "No valid target for the deep link intent: " + parseUri);
                } catch (URISyntaxException e) {
                    Log.e("SecDeepLinkHomepageActivity", "Failed to parse deep link intent: " + e);
                }
            }
        }
        finish();
        super.onCreate(bundle);
    }
}
