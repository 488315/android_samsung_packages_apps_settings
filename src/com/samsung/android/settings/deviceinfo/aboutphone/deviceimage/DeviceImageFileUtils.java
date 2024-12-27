package com.samsung.android.settings.deviceinfo.aboutphone.deviceimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DeviceImageFileUtils {
    public static void compressImage(Bitmap bitmap, String str) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap decoded from fileDescriptor is null");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            Log.w("DeviceImageFileUtils", "compressImage failed : " + e.getMessage());
        }
    }

    public static String getImageFilePath(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getApplicationContext().getFilesDir());
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                sb, File.separator, "DeviceImage.png");
    }

    public static boolean isImageFileExist(Context context) {
        boolean m =
                SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                        getImageFilePath(context));
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isImageFileExist : ", "DeviceImageFileUtils", m);
        return m;
    }
}
