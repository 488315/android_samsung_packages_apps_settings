package com.android.settingslib.spa.widget.preference;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settings.spa.network.SimOnboardingSelectSimKt$SelectSimBody$1$1;
import com.android.settingslib.spa.framework.util.EntryHighlightKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CheckboxPreferenceKt {
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settingslib.spa.widget.preference.CheckboxPreferenceKt$CheckboxPreference$1, kotlin.jvm.internal.Lambda] */
    public static final void CheckboxPreference(
            final CheckboxPreferenceModel model, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1336118554);
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
                            -1058445865,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.preference.CheckboxPreferenceKt$CheckboxPreference$1
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
                                    SimOnboardingSelectSimKt$SelectSimBody$1$1
                                            simOnboardingSelectSimKt$SelectSimBody$1$1 =
                                                    (SimOnboardingSelectSimKt$SelectSimBody$1$1)
                                                            CheckboxPreferenceModel.this;
                                    String str = simOnboardingSelectSimKt$SelectSimBody$1$1.title;
                                    Function0 summary =
                                            simOnboardingSelectSimKt$SelectSimBody$1$1.getSummary();
                                    CheckboxPreferenceModel.this.getClass();
                                    CheckboxPreferenceKt.m1045InternalCheckboxPreference0HqY7hA(
                                            str,
                                            summary,
                                            null,
                                            (Boolean)
                                                    ((SimOnboardingSelectSimKt$SelectSimBody$1$1)
                                                                    CheckboxPreferenceModel.this)
                                                            .checked.mo1068invoke(),
                                            ((Boolean)
                                                            ((SimOnboardingSelectSimKt$SelectSimBody$1$1)
                                                                            CheckboxPreferenceModel
                                                                                    .this)
                                                                    .changeable.mo1068invoke())
                                                    .booleanValue(),
                                            0.0f,
                                            0.0f,
                                            0.0f,
                                            ((SimOnboardingSelectSimKt$SelectSimBody$1$1)
                                                            CheckboxPreferenceModel.this)
                                                    .onCheckedChange,
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
                                      // com.android.settingslib.spa.widget.preference.CheckboxPreferenceKt$CheckboxPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CheckboxPreferenceKt.CheckboxPreference(
                                    CheckboxPreferenceModel.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01bd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01fc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01c7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x00e9  */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.settingslib.spa.widget.preference.CheckboxPreferenceKt$InternalCheckboxPreference$3, kotlin.jvm.internal.Lambda] */
    /* renamed from: InternalCheckboxPreference-0HqY7hA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1045InternalCheckboxPreference0HqY7hA(
            final java.lang.String r33,
            kotlin.jvm.functions.Function0 r34,
            kotlin.jvm.functions.Function2 r35,
            final java.lang.Boolean r36,
            boolean r37,
            float r38,
            float r39,
            float r40,
            final kotlin.jvm.functions.Function1 r41,
            androidx.compose.runtime.Composer r42,
            final int r43,
            final int r44) {
        /*
            Method dump skipped, instructions count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.preference.CheckboxPreferenceKt.m1045InternalCheckboxPreference0HqY7hA(java.lang.String,"
                    + " kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function2,"
                    + " java.lang.Boolean, boolean, float, float, float,"
                    + " kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }
}
