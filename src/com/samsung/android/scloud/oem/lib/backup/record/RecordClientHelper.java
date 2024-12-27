package com.samsung.android.scloud.oem.lib.backup.record;

import android.util.JsonWriter;

import com.samsung.android.scloud.oem.lib.LOG;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecordClientHelper {
    public JsonWriter jsonWriter;
    public String sourceKey;

    public RecordClientHelper(String str, JsonWriter jsonWriter) {
        this.jsonWriter = jsonWriter;
        this.sourceKey = str;
    }

    public final void open() {
        LOG.d("RecordClientHelper", "[" + this.sourceKey + "] open");
        JsonWriter jsonWriter = this.jsonWriter;
        if (jsonWriter != null) {
            try {
                jsonWriter.beginArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void release() {
        LOG.d("RecordClientHelper", "[" + this.sourceKey + "] release");
        try {
            JsonWriter jsonWriter = this.jsonWriter;
            if (jsonWriter != null) {
                jsonWriter.endArray();
                this.jsonWriter.flush();
                this.jsonWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
