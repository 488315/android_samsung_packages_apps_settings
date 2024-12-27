package com.android.settings.bluetooth;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothFindBroadcastsFragment$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothFindBroadcastsFragment.AnonymousClass1 f$0;

    public /* synthetic */ BluetoothFindBroadcastsFragment$1$$ExternalSyntheticLambda0(
            BluetoothFindBroadcastsFragment.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BluetoothFindBroadcastsFragment.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment =
                        anonymousClass1.this$0;
                bluetoothFindBroadcastsFragment.cacheRemoveAllPrefs(
                        bluetoothFindBroadcastsFragment.mBroadcastSourceListCategory);
                bluetoothFindBroadcastsFragment.addConnectedSourcePreference();
                break;
            case 1:
                BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment2 =
                        anonymousClass1.this$0;
                BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference =
                        bluetoothFindBroadcastsFragment2.mSelectedPreference;
                if (bluetoothBroadcastSourcePreference != null) {
                    if (bluetoothBroadcastSourcePreference.mBluetoothLeBroadcastMetadata == null) {
                        bluetoothFindBroadcastsFragment2.mBroadcastSourceListCategory
                                .removePreference(bluetoothBroadcastSourcePreference);
                    } else {
                        bluetoothBroadcastSourcePreference.mBluetoothLeBroadcastReceiveState = null;
                        String programInfo = bluetoothBroadcastSourcePreference.getProgramInfo();
                        bluetoothBroadcastSourcePreference.mTitle = programInfo;
                        bluetoothBroadcastSourcePreference.mStatus = false;
                        bluetoothBroadcastSourcePreference.setTitle(programInfo);
                        bluetoothBroadcastSourcePreference.updateStatusButton();
                    }
                }
                bluetoothFindBroadcastsFragment2.mSelectedPreference = null;
                break;
            default:
                BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment3 =
                        anonymousClass1.this$0;
                bluetoothFindBroadcastsFragment3.updateListCategoryFromBroadcastMetadata(
                        bluetoothFindBroadcastsFragment3
                                .mSelectedPreference
                                .mBluetoothLeBroadcastMetadata,
                        true);
                break;
        }
    }
}
