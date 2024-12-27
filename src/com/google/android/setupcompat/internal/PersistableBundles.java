package com.google.android.setupcompat.internal;

import android.os.BaseBundle;
import android.os.PersistableBundle;
import android.util.ArrayMap;

import com.google.android.setupcompat.util.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class PersistableBundles {
    public static final Logger LOG = new Logger("PersistableBundles");

    public static void assertIsValid(PersistableBundle persistableBundle) {
        Preconditions.checkNotNull(persistableBundle, "PersistableBundle cannot be null!");
        for (String str : persistableBundle.keySet()) {
            Object obj = persistableBundle.get(str);
            Preconditions.checkArgument(
                    String.format("Unknown/unsupported data type [%s] for key %s", obj, str),
                    isSupportedDataType(obj));
        }
    }

    public static boolean isSupportedDataType(Object obj) {
        return (obj instanceof Integer)
                || (obj instanceof Long)
                || (obj instanceof Double)
                || (obj instanceof Float)
                || (obj instanceof String)
                || (obj instanceof Boolean);
    }

    public static ArrayMap toMap(BaseBundle baseBundle) {
        if (baseBundle == null || baseBundle.isEmpty()) {
            return new ArrayMap(0);
        }
        ArrayMap arrayMap = new ArrayMap(baseBundle.size());
        for (String str : baseBundle.keySet()) {
            Object obj = baseBundle.get(str);
            if (isSupportedDataType(obj)) {
                arrayMap.put(str, baseBundle.get(str));
            } else {
                LOG.w(String.format("Unknown/unsupported data type [%s] for key %s", obj, str));
            }
        }
        return arrayMap;
    }
}
