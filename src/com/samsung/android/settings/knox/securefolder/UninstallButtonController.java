package com.samsung.android.settings.knox.securefolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.settings.knox.KnoxUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UninstallButtonController extends KnoxLockScreenButtonController {
    public final boolean mIsResetEnabled;
    public final LockPatternUtils mLockPatternUtils;

    public UninstallButtonController(Context context, int i, TextView textView) {
        super(context, i, textView);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mIsResetEnabled = KnoxUtils.isResetWithSamsungAccountEnable(context, i);
    }

    @Override // com.samsung.android.settings.knox.securefolder.KnoxLockScreenButtonController
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        TextView textView = this.mTextView;
        if (textView == null) {
            return;
        }
        textView.setOnClickListener(onClickListener);
    }

    @Override // com.samsung.android.settings.knox.securefolder.KnoxLockScreenButtonController
    public final void setVisibility(int i) {
        TextView textView = this.mTextView;
        if (textView == null) {
            return;
        }
        if (this.mIsResetEnabled) {
            textView.setVisibility(8);
            return;
        }
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i2 = this.mUserId;
        if (lockPatternUtils.getCurrentFailedPasswordAttempts(i2) < 15) {
            this.mTextView.setVisibility(8);
        } else if (this.mLockPatternUtils.getLockoutAttemptDeadline(i2) != 0) {
            this.mTextView.setVisibility(0);
        } else {
            this.mTextView.setVisibility(i);
        }
    }
}
