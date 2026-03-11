package com.example.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.main.R
import com.example.main.statemgmt.AppViewModel

/**
 * Step 3 of the linear navigation chain.
 */
@Composable
fun Step3Screen(viewModel: AppViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.content_step3),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { 
            // We pop the stack 3 times to return all the way back to the Settings screen
            viewModel.popBackStack()
            viewModel.popBackStack()
            viewModel.popBackStack()
        }) {
            Text(stringResource(R.string.btn_finish))
        }
    }
}
