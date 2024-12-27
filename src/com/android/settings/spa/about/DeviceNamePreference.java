package com.android.settings.spa.about;

import android.content.Context;

import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.dialog.AlertDialogButton;
import com.android.settingslib.spa.widget.dialog.AlertDialogPresenter;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import com.sec.ims.presence.ServiceTuple;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceNamePreference {
    public static final DeviceNamePreference INSTANCE = new DeviceNamePreference();

    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.settings.spa.about.DeviceNamePreference$EntryItem$dialogPresenter$2, kotlin.jvm.internal.Lambda] */
    public final void EntryItem(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(495168356);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(-1210218946);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new DeviceNamePresenter(context);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final DeviceNamePresenter deviceNamePresenter = (DeviceNamePresenter) rememberedValue;
            composerImpl.end(false);
            String stringResource =
                    StringResources_androidKt.stringResource(composerImpl, R.string.okay);
            DeviceNamePreference deviceNamePreference = INSTANCE;
            composerImpl.startReplaceGroup(-1210218650);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 =
                        new DeviceNamePreference$EntryItem$dialogPresenter$1$1(
                                0,
                                deviceNamePreference,
                                DeviceNamePreference.class,
                                "confirmChange",
                                "confirmChange()V",
                                0);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            final SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                    rememberAlertDialogPresenter =
                            SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                    new AlertDialogButton(
                                            stringResource,
                                            (Function0) ((KFunction) rememberedValue2),
                                            2),
                                    new AlertDialogButton(
                                            StringResources_androidKt.stringResource(
                                                    composerImpl, R.string.cancel),
                                            (Function0) null,
                                            6),
                                    StringResources_androidKt.stringResource(
                                            composerImpl,
                                            R.string.my_device_info_device_name_preference_title),
                                    ComposableLambdaKt.rememberComposableLambda(
                                            793323518,
                                            new Function2() { // from class:
                                                              // com.android.settings.spa.about.DeviceNamePreference$EntryItem$dialogPresenter$2
                                                {
                                                    super(2);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public final Object invoke(
                                                        Object obj, Object obj2) {
                                                    Composer composer2 = (Composer) obj;
                                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                                        ComposerImpl composerImpl2 =
                                                                (ComposerImpl) composer2;
                                                        if (composerImpl2.getSkipping()) {
                                                            composerImpl2.skipToGroupEnd();
                                                            return Unit.INSTANCE;
                                                        }
                                                    }
                                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                                    TextKt.m210Text4IGK_g(
                                                            DeviceNamePresenter.this
                                                                    .deviceNamePreferenceController
                                                                    .getSummary()
                                                                    .toString(),
                                                            null,
                                                            0L,
                                                            0L,
                                                            null,
                                                            null,
                                                            null,
                                                            0L,
                                                            null,
                                                            null,
                                                            0L,
                                                            0,
                                                            false,
                                                            0,
                                                            0,
                                                            null,
                                                            null,
                                                            composer2,
                                                            0,
                                                            0,
                                                            131070);
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composerImpl),
                                    composerImpl,
                                    0);
            PreferenceKt.Preference(
                    new PreferenceModel(
                            composerImpl,
                            rememberAlertDialogPresenter,
                            deviceNamePresenter) { // from class:
                                                   // com.android.settings.spa.about.DeviceNamePreference$EntryItem$1
                        public final KFunction onClick;
                        public final Function0 summary;
                        public final String title;

                        {
                            this.title =
                                    StringResources_androidKt.stringResource(
                                            composerImpl,
                                            R.string.my_device_info_device_name_preference_title);
                            this.summary =
                                    new Function0() { // from class:
                                                      // com.android.settings.spa.about.DeviceNamePreference$EntryItem$1$summary$1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return DeviceNamePresenter.this
                                                    .deviceNamePreferenceController
                                                    .getSummary()
                                                    .toString();
                                        }
                                    };
                            this.onClick =
                                    new DeviceNamePreference$EntryItem$1$onClick$1(
                                            0,
                                            rememberAlertDialogPresenter,
                                            AlertDialogPresenter.class,
                                            ServiceTuple.BASIC_STATUS_OPEN,
                                            "open()V",
                                            0);
                        }

                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                        public final Function0 getOnClick() {
                            return (Function0) this.onClick;
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
                    composerImpl,
                    0,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.about.DeviceNamePreference$EntryItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            DeviceNamePreference.this.EntryItem(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
