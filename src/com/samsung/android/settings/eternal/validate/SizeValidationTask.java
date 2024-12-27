package com.samsung.android.settings.eternal.validate;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.settings.eternal.constant.EternalRune;
import com.samsung.android.settings.eternal.data.EpisodeHolder;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.policy.PolicyManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SizeValidationTask extends FutureTask {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SizeValidationWorker extends BaseValidationWorker {
        public final List mSizeViolationItems;

        public SizeValidationWorker(Context context, EpisodeHolder episodeHolder) {
            super(context, episodeHolder);
            this.mSizeViolationItems = new ArrayList();
        }

        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        public final String getValidationFileName() {
            return "backupSizeValidation.json";
        }

        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        public final List validateSceneList(Map.Entry entry, List list) {
            Bundle bundle;
            ((ArrayList) this.mSizeViolationItems).clear();
            ArrayList arrayList = new ArrayList();
            if (list == null) {
                return arrayList;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Scene scene = (Scene) it.next();
                SizeValidationItem sizeValidationItem = null;
                if (scene != null && (bundle = scene.mSceneValue) != null) {
                    Iterator<String> it2 = bundle.keySet().iterator();
                    int i = 0;
                    while (it2.hasNext()) {
                        String attribute = scene.getAttribute(it2.next());
                        if (!TextUtils.isEmpty(attribute)) {
                            i += attribute.length();
                        }
                    }
                    if (i > 1000) {
                        boolean contains =
                                ((HashSet)
                                                PolicyManager.LazyHolder.sInstance
                                                        .mBackupPolicy
                                                        .mBackupSizeLimitAllowItems)
                                        .contains(scene.mSceneKey);
                        EternalFileLog.d(
                                "Eternal/SizeValidationTask",
                                "validateSceneSize() key = "
                                        + scene.mSceneKey
                                        + " / size = "
                                        + i
                                        + " / isAllow = "
                                        + contains);
                        if (!contains) {
                            sizeValidationItem = new SizeValidationItem();
                            Bundle bundle2 = scene.mSceneValue;
                            if (bundle2 != null) {
                                Iterator<String> it3 = bundle2.keySet().iterator();
                                while (it3.hasNext()) {
                                    TextUtils.isEmpty(scene.getValue(it3.next(), false));
                                }
                            }
                        }
                    }
                }
                if (sizeValidationItem != null) {
                    arrayList.add(scene);
                    EternalRune.isTestMode();
                }
            }
            return arrayList;
        }
    }
}
