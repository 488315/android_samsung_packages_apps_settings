package com.samsung.android.settings.wifi.mobileap.datamodels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;

import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApAutoHotspotInvitationConfig {
    public long mExpiryTimeToAdd;
    public String mGroupId;
    public String mProfileIconString;
    public long mRequestedTime;
    public String mRequesterName;

    public final String getGroupId() {
        String str = this.mGroupId;
        if (str != null) {
            return str;
        }
        Log.e("WifiApAutoHotspotInvitationConfig", "GroupId is NULL");
        return ApnSettings.MVNO_NONE;
    }

    public final RoundedBitmapDrawable21 getPhoto(Context context) {
        Bitmap bitmap;
        Log.d("WifiApAutoHotspotInvitationConfig", "getPhoto - Triggered");
        String str = this.mProfileIconString;
        if (str == null || str.isEmpty()) {
            Log.d("WifiApAutoHotspotInvitationConfig", "getPhoto() - photoString is null.");
            return null;
        }
        byte[] decode = Base64.decode(this.mProfileIconString, 0);
        if (decode != null) {
            Log.d(
                    "WifiApAutoHotspotInvitationConfig",
                    "getPhoto() - photoString BitmapFactory.decodeByteArray :"
                            + this.mProfileIconString.length());
            bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        } else {
            Log.e("WifiApAutoHotspotInvitationConfig", "getPhoto() - photo is null");
            bitmap = null;
        }
        if (bitmap == null) {
            Log.e("WifiApAutoHotspotInvitationConfig", "getPhoto() - profileImage is null");
            return null;
        }
        RoundedBitmapDrawable21 roundedBitmapDrawable21 =
                new RoundedBitmapDrawable21(context.getResources(), bitmap);
        roundedBitmapDrawable21.setCircular();
        Utils$$ExternalSyntheticOutline0.m(
                new StringBuilder("getPhoto() - photo icon set for "),
                this.mRequesterName,
                "WifiApAutoHotspotInvitationConfig");
        return roundedBitmapDrawable21;
    }

    public final String toString() {
        String groupId = getGroupId();
        String str = this.mRequesterName;
        if (str == null) {
            Log.e("WifiApAutoHotspotInvitationConfig", "RequesterName is NULL");
            str = ApnSettings.MVNO_NONE;
        }
        long j = this.mRequestedTime;
        long j2 = this.mExpiryTimeToAdd;
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "Group ID = ", groupId, ", Requester Name = ", str, ", Requested Time = ");
        m.append(j);
        m.append(", Expired Time = ");
        m.append(j2);
        return m.toString();
    }
}
