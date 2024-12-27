package com.android.settings.applications.specialaccess.deviceadmin;

import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.IconDrawableFactory;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceAdminListItem implements Comparable {
    public final DevicePolicyManager mDPM;
    public final Drawable mIcon;
    public final DeviceAdminInfo mInfo;
    public final String mKey;
    public final CharSequence mName;
    public final UserHandle mUserHandle;

    public DeviceAdminListItem(Context context, DeviceAdminInfo deviceAdminInfo) {
        Drawable defaultActivityIcon;
        this.mInfo = deviceAdminInfo;
        UserHandle userHandle = new UserHandle(getUserIdFromDeviceAdminInfo(deviceAdminInfo));
        this.mUserHandle = userHandle;
        this.mKey =
                userHandle.getIdentifier() + "@" + deviceAdminInfo.getComponent().flattenToString();
        this.mDPM = (DevicePolicyManager) context.getSystemService("device_policy");
        PackageManager packageManager = context.getPackageManager();
        this.mName = deviceAdminInfo.loadLabel(packageManager);
        try {
            deviceAdminInfo.loadDescription(packageManager);
        } catch (Resources.NotFoundException unused) {
            MainClear$$ExternalSyntheticOutline0.m$1(
                    new StringBuilder("Setting description to null because can't find resource: "),
                    this.mKey,
                    "DeviceAdminListItem");
        }
        if (this.mInfo.getActivityInfo().icon != 0) {
            this.mIcon =
                    packageManager.getUserBadgedIcon(
                            this.mInfo.loadIcon(packageManager), this.mUserHandle);
            return;
        }
        IconDrawableFactory newInstance = IconDrawableFactory.newInstance(context);
        String packageName = this.mInfo.getPackageName();
        int userIdFromDeviceAdminInfo = getUserIdFromDeviceAdminInfo(this.mInfo);
        StringBuilder sb = Utils.sBuilder;
        try {
            defaultActivityIcon =
                    newInstance.getBadgedIcon(
                            packageManager.getApplicationInfoAsUser(
                                    packageName, 128, userIdFromDeviceAdminInfo),
                            userIdFromDeviceAdminInfo);
        } catch (PackageManager.NameNotFoundException unused2) {
            defaultActivityIcon = packageManager.getDefaultActivityIcon();
        }
        this.mIcon = defaultActivityIcon;
    }

    public static int getUserIdFromDeviceAdminInfo(DeviceAdminInfo deviceAdminInfo) {
        return UserHandle.getUserId(deviceAdminInfo.getActivityInfo().applicationInfo.uid);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.mName.toString().compareTo(((DeviceAdminListItem) obj).mName.toString());
    }
}
