package com.samsung.android.scloud.lib.platform.api;

import android.os.Bundle;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.scloud.lib.platform.data.ConfigurationDataSet;
import com.samsung.android.scloud.lib.platform.vo.ConfigurationVo;
import com.samsung.android.settings.goodsettings.GoodSettingsProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Configuration extends AbstractApi {
    public final ConfigurationDataSet initialize(ConfigurationVo configurationVo) {
        StringBuilder sb = new StringBuilder("initialize : sv080do9zh, appVer : ");
        String str = configurationVo.appVersion;
        sb.append(str);
        String sb2 = sb.toString();
        String str2 = this.TAG;
        LOG.i(str2, sb2);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("token", configurationVo.token);
            bundle.putString("appId", "sv080do9zh");
            bundle.putString(FieldName.VERSION, str);
            bundle.putString("receiverPackageName", "com.samsung.android.app.settings.bixby");
            return DataSetFactory.newConfigurationDataSet(
                    call(
                            GoodSettingsProvider.METHOD_INITIALIZE,
                            this.context.getPackageName(),
                            bundle),
                    null);
        } catch (Exception e) {
            LOG.e(str2, "cannot register package : " + e.getMessage());
            return DataSetFactory.newConfigurationDataSet(e);
        }
    }
}
