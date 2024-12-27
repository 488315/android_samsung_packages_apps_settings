package com.samsung.android.settings.nfc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.nfc.cardemulation.ApduServiceInfo;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.util.Pair;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WalletRoleUiBehavior {
    public Context mContext;

    public final Pair prepareApplicationPreferenceAsUser(
            ApplicationInfo applicationInfo, UserHandle userHandle, Context context) {
        Pair pair = null;
        if (!context.getUser().equals(userHandle)) {
            try {
                context =
                        context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("WalletRoleUiBehavior", "Cannot create context for user " + userHandle, e);
                context = null;
            }
        }
        if ((applicationInfo.flags & 1) != 0) {
            String str = applicationInfo.packageName;
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent("android.nfc.cardemulation.action.HOST_APDU_SERVICE");
            Intent intent2 = new Intent("android.nfc.cardemulation.action.OFF_HOST_APDU_SERVICE");
            intent.setPackage(str);
            intent2.setPackage(str);
            List queryIntentServicesAsUser =
                    packageManager.queryIntentServicesAsUser(
                            intent, PackageManager.ResolveInfoFlags.of(640L), userHandle);
            List queryIntentServicesAsUser2 =
                    packageManager.queryIntentServicesAsUser(
                            intent2, PackageManager.ResolveInfoFlags.of(640L), userHandle);
            ArrayList arrayList = new ArrayList();
            int size = queryIntentServicesAsUser.size();
            for (int i = 0; i < size; i++) {
                try {
                    arrayList.add(
                            new ApduServiceInfo(
                                    packageManager,
                                    (ResolveInfo) queryIntentServicesAsUser.get(i),
                                    true));
                } catch (IOException | XmlPullParserException e2) {
                    Log.e("WalletRoleUiBehavior", "Error creating the apduserviceinfo.", e2);
                }
            }
            int size2 = queryIntentServicesAsUser2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                try {
                    arrayList.add(
                            new ApduServiceInfo(
                                    packageManager,
                                    (ResolveInfo) queryIntentServicesAsUser2.get(i2),
                                    false));
                } catch (IOException | XmlPullParserException e3) {
                    Log.e("WalletRoleUiBehavior", "Error creating the apduserviceinfo.", e3);
                }
            }
            PackageManager packageManager2 = this.mContext.getPackageManager();
            int size3 = arrayList.size();
            int i3 = 0;
            loop2:
            while (true) {
                if (i3 >= size3) {
                    break;
                }
                ApduServiceInfo apduServiceInfo = (ApduServiceInfo) arrayList.get(i3);
                if (apduServiceInfo.getAids().isEmpty()) {
                    Drawable loadBanner = apduServiceInfo.loadBanner(packageManager2);
                    CharSequence loadLabel = apduServiceInfo.loadLabel(packageManager2);
                    Pair pair2 =
                            (loadBanner == null || TextUtils.isEmpty(loadLabel))
                                    ? null
                                    : new Pair(loadBanner, loadLabel);
                    if (pair2 != null) {
                        pair = pair2;
                        break;
                    }
                    i3++;
                } else {
                    List aids = apduServiceInfo.getAids();
                    int size4 = aids.size();
                    for (int i4 = 0; i4 < size4; i4++) {
                        if (!"payment"
                                .equals(apduServiceInfo.getCategoryForAid((String) aids.get(i4)))) {
                            Drawable loadBanner2 = apduServiceInfo.loadBanner(packageManager2);
                            CharSequence loadLabel2 = apduServiceInfo.loadLabel(packageManager2);
                            Pair pair3 =
                                    (loadBanner2 == null || TextUtils.isEmpty(loadLabel2))
                                            ? null
                                            : new Pair(loadBanner2, loadLabel2);
                            if (pair3 != null) {
                                pair = pair3;
                                break loop2;
                            }
                        }
                    }
                    i3++;
                }
            }
            if (pair != null) {
                Log.d("WalletRoleUiBehavior", "bannerAndLabel is not null");
                Log.d("WalletRoleUiBehavior", "Title = " + pair.second);
            }
        }
        return pair;
    }
}
