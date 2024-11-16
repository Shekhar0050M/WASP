package com.project.wasp.prompts

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import kotlin.random.Random

class promptPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private val prompts = listOf(
        "What motivates you?",
        "What is your favorite hobby?",
        "Where do you see yourself in 5 years?",
        "What inspires you to keep going?"
    )

    init {
        // Randomly select a prompt
        val randomPrompt = prompts[Random.nextInt(prompts.size)]

        // Set the text to the selected prompt
        text = randomPrompt

        // Set a click listener to display the selected prompt
        setOnClickListener {
            Toast.makeText(context, "Selected: $randomPrompt", Toast.LENGTH_SHORT).show()
        }
    }
}