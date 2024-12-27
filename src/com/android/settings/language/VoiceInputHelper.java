package com.android.settings.language;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.Xml;

import com.android.internal.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VoiceInputHelper {
    public ArrayList mAvailableRecognizerInfos = new ArrayList();
    public final Context mContext;
    public ComponentName mCurrentRecognizer;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RecognizerInfo implements Comparable {
        public final ComponentName mComponentName;
        public final String mKey;
        public final CharSequence mLabel;
        public final String mLabelStr;
        public final boolean mSelectableAsDefault;
        public final ServiceInfo mService;
        public final ComponentName mSettings;

        public RecognizerInfo(
                PackageManager packageManager, ServiceInfo serviceInfo, String str, boolean z) {
            this.mService = serviceInfo;
            ComponentName componentName =
                    new ComponentName(serviceInfo.packageName, serviceInfo.name);
            this.mComponentName = componentName;
            this.mKey = componentName.flattenToShortString();
            this.mSettings = str != null ? new ComponentName(serviceInfo.packageName, str) : null;
            CharSequence loadLabel = serviceInfo.loadLabel(packageManager);
            this.mLabel = loadLabel;
            this.mLabelStr = loadLabel.toString();
            serviceInfo.applicationInfo.loadLabel(packageManager);
            this.mSelectableAsDefault = z;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            return this.mLabelStr.compareTo(((RecognizerInfo) obj).mLabelStr);
        }
    }

    public VoiceInputHelper(Context context) {
        this.mContext = context;
    }

    public static ArrayList validRecognitionServices(Context context) {
        Pair pair;
        XmlResourceParser loadXmlMetaData;
        int next;
        List<ResolveInfo> queryIntentServices =
                context.getPackageManager()
                        .queryIntentServices(new Intent("android.speech.RecognitionService"), 128);
        ArrayList arrayList = new ArrayList();
        Iterator<ResolveInfo> it = queryIntentServices.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().serviceInfo;
            try {
                loadXmlMetaData =
                        serviceInfo.loadXmlMetaData(context.getPackageManager(), "android.speech");
            } catch (PackageManager.NameNotFoundException
                    | IOException
                    | XmlPullParserException e) {
                Log.e(
                        "VoiceInputHelper",
                        "Error parsing "
                                + serviceInfo.packageName
                                + " package recognition service meta-data",
                        e);
                pair = null;
            }
            if (loadXmlMetaData == null) {
                throw new XmlPullParserException(
                        "No android.speech meta-data for " + serviceInfo.packageName + " package");
            }
            try {
                Resources resourcesForApplication =
                        context.getPackageManager()
                                .getResourcesForApplication(serviceInfo.applicationInfo);
                AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"recognition-service".equals(loadXmlMetaData.getName())) {
                    throw new XmlPullParserException(
                            serviceInfo.packageName
                                    + " package meta-data does not start with a"
                                    + " `recognition-service` tag");
                }
                TypedArray obtainAttributes =
                        resourcesForApplication.obtainAttributes(
                                asAttributeSet, R.styleable.RecognitionService);
                String string = obtainAttributes.getString(0);
                boolean z = obtainAttributes.getBoolean(1, true);
                obtainAttributes.recycle();
                loadXmlMetaData.close();
                pair = Pair.create(string, Boolean.valueOf(z));
                if (pair != null) {
                    arrayList.add(
                            new RecognizerInfo(
                                    context.getPackageManager(),
                                    serviceInfo,
                                    (String) pair.first,
                                    ((Boolean) pair.second).booleanValue()));
                }
            } catch (Throwable th) {
                if (loadXmlMetaData != null) {
                    try {
                        loadXmlMetaData.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        return arrayList;
    }

    public final void buildUi() {
        String string =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(), "voice_recognition_service");
        if (string == null || string.isEmpty()) {
            this.mCurrentRecognizer = null;
        } else {
            this.mCurrentRecognizer = ComponentName.unflattenFromString(string);
        }
        ArrayList validRecognitionServices = validRecognitionServices(this.mContext);
        this.mAvailableRecognizerInfos = new ArrayList();
        Iterator it = validRecognitionServices.iterator();
        while (it.hasNext()) {
            RecognizerInfo recognizerInfo = (RecognizerInfo) it.next();
            if (!recognizerInfo.mSelectableAsDefault) {
                ServiceInfo serviceInfo = recognizerInfo.mService;
                if (new ComponentName(serviceInfo.packageName, serviceInfo.name)
                        .equals(this.mCurrentRecognizer)) {}
            }
            this.mAvailableRecognizerInfos.add(recognizerInfo);
        }
        Collections.sort(this.mAvailableRecognizerInfos);
    }
}
