package com.android.settings.applications.assist;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;

import com.android.settingslib.utils.ThreadUtils;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AssistSettingObserver extends ContentObserver {
    public final Uri ASSIST_URI;

    public AssistSettingObserver() {
        super(null);
        this.ASSIST_URI = Settings.Secure.getUriFor("assistant");
    }

    public abstract List getSettingUris();

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        super.onChange(z, uri);
        List settingUris = getSettingUris();
        if (this.ASSIST_URI.equals(uri) || (settingUris != null && settingUris.contains(uri))) {
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.android.settings.applications.assist.AssistSettingObserver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AssistSettingObserver.this.onSettingChange();
                        }
                    });
        }
    }

    public abstract void onSettingChange();

    public final void register(ContentResolver contentResolver) {
        contentResolver.registerContentObserver(this.ASSIST_URI, false, this);
        List settingUris = getSettingUris();
        if (settingUris != null) {
            Iterator it = settingUris.iterator();
            while (it.hasNext()) {
                contentResolver.registerContentObserver((Uri) it.next(), false, this);
            }
        }
    }
}
