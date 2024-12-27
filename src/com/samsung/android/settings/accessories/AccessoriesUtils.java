package com.samsung.android.settings.accessories;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessoriesUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessories.AccessoriesUtils$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    public static boolean hasCoverSettingCoverOrientation(Context context) {
        if (UsefulfeatureUtils.getTypeOfCover(context) != 15) {
            Log.secD("Utils", "CoverOrientation - false");
            return false;
        }
        Log.secD("Utils", "CoverOrientation - true");
        return true;
    }

    public static boolean hasCoverSettingLEDCover(Context context) {
        if (UsefulfeatureUtils.getTypeOfCover(context) != 7) {
            Log.secD("Utils", "LEDCover - false");
            return false;
        }
        Log.secD("Utils", "LEDCover - true");
        return true;
    }

    public static void showDownloadLedAppDialog(
            final Context context, String str, final String str2) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.download_scloud_title, str))
                .setMessage(context.getString(R.string.download_scloud_message, str))
                .setCancelable(true)
                .setPositiveButton(
                        R.string.monotype_dialog_button,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.accessories.AccessoriesUtils.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Context context2 = context;
                                String str3 = str2;
                                Intent intent = new Intent();
                                intent.setData(Uri.parse("samsungapps://ProductDetail/" + str3));
                                intent.putExtra("form", "popup");
                                intent.putExtra("directClose", true);
                                intent.addFlags(335544352);
                                if (context2.getPackageManager()
                                                .queryIntentActivities(intent, 0)
                                                .size()
                                        <= 0) {
                                    Log.e("AccessoriesUtils", "Activity Not Found !");
                                } else {
                                    context2.startActivity(intent);
                                    Log.d("AccessoriesUtils", "Show QIP download popup");
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, new AnonymousClass1())
                .show();
    }
}
