package com.samsung.android.settings.accessibility.bixby.action;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.bixby.action.advanced.AccessibilityShortcutsAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.CameraFlashNotificationAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.DirectionLockAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.DownloadVoiceRecorderAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.FlashNotificationAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.ReadDeletedCharactersAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.ScreenFlashNotificationAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.SideAndVolumeUpKeysAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.SpeakKeyboardInputAloudAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.TimeToTakeAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.VoiceLabelAction;
import com.samsung.android.settings.accessibility.bixby.action.advanced.VolumeUpAndDownKeysAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.AutoActionAfterPointerStopsAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.BounceKeysAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.DexterityAndInteractionAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.EasyScreenTurnOnAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.IgnoredRepeatedTouchesAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.SlowKeysAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.StickyKeysAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.TapDurationAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.TouchAndHoldDelayAction;
import com.samsung.android.settings.accessibility.bixby.action.dexterity.TouchAndHoldDelayCustomAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.AdaptSoundAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.AmplifyAmbientSoundAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.BluetoothHearingAidAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.CaptionPreferenceAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.HearingAidCompatibilityAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.HearingAidSupportAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.HearingEnhancementAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.LiveCaptionAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.LiveTranscribeAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.LiveTranscribeDownloadAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.MonoAudioAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.MuteAllSoundsAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.SamsungSubtitlesAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.SoundDetectorAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.SoundNotificationAction;
import com.samsung.android.settings.accessibility.bixby.action.hearing.SpeechToTextAction;
import com.samsung.android.settings.accessibility.bixby.action.spoken.AudioDescriptionAction;
import com.samsung.android.settings.accessibility.bixby.action.talkback.TalkbackAction;
import com.samsung.android.settings.accessibility.bixby.action.talkback.TalkbackHelpAction;
import com.samsung.android.settings.accessibility.bixby.action.talkback.TalkbackSettingAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.AddColorFilterAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.BaseColorAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ColorInversionAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ExtraDimAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.FontSizeAndStyleAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.HighContrastFontsAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.HighContrastKeyboardAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.HighContrastThemeAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.HighlightButtonsAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.LargeMouseAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.LargerFontSizesAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.MagnificationAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.MagnifierAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.PointerSizeAndColorAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ReduceTransparencyAndBlurAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ReluminoOutlineAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.RemoveAnimationsAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ScreenZoomAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.VisibilityEnhancementAction;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BixbyActionData {
    public static Map bixbyActionMap;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum BixbyActionEnum {
        ACCESSIBILITY("Accessibility", new AccessibilityAction()),
        VISION("Vision", new VisibilityEnhancementAction()),
        ALL_MAGNIFICATION_OPTIONS("AllMagnificationOptions", new MagnificationAction()),
        COLOR_ADJUSTMENT("ColorAdjustment", new BaseColorAction()),
        COLOR_ADJUSTMENT_CONFIG("ColorAdjustmentConfig", new BaseColorAction()),
        COLOR_ADJUSTMENT_TEST("ColorAdjustmentTest", new BaseColorAction()),
        FONT_AND_STYLE("FontAndStyle", new FontSizeAndStyleAction()),
        FONT_SIZE_AND_STYLE("FontSizeAndStyle", new FontSizeAndStyleAction()),
        LARGE_FONT_SIZES("LargerFontSizes", new LargerFontSizesAction()),
        HIGH_CONTRAST_FONTS("HighContrastFonts", new HighContrastFontsAction()),
        HIGH_CONTRAST_THEME("HighContrastTheme", new HighContrastThemeAction()),
        HIGH_CONTRAST_KEYBOARD("HighContrastKeyboard", new HighContrastKeyboardAction()),
        MAGNIFICATION("Magnification", new MagnificationAction()),
        MAGNIFICATION_GESTURES("MagnificationGestures", new MagnificationAction()),
        MAGNIFIER_WINDOW("MagnifierWindow", new MagnificationAction()),
        MAGNIFIER("Magnifier", new MagnifierAction()),
        LARGE_MOUSE("LargeMouse", new LargeMouseAction()),
        NEGATIVE_COLORS("NegativeColors", new ColorInversionAction()),
        EXTRA_DIM("ExtraDim", new ExtraDimAction()),
        REDUCE_TRANSPARENCY("ReduceTransparencyAndBlur", new ReduceTransparencyAndBlurAction()),
        REMOVE_ANIMATIONS("RemoveAnimations", new RemoveAnimationsAction()),
        TAP_BUTTON_TO_MAGNIFY("TapButtonToMagnify", new MagnificationAction()),
        TRIPLE_TAP_SCREEN_TO_MAGNIFY("TapButtonToMagnify", new MagnificationAction()),
        SCREEN_ZOOM("ScreenZoom", new ScreenZoomAction()),
        SHOW_BUTTON_SHAPES("ShowButtonShapes", new HighlightButtonsAction()),
        COLOR_LENS("ColorLens", new AddColorFilterAction()),
        POINTER_SIZE_AND_COLOR("PointerSizeAndColor", new PointerSizeAndColorAction()),
        RELUMINO_OUTLINE("ReluminoOutline", new ReluminoOutlineAction()),
        DOWNLOAD_LIVE_TRANSCRIBE("DownloadLiveTranscribe", new LiveTranscribeDownloadAction()),
        HEARING("Hearing", new HearingEnhancementAction()),
        HEARING_AID("HearingAid", new HearingAidSupportAction()),
        HEARING_AID_SUPPORT("HearingAidSupport", new HearingAidSupportAction()),
        HEARING_AID_COMPATIBILITY("HearingAidCompatibility", new HearingAidCompatibilityAction()),
        LIVE_CAPTION("LiveCaption", new LiveCaptionAction()),
        LIVE_TRANSCRIBE("LiveTranscribe", new LiveTranscribeAction()),
        MONO_AUDIO("MonoAudio", new MonoAudioAction()),
        MUTE_ALL_SOUNDS("MuteAllSounds", new MuteAllSoundsAction()),
        ADAPT_SOUND("AdaptSound", new AdaptSoundAction()),
        AMPLIFY_AMBIENT_SOUND("AmplifyAmbientSound", new AmplifyAmbientSoundAction()),
        BABY_CRYING("BabyCrying", new SoundDetectorAction()),
        DOOR_BELL("DoorBell", new SoundDetectorAction()),
        SOUND_DETECTOR("SoundDetector", new SoundDetectorAction()),
        CHANGE_DOOR_BELL("ChangeDoorBell", new SoundDetectorAction()),
        CHANGE_DOOR_BELL_AND_SOUND_DETECTOR("DoorBell,soundDetector", new SoundDetectorAction()),
        BLUETOOTH_HEARING_AID("BluetoothHearingAid", new BluetoothHearingAidAction()),
        SOUND_NOTIFICATIONS("SoundNotifications", new SoundNotificationAction()),
        GOOGLE_SUBTITLES("GoogleSubtitles", new CaptionPreferenceAction()),
        SAMSUNG_SUBTITLES("SamsungSubtitles", new SamsungSubtitlesAction()),
        SPEECH_TO_TEXT("SpeechToText", new SpeechToTextAction()),
        AUTO_ACTION_AFTER_POINTER_STOPS(
                "ClickAfterPointerStops", new AutoActionAfterPointerStopsAction()),
        BOUNCE_KEYS("BounceKeys", new BounceKeysAction()),
        EASY_SCREEN_TURN("EasyScreenTurnOn", new EasyScreenTurnOnAction()),
        DEXTERITY_AND_INTERACTION("DexterityAndInteraction", new DexterityAndInteractionAction()),
        IGNORE_REPEATED_TOUCHES("IgnoreRepeatedTouches", new IgnoredRepeatedTouchesAction()),
        SLOW_KEYS("SlowKeys", new SlowKeysAction()),
        STICKY_KEYS("StickyKeys", new StickyKeysAction()),
        TAP_DURATION("TapDuration", new TapDurationAction()),
        TOUCH_AND_HOLD_DELAY("TouchAndHoldDelay", new TouchAndHoldDelayAction()),
        TOUCH_AND_HOLD_DELAY_CUSTOM("TouchAndHoldDelayCustom", new TouchAndHoldDelayCustomAction()),
        ADVANCED_SETTINGS("AdvancedSettings", new AccessibilityShortcutsAction()),
        ACCESSIBILITY_SHORTCUTS("DirectAccess", new SideAndVolumeUpKeysAction()),
        SIDE_AND_VOLUME_UP("PowerAndVolumeUpKeys", new SideAndVolumeUpKeysAction()),
        VOLUME_UP_AND_DOWN("VolumeUpAndDownKeys", new VolumeUpAndDownKeysAction()),
        ALL_FLASH_NOTIFICATION("All", new FlashNotificationAction()),
        FLASH_NOTIFICATION("FlashNotification", new FlashNotificationAction()),
        SCREEN_FLASH_NOTIFICATION("Screen", new ScreenFlashNotificationAction()),
        CAMERA_FLASH_NOTIFICATION("CameraLight", new CameraFlashNotificationAction()),
        DIRECTION_LOCK("DirectionLock", new DirectionLockAction()),
        SPEAK_KEYBOARD_INPUT_ALOUD("SpeakKeyboardInputAloud", new SpeakKeyboardInputAloudAction()),
        READ_DELETED_CHARACTERS_ALOUD(
                "ReadDeletedCharactersAloud", new ReadDeletedCharactersAction()),
        TIME_TO_TAKE("TimeToTakeAction", new TimeToTakeAction()),
        VOICE_LABEL("VoiceLabel", new VoiceLabelAction()),
        DOWNLOAD_VOICE_RECORDER("DownloadVoiceRecorder", new DownloadVoiceRecorderAction()),
        TALKBACK("VoiceAssistant", new TalkbackAction()),
        TALKBACK_HELP("VoiceAssistantHelp", new TalkbackHelpAction()),
        TALKBACK_SETTING("VoiceAssistantSettings", new TalkbackSettingAction()),
        AUDIO_DESCRIPTION("AudioDescription", new AudioDescriptionAction()),
        /* JADX INFO: Fake field, exist only in values array */
        HOW_TO_USE_ACCESSIBILITY("HowToUseAccessibility", new HowToUseAccessibilityAction()),
        /* JADX INFO: Fake field, exist only in values array */
        DUMMY(ApnSettings.MVNO_NONE, new DummyAction());

        private final BixbyActionTarget actionTarget;
        private final String name;

        BixbyActionEnum(String str, BixbyActionTarget bixbyActionTarget) {
            this.name = str;
            this.actionTarget = bixbyActionTarget;
        }

        public final String getName() {
            return this.name;
        }

        public final BixbyActionTarget getTargetMenu() {
            return this.actionTarget;
        }
    }

    public static BixbyActionTarget getTargetMenu(String str) {
        if (bixbyActionMap == null) {
            bixbyActionMap = new HashMap();
            for (BixbyActionEnum bixbyActionEnum : BixbyActionEnum.values()) {
                ((HashMap) bixbyActionMap).put(bixbyActionEnum.getName(), bixbyActionEnum);
            }
        }
        BixbyActionEnum bixbyActionEnum2 = (BixbyActionEnum) ((HashMap) bixbyActionMap).get(str);
        return bixbyActionEnum2 != null ? bixbyActionEnum2.getTargetMenu() : new DummyAction();
    }
}
