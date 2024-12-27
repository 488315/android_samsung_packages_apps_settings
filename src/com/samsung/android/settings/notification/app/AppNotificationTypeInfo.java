package com.samsung.android.settings.notification.app;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppNotificationTypeInfo {
    public Long contactId;
    public String contactNumber;
    public Bitmap contactPhoto;
    public int imageEntry;
    public Drawable mPackageIcon;
    public String notificationType;
    public int uId;
    public Boolean selected = Boolean.TRUE;
    public String title = ApnSettings.MVNO_NONE;
    public String mPackage = ApnSettings.MVNO_NONE;
    public Boolean isAddAppException = Boolean.FALSE;
}
