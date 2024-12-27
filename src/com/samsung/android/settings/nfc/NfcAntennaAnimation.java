package com.samsung.android.settings.nfc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.SystemProperties;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NfcAntennaAnimation {
    public LottieAnimationView animation;
    public final int deviceImgHeight;
    public final int deviceImgWidth;
    public final int deviceType;
    public ImageView device_jpn_model_land;
    public ImageView device_jpn_model_vert;
    public final int felicaAntennaX;
    public final int felicaAntennaY;
    public final boolean isJpnPos;
    public float jpnAnimationDimension;
    public LottieAnimationView jpnLandAnimation;
    public LottieAnimationView jpnVertAnimation;
    public final Context mContext;
    public LayoutPreference mLayoutPref;
    public LottieAnimationView iv = null;
    public TextView tv = null;
    public boolean isJpn = false;

    public NfcAntennaAnimation(Context context) {
        this.deviceType = 0;
        this.isJpnPos = false;
        this.mContext = context;
        boolean z = SystemProperties.getBoolean("ro.vendor.nfc.info.deviceFoldable", false);
        String str = SystemProperties.get("ro.product.vendor.device", ApnSettings.MVNO_NONE);
        int i = SystemProperties.getInt("ro.vendor.nfc.info.felicaAntposX", 0);
        this.felicaAntennaX = i;
        int i2 = SystemProperties.getInt("ro.vendor.nfc.info.felicaAntposY", 0);
        this.felicaAntennaY = i2;
        SemLog.d("NfcAntennaAnimation", "modelName " + str);
        boolean z2 =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Resources resources = context.getResources();
        int i3 = this.deviceType;
        BitmapFactory.decodeResource(
                resources,
                i3 == 0
                        ? R.drawable.nfc_felica_phone
                        : i3 == 1 ? R.drawable.nfc_felica_flip : R.drawable.nfc_felica_fold,
                options);
        this.deviceImgWidth = options.outWidth;
        this.deviceImgHeight = options.outHeight;
        if (z) {
            this.deviceType = z2 ? 1 : 2;
        }
        if (i == 0 || i2 == 0) {
            return;
        }
        this.isJpnPos = true;
    }
}
