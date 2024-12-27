package com.samsung.android.knox;

import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KnoxInternalFeature {
    public static final int KNOX_CONFIG_VERSION =
            Integer.parseInt(DATA.DM_FIELD_INDEX.GZIP_FLAG) - 5;
    public static final int KNOX_CONFIG_MDM_VERSION =
            Integer.parseInt(DATA.DM_FIELD_INDEX.GZIP_FLAG) - 3;
}
