package com.android.settings.core;

import android.text.TextUtils;
import android.util.Log;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface PreferenceControllerMixin {
    /* JADX WARN: Multi-variable type inference failed */
    default void updateNonIndexableKeys(List list) {
        if (this instanceof AbstractPreferenceController) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) this;
            if (abstractPreferenceController.isAvailable()) {
                return;
            }
            String preferenceKey = abstractPreferenceController.getPreferenceKey();
            if (TextUtils.isEmpty(preferenceKey)) {
                Log.w(
                        "PrefControllerMixin",
                        "Skipping updateNonIndexableKeys due to empty key " + toString());
            } else {
                if (!list.contains(preferenceKey)) {
                    list.add(preferenceKey);
                    return;
                }
                Log.w(
                        "PrefControllerMixin",
                        "Skipping updateNonIndexableKeys, key already in list. " + toString());
            }
        }
    }

    default void updateDynamicRawDataToIndex(List list) {}

    default void updateRawDataToIndex(List list) {}
}
