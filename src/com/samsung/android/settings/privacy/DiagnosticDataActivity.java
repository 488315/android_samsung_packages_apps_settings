package com.samsung.android.settings.privacy;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.settings.R;

import com.sec.android.app.swlpcontract.SWLPContract;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DiagnosticDataActivity extends AppCompatActivity {
    public TextView mDiagnosticInfoText;
    public View mScrollView;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sec_diagnostic_data_layout);
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mDiagnosticInfoText = (TextView) findViewById(R.id.diagnostic_info_body);
        View findViewById = findViewById(R.id.diagnostic_info_scroll);
        this.mScrollView = findViewById;
        findViewById.semSetRoundedCorners(15);
        this.mScrollView.semSetRoundedCornerColor(
                15, getApplication().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        int intExtra = getIntent().getIntExtra("swlp_option", -1);
        if (intExtra != -1) {
            setTitle(R.string.link_details);
        }
        this.mDiagnosticInfoText.setText(
                intExtra == 0
                        ? SWLPContract.getStringByUri(
                                this, SWLPContract.URI_DIAGNONSENSITIVE_GET, false)
                        : SWLPContract.getStringByUri(this, SWLPContract.URI_DIAG_GET, false));
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        Log.i("DiagnosticDataActivity", "onOptionsItemSelected() up button pressed");
        finish();
        return true;
    }
}
