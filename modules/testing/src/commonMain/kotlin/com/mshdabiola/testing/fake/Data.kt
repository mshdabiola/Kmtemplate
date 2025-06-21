/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.testing.fake

import com.mshdabiola.model.Note

val notes =
    listOf(
        Note(1, "Grocery List", "Milk, eggs, bread, cheese, yogurt, apples, bananas, spinach, chicken, pasta"),
        Note(2, "Meeting Notes", "- Discuss project timeline - Review budget - Assign tasks"),
        Note(
            3,
            "Travel Itinerary",
            "Day 1: Arrive in Rome, check into hotel, visit Colosseum Day 2: Vatican City tour, St. Peter's Basilica",
        ),
        Note(4, "Book Recommendations", "1.  Sapiens by Yuval Noah Harari 2 To Kill a Mockingbird by Harper Lee"),
        Note(5, "Workout Plan", "Monday: Legs Tuesday: Chest and Triceps Wednesday: Back and Biceps"),
        Note(6, "Gift Ideas", "For Mom: Spa day, new book, flowers For Dad: Golf clubs, grilling tools"),
        Note(
            7,
            "Password Reminders",
            "**Important: Do not store actual passwords here!** - Email: Hint for password - Bank: Hint for password",
        ),
        Note(
            8,
            "Recipe: Chocolate Chip Cookies",
            "Ingredients: - 2 1/4 cups all-purpose flour - 1 tsp baking soda - ... (rest of the recipe)",
        ),
        Note(9, "Project Ideas", "- Build a personal website - Learn a new programming language - Start a blog"),
        Note(10, "Quotes to Remember", "\"The only way to do great work is to love what you do.\" - Steve Jobs"),
    )
