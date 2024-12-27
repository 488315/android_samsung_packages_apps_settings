package com.samsung.android.settings.mde;

import android.util.Log;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MDESuggestionManager {
    public static MDESuggestionManager sInstance;
    public ArrayList mDismissedSuggestions;

    public static MDESuggestionManager getInstance() {
        if (sInstance == null) {
            MDESuggestionManager mDESuggestionManager = new MDESuggestionManager();
            mDESuggestionManager.mDismissedSuggestions = new ArrayList();
            Log.d("MDESuggestionManager", "MDESuggestionManager()");
            sInstance = mDESuggestionManager;
        }
        return sInstance;
    }
}
