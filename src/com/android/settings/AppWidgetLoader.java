package com.android.settings;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class AppWidgetLoader {
    public AppWidgetManager mAppWidgetManager;
    public Context mContext;
    public ItemConstructor mItemConstructor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.AppWidgetLoader$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        public Collator mCollator;

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return this.mCollator.compare(
                    ((ActivityPicker.PickAdapter.Item) obj).label,
                    ((ActivityPicker.PickAdapter.Item) obj2).label);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ItemConstructor {}

    public final void putAppWidgetItems(List list, List list2, List list3, int i, boolean z) {
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            AppWidgetProviderInfo appWidgetProviderInfo = (AppWidgetProviderInfo) list.get(i2);
            if (z || (appWidgetProviderInfo.widgetCategory & i) != 0) {
                Context context = this.mContext;
                Drawable drawable = null;
                Bundle bundle = list2 != null ? (Bundle) list2.get(i2) : null;
                AppWidgetPickActivity appWidgetPickActivity =
                        (AppWidgetPickActivity) this.mItemConstructor;
                appWidgetPickActivity.getClass();
                String str = appWidgetProviderInfo.label;
                if (appWidgetProviderInfo.icon != 0) {
                    try {
                        int i3 = context.getResources().getDisplayMetrics().densityDpi;
                        if (i3 == 160 || i3 == 213 || i3 == 240 || i3 != 320) {}
                        drawable =
                                appWidgetPickActivity
                                        .mPackageManager
                                        .getResourcesForApplication(
                                                appWidgetProviderInfo.provider.getPackageName())
                                        .getDrawableForDensity(
                                                appWidgetProviderInfo.icon,
                                                (int) ((i3 * 0.75f) + 0.5f));
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.w(
                                "AppWidgetPickActivity",
                                "Can't load icon drawable 0x"
                                        + Integer.toHexString(appWidgetProviderInfo.icon)
                                        + " for provider: "
                                        + appWidgetProviderInfo.provider);
                    }
                    if (drawable == null) {
                        Log.w(
                                "AppWidgetPickActivity",
                                "Can't load icon drawable 0x"
                                        + Integer.toHexString(appWidgetProviderInfo.icon)
                                        + " for provider: "
                                        + appWidgetProviderInfo.provider);
                    }
                }
                ActivityPicker.PickAdapter.Item item =
                        new ActivityPicker.PickAdapter.Item(context, str, drawable);
                item.packageName = appWidgetProviderInfo.provider.getPackageName();
                item.className = appWidgetProviderInfo.provider.getClassName();
                item.extras = bundle;
                ((ArrayList) list3).add(item);
            }
        }
    }

    public final void putCustomAppWidgets(Intent intent, List list) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("customInfo");
        if (parcelableArrayListExtra == null || parcelableArrayListExtra.size() == 0) {
            Log.i("AppWidgetAdapter", "EXTRA_CUSTOM_INFO not present.");
            arrayList = parcelableArrayListExtra;
            arrayList2 = null;
        } else {
            int size = parcelableArrayListExtra.size();
            for (int i = 0; i < size; i++) {
                Parcelable parcelable = (Parcelable) parcelableArrayListExtra.get(i);
                if (parcelable == null || !(parcelable instanceof AppWidgetProviderInfo)) {
                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                            .m(i, "error using EXTRA_CUSTOM_INFO index=", "AppWidgetAdapter");
                    break;
                }
            }
            ArrayList parcelableArrayListExtra2 =
                    intent.getParcelableArrayListExtra("customExtras");
            if (parcelableArrayListExtra2 == null) {
                Log.e("AppWidgetAdapter", "EXTRA_CUSTOM_INFO without EXTRA_CUSTOM_EXTRAS");
                arrayList2 = parcelableArrayListExtra2;
                arrayList = null;
            } else {
                int size2 = parcelableArrayListExtra2.size();
                if (size != size2) {
                    Log.e(
                            "AppWidgetAdapter",
                            "list size mismatch: EXTRA_CUSTOM_INFO: "
                                    + size
                                    + " EXTRA_CUSTOM_EXTRAS: "
                                    + size2);
                } else {
                    for (int i2 = 0; i2 < size2; i2++) {
                        Parcelable parcelable2 = (Parcelable) parcelableArrayListExtra2.get(i2);
                        if (parcelable2 == null || !(parcelable2 instanceof Bundle)) {
                            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                    .m(
                                            i2,
                                            "error using EXTRA_CUSTOM_EXTRAS index=",
                                            "AppWidgetAdapter");
                        }
                    }
                    arrayList2 = parcelableArrayListExtra2;
                    arrayList = parcelableArrayListExtra;
                }
                arrayList = null;
                arrayList2 = null;
            }
        }
        putAppWidgetItems(arrayList, arrayList2, list, 0, true);
    }
}
