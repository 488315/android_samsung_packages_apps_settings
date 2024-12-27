package com.samsung.android.settings.share.common;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.samsung.android.settings.accessibility.home.InstalledAppsPreferenceController$$ExternalSyntheticLambda0;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ResolveInfoTargetPresentationGetter {
    public final ApplicationInfo mAi;
    public final Context mCtx;
    public final boolean mHasSubstitutePermission;
    public final int mIconDpi;
    public final PackageManager mPm;
    public final ResolveInfo mRi;

    public ResolveInfoTargetPresentationGetter(Context context, int i, ResolveInfo resolveInfo) {
        ApplicationInfo applicationInfo = resolveInfo.getComponentInfo().applicationInfo;
        this.mCtx = context;
        PackageManager packageManager = context.getPackageManager();
        this.mPm = packageManager;
        this.mAi = applicationInfo;
        this.mIconDpi = i;
        this.mHasSubstitutePermission =
                packageManager.checkPermission(
                                "android.permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON",
                                applicationInfo.packageName)
                        == 0;
        this.mRi = resolveInfo;
    }

    public static ResolveInfoTargetPresentationGetter makePresentationGetter(
            final int i,
            final ComponentName componentName,
            final Context context,
            final List list) {
        final int launcherLargeIconDensity =
                ((ActivityManager) context.getSystemService("activity"))
                        .getLauncherLargeIconDensity();
        return (ResolveInfoTargetPresentationGetter)
                list.stream()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.settings.share.common.TargetPresentationGetter$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        List list2 = list;
                                        ComponentName componentName2 = componentName;
                                        Context context2 = context;
                                        int i2 = i;
                                        Log.i(
                                                "TargetPresentationGetter",
                                                "originalIntent : " + list2);
                                        Intent intent = new Intent((Intent) obj);
                                        intent.setComponent(componentName2);
                                        return context2.getPackageManager()
                                                .resolveActivityAsUser(intent, 0, i2);
                                    }
                                })
                        .filter(new InstalledAppsPreferenceController$$ExternalSyntheticLambda0(1))
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.settings.share.common.TargetPresentationGetter$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Context context2 = context;
                                        int i2 = launcherLargeIconDensity;
                                        ResolveInfo resolveInfo = (ResolveInfo) obj;
                                        Log.i(
                                                "TargetPresentationGetter",
                                                "ri : " + resolveInfo.toString());
                                        return new ResolveInfoTargetPresentationGetter(
                                                context2, i2, resolveInfo);
                                    }
                                })
                        .findFirst()
                        .orElseGet(
                                new Supplier() { // from class:
                                                 // com.samsung.android.settings.share.common.TargetPresentationGetter$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        Log.i(
                                                "TargetPresentationGetter",
                                                "Failed to find resolveInfo with (componentName = "
                                                        + componentName
                                                        + ", userId = "
                                                        + i);
                                        return null;
                                    }
                                });
    }

    public final String getLabel() {
        String charSequence =
                this.mHasSubstitutePermission
                        ? this.mRi.getComponentInfo().loadLabel(this.mPm).toString()
                        : null;
        return charSequence == null ? (String) this.mAi.loadLabel(this.mPm) : charSequence;
    }

    public final Drawable loadIconFromResource(Resources resources, int i) {
        try {
            int i2 = this.mIconDpi;
            Resources.Theme theme = this.mCtx.getTheme();
            ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
            return resources.getDrawableForDensity(i, i2, theme);
        } catch (Resources.NotFoundException e) {
            Log.w("TargetPresentationGetter", "loadIconFromResource: " + e);
            return null;
        }
    }
}
