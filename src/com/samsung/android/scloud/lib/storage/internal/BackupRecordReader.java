package com.samsung.android.scloud.lib.storage.internal;

import android.text.TextUtils;

import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.scloud.lib.storage.data.Body;
import com.samsung.android.scloud.lib.storage.data.Header;
import com.samsung.android.scloud.lib.storage.data.RecordDataSet;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.utils.SCloudParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupRecordReader extends RecordReader {
    public final List getRecordDataSetList() {
        ArrayList arrayList = new ArrayList();
        while (this.jsonReader.hasNext()) {
            JSONObject jSONObject = SCloudParser.toJSONObject(this.jsonReader);
            String optString = jSONObject.optString("id");
            long optLong = jSONObject.optLong(PhoneRestrictionPolicy.TIMESTAMP);
            String optString2 = jSONObject.optString("record");
            JSONObject jSONObject2 =
                    !TextUtils.isEmpty(optString2) ? new JSONObject(optString2) : null;
            Header header = new Header();
            header.recordId = optString;
            header.timeStamp = optLong;
            RecordDataSet recordDataSet =
                    new RecordDataSet(
                            header, new Body(jSONObject2, RecordReader.getFileList(jSONObject)));
            LOG.d("BackupRecordReader", "getRecordDataSetList " + recordDataSet.toString());
            arrayList.add(recordDataSet);
        }
        return arrayList;
    }
}
