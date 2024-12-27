package com.samsung.android.settings.bluetooth.lebroadcast;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1 f$0;

    public /* synthetic */ SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
            SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings =
                        anonymousClass1.this$0;
                int i2 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                secBluetoothLeBroadcastAssistantSettings.clearOperationTimeout$1();
                break;
            case 1:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings2 =
                        anonymousClass1.this$0;
                int i3 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                Toast.makeText(
                                secBluetoothLeBroadcastAssistantSettings2,
                                secBluetoothLeBroadcastAssistantSettings2
                                        .getResources()
                                        .getString(
                                                R.string.sec_bluetooth_broadcast_add_source_failed),
                                0)
                        .show();
                break;
            case 2:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings3 =
                        anonymousClass1.this$0;
                int i4 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                secBluetoothLeBroadcastAssistantSettings3.clearOperationTimeout$1();
                Toast.makeText(
                                secBluetoothLeBroadcastAssistantSettings3,
                                secBluetoothLeBroadcastAssistantSettings3
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .sec_bluetooth_broadcast_add_source_failed_unsupported_audio_quality),
                                0)
                        .show();
                break;
            case 3:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings4 =
                        anonymousClass1.this$0;
                int i5 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                secBluetoothLeBroadcastAssistantSettings4.clearOperationTimeout$1();
                break;
            case 4:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings5 =
                        anonymousClass1.this$0;
                int i6 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                secBluetoothLeBroadcastAssistantSettings5.getClass();
                Log.d("SecBluetoothLeBroadcastAssistantSettings", "handleSearchStarted");
                secBluetoothLeBroadcastAssistantSettings5.clearListItem();
                MenuItem menuItem = secBluetoothLeBroadcastAssistantSettings5.mProgressBarItem;
                if (menuItem != null) {
                    menuItem.setEnabled(true);
                    secBluetoothLeBroadcastAssistantSettings5.mProgressBarItem.setActionView(
                            R.layout.sec_bluetooth_broadcast_progressbar);
                    secBluetoothLeBroadcastAssistantSettings5.mProgressBarItem.setVisible(true);
                    break;
                }
                break;
            case 5:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings6 =
                        anonymousClass1.this$0;
                MenuItem menuItem2 = secBluetoothLeBroadcastAssistantSettings6.mProgressBarItem;
                if (menuItem2 != null) {
                    menuItem2.setEnabled(false);
                    secBluetoothLeBroadcastAssistantSettings6.mProgressBarItem.setActionView(
                            (View) null);
                    secBluetoothLeBroadcastAssistantSettings6.mProgressBarItem.setVisible(false);
                    break;
                }
                break;
            case 6:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings7 =
                        anonymousClass1.this$0;
                int i7 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                secBluetoothLeBroadcastAssistantSettings7.clearOperationTimeout$1();
                Toast.makeText(
                                secBluetoothLeBroadcastAssistantSettings7,
                                secBluetoothLeBroadcastAssistantSettings7
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .sec_bluetooth_broadcast_add_source_failed_unsupported_audio_quality),
                                0)
                        .show();
                break;
            case 7:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings8 =
                        anonymousClass1.this$0;
                secBluetoothLeBroadcastAssistantSettings8.launchBroadcastCodeDialog(
                        secBluetoothLeBroadcastAssistantSettings8.mSelectedSourceItem, true);
                break;
            default:
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings9 =
                        anonymousClass1.this$0;
                int i8 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                secBluetoothLeBroadcastAssistantSettings9.clearOperationTimeout$1();
                break;
        }
    }
}
