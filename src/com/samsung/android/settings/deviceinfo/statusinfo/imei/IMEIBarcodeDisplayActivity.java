package com.samsung.android.settings.deviceinfo.statusinfo.imei;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.core.InstrumentedActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.io.UnsupportedEncodingException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class IMEIBarcodeDisplayActivity extends InstrumentedActivity
        implements DialogInterface.OnDismissListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 40;
    }

    @Override // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String str;
        BitMatrix bitMatrix;
        super.onCreate(bundle);
        if (!Rune.isJapanDCMModel()) {
            finish();
            return;
        }
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        String string = extras.getString("imei_barcode_value");
        if (TextUtils.isEmpty(string)) {
            finish();
            return;
        }
        AlertDialog.Builder onDismissListener =
                new AlertDialog.Builder(this)
                        .setTitle(R.string.status_imei)
                        .setOnDismissListener(this);
        Bitmap bitmap = null;
        View inflate = getLayoutInflater().inflate(R.layout.sec_imei_barcode, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imei_barcode);
        TextView textView = (TextView) inflate.findViewById(R.id.imei_textview);
        if (imageView != null) {
            try {
                str = new String(string.getBytes("UTF-8"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                str = ApnSettings.MVNO_NONE;
            }
            try {
                bitMatrix =
                        new MultiFormatWriter()
                                .encode(str, BarcodeFormat.CODE_128, 1920, FileType.XLSX, null);
            } catch (WriterException e2) {
                e2.printStackTrace();
                bitMatrix = null;
            }
            if (bitMatrix != null) {
                int i = bitMatrix.width;
                int i2 = bitMatrix.height;
                int[] iArr = new int[i * i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    int i4 = i3 * i;
                    for (int i5 = 0; i5 < i; i5++) {
                        iArr[i4 + i5] = bitMatrix.get(i5, i3) ? EmergencyPhoneWidget.BG_COLOR : -1;
                    }
                }
                bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                bitmap.setPixels(iArr, 0, i, 0, 0, i, i2);
            }
            imageView.setImageBitmap(bitmap);
        }
        if (textView != null) {
            textView.setText(string);
        }
        onDismissListener.setView(inflate);
        onDismissListener.show();
    }
}
