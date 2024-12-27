package com.samsung.android.settings.uwb.labs.view.uwbtest.uwbconfig;

import android.content.Context;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UwbConfigUtil {
    public Context mContext;
    public List mUwbConfigList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UwbPreference {
        public final int entry_values_id;
        public final int entrys_id;
        public final String key;
        public final int title_id;
        public final int type;

        public UwbPreference(int i, int i2, int i3, int i4, String str) {
            this.type = i;
            this.key = str;
            this.title_id = i2;
            this.entrys_id = i3;
            this.entry_values_id = i4;
        }
    }
}
