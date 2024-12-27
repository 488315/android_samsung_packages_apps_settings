package com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.android.settings.R;

import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class VoiceRecoderPackageInfo implements PackageInfo {
    public Context mContext;

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final String getCategory() {
        return "com.samsung.android.settings.is.category.productivity";
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final Drawable getIcon() {
        return this.mContext.getDrawable(R.drawable.sec_ic_intelligence_voice_recorder);
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final String getKeyHint() {
        return "key_voice_recoder";
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final Intent getLaunchIntent() {
        Intent intent = new Intent();
        intent.setClassName(
                "com.sec.android.app.voicenote",
                IntelligenceServiceUtils.getClassName(
                        this.mContext, "com.sec.android.app.voicenote"));
        intent.putExtra("from_settings", true);
        intent.putExtra("launch_from", "settings");
        intent.putExtra(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, "key_voice_recoder");
        SALogging.insertSALog("AI003", "key_voice_recoder");
        return intent;
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final int getOrder() {
        return 202000;
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final String getPackageName() {
        return "com.sec.android.app.voicenote";
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final Intent getQIPPopupIntent() {
        Intent intent = new Intent();
        intent.setData(
                Uri.parse(
                        "samsungapps://ProductDetail/com.sec.android.app.voicenote/?source=VoiceRecorder&fsOrigin=stubUpdateCheck&fsUpdateType=self"));
        intent.putExtra("type", "cover");
        intent.putExtra("form", "popup");
        intent.addFlags(335544352);
        SALogging.insertSALog("AI003", "key_voice_recoder_store");
        return intent;
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final String getSummary() {
        return this.mContext.getResources().getString(R.string.sec_package_summary_voice_recorder);
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final String getTitle() {
        return isInstalled()
                ? IntelligenceServiceUtils.getAppTitle(
                        this.mContext, "com.sec.android.app.voicenote")
                : this.mContext.getResources().getString(R.string.sec_package_name_voice_recorder);
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final boolean isInstalled() {
        return IntelligenceServiceUtils.isInstalled(this.mContext, "com.sec.android.app.voicenote");
    }

    @Override // com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo
    public final boolean isSupportedAI() {
        return IntelligenceServiceUtils.isSupportedAI(
                this.mContext, "com.sec.android.app.voicenote");
    }
}
