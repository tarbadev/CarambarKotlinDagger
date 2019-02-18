package com.tarbadev.carambar.blackbox

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tarbadev.carambar.TestCarambarApplication
import com.tarbadev.carambar.ui.activity.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@RunWith(AndroidJUnit4::class)
@Config(application = TestCarambarApplication::class)
class MainActivityBlackboxTest {

    private lateinit var mockServer: MockWebServer

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start(8888)
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }


    @Test
    fun home_displaysNewGeneratedCharacter() {
        val response = getFileContent("personClientResponse.json")
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.addHeader("Content-Type", APPLICATION_JSON_VALUE)
        mockedResponse.setBody(response)
        mockServer.enqueue(mockedResponse)

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val firstName = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterFirstName) as TextView
            val lastName = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterLastName) as TextView
            val sex = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterSex) as TextView
            val origin = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterOrigin) as TextView

            assertThat(firstName.text).isEqualTo("John")
            assertThat(lastName.text).isEqualTo("Doe")
            assertThat(sex.text).isEqualTo("Male")
            assertThat(origin.text).isEqualTo("United States")
        }
    }

    private fun getFileContent(fileName: String): String {
        return IOUtils.toString(javaClass.classLoader.getResourceAsStream(fileName))
    }
}