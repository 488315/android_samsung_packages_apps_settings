package com.android.settingslib.spaprivileged.template.scaffold;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;
import com.android.settingslib.spaprivileged.model.enterprise.BaseUserRestricted;
import com.android.settingslib.spaprivileged.model.enterprise.BlockedByAdminImpl;
import com.android.settingslib.spaprivileged.model.enterprise.BlockedByEcmImpl;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictedMode;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictionsProviderKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedMenuItemKt {
    public static final void RestrictedMenuItem(
            final MoreOptionsScope moreOptionsScope,
            final String text,
            boolean z,
            final Restrictions restrictions,
            final Function0 onClick,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(moreOptionsScope, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1047249980);
        final boolean z2 = (i2 & 2) != 0 ? true : z;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        RestrictedMenuItemImpl(
                moreOptionsScope,
                text,
                z2,
                restrictions,
                onClick,
                RestrictedMenuItemKt$RestrictedMenuItem$1.INSTANCE,
                composerImpl,
                (i & 14) | 4096 | (i & 112) | (i & 896) | (57344 & i),
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.scaffold.RestrictedMenuItemKt$RestrictedMenuItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedMenuItemKt.RestrictedMenuItem(
                                    MoreOptionsScope.this,
                                    text,
                                    z2,
                                    restrictions,
                                    onClick,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void RestrictedMenuItemImpl(
            final MoreOptionsScope moreOptionsScope,
            final String text,
            boolean z,
            final Restrictions restrictions,
            final Function0 onClick,
            final Function2 restrictionsProviderFactory,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(moreOptionsScope, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        Intrinsics.checkNotNullParameter(
                restrictionsProviderFactory, "restrictionsProviderFactory");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1772312054);
        final boolean z2 = (i2 & 2) != 0 ? true : z;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final RestrictedMode restrictedMode =
                (RestrictedMode)
                        RestrictionsProviderKt.rememberRestrictedMode(
                                        restrictionsProviderFactory, restrictions, composerImpl)
                                .getValue();
        moreOptionsScope.MenuItem(
                text,
                z2 && restrictedMode != BaseUserRestricted.INSTANCE,
                new Function0() { // from class:
                                  // com.android.settingslib.spaprivileged.template.scaffold.RestrictedMenuItemKt$RestrictedMenuItemImpl$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        RestrictedMode restrictedMode2 = RestrictedMode.this;
                        if (restrictedMode2 instanceof BlockedByAdminImpl) {
                            BlockedByAdminImpl blockedByAdminImpl =
                                    (BlockedByAdminImpl) restrictedMode2;
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                    blockedByAdminImpl.context, blockedByAdminImpl.enforcedAdmin);
                        } else if (restrictedMode2 instanceof BlockedByEcmImpl) {
                            BlockedByEcmImpl blockedByEcmImpl = (BlockedByEcmImpl) restrictedMode2;
                            blockedByEcmImpl.context.startActivity(blockedByEcmImpl.intent);
                        } else {
                            onClick.mo1068invoke();
                        }
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                ((i >> 3) & 14) | ((i << 9) & 7168),
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.scaffold.RestrictedMenuItemKt$RestrictedMenuItemImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedMenuItemKt.RestrictedMenuItemImpl(
                                    MoreOptionsScope.this,
                                    text,
                                    z2,
                                    restrictions,
                                    onClick,
                                    restrictionsProviderFactory,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
