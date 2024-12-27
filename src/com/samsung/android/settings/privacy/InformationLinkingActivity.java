package com.samsung.android.settings.privacy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class InformationLinkingActivity extends Activity {
    public TextView mDiagnosticInfoText;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.sec_diagnostic_data_layout);
        this.mDiagnosticInfoText = (TextView) findViewById(R.id.diagnostic_info_body);
        this.mDiagnosticInfoText.setText(
                getString(R.string.diagnostic_information_linking_consent)
                        + "\n\n"
                        + getString(R.string.diagnostic_information_linking_body));
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        Log.i("InformationLinkingActivity", "onOptionsItemSelected() up button pressed");
        finish();
        return true;
    }
}
