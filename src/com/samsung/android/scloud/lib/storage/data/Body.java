package com.samsung.android.scloud.lib.storage.data;

import org.json.JSONObject;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Body {
    public final List fileList;
    public final JSONObject itemData;

    public Body(JSONObject jSONObject, List list) {
        this.itemData = jSONObject == null ? new JSONObject() : jSONObject;
        this.fileList = list;
    }
}
