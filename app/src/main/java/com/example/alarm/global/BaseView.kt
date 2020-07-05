package com.example.alarm.global

interface BaseView<T> {
    fun setPresenter(presenter: T)
}