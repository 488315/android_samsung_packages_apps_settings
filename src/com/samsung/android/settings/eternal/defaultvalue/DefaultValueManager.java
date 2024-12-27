package com.samsung.android.settings.eternal.defaultvalue;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DefaultValueManager {
    public JSONManager mJSONManager;
    public HashMap mSystemDefaultHashMap;
    public XmlManager mXmlManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class DefaultValueManagerHolder {
        public static final DefaultValueManager INSTANCE;

        static {
            DefaultValueManager defaultValueManager = new DefaultValueManager();
            defaultValueManager.mXmlManager = new XmlManager();
            defaultValueManager.mJSONManager = new JSONManager();
            INSTANCE = defaultValueManager;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void migrationOldDefaultValue(android.content.Context r17) {
        /*
            Method dump skipped, instructions count: 323
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.defaultvalue.DefaultValueManager.migrationOldDefaultValue(android.content.Context):void");
    }
}
