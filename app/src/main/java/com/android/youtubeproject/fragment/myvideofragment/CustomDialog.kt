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
    private val userData: UserData?,
    //리스트에 추가해 줄 생성자
    val onSave: (item: UserData) -> Unit


) : DialogFragment() {
    //    private val dialog = Dialog(context)
    private lateinit var binding: DialogLayoutBinding
    private val REQUEST_CODE_DOCUMENT_IMAGE = 1
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

        userData?.run {
            dialogName.setText(nickname)
            dialogId.setText(id)
            imgUri = picture
            dialogImg.setImageURI(imgUri)
        }

        var dialogNameError = binding.tvNicknameError
        var dialogIdError = binding.tvDialogIdError

        dialogImg.setOnClickListener {
            openGallery()
        }

        // 순서 - 닉네임, 아이디
        dialogName.addTextChangedListener(createNickNameTextWatcher(dialogName))
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
                val user = UserData(0, dialogId.text.toString(), dialogName.text.toString(), imgUri)

                onSave(user)
                dismiss()

            } else {
                Snackbar.make(binding.root, "입력 값을 확인해주세요.", Snackbar.LENGTH_SHORT).show()
            }

        }
        return binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, REQUEST_CODE_DOCUMENT_IMAGE)
    }

    fun getUri(resid: Int): Uri = Uri.parse("android.resource://" + R::class.java.`package`?.name + "/" + resid)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_DOCUMENT_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                requireActivity().contentResolver.takePersistableUriPermission(uri, takeFlags)

                imgUri = uri
                binding.ivDialog.setImageURI(uri)
            }
        }
    }

    //닉네임 유효성 : 3자 이상, 15자 이하의 한영, 숫자, 밑줄(_)허용, 특수 문자X
    private fun createNickNameTextWatcher(editText: EditText): TextWatcher {
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
                    val nickname = s.toString()
                    val regexPattern = Regex("^[a-zA-Z0-9_가-힣ㄱ-ㅎ]{3,15}$")

                    if (nickname.length < 3) {
                        // 3글자 이상 입력하지 않은 경우
                        tvNicknameError.visibility = View.VISIBLE
                    } else if (!regexPattern.matches(nickname)) {
                        // 특수문자 또는 공백을 입력한 경우
                        tvNicknameError.text = "특수문자 또는 공백은 입력할 수 없습니다."
                        tvNicknameError.visibility = View.VISIBLE
                    } else {
                        // 닉네임이 유효한 경우
                        tvNicknameError.visibility = View.INVISIBLE
                    }
                }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }

    //ID 유효성 : 4자 이상, 16자 이하의 영문 대/소문자, 숫자, 특수문자, 공백X
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
                    val id = s.toString()
                    val regexPattern = Regex("^[a-zA-Z0-9_!@#\$%^&*()-+=<>?/.,;:'\"\\[\\]{}|~]{4,16}$")

                    if (id.length < 4) {
                        // 4글자 이상 입력하지 않은 경우
                        tvDialogIdError.visibility = View.VISIBLE
                    } else if (!regexPattern.matches(id)) {
                        // 공백을 입력한 경우
                        tvDialogIdError.text = "한글 또는 공백은 입력할 수 없습니다."
                        tvDialogIdError.visibility = View.VISIBLE
                    } else {
                        // ID 유효한 경우
                        tvDialogIdError.visibility = View.INVISIBLE
                    }
                }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }

}
