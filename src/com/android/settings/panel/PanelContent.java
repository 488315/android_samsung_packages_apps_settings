package com.android.settings.panel;

import android.content.Intent;

import com.android.settingslib.core.instrumentation.Instrumentable;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface PanelContent extends Instrumentable {
    Intent getSeeMoreIntent();

    List getSlices();

    CharSequence getTitle();

    default int getViewType() {
        return 0;
    }

    default void registerCallback(PanelFragment.LocalPanelCallback localPanelCallback) {}
}
