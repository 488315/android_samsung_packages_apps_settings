package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.foundation.layout.RowScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$RegularScaffoldKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f89lambda1 =
            new ComposableLambdaImpl(
                    -1104037747,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$RegularScaffoldKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter((RowScope) obj, "$this$null");
                            if ((intValue & 81) == 16) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f90lambda2 =
            new ComposableLambdaImpl(
                    1935221320,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$RegularScaffoldKt$lambda-2$1

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        /* renamed from: com.android.settingslib.spa.widget.scaffold.ComposableSingletons$RegularScaffoldKt$lambda-2$1$1, reason: invalid class name */
                        public final class AnonymousClass1 implements PreferenceModel {
                            public final /* synthetic */ int $r8$classId;

                            public /* synthetic */ AnonymousClass1(int i) {
                                this.$r8$classId = i;
                            }

                            @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                            public final String getTitle() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        return "Item 1";
                                    case 1:
                                        return "Item 2";
                                    case 2:
                                        return "Item 1";
                                    case 3:
                                        return "Item 2";
                                    case 4:
                                        return "Item 1";
                                    default:
                                        return "Item 2";
                                }
                            }
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i = 0;
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            PreferenceKt.Preference(new AnonymousClass1(i), false, composer, 0, 2);
                            PreferenceKt.Preference(new AnonymousClass1(1), false, composer, 0, 2);
                            return Unit.INSTANCE;
                        }
                    });

    static {
        int i = ComposableSingletons$RegularScaffoldKt$lambda3$1.$r8$clinit;
    }
}
