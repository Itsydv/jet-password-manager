package io.itsydv.jetpasswordmanager.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.itsydv.jetpasswordmanager.ui.theme.CoolBlue
import io.itsydv.jetpasswordmanager.ui.theme.DarkGray
import io.itsydv.jetpasswordmanager.ui.theme.LiteGray
import io.itsydv.jetpasswordmanager.ui.theme.LiteText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyInputText(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    placeHolder: String? = null,
    leadingIcon: @Composable (() -> Unit),
    textColor: Color = MaterialTheme.colors.onBackground,
    backgroundColor: Color = if (isSystemInDarkTheme()) DarkGray else LiteText,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    height: Dp = 56.dp,
    password: Boolean = false,
    onImeAction: () -> Unit = {},
    onTextChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisibility by remember { mutableStateOf(false) }

    Column {
        if (label != null) {
            Text(
                text = label,
                color = textColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )
        }
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { if (placeHolder != null) { Text(text = placeHolder) }},
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(height),
            leadingIcon = leadingIcon,
            trailingIcon = { if (password) {
                IconButton( onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    if (passwordVisibility) Icon(imageVector = Icons.Filled.Visibility, contentDescription = "Visisble")
                    else Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "Visible off") }
                }},
            textStyle = MaterialTheme.typography.button.copy(
                color = textColor,
                fontSize = 18.sp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColor,
                cursorColor = textColor,
                placeholderColor = textColor.copy(alpha = 0.8f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledLeadingIconColor = textColor,
                backgroundColor = backgroundColor,
                leadingIconColor = textColor,
                trailingIconColor = textColor,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent),
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
                keyboardController?.hide()
            }),
            visualTransformation = if (!password || passwordVisibility) VisualTransformation.None
                                    else PasswordVisualTransformation(),
        )
    }
}

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    textColor: Color = Color.White,
    backgroundColor: Color = CoolBlue,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor, contentColor = textColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}
