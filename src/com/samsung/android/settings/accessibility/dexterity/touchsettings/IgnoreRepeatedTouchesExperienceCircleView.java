package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class IgnoreRepeatedTouchesExperienceCircleView extends FrameLayout {
    public final ImageView mFail;
    public final ImageView mIgnore;
    public final ImageView mStandBy;
    public int mState;
    public final ImageView mSuccess;
    public final ImageView mTimeUp;
    public final View mView;

    public IgnoreRepeatedTouchesExperienceCircleView(Context context) {
        super(context);
        this.mState = -1;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.layout_ignore_repeated_touches_experience_circle_view,
                                this);
        this.mView = inflate;
        this.mStandBy = (ImageView) inflate.findViewById(R.id.tap_stand_by);
        this.mSuccess = (ImageView) inflate.findViewById(R.id.tap_success);
        this.mIgnore = (ImageView) inflate.findViewById(R.id.tap_ignore);
        this.mTimeUp = (ImageView) inflate.findViewById(R.id.tap_time_up);
        this.mFail = (ImageView) inflate.findViewById(R.id.tap_fail);
    }

    public final void reset() {
        this.mSuccess.setVisibility(4);
        this.mFail.setVisibility(4);
        this.mStandBy.setVisibility(0);
    }

    public final void success() {
        this.mIgnore.setVisibility(4);
        this.mStandBy.setVisibility(4);
        this.mSuccess.setVisibility(0);
    }
}
