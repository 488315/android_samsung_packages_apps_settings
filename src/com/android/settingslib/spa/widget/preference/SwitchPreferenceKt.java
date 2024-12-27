package com.android.settingslib.spa.widget.preference;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settingslib.spa.framework.util.EntryHighlightKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SwitchPreferenceKt {
    /* JADX WARN: Removed duplicated region for block: B:104:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01fd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01c7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x00ed  */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.settingslib.spa.widget.preference.SwitchPreferenceKt$InternalSwitchPreference$3, kotlin.jvm.internal.Lambda] */
    /* renamed from: InternalSwitchPreference-0HqY7hA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1046InternalSwitchPreference0HqY7hA(
            final java.lang.String r34,
            kotlin.jvm.functions.Function0 r35,
            kotlin.jvm.functions.Function2 r36,
            final java.lang.Boolean r37,
            boolean r38,
            float r39,
            float r40,
            float r41,
            final kotlin.jvm.functions.Function1 r42,
            androidx.compose.runtime.Composer r43,
            final int r44,
            final int r45) {
        /*
            Method dump skipped, instructions count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.preference.SwitchPreferenceKt.m1046InternalSwitchPreference0HqY7hA(java.lang.String,"
                    + " kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function2,"
                    + " java.lang.Boolean, boolean, float, float, float,"
                    + " kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settingslib.spa.widget.preference.SwitchPreferenceKt$SwitchPreference$1, kotlin.jvm.internal.Lambda] */
    public static final void SwitchPreference(
            final SwitchPreferenceModel model, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-108298571);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(model) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            EntryHighlightKt.EntryHighlight(
                    ComposableLambdaKt.rememberComposableLambda(
                            1740749414,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.preference.SwitchPreferenceKt$SwitchPreference$1
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    SwitchPreferenceKt.m1046InternalSwitchPreference0HqY7hA(
                                            SwitchPreferenceModel.this.getTitle(),
                                            SwitchPreferenceModel.this.getSummary(),
                                            SwitchPreferenceModel.this.getIcon(),
                                            (Boolean)
                                                    SwitchPreferenceModel.this
                                                            .getChecked()
                                                            .mo1068invoke(),
                                            ((Boolean)
                                                            SwitchPreferenceModel.this
                                                                    .getChangeable()
                                                                    .mo1068invoke())
                                                    .booleanValue(),
                                            0.0f,
                                            0.0f,
                                            0.0f,
                                            SwitchPreferenceModel.this.getOnCheckedChange(),
                                            composer2,
                                            0,
                                            224);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    6);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.SwitchPreferenceKt$SwitchPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SwitchPreferenceKt.SwitchPreference(
                                    SwitchPreferenceModel.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
