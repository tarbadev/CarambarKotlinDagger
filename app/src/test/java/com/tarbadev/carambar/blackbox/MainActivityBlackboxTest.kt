package com.tarbadev.carambar.blackbox

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.tarbadev.carambar.TestCarambarApplication
import com.tarbadev.carambar.blackbox.activity.MainActivityView
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.repository.PersonRepository
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
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start(8888)

        context.deleteFile(PersonRepository.FILENAME)
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }

    @Test
    fun `home displays New Generated Character`() {
        val response = getResourceFileContent("personClientResponse.json")
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.addHeader("Content-Type", APPLICATION_JSON_VALUE)
        mockedResponse.setBody(response)
        mockServer.enqueue(mockedResponse)

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = MainActivityView(activity)

            val firstName = mainActivityView.getFirstName()
            val lastName = mainActivityView.getLastName()
            val sex = mainActivityView.getSex()
            val origin = mainActivityView.getOrigin()

            assertThat(firstName).isEqualTo("John")
            assertThat(lastName).isEqualTo("Doe")
            assertThat(sex).isEqualTo("Male")
            assertThat(origin).isEqualTo("United States")

            val person = Person(
                firstName = "John",
                lastName = "Doe",
                sex = "Male",
                origin = "United States",
                age = 0
            )
            assertThat(getInternalFileContent(PersonRepository.FILENAME)).isEqualTo(Gson().toJson(person))
        }
    }

    @Test
    fun `home displays Character's Age`() {
        val response = getResourceFileContent("personClientResponse.json")
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.addHeader("Content-Type", APPLICATION_JSON_VALUE)
        mockedResponse.setBody(response)
        mockServer.enqueue(mockedResponse)

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = MainActivityView(activity)

            val age = mainActivityView.getAge()

            assertThat(age).isEqualTo("0")
        }
    }

    @Test
    fun `home displays a button Age that updates the age of the character`() {
        val response = getResourceFileContent("personClientResponse.json")
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.addHeader("Content-Type", APPLICATION_JSON_VALUE)
        mockedResponse.setBody(response)
        mockServer.enqueue(mockedResponse)

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = MainActivityView(activity)

            var age = mainActivityView.getAge()
            assertThat(age).isEqualTo("0")

            mainActivityView.clickOnAge()
            mainActivityView.clickOnAge()

            age = mainActivityView.getAge()
            assertThat(age).isEqualTo("2")
        }
    }

    private fun getResourceFileContent(fileName: String): String {
        return IOUtils.toString(javaClass.classLoader.getResourceAsStream(fileName))
    }

    private fun getInternalFileContent(fileName: String): String {
        val fileInput = context.openFileInput(fileName)
        return String(fileInput.readBytes())
    }
}