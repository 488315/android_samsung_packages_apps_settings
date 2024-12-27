package com.android.settings.datausage;

import android.content.Context;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.lib.BillingCycleRepository;
import com.android.settings.datausage.lib.BillingCycleRepository$isModifiableFlow$$inlined$map$1;
import com.android.settings.spa.preference.ComposePreference;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000(\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B%\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b"
                + "\t\u0010\n"
                + "¨\u0006\r"
                + "²\u0006\f\u0010\f\u001a\u00020\u000b8\n"
                + "X\u008a\u0084\u0002"
        },
        d2 = {
            "Lcom/android/settings/datausage/BillingCyclePreference;",
            "Lcom/android/settings/spa/preference/ComposePreference;",
            "Lcom/android/settings/datausage/TemplatePreference;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attrs",
            "Lcom/android/settings/datausage/lib/BillingCycleRepository;",
            "repository",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;Lcom/android/settings/datausage/lib/BillingCycleRepository;)V",
            ApnSettings.MVNO_NONE,
            "isModifiable",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class BillingCyclePreference extends ComposePreference implements TemplatePreference {
    public final BillingCycleRepository repository;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BillingCyclePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, null, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.android.settings.datausage.TemplatePreference
    public final void setTemplate(final NetworkTemplate template, final int i) {
        Intrinsics.checkNotNullParameter(template, "template");
        this.content =
                new ComposableLambdaImpl(
                        -1094640839,
                        true,
                        new Function2() { // from class:
                                          // com.android.settings.datausage.BillingCyclePreference$setTemplate$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

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
                                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                                composerImpl2.startReplaceGroup(1743126325);
                                boolean changed = composerImpl2.changed(i);
                                BillingCyclePreference billingCyclePreference = this;
                                int i2 = i;
                                Object rememberedValue = composerImpl2.rememberedValue();
                                if (changed || rememberedValue == Composer.Companion.Empty) {
                                    BillingCycleRepository billingCycleRepository =
                                            billingCyclePreference.repository;
                                    rememberedValue =
                                            FlowKt.flowOn(
                                                    FlowKt.buffer$default(
                                                            new BillingCycleRepository$isModifiableFlow$$inlined$map$1(
                                                                    billingCycleRepository
                                                                            .telephonyRepository
                                                                            .isDataEnabledFlow(i2),
                                                                    billingCycleRepository),
                                                            -1),
                                                    Dispatchers.Default);
                                    composerImpl2.updateRememberedValue(rememberedValue);
                                }
                                composerImpl2.end(false);
                                PreferenceKt.Preference(
                                        new PreferenceModel(
                                                composerImpl2,
                                                FlowExtKt.collectAsStateWithLifecycle(
                                                        (Flow) rememberedValue,
                                                        Boolean.FALSE,
                                                        composerImpl2,
                                                        56),
                                                this,
                                                template) { // from class:
                                                            // com.android.settings.datausage.BillingCyclePreference$setTemplate$1.1
                                            public final Function0 enabled;
                                            public final Function0 onClick;
                                            public final String title;

                                            {
                                                this.title =
                                                        StringResources_androidKt.stringResource(
                                                                composerImpl2,
                                                                R.string.billing_cycle);
                                                this.enabled =
                                                        new Function0() { // from class:
                                                                          // com.android.settings.datausage.BillingCyclePreference$setTemplate$1$1$enabled$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            /* renamed from: invoke */
                                                            public final Object mo1068invoke() {
                                                                Boolean bool =
                                                                        (Boolean) r1.getValue();
                                                                bool.booleanValue();
                                                                return bool;
                                                            }
                                                        };
                                                this.onClick =
                                                        new Function0() { // from class:
                                                                          // com.android.settings.datausage.BillingCyclePreference$setTemplate$1$1$onClick$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            /* renamed from: invoke */
                                                            public final Object mo1068invoke() {
                                                                BillingCyclePreference
                                                                        billingCyclePreference2 =
                                                                                BillingCyclePreference
                                                                                        .this;
                                                                Parcelable parcelable = r2;
                                                                billingCyclePreference2.getClass();
                                                                Bundle bundle = new Bundle();
                                                                bundle.putParcelable(
                                                                        "network_template",
                                                                        parcelable);
                                                                SubSettingLauncher
                                                                        subSettingLauncher =
                                                                                new SubSettingLauncher(
                                                                                        billingCyclePreference2
                                                                                                .getContext());
                                                                String name =
                                                                        BillingCycleSettings.class
                                                                                .getName();
                                                                SubSettingLauncher.LaunchRequest
                                                                        launchRequest =
                                                                                subSettingLauncher
                                                                                        .mLaunchRequest;
                                                                launchRequest.mDestinationName =
                                                                        name;
                                                                launchRequest.mArguments = bundle;
                                                                subSettingLauncher.setTitleRes(
                                                                        R.string.billing_cycle,
                                                                        null);
                                                                launchRequest
                                                                                .mSourceMetricsCategory =
                                                                        0;
                                                                subSettingLauncher.launch();
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                            }

                                            @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                            public final Function0 getEnabled() {
                                                return this.enabled;
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
                                        false,
                                        composerImpl2,
                                        0,
                                        2);
                                return Unit.INSTANCE;
                            }
                        });
    }

    public /* synthetic */ BillingCyclePreference(
            Context context,
            AttributeSet attributeSet,
            BillingCycleRepository billingCycleRepository,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                context,
                attributeSet,
                (i & 4) != 0 ? new BillingCycleRepository(context) : billingCycleRepository);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BillingCyclePreference(
            Context context, AttributeSet attributeSet, BillingCycleRepository repository) {
        super(context, attributeSet, 0, 0, 12, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(repository, "repository");
        this.repository = repository;
    }
}
