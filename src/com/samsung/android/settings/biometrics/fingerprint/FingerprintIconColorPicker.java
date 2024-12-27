package com.samsung.android.settings.biometrics.fingerprint;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.picker3.app.SeslColorPickerDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintIconColorPicker extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SeslColorPickerDialog mColorPickerDialog;
    public final FingerprintIconColorPicker$$ExternalSyntheticLambda1 mColorSetListener =
            new SeslColorPickerDialog
                    .OnColorSetListener() { // from class:
                                            // com.samsung.android.settings.biometrics.fingerprint.FingerprintIconColorPicker$$ExternalSyntheticLambda1
                @Override // androidx.picker3.app.SeslColorPickerDialog.OnColorSetListener
                public final void onColorSet(int i) {
                    int i2 = FingerprintIconColorPicker.$r8$clinit;
                    FingerprintIconColorPicker fingerprintIconColorPicker =
                            FingerprintIconColorPicker.this;
                    fingerprintIconColorPicker.getClass();
                    Log.i(
                            "FpstFingerprintIconColorPicker",
                            "onColorSet : " + Integer.toHexString(i));
                    Intent intent = new Intent();
                    intent.putExtra("selected_color", i);
                    fingerprintIconColorPicker.setResult(-1, intent);
                }
            };
    public int mCurrentColor;
    public int[] mRecentlyUsedColor;
    public boolean mShowOpacitySeekBar;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        setResult(0);
        this.mCurrentColor = intent.getIntExtra("current_color", -1);
        this.mRecentlyUsedColor = intent.getIntArrayExtra("recently_used_color");
        this.mShowOpacitySeekBar = intent.getBooleanExtra("show_opacity_seekbar", false);
        SeslColorPickerDialog seslColorPickerDialog =
                new SeslColorPickerDialog(
                        this,
                        this.mColorSetListener,
                        this.mCurrentColor,
                        this.mRecentlyUsedColor,
                        this.mShowOpacitySeekBar);
        this.mColorPickerDialog = seslColorPickerDialog;
        seslColorPickerDialog.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.biometrics.fingerprint.FingerprintIconColorPicker$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        FingerprintIconColorPicker fingerprintIconColorPicker =
                                FingerprintIconColorPicker.this;
                        int i = FingerprintIconColorPicker.$r8$clinit;
                        fingerprintIconColorPicker.getClass();
                        Log.i("FpstFingerprintIconColorPicker", "ColorPickerDialog : onDismiss");
                        fingerprintIconColorPicker.finish();
                    }
                });
        this.mColorPickerDialog.show();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        finish();
    }
}
