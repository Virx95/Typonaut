package ee.technest.typonaut.config;


import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Typer;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TyponautSession {

    private static final ConcurrentHashMap<String, Typer> typers = new ConcurrentHashMap<>();

    public static void addTyper(String sessid, Typer typer) {
        typers.put(sessid, typer);
    }

    public static Optional<Typer> getBySessionId(String sessId) {
        return Optional.ofNullable(typers.get(sessId));
    }

    public static Optional<Typer> getLookingTyper() {
        return typers.values()
                .stream()
                .filter(s -> s.getStatus() == Status.LOOKING)
                .findAny();
    }

    public static void removeTyper(String sessid) {
        typers.remove(sessid);
    }
}
