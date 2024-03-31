package com.example.githubuserapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.ViewModelFactory
import com.example.githubuserapp.databinding.ActivitySettingBinding
import com.example.githubuserapp.ui.home.MainActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.datastore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        switchTheme(viewModel)
    }

    private fun switchTheme(viewModel: SettingViewModel) {
        val switchTheme = binding.switchTheme

        this.viewModel.getThemeSettings().observe(this@SettingActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            this.viewModel.saveThemeSetting(isChecked)
        }
    }

    fun goToHome(view: View) {
        val intent = Intent(this@SettingActivity, MainActivity::class.java)
        startActivity(intent)
    }
}