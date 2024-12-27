package com.samsung.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecFontTrialSettings {
    public FontPreviewBubbleListAdapter mBubbleListViewAdapter;
    public final Context mContext;
    public final Intent mIntent;
    public Toast mMaxCharacterToast;

    public SecFontTrialSettings(Context context, Intent intent) {
        this.mContext = context;
        this.mIntent = intent;
    }
}
