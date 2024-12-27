package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbstractTryItem implements TryItem {
    public final Context mContext;

    public AbstractTryItem(Context context) {
        this.mContext = context;
    }

    public String getFragmentClassName() {
        return null;
    }

    public String getHighlightablePreferenceKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String fragmentClassName = getFragmentClassName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = fragmentClassName;
        launchRequest.mSourceMetricsCategory = 0;
        Intent intent = subSettingLauncher.toIntent();
        if (getHighlightablePreferenceKey() != null) {
            Bundle bundle = new Bundle();
            bundle.putString(":settings:fragment_args_key", getHighlightablePreferenceKey());
            intent.putExtra(":settings:show_fragment_args", bundle);
        }
        return intent;
    }
}
