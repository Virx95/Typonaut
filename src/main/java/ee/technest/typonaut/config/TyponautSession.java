package ee.technest.typonaut.config;


import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Typer;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TyponautSession {

    private static final ConcurrentHashMap<Long, Typer> typers = new ConcurrentHashMap<>();

    public static void addTyper(Typer typer) {
        typers.put(typer.getId(), typer);
    }

    public static Optional<Typer> getBySessionId(String sessId) {
        return typers.values()
                .stream()
                .filter(s -> s.getSession().getId().equals(sessId))
                .findAny();
    }

    public static Optional<Typer> getLookingTyper() {
        return typers.values()
                .stream()
                .filter(s -> s.getStatus() == Status.LOOKING)
                .findAny();
    }
}
