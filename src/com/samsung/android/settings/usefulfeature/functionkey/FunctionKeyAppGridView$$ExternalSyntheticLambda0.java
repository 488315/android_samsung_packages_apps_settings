package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.picker.model.AppInfo;
import androidx.picker.widget.AppPickerEvent$OnItemClickEventListener;
import androidx.picker.widget.SeslAppPickerView;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class FunctionKeyAppGridView$$ExternalSyntheticLambda0
        implements AppPickerEvent$OnItemClickEventListener,
                SeslAppPickerView.OnSearchFilterListener {
    public final /* synthetic */ Object f$0;

    @Override // androidx.picker.widget.AppPickerEvent$OnItemClickEventListener
    public boolean onClick(View view, AppInfo appInfo) {
        FunctionKeyAppGridView functionKeyAppGridView = (FunctionKeyAppGridView) this.f$0;
        functionKeyAppGridView.getClass();
        String str = appInfo.packageName + "/" + appInfo.activityName;
        Context context = functionKeyAppGridView.mContext;
        int i = functionKeyAppGridView.mPressType;
        Settings.Global.putString(
                context.getContentResolver(),
                i != 2 ? ApnSettings.MVNO_NONE : "function_key_config_doublepress_app_action",
                str);
        Log.d("FunctionKeyInfo", "[save] Press Type : " + i + "/ app : " + str);
        Intent intent = new Intent();
        int i2 = FunctionKeySettingsProvider.$r8$clinit;
        intent.putExtra(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, "key_app");
        functionKeyAppGridView.setResult(-1, intent);
        SALogging.insertSALog(String.valueOf(7616), String.valueOf(7616), str);
        functionKeyAppGridView.finish();
        return true;
    }

    @Override // androidx.picker.widget.SeslAppPickerView.OnSearchFilterListener
    public void onSearchFilterCompleted(int i) {
        FunctionKeyAppGridView functionKeyAppGridView =
                ((FunctionKeyAppGridView.AnonymousClass3) this.f$0).this$0;
        TextView textView = functionKeyAppGridView.mEmptyViewText;
        if (textView != null) {
            textView.setVisibility(i == 0 ? 0 : 8);
            functionKeyAppGridView.mAppPickerGridView.setVisibility(i != 0 ? 0 : 8);
        }
    }
}
