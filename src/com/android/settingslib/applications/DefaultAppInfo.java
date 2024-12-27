package com.android.settingslib.applications;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.util.IconDrawableFactory;

import com.android.settingslib.widget.CandidateInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultAppInfo extends CandidateInfo {
    public final ComponentName componentName;
    public final Context mContext;
    public final PackageManager mPm;
    public final PackageItemInfo packageItemInfo;
    public final String summary;
    public final int userId;

    public DefaultAppInfo(
            Context context, PackageManager packageManager, int i, ComponentName componentName) {
        this(context, packageManager, i, componentName, 0);
    }

    public final ComponentInfo getComponentInfo() {
        int i = this.userId;
        try {
            ActivityInfo activityInfo =
                    AppGlobals.getPackageManager().getActivityInfo(this.componentName, 0L, i);
            return activityInfo == null
                    ? AppGlobals.getPackageManager().getServiceInfo(this.componentName, 0L, i)
                    : activityInfo;
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // com.android.settingslib.widget.CandidateInfo
    public String getKey() {
        ComponentName componentName = this.componentName;
        if (componentName != null) {
            return componentName.flattenToString();
        }
        PackageItemInfo packageItemInfo = this.packageItemInfo;
        if (packageItemInfo != null) {
            return packageItemInfo.packageName;
        }
        return null;
    }

    @Override // com.android.settingslib.widget.CandidateInfo
    public Drawable loadIcon() {
        IconDrawableFactory newInstance = IconDrawableFactory.newInstance(this.mContext);
        ComponentName componentName = this.componentName;
        int i = this.userId;
        if (componentName != null) {
            try {
                ComponentInfo componentInfo = getComponentInfo();
                ApplicationInfo applicationInfoAsUser =
                        this.mPm.getApplicationInfoAsUser(
                                this.componentName.getPackageName(), 0, i);
                return componentInfo != null
                        ? newInstance.getBadgedIcon(componentInfo, applicationInfoAsUser, i)
                        : newInstance.getBadgedIcon(applicationInfoAsUser);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }
        PackageItemInfo packageItemInfo = this.packageItemInfo;
        if (packageItemInfo != null) {
            try {
                return newInstance.getBadgedIcon(
                        this.packageItemInfo,
                        this.mPm.getApplicationInfoAsUser(packageItemInfo.packageName, 0, i),
                        i);
            } catch (PackageManager.NameNotFoundException unused2) {
            }
        }
        return null;
    }

    @Override // com.android.settingslib.widget.CandidateInfo
    public CharSequence loadLabel() {
        if (this.componentName != null) {
            try {
                ComponentInfo componentInfo = getComponentInfo();
                return componentInfo != null
                        ? componentInfo.loadLabel(this.mPm)
                        : this.mPm
                                .getApplicationInfoAsUser(
                                        this.componentName.getPackageName(), 0, this.userId)
                                .loadLabel(this.mPm);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }
        PackageItemInfo packageItemInfo = this.packageItemInfo;
        if (packageItemInfo != null) {
            return packageItemInfo.loadLabel(this.mPm);
        }
        return null;
    }

    public DefaultAppInfo(
            Context context,
            PackageManager packageManager,
            int i,
            PackageItemInfo packageItemInfo) {
        this(context, packageManager, i, packageItemInfo, null, true);
    }

    public DefaultAppInfo(
            Context context,
            PackageManager packageManager,
            int i,
            ComponentName componentName,
            int i2) {
        super(true);
        this.mContext = context;
        this.mPm = packageManager;
        this.packageItemInfo = null;
        this.userId = i;
        this.componentName = componentName;
        this.summary = null;
    }

    public DefaultAppInfo(
            Context context,
            PackageManager packageManager,
            int i,
            PackageItemInfo packageItemInfo,
            String str,
            boolean z) {
        super(z);
        this.mContext = context;
        this.mPm = packageManager;
        this.userId = i;
        this.packageItemInfo = packageItemInfo;
        this.componentName = null;
        this.summary = str;
    }
}
