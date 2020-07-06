package com.example.alarm.global

interface BaseView<T> {

    /**
     * Set the presenter of the view
     *
     * @param presenter The presenter
     */
    fun setPresenter(presenter: T)
}