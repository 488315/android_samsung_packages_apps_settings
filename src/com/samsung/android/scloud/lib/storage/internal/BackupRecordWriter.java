package com.samsung.android.scloud.lib.storage.internal;

import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.utils.HashUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupRecordWriter extends RecordWriter {
    public final void addRecordAndFiles(String str, String str2, String str3, List list) {
        try {
            this.jsonWriter.beginObject();
            this.jsonWriter.name("id").value(str);
            this.jsonWriter.name(PhoneRestrictionPolicy.TIMESTAMP).value(str2);
            this.jsonWriter.name("record").value(str3);
            if (list != null && list.size() > 0) {
                this.jsonWriter.name("files");
                this.jsonWriter.beginArray();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    File file = (File) it.next();
                    this.jsonWriter.beginObject();
                    this.jsonWriter.name("path").value(file.getAbsolutePath());
                    this.jsonWriter.name("size").value(file.length());
                    this.jsonWriter.name("hash").value(HashUtil.getFileSHA256(file));
                    this.jsonWriter.endObject();
                }
                this.jsonWriter.endArray();
            }
            this.jsonWriter.endObject();
            this.jsonWriter.flush();
        } catch (IOException e) {
            LOG.e("BackupRecordWriter", "addRecordAndFiles ", e);
        }
    }
}
