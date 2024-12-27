package com.samsung.android.settings.gts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DisplaySettingsGtsProvider extends SettingsGtsProvider {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // com.samsung.android.settings.gts.SettingsGtsProvider
    public final List getGroups() {
        return (List)
                Arrays.stream(GtsResources.GtsGroup.values())
                        .filter(new DisplaySettingsGtsProvider$$ExternalSyntheticLambda0())
                        .collect(Collectors.toList());
    }
}
