package com.matag.game.turn.phases;

import com.matag.game.status.GameStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Main2Phase implements Phase {
  public static final String M2 = "M2";

  private final EndTurnPhase endTurnPhase;

  @Override
  public void apply(GameStatus gameStatus) {
    gameStatus.getTurn().setCurrentPhase(EndTurnPhase.ET);
    gameStatus.getTurn().setCurrentPhaseActivePlayer(gameStatus.getCurrentPlayer().getName());
    endTurnPhase.apply(gameStatus);
  }
}
