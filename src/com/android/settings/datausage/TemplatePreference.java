package com.android.settings.datausage;

import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.INetworkManagementService;
import android.os.UserManager;
import android.telephony.TelephonyManager;

import com.android.settingslib.NetworkPolicyEditor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface TemplatePreference {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NetworkServices {
        public INetworkManagementService mNetworkService;
        public NetworkPolicyEditor mPolicyEditor;
        public NetworkPolicyManager mPolicyManager;
        public TelephonyManager mTelephonyManager;
        public UserManager mUserManager;
    }

    void setTemplate(NetworkTemplate networkTemplate, int i);
}
