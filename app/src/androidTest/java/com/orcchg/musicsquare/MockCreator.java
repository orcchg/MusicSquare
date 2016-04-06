package com.orcchg.musicsquare;

import com.orcchg.musicsquare.data.model.Musician;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates various mock objects.
 */
public class MockCreator {

    public static int[] musicianIds() {
        return new int[] {1080505, 161247, 125851};
    }

    // #0
    public static Musician firstMusician() {
        String[] genres = new String[] {"pop", "dance", "electronics"};
        String description = "шведская певица и автор песен. " +
                "Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», " +
                "но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage " +
                "на эту песню, который получил название «Stay High». " +
                "4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, " +
                "а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. " +
                "Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.";

        Map<String, String> covers = new HashMap<>();
        covers.put("big", "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000");
        covers.put("small", "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300");

        Musician mock = new Musician.Builder(1080505, "Tove Lo")
                .setGenres(Arrays.asList(genres))
                .setTracksCount(81)
                .setAlbumsCount(22)
                .setLink("http://www.tove-lo.com/")
                .setDescription(description)
                .setCovers(covers)
                .build();

        return mock;
    }

    // #100
    public static Musician anyMusician() {
        String[] genres = new String[] {"pop", "estrada"};
        String description = "российская музыкальная поп-группа. " +
                "До августа 2006 года состояла из Сергея Жукова и Алексея Потехина. " +
                "После Сергей Жуков решил остаться один, вместе с официальным названием группы.";

        Map<String, String> covers = new HashMap<>();
        covers.put("big", "http://avatars.yandex.net/get-music-content/02589e08.p.161247/1000x1000");
        covers.put("small", "http://avatars.yandex.net/get-music-content/02589e08.p.161247/300x300");

        Musician mock = new Musician.Builder(161247, "Руки Вверх!")
                .setGenres(Arrays.asList(genres))
                .setTracksCount(150)
                .setAlbumsCount(27)
                .setLink("http://rukivverh.ru/")
                .setDescription(description)
                .setCovers(covers)
                .build();

        return mock;
    }

    // #316
    public static Musician lastMusician() {
        String[] genres = new String[] {"pop", "rnb", "rap"};
        String description = "американский певец, автор песен, актёр, музыкант и танцор.";

        Map<String, String> covers = new HashMap<>();
        covers.put("big", "http://avatars.yandex.net/get-music-content/c6672c12.p.125851/1000x1000");
        covers.put("small", "http://avatars.yandex.net/get-music-content/c6672c12.p.125851/300x300");

        Musician mock = new Musician.Builder(125851, "Jason Derulo")
                .setGenres(Arrays.asList(genres))
                .setTracksCount(113)
                .setAlbumsCount(58)
                .setLink("http://www.jasonderulo.com")
                .setDescription(description)
                .setCovers(covers)
                .build();

        return mock;
    }
}
