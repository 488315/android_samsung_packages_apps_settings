package com.samsung.android.settings.accessibility.bixby.action.hearing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LiveTranscribeDownloadAction extends BixbyActionTarget {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        bundle.putString(
                "result", Utils.hasPackage(context, "com.android.vending") ? "true" : "false");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        return new Intent("android.intent.action.VIEW")
                .setData(
                        Uri.parse(
                                "https://play.google.com/store/apps/details?id=com.google.audio.hearing.visualization.accessibility.scribe"))
                .setPackage("com.android.vending");
    }
}
