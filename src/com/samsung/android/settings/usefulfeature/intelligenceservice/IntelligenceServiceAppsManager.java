package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;

import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.dynamicmenu.SecDynamicMenuFeatureProviderImpl;
import com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.InternetPackageInfo;
import com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PhotoEditorPackageInfo;
import com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.SamsungNotesPackageInfo;
import com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.VoiceRecoderPackageInfo;
import com.samsung.android.util.SemLog;

import java.util.HashMap;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class IntelligenceServiceAppsManager {
    public final Context context;
    public final int mUserId;
    public final SecDynamicMenuFeatureProviderImpl provider;
    public final TreeSet[] mAppList = new TreeSet[4];
    public final HashMap aiSupportedAppList = new HashMap();
    public String mPriorityCategory = "com.samsung.android.settings.is.category.communicate";

    public IntelligenceServiceAppsManager(Context context, int i) {
        this.context = context;
        this.mUserId = i;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.provider =
                (SecDynamicMenuFeatureProviderImpl)
                        featureFactoryImpl.secDynamicMenuFeatureProvider$delegate.getValue();
        for (int i2 = 0; i2 < 4; i2++) {
            this.mAppList[i2] = new TreeSet();
        }
        Context context2 = this.context;
        InternetPackageInfo internetPackageInfo = new InternetPackageInfo();
        internetPackageInfo.mContext = context2;
        this.aiSupportedAppList.put("com.sec.android.app.sbrowser", internetPackageInfo);
        Context context3 = this.context;
        PhotoEditorPackageInfo photoEditorPackageInfo = new PhotoEditorPackageInfo();
        photoEditorPackageInfo.mContext = context3;
        if (Rune.isChinaModel()) {
            this.aiSupportedAppList.put(
                    "com.sec.android.mimage.photoretouching", photoEditorPackageInfo);
        }
        Context context4 = this.context;
        SamsungNotesPackageInfo samsungNotesPackageInfo = new SamsungNotesPackageInfo();
        samsungNotesPackageInfo.mContext = context4;
        this.aiSupportedAppList.put("com.samsung.android.app.notes", samsungNotesPackageInfo);
        Context context5 = this.context;
        VoiceRecoderPackageInfo voiceRecoderPackageInfo = new VoiceRecoderPackageInfo();
        voiceRecoderPackageInfo.mContext = context5;
        this.aiSupportedAppList.put("com.sec.android.app.voicenote", voiceRecoderPackageInfo);
    }

    public final String[] getAIFeaturesFromMetaData(ResolveInfo resolveInfo, String str) {
        try {
            return this.context
                    .getPackageManager()
                    .getResourcesForApplication(resolveInfo.getComponentInfo().packageName)
                    .getStringArray(resolveInfo.activityInfo.metaData.getInt(str));
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            SemLog.d(
                    "IntelligenceServiceAppsManager",
                    "getAIFeatures: AI Features are not found = " + e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x03e7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x03cd A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0262  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.TreeSet[] getAppList(java.lang.String r31) {
        /*
            Method dump skipped, instructions count: 1028
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceAppsManager.getAppList(java.lang.String):java.util.TreeSet[]");
    }

    public final Bundle getBundleFromProvider(Bundle bundle, String str) {
        String string = bundle.getString(str);
        if (string != null && !string.isEmpty()) {
            Uri parse = Uri.parse(string);
            String lastPathSegment = parse.getLastPathSegment();
            try {
                ContentProviderClient acquireContentProviderClient =
                        this.context.getContentResolver().acquireContentProviderClient(parse);
                if (acquireContentProviderClient != null && lastPathSegment != null) {
                    try {
                        Bundle call =
                                acquireContentProviderClient.call(lastPathSegment, null, null);
                        acquireContentProviderClient.close();
                        return call;
                    } finally {
                    }
                }
                if (acquireContentProviderClient != null) {
                    acquireContentProviderClient.close();
                }
            } catch (RemoteException e) {
                SemLog.e(
                        "IntelligenceServiceAppsManager",
                        "getBundleFromProvider: uri = " + parse + ", " + e.getMessage());
            }
        }
        return null;
    }
}
