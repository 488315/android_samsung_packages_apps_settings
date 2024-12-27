package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.Utils;

import com.samsung.android.settings.deviceinfo.SecDeviceInfoUtils;

import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRegulatoryInfoActivityChina extends SettingsPreferenceFragment {
    public static String mDeviceLicenseCode;
    public Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CheckLicenseInfoRunnable implements Runnable {
        public CheckLicenseInfoRunnable() {}

        /* JADX WARN: Code restructure failed: missing block: B:51:0x00ef, code lost:

           if (r5 != null) goto L23;
        */
        /* JADX WARN: Removed duplicated region for block: B:59:0x0144 A[Catch: IOException -> 0x0147, TRY_LEAVE, TryCatch #5 {IOException -> 0x0147, blocks: (B:67:0x013f, B:59:0x0144), top: B:66:0x013f }] */
        /* JADX WARN: Removed duplicated region for block: B:63:0x0149  */
        /* JADX WARN: Removed duplicated region for block: B:65:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:66:0x013f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 333
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityChina.CheckLicenseInfoRunnable.run():void");
        }
    }

    public final String getMessageBody() {
        String imei = SecDeviceInfoUtils.getImei(this.mContext, 0, false);
        if (Utils.isWifiOnly(this.mContext)) {
            imei = SecDeviceInfoUtils.getSerialNumber(this.mContext);
        }
        if (TextUtils.isEmpty(imei) || "000000000000000".equals(imei)) {
            Log.w("SecRegulatoryInfoActivityChina", "imei is empty");
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("BizCode", "1000");
        jSONObject.put("R", mDeviceLicenseCode);
        jSONObject.put("M", imei);
        return jSONObject.toString();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 77030;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00ef  */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onCreateView(
            android.view.LayoutInflater r8, android.view.ViewGroup r9, android.os.Bundle r10) {
        /*
            r7 = this;
            r9 = 2131560811(0x7f0d096b, float:1.8747005E38)
            r10 = 0
            android.view.View r8 = r8.inflate(r9, r10)
            r9 = 2131362828(0x7f0a040c, float:1.8345448E38)
            android.view.View r9 = r8.findViewById(r9)
            if (r9 == 0) goto L24
            r10 = 15
            r9.semSetRoundedCorners(r10)
            android.content.res.Resources r0 = r7.getResources()
            r1 = 2131101488(0x7f060730, float:1.7815387E38)
            int r0 = r0.getColor(r1)
            r9.semSetRoundedCornerColor(r10, r0)
        L24:
            r9 = 2131365021(0x7f0a0c9d, float:1.8349896E38)
            android.view.View r9 = r8.findViewById(r9)
            android.widget.RelativeLayout r9 = (android.widget.RelativeLayout) r9
            r10 = 2131364205(0x7f0a096d, float:1.834824E38)
            android.view.View r10 = r8.findViewById(r10)
            android.widget.TextView r10 = (android.widget.TextView) r10
            r0 = 2131363057(0x7f0a04f1, float:1.8345912E38)
            android.view.View r0 = r8.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 2131362563(0x7f0a0303, float:1.834491E38)
            android.view.View r1 = r8.findViewById(r1)
            android.widget.Button r1 = (android.widget.Button) r1
            boolean r2 = com.samsung.android.settings.connection.ConnectionsUtils.isSupport5GConcept()
            java.lang.String r3 = "ro.product.first_api_level"
            r4 = 0
            int r3 = android.os.SystemProperties.getInt(r3, r4)
            if (r2 == 0) goto L5c
            if (r2 == 0) goto L62
            r2 = 34
            if (r3 < r2) goto L62
        L5c:
            r2 = 2131232905(0x7f080889, float:1.8081932E38)
            r9.setBackgroundResource(r2)
        L62:
            android.content.Context r9 = r7.mContext
            android.content.res.AssetManager r9 = r9.getAssets()
            java.lang.String r2 = "chinese_network_access_license_font/HYCuSong-CAICT_0727.ttf"
            android.graphics.Typeface r9 = android.graphics.Typeface.createFromAsset(r9, r2)
            r10.setTypeface(r9)
            r0.setTypeface(r9)
            java.lang.String r9 = "SecRegulatoryInfoActivityChina"
            java.lang.String r2 = ""
            java.io.File r3 = new java.io.File
            java.lang.String r4 = "/efs/FactoryApp/model_device_code"
            r3.<init>(r4)
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Exception -> La2
            r4.<init>(r3)     // Catch: java.lang.Exception -> La2
            int r3 = r4.available()     // Catch: java.lang.Throwable -> L98
            byte[] r3 = new byte[r3]     // Catch: java.lang.Throwable -> L98
            r4.read(r3)     // Catch: java.lang.Throwable -> L98
            java.lang.String r5 = new java.lang.String     // Catch: java.lang.Throwable -> L98
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L98
            r4.close()     // Catch: java.lang.Exception -> L96
            goto Lb9
        L96:
            r3 = move-exception
            goto La4
        L98:
            r3 = move-exception
            r4.close()     // Catch: java.lang.Throwable -> L9d
            goto La1
        L9d:
            r4 = move-exception
            r3.addSuppressed(r4)     // Catch: java.lang.Exception -> La2
        La1:
            throw r3     // Catch: java.lang.Exception -> La2
        La2:
            r3 = move-exception
            r5 = r2
        La4:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r6 = "Fail to get value from EFS : "
            r4.<init>(r6)
            java.lang.String r3 = r3.getMessage()
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Log.w(r9, r3)
        Lb9:
            java.lang.String r3 = "license code: "
            java.lang.String r3 = r3.concat(r5)
            android.util.Log.i(r9, r3)
            java.lang.String r9 = "\n"
            java.lang.String r9 = r5.replace(r9, r2)
            java.lang.String r3 = "_"
            java.lang.String r9 = r9.replace(r3, r2)
            com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityChina.mDeviceLicenseCode = r9
            java.lang.String r9 = "ro.product.model"
            java.lang.String r9 = android.os.SystemProperties.get(r9)
            r10.setText(r9)
            java.lang.String r9 = com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityChina.mDeviceLicenseCode
            r0.setText(r9)
            com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityChina$$ExternalSyntheticLambda0 r9 = new com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityChina$$ExternalSyntheticLambda0
            r9.<init>()
            r1.setOnClickListener(r9)
            java.lang.String r7 = com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityChina.mDeviceLicenseCode
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 == 0) goto Lf5
            java.lang.String r7 = "none"
            r0.setText(r7)
        Lf5:
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityChina.onCreateView(android.view.LayoutInflater,"
                    + " android.view.ViewGroup, android.os.Bundle):android.view.View");
    }
}
