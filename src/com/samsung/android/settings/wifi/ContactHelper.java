package com.samsung.android.settings.wifi;

import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ContactHelper {
    public static final Uri CONTENT_COUNT_URI =
            Uri.parse("content://com.android.contacts/contacts");
    public static final String[] projection = {"_id", "display_name", "photo_uri", "data1"};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ContactInfo {
        public final long id;
        public final String name;
        public final Uri photoUri;

        public ContactInfo(long j, String str, String str2) {
            this.id = j;
            this.name = str;
            this.photoUri = str2 != null ? Uri.parse(str2) : null;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("id:");
            sb.append(this.id);
            sb.append(", name:");
            String str = this.name;
            sb.append(str);
            sb.append(", photo:");
            sb.append(str);
            return sb.toString();
        }
    }
}
