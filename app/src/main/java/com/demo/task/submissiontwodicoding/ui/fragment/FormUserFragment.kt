package com.demo.task.submissiontwodicoding.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.models.UserModel
import com.demo.task.submissiontwodicoding.utils.UserPreference
import com.demo.task.submissiontwodicoding.utils.isValidEmail
import kotlinx.android.synthetic.main.fragment_form_user.*

/**
 * A simple [Fragment] subclass.
 */
class FormUserFragment : Fragment(), View.OnClickListener {

    companion object {
        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
        private const val FIELD_DIGIT_ONLY = "Hanya boleh Numerik Value"
        private const val FIELD_ISNT_VALID = "Email tidak Valid"
    }

    private lateinit var userModel: UserModel
    private var formType: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_save.setOnClickListener(this)
        userModel = FormUserFragmentArgs.fromBundle(requireArguments()).keyUserModel
        formType = FormUserFragmentArgs.fromBundle(requireArguments()).keyFormType.apply {
            when (this) {
                TYPE_ADD -> {
                    //activity?.actionBar?.title = "Tambah Baru"
                    (activity as AppCompatActivity).supportActionBar?.title = "Tambah Baru"
                    btn_save.text = "Simpan"
                }
                TYPE_EDIT -> {
                    //activity?.actionBar?.title = "Ubah Data"
                    (activity as AppCompatActivity).supportActionBar?.title = "Ubah Data"
                    btn_save.text = "Update"
                    showPreferenceInForm(userModel)
                }
            }
        }

    }

    private fun showPreferenceInForm(userModel: UserModel) {
        edt_name.setText(userModel.name)
        edt_email.setText(userModel.email)
        edt_age.setText(userModel.age.toString())
        edt_phone.setText(userModel.phoneNumber)

        if (userModel.isLove) rb_yes.isChecked = true else rb_no.isChecked = true
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_save -> {
                val name = edt_name.text.toString().trim()
                val email = edt_email.text.toString().trim()
                val age = edt_age.text.toString().trim()
                val phoneNo = edt_phone.text.toString().trim()
                val isLoveMU = rg_love_mu.checkedRadioButtonId == R.id.rb_yes

                if (name.isEmpty()) {
                    edt_name.error = FIELD_REQUIRED
                    return
                }

                if (email.isEmpty()) {
                    edt_email.error = FIELD_REQUIRED
                    return
                }

                if (!isValidEmail(email)) {
                    edt_name.error = FIELD_ISNT_VALID
                    return
                }

                if (age.isEmpty()) {
                    edt_name.error = FIELD_REQUIRED
                    return
                }

                if (phoneNo.isEmpty()) {
                    edt_phone.error = FIELD_REQUIRED
                    return
                }

                if ((!TextUtils.isDigitsOnly(phoneNo))) {
                    edt_phone.error = FIELD_DIGIT_ONLY
                    return
                }

                saveUsers(name, email, age, phoneNo, isLoveMU)

            }
        }
    }

    private fun saveUsers(
        name: String,
        email: String,
        age: String,
        phoneNo: String,
        loveMU: Boolean
    ) {
        val userPreference = UserPreference(requireActivity().applicationContext)

        userModel.name = name
        userModel.email = email
        userModel.age = Integer.parseInt(age)
        userModel.phoneNumber = phoneNo
        userModel.isLove = loveMU

        userPreference.setUsers(userModel)
        Toast.makeText(context, "Data Saved!", Toast.LENGTH_SHORT).show()
    }

}
