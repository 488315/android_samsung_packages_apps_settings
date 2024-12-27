package com.samsung.android.settings.actions.development;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.loader.content.AsyncTaskLoader;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionLoader extends AsyncTaskLoader {
    public final String mTargetAuthority;

    public ActionLoader(Context context, String str) {
        super(context);
        this.mTargetAuthority = str;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        int i;
        ArrayList parcelableArrayList;
        CommandManager commandManager = CommandManager.getInstance(this.mContext);
        String str = this.mTargetAuthority;
        commandManager.getClass();
        ArrayList arrayList = new ArrayList();
        try {
            Bundle call =
                    commandManager
                            .mContext
                            .getContentResolver()
                            .call(str, "method_CREATE", (String) null, (Bundle) null);
            if (call != null
                    && (((i = call.getInt("response_code")) == 1 || i == 3)
                            && (parcelableArrayList = call.getParcelableArrayList("command_list"))
                                    != null)) {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    arrayList.add(new Command((Bundle) it.next()));
                }
            }
        } catch (Exception e) {
            SemLog.e("CommandManager", e.getMessage());
        }
        SemLog.d("CommandManager", "loaded " + arrayList.size() + " command(s) from " + str);
        HashMap hashMap = new HashMap();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Command command = (Command) it2.next();
            String str2 = command.mCommandId;
            String str3 = command.mTitle;
            if (TextUtils.isEmpty(str3)) {
                str3 = "(Untitled)";
            }
            hashMap.put(str2, str3);
        }
        LinkedList<Map.Entry> linkedList = new LinkedList(hashMap.entrySet());
        Collections.sort(
                linkedList, Comparator.comparing(new ActionLoader$$ExternalSyntheticLambda0()));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : linkedList) {
            linkedHashMap.put((String) entry.getKey(), (String) entry.getValue());
        }
        return linkedHashMap;
    }
}
