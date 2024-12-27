package com.android.settings.development;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DSUTermsOfServiceActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dsu_terms_of_service);
        TextView textView = (TextView) findViewById(R.id.tos_content);
        final Intent intent = getIntent();
        if (!intent.hasExtra("KEY_TOS")) {
            finish();
        }
        String stringExtra = intent.getStringExtra("KEY_TOS");
        if (!TextUtils.isEmpty(stringExtra)) {
            textView.setText(stringExtra);
            ((Button) findViewById(R.id.accept))
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.development.DSUTermsOfServiceActivity.1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    DSUTermsOfServiceActivity dSUTermsOfServiceActivity =
                                            DSUTermsOfServiceActivity.this;
                                    Intent intent2 = intent;
                                    int i = DSUTermsOfServiceActivity.$r8$clinit;
                                    dSUTermsOfServiceActivity.getClass();
                                    intent2.setClassName(
                                            "com.android.dynsystem",
                                            "com.android.dynsystem.VerificationActivity");
                                    dSUTermsOfServiceActivity.startActivity(intent2);
                                    dSUTermsOfServiceActivity.finish();
                                }
                            });
        } else {
            intent.setClassName(
                    "com.android.dynsystem", "com.android.dynsystem.VerificationActivity");
            startActivity(intent);
            finish();
        }
    }
}
