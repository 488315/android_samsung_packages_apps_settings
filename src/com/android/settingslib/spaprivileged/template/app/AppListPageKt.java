package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppListPageKt {
    /* JADX WARN: Removed duplicated region for block: B:13:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0072  */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.settingslib.spaprivileged.template.app.AppListPageKt$AppListPage$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.settingslib.spaprivileged.template.app.AppListPageKt$AppListPage$2, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AppListPage(
            final java.lang.String r20,
            final com.android.settingslib.spaprivileged.model.app.AppListModel r21,
            boolean r22,
            boolean r23,
            boolean r24,
            java.lang.String r25,
            kotlin.jvm.functions.Function3 r26,
            kotlin.jvm.functions.Function2 r27,
            kotlin.jvm.functions.Function3 r28,
            androidx.compose.runtime.Composer r29,
            final int r30,
            final int r31) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spaprivileged.template.app.AppListPageKt.AppListPage(java.lang.String,"
                    + " com.android.settingslib.spaprivileged.model.app.AppListModel, boolean,"
                    + " boolean, boolean, java.lang.String, kotlin.jvm.functions.Function3,"
                    + " kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3,"
                    + " androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void access$ShowSystemAction(
            final MoreOptionsScope moreOptionsScope,
            final boolean z,
            final Function1 function1,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(16392974);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(moreOptionsScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(z) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 256 : 128;
        }
        if ((i2 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            String stringResource =
                    StringResources_androidKt.stringResource(
                            composerImpl,
                            z ? R.string.menu_hide_system : R.string.menu_show_system);
            composerImpl.startReplaceGroup(15127710);
            boolean z2 = ((i2 & 896) == 256) | ((i2 & 112) == 32);
            Object rememberedValue = composerImpl.rememberedValue();
            if (z2 || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new Function0() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.AppListPageKt$ShowSystemAction$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Function1.this.invoke(Boolean.valueOf(!z));
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            moreOptionsScope.MenuItem(
                    stringResource,
                    false,
                    (Function0) rememberedValue,
                    composerImpl,
                    (i2 << 9) & 7168,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListPageKt$ShowSystemAction$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListPageKt.access$ShowSystemAction(
                                    MoreOptionsScope.this,
                                    z,
                                    function1,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
