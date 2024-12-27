package com.samsung.android.settings.notification.zen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecZenScheduleRepeatPreference extends LinearLayout {
    public SecZenScheduleRepeatButton mRepeatButton;

    public SecZenScheduleRepeatPreference(Context context) {
        super(context);
        this.mRepeatButton = (SecZenScheduleRepeatButton) findViewById(R.id.custom_repeat_btn);
    }

    public SecZenScheduleRepeatPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRepeatButton = (SecZenScheduleRepeatButton) findViewById(R.id.custom_repeat_btn);
    }

    public SecZenScheduleRepeatPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRepeatButton = (SecZenScheduleRepeatButton) findViewById(R.id.custom_repeat_btn);
    }

    public SecZenScheduleRepeatPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRepeatButton = (SecZenScheduleRepeatButton) findViewById(R.id.custom_repeat_btn);
    }
}
