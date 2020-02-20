package pxl.student.be.hackatongroup1

import org.junit.Test

import org.junit.Assert.*
import pxl.student.be.hackatongroup1.data.async.DetectCall
import pxl.student.be.hackatongroup1.data.async.OnHttpDataAvailable
import pxl.student.be.hackatongroup1.data.model.RequestDetect
import java.nio.charset.Charset

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testDetect() {
        print("Test")
        val detectCall = DetectCall(object: OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                print("Data: $data")
            }
        })
        detectCall.execute(RequestDetect("Test".toByteArray(Charsets.UTF_8)))
    }
}
