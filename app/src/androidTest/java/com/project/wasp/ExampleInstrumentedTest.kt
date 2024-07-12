package com.project.wasp

import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.FOREGROUND_SERVICE_MICROPHONE
import android.Manifest.permission.MODIFY_AUDIO_SETTINGS
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.RECEIVE_BOOT_COMPLETED
import android.Manifest.permission.RECORD_AUDIO
import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        POST_NOTIFICATIONS,
        RECORD_AUDIO,
        FOREGROUND_SERVICE,
        RECEIVE_BOOT_COMPLETED,
        FOREGROUND_SERVICE_MICROPHONE,
        MODIFY_AUDIO_SETTINGS
    )

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        // Launch the activity under test
        activityScenario = ActivityScenario.launch(MainActivity::class.java)

        grantPermissions(POST_NOTIFICATIONS,
            RECORD_AUDIO,
            FOREGROUND_SERVICE,
            RECEIVE_BOOT_COMPLETED,
            FOREGROUND_SERVICE_MICROPHONE,
            MODIFY_AUDIO_SETTINGS)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.project.wasp", appContext.packageName)

    }

    @Test
    fun testThemeApp(){
        // Theme of the app as per the time under test
        val hour = 1
        val expectedTheme = R.style.Theme_EarlyNight
        activityScenario.onActivity { activity ->
            // Simulate setting the theme based on time
            activity.setThemeBasedOnTime(hour)

            // Verify the theme
            val themeAttr = android.R.attr.windowBackground
            val typedValue = android.util.TypedValue()
            activity.theme.resolveAttribute(themeAttr, typedValue, true)
            val actualThemeResId = typedValue.resourceId
            val actualThemeBackgroundName = activity.convertResourceIdToStyleName(actualThemeResId)
            activity.theme.resolveAttribute(expectedTheme, typedValue, true)
            val expectedThemeResId = typedValue.resourceId
            val expectedThemeBackgroundName = activity.convertResourceIdToStyleName(expectedThemeResId)
            assertEquals(expectedThemeBackgroundName, actualThemeBackgroundName)
        }
    }

    private fun grantPermissions(vararg permissions: String) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        for (permission in permissions) {
            val command = "pm grant ${instrumentation.targetContext.packageName} $permission"
            instrumentation.uiAutomation.executeShellCommand(command)
        }
    }

    private fun MainActivity.setThemeBasedOnTime(hour: Int) {
        when (hour) {
            in 1..3 -> setTheme(R.style.Theme_EarlyNight)
            in 3..5 -> setTheme(R.style.Theme_LateNight)
            in 5..6 -> setTheme(R.style.Theme_EarlyMorning)
            in 6..12 -> setTheme(R.style.Theme_LateMorning)
            in 12..15 -> setTheme(R.style.Theme_EarlyAfternoon)
            in 15..18 -> setTheme(R.style.Theme_LateAfternoon)
            in 18..21 -> setTheme(R.style.Theme_EarlyEvening)
            in 21..23 -> setTheme(R.style.Theme_LateEvening)
            else -> setTheme(R.style.Theme_TrueColors)
        }
    }

    private fun Context.convertResourceIdToStyleName(resourceId: Int): String {
        return try {
            this.resources.getResourceEntryName(resourceId)
        } catch (e: Resources.NotFoundException) {
            "Resource not found"
        }
    }
}