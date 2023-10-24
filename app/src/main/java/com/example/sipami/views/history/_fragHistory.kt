package com.example.sipami.views.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sipami.databinding.FHistoryBinding

class _fragHistory : Fragment() {
    private lateinit var _b : FHistoryBinding
    lateinit var _v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _b = FHistoryBinding.inflate(layoutInflater)
        _v = _b.root

        return _v
    }
}