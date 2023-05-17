package com.example.scorp_case_study_mustafa_berk.ui

import DataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scorp_case_study_mustafa_berk.domain.UserListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {


    private val _userListState:MutableStateFlow<UserListState> = MutableStateFlow(UserListState.Idle)
    val userListState:StateFlow<UserListState> = _userListState


    init {
        viewModelScope.launch {
            fetch(null)
        }
    }


   suspend fun fetch(next:String?){
         DataSource().fetch(next){fetchResponse, fetchError ->
             if (fetchError!=null){
                 _userListState.value = UserListState.Error(fetchError.errorDescription)
             }else{
                 if (fetchResponse?.people.isNullOrEmpty()){
                     _userListState.value = UserListState.Empty
                 }else{
                    _userListState.value = UserListState.Success(fetchResponse?.people!!)
                 }
             }
         }
    }


}