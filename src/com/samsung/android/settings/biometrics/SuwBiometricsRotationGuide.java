package com.samsung.android.settings.biometrics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SuwBiometricsRotationGuide extends BiometricsRotationGuide
        implements View.OnClickListener {
    public Button mNextButton;

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.next_button) {
            Log.d("SuwBiometricsRotationGuide", "Next button clicked");
            finishRotationGuide(-1);
        }
    }

    @Override // com.samsung.android.settings.biometrics.BiometricsRotationGuide,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SuwBiometricsRotationGuide", "onCreate");
        Button button = (Button) findViewById(R.id.next_button);
        this.mNextButton = button;
        if (button != null) {
            button.setOnClickListener(this);
            float f = getResources().getConfiguration().fontScale;
            Log.d("BiometricsGenericHelper", "getAdjustedFontScale : " + f + ", 1.3");
            float f2 = f > 1.3f ? 1.3f / f : 1.0f;
            Log.d("BiometricsGenericHelper", "getAdjustedFontScale : " + f2);
            Button button2 = this.mNextButton;
            if (button2 != null) {
                button2.setTextSize(
                        0,
                        getResources()
                                        .getDimensionPixelSize(
                                                R.dimen
                                                        .sec_biometrics_guide_common_continue_button_text_size)
                                * f2);
            }
        }
    }
}
