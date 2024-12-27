package com.android.settings.applications;

import android.content.Intent;
import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum EnterpriseDefaultApps {
    /* JADX INFO: Fake field, exist only in values array */
    BROWSER(
            new Intent[] {
                buildIntent(
                        "android.intent.action.VIEW",
                        "android.intent.category.BROWSABLE",
                        "http:",
                        null)
            }),
    /* JADX INFO: Fake field, exist only in values array */
    CALENDAR(
            new Intent[] {
                buildIntent(
                        "android.intent.action.INSERT", null, null, "vnd.android.cursor.dir/event")
            }),
    /* JADX INFO: Fake field, exist only in values array */
    CAMERA(
            new Intent[] {
                new Intent("android.media.action.IMAGE_CAPTURE"),
                new Intent("android.media.action.VIDEO_CAPTURE")
            }),
    /* JADX INFO: Fake field, exist only in values array */
    CONTACTS(
            new Intent[] {
                buildIntent(
                        "android.intent.action.PICK", null, null, "vnd.android.cursor.dir/contact")
            }),
    /* JADX INFO: Fake field, exist only in values array */
    EMAIL(
            new Intent[] {
                new Intent("android.intent.action.SENDTO"),
                new Intent("android.intent.action.SEND"),
                new Intent("android.intent.action.SEND_MULTIPLE")
            }),
    /* JADX INFO: Fake field, exist only in values array */
    MAP(new Intent[] {buildIntent("android.intent.action.VIEW", null, "geo:", null)}),
    /* JADX INFO: Fake field, exist only in values array */
    PHONE(
            new Intent[] {
                new Intent("android.intent.action.DIAL"), new Intent("android.intent.action.CALL")
            });

    public static final EnterpriseDefaultApps BROWSER = null;
    public static final EnterpriseDefaultApps CALENDAR = null;
    public static final EnterpriseDefaultApps CAMERA = null;
    public static final EnterpriseDefaultApps CONTACTS = null;
    public static final EnterpriseDefaultApps EMAIL = null;
    public static final EnterpriseDefaultApps MAP = null;
    public static final EnterpriseDefaultApps PHONE = null;
    private final Intent[] mIntents;

    EnterpriseDefaultApps(Intent[] intentArr) {
        this.mIntents = intentArr;
    }

    public static Intent buildIntent(String str, String str2, String str3, String str4) {
        Intent intent = new Intent(str);
        if (str2 != null) {
            intent.addCategory(str2);
        }
        if (str3 != null) {
            intent.setData(Uri.parse(str3));
        }
        if (str4 != null) {
            intent.setType(str4);
        }
        return intent;
    }

    public final Intent[] getIntents() {
        return this.mIntents;
    }
}
