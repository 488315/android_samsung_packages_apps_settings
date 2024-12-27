package com.android.settings.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TetherProvisioningActivity extends Activity {
    public static final boolean DEBUG = Log.isLoggable("TetherProvisioningAct", 3);
    static final String EXTRA_TETHER_SUBID = "android.net.extra.TETHER_SUBID";
    public static final String EXTRA_TETHER_UI_PROVISIONING_APP_NAME =
            "android.net.extra.TETHER_UI_PROVISIONING_APP_NAME";
    static final int PROVISION_REQUEST = 0;
    public ResultReceiver mResultReceiver;

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 0) {
            if (DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2, "Got result from app: ", "TetherProvisioningAct");
            }
            this.mResultReceiver.send(i2 == -1 ? 0 : 11, null);
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ResultReceiver resultReceiver =
                (ResultReceiver) getIntent().getParcelableExtra("extraProvisionCallback");
        this.mResultReceiver = resultReceiver;
        if (resultReceiver == null) {
            Log.i("TetherProvisioningAct", "unexpected access");
            finish();
            return;
        }
        int intExtra = getIntent().getIntExtra("extraAddTetherType", -1);
        int intExtra2 = getIntent().getIntExtra("android.net.extra.TETHER_SUBID", -1);
        int activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId();
        if (intExtra2 != activeDataSubscriptionId) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(
                            activeDataSubscriptionId,
                            "This Provisioning request is outdated, current subId: ",
                            "TetherProvisioningAct");
            this.mResultReceiver.send(11, null);
            finish();
            return;
        }
        String[] stringArrayExtra =
                getIntent().getStringArrayExtra(EXTRA_TETHER_UI_PROVISIONING_APP_NAME);
        if (stringArrayExtra == null || stringArrayExtra.length != 2) {
            Log.e("TetherProvisioningAct", "Unexpected provision app configuration");
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(stringArrayExtra[0], stringArrayExtra[1]);
        intent.putExtra("TETHER_TYPE", intExtra);
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", activeDataSubscriptionId);
        intent.putExtra("extraProvisionCallback", this.mResultReceiver);
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("Starting provisioning app: ");
            sb.append(stringArrayExtra[0]);
            sb.append(".");
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    sb, stringArrayExtra[1], "TetherProvisioningAct");
        }
        if (!getPackageManager().queryIntentActivities(intent, 65536).isEmpty()) {
            startActivityForResultAsUser(intent, 0, UserHandle.CURRENT);
            return;
        }
        Log.e("TetherProvisioningAct", "Provisioning app is configured, but not available.");
        this.mResultReceiver.send(11, null);
        finish();
    }
}
