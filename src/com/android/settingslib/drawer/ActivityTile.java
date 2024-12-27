package com.android.settingslib.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActivityTile extends Tile {
    @Override // com.android.settingslib.drawer.Tile
    public final ComponentInfo getComponentInfo(Context context) {
        if (this.mComponentInfo == null) {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            Intent intent = this.mIntent;
            List<ResolveInfo> queryIntentActivities =
                    packageManager.queryIntentActivities(intent, 128);
            if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
                Log.e(
                        "ActivityTile",
                        "Cannot find package info for " + intent.getComponent().flattenToString());
            } else {
                ActivityInfo activityInfo = queryIntentActivities.get(0).activityInfo;
                this.mComponentInfo = activityInfo;
                this.mMetaData = ((ComponentInfo) activityInfo).metaData;
            }
        }
        return this.mComponentInfo;
    }

    @Override // com.android.settingslib.drawer.Tile
    public final CharSequence getComponentLabel(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ComponentInfo componentInfo = getComponentInfo(context);
        if (componentInfo == null) {
            return null;
        }
        return componentInfo.loadLabel(packageManager);
    }

    @Override // com.android.settingslib.drawer.Tile
    public final String getDescription() {
        return this.mComponentPackage + "/" + this.mComponentName;
    }
}
