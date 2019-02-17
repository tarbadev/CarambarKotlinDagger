package com.tarbadev.carambar.blackbox

import android.widget.TextView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tarbadev.carambar.MainActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class MainActivityBlackboxTest {
    @Test
    fun example_blackboxTest() {
        launch(MainActivity::class.java)
            .use { scenario -> scenario.onActivity { activity ->
                val textView = activity.findViewById(com.tarbadev.carambar.R.id.hello) as TextView

                assertThat(textView.text).isEqualTo("Hello World!")
            } }
    }
}