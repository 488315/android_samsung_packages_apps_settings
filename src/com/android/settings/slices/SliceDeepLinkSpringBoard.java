package com.android.settings.slices;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;

import com.android.settings.bluetooth.BluetoothSliceBuilder;
import com.android.settings.notification.zen.ZenModeSliceBuilder;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SliceDeepLinkSpringBoard extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Uri data = getIntent().getData();
        Uri uri = null;
        if (data != null) {
            String queryParameter = data.getQueryParameter("slice");
            if (TextUtils.isEmpty(queryParameter)) {
                EventLog.writeEvent(1397638484, "122836081", -1, ApnSettings.MVNO_NONE);
            } else {
                uri = Uri.parse(queryParameter);
            }
        }
        if (uri == null) {
            Log.e("DeeplinkSpringboard", "No data found");
            finish();
            return;
        }
        try {
            Map<Uri, Class<? extends CustomSliceable>> map = CustomSliceRegistry.sUriToSlice;
            startActivity(
                    map.containsKey(uri.buildUpon().clearQuery().build())
                            ? CustomSliceable.createInstance(
                                            getApplicationContext(),
                                            map.get(uri.buildUpon().clearQuery().build()))
                                    .getIntent()
                            : CustomSliceRegistry.ZEN_MODE_SLICE_URI.equals(uri)
                                    ? ZenModeSliceBuilder.getIntent(this)
                                    : CustomSliceRegistry.BLUETOOTH_URI.equals(uri)
                                            ? BluetoothSliceBuilder.getIntent(this)
                                            : SliceBuilderUtils.getContentIntent(
                                                    this,
                                                    new SlicesDatabaseAccessor(this)
                                                            .getSliceDataFromUri(uri)));
            finish();
        } catch (Exception e) {
            Log.w("DeeplinkSpringboard", "Couldn't launch Slice intent", e);
            startActivity(new Intent("android.settings.SETTINGS").setPackage(getPackageName()));
            finish();
        }
    }
}
