package com.android.settings.system;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanDelegate;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalChangeFlowKt;
import com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u00002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r"
                + "\u0010\n"
                + "\u001a\u00020\u000bH\u0017¢\u0006\u0002\u0010\fJ\r"
                + "\u0010\r"
                + "\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020"
                + "\t0\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0010²\u0006\n"
                + "\u0010\u0011\u001a\u00020\tX\u008a\u0084\u0002"
        },
        d2 = {
            "Lcom/android/settings/system/DeveloperOptionsController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "isDevelopmentSettingsEnabledFlow",
            "Lkotlinx/coroutines/flow/Flow;",
            ApnSettings.MVNO_NONE,
            "Content",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "DeveloperOptionsPreference",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core",
            "isDevelopmentSettingsEnabled"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DeveloperOptionsController extends ComposePreferenceController {
    public static final int $stable = 8;
    private final Flow isDevelopmentSettingsEnabledFlow;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeveloperOptionsController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        boolean z = Build.IS_ENG;
        KProperty[] kPropertyArr = SettingsGlobalBooleanKt.$$delegatedProperties;
        this.isDevelopmentSettingsEnabledFlow =
                FlowKt.distinctUntilChanged(
                        new SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1(
                                SettingsGlobalChangeFlowKt.settingsGlobalChangeFlow(
                                        context, "development_settings_enabled", true),
                                new SettingsGlobalBooleanDelegate(
                                        context, "development_settings_enabled", z)));
    }

    private static final boolean Content$lambda$0(State state) {
        return ((Boolean) state.getValue()).booleanValue();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1536683133);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (Content$lambda$0(
                FlowExtKt.collectAsStateWithLifecycle(
                        this.isDevelopmentSettingsEnabledFlow, Boolean.FALSE, composerImpl, 56))) {
            DeveloperOptionsPreference(composerImpl, 8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.system.DeveloperOptionsController$Content$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            DeveloperOptionsController.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void DeveloperOptionsPreference(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1240858731);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        RestrictedPreferenceKt.RestrictedPreference(
                new PreferenceModel(
                        composerImpl,
                        this) { // from class:
                                // com.android.settings.system.DeveloperOptionsController$DeveloperOptionsPreference$1
                    public final ComposableLambdaImpl icon =
                            ComposableSingletons$DeveloperOptionsControllerKt.f68lambda1;
                    public final Function0 onClick;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.development_settings_title);
                        this.onClick =
                                new Function0() { // from class:
                                                  // com.android.settings.system.DeveloperOptionsController$DeveloperOptionsPreference$1$onClick$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        Context context;
                                        context =
                                                ((AbstractPreferenceController)
                                                                DeveloperOptionsController.this)
                                                        .mContext;
                                        SubSettingLauncher subSettingLauncher =
                                                new SubSettingLauncher(context);
                                        String qualifiedName =
                                                Reflection.factory
                                                        .getOrCreateKotlinClass(
                                                                DevelopmentSettingsDashboardFragment
                                                                        .class)
                                                        .getQualifiedName();
                                        SubSettingLauncher.LaunchRequest launchRequest =
                                                subSettingLauncher.mLaunchRequest;
                                        launchRequest.mDestinationName = qualifiedName;
                                        launchRequest.mSourceMetricsCategory = 744;
                                        subSettingLauncher.launch();
                                        return Unit.INSTANCE;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function2 getIcon() {
                        return this.icon;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return this.onClick;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                new Restrictions(
                        0, CollectionsKt__CollectionsJVMKt.listOf("no_debugging_features"), 5),
                composerImpl,
                64);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.system.DeveloperOptionsController$DeveloperOptionsPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            DeveloperOptionsController.this.DeveloperOptionsPreference(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        return ContextsKt.getUserManager(mContext).isAdminUser() ? 0 : 4;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
