package com.samsung.android.settings.accessibility.bixby.action.advanced;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DownloadVoiceRecorderAction extends BixbyActionTarget {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("samsungapps://ProductDetail/com.sec.android.app.voicenote"));
        intent.putExtra("type", "cover");
        intent.addFlags(335544352);
        return intent;
    }
}
