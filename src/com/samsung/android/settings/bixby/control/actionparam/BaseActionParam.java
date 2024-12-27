package com.samsung.android.settings.bixby.control.actionparam;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.sdk.bixby2.util.BixbyContextInfo;
import com.samsung.android.sdk.bixby2.util.BixbyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BaseActionParam {
    public String mActionName;
    public final Context mContext;
    public final Bundle mExtra;
    public boolean mIsCheckSupport = false;
    public final List mParamList;
    public HashMap mSpecificParamsMap;

    public BaseActionParam(Context context, String str, Bundle bundle) {
        ArrayList arrayList = new ArrayList(Arrays.asList("onoff", "keyword"));
        this.mContext = context;
        this.mActionName = str;
        this.mExtra = bundle;
        this.mParamList = new ArrayList();
        parsingBundle();
        createParamIfNeeded();
        arrayList.addAll(getKeysNeedConvertingKey());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Iterator it2 = ((ArrayList) this.mParamList).iterator();
            while (it2.hasNext()) {
                Parameter parameter = (Parameter) it2.next();
                if (TextUtils.equals(str2, parameter.key)) {
                    parameter.key = "value";
                }
            }
        }
    }

    public List getKeysNeedConvertingKey() {
        return Collections.emptyList();
    }

    public final String getParameterValue(String str) {
        Iterator it = ((ArrayList) this.mParamList).iterator();
        while (it.hasNext()) {
            Parameter parameter = (Parameter) it.next();
            if (TextUtils.equals(parameter.key, str)) {
                return parameter.value;
            }
        }
        return null;
    }

    public String getValue() {
        return getParameterValue("value");
    }

    public final boolean isNewDex() {
        String parameterValue = getParameterValue("newDex");
        if (TextUtils.isEmpty(parameterValue)) {
            return false;
        }
        return Boolean.parseBoolean(parameterValue);
    }

    public void parsingBundle() {
        Bundle bundle = this.mExtra;
        if (bundle == null) {
            Log.e("BaseActionParam", "mExtra is null");
            return;
        }
        HashMap hashMap = (HashMap) bundle.getSerializable("params");
        if (hashMap == null || hashMap.size() == 0) {
            Log.e("BaseActionParam", "params is null");
            return;
        }
        Iterator it = hashMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            String str = (String) entry.getKey();
            if (TextUtils.equals(str, "supported")) {
                this.mIsCheckSupport = true;
                break;
            }
            List list = (List) entry.getValue();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String str2 = (String) list.get(i);
                ((ArrayList) this.mParamList).add(new Parameter(str, str2));
                Log.i("BaseActionParam", "key : " + str + ", value : " + str2);
            }
        }
        BixbyContextInfo bixbyContextInfo = BixbyUtils.getBixbyContextInfo(this.mExtra);
        Integer num = bixbyContextInfo != null ? bixbyContextInfo.bixbyClientTaskId : null;
        if (num != null) {
            ((ArrayList) this.mParamList).add(new Parameter("task_id", String.valueOf(num)));
        }
        List list2 = this.mParamList;
        Bundle bundle2 = this.mExtra;
        ((ArrayList) list2)
                .add(
                        new Parameter(
                                "isFolded",
                                String.valueOf(
                                        bundle2 != null
                                                ? bundle2.getBoolean("isFolded", false)
                                                : false)));
    }

    public void createParamIfNeeded() {}
}
