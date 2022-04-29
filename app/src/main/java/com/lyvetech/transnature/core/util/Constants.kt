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
    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"
    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L
    const val POLYLINE_COLOR = Color.BLACK
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
    const val KEY_FIRST_TIME_TOGGLE = "KEY_FIRST_TIME_TOGGLE"
    const val KEY_NAME = "KEY_NAME"
    const val KEY_WEIGHT = "KEY_WEIGHT"
}