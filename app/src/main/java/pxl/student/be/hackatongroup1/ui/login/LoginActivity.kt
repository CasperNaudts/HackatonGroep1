package pxl.student.be.hackatongroup1.ui.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import kotlinx.android.synthetic.main.activity_login.*
import pxl.student.be.hackatongroup1.R
import pxl.student.be.hackatongroup1.data.async.OnHttpDataAvailable
import pxl.student.be.hackatongroup1.data.model.RequestDetect
import pxl.student.be.hackatongroup1.data.model.ResponsePersonLargePersonGroup
import pxl.student.be.hackatongroup1.domain.entity.Person
import pxl.student.be.hackatongroup1.domain.services.FaceService

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity(), OnHttpDataAvailable {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val service = FaceService(this)
        camera.setLifecycleOwner(this)
        camera.addCameraListener(object: CameraListener(){
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                service.detectFace(RequestDetect(result.data))
            }
        })

        takePictureButton.setOnClickListener {
            camera.takePicture()
        }
    }

    override fun onHttpDataAvailable(data: String) {
        if(data != "No Name"){
            Log.d(TAG, Person.fromModelToEntity(ResponsePersonLargePersonGroup.fromJsonToModel(data)).name)
        } else {
            Log.d(TAG, "No Person")
        }
    }
}
