package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.Utils;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
class ShortcutActivityCandidate extends ShortcutCandidate {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends ShortcutCandidate.Builder {
        public final ActivityInfo activityInfo;
        public int blockedFlag;
        public String className;
        public String exclusiveFeature;
        public int iconRes;
        public int labelRes;

        public Builder(ActivityInfo activityInfo) {
            super(
                    ComponentName.createRelative(activityInfo.packageName, activityInfo.name)
                            .flattenToString());
            this.labelRes = 0;
            this.iconRes = 0;
            this.blockedFlag = 0;
            this.activityInfo = activityInfo;
        }

        @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutCandidate.Builder
        public final ShortcutSimpleEntry buildEntry(Context context) {
            String str;
            String str2 = this.className;
            if (str2 != null) {
                try {
                    int availabilityStatus =
                            BasePreferenceController.createInstance(context, str2, "dummy")
                                    .getAvailabilityStatus();
                    if (availabilityStatus != 0 && availabilityStatus != 1) {
                        AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                                new StringBuilder(),
                                this.className,
                                "returns unavailable.",
                                "ShortcutActivityCandidate");
                        return null;
                    }
                } catch (IllegalStateException unused) {
                    Log.w("ShortcutActivityCandidate", this.className + "is invalid.");
                    return null;
                }
            }
            ActivityInfo activityInfo = this.activityInfo;
            if (activityInfo != null) {
                str = activityInfo.loadLabel(context.getPackageManager()).toString();
            } else {
                try {
                    str = context.getString(this.labelRes);
                } catch (Resources.NotFoundException unused2) {
                    str = ApnSettings.MVNO_NONE;
                }
            }
            return new ShortcutSimpleEntry(this.priority, this.componentName, str);
        }

        @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutCandidate.Builder
        public final ShortcutActivityCandidate build(Context context, int i) {
            ShortcutSimpleEntry buildEntry;
            Drawable drawable = null;
            if ((i & this.blockedFlag) != 0 || (buildEntry = buildEntry(context)) == null) {
                return null;
            }
            ActivityInfo activityInfo = this.activityInfo;
            if (activityInfo != null) {
                drawable = activityInfo.loadIcon(context.getPackageManager());
            } else {
                try {
                    Drawable drawable2 = context.getDrawable(this.iconRes);
                    if (drawable2 != null) {
                        StringBuilder sb = Utils.sBuilder;
                        if (!TextUtils.isEmpty(
                                Settings.System.getString(
                                        context.getContentResolver(),
                                        "current_sec_active_themepackage"))) {
                            drawable =
                                    context.getPackageManager()
                                            .semGetDrawableForIconTray(drawable2, 1);
                        }
                    }
                    drawable = drawable2;
                } catch (Resources.NotFoundException unused) {
                }
            }
            return new ShortcutActivityCandidate(
                    buildEntry, drawable, this.exclusiveFeature, this.category);
        }

        public Builder(String str) {
            super(str);
            this.labelRes = 0;
            this.iconRes = 0;
            this.blockedFlag = 0;
            this.activityInfo = null;
        }
    }
}
