package com.example.scorp_case_study_mustafa_berk.ui

import Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.scorp_case_study_mustafa_berk.databinding.ActivityMainBinding
import com.example.scorp_case_study_mustafa_berk.domain.UserListState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private var userAdapter:UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeUserListState()
    }

    private fun observeUserListState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.userListState.collect{
                    println(it.toString())
                    when(it){
                        is UserListState.Idle->{}
                        is UserListState.Empty->{
                            Toast.makeText(this@MainActivity,"empty data",Toast.LENGTH_LONG).show()
                        }
                        is UserListState.Success->{
                            drawSuccess(it.users)
                        }
                        is UserListState.Error->{
                            Toast.makeText(this@MainActivity,"error happened",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }

    private fun drawSuccess(users: List<Person>) {
        userAdapter = UserAdapter(this, users)
        binding.rvUsers.adapter = userAdapter

    }
}