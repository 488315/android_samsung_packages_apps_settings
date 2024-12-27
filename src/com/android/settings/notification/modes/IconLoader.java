package com.android.settings.notification.modes;

import android.R;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.LruCache;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class IconLoader {
    public static final Drawable MISSING = new ColorDrawable();
    public static IconLoader sInstance;
    public final ListeningExecutorService mBackgroundExecutor;
    public final LruCache mCache = new LruCache(50);

    public IconLoader(ExecutorService executorService) {
        this.mBackgroundExecutor = MoreExecutors.listeningDecorator(executorService);
    }

    public static Drawable getFallbackIcon(Context context, int i) {
        int i2 = R.drawable.jog_tab_right_confirm_yellow;
        switch (i) {
            case 0:
                i2 = R.drawable.jog_tab_left_unlock;
                break;
            case 1:
                i2 = R.drawable.jog_tab_right_confirm_green;
                break;
            case 2:
                i2 = R.drawable.jog_tab_right_confirm_gray;
                break;
            case 3:
                i2 = R.drawable.jog_tab_left_confirm_yellow;
                break;
            case 4:
                i2 = R.drawable.jog_tab_left_generic;
                break;
            case 5:
                i2 = R.drawable.jog_tab_left_normal;
                break;
            case 6:
                i2 = R.drawable.jog_tab_right_confirm_red;
                break;
            case 7:
                i2 = R.drawable.jog_tab_left_pressed;
                break;
        }
        Drawable drawable = context.getDrawable(i2);
        Objects.requireNonNull(drawable);
        return drawable;
    }

    public static IconLoader getInstance() {
        if (sInstance == null) {
            sInstance = new IconLoader(Executors.newFixedThreadPool(4));
        }
        return sInstance;
    }
}
