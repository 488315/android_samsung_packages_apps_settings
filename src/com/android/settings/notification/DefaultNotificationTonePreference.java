package com.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.android.settings.DefaultRingtonePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultNotificationTonePreference extends DefaultRingtonePreference {
    public DefaultNotificationTonePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settings.DefaultRingtonePreference,
              // com.android.settings.RingtonePreference
    public final void onPrepareRingtonePickerIntent(Intent intent) {
        super.onPrepareRingtonePickerIntent(intent);
        intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", (Parcelable) null);
    }

    @Override // com.android.settings.DefaultRingtonePreference,
              // com.android.settings.RingtonePreference
    public final Uri onRestoreRingtone() {
        return null;
    }
}
