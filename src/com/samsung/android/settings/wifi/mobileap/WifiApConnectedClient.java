package com.samsung.android.settings.wifi.mobileap;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConnectedClient extends WifiApPreference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View customView;
    public String dateFormat;
    public Long mConnectedTime;
    public Context mContext;
    public String mDeviceName;
    public String mIp;
    public String mMac;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApConnectedClient$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            SALogging.insertSALog("TETH_010", "8027");
        }
    }
}
