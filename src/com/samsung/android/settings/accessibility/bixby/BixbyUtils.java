package com.samsung.android.settings.accessibility.bixby;

import android.content.Context;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BixbyUtils {
    public static String doSetFloatValueMaxOrMin(ParsedBundle parsedBundle, float f) {
        if ("Time".equals(parsedBundle.unit)) {
            String str = parsedBundle.level;
            if ("Minimum".equals(str)) {
                if (f != 0.1f) {
                    return "success";
                }
            } else if ("Maximum".equals(str)) {
                if (f != 4.0f) {
                    return "success";
                }
            }
            return "already_set";
        }
        return "fail";
    }

    public static String getExclusivepopupDescription(Context context, String str, String str2) {
        List<ExclusiveTaskInfo> exclusiveTaskInfoList =
                AccessibilityExclusiveUtil.getExclusiveTaskInfoList(context, str);
        boolean isEmpty = exclusiveTaskInfoList.isEmpty();
        String str3 = ApnSettings.MVNO_NONE;
        if (isEmpty) {
            return ApnSettings.MVNO_NONE;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.turn_off_app_exclusive_option_content_short, str2));
        sb.append("\n");
        if (!exclusiveTaskInfoList.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (ExclusiveTaskInfo exclusiveTaskInfo : exclusiveTaskInfoList) {
                if (exclusiveTaskInfo.isAccessibilityTask()) {
                    arrayList.add(exclusiveTaskInfo);
                } else {
                    arrayList2.add(exclusiveTaskInfo);
                }
            }
            StringBuilder sb2 = new StringBuilder();
            boolean z = !arrayList.isEmpty();
            boolean z2 = !arrayList2.isEmpty();
            if (z) {
                if (z2) {
                    sb2.append(context.getString(R.string.accessibility_settings));
                    sb2.append("\n");
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ExclusiveTaskInfo exclusiveTaskInfo2 = (ExclusiveTaskInfo) it.next();
                        sb2.append("• ");
                        sb2.append(exclusiveTaskInfo2.getTaskTitle(context));
                        sb2.append("\n");
                    }
                    sb2.append(context.getString(R.string.others_exclusive_category_title));
                    sb2.append("\n");
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        ExclusiveTaskInfo exclusiveTaskInfo3 = (ExclusiveTaskInfo) it2.next();
                        sb2.append("• ");
                        sb2.append(exclusiveTaskInfo3.getTaskTitle(context));
                        sb2.append("\n");
                    }
                } else {
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        ExclusiveTaskInfo exclusiveTaskInfo4 = (ExclusiveTaskInfo) it3.next();
                        sb2.append("• ");
                        sb2.append(exclusiveTaskInfo4.getTaskTitle(context));
                        sb2.append("\n");
                    }
                }
            } else if (z2) {
                Iterator it4 = arrayList2.iterator();
                while (it4.hasNext()) {
                    ExclusiveTaskInfo exclusiveTaskInfo5 = (ExclusiveTaskInfo) it4.next();
                    sb2.append("• ");
                    sb2.append(exclusiveTaskInfo5.getTaskTitle(context));
                    sb2.append("\n");
                }
            }
            int lastIndexOf = sb2.lastIndexOf("\n");
            if (lastIndexOf == sb2.length() - 1) {
                sb2.deleteCharAt(lastIndexOf);
            }
            str3 = sb2.toString();
        }
        sb.append(str3);
        return sb.toString();
    }

    public static String getStateAlreadyChecked(String str, boolean z) {
        return ("On".equalsIgnoreCase(str) && z)
                ? "true"
                : ("Off".equalsIgnoreCase(str) && (z ^ true)) ? "true" : "false";
    }
}
