package com.demo.task.submissiontwodicoding.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.databinding.FragmentDetailUserBinding
import com.demo.task.submissiontwodicoding.utils.SectionPagerAdapter
import com.demo.task.submissiontwodicoding.utils.definePlurals
import com.demo.task.submissiontwodicoding.viewmodel.DetailUserViewModel
import kotlinx.android.synthetic.main.fragment_detail_user.*

class DetailUserFragment : Fragment() {

    companion object {
        lateinit var usernameObj: String
    }

    private lateinit var binding: FragmentDetailUserBinding

    private lateinit var followers: String
    private lateinit var following: String

    /** Initialize ViewModel **/
    private val viewModel: DetailUserViewModel by lazy {
        usernameObj = DetailUserFragmentArgs.fromBundle(requireArguments()).keyUsername

        val viewModelFactory =
            DetailUserViewModel.Factory(requireActivity().application, usernameObj)
        ViewModelProvider(this, viewModelFactory)
            .get(DetailUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        /** To Set Followers and Following Value Before Initialize the Tabs **/
        viewModel.detailUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                followers = resources.getQuantityString(
                    R.plurals.followersNumber,
                    definePlurals(it.followers),
                    it.followers
                )
                following = resources.getQuantityString(
                    R.plurals.followingNumber,
                    definePlurals(it.following),
                    it.following
                )
            }.apply {
                pagerAdapter.addFragment(FollowerUserFragment(), followers)
                pagerAdapter.addFragment(FollowingUserFragment(), following)

                view_pager.adapter = pagerAdapter
                tabs.setupWithViewPager(view_pager)
            }
        })
    }

    private val pagerAdapter: SectionPagerAdapter by lazy {
        SectionPagerAdapter(childFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_setting, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}
