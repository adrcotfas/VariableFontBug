package com.example.variablefontbug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.variablefontbug.ui.theme.TextFont
import com.example.variablefontbug.ui.theme.VariableFontBugTheme
import com.example.variablefontbug.ui.theme.fontWeights
import com.example.variablefontbug.ui.theme.textStyles
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var repo: TextStyleRepo

    @OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = TextStyleRepo(this)

        enableEdgeToEdge()
        setContent {

            val style by repo.textStyle.collectAsStateWithLifecycle(TextStyleData())

            VariableFontBugTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(text = "Text Style") },
                        )
                    },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                SliderRow(
                                    min = fontWeights.first(),
                                    max = fontWeights.last(),
                                    steps = fontWeights.size - 2,
                                    value = style.fontWeight,
                                    onValueChange = {
                                        GlobalScope.launch {
                                            repo.setStyle(style.copy(fontWeight = it))
                                        }
                                    },
                                    showValue = false
                                )
                                DropdownMenuPreference(
                                    title = "Font",
                                    value = TextFont.entries[style.fontIndex].prettyName(),
                                    dropdownMenuOptions = prettyNames<TextFont>()
                                ) {
                                    GlobalScope.launch {
                                        repo.setStyle(style.copy(fontIndex = it))
                                    }
                                }
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "1234",
                                    style = TextStyle(
                                        fontFamily = textStyles[style.fontIndex]!![style.fontWeight],
                                        fontSize = 100.sp
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}