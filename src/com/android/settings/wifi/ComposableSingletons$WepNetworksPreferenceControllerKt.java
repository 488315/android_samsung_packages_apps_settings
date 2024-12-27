package com.android.settings.wifi;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.style.TextAlign;

import com.android.settings.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$WepNetworksPreferenceControllerKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f69lambda1 =
            new ComposableLambdaImpl(
                    769061620,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.wifi.ComposableSingletons$WepNetworksPreferenceControllerKt$lambda-1$1
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
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer,
                                            R.string.wifi_settings_wep_networks_disconnect_summary),
                                    SizeKt.FillWholeMaxWidth,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    new TextAlign(3),
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    48,
                                    0,
                                    130556);
                            return Unit.INSTANCE;
                        }
                    });
}
