package com.project.wasp

import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.FOREGROUND_SERVICE_MICROPHONE
import android.Manifest.permission.MODIFY_AUDIO_SETTINGS
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.RECEIVE_BOOT_COMPLETED
import android.Manifest.permission.RECORD_AUDIO
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

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
        val calendar = Calendar.getInstance()
        val hour = 1
        val expectedTheme = R.style.Theme_EarlyNight
        val target: Any = activityScenario
        val field = target.javaClass.getDeclaredField(expectedTheme.toString())
        field.isAccessible = true
        field.set(target,calendar)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        activityScenario.onActivity { activity ->
            val themeAttr = android.R.attr.windowBackground
            val typedValue = android.util.TypedValue()
            activity.theme.resolveAttribute(themeAttr, typedValue, true)
            val actualThemeResId = typedValue.resourceId
            assertEquals(expectedTheme, actualThemeResId)
        }
    }

    private fun grantPermissions(vararg permissions: String) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        for (permission in permissions) {
            val command = "pm grant ${instrumentation.targetContext.packageName} $permission"
            instrumentation.uiAutomation.executeShellCommand(command)
        }
    }
}