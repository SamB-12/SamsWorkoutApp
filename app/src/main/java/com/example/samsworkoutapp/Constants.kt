package com.example.samsworkoutapp

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)

        val pushUp = ExerciseModel(
            2,
            "Push Ups",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushUp)

        val pushUpAndRotate = ExerciseModel(
            3,
            "Push Up and Rotate",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        exerciseList.add(pushUpAndRotate)

        val stepUp = ExerciseModel(
            4,
            "Step Ups",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(stepUp)

        val highKneeRunning = ExerciseModel(
            5,
            "High Knee Running",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highKneeRunning)

        val tricepsDip = ExerciseModel(
            6,
            "Triceps Dips",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        exerciseList.add(tricepsDip)

        val wallSit = ExerciseModel(
            7,
            "Wall Sits",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallSit)

        val squats = ExerciseModel(
            8,
            "Squats",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squats)

        val lunge = ExerciseModel(
            9,
            "Lunges",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunge)

        val plank = ExerciseModel(
            10,
            "Plank Hold",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val abdominalCrunch = ExerciseModel(
            11,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(abdominalCrunch)

        val sidePlankHold = ExerciseModel(
            12,
            "Side Plank Hold",
            R.drawable.ic_side_plank,
            false,
            false
        )
        exerciseList.add(sidePlankHold)

        return exerciseList
    }
}