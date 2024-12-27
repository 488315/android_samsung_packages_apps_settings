package com.samsung.android.settings.biometrics;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsDisclaimerSettingsActivity extends BiometricsDisclaimerActivity {
    public ActionBar mActionBar;

    public final void changeChildViewTextColor(int i) {
        LinearLayout linearLayout = (LinearLayout) findViewById(i);
        if (linearLayout != null) {
            for (int i2 = 0; i2 < linearLayout.getChildCount(); i2++) {
                View childAt = linearLayout.getChildAt(i2);
                if (childAt instanceof TextView) {
                    ((TextView) childAt)
                            .setTextColor(
                                    getColor(
                                            R.color
                                                    .sec_biometrics_guide_common_info_text_theme_color));
                }
            }
        }
    }

    @Override // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int i = this.mRequestBiometricsType;
        if (i == 1) {
            setTitle(R.string.sec_biometrics_disclaimer_option_title_fingerprint);
        } else if (i == 256) {
            setTitle(R.string.sec_face_settings_option_about_face_recognition);
        }
        this.mActionBar = getSupportActionBar();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Log.d("BiometricsDisclaimerActivity", "ActionBar clicked");
            finishBiometricsDisclaimer(-1);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mActionBar.setDisplayHomeAsUpEnabled(true);
        this.mActionBar.setHomeButtonEnabled();
        if (this.mRequestBiometricsType == 1) {
            changeChildViewTextColor(R.id.biometrics_disclaimer_finger_learn_more);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_finger_info);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_desc_fingerprint_1);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_desc_fingerprint_2);
        }
        if (this.mRequestBiometricsType == 256) {
            changeChildViewTextColor(R.id.biometrics_disclaimer_face_learn_more);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_face_info);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_desc_face_1);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_desc_face_2);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_desc_face_3);
            changeChildViewTextColor(R.id.biometrics_disclaimer_more_desc_face_4);
        }
    }
}
