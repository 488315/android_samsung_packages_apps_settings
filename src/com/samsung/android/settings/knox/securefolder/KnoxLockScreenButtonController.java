package com.samsung.android.settings.knox.securefolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class KnoxLockScreenButtonController {
    public final Context mContext;
    public final TextView mTextView;
    public final int mUserId;

    public KnoxLockScreenButtonController(Context context, int i, TextView textView) {
        this.mContext = context;
        this.mUserId = i;
        this.mTextView = textView;
    }

    public abstract void setOnClickListener(View.OnClickListener onClickListener);

    public abstract void setVisibility(int i);
}
