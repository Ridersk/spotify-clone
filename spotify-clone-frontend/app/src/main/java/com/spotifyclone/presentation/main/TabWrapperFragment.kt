package com.spotifyclone.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.spotifyclone.R

class TabWrapperFragment(private val initialFragment: Fragment) : Fragment(), IWrapperFragment {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frameLayout = FrameLayout(activity!!.applicationContext)
        frameLayout.id = ID
        val args = Bundle()
        initialFragment.arguments = args
        reset()
        return frameLayout
    }

    override fun onReplace(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(ID, fragment)
            .addToBackStack(BACK_STACK_ROOT_TAG)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun reset() {
        removeBackStatck()
        childFragmentManager
            .beginTransaction()
            .replace(ID, initialFragment)
            .addToBackStack(BACK_STACK_ROOT_TAG)
            .commit()
    }

    private fun removeBackStatck() {
        if (childFragmentManager.fragments.size == 0) {
            return
        }
        childFragmentManager.popBackStack(
            BACK_STACK_ROOT_TAG,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        childFragmentManager.executePendingTransactions()
    }

    override fun onBackPressed(): Boolean {
        val size = childFragmentManager.backStackEntryCount
        if (size > 1) {
            childFragmentManager.popBackStackImmediate()
            return true
        }
        return false
    }

    companion object {
        private const val ID = R.id.containerViewPager
        private const val BACK_STACK_ROOT_TAG = "ROOT_FRAGMENT"

        fun getInstance(fragment: Fragment): TabWrapperFragment {
            val activityTabFragment = TabWrapperFragment(fragment)
            val args = Bundle()
            activityTabFragment.arguments = args
            return activityTabFragment
        }
    }
}