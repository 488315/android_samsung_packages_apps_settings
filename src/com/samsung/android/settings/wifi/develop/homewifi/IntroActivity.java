package com.samsung.android.settings.wifi.develop.homewifi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IntroActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sec_wifi_development_homewifi_intro_activity);
        findViewById(R.id.start)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.develop.homewifi.IntroActivity$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                IntroActivity introActivity = IntroActivity.this;
                                int i = IntroActivity.$r8$clinit;
                                introActivity.getClass();
                                SALogging.insertSALog("WIFI_LABS", "2003");
                                introActivity.startActivity(
                                        new Intent(introActivity, (Class<?>) MainActivity.class));
                                introActivity.finishAfterTransition();
                            }
                        });
    }
}
