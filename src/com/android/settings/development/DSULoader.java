package com.android.settings.development;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.settings.R;
import com.android.settings.development.DSULoader;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DSULoader extends ListActivity {
    public DSUPackageListAdapter mAdapter;
    public final List mDSUList = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.DSULoader$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DSULoader this$0;
        public final Object val$dsu;

        public /* synthetic */ AnonymousClass1(DSULoader dSULoader, Object obj, int i) {
            this.$r8$classId = i;
            this.this$0 = dSULoader;
            this.val$dsu = obj;
        }

        public void fetch(URL url) {
            boolean z;
            JSONObject jSONObject = new JSONObject(DSULoader.m822$$Nest$smreadAll(url));
            if (jSONObject.has("include")) {
                JSONArray jSONArray = jSONObject.getJSONArray("include");
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    if (!jSONArray.isNull(i)) {
                        fetch(new URL(jSONArray.getString(i)));
                    }
                }
            }
            if (jSONObject.has("images")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("images");
                int length2 = jSONArray2.length();
                for (int i2 = 0; i2 < length2; i2++) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i2);
                    DSUPackage dSUPackage = new DSUPackage();
                    Date date = null;
                    dSUPackage.mName = null;
                    dSUPackage.mDetails = null;
                    dSUPackage.mCpuAbi = null;
                    dSUPackage.mOsVersion = -1;
                    dSUPackage.mVndk = null;
                    dSUPackage.mPubKey = ApnSettings.MVNO_NONE;
                    dSUPackage.mSPL = null;
                    dSUPackage.mTosUrl = null;
                    Slog.i("DSULOADER", "DSUPackage: " + jSONObject2.toString());
                    dSUPackage.mName = jSONObject2.getString("name");
                    dSUPackage.mDetails = jSONObject2.getString("details");
                    dSUPackage.mCpuAbi = jSONObject2.getString("cpu_abi");
                    dSUPackage.mUri = new URL(jSONObject2.getString("uri"));
                    if (jSONObject2.has("os_version")) {
                        dSUPackage.mOsVersion = DSUPackage.dessertNumber(10, jSONObject2.getString("os_version"));
                    }
                    if (jSONObject2.has("vndk")) {
                        JSONArray jSONArray3 = jSONObject2.getJSONArray("vndk");
                        dSUPackage.mVndk = new int[jSONArray3.length()];
                        for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                            dSUPackage.mVndk[i3] = jSONArray3.getInt(i3);
                        }
                    }
                    if (jSONObject2.has("pubkey")) {
                        dSUPackage.mPubKey = jSONObject2.getString("pubkey");
                    }
                    if (jSONObject2.has("tos")) {
                        dSUPackage.mTosUrl = new URL(jSONObject2.getString("tos"));
                    }
                    if (jSONObject2.has("spl")) {
                        dSUPackage.mSPL = new SimpleDateFormat("yyyy-MM-dd").parse(jSONObject2.getString("spl"));
                    }
                    String lowerCase = SystemProperties.get("ro.product.cpu.abi").toLowerCase();
                    if (lowerCase.startsWith("aarch64")) {
                        lowerCase = "arm64-v8a";
                    }
                    String str = dSUPackage.mCpuAbi;
                    if (str.equals(lowerCase)) {
                        z = true;
                    } else {
                        Slog.i("DSULOADER", str + " != " + lowerCase);
                        z = false;
                    }
                    int i4 = dSUPackage.mOsVersion;
                    if (i4 > 0) {
                        int dessertNumber = DSUPackage.dessertNumber(10, SystemProperties.get("ro.system.build.version.release"));
                        if (dessertNumber < 0) {
                            Slog.i("DSULOADER", "Failed to getDeviceOs");
                        } else if (i4 < dessertNumber) {
                            Slog.i("DSULOADER", i4 + " < " + dessertNumber);
                        }
                        z = false;
                    }
                    int[] iArr = dSUPackage.mVndk;
                    if (iArr != null) {
                        int dessertNumber2 = DSUPackage.dessertNumber(28, SystemProperties.get("ro.vndk.version"));
                        if (dessertNumber2 < 0) {
                            Slog.i("DSULOADER", "Failed to getDeviceVndk");
                        } else {
                            for (int i5 : iArr) {
                                if (i5 == dessertNumber2) {
                                    break;
                                }
                            }
                            Slog.i("DSULOADER", "vndk:" + dessertNumber2 + " not found");
                        }
                        z = false;
                    }
                    if (dSUPackage.mSPL != null) {
                        String str2 = SystemProperties.get("ro.build.version.security_patch");
                        if (!TextUtils.isEmpty(str2)) {
                            try {
                                date = new SimpleDateFormat("yyyy-MM-dd").parse(str2);
                            } catch (ParseException unused) {
                            }
                        }
                        if (date == null) {
                            Slog.i("DSULOADER", "Failed to getDeviceSPL");
                        } else if (date.getTime() > dSUPackage.mSPL.getTime()) {
                            Slog.i("DSULOADER", "Device SPL:" + date.toString() + " > " + dSUPackage.mSPL.toString());
                        }
                        z = false;
                    }
                    Slog.i("DSULOADER", dSUPackage.mName + " isSupported " + z);
                    if (z) {
                        ((ArrayList) this.this$0.mDSUList).add(dSUPackage);
                    }
                }
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            String m822$$Nest$smreadAll;
            switch (this.$r8$classId) {
                case 0:
                    URL url = ((DSUPackage) this.val$dsu).mTosUrl;
                    if (url != null) {
                        try {
                            m822$$Nest$smreadAll = DSULoader.m822$$Nest$smreadAll(url);
                        } catch (IOException e) {
                            Slog.e("DSULOADER", e.toString());
                        }
                        Intent intent = new Intent(this.this$0, (Class<?>) DSUTermsOfServiceActivity.class);
                        intent.putExtra("KEY_TOS", m822$$Nest$smreadAll);
                        intent.setData(Uri.parse(((DSUPackage) this.val$dsu).mUri.toString()));
                        intent.putExtra("KEY_PUBKEY", ((DSUPackage) this.val$dsu).mPubKey);
                        this.this$0.startActivity(intent);
                        break;
                    }
                    m822$$Nest$smreadAll = ApnSettings.MVNO_NONE;
                    Intent intent2 = new Intent(this.this$0, (Class<?>) DSUTermsOfServiceActivity.class);
                    intent2.putExtra("KEY_TOS", m822$$Nest$smreadAll);
                    intent2.setData(Uri.parse(((DSUPackage) this.val$dsu).mUri.toString()));
                    intent2.putExtra("KEY_PUBKEY", ((DSUPackage) this.val$dsu).mPubKey);
                    this.this$0.startActivity(intent2);
                default:
                    try {
                        fetch((URL) this.val$dsu);
                        if (((ArrayList) this.this$0.mDSUList).size() == 0) {
                            ((ArrayList) this.this$0.mDSUList).add(0, "No DSU available for this device");
                        }
                    } catch (IOException e2) {
                        Slog.e("DSULOADER", e2.toString());
                        ((ArrayList) this.this$0.mDSUList).add(0, "Network Error");
                    } catch (Exception e3) {
                        Slog.e("DSULOADER", e3.toString());
                        ((ArrayList) this.this$0.mDSUList).add(0, "Metadata Error");
                    }
                    this.this$0.runOnUiThread(new Runnable() { // from class: com.android.settings.development.DSULoader$Fetcher$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DSULoader.AnonymousClass1.this.this$0.mAdapter.clear();
                            DSULoader dSULoader = DSULoader.AnonymousClass1.this.this$0;
                            dSULoader.mAdapter.addAll(dSULoader.mDSUList);
                            DSULoader dSULoader2 = DSULoader.AnonymousClass1.this.this$0;
                            ListView listView = dSULoader2.getListView();
                            View view = dSULoader2.mAdapter.getView(0, null, listView);
                            view.measure(View.MeasureSpec.makeMeasureSpec(listView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                            layoutParams.height = (dSULoader2.mAdapter.getCount() + 1) * view.getMeasuredHeight();
                            Slog.e("DSULOADER", "resizeListView height=" + layoutParams.height);
                            listView.setLayoutParams(layoutParams);
                            listView.requestLayout();
                        }
                    });
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DSUPackage {
        public String mCpuAbi;
        public String mDetails;
        public String mName;
        public int mOsVersion;
        public String mPubKey;
        public Date mSPL;
        public URL mTosUrl;
        public URL mUri;
        public int[] mVndk;

        public static int dessertNumber(int i, String str) {
            if (str == null || str.isEmpty()) {
                return -1;
            }
            return Character.isDigit(str.charAt(0)) ? Integer.parseInt(str) : (str.toUpperCase().charAt(0) - 'Q') + i;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DSUPackageListAdapter extends ArrayAdapter {
        public final LayoutInflater mInflater;

        public DSUPackageListAdapter(Context context) {
            super(context, 0);
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            AppViewHolder createOrRecycle = AppViewHolder.createOrRecycle(this.mInflater, view);
            View view2 = createOrRecycle.rootView;
            Object item = getItem(i);
            if (item instanceof DSUPackage) {
                DSUPackage dSUPackage = (DSUPackage) item;
                createOrRecycle.appName.setText(dSUPackage.mName);
                createOrRecycle.summary.setText(dSUPackage.mDetails);
            } else {
                createOrRecycle.summary.setText((String) item);
            }
            createOrRecycle.appIcon.setImageDrawable(null);
            createOrRecycle.disabled.setVisibility(8);
            return view2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.net.URL] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v5, types: [javax.net.ssl.HttpsURLConnection] */
    /* JADX WARN: Type inference failed for: r6v7, types: [javax.net.ssl.HttpsURLConnection] */
    /* renamed from: -$$Nest$smreadAll, reason: not valid java name */
    public static String m822$$Nest$smreadAll(URL url) {
        Throwable th;
        ?? r0 = "HTTP error code: ";
        Slog.i("DSULOADER", "fetch " + url.toString());
        try {
            try {
                url = (HttpsURLConnection) url.openConnection();
            } catch (Exception e) {
                throw e;
            } catch (Throwable th2) {
                r0 = 0;
                th = th2;
                url = 0;
            }
            try {
                url.setReadTimeout(EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
                url.setConnectTimeout(EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
                url.setRequestMethod("GET");
                url.setDoInput(true);
                url.connect();
                int responseCode = url.getResponseCode();
                if (url.getResponseCode() != 200) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(url.getInputStream());
                try {
                    String readAll = readAll(bufferedInputStream);
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused) {
                    }
                    url.disconnect();
                    return readAll;
                } catch (Exception e2) {
                    throw e2;
                }
            } catch (Exception e3) {
                throw e3;
            } catch (Throwable th3) {
                th = th3;
                r0 = 0;
                if (r0 != 0) {
                    try {
                        r0.close();
                    } catch (IOException unused2) {
                    }
                }
                if (url == 0) {
                    throw th;
                }
                url.disconnect();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static String readAll(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr, 0, 4096);
            if (read == -1) {
                return sb.toString();
            }
            sb.append(new String(Arrays.copyOf(bArr, read)));
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String str = SystemProperties.get("persist.sys.fflag.override.settings_dynamic_system.list");
        Slog.e("DSULOADER", "Try to get DSU list from: persist.sys.fflag.override.settings_dynamic_system.list");
        if (str == null || str.isEmpty()) {
            str = "https://dl.google.com/developers/android/gsi/gsi-src.json";
        }
        Slog.e("DSULOADER", "DSU list: ".concat(str));
        try {
            URL url = new URL(str);
            DSUPackageListAdapter dSUPackageListAdapter = new DSUPackageListAdapter(this);
            this.mAdapter = dSUPackageListAdapter;
            setListAdapter(dSUPackageListAdapter);
            this.mAdapter.add(getResources().getString(R.string.dsu_loader_loading));
            new Thread(new AnonymousClass1(this, url, 1)).start();
        } catch (MalformedURLException e) {
            Slog.e("DSULOADER", e.toString());
        }
    }

    @Override // android.app.ListActivity
    public final void onListItemClick(ListView listView, View view, int i, long j) {
        Object item = this.mAdapter.getItem(i);
        if (item instanceof DSUPackage) {
            this.mAdapter.clear();
            this.mAdapter.add(getResources().getString(R.string.dsu_loader_loading));
            new Thread(new AnonymousClass1(this, (DSUPackage) item, 0)).start();
        }
        finish();
    }
}
