package com.soligdag.filmdrawer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(
    modifier : Modifier = Modifier,
    queryText: String,
    onQueryTextChange: (String) -> Unit,
    onQueryTextSubmit: (String) -> Unit
) {
    OutlinedTextField(
        value = queryText,
        onValueChange = { value ->
            onQueryTextChange(value)
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp),
        //textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        label = { Text(text = "Search") },
        trailingIcon = {
            if (queryText != "") {
                IconButton(
                    onClick = {
                        onQueryTextChange("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            if (queryText.isNotBlank()) {
                onQueryTextSubmit(queryText)
            }
        })
    )
}

@Composable
fun BackBtn(modifier: Modifier,upPress: () -> Unit) {
    androidx.compose.material.IconButton(
        onClick = upPress,
        modifier = modifier
            .size(36.dp)
            .background(
                //color = Neutral8.copy(alpha = 0.32f),
                color = Color.Transparent,
                shape = CircleShape
            )
    ) {
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
            contentDescription = "backItem"
        )
    }
}

@Preview
@Composable
fun backBtnPreview() {
    FilmDrawerTheme {
        BackBtn(modifier = Modifier) {

        }
    }
}


@Composable
fun Dropdown(menuItems : List<String>, onItemSelected : (selectedItemIndex : Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        androidx.compose.material.Icon(imageVector = Icons.Filled.MoreVert,
            tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
            contentDescription = "",
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(6.dp))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(androidx.compose.material3.MaterialTheme.colorScheme.background)
        ) {
            menuItems.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    onItemSelected(selectedIndex)
                    expanded = false
                }) {
                    Text(text = s, modifier = Modifier.padding(8.dp,0.dp) )
                }
            }
        }
    }
}


@Preview
@Composable
private fun dropDownPreview() {
    FilmDrawerTheme {
        Dropdown(listOf("",""), onItemSelected = {})
    }
}

@Composable
fun customSearchView(
    queryText: String,
    onQueryTextChange: (String) -> Unit,
    onQueryTextSubmit: (String) -> Unit
) {
    androidx.compose.material.OutlinedTextField(
        value = queryText,
        onValueChange = { value ->
            onQueryTextChange(value)
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp),
        //textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            androidx.compose.material.Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        label = { androidx.compose.material.Text(text = "Search") },
        trailingIcon = {
            if (queryText != "") {
                androidx.compose.material.IconButton(
                    onClick = {
                        onQueryTextChange("")
                    }
                ) {
                    androidx.compose.material.Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            if (queryText.isNotBlank()) {
                onQueryTextSubmit(queryText)
            }
        })
    )
}

@Preview (showBackground = true)
@Composable
fun SearchViewPreview() {
    FilmDrawerTheme() {
        customSearchView(queryText = "Night int the museum", onQueryTextChange = {}, onQueryTextSubmit = {})
    }
    
}

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {  },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PasswordTrailingIcon(passwordVisibility: Boolean, onIconClicked: () -> Unit) {
    val (icon, iconColor) = if (passwordVisibility) {
        Pair(
            Icons.Filled.Visibility,
            MaterialTheme.colorScheme.onBackground
        )
    } else {
        Pair(
            Icons.Filled.VisibilityOff,
            MaterialTheme.colorScheme.onBackground
        )
    }
    IconButton(onClick = onIconClicked) {
        Icon(
            icon,
            contentDescription = "Visibility",
            tint = iconColor
        )
    }
}

@Composable
fun DividerWithText(text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Divider(
            Modifier.width((LocalConfiguration.current.screenWidthDp / 5).dp).background(color = MaterialTheme.colorScheme.onBackground)
        )
        Text(
            text = text,
            Modifier.padding(12.dp, 0.dp), style = Typography.bodyLarge

        )
        Divider(
            Modifier.width((LocalConfiguration.current.screenWidthDp / 5).dp).background(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}