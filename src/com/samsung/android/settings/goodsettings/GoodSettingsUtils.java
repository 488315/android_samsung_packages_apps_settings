package com.samsung.android.settings.goodsettings;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GoodSettingsUtils {
    private static final Map<String, Long> mExtraFlagMap;

    static {
        HashMap hashMap = new HashMap();
        mExtraFlagMap = hashMap;
        hashMap.put(GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE, 8589938698L);
        hashMap.put(UniversalCredentialUtil.AGENT_TITLE, 4122L);
        hashMap.put(UniversalCredentialUtil.AGENT_SUMMARY, 4138L);
        hashMap.put("icon", 64L);
        hashMap.put("intent", 196608L);
        hashMap.put("type", Long.valueOf(GoodSettingsContract.PreferenceFlag.FLAG_NEED_TYPE));
        hashMap.put("order", 32768L);
    }

    public static long getFlags(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        long j = 0;
        while (it.hasNext()) {
            j |= mExtraFlagMap.getOrDefault((String) it.next(), 0L).longValue();
        }
        return j;
    }
}
