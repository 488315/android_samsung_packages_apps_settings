package com.samsung.android.settings.actions;

import com.android.settings.R;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RoutinesCommandRegistry {
    public static final Map ROUTINES;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Routine {
        public String mCategory;
        public int mIconResId;
        public int mTitleResId;
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ROUTINES = linkedHashMap;
        Routine routine = new Routine();
        routine.mTitleResId = R.string.data_saver_title;
        routine.mCategory = "connection_setting";
        routine.mIconResId = R.drawable.sec_ic_routines_ic_data_saver;
        linkedHashMap.put("data_saver_switch", routine);
    }
}
