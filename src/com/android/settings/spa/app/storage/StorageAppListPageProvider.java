package com.android.settings.spa.app.storage;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.spa.framework.common.SettingsPageProvider;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class StorageAppListPageProvider implements SettingsPageProvider {
    public final StorageType type;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Apps extends StorageAppListPageProvider {
        public static final Apps INSTANCE = new Apps(StorageType.Apps.INSTANCE);
        public static final String name = "StorageAppList";

        @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
        public final String getName() {
            return name;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Games extends StorageAppListPageProvider {
        public static final Games INSTANCE = new Games(StorageType.Games.INSTANCE);
        public static final String name = "GameStorageAppList";

        @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
        public final String getName() {
            return name;
        }
    }

    public StorageAppListPageProvider(StorageType storageType) {
        this.type = storageType;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-513102300);
        if ((i & 112) == 0) {
            i2 = (composerImpl.changed(this) ? 32 : 16) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 81) == 16 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            StorageAppListKt.StorageAppListPage(this.type, null, composerImpl, 0, 2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.storage.StorageAppListPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            StorageAppListPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
