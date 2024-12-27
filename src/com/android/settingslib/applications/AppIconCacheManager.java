package com.android.settingslib.applications;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.Log;
import android.util.LruCache;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppIconCacheManager {
    public static int mCurCacheSize;
    public static AppIconCacheManager sAppIconCacheManager;
    public final AnonymousClass1 mDrawableCache = new AnonymousClass1(MAX_CACHE_SIZE_IN_KB);
    static final int MAX_CACHE_SIZE_IN_KB =
            Math.round((Runtime.getRuntime().maxMemory() * 0.1f) / 1024.0f);
    public static final int ICON_SIZE_LIMIT =
            IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.AppIconCacheManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends LruCache {
        @Override // android.util.LruCache
        public final int sizeOf(Object obj, Object obj2) {
            Drawable drawable = (Drawable) obj2;
            if (drawable instanceof BitmapDrawable) {
                return (((BitmapDrawable) drawable).getBitmap().getByteCount() / 1024) + 1;
            }
            return (((drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight()) * 4) / 1024)
                    + 1;
        }
    }

    public static synchronized AppIconCacheManager getInstance() {
        AppIconCacheManager appIconCacheManager;
        synchronized (AppIconCacheManager.class) {
            try {
                if (sAppIconCacheManager == null) {
                    sAppIconCacheManager = new AppIconCacheManager();
                    Log.i("AppIconCacheManager", "MAX_CACHE_SIZE_IN_KB : " + MAX_CACHE_SIZE_IN_KB);
                    mCurCacheSize = 0;
                }
                appIconCacheManager = sAppIconCacheManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return appIconCacheManager;
    }

    public static String getKey(int i, String str, boolean z) {
        if (str == null || i < 0) {
            return null;
        }
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str, ":");
        m.append(UserHandle.getUserId(i));
        m.append(":");
        m.append(z);
        return m.toString();
    }

    public final void put(String str, int i, boolean z, Drawable drawable) {
        String key = getKey(i, str, z);
        if (key == null
                || drawable == null
                || drawable.getIntrinsicHeight() < 0
                || drawable.getIntrinsicWidth() < 0) {
            Log.w("AppIconCacheManager", "Invalid key or drawable.");
            return;
        }
        if (!(drawable instanceof BitmapDrawable)) {
            Log.i("AppIconCacheManager", str + " is not BitmapDrawable, cache is not support");
            return;
        }
        int byteCount = (((BitmapDrawable) drawable).getBitmap().getByteCount() / 1024) + 1;
        int i2 = ICON_SIZE_LIMIT;
        if (byteCount > i2) {
            Log.i("AppIconCacheManager", str + " BitmapDrawable icon size is over than " + i2);
            return;
        }
        int i3 = mCurCacheSize + byteCount;
        int i4 = MAX_CACHE_SIZE_IN_KB;
        if (i3 > i4) {
            Log.i("AppIconCacheManager", "Max cache size reached");
            return;
        }
        StringBuilder m =
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i, "AppIconCacheManager Added : ", str, " , uid : ", " , size : ");
        m.append(byteCount);
        m.append(" , h : ");
        m.append(drawable.getIntrinsicHeight());
        m.append(" w : ");
        m.append(drawable.getIntrinsicWidth());
        Log.i("AppIconCacheManager", m.toString());
        mCurCacheSize += byteCount;
        TooltipPopup$$ExternalSyntheticOutline0.m(
                ListPopupWindow$$ExternalSyntheticOutline0.m(i4, "maxSize : ", " , curSize : "),
                mCurCacheSize,
                "AppIconCacheManager");
        this.mDrawableCache.put(key, drawable);
    }
}
