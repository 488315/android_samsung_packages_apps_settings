package com.samsung.android.settings.actions.development;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.util.SemLog;
import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ActionControlDialog extends DialogFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mResponseView;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0201  */
    /* JADX WARN: Type inference failed for: r0v17, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v2, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r6v4, types: [android.widget.Switch] */
    /* JADX WARN: Type inference failed for: r6v6, types: [androidx.appcompat.widget.SeslAbsSeekBar, androidx.appcompat.widget.SeslSeekBar] */
    /* JADX WARN: Type inference failed for: r6v8, types: [android.view.ViewGroup, android.widget.RadioGroup] */
    /* JADX WARN: Type inference failed for: r6v9 */
    /* JADX WARN: Type inference failed for: r7v7, types: [android.view.LayoutInflater] */
    @Override // androidx.fragment.app.DialogFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.Dialog onCreateDialog(android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 518
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.actions.development.ActionControlDialog.onCreateDialog(android.os.Bundle):android.app.Dialog");
    }

    public final void setAction(CommandAction commandAction) {
        CommandManager commandManager = CommandManager.getInstance(getContext());
        String string = getArguments().getString("deeplink");
        commandManager.getClass();
        try {
            Bundle bundle = new Bundle();
            bundle.putBundle(IMSParameter.CALL.ACTION, commandAction.getDataBundle());
            Bundle call =
                    commandManager
                            .mContext
                            .getContentResolver()
                            .call(
                                    Uri.parse(string).getAuthority(),
                                    "method_ACTION",
                                    string,
                                    bundle);
            if (call != null) {
                int i = call.getInt("response_code");
                String string2 = call.getString("response_message");
                SemLog.d("CommandManager", "perform command action : " + i + ", " + string2);
                TextView textView = this.mResponseView;
                if (textView != null) {
                    textView.setText(
                            "Response : "
                                    + (i != 1
                                            ? i != 2
                                                    ? i != 3
                                                            ? "RESPONSE_UNKNOWN"
                                                            : "RESPONSE_OK_ASYNC"
                                                    : "RESPONSE_FAIL"
                                            : "RESPONSE_OK")
                                    + ", Response message : "
                                    + string2);
                }
            }
        } catch (Exception e) {
            SemLog.e("CommandManager", e.getMessage());
        }
    }
}
