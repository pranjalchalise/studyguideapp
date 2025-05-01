package hu.ait.studyguide.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import hu.ait.studyguide.navigation.StudyGuideNavHost
import hu.ait.studyguide.ui.theme.StudyguideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyguideTheme(dynamicColor = true) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    StudyGuideNavHost()
                }
            }
        }
    }
}
