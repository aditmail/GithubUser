package com.demo.task.submissiontwodicoding.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.models.UserModel
import com.demo.task.submissiontwodicoding.ui.fragment.FormUserFragment.Companion.TYPE_ADD
import com.demo.task.submissiontwodicoding.ui.fragment.FormUserFragment.Companion.TYPE_EDIT
import com.demo.task.submissiontwodicoding.utils.UserPreference
import kotlinx.android.synthetic.main.fragment_form_user.btn_save
import kotlinx.android.synthetic.main.fragment_shared_pref.*

/**
 * A simple [Fragment] subclass.
 */
class SharedPrefFragment : Fragment(), View.OnClickListener {

    private lateinit var _userPreferences: UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "User Preferences!"
        _userPreferences = UserPreference(requireActivity())
        return inflater.inflate(R.layout.fragment_shared_pref, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_save.setOnClickListener(this)
        showExistingPreference()
    }

    private fun showExistingPreference() {
        userModel = _userPreferences.getUsers()
        checkAndPopulateView(userModel)
    }

    private fun checkAndPopulateView(userModel: UserModel) {
        when {
            userModel.name.toString().isNotEmpty() -> {
                btn_save.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                btn_save.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }

        tv_name.text = if (userModel.name.toString().isEmpty()) "No Data" else userModel.name
        tv_is_love_mu.text = if (userModel.isLove) "Yes" else "No"
        tv_email.text = if (userModel.email.toString().isEmpty()) "No Data" else userModel.email
        tv_age.text =
            if (userModel.age.toString().isEmpty()) "No Data" else userModel.age.toString()
        tv_phone.text =
            if (userModel.phoneNumber.toString().isEmpty()) "No Data" else userModel.phoneNumber

    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_save) {
            when {
                isPreferenceEmpty -> {
                    this.findNavController()
                        .navigate(
                            SharedPrefFragmentDirections.actionSharedPrefFragmentToFormUserFragment(
                                userModel,
                                TYPE_ADD
                            )
                        )
                }
                else -> {
                    this.findNavController()
                        .navigate(
                            SharedPrefFragmentDirections.actionSharedPrefFragmentToFormUserFragment(
                                userModel,
                                TYPE_EDIT
                            )
                        )
                }
            }
        }
    }

}
