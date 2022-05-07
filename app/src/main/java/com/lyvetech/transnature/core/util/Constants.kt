package com.lyvetech.transnature.core.util

import android.graphics.Color

object Constants {
    /**
     * Pagination
     */
    const val QUERY_PAGE_SIZE = 20

    /**
     * Bundle keys
     */
    const val BUNDLE_TRAIL_KEY = "bundleTrailKey"
    const val BUNDLE_ROUTE_KEY = "bundleRouteKey"

    /**
     * Location service
     */
    const val REQUEST_LOCATION_PERMISSION = 0
    const val MAP_ZOOM = 18f
    const val ACTION_START_OR_RESUME_SERVICE = "action_start_or_resume_service"
    const val ACTION_PAUSE_SERVICE = "action_pause_service"
    const val ACTION_STOP_SERVICE = "action_stop_service"
    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1
    const val ACTION_SHOW_TRACKING_FRAGMENT = "action_show_tracking_fragment"
    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L
    const val POLYLINE_COLOR = Color.BLACK
    const val USER_POLYLINE_COLOR = Color.GREEN
    const val POLYLINE_WIDTH = 8f
    const val TIMER_UPDATE_INTERVAL = 50L

    /**
     * Trail tags
     */
    const val TAG_SCARITA = "tag_scarita"
    const val TAG_CARAS = "tag_caras"
    const val TAG_CASCADA = "tag_cascada"
    const val TAG_LOREM = "tag_lorem"

    /**
     * Shared pref
     */
    const val SHARED_PREFERENCES_NAME = "sharedPref"
    const val KEY_FIRST_TIME_TOGGLE = "key_first_time_toggle"
    const val KEY_NAME = "key_name"
    const val KEY_WEIGHT = "key_weight"
    const val KEY_HEIGHT = "key_height"
}