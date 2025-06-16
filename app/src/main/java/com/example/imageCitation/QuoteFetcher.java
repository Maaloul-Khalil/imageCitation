package com.example.imageCitation;

import android.util.Log;
import java.util.Random;

/**
 * A utility class that stores French quotes and authors locally.
 */
public class QuoteFetcher {
    private static final String TAG = "QuoteFetcher";

    // Array of French quotes
    private static final String[] FRENCH_QUOTES = {
            "La vie est un mystère qu'il faut vivre, et non un problème à résoudre.",
            "Il n'y a qu'un bonheur dans la vie, c'est d'aimer et d'être aimé.",
            "Le courage n'est pas l'absence de peur, mais la capacité de la vaincre.",
            "La seule façon de faire du bon travail est d'aimer ce que vous faites.",
            "Soyez vous-même, tous les autres sont déjà pris.",
            "L'imagination est plus importante que le savoir.",
            "Dans vingt ans vous serez plus déçus par les choses que vous n'avez pas faites que par celles que vous avez faites.",
            "Le succès c'est d'aller d'échec en échec sans perdre son enthousiasme.",
            "La beauté commence au moment où vous décidez d'être vous-même.",
            "Ce n'est pas la destination qui compte, c'est le voyage.",
            "Il faut toujours viser la lune, car même en cas d'échec, on atterrit dans les étoiles.",
            "La plus grande gloire n'est pas de ne jamais tomber, mais de se relever à chaque chute.",
            "Hier est derrière, demain est un mystère, et aujourd'hui est un cadeau.",
            "Le bonheur n'est pas une destination, c'est une façon de voyager.",
            "On ne voit bien qu'avec le cœur, l'essentiel est invisible pour les yeux."
    };

    // Corresponding authors for each quote
    private static final String[] AUTHORS = {
            "Gandhi",
            "George Sand",
            "Nelson Mandela",
            "Steve Jobs",
            "Oscar Wilde",
            "Albert Einstein",
            "Mark Twain",
            "Winston Churchill",
            "Coco Chanel",
            "Ralph Waldo Emerson",
            "Oscar Wilde",
            "Confucius",
            "Eleanor Roosevelt",
            "Anonyme",
            "Antoine de Saint-Exupéry"
    };

    /**
     * Gets a random French quote.
     *
     * @return The quote text (without quotes).
     */
    public static String getRandomFrenchQuote() {
        Random random = new Random();
        int index = random.nextInt(FRENCH_QUOTES.length);
        String selectedQuote = FRENCH_QUOTES[index];

        Log.d(TAG, "Selected quote at index " + index + ": " + selectedQuote);
        return selectedQuote;
    }

    /**
     * Gets a random French quote author.
     *
     * @return The author name.
     */
    public static String getRandomFrenchQuoteAuthor() {
        Random random = new Random();
        int index = random.nextInt(AUTHORS.length);
        String selectedAuthor = AUTHORS[index];

        Log.d(TAG, "Selected author at index " + index + ": " + selectedAuthor);
        return selectedAuthor;
    }

    /**
     * Gets a random French quote and its author as separate values.
     * This method ensures the quote and author match.
     *
     * @return A QuoteAndAuthor object containing both the quote and author.
     */
    public static QuoteAndAuthor getRandomFrenchQuoteWithAuthor() {
        Random random = new Random();
        int index = random.nextInt(FRENCH_QUOTES.length);
        String quote = FRENCH_QUOTES[index];
        String author = AUTHORS[index];

        Log.d(TAG, "Selected quote and author at index " + index + ": " + quote + " by " + author);
        return new QuoteAndAuthor(quote, author);
    }

    /**
     * Simple class to hold both quote and author.
     */
    public static class QuoteAndAuthor {
        public final String quote;
        public final String author;

        public QuoteAndAuthor(String quote, String author) {
            this.quote = quote;
            this.author = author;
        }
    }
}