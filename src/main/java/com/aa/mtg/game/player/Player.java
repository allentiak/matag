package com.aa.mtg.game.player;

public class Player {

    private final String sessionId;
    private final String name;
    private final int life;
    private final Library library;
    private final Hand hand;
    private final Battlefield battlefield;

    public Player(String sessionId, String name, Library library) {
        this.sessionId = sessionId;
        this.name = name;
        this.life = 20;
        this.library = library;
        this.hand = new Hand();
        this.battlefield = new Battlefield();

        for (int i = 0; i < 7; i++) {
            this.hand.getCards().add(this.library.draw());
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public Library getLibrary() {
        return library;
    }

    public Hand getHand() {
        return hand;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

}
