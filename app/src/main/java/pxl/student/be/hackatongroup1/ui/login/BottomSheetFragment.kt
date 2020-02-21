package pxl.student.be.hackatongroup1.ui.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*

import pxl.student.be.hackatongroup1.R
import pxl.student.be.hackatongroup1.domain.entity.Person

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val PERSON = "person"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BottomSheetFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "BottomSheetFragment"

class BottomSheetFragment(val person: Person): BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var isRecognised: Boolean = false
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(PERSON)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isRecognised = person.name != "No person founded"
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton.text = if(isRecognised) "Volgende" else "Annuleer"
        nextButton.setOnClickListener {
            Log.d(TAG, "Next photo")
        }
        loginMessage.text = if(isRecognised) "Bent u ${person.name}?"  else person.name
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomSheetFragment(Person("No person recognized")).apply {
                arguments = Bundle().apply {
                    putString(PERSON, param1)
                }
            }
    }
}
