package com.android.settingslib.core.instrumentation;

import android.view.View;

import com.android.internal.jank.InteractionJankMonitor;

import kotlin.jvm.internal.Intrinsics;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsJankMonitor {
    public static final InteractionJankMonitor jankMonitor = InteractionJankMonitor.getInstance();
    public static final ScheduledExecutorService scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor();

    public static final void detectToggleJank(View view, String str) {
        Intrinsics.checkNotNullParameter(view, "view");
        InteractionJankMonitor.Configuration.Builder withView =
                InteractionJankMonitor.Configuration.Builder.withView(57, view);
        if (str != null) {
            withView.setTag(str);
        }
        if (jankMonitor.begin(withView)) {
            scheduledExecutorService.schedule(
                    SettingsJankMonitor$detectToggleJank$1.INSTANCE, 300L, TimeUnit.MILLISECONDS);
        }
    }

    public static /* synthetic */ void getMONITORED_ANIMATION_DURATION_MS$annotations() {}
}
