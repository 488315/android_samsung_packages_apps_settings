package com.samsung.android.settings.nfc;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcAntennaGuideDialog extends Activity {
    public LottieAnimationView nfcDialogImage;

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == 2) {
            setTheme(2132083384);
        }
        recreate();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("NfcAntennaGuideDialog", "Show NFC antenna position guide dialog from Tile");
        getWindow().setGravity(80);
        getWindow().setLayout(-1, -2);
        setContentView(R.layout.nfc_ant_position_guide_dialog);
        setFinishOnTouchOutside(false);
        this.nfcDialogImage = (LottieAnimationView) findViewById(R.id.iv_anim);
        ((TextView) findViewById(R.id.tv_title)).setText(R.string.nfc_antenna_position_explain_all);
        NfcAntennaInfo nfcAntennaInfo =
                NfcAntennaInfo.values()[SystemProperties.getInt("ro.vendor.nfc.info.antpos", 0)];
        Log.i("NfcAntennaGuideDialog", "Antenna Position : " + nfcAntennaInfo.name());
        this.nfcDialogImage.setAnimation(nfcAntennaInfo.animationJSONValue);
        this.nfcDialogImage.playAnimation$1();
        TextView textView = (TextView) findViewById(R.id.btn_cancel);
        textView.setText(R.string.nfc_antenna_guide_dilaog_ok);
        textView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.nfc.NfcAntennaGuideDialog.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NfcAntennaGuideDialog.this.finish();
                    }
                });
    }
}
