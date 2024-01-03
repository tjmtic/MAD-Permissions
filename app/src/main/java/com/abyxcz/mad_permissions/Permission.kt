package com.abyxcz.mad_permissions

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@ExperimentalPermissionsApi
@Composable
fun Permission(
    permission: String = android.Manifest.permission.CAMERA,
    rationale: String = "This permission is important for this app. Please grant the permission.",
    permissionNotAvailableContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    val permissionState = rememberPermissionState(permission)

    LaunchedEffect(key1 = true) {
        permissionState.launchPermissionRequest()
    }

    Rationale(rationale, { permissionState.launchPermissionRequest() } )


    when {
        permissionState.status.isGranted -> {
            // Location permission granted, continue with the location fetching
            content
        }
        permissionState.status.shouldShowRationale -> {
            // Explain to the user why we need the permission
            Rationale(rationale, { permissionState.launchPermissionRequest() } )
        }
        !permissionState.status.isGranted -> {
            // Permission is not granted.
            // You might want to show a dialog or a message to the user.
            permissionNotAvailableContent
            //Rationale()
        }
    }
}

@Composable
private fun Rationale(
    text: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Don't */ },
        title = {
            Text(text = "Permission request")
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(onClick = onRequestPermission ) {
                Text("Ok")
            }
        }
    )
}