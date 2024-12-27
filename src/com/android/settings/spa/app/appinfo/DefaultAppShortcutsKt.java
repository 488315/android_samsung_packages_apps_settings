package com.android.settings.spa.app.appinfo;

import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settings.R;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DefaultAppShortcutsKt {
    public static final List SHORT_CUTS =
            CollectionsKt__CollectionsKt.listOf(
                    (Object[])
                            new DefaultAppShortcut[] {
                                new DefaultAppShortcut("android.app.role.HOME", R.string.home_app),
                                new DefaultAppShortcut(
                                        "android.app.role.BROWSER", R.string.default_browser_title),
                                new DefaultAppShortcut(
                                        "android.app.role.DIALER", R.string.default_phone_title),
                                new DefaultAppShortcut(
                                        "android.app.role.EMERGENCY",
                                        R.string.default_emergency_app),
                                new DefaultAppShortcut(
                                        "android.app.role.SMS", R.string.sms_application_title)
                            });

    public static final void DefaultAppShortcuts(
            final ApplicationInfo app, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-839606307);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Iterator it = SHORT_CUTS.iterator();
        while (it.hasNext()) {
            DefaultAppShortcutPreferenceKt.DefaultAppShortcutPreference(
                    (DefaultAppShortcut) it.next(), app, composerImpl, 64);
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.DefaultAppShortcutsKt$DefaultAppShortcuts$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            DefaultAppShortcutsKt.DefaultAppShortcuts(
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
