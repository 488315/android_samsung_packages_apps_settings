package com.samsung.android.scloud.lib.storage.internal;

import android.os.ParcelFileDescriptor;
import android.util.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RecordWriter {
    public final FileWriter fileWriter;
    public final JsonWriter jsonWriter;

    public RecordWriter(ParcelFileDescriptor parcelFileDescriptor) {
        FileWriter fileWriter = new FileWriter(parcelFileDescriptor.getFileDescriptor());
        this.fileWriter = fileWriter;
        this.jsonWriter = new JsonWriter(fileWriter);
    }

    public final void close() {
        try {
            this.jsonWriter.close();
            this.fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void end() {
        try {
            JsonWriter jsonWriter = this.jsonWriter;
            if (jsonWriter != null) {
                jsonWriter.endArray();
                this.jsonWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
