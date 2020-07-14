package com.demo.task.submissiontwodicoding.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.task.submissiontwodicoding.adapter.UserFollowingAdapter
import com.demo.task.submissiontwodicoding.databinding.FragmentFollowingUserBinding
import com.demo.task.submissiontwodicoding.viewmodel.FollowingUserViewModel

class FollowingUserFragment : Fragment() {

    private lateinit var binding: FragmentFollowingUserBinding

    /** Initialize ViewModel **/
    private val viewModel: FollowingUserViewModel? by lazy {
        val viewModelFactory = FollowingUserViewModel.Factory(
            requireActivity().application,
            DetailUserFragment.usernameObj
        )

        ViewModelProvider(this, viewModelFactory)
            .get(FollowingUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingUserBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowingList.adapter = UserFollowingAdapter()
    }
}
