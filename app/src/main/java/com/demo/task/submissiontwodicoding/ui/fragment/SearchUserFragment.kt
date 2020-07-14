package com.demo.task.submissiontwodicoding.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.adapter.UserSearchAdapter
import com.demo.task.submissiontwodicoding.adapter.UserSearchListener
import com.demo.task.submissiontwodicoding.databinding.FragmentSearchUserBinding
import com.demo.task.submissiontwodicoding.viewmodel.SearchUserViewModel

class SearchUserFragment : Fragment() {

    private lateinit var binding: FragmentSearchUserBinding

    /** Initialize ViewModel **/
    private val viewModel: SearchUserViewModel by lazy {
        ViewModelProvider(this, SearchUserViewModel.Factory(requireActivity().application))
            .get(SearchUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchUserBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.selectedUser.observe(viewLifecycleOwner, Observer { navigate ->
            navigate?.let {
                this.findNavController().navigate(
                    SearchUserFragmentDirections.actionSearchUserFragmentToDetailUserFragment(
                        navigate.login
                    )
                )
                viewModel.onDoneClicked()
            }
        })

        val userSearchAdapter =
            UserSearchAdapter(UserSearchListener { viewModel.onListUserClicked(it) })
        binding.rvUserList.adapter = userSearchAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        /** SearchView Method in Toolbar **/
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_user)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) viewModel.searchUserDefault(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
