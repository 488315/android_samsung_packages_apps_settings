package com.samsung.android.settings.wifi.mobileap.datamodels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import androidx.core.graphics.drawable.RoundedBitmapDrawable21;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApFamilyMember {
    public final Context mContext;
    public final String mGuid;
    public final String mName;
    public final String mPhotoString;

    public WifiApFamilyMember(Context context, String str, String str2, String str3) {
        this.mContext = context;
        this.mGuid = str;
        this.mName = str2;
        this.mPhotoString = str3;
    }

    public final Drawable getPhoto() {
        String str = this.mPhotoString;
        if (str == null || str.isEmpty()) {
            return this.mContext.getDrawable(R.drawable.sec_wifi_ap_profile_default_image);
        }
        byte[] decode = Base64.decode(str, 0);
        Bitmap decodeByteArray =
                decode != null ? BitmapFactory.decodeByteArray(decode, 0, decode.length) : null;
        if (decodeByteArray == null) {
            Log.e("WifiApFamilyMember", "getPhoto() - profileImage is null");
            return null;
        }
        RoundedBitmapDrawable21 roundedBitmapDrawable21 =
                new RoundedBitmapDrawable21(this.mContext.getResources(), decodeByteArray);
        roundedBitmapDrawable21.setCircular();
        return roundedBitmapDrawable21;
    }
}
