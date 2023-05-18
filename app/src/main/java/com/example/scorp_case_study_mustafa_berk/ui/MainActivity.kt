package com.example.scorp_case_study_mustafa_berk.ui

import Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.scorp_case_study_mustafa_berk.R
import com.example.scorp_case_study_mustafa_berk.databinding.ActivityMainBinding
import com.example.scorp_case_study_mustafa_berk.domain.UserListState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private var userAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initListeners()

        observeUserListState()
        observeUsers()
    }

    private fun initListeners() {
        binding.btnTryAgain.setOnClickListener {
            viewModel.fetch()
        }

        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.fetch()
            }
        }
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect {
                    drawUsers(it)
                }
            }
        }
    }

    private fun drawUsers(users: List<Person>) {
        binding.swipeRefresh.isRefreshing = false
        userAdapter = UserAdapter(this, users)
        binding.rvUsers.adapter = userAdapter
    }

    private fun observeUserListState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userListState.collect {
                    binding.swipeRefresh.isRefreshing = it == UserListState.Loading
                    println(it.toString())
                    when (it) {
                        is UserListState.Idle -> {}
                        is UserListState.Loading -> {
                            drawLoading()
                        }
                        is UserListState.Empty -> {
                            drawError(getString(R.string.empty_list))
                        }
                        is UserListState.Success -> {
                            drawSuccess()
                        }
                        is UserListState.Error -> {
                            drawError(it.error ?: getString(R.string.unexpected_error))
                        }
                    }
                }
            }
        }

    }

    private fun drawLoading() {
        binding.llEmptyOrErrorArea.isVisible = false
    }

    private fun drawError(message: String) {
        binding.rvUsers.isVisible = false
        binding.llEmptyOrErrorArea.isVisible = true
        binding.tvMessage.text = message
    }

    private fun drawSuccess() {
        binding.rvUsers.isVisible = true
        binding.llEmptyOrErrorArea.isVisible = false
    }
}