package com.example.googletasksclone

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.googletasksclone.newlist.NewListScreen
import com.example.googletasksclone.ui.theme.TasksAppTheme
import com.example.googletasksclone.utils.ImagePainterUtil.resolvePainter

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksAppTheme {
                HomeScreen()
            }
        }

    }

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {

    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = resolvePainter(R.drawable.ic_list_24),
                    contentDescription = stringResource(id = R.string.switch_task_lists)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = resolvePainter(R.drawable.ic_swap_24),
                    contentDescription = stringResource(id = R.string.change_sort_order)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = resolvePainter(R.drawable.ic_more_24),
                    contentDescription = stringResource(id = R.string.more_options)
                )
            }
        }, floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        })
    }) {
        if (showBottomSheet) {
            NewListScreen { showBottomSheet = false }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TasksAppTheme {
        HomeScreen()
    }
}