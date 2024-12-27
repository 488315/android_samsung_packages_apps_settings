package com.android.settings.spa.app.specialaccess;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.common.SettingsEntry;
import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListProvider;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SpecialAppAccessPageProvider implements SettingsPageProvider {
    public static final SpecialAppAccessPageProvider INSTANCE;
    public static final SettingsPage owner;

    static {
        SpecialAppAccessPageProvider specialAppAccessPageProvider =
                new SpecialAppAccessPageProvider();
        INSTANCE = specialAppAccessPageProvider;
        owner = SettingsPageProviderKt.createSettingsPage(specialAppAccessPageProvider, null);
    }

    public final void EntryItem(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2039174106);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            PreferenceKt.Preference(
                    new PreferenceModel(
                            composerImpl) { // from class:
                                            // com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider$EntryItem$1
                        public final Lambda onClick;
                        public final String title;

                        {
                            this.title =
                                    StringResources_androidKt.stringResource(
                                            composerImpl, R.string.special_access);
                            SpecialAppAccessPageProvider specialAppAccessPageProvider =
                                    SpecialAppAccessPageProvider.INSTANCE;
                            this.onClick =
                                    (Lambda)
                                            NavControllerWrapperKt.navigator(
                                                    composerImpl, "SpecialAppAccess");
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
                    composerImpl,
                    0,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider$EntryItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SpecialAppAccessPageProvider.this.EntryItem(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider$Page$1, kotlin.jvm.internal.Lambda] */
    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-321362238);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        RegularScaffoldKt.RegularScaffold(
                StringResources_androidKt.stringResource(composerImpl, R.string.special_access),
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        -300057465,
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider$Page$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                                Iterator it =
                                        ((ArrayList)
                                                        SpecialAppAccessPageProvider.INSTANCE
                                                                .buildEntry(bundle))
                                                .iterator();
                                while (it.hasNext()) {
                                    ((SettingsEntry) it.next()).UiLayout(null, composer2, 64, 1);
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                384,
                2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider$Page$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SpecialAppAccessPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List buildEntry(Bundle bundle) {
        List listOf =
                CollectionsKt__CollectionsKt.listOf(
                        (Object[])
                                new TogglePermissionAppListProvider[] {
                                    AllFilesAccessAppListProvider.INSTANCE,
                                    DisplayOverOtherAppsAppListProvider.INSTANCE,
                                    MediaManagementAppsAppListProvider.INSTANCE,
                                    MediaRoutingControlAppListProvider.INSTANCE,
                                    ModifySystemSettingsAppListProvider.INSTANCE,
                                    UseFullScreenIntentAppListProvider.INSTANCE,
                                    PictureInPictureListProvider.INSTANCE,
                                    InstallUnknownAppsListProvider.INSTANCE,
                                    AlarmsAndRemindersAppListProvider.INSTANCE,
                                    WifiControlAppListProvider.INSTANCE,
                                    LongBackgroundTasksAppListProvider.INSTANCE,
                                    TurnScreenOnAppsAppListProvider.INSTANCE
                                });
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        Iterator it = listOf.iterator();
        while (it.hasNext()) {
            SettingsEntryBuilder buildAppListInjectEntry =
                    ((TogglePermissionAppListProvider) it.next()).buildAppListInjectEntry();
            SettingsEntryBuilder.setLink$default(buildAppListInjectEntry, owner, null, 2);
            arrayList.add(buildAppListInjectEntry.build());
        }
        return arrayList;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "SpecialAppAccess";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final boolean isEnabled() {
        return false;
    }
}
