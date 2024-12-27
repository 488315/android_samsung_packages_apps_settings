package com.samsung.android.settings.accessibility.bixby;

import androidx.appcompat.app.AppCompatActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BixbyActivity extends AppCompatActivity {
    /* JADX WARN: Removed duplicated region for block: B:7:0x0038  */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r8) {
        /*
            r7 = this;
            java.lang.String r0 = "startActivity intent : "
            super.onCreate(r8)
            android.content.Intent r8 = r7.getIntent()
            java.lang.String r1 = "titleRes"
            r2 = 0
            java.lang.String r3 = "desFrag"
            java.lang.String r4 = "BixbyActivity"
            if (r8 == 0) goto L35
            java.lang.String r5 = r8.toString()
            android.util.Log.d(r4, r5)
            com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget r8 = com.samsung.android.settings.accessibility.bixby.BixbyActionFactory.findTarget(r7, r8)
            if (r8 == 0) goto L35
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>()
            java.lang.String r6 = r8.getDestinationFragment()
            r5.putString(r3, r6)
            int r8 = r8.getTitleRes()
            r5.putInt(r1, r8)
            goto L36
        L35:
            r5 = r2
        L36:
            if (r5 == 0) goto Lb9
            java.lang.String r8 = r5.getString(r3)
            if (r8 == 0) goto L65
            com.android.settings.core.SubSettingLauncher r8 = new com.android.settings.core.SubSettingLauncher
            r8.<init>(r7)
            r0 = 0
            com.android.settings.core.SubSettingLauncher$LaunchRequest r4 = r8.mLaunchRequest
            r4.mSourceMetricsCategory = r0
            java.lang.String r0 = r5.getString(r3)
            r4.mDestinationName = r0
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            r4.mArguments = r0
            int r0 = r5.getInt(r1)
            r8.setTitleRes(r0, r2)
            r0 = 335544320(0x14000000, float:6.4623485E-27)
            r8.addFlags(r0)
            r8.launch()
            goto Lb9
        L65:
            android.content.Intent r8 = r7.getIntent()
            if (r8 == 0) goto L9f
            java.lang.String r1 = r8.toString()
            android.util.Log.d(r4, r1)
            com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget r1 = com.samsung.android.settings.accessibility.bixby.BixbyActionFactory.findTarget(r7, r8)
            if (r1 == 0) goto L7d
            android.content.Intent r1 = r1.getPunchoutIntent(r7)
            goto L7e
        L7d:
            r1 = r2
        L7e:
            if (r1 != 0) goto L81
            goto L9f
        L81:
            android.os.Bundle r8 = r8.getExtras()
            if (r8 == 0) goto L98
            java.lang.String r2 = "bixbyClient_taskId"
            boolean r3 = r8.containsKey(r2)
            if (r3 == 0) goto L98
            int r8 = r8.getInt(r2)
            java.lang.String r2 = "BixbyActivity bundle taskId : "
            com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0.m(r8, r2, r4)
        L98:
            r8 = 268468224(0x10008000, float:2.5342157E-29)
            r1.setFlags(r8)
            r2 = r1
        L9f:
            if (r2 == 0) goto Lb9
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb4
            r8.<init>(r0)     // Catch: java.lang.Throwable -> Lb4
            r8.append(r2)     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> Lb4
            android.util.Log.d(r4, r8)     // Catch: java.lang.Throwable -> Lb4
            r7.startActivity(r2)     // Catch: java.lang.Throwable -> Lb4
            goto Lb9
        Lb4:
            java.lang.String r8 = "ActivityNotFoundException : Can not found Activity"
            android.util.Log.e(r4, r8)
        Lb9:
            r7.finishAndRemoveTask()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.bixby.BixbyActivity.onCreate(android.os.Bundle):void");
    }
}
