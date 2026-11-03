package com.fitlog.app.data

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val unlocked: Boolean,
)

/**
 * Badges derived entirely from real Room data. A brand-new account has
 * every badge greyed out — nothing is unlocked by default. Matches the
 * Part 1 design doc's "Streaks and simple gamification" feature.
 */
fun computeAchievements(workouts: List<WorkoutEntity>, sets: List<WorkoutSetEntity>, streakWeeks: Int): List<Achievement> {
    val totalWorkouts = workouts.size
    val totalSets = sets.size
    val totalVolumeKg = sets.sumOf { (it.weightKg * it.reps).toDouble() }
    val maxSingleWeight = sets.maxOfOrNull { it.weightKg } ?: 0f

    return listOf(
        Achievement("first_workout", "First Steps", "Log your first workout", "\uD83C\uDFC1", totalWorkouts >= 1),
        Achievement("streak_2", "Building a Habit", "Reach a 2-week training streak", "\uD83D\uDD25", streakWeeks >= 2),
        Achievement("streak_4", "4-Week Streak", "Reach a 4-week training streak", "\uD83D\uDD25", streakWeeks >= 4),
        Achievement("ten_workouts", "Getting Serious", "Log 10 workouts", "\uD83D\uDCAA", totalWorkouts >= 10),
        Achievement("hundred_sets", "Century Club", "Log 100 sets", "\uD83D\uDCAF", totalSets >= 100),
        Achievement("volume_5000", "Volume Beast", "Lift 5,000kg total volume", "\uD83C\uDFCB", totalVolumeKg >= 5000.0),
        Achievement("heavy_lifter", "Heavy Lifter", "Log a set of 100kg or more", "\uD83E\uDD47", maxSingleWeight >= 100f),
    )
}

