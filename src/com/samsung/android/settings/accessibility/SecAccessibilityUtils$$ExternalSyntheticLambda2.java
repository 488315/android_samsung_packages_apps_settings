package com.samsung.android.settings.accessibility;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecAccessibilityUtils$$ExternalSyntheticLambda2
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;

    public /* synthetic */ SecAccessibilityUtils$$ExternalSyntheticLambda2(Context context, int i) {
        this.$r8$classId = i;
        this.f$0 = context;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        Context context = this.f$0;
        switch (i2) {
            case 0:
                SecAccessibilityUtils.enableMuteAllSounds(context, true);
                break;
            default:
                Intent intent = new Intent();
                intent.setData(
                        Uri.parse("samsungapps://ProductDetail/com.sec.android.app.voicenote"));
                intent.putExtra("type", "cover");
                intent.addFlags(335544352);
                try {
                    context.startActivity(intent);
                    break;
                } catch (ActivityNotFoundException e) {
                    Log.e("A11yUtils", "There is wrong intent for Voice recorder download page", e);
                }
        }
    }
}
