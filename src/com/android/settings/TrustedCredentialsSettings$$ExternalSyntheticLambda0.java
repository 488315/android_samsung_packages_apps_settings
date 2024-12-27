package com.android.settings;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final /* synthetic */ class TrustedCredentialsSettings$$ExternalSyntheticLambda0
        implements TabLayoutMediator.TabConfigurationStrategy {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
    public final void onConfigureTab(TabLayout.Tab tab, int i) {
        int i2;
        i2 = ((TrustedCredentialsSettings.Tab) TrustedCredentialsSettings.TABS.get(i)).mLabel;
        TabLayout tabLayout = tab.parent;
        if (tabLayout == null) {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }
        tab.setText(tabLayout.getResources().getText(i2));
    }
}
