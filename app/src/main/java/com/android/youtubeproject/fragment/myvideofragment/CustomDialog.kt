import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.youtubeproject.R
import com.android.youtubeproject.databinding.DialogLayoutBinding
import com.android.youtubeproject.fragment.myvideofragment.db.UserData
import com.google.android.material.snackbar.Snackbar


@SuppressLint("MissingInflatedId")
class CustomDialog(
    //리스트에 추가해 줄 생성자
    val onSave: (item: UserData) -> Unit


) : DialogFragment() {
    //    private val dialog = Dialog(context)
    private lateinit var binding: DialogLayoutBinding
    private val PICK_IMAGE_REQUEST = 1
    private var imgUri: Uri = getUri(R.drawable.ic_baseline_add_photo_alternate_24)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLayoutBinding.inflate(inflater, container, false)


        // Cancel 버튼
        binding.dialogCancel.setOnClickListener {
            dismiss()
        }

        // 추가할 데이터 변수
        var dialogName = binding.etDialogName
        var dialogId = binding.etDialogId
        var dialogImg = binding.ivDialog


        var dialogNameError = binding.tvDialogNameError
        var dialogIdError = binding.tvDialogIdError

        dialogImg.setOnClickListener {
            openGallery()
        }

        // 순서 - 닉네임, 아이디
        dialogName.addTextChangedListener(createNameTextWatcher(dialogName))
        dialogId.addTextChangedListener(createIdTextWatcher(dialogId))
        // Save 버튼
        binding.dialogSave.setOnClickListener {


            // 조건 추가하기 ( not null + @ )
            if (dialogNameError.visibility == View.INVISIBLE &&
                dialogIdError.visibility == View.INVISIBLE &&
                dialogName.text.toString().trim().isNotEmpty() &&
                dialogId.text.toString().trim().isNotEmpty()
            ) {
                Toast.makeText(requireContext(), "완료!", Toast.LENGTH_SHORT).show()

                //사진가져오는 기능이 필요
                val user = UserData(dialogId.text.toString(), dialogName.text.toString(), imgUri)

                onSave(user)
                dismiss()

            } else {
                Snackbar.make(binding.root, "입력 값을 확인해주세요.", Snackbar.LENGTH_SHORT).show()
            }

        }
        return binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    fun getUri(resid: Int): Uri = Uri.parse("android.resource://" + R::class.java.`package`?.name + "/" + resid)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            selectedImageUri?.let {
                imgUri = it
                binding.ivDialog.setImageURI(it)
            }


            // Now you can use the selectedImageUri to display the image or do further processing
        }
    }

    //dialog_name
    private fun createNameTextWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) =
                with(binding) {
//                    val id = s.toString()
//                    val regexPattern = Regex("\\b[가-힣]{2,}\\b")
//                    if (id.isNotEmpty() && regexPattern.matches(id)) {
//                        tvDialogNameError.visibility = View.INVISIBLE
//                    } else {
//                        tvDialogIdError.visibility = View.VISIBLE
//                    }
                }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }

    private fun createIdTextWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) =
                with(binding) {
//                    val id = s.toString()
//                    val regexPattern = Regex("\\b[가-힣]{2,}\\b")
//                    if (id.isNotEmpty() && regexPattern.matches(id)) {
//                        tvDialogNameError.visibility = View.INVISIBLE
//                    } else {
//                        tvDialogIdError.visibility = View.VISIBLE
//                    }
                }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }

}
