package com.samsung.android.settings.eternal.policy.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.samsung.android.scloud.lib.platform.api.Configuration;
import com.samsung.android.scloud.lib.platform.api.DataSetFactory;
import com.samsung.android.scloud.lib.platform.api.LOG;
import com.samsung.android.scloud.lib.platform.data.ConfigurationDataSet;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.scpm.ScpmUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SCPMPolicy extends PolicyProvider {
    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getPolicyId() {
        return "SCPMPolicy";
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getPolicyVersion() {
        return this.mVersion;
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getRawPolicy() {
        ConfigurationDataSet newConfigurationDataSet;
        ScpmUtils scpmUtils = ScpmUtils.getInstance(this.mContext);
        SharedPreferences sharedPreferences =
                scpmUtils.mContext.getSharedPreferences("scpmInfo", 0);
        String string =
                sharedPreferences != null ? sharedPreferences.getString("token", null) : null;
        Context context = scpmUtils.mContext;
        Configuration configuration = new Configuration(context, "ConfigurationApi");
        context.getApplicationContext();
        String str = configuration.TAG;
        LOG.i(str, "getData : BNR_ETERNAL_POLICY");
        String str2 = "getData : BNR_ETERNAL_POLICY, token : " + string;
        if (LOG.ENABLED && str2 != null) {
            Log.d("[SCPMLIB_1.0.0]" + str, str2);
        }
        try {
            ParcelFileDescriptor openFile = configuration.openFile(string);
            Bundle bundle = new Bundle();
            bundle.putString("token", string);
            if (openFile == null) {
                Bundle call =
                        configuration.call(
                                "getLastError", configuration.context.getPackageName(), bundle);
                LOG.e(
                        str,
                        "cannot get new policy : "
                                + call.getInt("rcode")
                                + ", "
                                + call.getString("rmsg"));
                newConfigurationDataSet = DataSetFactory.newConfigurationDataSet(call, null);
            } else {
                newConfigurationDataSet =
                        DataSetFactory.newConfigurationDataSet(
                                configuration.call(
                                        "getStatus",
                                        configuration.context.getPackageName(),
                                        bundle),
                                openFile);
            }
        } catch (Exception e) {
            LOG.e(str, "cannot get new policy : " + e.getMessage());
            newConfigurationDataSet = DataSetFactory.newConfigurationDataSet(e);
        }
        if (newConfigurationDataSet.result == 2) {
            int i = newConfigurationDataSet.rcode;
            if (i == 40100001 || i == 80100000) {
                SharedPreferences sharedPreferences2 =
                        scpmUtils.mContext.getSharedPreferences("scpmInfo", 0);
                if (sharedPreferences2 != null) {
                    SharedPreferences.Editor edit = sharedPreferences2.edit();
                    edit.clear();
                    edit.apply();
                }
                scpmUtils.registerScpm();
            }
        } else {
            try {
                ParcelFileDescriptor parcelFileDescriptor =
                        newConfigurationDataSet.parcelFileDescriptor;
                try {
                    if (parcelFileDescriptor != null) {
                        ScpmUtils.copyPFDToSettingsBixby(
                                scpmUtils.mContext.getFilesDir().getAbsolutePath()
                                        + "/eternalPolicy.json",
                                parcelFileDescriptor);
                        EternalFileLog.d(
                                "ScpmUtils",
                                "getConfigurationFromScpm() : success get configuration.");
                    } else {
                        EternalFileLog.d(
                                "ScpmUtils",
                                "getConfigurationFromScpm() : fail get configuration.");
                    }
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                } finally {
                }
            } catch (Exception e2) {
                EternalFileLog.e("ScpmUtils", "getConfigurationFromScpm() : " + e2.getMessage());
            }
        }
        ScpmUtils scpmUtils2 = ScpmUtils.getInstance(this.mContext);
        scpmUtils2.getClass();
        try {
            return scpmUtils2.jsonToString();
        } catch (Exception unused) {
            EternalFileLog.e("ScpmUtils", "getScpmPolicy() : Fail to get policy file");
            return null;
        }
    }
}
