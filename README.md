# Personalised Learning Experience App

This Android app was developed for SIT305 Task 6.1D and focuses on delivering a personalised, AI-enhanced learning experience for students. The app collects user interests and dynamically generates multiple-choice quiz questions using OpenAI's GPT model.

## Features

-  User registration with input validation (username, email, password, phone)
-  Selection of up to 5 personal learning interests (e.g., AI, Algorithms, UX)
-  AI-generated quiz questions tailored to selected interests via OpenAI GPT
-  Real-time evaluation of user answers with feedback and scoring
-  Result summary with correct/incorrect answer comparison
-  Simple homepage showing selected interests and tasks

##  Tech Stack

- **Language:** Java  
- **Platform:** Android SDK  
- **AI Integration:** Retrofit2 + OpenAI Chat Completions API  
- **UI Components:** Material Buttons, RadioGroups, ScrollViews, LinearLayouts

##  Important Notes

- If you were to re create, you would have to insert your own API Key in the OpenAIService.java file
