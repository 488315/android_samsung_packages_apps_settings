package com.samsung.android.settings.wifi.intelligent;

import android.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MobileWipsAlertDialog extends AppCompatActivity {
    public MobileWipsAlertDialogFragment mFragment;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MobileWipsAlertDialogFragment mobileWipsAlertDialogFragment =
                new MobileWipsAlertDialogFragment();
        this.mFragment = mobileWipsAlertDialogFragment;
        mobileWipsAlertDialogFragment.show(getSupportFragmentManager(), "MobileWipsAlertDialog");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("MobileWipsAlertDialog", "Called onNewIntent()");
        if (this.mFragment != null) {
            setIntent(intent);
            MobileWipsAlertDialogFragment mobileWipsAlertDialogFragment = this.mFragment;
            mobileWipsAlertDialogFragment.getClass();
            Log.d("MobileWipsAlertDialogFragment", "update: Dialog update intent");
            String stringExtra = intent.getStringExtra("ssid");
            if (stringExtra == null) {
                Log.e("MobileWipsAlertDialogFragment", "update: SSID is null");
                mobileWipsAlertDialogFragment.dismissInternal(false, false);
            } else {
                TextView textView =
                        (TextView) mobileWipsAlertDialogFragment.mDialog.findViewById(R.id.message);
                if (textView != null) {
                    textView.setText(
                            mobileWipsAlertDialogFragment
                                    .getActivity()
                                    .getResources()
                                    .getString(
                                            com.android.settings.R.string
                                                    .wifi_mobile_wips_detection_popup_content,
                                            stringExtra));
                }
                mobileWipsAlertDialogFragment.mDialog.show();
            }
        }
    }
}
