package com.samsung.android.settings.languagepack;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

import com.samsung.android.settings.Rune;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackDialogActivity extends FragmentActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String stringExtra = getIntent().getStringExtra(SpeechRecognitionConst.Key.LOCALE);
        String stringExtra2 = getIntent().getStringExtra("package");
        String stringExtra3 = getIntent().getStringExtra("function");
        if (!Rune.FEATURE_OFFLINE_LANGUAGE_PACK) {
            Toast.makeText(this, R.string.sec_offline_lang_pack_toast_not_support, 0).show();
            finish();
            return;
        }
        if (TextUtils.isEmpty(stringExtra) || TextUtils.isEmpty(stringExtra3)) {
            Log.d(
                    "LanguagePackDialogActivity",
                    "wrong extra code was delivered. Activity finished.");
            finish();
            return;
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "extra localeCode = ", stringExtra, "LanguagePackDialogActivity");
        if (bundle == null) {
            LanguagePackDialogFragment languagePackDialogFragment =
                    new LanguagePackDialogFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("lang_code", stringExtra);
            bundle2.putString("calling_package", stringExtra2);
            bundle2.putString("function", stringExtra3);
            languagePackDialogFragment.setArguments(bundle2);
            languagePackDialogFragment.show(
                    getSupportFragmentManager(), "LanguagePackDialogFragment");
        }
    }
}
