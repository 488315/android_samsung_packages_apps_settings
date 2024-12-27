package com.samsung.android.settings.bluetooth;

import android.content.Context;
import android.os.Debug;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.entity.BluetoothBlockDevice;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothBlockListAdapter extends BaseAdapter {
    public static final boolean DBG = Debug.semIsProductDev();
    public List mBlockNumbersList;
    public Context mContext;
    public LayoutInflater mInflater;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder {
        public ImageView deleteBtn;
        public TextView title;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return ((ArrayList) this.mBlockNumbersList).size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        return ((ArrayList) this.mBlockNumbersList).get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x007f A[Catch: IndexOutOfBoundsException -> 0x0023, TryCatch #0 {IndexOutOfBoundsException -> 0x0023, blocks: (B:3:0x0005, B:7:0x0015, B:10:0x001c, B:11:0x0049, B:13:0x0058, B:15:0x0060, B:16:0x0079, B:18:0x007f, B:19:0x008c, B:21:0x0087, B:22:0x006d, B:23:0x0026), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0087 A[Catch: IndexOutOfBoundsException -> 0x0023, TryCatch #0 {IndexOutOfBoundsException -> 0x0023, blocks: (B:3:0x0005, B:7:0x0015, B:10:0x001c, B:11:0x0049, B:13:0x0058, B:15:0x0060, B:16:0x0079, B:18:0x007f, B:19:0x008c, B:21:0x0087, B:22:0x006d, B:23:0x0026), top: B:2:0x0005 }] */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View getView(
            final int r6, android.view.View r7, android.view.ViewGroup r8) {
        /*
            r5 = this;
            com.samsung.android.settings.bluetooth.BluetoothBlockListAdapter$ViewHolder r8 = new com.samsung.android.settings.bluetooth.BluetoothBlockListAdapter$ViewHolder
            r8.<init>()
            java.util.List r0 = r5.mBlockNumbersList     // Catch: java.lang.IndexOutOfBoundsException -> L23
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: java.lang.IndexOutOfBoundsException -> L23
            java.lang.Object r0 = r0.get(r6)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            com.samsung.android.settings.bluetooth.entity.BluetoothBlockDevice r0 = (com.samsung.android.settings.bluetooth.entity.BluetoothBlockDevice) r0     // Catch: java.lang.IndexOutOfBoundsException -> L23
            if (r0 == 0) goto Lc9
            java.lang.String r1 = r0.name
            if (r7 == 0) goto L26
            java.lang.Object r2 = r7.getTag()     // Catch: java.lang.IndexOutOfBoundsException -> L23
            if (r2 != 0) goto L1c
            goto L26
        L1c:
            java.lang.Object r8 = r7.getTag()     // Catch: java.lang.IndexOutOfBoundsException -> L23
            com.samsung.android.settings.bluetooth.BluetoothBlockListAdapter$ViewHolder r8 = (com.samsung.android.settings.bluetooth.BluetoothBlockListAdapter.ViewHolder) r8     // Catch: java.lang.IndexOutOfBoundsException -> L23
            goto L49
        L23:
            r5 = move-exception
            goto Lb2
        L26:
            android.view.LayoutInflater r2 = r5.mInflater     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r3 = 0
            r4 = 2131560491(0x7f0d082b, float:1.8746356E38)
            android.view.View r7 = r2.inflate(r4, r3)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r2 = 2131365850(0x7f0a0fda, float:1.8351577E38)
            android.view.View r2 = r7.findViewById(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            android.widget.TextView r2 = (android.widget.TextView) r2     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r8.title = r2     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r2 = 2131362425(0x7f0a0279, float:1.834463E38)
            android.view.View r2 = r7.findViewById(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            android.widget.ImageView r2 = (android.widget.ImageView) r2     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r8.deleteBtn = r2     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r7.setTag(r8)     // Catch: java.lang.IndexOutOfBoundsException -> L23
        L49:
            boolean r2 = com.android.settingslib.bluetooth.BluetoothUtils.DEBUG     // Catch: java.lang.IndexOutOfBoundsException -> L23
            java.lang.String r2 = "ro.product.model"
            java.lang.String r2 = android.os.SystemProperties.get(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            if (r3 != 0) goto L6d
            java.lang.String r3 = "SM-T97"
            boolean r2 = r2.startsWith(r3)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            if (r2 == 0) goto L6d
            android.content.Context r2 = r5.mContext     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r3 = 2131232891(0x7f08087b, float:1.8081904E38)
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r3)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r7.setBackground(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            goto L79
        L6d:
            android.content.Context r2 = r5.mContext     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r3 = 2131232890(0x7f08087a, float:1.8081902E38)
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r3)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r7.setBackground(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
        L79:
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            if (r2 == 0) goto L87
            android.widget.TextView r1 = r8.title     // Catch: java.lang.IndexOutOfBoundsException -> L23
            java.lang.String r2 = r0.address     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r1.setText(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            goto L8c
        L87:
            android.widget.TextView r2 = r8.title     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r2.setText(r1)     // Catch: java.lang.IndexOutOfBoundsException -> L23
        L8c:
            android.widget.ImageView r1 = r8.deleteBtn     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r2 = 1
            r1.setClickable(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            android.widget.ImageView r1 = r8.deleteBtn     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r1.setFocusable(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            android.widget.ImageView r1 = r8.deleteBtn     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r3 = 0
            r1.setVisibility(r3)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            android.widget.ImageView r1 = r8.deleteBtn     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r1.setEnabled(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            android.widget.ImageView r1 = r8.deleteBtn     // Catch: java.lang.IndexOutOfBoundsException -> L23
            com.samsung.android.settings.bluetooth.BluetoothBlockListAdapter$$ExternalSyntheticLambda0 r2 = new com.samsung.android.settings.bluetooth.BluetoothBlockListAdapter$$ExternalSyntheticLambda0     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r2.<init>(r0, r6)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r1.setOnClickListener(r2)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            android.widget.ImageView r5 = r8.deleteBtn     // Catch: java.lang.IndexOutOfBoundsException -> L23
            r5.setHapticFeedbackEnabled(r3)     // Catch: java.lang.IndexOutOfBoundsException -> L23
            goto Lc9
        Lb2:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r8 = "getView : %s"
            r6.<init>(r8)
            java.lang.String r5 = r5.toString()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            java.lang.String r6 = "BluetoothBlockListAdapter"
            android.util.Log.d(r6, r5)
        Lc9:
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.BluetoothBlockListAdapter.getView(int,"
                    + " android.view.View, android.view.ViewGroup):android.view.View");
    }

    public final void updateBlockDeviceList() {
        this.mBlockNumbersList.clear();
        String string =
                this.mContext
                        .getSharedPreferences("bluetooth_blocking_device", 0)
                        .getString("blocking_device_list", ApnSettings.MVNO_NONE);
        if (!TextUtils.isEmpty(string)) {
            for (String str : string.split(";")) {
                String[] split = str.split(",");
                if (split.length == 5) {
                    String str2 = split[0];
                    String str3 = split[1];
                    if (Integer.parseInt(split[4]) == 2) {
                        if (DBG) {
                            DialogFragment$$ExternalSyntheticOutline0.m(
                                    "addDevicePreferences for blocked device :: name=",
                                    str3,
                                    "BluetoothBlockListAdapter");
                        }
                        List list = this.mBlockNumbersList;
                        BluetoothBlockDevice bluetoothBlockDevice = new BluetoothBlockDevice();
                        bluetoothBlockDevice.address = str2;
                        bluetoothBlockDevice.name = str3;
                        list.add(bluetoothBlockDevice);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
