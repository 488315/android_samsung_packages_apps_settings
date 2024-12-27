package com.samsung.android.sdk.routines.v3.data;

import android.app.PendingIntent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ErrorContents {
    public final String a;
    public final String b;
    public final DialogButton c;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DialogButton {
        public final String a = "Activate";
        public final PendingIntent b;

        public DialogButton(PendingIntent pendingIntent) {
            this.b = pendingIntent;
        }
    }

    public ErrorContents(String str, String str2, DialogButton dialogButton) {
        this.a = str;
        this.b = str2;
        this.c = dialogButton;
    }
}
