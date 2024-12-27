package com.android.settings.development;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.pm.PackageManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EmulateDisplayCutoutPreferenceController
        extends OverlayCategoryPreferenceController {
    public EmulateDisplayCutoutPreferenceController(
            Context context, PackageManager packageManager, IOverlayManager iOverlayManager) {
        super(
                context,
                packageManager,
                iOverlayManager,
                "com.android.internal.display_cutout_emulation");
    }

    @Override // com.android.settings.development.OverlayCategoryPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "display_cutout_emulation";
    }
}
