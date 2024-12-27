package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.view.accessibility.CaptioningManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CaptionHelper {
    static final float LINE_HEIGHT_RATIO = 0.0533f;
    public final CaptioningManager mCaptioningManager;
    public final ContentResolver mContentResolver;
    public final Context mContext;

    public CaptionHelper(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mCaptioningManager =
                (CaptioningManager) context.getSystemService(CaptioningManager.class);
    }

    public final int getBackgroundColor() {
        CaptioningManager.CaptionStyle customStyle =
                CaptioningManager.CaptionStyle.getCustomStyle(this.mContentResolver);
        if (customStyle.hasBackgroundColor()) {
            return customStyle.backgroundColor;
        }
        return 16777215;
    }

    public final int getForegroundColor() {
        CaptioningManager.CaptionStyle customStyle =
                CaptioningManager.CaptionStyle.getCustomStyle(this.mContentResolver);
        if (customStyle.hasForegroundColor()) {
            return customStyle.foregroundColor;
        }
        return 16777215;
    }

    public final int getWindowColor() {
        CaptioningManager.CaptionStyle customStyle =
                CaptioningManager.CaptionStyle.getCustomStyle(this.mContentResolver);
        if (customStyle.hasWindowColor()) {
            return customStyle.windowColor;
        }
        return 16777215;
    }
}
