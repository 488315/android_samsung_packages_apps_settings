package com.samsung.android.settings.asbase.vibration;

import android.content.UriMatcher;

import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class VibPickerConstants {
    public static final HashMap BASE_LOOKUP_TABLE =
            new HashMap<
                    String,
                    String>() { // from class:
                                // com.samsung.android.settings.asbase.vibration.VibPickerConstants.1
                {
                    put("1", "50035");
                    put("2", "50033");
                    put(DATA.DM_FIELD_INDEX.PUBLIC_USER_ID, "50034");
                    put("4", "50037");
                    put(DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE, "50036");
                    put(DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE, "50041");
                    put(DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB, "50042");
                    put(DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER, "50043");
                    put(DATA.DM_FIELD_INDEX.SMS_FORMAT, "50044");
                    put(DATA.DM_FIELD_INDEX.SMS_OVER_IMS, "50045");
                    put(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, "50125");
                    put(DATA.DM_FIELD_INDEX.SIP_T1_TIMER, "50126");
                    put(DATA.DM_FIELD_INDEX.SIP_T2_TIMER, "50127");
                    put(DATA.DM_FIELD_INDEX.SIP_T4_TIMER, "50128");
                    put(DATA.DM_FIELD_INDEX.SIP_TA_TIMER, "50129");
                    put(DATA.DM_FIELD_INDEX.SIP_TB_TIMER, "50130");
                    put(DATA.DM_FIELD_INDEX.SIP_TC_TIMER, "50131");
                }
            };
    public static final List COLORFUL_NOTIFICATION_PATTERN;
    public static final List COLORFUL_RINGTONE_PATTERN;
    public static final List COLORFUL_RINGTONE_PATTERN_LEGACY;
    public static final List LIVE_NOTIFICATION_PATTERN;
    public static final List LIVE_RINGTONE_PATTERN;
    public static final AnonymousClass2 PATH_URI_MATCHER;
    public static final List SILENT_PATTERN;
    public static final List SIMPLE_NOTIFICATION_PATTERN;
    public static final List SIMPLE_RINGTONE_PATTERN;
    public static final List SIMPLE_RINGTONE_PATTERN_LEGACY;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerConstants$2, reason: invalid class name */
    public final class AnonymousClass2 extends UriMatcher {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PatternContainer {
        public final int index;
        public final String name;

        public PatternContainer(int i, String str) {
            this.index = i;
            this.name = str;
        }
    }

    static {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(-1);
        anonymousClass2.addURI(
                "com.android.settings.personalvibration.PersonalVibrationProvider",
                "#",
                VibUri.RINGTONE_WITH_INDEX.getType());
        anonymousClass2.addURI(
                "com.android.settings.personalvibration.PersonalVibrationProvider",
                "registerinfo/#",
                VibUri.RINGTONE_PATH_WITH_INDEX.getType());
        anonymousClass2.addURI(
                "com.android.settings.personalvibration.PersonalVibrationProvider",
                "notification/#",
                VibUri.NOTIFICATION_PATH_WITH_INDEX.getType());
        PATH_URI_MATCHER = anonymousClass2;
        SIMPLE_RINGTONE_PATTERN_LEGACY =
                new ArrayList(
                        List.of(
                                new PatternContainer(11, "Basic call"),
                                new PatternContainer(9, "Heartbeat"),
                                new PatternContainer(10, "Ticktock"),
                                new PatternContainer(13, "Waltz"),
                                new PatternContainer(12, "Zig-zig-zig")));
        COLORFUL_RINGTONE_PATTERN_LEGACY =
                new ArrayList(
                        List.of(
                                new PatternContainer(17, "Off-beat"),
                                new PatternContainer(18, "Spinning"),
                                new PatternContainer(19, "Siren"),
                                new PatternContainer(20, "Telephone"),
                                new PatternContainer(21, "Ripple")));
        SIMPLE_RINGTONE_PATTERN =
                new ArrayList(
                        List.of(
                                new PatternContainer(56, "Short"),
                                new PatternContainer(57, "Medium"),
                                new PatternContainer(11, "Basic call"),
                                new PatternContainer(9, "Heartbeat"),
                                new PatternContainer(10, "Ticktock"),
                                new PatternContainer(13, "Waltz"),
                                new PatternContainer(12, "Zig-zig-zig")));
        COLORFUL_RINGTONE_PATTERN =
                new ArrayList(
                        List.of(
                                new PatternContainer(17, "Off-beat"),
                                new PatternContainer(18, "Spinning"),
                                new PatternContainer(19, "Siren"),
                                new PatternContainer(20, "Telephone"),
                                new PatternContainer(21, "Ripple"),
                                new PatternContainer(91, "Cricket"),
                                new PatternContainer(92, "Rebound")));
        LIVE_RINGTONE_PATTERN =
                new ArrayList(
                        List.of(
                                new PatternContainer(101, "Bounce"),
                                new PatternContainer(102, "Tap"),
                                new PatternContainer(103, "Dubstep"),
                                new PatternContainer(104, "Fireworks"),
                                new PatternContainer(105, "Gallop"),
                                new PatternContainer(106, "Shuffle"),
                                new PatternContainer(107, "Spring")));
        SIMPLE_NOTIFICATION_PATTERN =
                new ArrayList(
                        List.of(
                                new PatternContainer(56, "Short"),
                                new PatternContainer(57, "Medium"),
                                new PatternContainer(59, "Basic"),
                                new PatternContainer(9, "Heartbeat"),
                                new PatternContainer(10, "Ticktock"),
                                new PatternContainer(13, "Waltz"),
                                new PatternContainer(12, "Zig-zig-zig")));
        COLORFUL_NOTIFICATION_PATTERN =
                new ArrayList(
                        List.of(
                                new PatternContainer(84, "Off-beat"),
                                new PatternContainer(85, "Spinning"),
                                new PatternContainer(86, "Siren"),
                                new PatternContainer(87, "Telephone"),
                                new PatternContainer(88, "Ripple"),
                                new PatternContainer(89, "Cricket"),
                                new PatternContainer(90, "Rebound")));
        LIVE_NOTIFICATION_PATTERN =
                new ArrayList(
                        List.of(
                                new PatternContainer(93, "Bounce"),
                                new PatternContainer(94, "Tap"),
                                new PatternContainer(95, "Dubstep"),
                                new PatternContainer(96, "Fireworks"),
                                new PatternContainer(97, "Gallop"),
                                new PatternContainer(98, "Shuffle"),
                                new PatternContainer(99, "Spring")));
        SILENT_PATTERN = new ArrayList(List.of(new PatternContainer(60, "Silent")));
    }

    public static String getSepIndexById(String str) {
        for (String str2 : BASE_LOOKUP_TABLE.keySet()) {
            if (str.equals(str2)) {
                return (String) BASE_LOOKUP_TABLE.get(str2);
            }
        }
        return (String) BASE_LOOKUP_TABLE.get("1");
    }
}
