package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.File;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WarrantyInfo extends AlertActivity {
    public String FILE_PATH_GENERAL_TERMS = ApnSettings.MVNO_NONE;
    public String FILE_PATH_WARRANTY_EXCEPTION = ApnSettings.MVNO_NONE;
    public String FILE_PATH_PRODUCT_WARRANTY = ApnSettings.MVNO_NONE;

    public static String getWarrantyInfoFilePath(String str) {
        String str2 = SystemProperties.get("persist.sys.omc_etcpath", ApnSettings.MVNO_NONE);
        String str3 = null;
        try {
            if (new File("/system/serviceinfo/".concat(str)).exists()) {
                str3 = "/system/serviceinfo/".concat(str);
            } else if (new File("/system/etc/".concat(str)).exists()) {
                str3 = "/system/etc/".concat(str);
            } else {
                if (new File(str2 + "/" + str).exists()) {
                    str3 = str2 + "/" + str;
                }
            }
            Log.d("WarrantyInfo", "take WarrantyInfo from : " + str3);
        } catch (Exception unused) {
            Log.e("WarrantyInfo", "fail to find a file");
        }
        return str3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00db A[Catch: IOException -> 0x009f, TRY_ENTER, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e0 A[Catch: IOException -> 0x009f, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e5 A[Catch: IOException -> 0x009f, TRY_LEAVE, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c8 A[Catch: IOException -> 0x009f, TRY_ENTER, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cd A[Catch: IOException -> 0x009f, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d2 A[Catch: IOException -> 0x009f, TRY_LEAVE, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b5 A[Catch: IOException -> 0x009f, TRY_ENTER, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ba A[Catch: IOException -> 0x009f, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00bf A[Catch: IOException -> 0x009f, TRY_LEAVE, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x009b A[Catch: IOException -> 0x009f, TRY_ENTER, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a3 A[Catch: IOException -> 0x009f, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a8 A[Catch: IOException -> 0x009f, TRY_LEAVE, TryCatch #18 {IOException -> 0x009f, blocks: (B:57:0x009b, B:59:0x00a3, B:61:0x00a8, B:48:0x00b5, B:50:0x00ba, B:52:0x00bf, B:39:0x00c8, B:41:0x00cd, B:43:0x00d2, B:29:0x00db, B:31:0x00e0, B:33:0x00e5), top: B:2:0x0001 }] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v32 */
    /* JADX WARN: Type inference failed for: r6v33 */
    /* JADX WARN: Type inference failed for: r6v34 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readCSCFile(java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.regulatoryinfo.WarrantyInfo.readCSCFile(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int intExtra = getIntent().getIntExtra("Option", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(intExtra, "option ", "WarrantyInfo");
        if (intExtra == 0) {
            finish();
        }
        View inflate =
                LayoutInflater.from(this)
                        .inflate(R.layout.sec_warranty_info_layout, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.warranty_legal);
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        this.FILE_PATH_GENERAL_TERMS = getWarrantyInfoFilePath("general_terms.txt");
        this.FILE_PATH_WARRANTY_EXCEPTION = getWarrantyInfoFilePath("warranty_exceptions.txt");
        this.FILE_PATH_PRODUCT_WARRANTY = getWarrantyInfoFilePath("product_warranty.txt");
        String warrantyInfoFilePath = getWarrantyInfoFilePath("contact_us.txt");
        if (intExtra == 1) {
            String readCSCFile = readCSCFile(this.FILE_PATH_GENERAL_TERMS);
            alertParams.mTitle =
                    getResources().getString(R.string.general_term_and_conditions_title);
            textView.setText(readCSCFile);
        } else if (intExtra == 2) {
            String readCSCFile2 = readCSCFile(this.FILE_PATH_WARRANTY_EXCEPTION);
            alertParams.mTitle = getResources().getString(R.string.warranty_exception_title);
            textView.setText(readCSCFile2);
        } else if (intExtra == 3) {
            String readCSCFile3 = readCSCFile(this.FILE_PATH_PRODUCT_WARRANTY);
            alertParams.mTitle = getResources().getString(R.string.product_warranty_title);
            textView.setText(readCSCFile3);
        } else if (intExtra == 4) {
            String readCSCFile4 = readCSCFile(warrantyInfoFilePath);
            alertParams.mTitle = getResources().getString(R.string.contact_us_title);
            textView.setText(readCSCFile4);
        }
        alertParams.mView = inflate;
        alertParams.mPositiveButtonText = getString(android.R.string.ok);
        setupAlert();
    }

    public final void onDestroy() {
        super.onDestroy();
    }
}
