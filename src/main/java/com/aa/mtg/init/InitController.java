package com.aa.mtg.init;

import com.aa.mtg.event.Event;
import com.aa.mtg.event.EventSender;
import com.aa.mtg.status.GameStatus;
import com.aa.mtg.status.GameStatusRepository;
import com.aa.mtg.status.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class InitController {

    private EventSender eventSender;
    private GameStatusRepository gameStatusRepository;

    public InitController(EventSender eventSender, GameStatusRepository gameStatusRepository) {
        this.eventSender = eventSender;
        this.gameStatusRepository = gameStatusRepository;
    }

    @MessageMapping("/init")
    public void init(SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        log.info("Init request received for sessionId '{}'", sessionId);

        GameStatus gameStatus = gameStatusRepository.get();

        if (gameStatus.getPlayer1() == null) {
            gameStatus.setPlayer1(new Player(sessionId));

            eventSender.sendToUser(sessionId, Event.builder()
                    .type("INIT")
                    .value("WAITING_OPPONENT")
                    .build());
        } else if (gameStatus.getPlayer2() == null) {
            gameStatus.setPlayer2(new Player (sessionId));

            eventSender.sendToAll(Event.builder()
                    .type("INIT")
                    .value("COMPLETED")
                    .build());
        }
    }

}
