package com.example.scorp_case_study_mustafa_berk.domain

import Person

sealed class UserListState {
    object Idle:UserListState()
    object Loading:UserListState()
    object Success:UserListState()
    data class Error(val error:String?):UserListState()
    object Empty:UserListState()

}