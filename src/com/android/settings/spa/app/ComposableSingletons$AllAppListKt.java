package com.android.settings.spa.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.template.app.AppListInput;
import com.android.settingslib.spaprivileged.template.app.AppListKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$AllAppListKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f32lambda1 =
            new ComposableLambdaImpl(
                    -70075166,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.app.ComposableSingletons$AllAppListKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            ((Number) obj3).intValue();
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            PreferenceKt.Preference(
                                    new PreferenceModel(
                                            composer) { // from class:
                                                        // com.android.settings.spa.app.ComposableSingletons$AllAppListKt$lambda-1$1.1
                                        public final Lambda onClick;
                                        public final String title;

                                        {
                                            this.title =
                                                    StringResources_androidKt.stringResource(
                                                            composer, R.string.all_apps);
                                            AllAppListPageProvider allAppListPageProvider =
                                                    AllAppListPageProvider.INSTANCE;
                                            this.onClick =
                                                    (Lambda)
                                                            NavControllerWrapperKt.navigator(
                                                                    composer, "AllAppList");
                                        }

                                        /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final Function0 getOnClick() {
                                            return this.onClick;
                                        }

                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final String getTitle() {
                                            return this.title;
                                        }
                                    },
                                    false,
                                    composer,
                                    0,
                                    2);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f33lambda2 =
            new ComposableLambdaImpl(
                    -2070588868,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.app.ComposableSingletons$AllAppListKt$lambda-2$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AppListInput appListInput = (AppListInput) obj;
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(appListInput, "$this$null");
                            if ((intValue & 14) == 0) {
                                intValue |= ((ComposerImpl) composer).changed(appListInput) ? 4 : 2;
                            }
                            if ((intValue & 91) == 18) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            AppListKt.AppList(appListInput, composer, (intValue & 14) | 8);
                            return Unit.INSTANCE;
                        }
                    });
}
