package com.tarbadev.carambar.blackbox

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tarbadev.carambar.TestCarambarApplication
import com.tarbadev.carambar.repository.PersonRepository
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.apache.commons.io.IOUtils
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.springframework.http.MediaType

@RunWith(AndroidJUnit4::class)
@Config(application = TestCarambarApplication::class)
abstract class BlackboxTest {

    private lateinit var mockServer: MockWebServer
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setupBlackbox() {
        mockServer = MockWebServer()
        mockServer.start(8888)

        context.deleteFile(PersonRepository.FILENAME)
    }

    @After
    fun teardownBlackbox() {
        mockServer.shutdown()
    }

    private fun getResourceFileContent(fileName: String): String {
        return IOUtils.toString(javaClass.classLoader.getResourceAsStream(fileName))
    }

    protected fun getInternalFileContent(fileName: String): String {
        val fileInput = context.openFileInput(fileName)
        return String(fileInput.readBytes())
    }

    protected fun enqueueSuccessGenerationResponse() {
        val response = getResourceFileContent("personClientResponse.json")
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        mockedResponse.setBody(response)
        mockServer.enqueue(mockedResponse)
    }

    protected fun writeInternalFile(fileName: String, fileContent: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }
    }
}