package com.example.googletasksclone.newlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.googletasksclone.R
import com.example.googletasksclone.ui.theme.TasksAppTheme
import com.example.googletasksclone.utils.ImagePainterUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewListScreen(onDismissBottomSheet: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var listTitle by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismissBottomSheet,
        sheetState = sheetState,
    ) {
        NewListContent(listTitle, onValueChange = { listTitle = it }, onDone = {
            scope.launch {
                sheetState.hide()
                onDismissBottomSheet()
            }
        }, onCancel = {
            scope.launch {
                sheetState.hide()
                onDismissBottomSheet()
            }
        })
    }
}

@Composable
private fun NewListContent(
    listTitle: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    onCancel: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val isDoneButtonEnabled = listTitle.isNotBlank()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {

            Icon(painter = ImagePainterUtil.resolvePainter(R.drawable.ic_close_24),
                contentDescription = null,
                modifier = Modifier.clickable { onCancel() })
            Text(text = stringResource(id = R.string.create_new_list))
            Text(text = stringResource(id = R.string.done),
                color = if (isDoneButtonEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable(
                    enabled = isDoneButtonEnabled
                ) { onDone() })
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = listTitle,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.enter_list_title_hint
                    )
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { if (isDoneButtonEnabled) onDone() })
        )

    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun NewListScreenPreview() {
    TasksAppTheme {
        NewListContent(listTitle = "", {}, {}, {})
    }
}