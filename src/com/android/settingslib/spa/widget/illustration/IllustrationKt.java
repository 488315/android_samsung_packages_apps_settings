package com.android.settingslib.spa.widget.illustration;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;

import com.android.settings.R;
import com.android.settings.spa.app.appcompat.ComposableSingletons$UserAspectRatioAppsPageProviderKt$lambda3$1;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class IllustrationKt {
    public static final void Illustration(
            final IllustrationModel model, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-332988545);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(model) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Illustration(
                    R.raw.user_aspect_ratio_education,
                    ((ComposableSingletons$UserAspectRatioAppsPageProviderKt$lambda3$1
                                            .AnonymousClass2)
                                    model)
                            .resourceType,
                    Modifier.Companion.$$INSTANCE,
                    composerImpl,
                    384,
                    0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.illustration.IllustrationKt$Illustration$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            IllustrationKt.Illustration(
                                    IllustrationModel.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Illustration(
            final int r17,
            final com.android.settingslib.spa.widget.illustration.ResourceType r18,
            androidx.compose.ui.Modifier r19,
            androidx.compose.runtime.Composer r20,
            final int r21,
            final int r22) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.illustration.IllustrationKt.Illustration(int,"
                    + " com.android.settingslib.spa.widget.illustration.ResourceType,"
                    + " androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }
}
