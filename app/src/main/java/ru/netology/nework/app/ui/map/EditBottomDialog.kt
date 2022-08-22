package ru.netology.nework.app.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.netology.nework.databinding.DialogBottomEditBinding

class EditBottomDialog : BottomSheetDialogFragment() {

    private var markerCaption: String = ""
    private var markerId: Int = 0

    private lateinit var binding: DialogBottomEditBinding

    private val viewModel: MarkersViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        markerId = requireArguments().getInt(EXTRA_ID)
        markerCaption = requireArguments().getString(EXTRA_CAPTION, "")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBottomEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.captionEditText.setText(markerCaption)

        binding.deleteButton.setOnClickListener {
            dismissAllowingStateLoss()
            viewModel.deleteMarker(markerId)
        }
        binding.moveButton.setOnClickListener {
            dismissAllowingStateLoss()
            viewModel.editMarker(markerId)
        }
        binding.saveButton.setOnClickListener {
            dismissAllowingStateLoss()
            viewModel.saveCaption(markerId, binding.captionEditText.text.toString())
        }
    }


    companion object {

        const val TAG: String = "EditBottomDialog"
        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_CAPTION = "EXTRA_CAPTION"

        @JvmStatic
        fun getInstance(marker: MyMarker) = EditBottomDialog().also { dialog ->
            dialog.arguments = Bundle().also {
                it.putInt(EXTRA_ID, marker.id)
                it.putString(EXTRA_CAPTION, marker.caption)
            }
        }
    }
}