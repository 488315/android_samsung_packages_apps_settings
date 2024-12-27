package com.samsung.android.scloud.lib.storage.internal;

import android.os.ParcelFileDescriptor;
import android.util.JsonReader;

import com.samsung.android.scloud.oem.lib.LOG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RecordReader {
    public final FileReader fileReader;
    public final JsonReader jsonReader;

    public RecordReader(ParcelFileDescriptor parcelFileDescriptor) {
        FileReader fileReader = new FileReader(parcelFileDescriptor.getFileDescriptor());
        this.fileReader = fileReader;
        this.jsonReader = new JsonReader(fileReader);
    }

    public static List getFileList(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray = jSONObject.getJSONArray("files");
        } catch (JSONException unused) {
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            String string = jSONArray.getJSONObject(i).getString("path");
            LOG.d("RecordReader", "file path : " + string);
            arrayList.add(new File(string));
        }
        return arrayList;
    }

    public final void close() {
        try {
            this.jsonReader.close();
            this.fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
