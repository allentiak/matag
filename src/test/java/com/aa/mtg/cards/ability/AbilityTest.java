package com.aa.mtg.cards.ability;

import org.junit.Test;

import static com.aa.mtg.cards.ability.type.AbilityType.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class AbilityTest {

    @Test
    public void simpleAbilityText() {
        // Given
        Ability ability = new Ability(FLYING);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Flying.");
    }

    @Test
    public void drawXCardsText() {
        // Given
        Ability ability = new Ability(DRAW_X_CARDS, emptyList(), singletonList("2"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Draw 2 cards.");
    }

    @Test
    public void gainXLifeText() {
        // Given
        Ability ability = new Ability(ADD_X_LIFE, emptyList(), singletonList("2"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Gain 2 life.");
    }

    @Test
    public void loseXLifeText() {
        // Given
        Ability ability = new Ability(ADD_X_LIFE, emptyList(), singletonList("-2"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Lose 2 life.");
    }

    @Test
    public void eachPlayerGainsXLifeText() {
        // Given
        Ability ability = new Ability(EACH_PLAYERS_ADD_X_LIFE, emptyList(), singletonList("2"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Each player gains 2 life.");
    }

    @Test
    public void eachPlayerLosesXLifeText() {
        // Given
        Ability ability = new Ability(EACH_PLAYERS_ADD_X_LIFE, emptyList(), singletonList("-2"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Each player loses 2 life.");
    }

    @Test
    public void enchantedCreatureGetOneAbilityText() {
        // Given
        Ability ability = new Ability(ENCHANTED_CREATURE_GETS, emptyList(), singletonList("+2/+2"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Enchanted creature gets +2/+2.");
    }

    @Test
    public void enchantedCreatureGetMultipleAbilitiesText() {
        // Given
        Ability ability = new Ability(asList(ENCHANTED_CREATURE_GETS, ENCHANTED_CREATURE_GETS, ENCHANTED_CREATURE_GETS), emptyList(), asList("+2/+2", "TRAMPLE", "HASTE"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Enchanted creature gets +2/+2, trample and haste.");
    }

    @Test
    public void draw1CardLose2LifeText() {
        // Given
        Ability ability = new Ability(asList(DRAW_X_CARDS, ADD_X_LIFE), emptyList(), asList("1", "-2"), null);

        // When
        String text = ability.getAbilityTypesText();

        // Then
        assertThat(text).isEqualTo("Draw 1 cards. Lose 2 life.");
    }
}