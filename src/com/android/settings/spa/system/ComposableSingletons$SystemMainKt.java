package com.android.settings.spa.system;

import androidx.compose.material.icons.outlined.InfoKt;
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
import com.android.settingslib.spa.widget.ui.IconKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$SystemMainKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f65lambda1 =
            new ComposableLambdaImpl(
                    1810834996,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.system.ComposableSingletons$SystemMainKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            LanguageAndInputPageProvider.INSTANCE.EntryItem(composer, 6);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f66lambda2 =
            new ComposableLambdaImpl(
                    -2128894681,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.system.ComposableSingletons$SystemMainKt$lambda-2$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            IconKt.SettingsIcon(InfoKt.getInfo(), composer, 0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f67lambda3 =
            new ComposableLambdaImpl(
                    -762045976,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.system.ComposableSingletons$SystemMainKt$lambda-3$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            ((Number) obj3).intValue();
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            PreferenceKt.Preference(
                                    new PreferenceModel(
                                            composer,
                                            StringResources_androidKt.stringResource(
                                                    composer,
                                                    R.string
                                                            .system_dashboard_summary)) { // from
                                                                                          // class:
                                                                                          // com.android.settings.spa.system.ComposableSingletons$SystemMainKt$lambda-3$1.1
                                        public final ComposableLambdaImpl icon;
                                        public final Lambda onClick;
                                        public final Function0 summary;
                                        public final String title;

                                        {
                                            this.title =
                                                    StringResources_androidKt.stringResource(
                                                            composer,
                                                            R.string.header_category_system);
                                            this.summary =
                                                    new Function0() { // from class:
                                                                      // com.android.settings.spa.system.ComposableSingletons$SystemMainKt$lambda-3$1$1$summary$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        /* renamed from: invoke */
                                                        public final Object mo1068invoke() {
                                                            return r1;
                                                        }
                                                    };
                                            SystemMainPageProvider systemMainPageProvider =
                                                    SystemMainPageProvider.INSTANCE;
                                            this.onClick =
                                                    (Lambda)
                                                            NavControllerWrapperKt.navigator(
                                                                    composer, "SystemMain");
                                            this.icon =
                                                    ComposableSingletons$SystemMainKt.f66lambda2;
                                        }

                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final Function2 getIcon() {
                                            return this.icon;
                                        }

                                        /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final Function0 getOnClick() {
                                            return this.onClick;
                                        }

                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final Function0 getSummary() {
                                            return this.summary;
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
}
