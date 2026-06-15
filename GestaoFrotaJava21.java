import java.util.*;

public class GestaoFrotaJava21 {

    static List<Motorista> getMotoristaDaEmpresa() {
        return Arrays.asList(
            new Motorista("Carlos", true, 8),
            new Motorista("Ana",    false, 12),
            new Motorista("Pedro",  true, 3),
            new Motorista("Maria",  true, 15),
            new Motorista("João",   false, 5)
        );
    }

    public static void main(String[] args) {
        getMotoristaDaEmpresa().stream()
            .filter(Motorista::isCnhAtiva)
            .sorted(Comparator.comparingInt(Motorista::getAnosEmpresa))
            .forEach(System.out::println);
    }
}