package com.android.settings.homepage.contextualcards.slices;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.face.FaceManager;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.Utils;
import com.android.settings.biometrics.face.FaceStatusPreferenceController;
import com.android.settings.homepage.contextualcards.FaceReEnrollDialog;
import com.android.settings.security.SecuritySettings;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBuilderUtils;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSetupSlice implements CustomSliceable {
    public final Context mContext;
    public FaceManager mFaceManager;

    public FaceSetupSlice(Context context) {
        this.mContext = context;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return !this.mFaceManager.hasEnrolledTemplates(UserHandle.myUserId())
                ? SliceBuilderUtils.buildSearchResultPageIntent(
                                VolteConstants.ErrorCode.RTP_TIME_OUT,
                                R.string.menu_key_security,
                                this.mContext,
                                SecuritySettings.class.getName(),
                                FaceStatusPreferenceController.KEY_FACE_SETTINGS,
                                this.mContext
                                        .getText(R.string.security_settings_face_settings_enroll)
                                        .toString())
                        .setClassName(this.mContext.getPackageName(), SubSettings.class.getName())
                : new Intent(this.mContext, (Class<?>) FaceReEnrollDialog.class);
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        CharSequence text;
        CharSequence text2;
        FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(this.mContext);
        this.mFaceManager = faceManagerOrNull;
        if (faceManagerOrNull == null) {
            ListBuilder listBuilder =
                    new ListBuilder(this.mContext, CustomSliceRegistry.FACE_ENROLL_SLICE_URI);
            listBuilder.mImpl.setIsError();
            return listBuilder.build();
        }
        int myUserId = UserHandle.myUserId();
        boolean hasEnrolledTemplates = this.mFaceManager.hasEnrolledTemplates(myUserId);
        int intForUser =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "face_unlock_re_enroll", 0, myUserId);
        if (!hasEnrolledTemplates) {
            text = this.mContext.getText(R.string.security_settings_face_settings_enroll);
            text2 =
                    this.mContext.getText(
                            R.string.security_settings_face_settings_context_subtitle);
        } else if (intForUser == 1) {
            text =
                    this.mContext.getText(
                            R.string.security_settings_face_enroll_should_re_enroll_title);
            text2 =
                    this.mContext.getText(
                            R.string.security_settings_face_enroll_should_re_enroll_subtitle);
        } else {
            if (intForUser != 3) {
                ListBuilder listBuilder2 =
                        new ListBuilder(this.mContext, CustomSliceRegistry.FACE_ENROLL_SLICE_URI);
                listBuilder2.mImpl.setIsError();
                return listBuilder2.build();
            }
            text =
                    this.mContext.getText(
                            R.string.security_settings_face_enroll_must_re_enroll_title);
            text2 =
                    this.mContext.getText(
                            R.string.security_settings_face_enroll_must_re_enroll_subtitle);
        }
        ListBuilder listBuilder3 =
                new ListBuilder(this.mContext, CustomSliceRegistry.FACE_ENROLL_SLICE_URI);
        listBuilder3.mImpl.setColor(
                com.android.settingslib.Utils.getColorAttrDefaultColor(
                        this.mContext, android.R.attr.colorAccent));
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.ic_face_24dp);
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                        createWithResource,
                        0,
                        text);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.setTitleItem(createWithResource);
        rowBuilder.mTitle = text;
        rowBuilder.mTitleLoading = false;
        rowBuilder.mSubtitle = text2;
        rowBuilder.mSubtitleLoading = false;
        rowBuilder.mPrimaryAction = createDeeplink;
        listBuilder3.mImpl.addRow(rowBuilder);
        return listBuilder3.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_security;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.FACE_ENROLL_SLICE_URI;
    }
}
