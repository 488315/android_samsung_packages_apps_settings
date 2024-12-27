package com.android.settings.notification.app;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.RingtonePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationSoundPreference extends RingtonePreference {
    public Uri mRingtone;
    public String mRingtoneName;

    public NotificationSoundPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settings.RingtonePreference
    public final boolean onActivityResult(int i, int i2, Intent intent) {
        if (intent == null) {
            return true;
        }
        Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
        if (isValidRingtoneUri(uri)) {
            setRingtone(uri);
            callChangeListener(uri);
            return true;
        }
        Log.e(
                "NotificationSoundPreference",
                "onActivityResult for URI:" + uri + " ignored: invalid ringtone Uri");
        return true;
    }

    @Override // com.android.settings.RingtonePreference
    public final Uri onRestoreRingtone() {
        return this.mRingtone;
    }

    public final void setRingtone(Uri uri) {
        if (this.mRingtone != uri || (uri == null && this.mRingtoneName == null)) {
            this.mRingtone = uri;
            setSummary(" ");
            final Uri uri2 = this.mRingtone;
            new AsyncTask() { // from class:
                              // com.android.settings.notification.app.NotificationSoundPreference.1
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    Uri uri3 = uri2;
                    if (uri3 == null) {
                        NotificationSoundPreference notificationSoundPreference =
                                NotificationSoundPreference.this;
                        notificationSoundPreference.mRingtoneName =
                                notificationSoundPreference
                                        .getContext()
                                        .getString(R.string.sec_notification_sound_silent);
                        return NotificationSoundPreference.this
                                .getContext()
                                .getString(R.string.sec_notification_sound_silent);
                    }
                    if (RingtoneManager.isDefault(uri3)) {
                        String title =
                                Ringtone.getTitle(
                                        NotificationSoundPreference.this.getContext(),
                                        RingtoneManager.getActualDefaultRingtoneUri(
                                                NotificationSoundPreference.this.getContext(),
                                                RingtoneManager.getDefaultType(uri2)),
                                        false,
                                        true);
                        NotificationSoundPreference notificationSoundPreference2 =
                                NotificationSoundPreference.this;
                        notificationSoundPreference2.mRingtoneName =
                                notificationSoundPreference2
                                        .getContext()
                                        .getString(R.string.notification_sound_default, title);
                        return NotificationSoundPreference.this
                                .getContext()
                                .getString(R.string.notification_sound_default, title);
                    }
                    if ("android.resource".equals(uri2.getScheme())) {
                        NotificationSoundPreference notificationSoundPreference3 =
                                NotificationSoundPreference.this;
                        notificationSoundPreference3.mRingtoneName =
                                notificationSoundPreference3
                                        .getContext()
                                        .getString(R.string.notification_unknown_sound_title);
                        return NotificationSoundPreference.this
                                .getContext()
                                .getString(R.string.notification_unknown_sound_title);
                    }
                    NotificationSoundPreference notificationSoundPreference4 =
                            NotificationSoundPreference.this;
                    notificationSoundPreference4.mRingtoneName = "external resources";
                    return Ringtone.getTitle(
                            notificationSoundPreference4.getContext(), uri2, false, true);
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    NotificationSoundPreference.this.setSummary((CharSequence) obj);
                }
            }.execute(new Object[0]);
        }
    }
}
