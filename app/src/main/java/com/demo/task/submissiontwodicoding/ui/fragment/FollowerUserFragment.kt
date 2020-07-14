package com.demo.task.submissiontwodicoding.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.task.submissiontwodicoding.adapter.UserFollowersAdapter
import com.demo.task.submissiontwodicoding.databinding.FragmentFollowerUserBinding
import com.demo.task.submissiontwodicoding.viewmodel.FollowerUserViewModel

class FollowerUserFragment : Fragment() {

    private lateinit var binding: FragmentFollowerUserBinding

    /** Initialize ViewModel **/
    private val viewModel: FollowerUserViewModel? by lazy {
        val viewModelFactory = FollowerUserViewModel.Factory(
            requireActivity().application,
            DetailUserFragment.usernameObj
        )

        ViewModelProvider(this, viewModelFactory)
            .get(FollowerUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerUserBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowerList.adapter = UserFollowersAdapter()
    }
}
