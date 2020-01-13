package com.omni.gitapiassignment.ui

import android.os.Bundle
import com.omni.gitapiassignment.ui.trendings.adapter.DataBindingViewHolder
import java.util.*

class ActionManager private constructor(){
    companion object {
        val instance: ActionManager by lazy {
            ActionManager()
        }
    }

    val actionStack: Stack<Action> = Stack()
    var onActionListener: ((Action) -> Unit)? = null

    fun fire(action: Action) {
        actionStack.push(action)
        onActionListener?.let {
            it(action)
        }
    }
}


data class Action(val type: ActionType, val data: Bundle? = null, val holder: DataBindingViewHolder? = null)

enum class ActionType {
    UNKNOWN,
    ACTION_TRENDING_REPOS,
    ACTION_REPO
}