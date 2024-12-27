package com.samsung.android.settings.wifi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

import java.util.Hashtable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiQrCodeGenerator {
    public final Context mContext;

    public WifiQrCodeGenerator(Context context) {
        this.mContext = context;
    }

    public static String escapeSpecialCharacters(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\\' || charAt == ',' || charAt == ';' || charAt == ':') {
                sb.append('\\');
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getQrCodeString(android.net.wifi.WifiConfiguration r9) {
        /*
            if (r9 != 0) goto L4
            r9 = 0
            return r9
        L4:
            java.util.BitSet r0 = r9.allowedKeyManagement
            r1 = 8
            boolean r0 = r0.get(r1)
            java.lang.String r1 = "WEP"
            r2 = 0
            if (r0 == 0) goto L15
            java.lang.String r0 = "SAE"
        L13:
            r3 = r0
            goto L4f
        L15:
            java.util.BitSet r0 = r9.allowedKeyManagement
            r3 = 9
            boolean r0 = r0.get(r3)
            java.lang.String r3 = "nopass"
            if (r0 == 0) goto L22
            goto L4f
        L22:
            java.util.BitSet r0 = r9.allowedKeyManagement
            r4 = 1
            boolean r0 = r0.get(r4)
            if (r0 != 0) goto L4c
            java.util.BitSet r0 = r9.allowedKeyManagement
            r4 = 4
            boolean r0 = r0.get(r4)
            if (r0 == 0) goto L35
            goto L4c
        L35:
            java.util.BitSet r0 = r9.allowedKeyManagement
            r4 = 13
            boolean r0 = r0.get(r4)
            if (r0 == 0) goto L42
            java.lang.String r0 = "WAPI"
            goto L13
        L42:
            java.lang.String[] r0 = r9.wepKeys
            r0 = r0[r2]
            if (r0 != 0) goto L4a
            r0 = r3
            goto L13
        L4a:
            r0 = r1
            goto L13
        L4c:
            java.lang.String r0 = "WPA"
            goto L13
        L4f:
            boolean r0 = r1.equals(r3)
            if (r0 == 0) goto L6b
            java.lang.String r4 = r9.getPrintableSsid()
            java.lang.String[] r0 = r9.wepKeys
            r0 = r0[r2]
            java.lang.String r5 = com.samsung.android.wifitrackerlib.SemWifiUtils.removeDoubleQuotes(r0)
            boolean r6 = r9.hiddenSSID
            int r7 = r9.networkId
            r8 = 0
            com.android.settings.wifi.dpp.WifiNetworkConfig r9 = com.android.settings.wifi.dpp.WifiNetworkConfig.getValidConfigOrNull(r3, r4, r5, r6, r7, r8)
            goto L7e
        L6b:
            java.lang.String r4 = r9.getPrintableSsid()
            java.lang.String r0 = r9.preSharedKey
            java.lang.String r5 = com.samsung.android.wifitrackerlib.SemWifiUtils.removeDoubleQuotes(r0)
            boolean r6 = r9.hiddenSSID
            int r7 = r9.networkId
            r8 = 0
            com.android.settings.wifi.dpp.WifiNetworkConfig r9 = com.android.settings.wifi.dpp.WifiNetworkConfig.getValidConfigOrNull(r3, r4, r5, r6, r7, r8)
        L7e:
            java.lang.String r9 = r9.getQrCode()
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiQrCodeGenerator.getQrCodeString(android.net.wifi.WifiConfiguration):java.lang.String");
    }

    public final Bitmap createBitmapFromResource(int i) {
        Drawable drawable = this.mContext.getDrawable(i);
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public final Bitmap createQrcodeImage(String str, boolean z) {
        Bitmap createBitmapFromResource =
                z
                        ? createBitmapFromResource(R.drawable.sec_qr_code_wifi)
                        : createBitmapFromResource(R.drawable.sec_qr_code_mobile_hotspot);
        Bitmap createBitmapFromResource2 = createBitmapFromResource(R.drawable.sec_qr_code_anchor);
        Bitmap bitmap = null;
        try {
            Hashtable hashtable = new Hashtable();
            hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
            ByteMatrix byteMatrix = Encoder.encode(str, ErrorCorrectionLevel.H, hashtable).matrix;
            int dimensionPixelSize =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.wifi_ap_qr_code_size);
            bitmap =
                    Bitmap.createBitmap(
                            dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = getPaint();
            paint.setColor(EmergencyPhoneWidget.BG_COLOR);
            float width = (float) ((bitmap.getWidth() * 1.0d) / byteMatrix.width);
            double d = width;
            float f = (float) (0.382d * d);
            float f2 = (float) (d / 2.0d);
            for (int i = 0; i < byteMatrix.height; i++) {
                for (int i2 = 0; i2 < byteMatrix.width; i2++) {
                    if (byteMatrix.get(i2, i) == 1) {
                        canvas.drawCircle((i2 * width) + f2, (i * width) + f2, f, paint);
                    }
                }
            }
            drawAnchor(bitmap, createBitmapFromResource2, byteMatrix);
            drawLogo(bitmap, createBitmapFromResource);
        } catch (WriterException e) {
            Log.e("WifiQrCodeGenerator", "Exception in encoding QR code");
            e.printStackTrace();
        }
        return bitmap;
    }

    public final void drawAnchor(Bitmap bitmap, Bitmap bitmap2, ByteMatrix byteMatrix) {
        if (bitmap2 == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = (float) ((width * 1.0d) / byteMatrix.width);
        int i = 0;
        for (int i2 = 0; i2 < byteMatrix.width && byteMatrix.get(i2, 0) == 1; i2++) {
            i++;
        }
        int i3 = (int) (i * f);
        float f2 = i3;
        float width2 = (1.0f * f2) / bitmap2.getWidth();
        Paint paint = getPaint();
        RectF rectF = new RectF(0.0f, 0.0f, f2, f2);
        float f3 = width - i3;
        RectF rectF2 = new RectF(f3, 0.0f, width, f2);
        float f4 = height - i3;
        RectF rectF3 = new RectF(0.0f, f4, f2, height);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(rectF, paint);
        canvas.drawRect(rectF2, paint);
        canvas.drawRect(rectF3, paint);
        Matrix matrix = new Matrix();
        matrix.postScale(width2, width2);
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createBitmap, f3, 0.0f, (Paint) null);
        canvas.drawBitmap(createBitmap, 0.0f, f4, (Paint) null);
        createBitmap.recycle();
        bitmap2.recycle();
    }

    public final void drawLogo(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap2 == null) {
            return;
        }
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight() / 2;
        int height2 = (bitmap.getHeight() / 2) - height;
        int i = width / 2;
        int width2 = (bitmap.getWidth() / 2) - i;
        int max =
                Math.max(i, height)
                        + this.mContext
                                .getResources()
                                .getDimensionPixelSize(R.dimen.wifi_ap_qr_code_logo_padding);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, max, getPaint());
        canvas.drawBitmap(bitmap2, width2, height2, (Paint) null);
        bitmap2.recycle();
    }

    public final Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(
                this.mContext.getResources().getColor(R.color.wifi_ap_qrcode_background_color));
        return paint;
    }
}
