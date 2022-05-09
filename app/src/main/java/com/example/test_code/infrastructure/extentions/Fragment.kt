package com.example.test_code.infrastructure.extentions
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()