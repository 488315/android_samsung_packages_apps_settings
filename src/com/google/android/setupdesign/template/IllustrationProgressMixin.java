package com.google.android.setupdesign.template;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.template.Mixin;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class IllustrationProgressMixin implements Mixin {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ProgressConfig {
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_DEFAULT(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_DEFAULT),
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_ACCOUNT(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_ACCOUNT),
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_CONNECTION(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_CONNECTION),
        /* JADX INFO: Fake field, exist only in values array */
        CONFIG_UPDATE(PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_UPDATE);

        private final PartnerConfig config;

        ProgressConfig(PartnerConfig partnerConfig) {
            if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.ILLUSTRATION) {
                throw new IllegalArgumentException(
                        "Illustration progress only allow illustration resource");
            }
            this.config = partnerConfig;
        }
    }
}
