package ee.technest.typonaut.config;


import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Player;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TyponautSession {

    private static final ConcurrentHashMap<String, Player> typers = new ConcurrentHashMap<>();

    public static void addTyper(String sessid, Player player) {
        typers.put(sessid, player);
    }

    public static Optional<Player> getBySessionId(String sessId) {
        return Optional.ofNullable(typers.get(sessId));
    }

    public static Optional<Player> getLookingTyper() {
        return typers.values()
                .stream()
                .filter(s -> s.getStatus() == Status.LOOKING)
                .findAny();
    }

    public static void removeTyper(String sessid) {
        typers.remove(sessid);
    }
}
