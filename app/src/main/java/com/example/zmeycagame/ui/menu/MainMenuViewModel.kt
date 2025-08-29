package com.example.zmeycagame.ui.menu

import androidx.lifecycle.ViewModel
import com.example.zmeycagame.data.BestScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    val bestScoreRepository: BestScoreRepository
) : ViewModel()
