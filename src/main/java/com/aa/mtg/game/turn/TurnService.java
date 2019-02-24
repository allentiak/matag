package com.aa.mtg.game.turn;

import com.aa.mtg.cards.Card;
import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.properties.Type;
import com.aa.mtg.event.Event;
import com.aa.mtg.event.EventSender;
import com.aa.mtg.game.player.Player;
import com.aa.mtg.game.status.GameStatus;
import com.aa.mtg.game.turn.phases.Phase;
import com.aa.mtg.message.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Arrays.asList;

@Service
public class TurnService {

    private final EventSender eventSender;

    @Autowired
    public TurnService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public void continueTurn(GameStatus gameStatus) {
        Turn turn = gameStatus.getTurn();
        updateToNextTurn(turn, gameStatus.getActivePlayer(), gameStatus.getInactivePlayer());
        eventSender.sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", gameStatus.getTurn())
        );
    }

    public void playLand(GameStatus gameStatus, int cardId) {
        Turn turn = gameStatus.getTurn();

        boolean alreadyPlayedALand = turn.getCardsPlayedWithinTurn().stream()
                .map(CardInstance::getCard)
                .map(Card::getTypes)
                .anyMatch(types -> types.contains(Type.LAND));

        if (!alreadyPlayedALand) {
            CardInstance cardInstance = gameStatus.getActivePlayer().getHand().extractCardById(cardId);
            turn.addCardToCardsPlayedWithinTurn(cardInstance);
            gameStatus.getActivePlayer().getBattlefield().addCard(cardInstance);

            eventSender.sendToPlayers(
                    asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                    new Event("UPDATE_ACTIVE_PLAYER_BATTLEFIELD", gameStatus.getActivePlayer().getBattlefield())
            );

            eventSender.sendToPlayer(gameStatus.getPlayer1(), new Event("UPDATE_ACTIVE_PLAYER_HAND", gameStatus.getActivePlayer().getHand().getCards()));
            eventSender.sendToPlayer(gameStatus.getPlayer2(), new Event("UPDATE_ACTIVE_PLAYER_HAND", gameStatus.getActivePlayer().getHand().maskedHand()));

        } else {
            eventSender.sendToPlayer(gameStatus.getActivePlayer(), new Event("MESSAGE", new MessageEvent("You already played a land this turn", true)));
        }
    }

    private void updateToNextTurn(Turn turn, Player activePlayer, Player inactivePlayer) {
        if (turn.getCurrentPhase().equals(Phase.CL)) {
            turn.cleanup(inactivePlayer.getName());

        } else {
            if (turn.getCurrentPhaseActivePlayer().equals(activePlayer.getName())) {
                if (Phase.nonOpponentPhases().contains(turn.getCurrentPhase())) {
                    turn.setCurrentPhase(Phase.nextPhase(turn.getCurrentPhase()));
                } else {
                    turn.setCurrentPhaseActivePlayer(inactivePlayer.getName());
                }

            } else {
                turn.setCurrentPhase(Phase.nextPhase(turn.getCurrentPhase()));
                turn.setCurrentPhaseActivePlayer(activePlayer.getName());
            }
        }
    }
}