package com.example.scorp_case_study_mustafa_berk.domain

import Person

sealed class UserListState {
    object Idle:UserListState()
    data class Success(val users:List<Person>):UserListState()
    data class Error(val error:String?):UserListState()
    object Empty:UserListState()

}