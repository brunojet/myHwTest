package com.example.myhwtest

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myhwtest.corelink.HclHardware
import com.example.myhwtest.ui.theme.MyHwTestTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appModule = module {
            single(named("modelName")) { Build.MODEL }
        }

        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }

        setContent {
            MyHwTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val hw = HclHardware()
    Column(modifier = modifier) {
        Text(text = "Hello $name!")
        Text(text = "getManufacturer: ${hw.getManufacturer()}")
        Text(text = "getModelName: ${hw.getModelName()}")
        Text(text = "serialNumber: ${hw.serialNumber()}")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyHwTestTheme {
        Greeting("Android")
    }
}