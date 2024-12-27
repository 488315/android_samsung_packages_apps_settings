package com.sec.ims.im;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ImIntent {
    public static final String CATEGORY_ACTION =
            "com.samsung.rcs.framework.instantmessaging.category.ACTION";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Action {
        public static final String ACCEPT_CHAT =
                "com.samsung.rcs.framework.instantmessaging.action.ACCEPT_CHAT";
        public static final String ADD_PARTICIPANTS =
                "com.samsung.rcs.framework.instantmessaging.action.ADD_PARTICIPANTS";
        public static final String ADD_PARTICIPANTS_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.ADD_PARTICIPANTS_RESPONSE";
        public static final String ANSWER_GC_CHAT_INVITATION =
                "com.samsung.rcs.framework.instantmessaging.action.ANSWER_GC_CHAT_INVITATION";
        public static final String CHANGE_GROUPCHAT_LEADER =
                "com.samsung.rcs.framework.instantmessaging.action.CHANGE_GROUPCHAT_LEADER";
        public static final String CHANGE_GROUPCHAT_LEADER_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.CHANGE_GROUPCHAT_LEADER_RESPONSE";
        public static final String CHANGE_GROUP_ALIAS =
                "com.samsung.rcs.framework.instantmessaging.action.CHANGE_GROUP_ALIAS";
        public static final String CHANGE_GROUP_ALIAS_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.CHANGE_GROUP_ALIAS_RESPONSE";
        public static final String CHATBOT_ANONYMIZE =
                "com.samsung.rcs.framework.chatbot.action.CHATBOT_ANONYMIZE";
        public static final String CHATBOT_ANONYMIZE_NOTIFICATION =
                "com.samsung.rcs.framework.chatbot.action.CHATBOT_ANONYMIZE_NOTIFICATION";
        public static final String CHATBOT_ANONYMIZE_RESPONSE =
                "com.samsung.rcs.framework.chatbot.action.CHATBOT_ANONYMIZE_RESPONSE";
        public static final String CLOSE_CHAT =
                "com.samsung.rcs.framework.instantmessaging.action.CLOSE_CHAT";
        public static final String CREATE_CHAT =
                "com.samsung.rcs.framework.instantmessaging.action.CREATE_CHAT";
        public static final String CREATE_CHAT_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.CREATE_CHAT_RESPONSE";
        public static final String DELETE_ALL_CHATS =
                "com.samsung.rcs.framework.instantmessaging.action.DELETE_ALL_CHATS";
        public static final String DELETE_ALL_MESSAGES =
                "com.samsung.rcs.framework.instantmessaging.action.DELETE_ALL_MESSAGES";
        public static final String DELETE_CHATS =
                "com.samsung.rcs.framework.instantmessaging.action.DELETE_CHATS";
        public static final String DELETE_GROUPCHAT_ICON =
                "com.samsung.rcs.framework.instantmessaging.action.DELETE_GROUPCHAT_ICON";
        public static final String DELETE_MESSAGES =
                "com.samsung.rcs.framework.instantmessaging.action.DELETE_MESSAGES";
        public static final String DELIVERY_TIMEOUT =
                "com.samsung.rcs.framework.instantmessaging.action.DELIVERY_TIMEOUT";
        public static final String GET_IS_COMPOSING_ACTIVE_URIS =
                "com.samsung.rcs.framework.instantmessaging.action.GET_IS_COMPOSING_ACTIVE_URIS";
        public static final String GET_IS_COMPOSING_ACTIVE_URIS_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.GET_IS_COMPOSING_ACTIVE_URIS_RESPONSE";
        public static final String GET_LAST_MESSAGES_SENT =
                "com.samsung.rcs.framework.instantmessaging.action.GET_LAST_MESSAGES_SENT";
        public static final String GET_LAST_MESSAGES_SENT_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.GET_LAST_MESSAGES_SENT_RESPONSE";
        public static final String IGNORE_INCOMING_MESSAGE =
                "com.samsung.rcs.framework.instantmessaging.action.IGNORE_INCOMING_MESSAGE";
        public static final String IGNORE_INCOMING_MESSAGE_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.IGNORE_INCOMING_MESSAGE_RESPONSE";
        public static final String MESSAGE_REVOKE_REQUEST =
                "com.samsung.rcs.framework.instantmessaging.action.MESSAGE_REVOKE_REQUEST";
        public static final String MESSAGE_REVOKE_TIMER_EXPIRED =
                "com.samsung.rcs.framework.instantmessaging.action.MESSAGE_REVOKE_TIMER_EXPIRED";
        public static final String OPEN_CHAT =
                "com.samsung.rcs.framework.instantmessaging.action.OPEN_CHAT";
        public static final String OUT_OF_MEMORY_ERROR =
                "com.samsung.rcs.framework.instantmessaging.action.OUT_OF_MEMORY_ERROR";
        public static final String RCS_MESSAGE = "com.gsma.services.rcs.action.RCS_MESSAGE";
        public static final String READ_MESSAGE =
                "com.samsung.rcs.framework.instantmessaging.action.READ_MESSAGE";
        public static final String RECEIVE_CHAT_CLOSED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_CHAT_CLOSED";
        public static final String RECEIVE_CHAT_ESTABLISHED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_CHAT_ESTABLISHED";
        public static final String RECEIVE_CHAT_INVITATION =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_CHAT_INVITATION";
        public static final String RECEIVE_CHAT_SUBJECT_UPDATED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_CHAT_SUBJECT_UPDATED";
        public static final String RECEIVE_GROUPCHAT_ICON_DELETED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_GROUPCHAT_ICON_DELETED";
        public static final String RECEIVE_GROUPCHAT_ICON_UPDATED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_GROUPCHAT_ICON_UPDATED";
        public static final String RECEIVE_GROUPCHAT_LEADER_CHANGED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_GROUPCHAT_LEADER_CHANGED";
        public static final String RECEIVE_GROUPCHAT_SESSION =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_GROUPCHAT_SESSION";
        public static final String RECEIVE_MESSAGE_INSERTED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_MESSAGE_INSERTED";
        public static final String RECEIVE_MESSAGE_NOTIFICATION_STATUS =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_MESSAGE_NOTIFICATION_STATUS";
        public static final String RECEIVE_NEW_MESSAGE =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_NEW_MESSAGE";
        public static final String RECEIVE_PARTICIPANTS_DELETED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_PARTICIPANTS_DELETED";
        public static final String RECEIVE_PARTICIPANTS_INSERTED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_PARTICIPANTS_INSERTED";
        public static final String RECEIVE_PARTICIPANT_ALIAS_UPDATED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_PARTICIPANT_ALIAS_UPDATED";
        public static final String RECEIVE_PARTICIPANT_DELETED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_PARTICIPANT_DELETED";
        public static final String RECEIVE_PARTICIPANT_INSERTED =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_PARTICIPANT_INSERTED";
        public static final String RECEIVE_RCS_MESSAGE =
                "com.gsma.services.rcs.action.RECEIVE_RCS_MESSAGE";
        public static final String RECEIVE_SEND_MESSAGE_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_SEND_MESSAGE_RESPONSE";
        public static final String RECEIVE_TYPING_NOTIFICATION =
                "com.samsung.rcs.framework.instantmessaging.action.RECEIVE_TYPING_NOTIFICATION";
        public static final String REMOVE_PARTICIPANTS =
                "com.samsung.rcs.framework.instantmessaging.action.REMOVE_PARTICIPANTS";
        public static final String REMOVE_PARTICIPANTS_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.REMOVE_PARTICIPANTS_RESPONSE";
        public static final String REPORT_CHATBOT_AS_SPAM =
                "com.samsung.rcs.framework.chatbot.action.REPORT_CHATBOT_AS_SPAM";
        public static final String REPORT_CHATBOT_AS_SPAM_RESPONSE =
                "com.samsung.rcs.framework.chatbot.action.REPORT_CHATBOT_AS_SPAM_RESPONSE";
        public static final String REPORT_MESSAGES =
                "com.samsung.rcs.framework.instantmessaging.action.REPORT_MESSAGES";
        public static final String REPORT_MESSAGES_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.REPORT_MESSAGES_RESPONSE";
        public static final String SEND_MESSAGE =
                "com.samsung.rcs.framework.instantmessaging.action.SEND_MESSAGE";
        public static final String SEND_MESSAGE_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.SEND_MESSAGE_RESPONSE";
        public static final String SEND_MESSAGE_RESPONSE_TAKETOOLONG =
                "com.samsung.rcs.framework.instantmessaging.action.SEND_MESSAGE_RESPONSE_TAKETOOLONG";
        public static final String SEND_TYPING_NOTIFICATION =
                "com.samsung.rcs.framework.instantmessaging.action.SEND_TYPING_NOTIFICATION";
        public static final String SET_CHAT_SUBJECT =
                "com.samsung.rcs.framework.instantmessaging.action.SET_CHAT_SUBJECT";
        public static final String SET_CHAT_SUBJECT_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.SET_CHAT_SUBJECT_RESPONSE";
        public static final String SET_GROUPCHAT_ICON =
                "com.samsung.rcs.framework.instantmessaging.action.SET_GROUPCHAT_ICON";
        public static final String SET_GROUPCHAT_ICON_RESPONSE =
                "com.samsung.rcs.framework.instantmessaging.action.SET_GROUPCHAT_ICON_RESPONSE";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Extras {
        public static final String BOOLEAN_ANSWER = "invitation_answer";
        public static final String CHATBOT_ANONYMIZE_ACTION = "chatbot_anonymize_action";
        public static final String CHATBOT_ANONYMIZE_RESULT = "chatbot_anonymize_result";
        public static final String CHATBOT_ANONYMIZE_URI = "chatbot_anonymize_uri";
        public static final String CHATBOT_COMMAND_ID = "chatbot_command_id";
        public static final String CHATBOT_FREE_TEXT = "chatbot_free_text";
        public static final String CHATBOT_REQUEST_ID = "request_id";
        public static final String CHATBOT_SPAM_TYPE = "chatbot_spam_type";
        public static final String CHATBOT_URI = "chatbot_uri";
        public static final String CHATS_LIST = "chats_list";
        public static final String CHAT_ID = "chat_id";
        public static final String CHAT_STATUS = "chat_status";
        public static final String COMPOSING_URI_LIST = "composing_uri_list";
        public static final String CONTENT_TYPE = "content_type";
        public static final String CONTRIBUTION_ID = "contribution_id";
        public static final String CONVERSATION_ID = "conversation_id";
        public static final String DEVICE_NAME = "device_name";
        public static final String DISPOSITION_NOTIFICATION = "disposition_notification";
        public static final String ERROR_NOTIFICATION_ID = "error_notification_id";
        public static final String ERROR_REASON = "error_reason";
        public static final String EXTRA_DIRECTION = "direction";
        public static final String EXTRA_FROM = "from";
        public static final String EXTRA_MAAP_TRAFFIC_TYPE = "maap_traffic_type";
        public static final String EXTRA_RECIPIENTS = "recipients";
        public static final String GROUPCHAT_ICON_DATA = "groupchat_icon_data";
        public static final String GROUPCHAT_ICON_NAME = "groupchat_icon_name";
        public static final String GROUPCHAT_ICON_PARTICIPANT = "groupchat_icon_participant";
        public static final String GROUPCHAT_ICON_TIMESTAMP = "groupchat_icon_timestamp";
        public static final String GROUPCHAT_ICON_URI = "groupchat_icon_uri";
        public static final String GROUP_CCUSER_LIST = "group_ccuser_list";
        public static final String INTERVAL = "interval";
        public static final String INVITATION_UI = "invitation_ui";
        public static final String IS_ACCEPT = "is_accept";
        public static final String IS_BOT = "is_bot";
        public static final String IS_BROADCAST_MSG = "is_broadcast_msg";
        public static final String IS_CLOSED_GROUP_CHAT = "is_closed_group_chat";
        public static final String IS_DISMISS_GROUPCHAT = "is_dismissGroupChat";
        public static final String IS_FILE_TRANSFER = "is_file_transfer";
        public static final String IS_FT = "is_ft";
        public static final String IS_GROUP_CHAT = "is_group_chat";
        public static final String IS_IGNORE_INCOMING_MSG = "is_ignore_incoming_msg";
        public static final String IS_LOCAL_WIPEOUT = "isLocalWipeOut";
        public static final String IS_PUBLICACCOUNT_MSG = "is_publicAccountMsg";
        public static final String IS_ROUTING_MSG = "is_routingMsg";
        public static final String IS_TEMPORARY = "is_temporary";
        public static final String IS_TOKEN_LINK = "is_token_link";
        public static final String IS_TOKEN_USED = "is_token_used";
        public static final String IS_TYPING = "is_typing";
        public static final String LAST_SENT_MESSAGES_STATUS = "last_sent_messages_status";
        public static final String LENGTH_TYPE = "length_type";
        public static final String MESSAGES_ID_LIST = "messages_id_list";
        public static final String MESSAGES_IMDN_DIR_MAP = "messages_imdn_dir_map";
        public static final String MESSAGES_IMDN_ID_LIST = "messages_imdn_id_list";
        public static final String MESSAGE_BODY = "message_body";
        public static final String MESSAGE_DIRECTION = "message_direction";
        public static final String MESSAGE_IMDN_ID = "message_imdn_id";
        public static final String MESSAGE_NOTIFICATION_STATUS = "message_notification_status";
        public static final String MESSAGE_NOTIFICATION_STATUS_RECEIVED =
                "message_notification_status_received";
        public static final String MESSAGE_NUMBER = "message_number";
        public static final String MESSAGE_SERVICE = "message_service";
        public static final String MESSAGE_TYPE = "message_type";
        public static final String PARTICIPANTS_LIST = "participants_list";
        public static final String PARTICIPANT_ID = "participant_id";
        public static final String PARTICIPANT_STATUS = "participant_status";
        public static final String PARTICIPANT_URI = "participant";
        public static final String PREFERRED_LINE = "preferred_line";
        public static final String PUBLICACCOUNT_DOMAIN = "publicAccount_Send_Domain";
        public static final String RCS_REFERENCE_ID = "rcs_reference_id";
        public static final String RCS_REFERENCE_TYPE = "rcs_reference_type";
        public static final String RCS_TRAFFIC_TYPE = "rcs_traffic_type";
        public static final String REASON = "reason";
        public static final String REFERENCE_MESSAGE_IMDN_ID = "reference_message_imdn_id";
        public static final String REFERENCE_MESSAGE_TYPE = "reference_message_type";
        public static final String REFERENCE_MESSAGE_VALUE = "reference_message_value";
        public static final String RELIABLE_MESSAGE = "reliable_message";
        public static final String REMOTE_URI = "remote_uri";
        public static final String REQUEST_MESSAGES_LIST = "request_ids";
        public static final String REQUEST_MESSAGE_ID = "request_message_id";
        public static final String REQUEST_SUCCESS = "response_status";
        public static final String REQUEST_THREAD_ID = "request_thread_id";
        public static final String REQUIRED_ACTION = "required_action";
        public static final String RETRY_AFTER = "retry_after";
        public static final String ROUTING_MSG_TYPE = "routing_msg_type";
        public static final String SERVICE_ID = "service_id";
        public static final String SESSION_URI = "session_uri";
        public static final String SIM_SLOT_ID = "sim_slot_id";
        public static final String SIP_ERROR = "sip_error";
        public static final String SUBJECT = "subject";
        public static final String SUBJECT_PARTICIPANT = "subject_participant";
        public static final String SUBJECT_TIMESTAMP = "subject_timestamp";
        public static final String SUGGESTION_TEXT = "extra_suggestion_text";
        public static final String SUPPORTED_CONTENT_LIST = "supported_content_list";
        public static final String UPDATE_ONLY_MSTORE = "update_only_mstore";
        public static final String USER_ALIAS = "user_alias";
        public static final String USER_SELECT_MESSAGE_TYPE = "user_select_message_type";
        public static final String USER_SELECT_RESULT = "user_select_result";
        public static final String WARN_TEXT = "warn_text";
    }
}
