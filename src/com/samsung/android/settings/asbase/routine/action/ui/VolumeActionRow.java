package com.samsung.android.settings.asbase.routine.action.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSeekBar;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.asbase.widget.SecVolumeIcon;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\fB\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n"
                + "\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\u000b¨\u0006\r"
        },
        d2 = {
            "Lcom/samsung/android/settings/asbase/routine/action/ui/VolumeActionRow;",
            "Landroid/widget/LinearLayout;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrs",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
            "1",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class VolumeActionRow extends LinearLayout {
    public AudioManager audioManager;
    public SecVolumeIcon icon;
    public TextView levelText;
    public VolumeActionActivity$onModeChangedListener$1 listener;
    public int preVolumeLevel;
    public int ringerModeInt;
    public SeslSeekBar seekBar;
    public int streamType;
    public TextView titleView;
    public int volumeLevel;
    public int volumeMax;
    public int volumeMin;

    public VolumeActionRow(Context context) {
        super(context);
        this.ringerModeInt = 2;
        this.streamType = 3;
    }

    public final int getDefaultVolume() {
        int i = this.streamType;
        if (i != 1 && i != 2 && i != 5) {
            AudioManager audioManager = this.audioManager;
            if (audioManager != null) {
                return audioManager.getStreamVolume(i);
            }
            Intrinsics.throwUninitializedPropertyAccessException("audioManager");
            throw null;
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int i2 = this.streamType;
        ContentResolver contentResolver = context.getContentResolver();
        String str = ApnSettings.MVNO_NONE;
        int i3 =
                Settings.System.getInt(
                        contentResolver,
                        i2 != 1
                                ? i2 != 2
                                        ? i2 != 5 ? ApnSettings.MVNO_NONE : "volume_notification"
                                        : "volume_ring"
                                : "volume_system",
                        0);
        ContentResolver contentResolver2 = context.getContentResolver();
        if (i2 == 1) {
            str = "volume_system_speaker";
        } else if (i2 == 2) {
            str = "volume_ring_speaker";
        } else if (i2 == 5) {
            str = "volume_notification_speaker";
        }
        return Settings.System.getInt(contentResolver2, str, i3);
    }

    public final String getTitle() {
        TextView textView = this.titleView;
        if (textView != null) {
            return textView.getText().toString();
        }
        Intrinsics.throwUninitializedPropertyAccessException("titleView");
        throw null;
    }

    public final void saveVolume() {
        SeslSeekBar seslSeekBar = this.seekBar;
        if (seslSeekBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            throw null;
        }
        if (seslSeekBar.getProgress() != 0) {
            SeslSeekBar seslSeekBar2 = this.seekBar;
            if (seslSeekBar2 != null) {
                this.preVolumeLevel = seslSeekBar2.getProgress();
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                throw null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x004d, code lost:

       if (r8 != 5) goto L41;
    */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateFromRinger(int r8) {
        /*
            r7 = this;
            r0 = 1
            r1 = 0
            java.lang.String r2 = "icon"
            r3 = 2
            java.lang.String r4 = "seekBar"
            if (r8 == 0) goto L7e
            r5 = 5
            r6 = 0
            if (r8 == r0) goto L42
            if (r8 == r3) goto L12
            goto Lba
        L12:
            r7.saveVolume()
            r7.ringerModeInt = r6
            com.samsung.android.settings.asbase.widget.SecVolumeIcon r8 = r7.icon
            if (r8 == 0) goto L3e
            r8.updateLayoutForRoutine(r6, r6)
            int r8 = r7.streamType
            if (r8 != r5) goto L23
            goto L25
        L23:
            if (r8 != r0) goto L31
        L25:
            androidx.appcompat.widget.SeslSeekBar r8 = r7.seekBar
            if (r8 == 0) goto L2d
            r8.setEnabled(r6)
            goto L31
        L2d:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r1
        L31:
            androidx.appcompat.widget.SeslSeekBar r7 = r7.seekBar
            if (r7 == 0) goto L3a
            r7.setProgress(r6)
            goto Lba
        L3a:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r1
        L3e:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r1
        L42:
            r7.saveVolume()
            r7.ringerModeInt = r0
            int r8 = r7.streamType
            if (r8 == r0) goto L5c
            if (r8 == r3) goto L50
            if (r8 == r5) goto L5c
            goto L6a
        L50:
            com.samsung.android.settings.asbase.widget.SecVolumeIcon r8 = r7.icon
            if (r8 == 0) goto L58
            r8.updateLayoutForRoutine(r6, r0)
            goto L6a
        L58:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r1
        L5c:
            com.samsung.android.settings.asbase.widget.SecVolumeIcon r8 = r7.icon
            if (r8 == 0) goto L7a
            r8.updateLayoutForRoutine(r6, r0)
            androidx.appcompat.widget.SeslSeekBar r8 = r7.seekBar
            if (r8 == 0) goto L76
            r8.setEnabled(r6)
        L6a:
            androidx.appcompat.widget.SeslSeekBar r7 = r7.seekBar
            if (r7 == 0) goto L72
            r7.setProgress(r6)
            goto Lba
        L72:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r1
        L76:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r1
        L7a:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r1
        L7e:
            androidx.appcompat.widget.SeslSeekBar r8 = r7.seekBar
            if (r8 == 0) goto Lbb
            r8.setEnabled(r0)
            int r8 = r7.ringerModeInt
            if (r8 == r3) goto Lba
            r7.ringerModeInt = r3
            com.samsung.android.settings.asbase.widget.SecVolumeIcon r8 = r7.icon
            if (r8 == 0) goto Lb6
            androidx.appcompat.widget.SeslSeekBar r0 = r7.seekBar
            if (r0 == 0) goto Lb2
            int r0 = r0.getProgress()
            int r2 = r7.ringerModeInt
            r8.updateLayoutForRoutine(r0, r2)
            int r8 = r7.getDefaultVolume()
            androidx.appcompat.widget.SeslSeekBar r0 = r7.seekBar
            if (r0 == 0) goto Lae
            int r7 = r7.preVolumeLevel
            if (r7 != 0) goto La9
            goto Laa
        La9:
            r8 = r7
        Laa:
            r0.setProgress(r8)
            goto Lba
        Lae:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r1
        Lb2:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r1
        Lb6:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r1
        Lba:
            return
        Lbb:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.routine.action.ui.VolumeActionRow.updateFromRinger(int):void");
    }

    public VolumeActionRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ringerModeInt = 2;
        this.streamType = 3;
    }

    public VolumeActionRow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ringerModeInt = 2;
        this.streamType = 3;
    }
}
