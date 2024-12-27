package com.samsung.android.settings.voiceinput;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.settings.R;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class VoiceInputSettingsActivity extends AppCompatActivity {
    private static final String TAG = "@VoiceIn:VoiceInputSettingsActivity";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.voice_input_settings_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.voice_input_settings_title));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            SemLog.d(TAG, "titleBar not null");
            getSupportActionBar().setDisplayOptions();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout.setExpanded(false);
        collapsingToolbarLayout.setTitle(
                getResources().getString(R.string.voice_input_settings_title));
        appBarLayout.requestFocus();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
