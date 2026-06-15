public class Motorista {
    private String nome;
    private boolean cnhAtiva;
    private int anosEmpresa;

    public Motorista(String nome, boolean cnhAtiva, int anosEmpresa) {
        this.nome = nome;
        this.cnhAtiva = cnhAtiva;
        this.anosEmpresa = anosEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public boolean isCnhAtiva() {
        return cnhAtiva;
    }

    public int getAnosEmpresa() {
        return anosEmpresa;
    }

    @Override
    public String toString() {
        return nome + " (CNH: " + cnhAtiva + ", Anos: " + anosEmpresa + ")";
    }
}