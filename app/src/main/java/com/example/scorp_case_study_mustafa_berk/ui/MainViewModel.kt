package com.example.scorp_case_study_mustafa_berk.ui

import DataSource
import Person
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scorp_case_study_mustafa_berk.domain.UserListState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {


    private val _userListState:MutableStateFlow<UserListState> = MutableStateFlow(UserListState.Idle)
    val userListState:StateFlow<UserListState> = _userListState

    private val _users:MutableStateFlow<MutableList<Person>> = MutableStateFlow(mutableListOf())
    val users:StateFlow<List<Person>> = _users

    private var _next: String? = null

    init {
        viewModelScope.launch {
            fetch()
        }
    }
    fun fetch(){
        _userListState.value = UserListState.Loading
         DataSource().fetch(_next){fetchResponse, fetchError ->
             if (fetchError!=null){
                 _userListState.value = UserListState.Error(fetchError.errorDescription)
             }else{
                 _next = fetchResponse?.next
                 if (fetchResponse?.people.isNullOrEmpty()){
                     _userListState.value = UserListState.Empty
                 }else{
                     _userListState.value = UserListState.Success
                     viewModelScope.launch {
                         val temp = fetchResponse?.people!!.distinctBy { it.id }.toMutableList()
                         _users.value = temp
                     }
                 }
             }
         }
    }


}