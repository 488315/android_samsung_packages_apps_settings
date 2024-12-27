package com.samsung.android.scloud.lib.storage.internal;

import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.scloud.lib.storage.data.Body;
import com.samsung.android.scloud.lib.storage.data.Header;
import com.samsung.android.scloud.lib.storage.data.RecordDataSet;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.utils.SCloudParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SyncRecordReader extends RecordReader {
    public final List getRecordDataSetList() {
        ArrayList arrayList = new ArrayList();
        do {
            JSONArray jSONArray =
                    SCloudParser.toJSONObject(this.jsonReader).getJSONArray("records");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("record");
                LOG.d("SyncRecordReader", "resultString : " + string);
                JSONObject jSONObject2 = new JSONObject(string);
                arrayList.add(
                        new RecordDataSet(
                                new Header(
                                        jSONObject2.getLong(PhoneRestrictionPolicy.TIMESTAMP),
                                        jSONObject2.getString("record_id"),
                                        null,
                                        "normal"),
                                new Body(jSONObject2, RecordReader.getFileList(jSONObject))));
            }
        } while (this.jsonReader.hasNext());
        return arrayList;
    }
}
