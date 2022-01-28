package com.tutuland.wof.common.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.theme.BrandOnPrimary
import com.tutuland.wof.common.theme.CircleCornerShape
import com.tutuland.wof.common.theme.SearchBackgroundColor
import com.tutuland.wof.common.theme.SearchIconColor
import com.tutuland.wof.core.search.viewmodel.SearchViewModel

@Composable
fun SearchField(viewModel: SearchViewModel, contentPadding: Dp, showHeader: (Boolean) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    fun submit(text: String) {
        viewModel.searchFor(text)
    }

    fun clearSearch() {
        setText("")
        submit("")
    }

    fun backFromField() {
        clearSearch()
        localFocusManager.clearFocus()
    }

    fun focusChanged(isFocused: Boolean) {
        showHeader(isFocused.not())
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = contentPadding)
            .padding(top = 16.dp, bottom = 48.dp)
            .defaultMinSize(minHeight = 60.dp)
            .background(color = SearchBackgroundColor, shape = CircleCornerShape),
    ) {
        SearchFieldInput(
            text = text,
            onTextChange = setText,
            onFocusChange = { focusChanged(it) },
            onImeAction = { submit(text) },
            leadingIcon = { SearchFieldBack { backFromField() } },
            trailingIcon = { SearchFieldClean { clearSearch() } },
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFieldInput(
    text: String,
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onImeAction: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            value = text,
            placeholder = placeholder,
            onValueChange = onTextChange,
            colors = textFieldColors(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
                keyboardController?.hide()
            }),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it.isFocused)
                },
        )
    }
}

val customTextSelectionColors = TextSelectionColors(
    handleColor = BrandOnPrimary,
    backgroundColor = BrandOnPrimary.copy(alpha = 0.4f)
)

val placeholder = @Composable { Text("Search by name", style = MaterialTheme.typography.body1) }

@Composable
fun textFieldColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
    backgroundColor = Color.Transparent,
    cursorColor = BrandOnPrimary,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    focusedLabelColor = BrandOnPrimary,
    unfocusedLabelColor = BrandOnPrimary,
    leadingIconColor = SearchIconColor,
    trailingIconColor = SearchIconColor,
    placeholderColor = BrandOnPrimary,
)


@Composable
fun SearchFieldBack(onBackClicked: () -> Unit) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        tint = SearchIconColor,
        contentDescription = null,
        modifier = Modifier.clickable(onClickLabel = "Close search field") { onBackClicked() },
    )
}

@Composable
fun SearchFieldClean(onCleanClicked: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Close,
        tint = SearchIconColor,
        contentDescription = null,
        modifier = Modifier.clickable(onClickLabel = "Clear search field text") { onCleanClicked() },
    )
}

@Composable
fun SearchFieldIcon() {
    Icon(
        imageVector = Icons.Default.Search,
        tint = SearchIconColor,
        contentDescription = null,
    )
}
