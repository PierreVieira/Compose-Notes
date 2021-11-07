package com.plcoding.cleanarchitecturenoteapp.features.notes.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.core.util.TestTags
import com.plcoding.cleanarchitecturenoteapp.di.AppModule
import com.plcoding.cleanarchitecturenoteapp.features.notes.presentation.addEditNote.AddEditNoteScreen
import com.plcoding.cleanarchitecturenoteapp.features.notes.presentation.notes.NotesScreen
import com.plcoding.cleanarchitecturenoteapp.features.notes.presentation.util.Screen
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            CleanArchitectureNoteAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                            "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        composeRule.apply {
            // Click on FAB to get to add note screen
            onNodeWithContentDescription("Add").performClick()

            // Enter text in title and content text fields
            onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("test-title")
            onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("test-content")

            // Save the new
            onNodeWithContentDescription("Save").performClick()

            // Make sure there is a note in the list with our title and content
            onNodeWithText("test-title").assertIsDisplayed()

            // Click on note to edit it
            onNodeWithText("test-title").performClick()

            // Make sure title and content text fields contain note title and content
            onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("test-title")
            onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).assertTextEquals("test-content")

            // Add the text "2" to the title text field
            onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("2")

            // Update the node
            onNodeWithContentDescription("Save").performClick()

            // Make sure the update was applied to the list
            onNodeWithText("test-title2").assertIsDisplayed()
        }
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        composeRule.apply {
            for (i in 1..3) {
                // Click on FAB to get to add note screen
                onNodeWithContentDescription("Add").performClick()

                // Enter text in title and content text fields
                onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput(i.toString())
                onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput(i.toString())

                // Save the new
                onNodeWithContentDescription("Save").performClick()
            }
            for (i in 1..3) {
                onNodeWithText(i.toString()).assertIsDisplayed()
            }
            onNodeWithContentDescription("Sort").performClick()
            onNodeWithContentDescription("Title").performClick()
            onNodeWithContentDescription("Descending").performClick()
            onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("3")
            onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("2")
            onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("1")
        }
    }
}