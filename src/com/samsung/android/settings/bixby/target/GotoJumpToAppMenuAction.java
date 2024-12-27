package com.samsung.android.settings.bixby.target;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.settings.bixby.activity.BixbyTrampoline;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.util.SemLog;

import java.net.URISyntaxException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GotoJumpToAppMenuAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String value = getValue();
        if (value.contains("#Intent")) {
            try {
                if (!TextUtils.isEmpty(value)) {
                    Intent parseUri = Intent.parseUri(value, 0);
                    String stringExtra = parseUri.getStringExtra(":settings:fragment_args_key");
                    Bundle bundle = new Bundle();
                    bundle.putString(":settings:fragment_args_key", stringExtra);
                    parseUri.putExtra(":settings:show_fragment_args", bundle);
                    Intent intent = new Intent();
                    intent.setComponent(
                            new ComponentName(this.mContext, (Class<?>) BixbyTrampoline.class));
                    intent.addFlags(268468224);
                    intent.putExtra("android.intent.extra.INTENT", parseUri);
                    try {
                        launchSettings(intent, null);
                    } catch (ActivityNotFoundException e) {
                        SemLog.d("GotoJumpToAppMenuAction", "fail to find activity : " + e);
                    }
                }
            } catch (URISyntaxException e2) {
                Log.e("GotoJumpToAppMenuAction", "URISyntaxException : " + e2);
            }
        } else {
            Intent intent2 = new Intent();
            intent2.setComponent(
                    new ComponentName(this.mContext, (Class<?>) BixbyTrampoline.class));
            intent2.addFlags(268468224);
            intent2.putExtra("query", value);
            try {
                launchSettings(intent2, null);
            } catch (ActivityNotFoundException e3) {
                SemLog.d("GotoJumpToAppMenuAction", "fail to find activity : " + e3);
            }
        }
        return Action.createResult("success");
    }
}
