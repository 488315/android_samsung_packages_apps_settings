package com.samsung.android.settings.inputmethod.bnr;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.view.DifferentialMotionFlingController$$ExternalSyntheticLambda0;

import com.android.internal.util.FastXmlSerializer;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.IMSParameter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KeyCustomizationBackupRestoreManager {
    public Thread mBackupThread;
    public final Context mContext;
    public final DifferentialMotionFlingController$$ExternalSyntheticLambda0
            mRestoreKeyCustomizationInfoCallback =
                    new DifferentialMotionFlingController$$ExternalSyntheticLambda0();

    public KeyCustomizationBackupRestoreManager(Context context) {
        this.mContext = context;
    }

    public static int getAttributeInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return TextUtils.isEmpty(attributeValue) ? i : Integer.parseInt(attributeValue);
    }

    public static boolean isAvailableFreeSpace(String str) {
        StatFs statFs = new StatFs(str);
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long j = availableBlocksLong * blockSizeLong;
        if (j >= 10485760) {
            return true;
        }
        StringBuilder m =
                SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                        availableBlocksLong,
                        "Not enough free space, availableBlocks=",
                        " blockSize=");
        m.append(blockSizeLong);
        m.append(" freeSpace=");
        m.append(j);
        Log.d("KeyCustomizationBackupRestore", m.toString());
        return false;
    }

    public static void parseKeyCustomizationAttributes(XmlPullParser xmlPullParser, List list) {
        String name = xmlPullParser.getName();
        int eventType = xmlPullParser.getEventType();
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = null;
        boolean z = false;
        do {
            String name2 = xmlPullParser.getName();
            if (eventType != 2) {
                if (eventType == 3) {
                    if (keyCustomizationInfo != null
                            && GoodSettingsContract.EXTRA_NAME.POLICY_KEY.equals(name2)) {
                        ((ArrayList) list).add(keyCustomizationInfo);
                    }
                    if (name != null && name.equals(name2)) {
                        z = true;
                    }
                }
            } else if (GoodSettingsContract.EXTRA_NAME.POLICY_KEY.equals(name2)) {
                keyCustomizationInfo = new SemWindowManager.KeyCustomizationInfo();
                keyCustomizationInfo.press = getAttributeInt(xmlPullParser, "keyPress", -1);
                keyCustomizationInfo.keyCode = getAttributeInt(xmlPullParser, "keyCode", 0);
                keyCustomizationInfo.action = getAttributeInt(xmlPullParser, "launchAction", -1);
                keyCustomizationInfo.dispatching = getAttributeInt(xmlPullParser, "dispatching", 0);
                keyCustomizationInfo.id = getAttributeInt(xmlPullParser, "id", -1);
                keyCustomizationInfo.userId = getAttributeInt(xmlPullParser, "userId", -2);
                if (keyCustomizationInfo.id == 2003) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "ownerPackage");
                    if (!TextUtils.isEmpty(attributeValue)) {
                        keyCustomizationInfo.ownerPackage = attributeValue;
                    }
                }
            } else if ("intent".equals(name2) && keyCustomizationInfo != null) {
                Intent intent = new Intent();
                String attributeValue2 =
                        xmlPullParser.getAttributeValue(null, IMSParameter.CALL.ACTION);
                if (TextUtils.isEmpty(attributeValue2) || attributeValue2.equals("null")) {
                    Log.d(
                            "KeyCustomizationBackupRestore",
                            "restoreFromXml intent info is empty or null");
                    intent = null;
                } else {
                    try {
                        if (attributeValue2.startsWith("intent:")
                                && attributeValue2.endsWith(
                                        NetworkAnalyticsConstants.DataPoints.CLOSE_TIME)) {
                            Log.d(
                                    "KeyCustomizationBackupRestore",
                                    "restoreFromXml intent info is uri type. action="
                                            .concat(attributeValue2));
                            intent = Intent.parseUri(attributeValue2, 1);
                        }
                    } catch (Exception e) {
                        Log.e("KeyCustomizationBackupRestore", "restoreFromXml failed. ", e);
                    }
                }
                keyCustomizationInfo.intent = intent;
                if (intent != null
                        && keyCustomizationInfo.id == 2003
                        && TextUtils.isEmpty(keyCustomizationInfo.ownerPackage)) {
                    String packageName =
                            intent.getComponent() != null
                                    ? intent.getComponent().getPackageName()
                                    : null;
                    if (TextUtils.isEmpty(packageName)) {
                        packageName = intent.getPackage();
                    }
                    if (!TextUtils.isEmpty(packageName)) {
                        keyCustomizationInfo.ownerPackage = packageName;
                    }
                }
            }
            if (!z) {
                eventType = xmlPullParser.next();
            }
            if (eventType == 1) {
                return;
            }
        } while (!z);
    }

    public static void writeKeyCustomizationAttributes(XmlSerializer xmlSerializer, List list) {
        Log.v("KeyCustomizationBackupRestore", "writeKeyCustomizationAttributes. hot_key");
        FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
        fastXmlSerializer.startTag((String) null, "hot_key");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                    (SemWindowManager.KeyCustomizationInfo) it.next();
            fastXmlSerializer.startTag((String) null, GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
            if (keyCustomizationInfo != null) {
                fastXmlSerializer.attribute(
                        (String) null, "keyPress", Integer.toString(keyCustomizationInfo.press));
                fastXmlSerializer.attribute(
                        (String) null, "keyCode", Integer.toString(keyCustomizationInfo.keyCode));
                fastXmlSerializer.attribute(
                        (String) null,
                        "launchAction",
                        Integer.toString(keyCustomizationInfo.action));
                fastXmlSerializer.attribute(
                        (String) null,
                        "dispatching",
                        Integer.toString(keyCustomizationInfo.dispatching));
                fastXmlSerializer.attribute(
                        (String) null, "id", Integer.toString(keyCustomizationInfo.id));
                fastXmlSerializer.attribute(
                        (String) null, "userId", Integer.toString(keyCustomizationInfo.userId));
                if (keyCustomizationInfo.id == 2003
                        && !TextUtils.isEmpty(keyCustomizationInfo.ownerPackage)) {
                    fastXmlSerializer.attribute(
                            (String) null, "ownerPackage", keyCustomizationInfo.ownerPackage);
                }
                fastXmlSerializer.startTag((String) null, "intent");
                Intent intent = keyCustomizationInfo.intent;
                if (intent != null) {
                    fastXmlSerializer.attribute(
                            (String) null, IMSParameter.CALL.ACTION, intent.toUri(1));
                } else {
                    fastXmlSerializer.attribute((String) null, IMSParameter.CALL.ACTION, "null");
                }
                fastXmlSerializer.endTag((String) null, "intent");
                fastXmlSerializer.endTag((String) null, GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
            }
        }
        fastXmlSerializer.endTag((String) null, "hot_key");
    }

    public final void sendResponseToSmartSwitch(
            String str,
            Constants$RESULT_CODE constants$RESULT_CODE,
            Constants$ERROR_CODE constants$ERROR_CODE,
            String str2,
            String str3) {
        int i = constants$ERROR_CODE.equals() ? 10485760 : 0;
        StringBuilder sb = new StringBuilder("response, resultCode=");
        sb.append(constants$RESULT_CODE);
        sb.append(" errorCode=");
        sb.append(constants$ERROR_CODE);
        sb.append(" source=");
        ConstraintWidget$$ExternalSyntheticOutline0.m(
                sb, str2, " sessionTime=", str3, " requiredSize=");
        sb.append(i);
        Log.d("KeyCustomizationBackupRestore", sb.toString());
        Intent putExtra =
                new Intent(str)
                        .putExtra("RESULT", constants$RESULT_CODE.getCode())
                        .putExtra("ERR_CODE", constants$ERROR_CODE.getCode())
                        .putExtra("REQ_SIZE", i)
                        .putExtra("SOURCE", str2);
        if (!TextUtils.isEmpty(str3)) {
            putExtra.putExtra("EXPORT_SESSION_TIME", str3);
        }
        this.mContext.sendBroadcast(putExtra, "com.wssnps.permission.COM_WSSNPS");
        Log.d("KeyCustomizationBackupRestore", "sendBroadcast, responseToSmartSwitch");
        File file =
                new File(
                        Environment.getUserSystemDirectory(0), "key_customization_backup_info.xml");
        if (file.exists()) {
            file.delete();
        }
        File file2 =
                new File(
                        Environment.getUserSystemDirectory(0),
                        "key_customization_temp_backup_info.xml");
        if (file2.exists()) {
            file2.delete();
        }
    }
}
