package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothLeBroadcastMetadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ BluetoothLeBroadcastMetadata f$1;

    public /* synthetic */ SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda3(
            Object obj, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = bluetoothLeBroadcastMetadata;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1 anonymousClass1 =
                        (SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1) this.f$0;
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.f$1;
                SecBluetoothLeBroadcastAssistantSettings
                        .m1129$$Nest$mupdateListItemFromBroadcastMetadata(
                                SecBluetoothLeBroadcastAssistantSettings.this,
                                bluetoothLeBroadcastMetadata,
                                bluetoothLeBroadcastMetadata.getRssi());
                break;
            default:
                SecBluetoothLeBroadcastAssistantSettings.AnonymousClass2 anonymousClass2 =
                        (SecBluetoothLeBroadcastAssistantSettings.AnonymousClass2) this.f$0;
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 = this.f$1;
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings =
                        SecBluetoothLeBroadcastAssistantSettings.this;
                int i = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                secBluetoothLeBroadcastAssistantSettings.getClass();
                for (SecBluetoothLeBroadcastAssistantSourceItem
                        secBluetoothLeBroadcastAssistantSourceItem :
                                secBluetoothLeBroadcastAssistantSettings
                                        .getBluetoothBroadcastSourceItem(
                                                bluetoothLeBroadcastMetadata2.getBroadcastId())) {
                    if (secBluetoothLeBroadcastAssistantSourceItem.mRecvState == null
                            || secBluetoothLeBroadcastAssistantSourceItem.mMetadata != null) {
                        secBluetoothLeBroadcastAssistantSourceItem.updateMetadata(
                                bluetoothLeBroadcastMetadata2,
                                secBluetoothLeBroadcastAssistantSourceItem.isConnected());
                        if (secBluetoothLeBroadcastAssistantSourceItem.isConnected()) {
                            secBluetoothLeBroadcastAssistantSettings.updateConnectedCardUi(
                                    secBluetoothLeBroadcastAssistantSourceItem);
                        } else {
                            secBluetoothLeBroadcastAssistantSettings.mSourceListAdapter
                                    .notifyItemChanged(
                                            secBluetoothLeBroadcastAssistantSettings.mSourceItems
                                                    .indexOf(
                                                            secBluetoothLeBroadcastAssistantSourceItem));
                        }
                    } else {
                        secBluetoothLeBroadcastAssistantSourceItem.mBroadcastName =
                                bluetoothLeBroadcastMetadata2.getBroadcastName();
                        secBluetoothLeBroadcastAssistantSourceItem.updateTitleAndSummary();
                        if (secBluetoothLeBroadcastAssistantSourceItem.isConnected()) {
                            secBluetoothLeBroadcastAssistantSettings.updateConnectedCardUi(
                                    secBluetoothLeBroadcastAssistantSourceItem);
                        }
                    }
                }
                break;
        }
    }
}
