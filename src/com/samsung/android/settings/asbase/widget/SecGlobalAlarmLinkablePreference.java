package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecGlobalAlarmLinkablePreference extends SecAudioLinkablePreference {
    public SecGlobalAlarmLinkablePreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final int getGuideTextResource() {
        return R.string.sec_global_alarm_volume_link;
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference,
              // androidx.preference.Preference
    public final Intent getIntent() {
        Intent intent = new Intent("com.sec.android.app.clockpackage.SAME_VOLUME_FOR_ALARM");
        intent.setPackage("com.sec.android.app.clockpackage");
        intent.setClassName(
                "com.sec.android.app.clockpackage",
                "com.sec.android.app.clockpackage.alarm.ui.activity.AlarmVolumeActivity");
        intent.addFlags(268435456);
        return intent;
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final int getTitleResource$1() {
        return R.string.sec_global_alarm_volume_link;
    }

    public SecGlobalAlarmLinkablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecGlobalAlarmLinkablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecGlobalAlarmLinkablePreference(Context context) {
        this(context, null);
    }
}
