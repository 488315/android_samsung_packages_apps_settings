package com.android.settingslib.spa.widget.scaffold;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsScaffoldKt {
    public static final void ActivityTitle(final String title, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(title, "title");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(424118079);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(title) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            EffectsKt.LaunchedEffect(
                    composerImpl,
                    Boolean.TRUE,
                    new SettingsScaffoldKt$ActivityTitle$1(
                            (Context)
                                    composerImpl.consume(
                                            AndroidCompositionLocals_androidKt.LocalContext),
                            title,
                            null));
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SettingsScaffoldKt$ActivityTitle$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsScaffoldKt.ActivityTitle(
                                    title,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0055  */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.settingslib.spa.widget.scaffold.SettingsScaffoldKt$SettingsScaffold$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.settingslib.spa.widget.scaffold.SettingsScaffoldKt$SettingsScaffold$2, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SettingsScaffold(
            final java.lang.String r22,
            kotlin.jvm.functions.Function3 r23,
            final kotlin.jvm.functions.Function3 r24,
            androidx.compose.runtime.Composer r25,
            final int r26,
            final int r27) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.scaffold.SettingsScaffoldKt.SettingsScaffold(java.lang.String,"
                    + " kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3,"
                    + " androidx.compose.runtime.Composer, int, int):void");
    }

    public static final Activity getActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (!(context instanceof ContextWrapper)) {
            return null;
        }
        Context baseContext = ((ContextWrapper) context).getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
        return getActivity(baseContext);
    }
}
