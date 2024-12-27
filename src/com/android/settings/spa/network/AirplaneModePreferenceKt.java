package com.android.settings.spa.network;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.livedata.LiveDataAdapterKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

import com.android.settings.R;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AirplaneModePreferenceKt {
    public static final void AirplaneModePreference(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1654389747);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(436151416);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new AirplaneModeController(context);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final AirplaneModeController airplaneModeController =
                    (AirplaneModeController) rememberedValue;
            composerImpl.end(false);
            if (!airplaneModeController
                            .context
                            .getResources()
                            .getBoolean(R.bool.config_show_toggle_airplane)
                    || airplaneModeController
                            .context
                            .getPackageManager()
                            .hasSystemFeature("android.software.leanback")) {
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.block =
                            new Function2() { // from class:
                                              // com.android.settings.spa.network.AirplaneModePreferenceKt$AirplaneModePreference$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    AirplaneModePreferenceKt.AirplaneModePreference(
                                            (Composer) obj,
                                            RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    return Unit.INSTANCE;
                                }
                            };
                    return;
                }
                return;
            }
            final MutableState observeAsState =
                    LiveDataAdapterKt.observeAsState(
                            airplaneModeController._airplaneModeState,
                            Boolean.valueOf(
                                    WirelessUtils.isAirplaneModeOn(
                                            airplaneModeController.airplaneModeEnabler.mContext)),
                            composerImpl);
            SwitchPreferenceKt.SwitchPreference(
                    new SwitchPreferenceModel(
                            context,
                            observeAsState,
                            airplaneModeController) { // from class:
                                                      // com.android.settings.spa.network.AirplaneModePreferenceKt$AirplaneModePreference$2
                        public final Function0 checked;
                        public final ComposableLambdaImpl icon;
                        public final Function1 onCheckedChange;
                        public final String title;

                        {
                            String string = context.getString(R.string.airplane_mode);
                            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                            this.title = string;
                            this.checked =
                                    new Function0() { // from class:
                                                      // com.android.settings.spa.network.AirplaneModePreferenceKt$AirplaneModePreference$2$checked$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            Boolean bool = (Boolean) observeAsState.getValue();
                                            bool.booleanValue();
                                            return bool;
                                        }
                                    };
                            this.onCheckedChange =
                                    new Function1() { // from class:
                                                      // com.android.settings.spa.network.AirplaneModePreferenceKt$AirplaneModePreference$2$onCheckedChange$1
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            boolean booleanValue = ((Boolean) obj).booleanValue();
                                            AirplaneModeController airplaneModeController2 =
                                                    AirplaneModeController.this;
                                            if (WirelessUtils.isAirplaneModeOn(
                                                            airplaneModeController2
                                                                    .airplaneModeEnabler
                                                                    .mContext)
                                                    != booleanValue) {
                                                airplaneModeController2.airplaneModeEnabler
                                                        .setAirplaneMode(booleanValue);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                            this.icon = ComposableSingletons$AirplaneModePreferenceKt.f53lambda1;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function0 getChecked() {
                            return this.checked;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function2 getIcon() {
                            return this.icon;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function1 getOnCheckedChange() {
                            return this.onCheckedChange;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final String getTitle() {
                            return this.title;
                        }
                    },
                    composerImpl,
                    0);
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.AirplaneModePreferenceKt$AirplaneModePreference$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AirplaneModePreferenceKt.AirplaneModePreference(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
