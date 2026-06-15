import java.util.*;

public class GestaoFrotaJava7 {
    static List<Motorista> getMotoristaDaEmpresa() {
        return Arrays.asList(
                new Motorista("Carlos", true, 8),
                new Motorista("Ana", false, 12),
                new Motorista("Pedro", true, 3),
                new Motorista("Maria", true, 15),
                new Motorista("João", false, 5));
    }

    public static void main(String[] args) {
        List<Motorista> motoristas = getMotoristaDaEmpresa();
        List<Motorista> habilitados = new ArrayList<>();

        // Filtrando motoristas com CNH ativa
        for (Motorista m : motoristas) {
            if (m.isCnhAtiva()) {
                habilitados.add(m);
            }
        }

        // Ordenando por tempo de casa usando Comparator anônimo
        Collections.sort(habilitados, new Comparator<Motorista>() {
            @Override
            public int compare(Motorista m1, Motorista m2) {
                return Integer.compare(m1.getAnosEmpresa(), m2.getAnosEmpresa());
            }
        });

        for (Motorista m : habilitados) {
            System.out.println(m);
        }
    }
}